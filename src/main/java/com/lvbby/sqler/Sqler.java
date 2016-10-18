package com.lvbby.sqler;

import com.google.common.collect.Lists;
import com.lvbby.codebot.chain.ContextHandler;
import com.lvbby.codebot.chain.PipeLine;
import com.lvbby.codema.render.TemplateEngineFactory;
import com.lvbby.sqler.core.Config;
import com.lvbby.sqler.core.Context;
import com.lvbby.sqler.core.SqlExecutor;
import com.lvbby.sqler.core.TableFactory;
import com.lvbby.sqler.exceptions.SqlerException;
import com.lvbby.sqler.factory.DdlConfig;
import com.lvbby.sqler.factory.DdlTableFactory;
import com.lvbby.sqler.factory.JdbcConfig;
import com.lvbby.sqler.factory.JdbcTableFactory;
import com.lvbby.sqler.handler.Handlers;
import com.lvbby.sqler.handler.JavaTypeHandlers;
import com.lvbby.sqler.handler.TemplateEngineHandler;
import com.lvbby.sqler.pipeline.OutputPipeLine;
import com.lvbby.sqler.render.SqlerResource;
import com.lvbby.sqler.render.beetl.LeeFn;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * Created by lipeng on 16/7/28.
 */
public class Sqler {

    static String tm_javaBean = SqlerResource.Beetl_JavaBean.getResourceAsString();
    static String tm_dao = SqlerResource.Beetl_MybatisDao.getResourceAsString();
    static String tm_repository = SqlerResource.Beetl_Repository.getResourceAsString();
    static String tm_xml = SqlerResource.Beetl_MybatisDaoXml.getResourceAsString();

    public static List<ContextHandler<Context>> getTemplateEngineHandlers(Config dbConnectorConfig) {
        return Lists.newArrayList(TemplateEngineHandler.
                        of(TemplateEngineFactory.create(tm_javaBean))
                        .bind("className", context -> LeeFn.getEntityClassName(context.getTableInfo()))
                        .bind("classPack", context -> LeeFn.getEntityPackage(dbConnectorConfig.getPack()))
                        .toPipeLine().addPipeLine(OutputPipeLine.create()
                                .setDestDir(dbConnectorConfig.calDirectory(LeeFn.getEntityPackage(dbConnectorConfig.getPack())))
                                .setFileNameConverter(context -> LeeFn.getEntityClassName(context.getTableInfo()))
                                .setSuffix("java")),
                TemplateEngineHandler.
                        of(TemplateEngineFactory.create(tm_javaBean))
                        .bind("className", context -> LeeFn.getDtoClassName(context.getTableInfo()))
                        .bind("classPack", context -> LeeFn.getDtoPackage(dbConnectorConfig.getPack()))
                        .toPipeLine().addPipeLine(OutputPipeLine.create()
                                .setDestDir(dbConnectorConfig.calDirectory(LeeFn.getDtoPackage(dbConnectorConfig.getPack())))
                                .setFileNameConverter(context -> LeeFn.getDtoClassName(context.getTableInfo()))
                                .setSuffix("java")),
                TemplateEngineHandler.
                        of(TemplateEngineFactory.create(tm_dao))
                        .toPipeLine().addPipeLine(OutputPipeLine.create()
                                .setDestDir(dbConnectorConfig.calDirectory(LeeFn.getDaoPackage(dbConnectorConfig.getPack())))
                                .setFileNameConverter(context -> LeeFn.getDaoClassName(context.getTableInfo()))
                                .setSuffix("java")),
                TemplateEngineHandler.
                        of(TemplateEngineFactory.create(tm_repository))
                        .toPipeLine().addPipeLine(OutputPipeLine.create()
                                .setDestDir(dbConnectorConfig.calDirectory(LeeFn.getRepositoryPackage(dbConnectorConfig.getPack())))
                                .setFileNameConverter(context -> LeeFn.getRepositoryClassName(context.getTableInfo()))
                                .setSuffix("java")),
                TemplateEngineHandler.
                        of(TemplateEngineFactory.create(tm_xml))
                        .toPipeLine().addPipeLine(OutputPipeLine.create()
                                .setDestDir(dbConnectorConfig.calDirectory(LeeFn.getMapperXmlPackage(dbConnectorConfig.getPack())))
                                .setFileNameConverter(context -> LeeFn.getDaoClassName(context.getTableInfo()))
                                .setSuffix("xml"))
        );
    }

    public static List<ContextHandler<Context>> getTemplateEngineHandlersOnlyPrint(Config dbConnectorConfig) {
        List<ContextHandler<Context>> templateEngineHandlers = getTemplateEngineHandlers(dbConnectorConfig);
        templateEngineHandlers.forEach(contextContextHandler -> {
            PipeLine<String, Context> print = (t, context) -> System.out.println(t);
            if (contextContextHandler instanceof TemplateEngineHandler) {
                ((TemplateEngineHandler) contextContextHandler).toPipeLine().setPipeLines(Lists.newArrayList(print));
            }
        });
        return templateEngineHandlers;

    }

    public static SqlExecutor createSqlExecutorTemplate(Config config) throws Exception {
        return createSqlExecutorTemplate(config, createTableFactory(config));
    }

    public static SqlExecutor createSqlExecutorTemplate(Config config, TableFactory tableFactory) throws Exception {
        config.check();
        return new SqlExecutor()
                .setConfig(config)
                .setTableFactory(tableFactory)
                .addContextHandlers(Lists.newArrayList(
                        JavaTypeHandlers.basic,
                        // JavaTypeHandlers.boxingType,
                        Handlers.tableCase,
                        Handlers.fieldCase
                ));
    }

    public static SqlExecutor createDefaultSqlExecutor(Config config) throws Exception {
        return createSqlExecutorTemplate(config)
                .addContextHandlers(getTemplateEngineHandlers(config));
    }

    public static TableFactory createTableFactory(Config config) {
        if (config instanceof JdbcConfig)
            return JdbcTableFactory.create((JdbcConfig) config);
        if (config instanceof DdlConfig)
            return DdlTableFactory.create((DdlConfig) config);
        throw new SqlerException("unknown config type");
    }

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("usage: type[jdbc|ddl] configFile");
            return;
        }
        String type = StringUtils.trimToEmpty(args[1]);
        String file = StringUtils.trimToEmpty(args[2]);
        if (StringUtils.isBlank(file)) {
            System.out.println("config file is empty");
            return;
        }

    }

}
