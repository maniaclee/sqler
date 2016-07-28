package com.lvbby.sqler.handler;

import com.google.common.base.CaseFormat;
import com.lvbby.sqler.core.TableField;
import com.lvbby.sqler.core.TableHandler;
import com.lvbby.sqler.core.TableInfo;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

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

    public static final TableHandler print = s -> System.out.println(ReflectionToStringBuilder.toString(s));

    public static final TableHandler fieldCase = ofField(s -> s.setName(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, s.getName())));
    public static final TableHandler tableCase = ofTable(s -> s.setName(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, s.getName())));

    public static TableHandler ofField(SimpleFieldHandler simpleFieldHandler) {
        return context -> context.getTableInfo().getFields().stream().forEach(f -> simpleFieldHandler.handle(f));
    }

    public static TableHandler ofTable(SimpleTableHandler h) {
        return context -> h.handle(context.getTableInfo());
    }

}
