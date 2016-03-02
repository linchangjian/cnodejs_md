package com.aniu.cnodejs_md.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aniu.cnodejs_md.R;
import com.aniu.cnodejs_md.activity.UserDetailActivity;
import com.aniu.cnodejs_md.adapter.UserDetailItemAdapter;
import com.aniu.cnodejs_md.entity.TopicSimple;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by aniu on 16/3/2.
 */
public class UserDetailItemFragment extends Fragment{
    @Bind(R.id.user_detail_fragment_recycler_view)
    protected RecyclerView recyclerView;

    private UserDetailItemAdapter adapter;
    private List<TopicSimple> topicList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_user_detail_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new UserDetailItemAdapter(getActivity(), topicList);
        recyclerView.setAdapter(adapter);
    }

    public void notifyDataSetChanged(List<TopicSimple> topicList) {
        if(this.topicList.size() > 0){
            this.topicList.clear();
            this.topicList.addAll(topicList);
            adapter.notifyDataSetChanged();
        }else{
            this.topicList.addAll(topicList);
            adapter.notifyItemRangeChanged(0,topicList.size());
        }
    }
}
