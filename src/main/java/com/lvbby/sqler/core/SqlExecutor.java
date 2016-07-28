package com.lvbby.sqler.core;

import org.apache.commons.collections.CollectionUtils;

import java.util.List;

import static com.lvbby.sqler.util.LeeUtil.doCheck;

/**
 * Created by peng on 16/7/28.
 */
public class SqlExecutor implements Checkable {
    private TableFactory tableFactory;
    private List<TableHandler> tableHandlers;
    private Config config;

    @Override
    public void check() throws IllegalArgumentException {
        doCheck(config != null, "config is null");
        doCheck(tableFactory != null, "tableFactory is null");
        config.check();
    }

    public void run() {
        check();
        List<TableInfo> tables = tableFactory.getTables();
        if (CollectionUtils.isEmpty(tables)) {
            System.out.println("no table found");
            return;
        }
        if (CollectionUtils.isNotEmpty(tableHandlers)) {
            for (TableInfo table : tables)
                for (TableHandler tableHandler : tableHandlers) {
                    Context context = new Context();
                    context.setTableInfo(table);
                    context.setConfig(config);
                    tableHandler.handle(context);
                    //clear the context
                    context.clear();
                }
        }
    }

    @Deprecated
    private void exec(TableHandler tableHandler, Context context) {
        tableHandler.handle(context);
        if (tableHandler instanceof HierarchyTableHandler) {
            HierarchyTableHandler h = (HierarchyTableHandler) tableHandler;
            if (CollectionUtils.isNotEmpty(h.getChildren()))
                h.getChildren().stream().
                        filter(o -> o instanceof TableHandler)
                        .forEach(o -> exec(tableHandler, context));
        }

    }

    public Config getConfig() {
        return config;
    }

    public SqlExecutor setConfig(Config config) {
        this.config = config;
        return this;
    }

    public TableFactory getTableFactory() {
        return tableFactory;
    }

    public SqlExecutor setTableFactory(TableFactory tableFactory) {
        this.tableFactory = tableFactory;
        return this;
    }

    public List<TableHandler> getTableHandlers() {
        return tableHandlers;
    }

    public SqlExecutor setTableHandlers(List<TableHandler> tableHandlers) {
        this.tableHandlers = tableHandlers;
        return this;
    }


}
