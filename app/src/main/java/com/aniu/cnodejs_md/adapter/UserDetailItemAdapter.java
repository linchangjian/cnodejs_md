package com.aniu.cnodejs_md.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aniu.cnodejs_md.R;
import com.aniu.cnodejs_md.activity.UserDetailActivity;
import com.aniu.cnodejs_md.entity.TopicSimple;
import com.aniu.cnodejs_md.utils.FormatUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by aniu on 16/3/2.
 */
public class UserDetailItemAdapter extends RecyclerView.Adapter<UserDetailItemAdapter.ViewHolder> {

    private Activity activity;
    private LayoutInflater inflater;
    private List<TopicSimple> topicList;

    public UserDetailItemAdapter(Activity activity, List<TopicSimple> topicList) {
        this.activity = activity;
        inflater = LayoutInflater.from(activity);
        this.topicList = topicList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.activity_user_detail_item,parent,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.update(position);
    }

    @Override
    public int getItemCount() {
        return topicList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        @Bind(R.id.user_detail_item_img_avatar)
        protected ImageView imgAvatar;

        @Bind(R.id.user_detail_item_tv_title)
        protected TextView tvTitle;

        @Bind(R.id.user_detail_item_tv_login_name)
        protected TextView tvLoginName;

        @Bind(R.id.user_detail_item_tv_last_reply_time)
        protected TextView tvLastReplyTime;

        private TopicSimple topic;
        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, itemView);
        }

        public void update(int position) {
            topic = topicList.get(position);
            tvTitle.setText(topic.getTitle());
            Picasso.with(activity).load(topic.getAuthor().getAvatarUrl()).placeholder(R.drawable.image_placeholder).into(imgAvatar);
            tvLoginName.setText(topic.getAuthor().getLoginName());
            tvLastReplyTime.setText(FormatUtils.getRecentlyTimeText(topic.getLastReplyAt()));
        }

        @OnClick(R.id.user_detail_item_img_avatar)
        protected void onBtnAvatarClick() {
            UserDetailActivity.openWithTransitionAnimation(activity, topic.getAuthor().getLoginName(), imgAvatar, topic.getAuthor().getAvatarUrl());
        }

        @OnClick(R.id.user_detail_item_btn_item)
        protected void onBtnItemClick() {
            //TopicActivity.open(activity, topic.getId());
        }
    }
}
