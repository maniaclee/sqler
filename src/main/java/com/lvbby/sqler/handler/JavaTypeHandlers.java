package com.lvbby.sqler.handler;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.lvbby.codebot.chain.ContextHandler;

import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by peng on 16/7/27.
 * Convert factory type to java type
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
        putAll("boolean", Lists.newArrayList(Types.BIT));
    }

    private static HashMap<String, String> map = new HashMap<String, String>() {{
        put("long", "Long");
        put("int", "Integer");
        put("float", "Float");
        put("boolean", "Boolean");
    }};


    /***
     * convert factory type 2 java type @see Types
     */
    public static ContextHandler necessary = Handlers.ofField(field -> standardType.keySet().stream().
            filter(s -> standardType.get(s).contains(field.getDbType())).
            forEach(field::setAppType));

    public static ContextHandler basic = Handlers.ofField(f -> f.setAppType(calDbTypeByName(f.getDbTypeName())));

    /**
     * to boxing type like int -> Integer
     */
    public static ContextHandler boxingType = Handlers.ofField(f -> {
        if (map.containsKey(f.getAppType()))
            f.setAppType(map.get(f.getAppType()));
    });


    private static void putAll(String k, List<Integer> list) {
        standardType.putAll(k, list.stream().map(s -> String.valueOf(s)).collect(Collectors.toList()));
    }

    private static String calDbTypeByName(String type) {
        type = type.toLowerCase();
        if (type.equals("bit"))
            return "boolean";
        if (type.contains("bigint"))
            return "long";
        if (type.contains("int"))
            return "int";
        if (type.contains("char") || type.contains("text"))
            return "String";
        if (type.contains("float"))
            return "float";
        if (type.contains("date") || type.contains("time"))
            return "Date";
        if (type.contains("decimal"))
            return "BigDecimal";
        if (type.contains("double"))
            return "double";
        throw new IllegalArgumentException("unknown type : " + type);
    }

}
