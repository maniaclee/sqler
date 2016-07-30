package com.lvbby.sqler.core;

import org.apache.commons.collections.CollectionUtils;

import java.util.List;

import static com.lvbby.sqler.util.LeeUtil.doCheck;

/**
 * Created by peng on 16/7/28.
 */
public class SqlExecutor implements Checkable {
    private TableFactory tableFactory;
    private List<ContextHandler> contextHandlers;
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
        if (CollectionUtils.isNotEmpty(contextHandlers)) {
            for (TableInfo table : tables)
                for (ContextHandler contextHandler : contextHandlers) {
                    Context context = new Context();
                    context.setTableInfo(table);
                    context.setConfig(config);
                    contextHandler.handle(context);
                    //clear the context
                    context.clear();
                }
        }
    }

    @Deprecated
    private void exec(ContextHandler contextHandler, Context context) {
        contextHandler.handle(context);
        if (contextHandler instanceof HierarchyContextHandler) {
            HierarchyContextHandler h = (HierarchyContextHandler) contextHandler;
            if (CollectionUtils.isNotEmpty(h.getChildren()))
                h.getChildren().stream().
                        filter(o -> o instanceof ContextHandler)
                        .forEach(o -> exec(contextHandler, context));
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

    public List<ContextHandler> getContextHandlers() {
        return contextHandlers;
    }

    public SqlExecutor setContextHandlers(List<ContextHandler> contextHandlers) {
        this.contextHandlers = contextHandlers;
        return this;
    }


}
