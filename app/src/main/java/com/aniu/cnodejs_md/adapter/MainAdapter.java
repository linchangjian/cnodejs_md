package com.aniu.cnodejs_md.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aniu.cnodejs_md.R;
import com.aniu.cnodejs_md.entity.Topic;
import com.aniu.cnodejs_md.fragment.LatestFragment;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


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
//            case TYPE_LOAD_MORE:
//                return new LoadMoreViewHolder(inflater.inflate(R.layout.fragment_item_load_more, parent, false));
            default: // TYPE_NORMAL
                return new NormalViewHolder(inflater.inflate(R.layout.fragment_item_main, parent, false));
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


//        @Bind(R.id.item_load_more_icon_loading)
//        protected View iconLoading;

        @Bind(R.id.item_load_more_icon_finish)
        protected TextView iconFinish;

        public LoadMoreViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        protected void update(int position) {
            super.update(position);
        }
    }

    public class NormalViewHolder extends ViewHolder {
        @Bind(R.id.main_item_img_avatar)
        protected ImageView imgAvatar;
        @Bind(R.id.main_item_tv_title)
        protected TextView tvTitle;
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

            }
            // Picasso.with(activity).load(topic.getAuthor().getAvatarUrl()).placeholder(R.drawable.ic_discuss).into(imgAvatar);
        }
    }
}
