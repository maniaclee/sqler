package com.lvbby.sqler.jdbc;

import com.lvbby.sqler.core.Config;

/**
 * Created by peng on 16/7/27.
 */
public class DbConnectorConfig extends Config {
    private String jdbcUrl;
    private String user;
    private String password;

    public String getJdbcUrl() {
        return jdbcUrl;
    }

    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
