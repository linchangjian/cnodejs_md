package com.aniu.cnodejs_md.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.aniu.cnodejs_md.R;
import com.aniu.cnodejs_md.adapter.TopicAdapter;
import com.aniu.cnodejs_md.api.ApiClient;
import com.aniu.cnodejs_md.entity.Result;
import com.aniu.cnodejs_md.entity.TopicWithReply;
import com.aniu.cnodejs_md.listener.NavigationFinishClickListener;
import com.aniu.cnodejs_md.utils.RefreshLayoutUtils;
import com.aniu.cnodejs_md.utils.ToastUtils;
import com.melnykov.fab.FloatingActionButton;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by aniu on 16/3/3.
 */
public class TopicActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, TopicAdapter.OnAtOnClickListener, Toolbar.OnMenuItemClickListener{

    private static final String EXTRA_TOPIC_ID = "topicId";

    private TopicWithReply topic;

    @Bind(R.id.topic_toolbar)
    protected Toolbar toolbar;

    private String topicId;

    private TopicAdapter adapter;

    @Bind(R.id.topic_recycler_view)
    protected RecyclerView recyclerView;

    @Bind(R.id.topic_fab_reply)
    protected FloatingActionButton fabReply;

    @Bind(R.id.topic_icon_no_data)
    protected View iconNoData;

    @Bind(R.id.topic_refresh_layout)
    protected SwipeRefreshLayout refreshLayout;

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }

    @Override
    public void onRefresh() {
        ApiClient.service.getTopic(topicId, true, new Callback<Result<TopicWithReply>>() {
            @Override
            public void success(Result<TopicWithReply> result, Response response) {
                if (!isFinishing()) {
                    topic = result.getData();
                    adapter.setTopic(result.getData());
                    adapter.notifyDataSetChanged();
                    iconNoData.setVisibility(View.GONE);
                    refreshLayout.setRefreshing(false);

                }
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });

    }

    public static void open(Context context, String topicId) {
        Intent intent = new Intent(context, TopicActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(EXTRA_TOPIC_ID, topicId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);
        ButterKnife.bind(this);

        topicId = getIntent().getStringExtra(EXTRA_TOPIC_ID);

        toolbar.setNavigationOnClickListener(new NavigationFinishClickListener(this));
        toolbar.inflateMenu(R.menu.topic);
        toolbar.setOnMenuItemClickListener(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TopicAdapter(this, this);
        recyclerView.setAdapter(adapter);

        fabReply.attachToRecyclerView(recyclerView);

        //算是refreshlayout 与 activity 绑定
        RefreshLayoutUtils.initOnCreate(refreshLayout, this);
        RefreshLayoutUtils.refreshOnCreate(refreshLayout, this);

    }

    @Override
    public void onAt(String loginName) {

    }
}
