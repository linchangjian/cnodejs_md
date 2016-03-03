package com.aniu.cnodejs_md.listener;

import android.app.Activity;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;

import com.aniu.cnodejs_md.activity.TopicActivity;

/**
 * Created by aniu on 16/3/3.
 */
public class NavigationFinishClickListener implements View.OnClickListener {

    private Activity activity;
    public NavigationFinishClickListener(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {
        ActivityCompat.finishAfterTransition(activity);
    }
}
