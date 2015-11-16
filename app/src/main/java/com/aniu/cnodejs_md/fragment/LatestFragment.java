package com.aniu.cnodejs_md.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aniu.cnodejs_md.adapter.MainAdapter;
import com.aniu.cnodejs_md.R;
import com.aniu.cnodejs_md.api.ApiClient;
import com.aniu.cnodejs_md.entity.Result;
import com.aniu.cnodejs_md.entity.TabType;
import com.aniu.cnodejs_md.entity.Topic;
import com.aniu.cnodejs_md.listener.RecyclerViewLoadMoreListener;
import com.aniu.cnodejs_md.utils.RefreshLayoutUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class LatestFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener,RecyclerViewLoadMoreListener.OnLoadMoreListener{
    private final String TAG = LatestFragment.class.getSimpleName();

    private List<Topic> topicList = new ArrayList<>();

    @Bind(R.id.topic_refresh_layout)
    protected SwipeRefreshLayout refreshLayout;
    @Bind(R.id.topic_recycler_view)
    protected RecyclerView topic_recycler_view;

    private int currentPager = 1;
    private MainAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_topics, container, false);
        ButterKnife.bind(this, view);
        RefreshLayoutUtils.initOnCreate(refreshLayout, this);
        RefreshLayoutUtils.refreshOnCreate(refreshLayout, this);
        initRecyclerView2(topic_recycler_view);
        return view;
    }
    @Override public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
    private void initRecyclerView2(RecyclerView recyclerView) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new MainAdapter(getActivity(), topicList);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerViewLoadMoreListener(linearLayoutManager,this, 20));
    }

    private void notifyDataSetChange() {
//        if (topicList.size() < 20) {
//            adapter.setLoading(false);
//        }
        adapter.notifyDataSetChanged();
    }
    @Override
    public void onRefresh() {
        Log.e(TAG,"onRefresh");
        //final TabType tab = currentTab;
        ApiClient.service.getTopicList(TabType.all, 1, 20, true, new Callback<Result<List<Topic>>>() {
            @Override
            public void success(Result<List<Topic>> listResult, Response response) {
                Log.e(TAG,"onRefresh() --> success");
                if(listResult.getData() != null){
                    topicList.clear();
                    topicList.addAll(listResult.getData());
                    notifyDataSetChange();
                    refreshLayout.setRefreshing(false);
                    currentPager++;
                }
            }



            @Override
            public void failure(RetrofitError error) {
                Log.d(TAG, "onRefresh() --> failure");
            }
        });
    }

    @Override
    public void onLoadMore() {
        Log.d(TAG,"onLoadMore");
        ApiClient.service.getTopicList(TabType.all, currentPager++, 20, true, new Callback<Result<List<Topic>>>() {
            @Override
            public void success(Result<List<Topic>> listResult, Response response) {
                if(listResult.getData() != null){
                    topicList.addAll(listResult.getData());
                    notifyDataSetChange();
                    refreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(TAG, "onLoadMore() --> failure");

            }
        });

    }
}
