package com.aniu.cnodejs_md.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aniu.cnodejs_md.R;
import com.aniu.cnodejs_md.entity.Reply;
import com.aniu.cnodejs_md.entity.TopicWithReply;
import com.aniu.cnodejs_md.storage.LoginShared;
import com.aniu.cnodejs_md.utils.FormatUtils;
import com.aniu.cnodejs_md.utils.ThemeUtils;
import com.aniu.cnodejs_md.widget.CNodeWebView;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by aniu on 16/3/3.
 */
public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.ViewHolder>{
    private OnAtOnClickListener onAtClickListener;

    private Activity activity;

    private LayoutInflater inflater;

    private TopicWithReply topic;
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_REPLY = 1;
    private boolean isHeaderShow = false;
    public TopicAdapter(Activity activity, OnAtOnClickListener onAtClickListener) {
        this.onAtClickListener = onAtClickListener;
        this.activity = activity;
        inflater = LayoutInflater.from(activity);
    }

    public void setTopic(TopicWithReply topic){
        this.topic = topic;
        isHeaderShow = false;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case TYPE_HEADER:
                return new HeaderViewHolder(inflater.inflate(R.layout.activity_topic_item_header,parent,false));
            default:
                return new ReplyViewHolder(inflater.inflate(R.layout.activity_topic_item_reply,parent,false));
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        switch (getItemViewType(position)){
            case TYPE_HEADER:
                holder.update(position);
                break;
            default:
                holder.update(position - 1);
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (topic != null && position != 0) {
            return TYPE_REPLY;
        } else {
            return TYPE_HEADER;
        }
    }

    @Override
    public int getItemCount() {
        return topic == null ? 0 : topic.getReplyList().size() + 1;
    }

    public interface OnAtOnClickListener {
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        protected ViewHolder(View itemView) {
            super(itemView);

        }
        protected void update(int position){

        }
    }

    public class HeaderViewHolder extends ViewHolder{
        @Bind(R.id.topic_item_header_tv_title)
        protected TextView tvTitle;

        @Bind(R.id.topic_item_header_tv_tab)
        protected TextView tvTab;

        @Bind(R.id.topic_item_header_tv_visit_count)
        protected TextView tvVisitCount;

        @Bind(R.id.topic_item_header_img_avatar)
        protected ImageView imgAvatar;

        @Bind(R.id.topic_item_header_tv_login_name)
        protected TextView tvLoginName;

        @Bind(R.id.topic_item_header_tv_create_time)
        protected TextView tvCreateTime;

        @Bind(R.id.topic_item_header_web_content)
        protected CNodeWebView webContent;

        @Bind(R.id.topic_item_header_icon_good)
        protected View iconGood;

        @Bind(R.id.topic_item_header_layout_no_reply)
        protected ViewGroup layoutNoReply;
        public HeaderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        protected void update(int position) {
            if(!isHeaderShow){
                tvTitle.setText(topic.getTitle());
                tvTab.setText(topic.isTop() ? R.string.tab_top : topic.getTab().getNameId());
               // tvTab.setBackgroundDrawable(ThemeUtils.getThemeAttrDrawable(activity, topic.isTop() ? R.attr.referenceBackgroundAccent : R.attr.referenceBackgroundNormal));
                tvTab.setTextColor(topic.isTop() ? Color.WHITE : ThemeUtils.getThemeAttrColor(activity, android.R.attr.textColorSecondary));
                tvVisitCount.setText(topic.getVisitCount() + "次浏览");
                Picasso.with(activity).load(topic.getAuthor().getAvatarUrl()).placeholder(R.drawable.image_placeholder).into(imgAvatar);
                tvLoginName.setText(topic.getAuthor().getLoginName());
                tvCreateTime.setText(activity.getString(R.string.post_at_$) + FormatUtils.getRecentlyTimeText(topic.getCreateAt()));
                iconGood.setVisibility(topic.isGood() ? View.VISIBLE : View.GONE);

                // TODO 这里直接使用WebView，有性能问题  不理解有什么问题。
                webContent.loadRenderedContent(topic.getHandleContent());
                isHeaderShow = true;

            }
            layoutNoReply.setVisibility(topic.getReplyList().size() > 0 ? View.GONE : View.VISIBLE);

        }

    }


    public class ReplyViewHolder extends ViewHolder{

        @Bind(R.id.topic_item_reply_img_avatar)
        protected ImageView imgAvatar;

        @Bind(R.id.topic_item_reply_tv_login_name)
        protected TextView tvLoginName;

        @Bind(R.id.topic_item_reply_tv_index)
        protected TextView tvIndex;

        @Bind(R.id.topic_item_reply_tv_create_time)
        protected TextView tvCreateTime;

        @Bind(R.id.topic_item_reply_btn_ups)
        protected TextView btnUps;

        @Bind(R.id.topic_item_reply_web_content)
        protected CNodeWebView webContent;

        @Bind(R.id.topic_item_reply_icon_deep_line)
        protected View iconDeepLine;

        @Bind(R.id.topic_item_reply_icon_shadow_gap)
        protected View iconShadowGap;

        private Reply reply;
        private int position = -1;

        protected ReplyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        protected void update(int position) {
            super.update(position);
            this.position = position;
            reply = topic.getReplyList().get(position);
            Picasso.with(activity).load(reply.getAuthor().getAvatarUrl()).placeholder(R.drawable.image_placeholder).into(imgAvatar);
            tvLoginName.setText(reply.getAuthor().getLoginName());
            tvIndex.setText(position + 1 + "楼");
            tvCreateTime.setText(FormatUtils.getRecentlyTimeText(reply.getCreateAt()));
            btnUps.setText(String.valueOf(reply.getUpList().size()));
            btnUps.setCompoundDrawablesWithIntrinsicBounds(reply.getUpList().contains(LoginShared.getId(activity)) ? R.drawable.ic_thumb_up_theme_24dp : R.drawable.ic_thumb_up_grey600_24dp, 0, 0, 0);
            iconDeepLine.setVisibility(position == topic.getReplyList().size() - 1 ? View.GONE : View.VISIBLE);
            iconShadowGap.setVisibility(position == topic.getReplyList().size() - 1 ? View.VISIBLE : View.GONE);

            // TODO 这里直接使用WebView，有性能问题
            webContent.loadRenderedContent(reply.getHandleContent());
        }
    }
}
