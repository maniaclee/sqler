package com.lvbby.sqler.render;

/**
 * Created by lipeng on 16/7/28.
 */
public interface TemplateEngine {
    void bind(String key, Object obj);

    String render();
}
