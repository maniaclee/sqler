package com.lvbby.sqler.core;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * Created by peng on 16/7/27.
 */
public class Config implements Checkable, Serializable {

    private static final long serialVersionUID = -6154839171721207745L;
    String author;
    String pack;
    String rootPath;

    @Override
    public void check() throws IllegalArgumentException {
        author = StringUtils.trimToEmpty(author);
        pack = StringUtils.trimToEmpty(pack);
    }


    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPack() {
        return pack;
    }

    public void setPack(String pack) {
        this.pack = pack;
    }

    public String getRootPath() {
        return rootPath;
    }

    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }


}
