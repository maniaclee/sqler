package com.lvbby.mybatisy.handler;

import com.lvbby.mybatisy.core.TableHandler;
import com.lvbby.mybatisy.core.TableInfo;

/**
 * Created by peng on 16/7/28.
 */
public class PrintHandler implements TableHandler {
    @Override
    public void handle(TableInfo tableInfo) {
        System.out.println(tableInfo);
    }
}
