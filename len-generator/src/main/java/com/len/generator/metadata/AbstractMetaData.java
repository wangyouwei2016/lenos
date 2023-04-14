package com.len.generator.metadata;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractMetaData implements MetaData {

    protected final StrVar<String, Object> strVar = new StrVar<>();
    protected final ObjVar<String, Object> objVar = new ObjVar<>();

    protected final ArrVar<String, Collection<?>> arrVar = new ArrVar<>();

    @Override
    public Map<String, Object> getAll() {
        Map<String, Object> map = new HashMap<>();
        map.putAll(strVar);
        map.putAll(objVar);
        map.putAll(arrVar);
        return map;

    }
}
