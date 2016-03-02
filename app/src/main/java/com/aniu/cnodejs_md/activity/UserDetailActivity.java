package com.aniu.cnodejs_md.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.aniu.cnodejs_md.R;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by aniu on 16/3/1.
 */
public class UserDetailActivity extends AppCompatActivity {
    private static final String NAME_IMG_AVATAR = "imgAvatar";

    @Bind(R.id.user_detail_img_avatar)
    protected ImageView imgAvatar;

    @Bind(R.id.user_detail_tv_login_name)
    protected TextView tvLoginName;

    private ViewPagerAdapter adapter;

    private String loginName;
    public static void openWithTransitionAnimation(Activity activity, String loginName, ImageView imgAvatar, String avatarUrl) {
        Intent intent = new Intent(activity,UserDetailActivity.class);
        intent.putExtra("loginName",loginName);
        intent.putExtra("avatarUrl",avatarUrl);

        //控件动画效果启动另一个activity
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity,imgAvatar,NAME_IMG_AVATAR);
        ActivityCompat.startActivity(activity,intent,options.toBundle());
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        ButterKnife.bind(this);
        //设置切换activity对应的控件
        ViewCompat.setTransitionName(imgAvatar,NAME_IMG_AVATAR);

        //设置头像
        String avatarUrl = getIntent().getStringExtra("avatarUrl");
        if (!TextUtils.isEmpty(avatarUrl)) {
            Picasso.with(this).load(avatarUrl).placeholder(R.drawable.image_placeholder).into(imgAvatar);
        }
        //用户名
        loginName = getIntent().getStringExtra("loginName");
        if (!TextUtils.isEmpty(loginName)) {
            tvLoginName.setText(loginName);
        }

        

    }


    private class ViewPagerAdapter extends FragmentPagerAdapter{

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return null;
        }

        @Override
        public int getCount() {
            return 0;
        }
    }

}
