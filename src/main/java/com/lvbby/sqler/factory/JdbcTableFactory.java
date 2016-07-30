package com.lvbby.sqler.factory;

import com.google.common.collect.Lists;
import com.lvbby.sqler.core.TableFactory;
import com.lvbby.sqler.core.TableField;
import com.lvbby.sqler.core.TableInfo;

import java.sql.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by peng on 16/7/27.
 */
public class JdbcTableFactory implements TableFactory {
    private DbConnectorConfig dbConnectorConfig;
    Connection conn = null;

    private JdbcTableFactory() {
    }

    public static JdbcTableFactory create(DbConnectorConfig dbConnectorConfig) {
        JdbcTableFactory re = new JdbcTableFactory();
        re.dbConnectorConfig = dbConnectorConfig;
        re.init();
        return re;
    }

    public List<TableInfo> getTables() {
        try {
            return parseTables().stream().map(s -> {
                TableInfo instance = TableInfo.instance(s);
                instance.setFields(getFields(instance));
                return instance;
            }).collect(Collectors.toList());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void init() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(
                    dbConnectorConfig.getJdbcUrl(),
                    dbConnectorConfig.getUser(),
                    dbConnectorConfig.getPassword());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private List<String> parseTables() throws SQLException {
        DatabaseMetaData md = conn.getMetaData();
        ResultSet rs = md.getTables(null, null, "%", null);
        List<String> re = Lists.newArrayList();
        while (rs.next()) re.add(rs.getString(3));
        return re;
    }


    private List<TableField> getFields(TableInfo tableInfo) {
        List<TableField> re = Lists.newArrayList();
        try {
            DatabaseMetaData meta = conn.getMetaData();
            ResultSet rs = meta.getColumns(null, null, tableInfo.getName(), "%");
            while (rs.next()) {
                TableField f = new TableField();
                f.setNameInDb(rs.getString(4));
                f.setDbType(rs.getString(5));
                f.setDbTypeName(rs.getString(6));
                f.setComment(rs.getString(12));
                re.add(f);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return re;
    }

    @Deprecated
    private List<TableField> getFields2(TableInfo tableInfo) {
        List<TableField> re = Lists.newArrayList();
        String sql = String.format("select * from %s limit 1", tableInfo.getName());
        PreparedStatement stmt;
        try {
            stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery(sql);
            ResultSetMetaData data = rs.getMetaData();
            while (rs.next()) {
                for (int i = 1; i <= data.getColumnCount(); i++) {
                    TableField f = new TableField();
                    String columnName = data.getColumnName(i);
                    String columnTypeName = data.getColumnTypeName(i);
                    String columnClassName = data.getColumnClassName(i);

                    f.setNameInDb(columnName);
                    f.setDbType(columnTypeName);
                    f.setAppType(columnClassName);
                    re.add(f);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return re;
    }


}
