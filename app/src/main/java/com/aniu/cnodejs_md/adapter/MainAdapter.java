package com.aniu.cnodejs_md.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aniu.cnodejs_md.R;
import com.aniu.cnodejs_md.activity.UserDetailActivity;
import com.aniu.cnodejs_md.entity.Topic;
import com.aniu.cnodejs_md.fragment.LatestFragment;
import com.aniu.cnodejs_md.utils.FormatUtils;
import com.aniu.cnodejs_md.utils.ThemeUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    private static final int TYPE_NORMAL = 0;
    private static final int TYPE_LOAD_MORE = 1;

    private final Activity activity;
    private final LayoutInflater inflater;
    private final List<Topic> topiclist;

    public MainAdapter(Activity activity, @NonNull List<Topic> topiclist) {
        this.activity = activity;
        this.inflater = LayoutInflater.from(activity);
        this.topiclist = topiclist;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_LOAD_MORE:
                return new LoadMoreViewHolder(inflater.inflate(R.layout.fragment_item_load_more, parent, false));
            default: // TYPE_NORMAL
                return new NormalViewHolder(inflater.inflate(R.layout.activity_main_item, parent, false));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (topiclist.size() >= 20 && position == getItemCount() - 1) {
            return TYPE_LOAD_MORE;
        } else {
            return TYPE_NORMAL;
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.update(position);
    }

    @Override
    public int getItemCount() {
        return topiclist.size() >= 20 ? topiclist.size() + 1 : topiclist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }

        protected void update(int position) {
        }
    }

    public class LoadMoreViewHolder extends ViewHolder {


        @Bind(R.id.item_load_more_icon_finish)
        protected TextView tv;
        public LoadMoreViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        protected void update(int position) {
            super.update(position);
            tv.setText(tv.getResources().getString(R.string.load_more));
        }
    }

    public class NormalViewHolder extends ViewHolder {
        @Bind(R.id.main_item_img_avatar)
        protected ImageView imgAvatar;

        @Bind(R.id.main_item_tv_title)
        protected TextView tvTitle;

        @Bind(R.id.main_item_tv_tab)
        protected TextView tvTab;

        @Bind(R.id.main_item_tv_author)
        protected TextView tvAuthor;

        @Bind(R.id.main_item_tv_create_time)
        protected TextView tvCreateTime;

        @Bind(R.id.main_item_tv_reply_count)
        protected TextView tvReplyCount;

        @Bind(R.id.main_item_tv_visit_count)
        protected TextView tvVisitCount;

        @Bind(R.id.main_item_tv_last_reply_time)
        protected TextView tvLastReplyTime;

        @Bind(R.id.main_item_icon_good)
        protected View iconGood;
        private Topic topic;

        public NormalViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        protected void update(int position) {
            if (position < topiclist.size()) {
                topic = topiclist.get(position);
                tvTitle.setText(topic.getTitle());
                tvTab.setText(topic.isTop() ? R.string.tab_top : topic.getTab().getNameId());
               // tvTab.setBackgroundDrawable(ThemeUtils.getThemeAttrDrawable(activity, topic.isTop() ? R.attr.referenceBackgroundAccent : R.attr.referenceBackgroundNormal));
                tvTab.setTextColor(topic.isTop() ? Color.WHITE : ThemeUtils.getThemeAttrColor(activity, android.R.attr.textColorSecondary));
                tvAuthor.setText(topic.getAuthor().getLoginName());
                tvCreateTime.setText(activity.getString(R.string.create_at_$) + topic.getCreateAt().toString("yyyy-MM-dd HH:mm:ss"));
                tvReplyCount.setText(String.valueOf(topic.getReplyCount()));
                tvVisitCount.setText(String.valueOf(topic.getVisitCount()));
                tvLastReplyTime.setText(FormatUtils.getRecentlyTimeText(topic.getLastReplyAt()));
                iconGood.setVisibility(topic.isGood() ? View.VISIBLE : View.GONE);
                Picasso.with(activity).load(topic.getAuthor().getAvatarUrl()).placeholder(R.drawable.ic_discuss).into(imgAvatar);
            }
        }

        @OnClick(R.id.main_item_img_avatar)
        protected void onImgAvatarClick(){
            UserDetailActivity.openWithTransitionAnimation(activity,topic.getAuthor().getLoginName(),imgAvatar,topic.getAuthor().getAvatarUrl());
        }
    }
}
