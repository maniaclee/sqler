package com.lvbby.sqler.handler;

import com.lvbby.sqler.core.TableHandler;
import com.lvbby.sqler.core.TableInfo;

/**
 * Created by peng on 16/7/28.
 */
public class PrintHandler implements TableHandler {
    @Override
    public void handle(TableInfo tableInfo) {
        System.out.println(tableInfo);
    }
}
