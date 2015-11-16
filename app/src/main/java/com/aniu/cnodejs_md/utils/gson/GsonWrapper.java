package com.aniu.cnodejs_md.utils.gson;

import com.aniu.cnodejs_md.utils.gson.DateTimeTypeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.joda.time.DateTime;

public final class GsonWrapper {

    private GsonWrapper() {}

    public static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(DateTime.class, new DateTimeTypeAdapter())
            .create();

}
