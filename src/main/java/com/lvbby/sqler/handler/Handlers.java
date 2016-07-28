package com.lvbby.sqler.handler;

import com.google.common.base.CaseFormat;
import com.lvbby.sqler.core.Context;
import com.lvbby.sqler.core.TableField;
import com.lvbby.sqler.core.TableHandler;
import com.lvbby.sqler.core.TableInfo;

import java.util.Map;

/**
 * Created by lipeng on 16/7/28.
 */
public class Handlers {
    public interface SimpleTableHandler {
        void handle(TableInfo tableInfo);
    }

    public interface SimpleFieldHandler {
        void handle(TableField f);
    }

    public static final TableHandler<Context> print = s -> System.out.println(s);

    public static final TableHandler<Context> fieldCase = ofField(s -> s.setName(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, s.getName())));

    public static TableHandler ofField(SimpleFieldHandler simpleFieldHandler) {
        return context -> context.getTableInfo().getFields().stream().forEach(f -> simpleFieldHandler.handle(f));
    }

    public static TableHandler ofTable(SimpleTableHandler h) {
        return context -> h.handle(context.getTableInfo());
    }

    public static TableHandler ofMap(Map<String, String> map) {
        return ofField(f -> {
            if (map.containsKey(f.getAppType())) f.setAppType(map.get(f.getAppType()));
        });
    }

}
