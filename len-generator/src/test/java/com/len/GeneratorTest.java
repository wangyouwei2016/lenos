package com.len;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.len.generator.metadata.MetaData;
import com.len.generator.metadata.StandardMetaData;
import com.len.generator.metadata.source.JsonPathSource;
import com.len.generator.metadata.source.JsonSource;
import com.len.generator.metadata.source.MetaDataSource;
import com.len.generator.template.FreemarkerPathTemplateStrategy;
import com.len.generator.template.FreemarkerStrTemplateStrategy;
import com.len.generator.template.TemplateRenderer;

import junit.framework.TestCase;

public class GeneratorTest extends TestCase {



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

    /**
     * 模板内容
     */
    final String templateString =
        "Hello, ${name}! <#list items as item>${item.name} is ${item.age} years old. </#list>";
    /**
     * 入参(变量)
     */
    List<Map<String, Object>> list;

    {
        // 创建循环块的数据模型
        Map<String, Object> item1 = new HashMap<>();
        item1.put("name", "李四");
        item1.put("age", 30);
        Map<String, Object> item2 = new HashMap<>();
        item2.put("name", "王五");
        item2.put("age", 35);
        list = Arrays.asList(item1, item2);

    }

    /**
     * 不同模板来源
     */
    public void testGeneratorByDiffTemplateSource() {
        MetaData metaData = new StandardMetaData();
        metaData.putStrVar("name", "张三");
        metaData.putArrVar("items", list);

        // 文本
        TemplateRenderer renderer = new TemplateRenderer.Builder()
                .withTemplateStrategy(new FreemarkerStrTemplateStrategy(templateString))
                .build();
        String[] render = renderer.render(metaData);
        for (String renderItem : render) {
            System.out.println(renderItem);
        }



        // 路径
        String path1 = "templateTest1.ftl";
        String path2 = "templateTest2.ftl";
        renderer =
            new TemplateRenderer.Builder()
                    .withTemplateStrategy(new FreemarkerPathTemplateStrategy(path1,path2))
                    .build();
        render = renderer.render(metaData);
        for (String renderItem : render) {
            System.out.println(renderItem);
        }

    }

    /**
     * 不同参数来源
     */
    public void testGeneratorByDiffParam() {

        //入参 json path
        String path = "data.json";
        TemplateRenderer renderer = new TemplateRenderer.Builder()
                .withTemplateStrategy(new FreemarkerStrTemplateStrategy(templateString))
                .build();

        String[] render = renderer.render(
                new MetaDataSource.Builder<String>()
                        .source(new JsonPathSource(path))
                        .build()
        );
        for (String renderItem : render) {
            System.out.println(renderItem);
        }


        //入参 json  content
         render = renderer.render(
                new MetaDataSource.Builder<String>()
                        .source(new JsonSource<>(jsonDataStr))
                        .build()
        );
        for (String renderItem : render) {
            System.out.println(renderItem);
        }

    }


    public void testGeneratorByJson() {
        //入参 json path
        String path = "data.json";
        TemplateRenderer renderer = new TemplateRenderer.Builder()
                .withTemplateStrategy(new FreemarkerStrTemplateStrategy())
                .build();

        String[] render = renderer.render(
                new MetaDataSource.Builder<String>()
                        .source(new JsonPathSource(path))
                        .build()
        );
        for (String renderItem : render) {
            System.out.println(renderItem);
        }
    }
}
