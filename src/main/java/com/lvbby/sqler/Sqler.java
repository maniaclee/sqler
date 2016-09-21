package com.lvbby.sqler;

import com.google.common.collect.Lists;
import com.lvbby.codebot.chain.ContextHandler;
import com.lvbby.codema.render.TemplateEngineFactory;
import com.lvbby.sqler.core.Config;
import com.lvbby.sqler.core.Context;
import com.lvbby.sqler.core.SqlExecutor;
import com.lvbby.sqler.factory.DbConnectorConfig;
import com.lvbby.sqler.factory.JdbcTableFactory;
import com.lvbby.sqler.handler.Handlers;
import com.lvbby.sqler.handler.JavaTypeHandlers;
import com.lvbby.sqler.handler.TemplateEngineHandler;
import com.lvbby.sqler.pipeline.OutputPipeLine;
import com.lvbby.sqler.render.SqlerResource;
import com.lvbby.sqler.render.beetl.LeeFn;

import java.io.IOException;
import java.util.List;

/**
 * Created by lipeng on 16/7/28.
 */
public class Sqler {

    public static List<ContextHandler<Context>> getTemplateEngineHandlers(Config dbConnectorConfig) {
        String tm_javaBean = SqlerResource.Beetl_JavaBean.getResourceAsString();
        String tm_dao = SqlerResource.Beetl_MybatisDao.getResourceAsString();
        String tm_repository = SqlerResource.Beetl_Repository.getResourceAsString();
        String tm_xml = SqlerResource.Beetl_MybatisDaoXml.getResourceAsString();
        return Lists.newArrayList(TemplateEngineHandler.
                        of(TemplateEngineFactory.create(tm_javaBean))
                        .bind("className", context -> LeeFn.getEntityClassName(context.getTableInfo()))
                        .bind("classPack", context -> LeeFn.getEntityPackage(dbConnectorConfig.getPack()))
                        .addPipeLine(OutputPipeLine.create()
                                .setDestDir(dbConnectorConfig.calDirectory(LeeFn.getEntityPackage(dbConnectorConfig.getPack())))
                                .setFileNameConverter(context -> LeeFn.getEntityClassName(context.getTableInfo()))
                                .setSuffix("java")),
                TemplateEngineHandler.
                        of(TemplateEngineFactory.create(tm_javaBean))
                        .bind("className", context -> LeeFn.getDtoClassName(context.getTableInfo()))
                        .bind("classPack", context -> LeeFn.getDtoPackage(dbConnectorConfig.getPack()))
                        .addPipeLine(OutputPipeLine.create()
                                .setDestDir(dbConnectorConfig.calDirectory(LeeFn.getDtoPackage(dbConnectorConfig.getPack())))
                                .setFileNameConverter(context -> LeeFn.getDtoClassName(context.getTableInfo()))
                                .setSuffix("java")),
                TemplateEngineHandler.
                        of(TemplateEngineFactory.create(tm_dao))
                        .addPipeLine(OutputPipeLine.create()
                                .setDestDir(dbConnectorConfig.calDirectory(LeeFn.getDaoPackage(dbConnectorConfig.getPack())))
                                .setFileNameConverter(context -> LeeFn.getDaoClassName(context.getTableInfo()))
                                .setSuffix("java")),
                TemplateEngineHandler.
                        of(TemplateEngineFactory.create(tm_repository))
                        .addPipeLine(OutputPipeLine.create()
                                .setDestDir(dbConnectorConfig.calDirectory(LeeFn.getRepositoryPackage(dbConnectorConfig.getPack())))
                                .setFileNameConverter(context -> LeeFn.getRepositoryClassName(context.getTableInfo()))
                                .setSuffix("java")),
                TemplateEngineHandler.
                        of(TemplateEngineFactory.create(tm_xml))
                        .addPipeLine(OutputPipeLine.create()
                                .setDestDir(dbConnectorConfig.calDirectory(LeeFn.getMapperXmlPackage(dbConnectorConfig.getPack())))
                                .setFileNameConverter(context -> LeeFn.getDaoClassName(context.getTableInfo()))
                                .setSuffix("xml"))
        );
    }

    public static SqlExecutor createSqlExecutorFromDb(DbConnectorConfig config) throws IOException {
        config.check();
        return new SqlExecutor()
                .setConfig(config)
                .setTableFactory(JdbcTableFactory.create(config))
                // .setTableFactory(DDLTableFactory.create(IOUtils.toString(TestCase.class.getClassLoader().getResourceAsStream("testddl.sql"))))
                .addContextHandlers(Lists.newArrayList(
                        JavaTypeHandlers.basic,
                        // JavaTypeHandlers.boxingType,
                        Handlers.tableCase,
                        Handlers.fieldCase
                ))
                .addContextHandlers(getTemplateEngineHandlers(config));
    }

}
