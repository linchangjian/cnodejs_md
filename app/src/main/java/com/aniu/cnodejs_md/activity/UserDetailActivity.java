package com.aniu.cnodejs_md.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aniu.cnodejs_md.R;
import com.aniu.cnodejs_md.api.ApiClient;
import com.aniu.cnodejs_md.entity.Result;
import com.aniu.cnodejs_md.entity.User;
import com.aniu.cnodejs_md.fragment.UserDetailItemFragment;
import com.aniu.cnodejs_md.utils.HandlerUtils;
import com.aniu.cnodejs_md.utils.ShipUtils;
import com.pnikosis.materialishprogress.ProgressWheel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by aniu on 16/3/1.
 */
public class UserDetailActivity extends AppCompatActivity {
    private static final String NAME_IMG_AVATAR = "imgAvatar";

    @Bind(R.id.user_detail_img_avatar)
    protected ImageView imgAvatar;

    @Bind(R.id.user_detail_tv_login_name)
    protected TextView tvLoginName;

    @Bind(R.id.user_detail_view_pager)
    protected ViewPager viewPager;

    private ViewPagerAdapter adapter;

    @Bind(R.id.user_detail_tab_layout)
    protected TabLayout tabLayout;

    @Bind(R.id.user_detail_progress_wheel)
    protected ProgressWheel progressWheel;

    @Bind(R.id.user_detail_tv_github_username)
    protected TextView tvGithubUsername;

    @Bind(R.id.user_detail_tv_create_time)
    protected TextView tvCreateTime;

    @Bind(R.id.user_detail_tv_score)
    protected TextView tvScore;
    private String loginName;
    private String githubUsername;
    private boolean loading = false;
    private long startLoadingTime = 0;

    public static void openWithTransitionAnimation(Activity activity, String loginName, ImageView imgAvatar, String avatarUrl) {
        Intent intent = new Intent(activity,UserDetailActivity.class);
        intent.putExtra("loginName", loginName);
        intent.putExtra("avatarUrl", avatarUrl);

        //控件动画效果启动另一个activity
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity,imgAvatar,NAME_IMG_AVATAR);
        ActivityCompat.startActivity(activity, intent, options.toBundle());
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

        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(adapter.getCount());
        tabLayout.setupWithViewPager(viewPager);

        getUserAsyncTask();

    }

    private void getUserAsyncTask() {
        loading = true;
        progressWheel.spin();
        startLoadingTime = System.currentTimeMillis();
        ApiClient.service.getUser(loginName,new Callback<Result<User>>(){

            private long getPostTime(){
                long postTime = 1000 - (System.currentTimeMillis() - startLoadingTime);
                if(postTime > 0){
                    return postTime;
                }else{
                    return 0;
                }
            }

            @Override
            public void success(final Result<User> userResult, Response response) {
                HandlerUtils.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(!isFinishing()){
                            updateUserInfoViews(userResult.getData());
                            adapter.update(userResult.getData());
                            githubUsername = userResult.getData().getGithubUsername();
                            progressWheel.setProgress(0);
                            loading = false;
                        }
                    }
                },getPostTime());
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }




    private class ViewPagerAdapter extends FragmentPagerAdapter{


        private List<UserDetailItemFragment> fmList = new ArrayList<>();
        private String[] titles = {
                "最近回复",
                "最新发布"
        };

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
            fmList.add(new UserDetailItemFragment());
            fmList.add(new UserDetailItemFragment());
        }

        @Override
        public Fragment getItem(int position) {
            return fmList.get(position);
        }

        @Override
        public int getCount() {
            return fmList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        public void update(User user) {
            fmList.get(0).notifyDataSetChanged(user.getRecentReplyList());
            fmList.get(1).notifyDataSetChanged(user.getRecentTopicList());
        }
    }
    private void updateUserInfoViews(User user) {
        Picasso.with(this).load(user.getAvatarUrl()).placeholder(R.drawable.image_placeholder).into(imgAvatar);
        tvLoginName.setText(user.getLoginName());
        if (TextUtils.isEmpty(user.getGithubUsername())) {
            tvGithubUsername.setVisibility(View.INVISIBLE);
            tvGithubUsername.setText(null);
        } else {
            tvGithubUsername.setVisibility(View.VISIBLE);
            tvGithubUsername.setText(Html.fromHtml("<u>" + user.getGithubUsername() + "@github.com" + "</u>"));
        }
        tvCreateTime.setText(getString(R.string.register_time_$) + user.getCreateAt().toString("yyyy-MM-dd"));
        tvScore.setText(getString(R.string.score_$) + user.getScore());
    }

    @OnClick(R.id.user_detail_tv_github_username)
    protected void onBtnGithubUsernameClick() {
        if (!TextUtils.isEmpty(githubUsername)) {
            ShipUtils.openInBrowser(this, "https://github.com/" + githubUsername);
        }
    }
}
