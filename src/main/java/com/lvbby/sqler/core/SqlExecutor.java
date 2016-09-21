package com.lvbby.sqler.core;

import com.google.common.collect.Lists;
import com.lvbby.codebot.chain.ChainExecutor;
import com.lvbby.codebot.chain.ContextHandler;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

import static com.lvbby.sqler.util.LeeUtil.doCheck;

/**
 * Created by peng on 16/7/28.
 */
public class SqlExecutor implements Checkable {
    private TableFactory tableFactory;
    private List<ContextHandler<Context>> contextHandlers;
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
        ChainExecutor<Context> exec = ChainExecutor.create(contextHandlers);
        tables.stream().map(table -> {
            Context context = new Context();
            context.setTableInfo(table);
            context.setConfig(config);
            return context;
        }).forEach(context -> exec.exec(context));
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

    public List<ContextHandler<Context>> getContextHandlers() {
        return contextHandlers;
    }

    public SqlExecutor setContextHandlers(List<ContextHandler<Context>> contextHandlers) {
        this.contextHandlers = contextHandlers;
        return this;
    }

    public SqlExecutor addContextHandlers(List<ContextHandler<Context>> contextHandlers) {
        if (this.contextHandlers == null)
            this.contextHandlers = Lists.newLinkedList();
        this.contextHandlers.addAll(contextHandlers);
        return this;
    }

    public SqlExecutor addContextHandler(ContextHandler<Context> contextHandler) {
        if (this.contextHandlers == null)
            this.contextHandlers = Lists.newLinkedList();
        this.contextHandlers.add(contextHandler);
        return this;
    }


}
