package com.lvbby.sqler.handler;

import com.lvbby.codebot.ContextHandler;
import com.lvbby.sqler.core.*;
import com.lvbby.sqler.core.impl.ResultMapConst;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import static com.lvbby.sqler.util.LeeUtil.doCheck;

/**
 * Created by peng on 16/7/28.
 */
public class OutputHandler implements ContextHandler<Context> {
    private File destDir;
    private ContextConverter<String> fileNameConverter;

    public static OutputHandler create() {
        return new OutputHandler();
    }

    public OutputHandler setDestDir(File destDir) {
        this.destDir = destDir;
        return this;
    }

    public OutputHandler setFileNameConverter(ContextConverter<String> fileNameConverter) {
        this.fileNameConverter = fileNameConverter;
        return this;
    }

    @Override
    public void handle(Context context) {
        doCheck(destDir != null && (destDir.isDirectory() || !destDir.exists() && destDir.mkdirs()), "invalid dest directory");
        doCheck(fileNameConverter != null, "fileNameConverter is null");
        Object o = context.queryResult(ResultMapConst.OUTPUT);
        if (o == null)
            return;
        String re = o.toString();
        File file = new File(destDir, fileNameConverter.handle(context));
        try {
            PrintWriter printWriter = new PrintWriter(file);
            printWriter.print(re);
            printWriter.flush();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
