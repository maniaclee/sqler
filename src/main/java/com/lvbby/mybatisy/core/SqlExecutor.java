package com.lvbby.mybatisy.core;

import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * Created by peng on 16/7/28.
 */
public class SqlExecutor {
    private TableFactory tableFactory;
    private List<TableHandler> tableHandlers;

    public SqlExecutor(TableFactory tableFactory, List<TableHandler> tableHandlers) {
        this.tableFactory = tableFactory;
        this.tableHandlers = tableHandlers;
    }

    public void run() {
        List<TableInfo> tables = tableFactory.getTables();
        if (CollectionUtils.isEmpty(tables)) {
            System.out.println("no table found");
            return;
        }
        if (CollectionUtils.isNotEmpty(tableHandlers)) {
            for (TableInfo table : tables)
                for (TableHandler tableHandler : tableHandlers)
                    tableHandler.handle(table);
        }
    }

    public TableFactory getTableFactory() {
        return tableFactory;
    }

    public void setTableFactory(TableFactory tableFactory) {
        this.tableFactory = tableFactory;
    }

    public List<TableHandler> getTableHandlers() {
        return tableHandlers;
    }

    public void setTableHandlers(List<TableHandler> tableHandlers) {
        this.tableHandlers = tableHandlers;
    }
}
