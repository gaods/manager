//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.hesh.common.utils.json;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.DeserializationConfig.Feature;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.codehaus.jackson.map.type.TypeFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonBinder {
    private static final Logger logger = LoggerFactory.getLogger(JsonBinder.class);
    private ObjectMapper mapper = new ObjectMapper();

    public JsonBinder(Inclusion inclusion) {
        this.mapper.setSerializationInclusion(inclusion);
        this.mapper.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        this.setDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    public static JsonBinder buildNormalBinder() {
        return new JsonBinder(Inclusion.ALWAYS);
    }

    public static JsonBinder buildNonNullBinder() {
        return new JsonBinder(Inclusion.NON_NULL);
    }

    public static JsonBinder buildNonEmptyBinder() {
        return new JsonBinder(Inclusion.NON_EMPTY);
    }

    public static JsonBinder buildNonDefaultBinder() {
        return new JsonBinder(Inclusion.NON_DEFAULT);
    }

    public <T> T fromJson(String jsonString, Class<T> clazz) {
        if(StringUtils.isEmpty(jsonString)) {
            return null;
        } else {
            try {
                return this.mapper.readValue(jsonString, clazz);
            } catch (IOException var4) {
                logger.warn("parse json string error:" + jsonString, var4);
                throw new RuntimeException(var4);
            }
        }
    }

    public <T> List<T> fromJsonArray(String jsonArray, Class<T> clazz) {
        if(StringUtils.isEmpty(jsonArray)) {
            return null;
        } else {
            TypeFactory typeFactory = TypeFactory.defaultInstance();

            try {
                return (List)this.mapper.readValue(jsonArray, typeFactory.constructCollectionType(ArrayList.class, clazz));
            } catch (IOException var5) {
                logger.warn("parse json string error:" + jsonArray, var5);
                throw new RuntimeException(var5);
            }
        }
    }

    public Collection fromJsonArrayBy(String jsonArray, Class recordClazz, Class collectionClazz) {
        if(StringUtils.isEmpty(jsonArray)) {
            return null;
        } else {
            TypeFactory typeFactory = TypeFactory.defaultInstance();

            try {
                return (Collection)this.mapper.readValue(jsonArray, typeFactory.constructCollectionType(collectionClazz, recordClazz));
            } catch (IOException var6) {
                logger.warn("parse json string error:" + jsonArray, var6);
                throw new RuntimeException(var6);
            }
        }
    }

    public String toJson(Object object) {
        try {
            return this.mapper.writeValueAsString(object);
        } catch (IOException var3) {
            logger.warn("write to json string error:" + object, var3);
            throw new RuntimeException(var3);
        }
    }

    public void setDateFormat(String pattern) {
        if(StringUtils.isNotBlank(pattern)) {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            this.mapper.setDateFormat(df);
        }

    }

    public ObjectMapper getMapper() {
        return this.mapper;
    }

    public void setMapper(ObjectMapper mapper) {
        this.mapper = mapper;
    }
}
