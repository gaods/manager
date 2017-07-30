//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.hesh.common.utils.json;

import java.util.Collection;
import java.util.List;

public class JsonUtils {
    private static JsonBinder jsonBinder = JsonBinder.buildNonNullBinder();

    public JsonUtils() {
    }

    public static <T> T fromJson(String jsonString, Class<T> clazz) {
        return jsonBinder.fromJson(jsonString, clazz);
    }

    public static <T> List<T> fromJsonArray(String jsonArray, Class<T> clazz) {
        return jsonBinder.fromJsonArray(jsonArray, clazz);
    }

    public static Collection fromJsonArrayBy(String jsonArray, Class recordClazz, Class collectionClazz) {
        return jsonBinder.fromJsonArrayBy(jsonArray, recordClazz, collectionClazz);
    }

    public static String toJson(Object object) {
        return jsonBinder.toJson(object);
    }

    public static void setDateFormat(String pattern) {
        jsonBinder.setDateFormat(pattern);
    }
}
