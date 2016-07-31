package com.lvbby.sqler.render;

import com.lvbby.sqler.exceptions.SqlerException;
import org.apache.commons.io.IOUtils;

import java.io.IOException;

/**
 * Created by peng on 16/7/30.
 */
public enum SqlerResource {
    Beetl_JavaBean("templates/JavaBean.btl"),

    Beetl_MybatisDao("templates/MybatisDao.btl"),
    Beetl_Repository("templates/Repository.btl"),
    Beetl_MybatisDaoXml("templates/MybatisDaoXml.btl");


    public String getResourceAsString() {
        try {
            return IOUtils.toString(SqlerResource.class.getClassLoader().getResourceAsStream(path));
        } catch (IOException e) {
            throw new SqlerException(e);
        }
    }

    SqlerResource(String path) {
        this.path = path;
    }

    private String path;


}
