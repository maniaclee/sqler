package com.lvbby.sqler;

import com.google.common.collect.Lists;
import com.lvbby.sqler.core.SqlExecutor;
import com.lvbby.sqler.core.TableFactory;
import com.lvbby.sqler.handler.JavaTypeHandler;
import com.lvbby.sqler.handler.PrintHandler;
import com.lvbby.sqler.handler.TemplateEngineHandler;
import com.lvbby.sqler.handler.TypeHandler;
import com.lvbby.sqler.jdbc.DbConnectorConfig;
import com.lvbby.sqler.jdbc.JdbcTableFactory;
import com.lvbby.sqler.render.BeetlTemplateEngine;
import org.apache.commons.io.IOUtils;
import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.ClasspathResourceLoader;
import org.beetl.core.resource.StringTemplateResourceLoader;
import org.junit.Test;

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
    public void tee23() throws IOException {
        BeetlTemplateEngine t = BeetlTemplateEngine.create(IOUtils.toString(TestCase.class.getClassLoader().getResourceAsStream("templates/test.t")));
        System.out.println(t.render("name", "beetl"));
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

        TableFactory jdbcTableFactory = new JdbcTableFactory(dbConnectorConfig);

        SqlExecutor sqlExecutor = new SqlExecutor(jdbcTableFactory, Lists.newArrayList(
                JavaTypeHandler.instance,
                TypeHandler.javaPrimitive2BoxingType,
                PrintHandler.instance,
                TemplateEngineHandler.
                        of(BeetlTemplateEngine.create(IOUtils.toString(TestCase.class.getClassLoader().getResourceAsStream("templates/test.t"))))
                        .setRootKey("c")
        ));
        sqlExecutor.run();
    }


}
