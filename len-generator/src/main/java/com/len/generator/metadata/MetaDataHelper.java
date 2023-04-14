package com.len.generator.metadata;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class MetaDataHelper {

    public static final String str_var_key = "strVar";
    public static final String obj_var_key = "objVar";
    public static final String arr_var_key = "arrVar";
    public static final String templatePath_key = "templatePath";
    public static final String outputPath_key = "outputPath";

    /**
     * json 转成元数据
     * 
     * @param jsonStr 配置的json
     * @return MetaData
     */
    public static MetaData jsonConvertToMetaData(String jsonStr) {
        JSONObject metaJsonObj = JSON.parseObject(jsonStr);
        if (!metaJsonObj.containsKey(str_var_key) || !metaJsonObj.containsKey(obj_var_key)
            || !metaJsonObj.containsKey(arr_var_key)) {
            throw new RuntimeException(" json 格式错误，请参阅配置文档:");
        }

        String templatePathStr = metaJsonObj.getString(templatePath_key);
        String[] templatePaths = null;
        if (!StringUtils.isEmpty(templatePathStr)) {
            templatePaths = templatePathStr.split(",", -1);
        }

        String outputPath = metaJsonObj.getString(outputPath_key);
        MetaData metaData = new StandardMetaData(templatePaths, outputPath);

        JSONObject strObj = metaJsonObj.getJSONObject(str_var_key);
        for (Map.Entry<String, Object> strObjEntry : strObj.entrySet()) {
            metaData.putStrVar(strObjEntry.getKey(), strObjEntry.getValue());
        }

        JSONObject objObj = metaJsonObj.getJSONObject(obj_var_key);
        for (Map.Entry<String, Object> objObjEntry : objObj.entrySet()) {
            Object value = objObjEntry.getValue();
            metaData.putObjVar(objObjEntry.getKey(), value);
        }

        JSONObject arrObj = metaJsonObj.getJSONObject(arr_var_key);
        for (Map.Entry<String, Object> objObjEntry : arrObj.entrySet()) {
            Object value = objObjEntry.getValue();
            metaData.putArrVar(objObjEntry.getKey(), JSON.parseArray(value.toString()));
        }

        return metaData;

    }

}
