package com.lvbby.sqler.render;

import java.io.OutputStream;
import java.util.Map;

/**
 * Created by lipeng on 16/7/28.
 */
public interface ITemplateEngine {
    String render(String key ,Object obj);

    String render(Map obj);

    void renderTo(OutputStream outputStream);
}
