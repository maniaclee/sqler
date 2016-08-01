package com.lvbby.sqler;

import com.google.common.collect.Lists;
import com.lvbby.sqler.core.SqlExecutor;
import com.lvbby.sqler.core.impl.DefaultHierarchyContextHandler;
import com.lvbby.sqler.factory.DDLTableFactory;
import com.lvbby.sqler.factory.DbConnectorConfig;
import com.lvbby.sqler.factory.JdbcTableFactory;
import com.lvbby.sqler.handler.Handlers;
import com.lvbby.sqler.handler.JavaTypeHandlers;
import com.lvbby.sqler.handler.OutputHandler;
import com.lvbby.sqler.handler.TemplateEngineHandler;
import com.lvbby.sqler.pipeline.OutputPipeLine;
import com.lvbby.sqler.pipeline.PipeLines;
import com.lvbby.sqler.render.SqlerResource;
import com.lvbby.sqler.render.beetl.BeetlTemplateEngine;
import com.lvbby.sqler.render.beetl.LeeFn;
import com.lvbby.sqler.util.LeeUtil;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.ClasspathResourceLoader;
import org.beetl.core.resource.StringTemplateResourceLoader;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

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
    public void cases() throws IOException {
        DbConnectorConfig dbConnectorConfig = getDbConnectorConfig();
        SqlExecutor sqlExecutor = new SqlExecutor()
                .setConfig(dbConnectorConfig)
                .setTableFactory(JdbcTableFactory.create(dbConnectorConfig))
                //                .setTableFactory(DDLTableFactory.create(IOUtils.toString(TestCase.class.getClassLoader().getResourceAsStream("testddl.sql"))))
                .setContextHandlers(Lists.newArrayList(
                        //                        JavaTypeHandlers.necessary,
                        JavaTypeHandlers.basic,
                        JavaTypeHandlers.boxingType,
                        Handlers.tableCase,
                        Handlers.fieldCase,
                        //                        Handlers.print,
                        DefaultHierarchyContextHandler
                                .of(TemplateEngineHandler.
                                        of(BeetlTemplateEngine.create(IOUtils.toString(TestCase.class.getClassLoader().getResourceAsStream("templates/JavaBean.btl"))))
                                        .bind("className", context -> context.getTableInfo().getName() + "Entity"))
                                .addNext(OutputHandler.create()
                                        .setDestDir(new File("/Users/peng/tmp/gen/entity"))
                                        .setFileNameConverter(context -> context.getTableInfo().getName() + "Entity.java"))

                ));
        sqlExecutor.run();
    }

    @Test
    public void casesPipe() throws IOException {
        DbConnectorConfig dbConnectorConfig = getDbConnectorConfig();
        dbConnectorConfig.check();
        SqlExecutor sqlExecutor = new SqlExecutor()
                .setConfig(dbConnectorConfig)
                // .setTableFactory(JdbcTableFactory.create(dbConnectorConfig))
                .setTableFactory(DDLTableFactory.create(IOUtils.toString(TestCase.class.getClassLoader().getResourceAsStream("testddl.sql"))))
                .setContextHandlers(Lists.newArrayList(
                        JavaTypeHandlers.basic,
                        JavaTypeHandlers.boxingType,
                        Handlers.tableCase,
                        Handlers.fieldCase,
                        TemplateEngineHandler.
                                of(BeetlTemplateEngine.create(SqlerResource.Beetl_JavaBean.getResourceAsString()))
                                .bind("className", context -> LeeFn.getEntityClassName(context.getTableInfo()))
                                .addPipeLine(OutputPipeLine.create()
                                        .setDestDir(dbConnectorConfig.calDirectory(LeeFn.getEntityPackage(dbConnectorConfig.getPack())))
                                        .setFileNameConverter(context -> LeeFn.getEntityClassName(context.getTableInfo()))
                                        .setSuffix("java"))
                                .addPipeLine(PipeLines.print),
                        TemplateEngineHandler.
                                of(BeetlTemplateEngine.create(SqlerResource.Beetl_MybatisDao.getResourceAsString()))
                                .addPipeLine(OutputPipeLine.create()
                                        .setDestDir(dbConnectorConfig.calDirectory(LeeFn.getDaoPackage(dbConnectorConfig.getPack())))
                                        .setFileNameConverter(context -> LeeFn.getDaoClassName(context.getTableInfo()))
                                        .setSuffix("java"))
                                .addPipeLine(PipeLines.print),
                        TemplateEngineHandler.
                                of(BeetlTemplateEngine.create(SqlerResource.Beetl_Repository.getResourceAsString()))
                                .addPipeLine(OutputPipeLine.create()
                                        .setDestDir(dbConnectorConfig.calDirectory(LeeFn.getRepositoryPackage(dbConnectorConfig.getPack())))
                                        .setFileNameConverter(context -> LeeFn.getRepositoryClassName(context.getTableInfo()))
                                        .setSuffix("java"))
                                .addPipeLine(PipeLines.print),
                        TemplateEngineHandler.
                                of(BeetlTemplateEngine.create(SqlerResource.Beetl_MybatisDaoXml.getResourceAsString()))
                                .addPipeLine(OutputPipeLine.create()
                                        .setDestDir(dbConnectorConfig.calDirectory(LeeFn.getMapperXmlPackage(dbConnectorConfig.getPack())))
                                        .setFileNameConverter(context -> LeeFn.getDaoClassName(context.getTableInfo()))
                                        .setSuffix("xml"))
                                .addPipeLine(PipeLines.print)

                ));
        sqlExecutor.run();
    }

    private DbConnectorConfig getDbConnectorConfig() {
        DbConnectorConfig dbConnectorConfig = new DbConnectorConfig();
        dbConnectorConfig.setJdbcUrl("jdbc:mysql://localhost:3306/user");
        dbConnectorConfig.setUser("root");
        dbConnectorConfig.setPassword("");
        dbConnectorConfig.setAuthor("maniac.lee");
        dbConnectorConfig.setPack("com.lvbby.com.test");
        dbConnectorConfig.setRootPath("/Users/psyco/workspace/github/user/user-biz/src/main/java");
        return dbConnectorConfig;
    }

    @Test
    public void testPack() {
        LeeUtil.validatePackage("dao");
        System.out.println(StringUtils.isBlank("dao") || "dao".matches("^([a-zA-Z_])[a-zA-Z0-9_\\-\\.]*([^\\.])$"));
        System.out.println("_daoA.a".matches("^([a-zA-Z_])[a-zA-Z0-9_\\-\\.]*([^\\.])$"));
    }


}
