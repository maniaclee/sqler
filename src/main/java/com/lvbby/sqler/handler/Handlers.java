package com.lvbby.sqler.handler;

import com.google.common.base.CaseFormat;
import com.lvbby.sqler.core.Context;
import com.lvbby.codebot.chain.ContextHandler;
import com.lvbby.sqler.core.TableField;
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

    public static final ContextHandler print = s -> System.out.println(ReflectionToStringBuilder.toString(s));

    public static final ContextHandler fieldCase = ofField(s -> s.setName(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, s.getNameInDb())));
    public static final ContextHandler tableCase = ofTable(s -> s.setName(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, s.getNameInDb())));

    public static ContextHandler<Context> ofField(SimpleFieldHandler simpleFieldHandler) {

        return context -> context.getTableInfo().getFields().stream().forEach(f -> simpleFieldHandler.handle(f));
    }

    public static ContextHandler<Context> ofTable(SimpleTableHandler h) {
        return context -> h.handle(context.getTableInfo());
    }

}
