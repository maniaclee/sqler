package com.lvbby.sqler;

import com.google.common.collect.Lists;
import com.lvbby.sqler.core.DefaultHierarchyTableHandler;
import com.lvbby.sqler.core.SqlExecutor;
import com.lvbby.sqler.factory.DDLTableFactory;
import com.lvbby.sqler.factory.DbConnectorConfig;
import com.lvbby.sqler.handler.Handlers;
import com.lvbby.sqler.handler.JavaTypeHandlers;
import com.lvbby.sqler.handler.OutputHandler;
import com.lvbby.sqler.handler.TemplateEngineHandlerAdaptor;
import com.lvbby.sqler.render.beetl.BeetlTemplateEngine;
import org.apache.commons.io.IOUtils;
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
        String url = "jdbc:mysql://localhost:3306/user";
        String user = "root";
        String pass = "";
        DbConnectorConfig dbConnectorConfig = new DbConnectorConfig();
        dbConnectorConfig.setJdbcUrl(url);
        dbConnectorConfig.setUser(user);
        dbConnectorConfig.setPassword(pass);
        dbConnectorConfig.setAuthor("maniac.lee");


        SqlExecutor sqlExecutor = new SqlExecutor()
                .setConfig(dbConnectorConfig)
//                .setTableFactory(new JdbcTableFactory(dbConnectorConfig))
                .setTableFactory(DDLTableFactory.create(IOUtils.toString(TestCase.class.getClassLoader().getResourceAsStream("testddl.sql"))))
                .setTableHandlers(Lists.newArrayList(
//                        JavaTypeHandlers.necessary,
                        JavaTypeHandlers.basic,
                        JavaTypeHandlers.boxingType,
                        Handlers.tableCase,
                        Handlers.fieldCase,
//                        Handlers.print,
                        DefaultHierarchyTableHandler
                                .of(TemplateEngineHandlerAdaptor.
                                        of(BeetlTemplateEngine.create(IOUtils.toString(TestCase.class.getClassLoader().getResourceAsStream("templates/JavaBean.btl"))))
                                        .bind("className", context -> context.getTableInfo().getName() + "Entity"))
                                .addNext(OutputHandler.create()
                                        .setDestDir(new File("/Users/peng/tmp/gen/entity"))
                                        .setFileNameConverter(context -> context.getTableInfo().getName() + "Entity.java"))
                ));
        sqlExecutor.run();
    }


}
