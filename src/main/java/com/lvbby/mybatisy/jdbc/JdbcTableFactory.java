package com.lvbby.mybatisy.jdbc;

import com.google.common.collect.Lists;
import com.lvbby.mybatisy.core.SqlExecutor;
import com.lvbby.mybatisy.core.TableFactory;
import com.lvbby.mybatisy.core.TableField;
import com.lvbby.mybatisy.core.TableInfo;
import com.lvbby.mybatisy.handler.PrintHandler;
import org.junit.Test;

import java.sql.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by peng on 16/7/27.
 */
public class JdbcTableFactory implements TableFactory {
    private DbConnectorConfig dbConnectorConfig;
    Connection conn = null;


//    public JdbcTableFactory(DbConnectorConfig dbConnectorConfig) {
//        this.dbConnectorConfig = dbConnectorConfig;
//    }

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

    @Test
    public void init() throws SQLException {
        conn = getConnection();
        SqlExecutor sqlExecutor = new SqlExecutor(this, Lists.newArrayList(
//                TypeHandler.ClassName2PrimiveHandler,
//                TypeHandler.FullClassName2ClassNameHandler,
                JdbcTypeHandler.instance,
                TypeHandler.primitive2BoxingType,
                new PrintHandler()));
        sqlExecutor.run();
//        try {
//            Class<?> aClass = Class.forName("java.lang.Integer");
//            System.out.println(aClass.isPrimitive());
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }

    }


    public Connection getConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/user";
            String user = "root";
            String pass = "";
            conn = DriverManager.getConnection(url, user, pass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    private List<String> parseTables() throws SQLException {
        DatabaseMetaData md = conn.getMetaData();
        ResultSet rs = md.getTables(null, null, "%", null);
        List<String> re = Lists.newArrayList();
        while (rs.next()) re.add(rs.getString(3));
        return re;
    }

    private List<TableField> getFields2(TableInfo tableInfo) {
        List<TableField> re = Lists.newArrayList();
        String sql = String.format("select * from %s limit 1", tableInfo.getTableName());
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

                    f.setName(columnName);
                    f.setDbType(columnTypeName);
                    f.setType(columnClassName);
                    re.add(f);
                }
            }
        } catch (SQLException e) {
            System.out.println("数据库连接失败:" + e.getMessage());
        }
        return re;
    }

    private List<TableField> getFields(TableInfo tableInfo) {
        List<TableField> re = Lists.newArrayList();
        try {
            DatabaseMetaData meta = conn.getMetaData();
            ResultSet rs = meta.getColumns(null, null, tableInfo.getTableName(), "%");
            while (rs.next()) {
                TableField f = new TableField();
                f.setName(rs.getString(4));
                f.setDbType(rs.getString(6));
                f.setType(rs.getString(5));
                re.add(f);
            }
        } catch (SQLException e) {
            System.out.println("数据库连接失败:" + e.getMessage());
        }
        return re;
    }


}
