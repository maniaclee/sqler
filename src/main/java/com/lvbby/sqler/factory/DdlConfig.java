package com.lvbby.sqler.factory;

import com.lvbby.sqler.core.Config;
import org.apache.commons.lang3.StringUtils;

import static com.lvbby.sqler.util.LeeUtil.doCheck;

/**
 * Created by peng on 16/7/27.
 */
public class DdlConfig extends Config {
    private static final long serialVersionUID = -4544686755834779084L;
    private String ddl;

    @Override
    public void check() throws IllegalArgumentException {
        super.check();
        ddl = StringUtils.trimToNull(ddl);
        doCheck(StringUtils.isNoneBlank(ddl), "ddl is empty");
    }

    public String getDdl() {
        return ddl;
    }

    public void setDdl(String ddl) {
        this.ddl = ddl;
    }
}
