package com.lvbby.sqler.handler;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.lvbby.sqler.core.Context;
import com.lvbby.sqler.core.TableField;
import com.lvbby.sqler.core.TableHandler;
import com.lvbby.sqler.core.TableInfo;
import org.apache.commons.collections.CollectionUtils;

import java.sql.Types;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by peng on 16/7/27.
 * Convert jdbc type to java type
 */
public class JavaTypeHandler implements TableHandler<Context> {
    static ArrayListMultimap<String, String> standardType = ArrayListMultimap.create();
    public static JavaTypeHandler instance = new JavaTypeHandler();

    static {
        putAll("int", Lists.newArrayList(Types.SMALLINT, Types.INTEGER));
        putAll("long", Lists.newArrayList(Types.BIGINT));
        putAll("Date", Lists.newArrayList(Types.DATE, Types.TIME, Types.TIME_WITH_TIMEZONE, Types.TIMESTAMP, Types.TIMESTAMP_WITH_TIMEZONE));
        putAll("float", Lists.newArrayList(Types.FLOAT, Types.REAL));
        putAll("BigDecimal", Lists.newArrayList(Types.DOUBLE, Types.DECIMAL));
        putAll("String", Lists.newArrayList(Types.VARCHAR, Types.LONGNVARCHAR));
    }

    private static void putAll(String k, List<Integer> list) {
        standardType.putAll(k, list.stream().map(s -> String.valueOf(s)).collect(Collectors.toList()));
    }

    @Override
    public void handle(Context context) {
        TableInfo tableInfo = context.getTableInfo();
        List<TableField> fields = tableInfo.getFields();
        if (CollectionUtils.isNotEmpty(fields))
            for (TableField field : fields) {
                standardType.keySet().stream().
                        filter(s -> standardType.get(s).contains(field.getDbType())).
                        forEach(field::setAppType);
            }
    }

}
