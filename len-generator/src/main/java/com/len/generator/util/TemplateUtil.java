package com.len.generator.util;

import com.len.generator.template.FreemarkerPathTemplateStrategy;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class TemplateUtil {

    public static String[] getTemplateContentBypath(String... path) {
        if (path == null || path.length == 0) {
            throw new RuntimeException("模板路径不能为空");
        }
        String[] templateContent = new String[path.length];
        ClassLoader classLoader = FreemarkerPathTemplateStrategy.class.getClassLoader();
        int i = 0;
        for (String pathItem : path) {
            InputStream resourceAsStream = classLoader.getResourceAsStream(pathItem);
            if (resourceAsStream == null) {
                throw new RuntimeException(String.format("模板%s, 未找到", pathItem));
            }
            try {
                templateContent[i++] = IOUtils.toString(resourceAsStream, StandardCharsets.UTF_8);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return templateContent;
    }
}
