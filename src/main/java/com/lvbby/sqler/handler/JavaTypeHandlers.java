package com.lvbby.sqler.handler;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.lvbby.sqler.core.TableHandler;

import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by peng on 16/7/27.
 * Convert jdbc type to java type
 */
public class JavaTypeHandlers {
    static ArrayListMultimap<String, String> standardType = ArrayListMultimap.create();

    static {
        putAll("int", Lists.newArrayList(Types.SMALLINT, Types.INTEGER));
        putAll("long", Lists.newArrayList(Types.BIGINT));
        putAll("Date", Lists.newArrayList(Types.DATE, Types.TIME, Types.TIME_WITH_TIMEZONE, Types.TIMESTAMP, Types.TIMESTAMP_WITH_TIMEZONE));
        putAll("float", Lists.newArrayList(Types.FLOAT, Types.REAL));
        putAll("BigDecimal", Lists.newArrayList(Types.DOUBLE, Types.DECIMAL));
        putAll("String", Lists.newArrayList(Types.VARCHAR, Types.LONGNVARCHAR));
    }

    private static HashMap<String, String> map = new HashMap<String, String>() {{
        put("long", "Long");
        put("int", "Integer");
        put("float", "Float");
    }};


    public static TableHandler instance = Handlers.ofField(field -> standardType.keySet().stream().
            filter(s -> standardType.get(s).contains(field.getDbType())).
            forEach(field::setAppType));

    public static TableHandler javaPrimitive2BoxingType = Handlers.ofField(f -> {
        if (map.containsKey(f.getAppType()))
            f.setAppType(map.get(f.getAppType()));
    });


    private static void putAll(String k, List<Integer> list) {
        standardType.putAll(k, list.stream().map(s -> String.valueOf(s)).collect(Collectors.toList()));
    }

}
