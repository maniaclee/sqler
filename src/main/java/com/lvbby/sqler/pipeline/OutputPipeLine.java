package com.lvbby.sqler.pipeline;

import com.lvbby.sqler.core.Context;
import com.lvbby.sqler.core.ContextConverter;
import com.lvbby.sqler.core.PipeLine;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.PrintWriter;

import static com.lvbby.sqler.util.LeeUtil.doCheck;

/**
 * Created by peng on 16/7/28.
 */
public class OutputPipeLine implements PipeLine {
    private File destDir;
    private ContextConverter<String> fileNameConverter;
    private String suffix;

    public static OutputPipeLine create() {
        return new OutputPipeLine();
    }

    public OutputPipeLine setDestDir(File destDir) {
        this.destDir = destDir;
        return this;
    }

    public OutputPipeLine setFileNameConverter(ContextConverter<String> fileNameConverter) {
        this.fileNameConverter = fileNameConverter;
        return this;
    }

    public OutputPipeLine setSuffix(String suffix) {
        this.suffix = suffix;
        return this;
    }

    @Override
    public void handle(Object o, Context context) {
        doCheck(destDir != null && (destDir.isDirectory() || !destDir.exists() && destDir.mkdirs()), "invalid dest directory");
        doCheck(fileNameConverter != null, "fileNameConverter is null");
        if (o == null)
            return;
        String re = o.toString();
        String fileName = fileNameConverter.handle(context);
        if (StringUtils.isNotBlank(suffix))
            fileName = fileName + "." + suffix.trim();
        File file = new File(destDir, fileName);
        try {
            PrintWriter printWriter = new PrintWriter(file);
            printWriter.print(re);
            printWriter.flush();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
