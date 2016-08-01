package com.lvbby.sqler.pipeline;

import com.lvbby.codebot.chain.PipeLine;

/**
 * Created by peng on 16/7/30.
 */
public class PipeLines {
    public static final PipeLine print = (o, context) -> System.out.println(o);
}
