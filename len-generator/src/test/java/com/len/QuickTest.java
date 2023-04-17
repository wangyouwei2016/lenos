package com.len;

import com.len.generator.metadata.source.JsonSource;
import com.len.generator.metadata.source.MetaDataSource;
import com.len.generator.template.FreemarkerStrTemplateStrategy;
import com.len.generator.template.TemplateRenderer;

import junit.framework.TestCase;

public class QuickTest extends TestCase {

    //json配置
    final String jsonDataStr="{\n" +
            "  \"strVar\": {\n" +
            "    \"name\": \"张三\"\n" +
            "  },\n" +
            "  \"objVar\": {\n" +
            "  },\n" +
            "  \"arrVar\": {\n" +
            "    \"items\": [\n" +
            "      {\n" +
            "        \"name\": \"张三\",\n" +
            "        \"age\": 15\n" +
            "      },\n" +
            "      {\n" +
            "        \"name\": \"李四\",\n" +
            "        \"age\": 18\n" +
            "      }\n" +
            "    ]\n" +
            "  }\n" +
            "}";

    //模板内容
    final String templateString =
            "Hello, ${name}! <#list items as item>${item.name} is ${item.age} years old. </#list>";


    public void testQuick(){
        TemplateRenderer renderer = new TemplateRenderer.Builder()
                //引擎
                .withTemplateStrategy(new FreemarkerStrTemplateStrategy(templateString))
                .build();

        String[] render = renderer.render(
                new MetaDataSource.Builder<String>()
                        .source(new JsonSource<>(jsonDataStr))
                        .build()
        );
        for (String out : render) {
            System.out.println(out);
        }
    }
}
