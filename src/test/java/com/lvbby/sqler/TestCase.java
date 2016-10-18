package com.lvbby.sqler;

import com.google.common.base.CaseFormat;
import com.google.common.collect.Lists;
import com.lvbby.codebot.chain.ContextHandler;
import com.lvbby.codema.render.TemplateEngineFactory;
import com.lvbby.sqler.core.Context;
import com.lvbby.sqler.core.SqlExecutor;
import com.lvbby.sqler.factory.JdbcConfig;
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
import java.util.List;

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
        JdbcConfig jdbcConfig = getDbConnectorConfig();
        jdbcConfig.check();
        SqlExecutor sqlExecutor = new SqlExecutor()
                .setConfig(jdbcConfig)
                .setTableFactory(JdbcTableFactory.create(jdbcConfig))
                // .setTableFactory(DDLTableFactory.create(IOUtils.toString(TestCase.class.getClassLoader().getResourceAsStream("testddl.sql"))))
                .setContextHandlers(Lists.newArrayList(
                        JavaTypeHandlers.basic,
                        // JavaTypeHandlers.boxingType,
                        Handlers.tableCase,
                        Handlers.fieldCase,
                        TemplateEngineHandler.
                                of(TemplateEngineFactory.create(SqlerResource.Beetl_JavaBean.getResourceAsString()))
                                .bind("className", context -> LeeFn.getEntityClassName(context.getTableInfo()))
                                .bind("classPack", context -> LeeFn.getEntityPackage(jdbcConfig.getPack()))
                                .toPipeLine()
                                .addPipeLine(OutputPipeLine.create()
                                        .setDestDir(jdbcConfig.calDirectory(LeeFn.getEntityPackage(jdbcConfig.getPack())))
                                        .setFileNameConverter(context -> LeeFn.getEntityClassName(context.getTableInfo()))
                                        .setSuffix("java"))
                                .addPipeLine(PipeLines.print),
                        TemplateEngineHandler.
                                of(TemplateEngineFactory.create(SqlerResource.Beetl_JavaBean.getResourceAsString()))
                                .bind("className", context -> LeeFn.getDtoClassName(context.getTableInfo()))
                                .bind("classPack", context -> LeeFn.getDtoPackage(jdbcConfig.getPack()))
                                .toPipeLine()
                                .addPipeLine(OutputPipeLine.create()
                                        .setDestDir(jdbcConfig.calDirectory(LeeFn.getDtoPackage(jdbcConfig.getPack())))
                                        .setFileNameConverter(context -> LeeFn.getDtoClassName(context.getTableInfo()))
                                        .setSuffix("java"))
                                .addPipeLine(PipeLines.print),
                        TemplateEngineHandler.
                                of(TemplateEngineFactory.create(SqlerResource.Beetl_MybatisDao.getResourceAsString()))
                                .toPipeLine()
                                .addPipeLine(OutputPipeLine.create()
                                        .setDestDir(jdbcConfig.calDirectory(LeeFn.getDaoPackage(jdbcConfig.getPack())))
                                        .setFileNameConverter(context -> LeeFn.getDaoClassName(context.getTableInfo()))
                                        .setSuffix("java"))
                                .addPipeLine(PipeLines.print),
                        TemplateEngineHandler.
                                of(TemplateEngineFactory.create(SqlerResource.Beetl_Repository.getResourceAsString()))
                                .toPipeLine()
                                .addPipeLine(OutputPipeLine.create()
                                        .setDestDir(jdbcConfig.calDirectory(LeeFn.getRepositoryPackage(jdbcConfig.getPack())))
                                        .setFileNameConverter(context -> LeeFn.getRepositoryClassName(context.getTableInfo()))
                                        .setSuffix("java"))
                                .addPipeLine(PipeLines.print),
                        TemplateEngineHandler.
                                of(TemplateEngineFactory.create(SqlerResource.Beetl_MybatisDaoXml.getResourceAsString()))
                                .toPipeLine()
                                .addPipeLine(OutputPipeLine.create()
                                        .setDestDir(jdbcConfig.calDirectory(LeeFn.getMapperXmlPackage(jdbcConfig.getPack())))
                                        .setFileNameConverter(context -> LeeFn.getDaoClassName(context.getTableInfo()))
                                        .setSuffix("xml"))
                                .addPipeLine(PipeLines.print)

                ));
        sqlExecutor.run();
    }

    public static List<ContextHandler<Context>> getTemplateEngineHandlers(JdbcConfig jdbcConfig) {
        return Lists.newArrayList(TemplateEngineHandler.
                        of(TemplateEngineFactory.create(SqlerResource.Beetl_JavaBean.getResourceAsString()))
                        .bind("className", context -> LeeFn.getEntityClassName(context.getTableInfo()))
                        .bind("classPack", context -> LeeFn.getEntityPackage(jdbcConfig.getPack()))
                        .toPipeLine()
                        .addPipeLine(OutputPipeLine.create()
                                .setDestDir(jdbcConfig.calDirectory(LeeFn.getEntityPackage(jdbcConfig.getPack())))
                                .setFileNameConverter(context -> LeeFn.getEntityClassName(context.getTableInfo()))
                                .setSuffix("java"))
                        .addPipeLine(PipeLines.print),
                TemplateEngineHandler.
                        of(TemplateEngineFactory.create(SqlerResource.Beetl_JavaBean.getResourceAsString()))
                        .bind("className", context -> LeeFn.getDtoClassName(context.getTableInfo()))
                        .bind("classPack", context -> LeeFn.getDtoPackage(jdbcConfig.getPack()))
                        .toPipeLine()
                        .addPipeLine(OutputPipeLine.create()
                                .setDestDir(jdbcConfig.calDirectory(LeeFn.getDtoPackage(jdbcConfig.getPack())))
                                .setFileNameConverter(context -> LeeFn.getDtoClassName(context.getTableInfo()))
                                .setSuffix("java"))
                        .addPipeLine(PipeLines.print),
                TemplateEngineHandler.
                        of(TemplateEngineFactory.create(SqlerResource.Beetl_MybatisDao.getResourceAsString()))
                        .toPipeLine()
                        .addPipeLine(OutputPipeLine.create()
                                .setDestDir(jdbcConfig.calDirectory(LeeFn.getDaoPackage(jdbcConfig.getPack())))
                                .setFileNameConverter(context -> LeeFn.getDaoClassName(context.getTableInfo()))
                                .setSuffix("java"))
                        .addPipeLine(PipeLines.print),
                TemplateEngineHandler.
                        of(TemplateEngineFactory.create(SqlerResource.Beetl_Repository.getResourceAsString()))
                        .toPipeLine()
                        .addPipeLine(OutputPipeLine.create()
                                .setDestDir(jdbcConfig.calDirectory(LeeFn.getRepositoryPackage(jdbcConfig.getPack())))
                                .setFileNameConverter(context -> LeeFn.getRepositoryClassName(context.getTableInfo()))
                                .setSuffix("java"))
                        .addPipeLine(PipeLines.print),
                TemplateEngineHandler.
                        of(TemplateEngineFactory.create(SqlerResource.Beetl_MybatisDaoXml.getResourceAsString()))
                        .toPipeLine()
                        .addPipeLine(OutputPipeLine.create()
                                .setDestDir(jdbcConfig.calDirectory(LeeFn.getMapperXmlPackage(jdbcConfig.getPack())))
                                .setFileNameConverter(context -> LeeFn.getDaoClassName(context.getTableInfo()))
                                .setSuffix("xml"))
                        .addPipeLine(PipeLines.print));
    }


    private JdbcConfig getDbConnectorConfig() {
        JdbcConfig jdbcConfig = new JdbcConfig();
        jdbcConfig.setJdbcUrl("jdbc:mysql://localhost:3306/lvbby");
        jdbcConfig.setUser("root");
        jdbcConfig.setPassword("");
        jdbcConfig.setAuthor("lipeng");
        jdbcConfig.setPack("com.lvbby.garfield.test");
        // dbConnectorConfig.setRootPath("/Users/psyco/workspace/github/user/user-biz/src/main/java");
        jdbcConfig.setRootPath("/Users/psyco/workspace/coding/garfield/garfield-biz/src/main/java");
        return jdbcConfig;
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

    @Test
    public void testSqler() throws Exception {
        JdbcConfig dbConnectorConfig = getDbConnectorConfig();
        Sqler.createSqlExecutorTemplate(dbConnectorConfig)
                .addContextHandlers(Sqler.getTemplateEngineHandlersOnlyPrint(dbConnectorConfig))
                .run();
    }

}
