package com.lvbby.sqler;

import com.google.common.base.CaseFormat;
import com.google.common.collect.Lists;
import com.lvbby.codebot.chain.ContextHandler;
import com.lvbby.codema.render.TemplateEngineFactory;
import com.lvbby.sqler.core.Config;
import com.lvbby.sqler.core.Context;
import com.lvbby.sqler.core.SqlExecutor;
import com.lvbby.sqler.factory.DbConnectorConfig;
import com.lvbby.sqler.factory.JdbcTableFactory;
import com.lvbby.sqler.handler.Handlers;
import com.lvbby.sqler.handler.JavaTypeHandlers;
import com.lvbby.sqler.handler.TemplateEngineHandler;
import com.lvbby.sqler.pipeline.OutputPipeLine;
import com.lvbby.sqler.pipeline.PipeLines;
import com.lvbby.sqler.render.SqlerResource;
import com.lvbby.sqler.render.beetl.LeeFn;
import com.lvbby.sqler.util.Apps;
import com.lvbby.sqler.util.LeeUtil;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.ClasspathResourceLoader;
import org.beetl.core.resource.StringTemplateResourceLoader;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;

/**
 * Created by lipeng on 16/7/28.
 */
public class TestCase {


    @org.junit.Test
    public void tee() throws IOException {
        StringTemplateResourceLoader resourceLoader = new StringTemplateResourceLoader();
        Configuration cfg = Configuration.defaultConfiguration();
        GroupTemplate gt = new GroupTemplate(resourceLoader, cfg);
        Template t = gt.getTemplate("hello,${name}");
        t.binding("name", "beetl");
        String str = t.render();
        System.out.println(str);
    }

    @org.junit.Test
    public void tee2() throws IOException {
        ClasspathResourceLoader resourceLoader = new ClasspathResourceLoader();
        Configuration cfg = Configuration.defaultConfiguration();
        GroupTemplate gt = new GroupTemplate(resourceLoader, cfg);
        Template t = gt.getTemplate("/templates/test.t");
        t.binding("name", "beetl");
        String str = t.render();
        System.out.println(str);
    }


    @Test
    public void casesPipe() throws IOException {
        DbConnectorConfig dbConnectorConfig = getDbConnectorConfig();
        dbConnectorConfig.check();
        SqlExecutor sqlExecutor = new SqlExecutor()
                .setConfig(dbConnectorConfig)
                .setTableFactory(JdbcTableFactory.create(dbConnectorConfig))
                // .setTableFactory(DDLTableFactory.create(IOUtils.toString(TestCase.class.getClassLoader().getResourceAsStream("testddl.sql"))))
                .setContextHandlers(Lists.newArrayList(
                        JavaTypeHandlers.basic,
                        // JavaTypeHandlers.boxingType,
                        Handlers.tableCase,
                        Handlers.fieldCase,
                        TemplateEngineHandler.
                                of(TemplateEngineFactory.create(SqlerResource.Beetl_JavaBean.getResourceAsString()))
                                .bind("className", context -> LeeFn.getEntityClassName(context.getTableInfo()))
                                .bind("classPack", context -> LeeFn.getEntityPackage(dbConnectorConfig.getPack()))
                                .addPipeLine(OutputPipeLine.create()
                                        .setDestDir(dbConnectorConfig.calDirectory(LeeFn.getEntityPackage(dbConnectorConfig.getPack())))
                                        .setFileNameConverter(context -> LeeFn.getEntityClassName(context.getTableInfo()))
                                        .setSuffix("java"))
                                .addPipeLine(PipeLines.print),
                        TemplateEngineHandler.
                                of(TemplateEngineFactory.create(SqlerResource.Beetl_JavaBean.getResourceAsString()))
                                .bind("className", context -> LeeFn.getDtoClassName(context.getTableInfo()))
                                .bind("classPack", context -> LeeFn.getDtoPackage(dbConnectorConfig.getPack()))
                                .addPipeLine(OutputPipeLine.create()
                                        .setDestDir(dbConnectorConfig.calDirectory(LeeFn.getDtoPackage(dbConnectorConfig.getPack())))
                                        .setFileNameConverter(context -> LeeFn.getDtoClassName(context.getTableInfo()))
                                        .setSuffix("java"))
                                .addPipeLine(PipeLines.print),
                        TemplateEngineHandler.
                                of(TemplateEngineFactory.create(SqlerResource.Beetl_MybatisDao.getResourceAsString()))
                                .addPipeLine(OutputPipeLine.create()
                                        .setDestDir(dbConnectorConfig.calDirectory(LeeFn.getDaoPackage(dbConnectorConfig.getPack())))
                                        .setFileNameConverter(context -> LeeFn.getDaoClassName(context.getTableInfo()))
                                        .setSuffix("java"))
                                .addPipeLine(PipeLines.print),
                        TemplateEngineHandler.
                                of(TemplateEngineFactory.create(SqlerResource.Beetl_Repository.getResourceAsString()))
                                .addPipeLine(OutputPipeLine.create()
                                        .setDestDir(dbConnectorConfig.calDirectory(LeeFn.getRepositoryPackage(dbConnectorConfig.getPack())))
                                        .setFileNameConverter(context -> LeeFn.getRepositoryClassName(context.getTableInfo()))
                                        .setSuffix("java"))
                                .addPipeLine(PipeLines.print),
                        TemplateEngineHandler.
                                of(TemplateEngineFactory.create(SqlerResource.Beetl_MybatisDaoXml.getResourceAsString()))
                                .addPipeLine(OutputPipeLine.create()
                                        .setDestDir(dbConnectorConfig.calDirectory(LeeFn.getMapperXmlPackage(dbConnectorConfig.getPack())))
                                        .setFileNameConverter(context -> LeeFn.getDaoClassName(context.getTableInfo()))
                                        .setSuffix("xml"))
                                .addPipeLine(PipeLines.print)

                ));
        sqlExecutor.run();
    }

