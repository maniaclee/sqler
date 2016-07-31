package com.lvbby.sqler.factory;

import com.google.common.collect.Lists;
import com.lvbby.sqler.core.TableFactory;
import com.lvbby.sqler.core.TableField;
import com.lvbby.sqler.core.TableInfo;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by peng on 16/7/28.
 */
public class DDLTableFactory implements TableFactory {
    static Pattern p_table = Pattern.compile("\\s*CREATE\\s+TABLE\\s+`(?<table>\\S+)`\\s*\\((?<body>[^;]+);", Pattern.CASE_INSENSITIVE);
    static Pattern p_sentence = Pattern.compile("\\s*`(?<field>\\S+)`\\s+(?<type>[^\\(\\)\\s]+)[^,]+,", Pattern.CASE_INSENSITIVE);
    static Pattern p_pk = Pattern.compile("PRIMARY\\s+KEY\\s*\\(\\s*`(?<pk>\\S+)`\\s*\\)", Pattern.CASE_INSENSITIVE);
    private String ddl;

    public static DDLTableFactory create(String ddl) {
        DDLTableFactory re = new DDLTableFactory();
        re.ddl = ddl;
        return re;
    }

    @Override
    public List<TableInfo> getTables() {
        List<TableInfo> re = Lists.newArrayList();
        for (Matcher matcher = p_table.matcher(ddl); matcher.find(); ) {
            String table = matcher.group("table");
            TableInfo tableInfo = TableInfo.instance(table);
            String body = matcher.group("body");
            for (Matcher m = p_sentence.matcher(body); m.find(); ) {
                TableField tableField = new TableField();
                tableField.setNameInDb(m.group("field"));
                tableField.setDbTypeName(m.group("type"));
                tableInfo.getFields().add(tableField);
            }
            /** find pk */
            Matcher pkMatcher = p_pk.matcher(body);
            String pk = pkMatcher.find() ? pkMatcher.group("pk") : "";
            tableInfo.buildPrimaryKeyField(pk);

            re.add(tableInfo);
        }
        return re;
    }

}
