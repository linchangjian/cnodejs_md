package com.aniu.cnodejs_md.utils;

import android.os.Handler;
import android.os.Looper;

/**
 * Created by aniu on 15/10/30.
 */
public class HandlerUtils {


    private HandlerUtils() {
    }

    private static final Handler handler = new Handler(Looper.getMainLooper());

    public static boolean post(Runnable r) {
        return handler.post(r);
    }

    public static boolean postDelayed(Runnable r, long delayMillis) {
        return handler.postDelayed(r, delayMillis);

    }

}
