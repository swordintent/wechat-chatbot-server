package com.swordintent.wx.mp.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonUtils {
    private static Gson prettyGson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    private static Gson gson = new GsonBuilder()
            .create();
    public static String toJson(Object obj) {
        return gson.toJson(obj);
    }
    public static <T> T fromJson(String json, Class<T> t) {
        return gson.fromJson(json, t);
    }

}