    public static ContextHandler<Context> createJavaBeanTemplate(Config dbConnectorConfig, String sufix) {
        return TemplateEngineHandler.
                of(TemplateEngineFactory.create(SqlerResource.Beetl_JavaBean.getResourceAsString()))
                .bind("className", context -> context.getTableInfo() + StringUtils.capitalize(sufix))
                .bind("classPack", context -> LeeUtil.concatPackage(dbConnectorConfig.getPack(), sufix.toLowerCase()))
                .addPipeLine(OutputPipeLine.create()
                        .setDestDir(dbConnectorConfig.calDirectory(LeeFn.getEntityPackage(dbConnectorConfig.getPack())))
                        .setFileNameConverter(context -> context.getTableInfo() + StringUtils.capitalize(sufix))
                        .setSuffix("java"))
                .addPipeLine(PipeLines.print);
    }

    private DbConnectorConfig getDbConnectorConfig() {
        DbConnectorConfig dbConnectorConfig = new DbConnectorConfig();
        dbConnectorConfig.setJdbcUrl("jdbc:mysql://localhost:3306/user");
        dbConnectorConfig.setUser("root");
        dbConnectorConfig.setPassword("");
        dbConnectorConfig.setAuthor("lipeng");
        dbConnectorConfig.setPack("com.lvbby.user");
        // dbConnectorConfig.setRootPath("/Users/psyco/workspace/github/user/user-biz/src/main/java");
        dbConnectorConfig.setRootPath("/Users/peng/workspace/github/user/user-biz/src/main/java");
        return dbConnectorConfig;
    }

    @Test
    public void testJdbc() throws Exception {
        // JdbcTableFactory jdbcTableFactory = JdbcTableFactory.create(getDbConnectorConfig());
        // List<TableInfo> tables = jdbcTableFactory.getTables();
        // System.out.println(tables);
        // for (TableInfo table : tables) {
        //     System.out.println(table.getFields().size());
        // }

        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/user",
                "root",
                "");
        DatabaseMetaData meta = conn.getMetaData();
        ResultSet rs = meta.getColumns(null, "%", "user_detail", "%");
        // // ResultSet rs = meta.getColumns(null, null, tableInfo.getName(), "%");
        while (rs.next()) {
            System.out.println(rs.getString(4));
        }
    }

    @Test
    public void testPack() {
        LeeUtil.validatePackage("dao");
        System.out.println(StringUtils.isBlank("dao") || "dao".matches("^([a-zA-Z_])[a-zA-Z0-9_\\-\\.]*([^\\.])$"));
        System.out.println("_daoA.a".matches("^([a-zA-Z_])[a-zA-Z0-9_\\-\\.]*([^\\.])$"));
    }

    @Test
    public void testCreateTableDdl() throws Exception {
        String s = "/Users/peng/workspace/github/user/user-biz/src/main/java/com/lvbby/user/entity/UserEntity.java";
        s = IOUtils.toString(new FileInputStream(s));
        String re = Apps.genCreateTableDDL(s, CaseFormat.LOWER_UNDERSCORE);
        System.out.println(re);
    }


}
