package com.lvbby.sqler.handler;

import com.lvbby.sqler.core.Context;
import com.lvbby.sqler.core.TableHandler;

/**
 * Created by peng on 16/7/28.
 */
public class PrintHandler implements TableHandler<Context> {
    @Override
    public void handle(Context context) {
        System.out.println(context.getTableInfo());
    }
}
