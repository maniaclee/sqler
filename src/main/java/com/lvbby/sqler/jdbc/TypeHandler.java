package com.lvbby.sqler.jdbc;

import com.lvbby.sqler.core.TableField;
import com.lvbby.sqler.core.TableHandler;
import com.lvbby.sqler.core.TableInfo;
import org.apache.commons.collections.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by peng on 16/7/27.
 */
public class TypeHandler implements TableHandler {
    Map<String, String> map;


    public TypeHandler(Map<String, String> map) {
        this.map = map;
    }

    @Deprecated
    public static TypeHandler FullClassName2ClassNameHandler = new TypeHandler(new HashMap<String, String>() {{
        put("java.lang.Long", "Long");
        put("java.lang.Integer", "Integer");
        put("java.lang.String", "String");
        put("java.sql.Timestamp", "Date");
        put("java.math.BigDecimal", "BigDecimal");
        put("java.lang.Float", "Float");
    }});
    @Deprecated
    public static TypeHandler ClassName2PrimiveHandler = new TypeHandler(new HashMap<String, String>() {{
        put("java.lang.Long", "long");
        put("java.lang.Integer", "int");
        put("java.lang.Float", "float");
        put("Long", "long");
        put("Integer", "int");
        put("Float", "float");
    }});
    public static TypeHandler primitive2BoxingType = new TypeHandler(new HashMap<String, String>() {{
        put("long", "Long");
        put("int", "Integer");
        put("float", "Float");
    }});

    @Override
    public void handle(TableInfo tableInfo) {
        List<TableField> fields = tableInfo.getFields();
        if (CollectionUtils.isNotEmpty(fields))
            for (TableField field : fields) {
                String s = map.get(field.getType());
                if (s != null)
                    field.setType(s);
            }
    }
}
