package com.nju.graduation.project.bas.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nju.graduation.project.bas.exception.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * @author shanhe
 * @className JsonUtils
 * @date 2021-02-25 09:15
 **/
public class JsonUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtils.class);
    private static final Gson gson = new Gson();

    public static String object2Json(Object obj) {
        try {
            return gson.toJson(obj);
        } catch (Exception e) {
            LOGGER.error("JsonUtils.object2Json error, obj={}, errorMessage={}", obj, e.getMessage(), e);
        }
        return null;
    }


    public static <T> T json2Pojo(String jsonData, Class<T> beanType) {
        try {
            T t = gson.fromJson(jsonData, beanType);
            return t;
        } catch (Exception e) {
            LOGGER.error("JsonUtils.json2Pojo error, jsonData={}, Class<T>={}, errorMessage={}", jsonData, beanType, e.getMessage(), e);
        }
        return null;
    }

    public static <T> List<T> json2List(String jsonData, Class<T> beanType) {
        try {
            Type type = new ParameterizedTypeImpl(beanType);
            List<T> list = gson.fromJson(jsonData, type);
            return list;
        } catch (Exception e) {
            LOGGER.error("JsonUtils.json2List error, jsonData={}, Class<T>={}, errorMessage={}", jsonData, beanType, e.getMessage(), e);
        }
        return null;
    }

    private static class ParameterizedTypeImpl implements ParameterizedType {
        private Class clazz;

        public ParameterizedTypeImpl(Class clazz) {
            this.clazz = clazz;
        }

        @Override
        public Type[] getActualTypeArguments() {
            return new Type[]{clazz};
        }

        @Override
        public Type getRawType() {
            return List.class;
        }

        @Override
        public Type getOwnerType() {
            return null;
        }
    }
}
