package com.lvbby.sqler.factory;

import com.lvbby.sqler.core.Config;
import org.apache.commons.lang3.StringUtils;

import static com.lvbby.sqler.util.LeeUtil.doCheck;

/**
 * Created by peng on 16/7/27.
 */
public class DbConnectorConfig extends Config {
    private static final long serialVersionUID = -4544686755834779084L;
    private String jdbcUrl;
    private String user;
    private String password;

    @Override
    public void check() throws IllegalArgumentException {
        super.check();
        doCheck(StringUtils.isNoneBlank(jdbcUrl), "factory is empty");
        doCheck(StringUtils.isNoneBlank(user), "user is empty");
        if (StringUtils.isBlank(password))
            password = "";
    }

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
