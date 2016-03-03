package com.aniu.cnodejs_md.listener;

import android.content.Context;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.aniu.cnodejs_md.activity.TopicActivity;
import com.aniu.cnodejs_md.activity.UserDetailActivity;
import com.aniu.cnodejs_md.utils.ShipUtils;


public class CNodeWebViewClient extends WebViewClient {

    private volatile static CNodeWebViewClient singleton;

    public static CNodeWebViewClient with(Context context) {
        if (singleton == null) {
            synchronized (CNodeWebViewClient.class) {
                if (singleton == null) {
                    singleton = new CNodeWebViewClient(context);
                }
            }
        }
        return singleton;
    }

    private final Context context;

    private CNodeWebViewClient(Context context) {
        this.context = context.getApplicationContext();
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView webView, String url) {
        if (url.startsWith("https://cnodejs.org/user/")) { // 用户主页协议
            UserDetailActivity.open(context, url.substring(25));
        } else if (url.startsWith("https://cnodejs.org/topic/")) { // 话题主页协议
            TopicActivity.open(context, url.substring(26));
        } else { // 其他连接
            ShipUtils.openInBrowser(context, url);
        }
        return true;
    }

}
