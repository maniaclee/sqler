package com.lvbby.sqler.util;

import com.google.common.base.CaseFormat;
import com.lvbby.codema.bean.JavaBean;
import com.lvbby.codema.bean.JavaBeanDecoder;
import com.lvbby.codema.bean.JavaBeanField;
import com.lvbby.codema.render.TemplateEngineFactory;
import com.lvbby.codema.render.beetl.BeetlTemplateEngine;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.Validate;

import java.io.IOException;
import java.util.List;

/**
 * Created by peng on 16/8/3.
 */
public class Apps {

    private static String getString(String path) {
        Validate.notBlank(path, "classpath resource can't be blank");
        try {
            return IOUtils.toString(Apps.class.getClassLoader().getResourceAsStream(path));
        } catch (IOException e) {
            throw new RuntimeException("failed to get context from classpath:" + path);
        }
    }

    public static String genCreateTableDDL(String javaBean, CaseFormat caseFormat) {
        Validate.notBlank(javaBean, "javabean can't be blank");
        JavaBeanDecoder javaBeanDecoder = new JavaBeanDecoder();
        JavaBean parse = javaBeanDecoder.parse(javaBean);

        removeStatic(parse);

        if (caseFormat != null)
            format(caseFormat, parse);

        BeetlTemplateEngine templateEngine = TemplateEngineFactory.create(BeetlTemplateEngine.class, getString("templates/CreateTable.btl"));
        templateEngine.getGroupTemplate().registerFunctionPackage("lee", JdbcTypes.class);
        templateEngine.bind("b", parse);
        return templateEngine.render();
    }

    private static void removeStatic(JavaBean bean) {
        int index = -1;
        List<JavaBeanField> fields = bean.getFields();
        for (int i = 0; i < fields.size(); i++) {
            if (fields.get(i).getModifiers().contains("static")) {
                index = i;
                break;
            }
        }
        if (index >= 0)
            fields.remove(index);
    }

    private static void format(CaseFormat caseFormat, JavaBean parse) {
        parse.setClassName(CaseFormat.UPPER_CAMEL.to(caseFormat, parse.getClassName()));
        parse.getFields().forEach(javaBeanField -> javaBeanField.setName(CaseFormat.UPPER_CAMEL.to(caseFormat, javaBeanField.getName())));
    }
}
