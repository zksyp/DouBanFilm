package com.tekinarslan.material.sample;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.tekinarslan.material.sample.Info.RankBean;
import com.tekinarslan.material.sample.util.CelebrityUtil;
import com.tekinarslan.material.sample.util.StringUtil;

import java.util.List;

/**
 * Created by kaishen on 16/6/14.
 */
public class BoxAdapter extends RecyclerView.Adapter<BoxAdapter.MyViewHolder> {

    private static final int FIRST = 1;
    private static final int SECOND = 2;
    private static final int THIRD = 3;

    private static final String GOLD = "#D9D919";
    private static final String SILVERY = "#D9D919";
    private static final String COPPER = "#B87333";

    public static final String[] RANK = {"th", "1st", "2nd", "3rd"};

    private List<RankBean> mRankList;
    private LayoutInflater mInflater;
    private Context mContext;

    public BoxAdapter(Context context, List<RankBean> rankList) {
        mContext = context;
        mRankList = rankList;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new MyViewHolder(mInflater.inflate(R.layout.list_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.mRatingLayout.setVisibility(View.VISIBLE);
        float rate = mRankList.get(position).getSubject().getRating().getAverage();
        holder.mRatingBar.setRating(rate / 2);
        holder.mRating.setText(String.format("%s", rate));
        holder.mCount.setText("(");
        holder.mCount.append(String.format("%d", mRankList.get(position).getSubject().getCollect_count()));
        holder.mCount.append("人评价)");
        String title = mRankList.get(position).getSubject().getTitle();
        String original_title = mRankList.get(position).getSubject().getOriginal_title();
        holder.mTitle.setText(title);
        if (original_title.equals(title)) {
            holder.mOrignalTitle.setVisibility(View.GONE);
        } else {
            holder.mOrignalTitle.setText(original_title);
            holder.mOrignalTitle.setVisibility(View.VISIBLE);
        }
        holder.mGenre.setText(StringUtil.getListString(mRankList.get(position).getSubject().getGenres(), ','));
        holder.mDirector.setText(StringUtil.getSpannableString("导演:", Color.GRAY));
        holder.mDirector.append(CelebrityUtil.list2String(mRankList.get(position).getSubject().getDirectors(), '/'));
        holder.mCast.setText(StringUtil.getSpannableString("主演:", Color.GRAY));
        holder.mCast.append(CelebrityUtil.list2String(mRankList.get(position).getSubject().getCasts(), '/'));
        Glide.with(mContext)
                .load(mRankList.get(position).getSubject().getImages().getLarge())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.mPic);
        holder.mBoxContainerLayout.setVisibility(View.VISIBLE);
        int rank = mRankList.get(position).getRank();
        holder.mBoxTv.setTextColor(getRankTextColor(rank));
        if (rank < 4){
            holder.mBoxTv.setText(RANK[rank]);
        }else{
            holder.mBoxTv.setText(rank + "" + RANK[0]);
        }
        holder.mBoxIv.setVisibility(mRankList.get(position).getNew() ? View.VISIBLE : View.GONE);
    }

    private int getRankTextColor(int rank) {
        switch (rank) {
            case FIRST:
                return Color.parseColor(GOLD);
            case SECOND:
                return Color.parseColor(SILVERY);
            case THIRD:
                return Color.parseColor(COPPER);
            default:
                return Color.GRAY;
        }
    }

    @Override
    public int getItemCount() {
        if (mRankList == null)
            return 0;
        else
            return mRankList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView mPic;
        private TextView mTitle;
        private TextView mOrignalTitle;
        private TextView mDirector;
        private TextView mCast;
        private TextView mCount;
        private TextView mGenre;
        private TextView mRating;
        private RatingBar mRatingBar;
        private LinearLayout mRatingLayout;
        private FrameLayout mBoxContainerLayout;
        private TextView mBoxTv;
        private ImageView mBoxIv;

        public MyViewHolder(View itemView) {
            super(itemView);
            mPic = (ImageView) itemView.findViewById(R.id.iv_movieinfo_image);
            mTitle = (TextView) itemView.findViewById(R.id.tv_movieinfo_title);
            mRating = (TextView) itemView.findViewById(R.id.tv_movieinfo_rating);
            mOrignalTitle = (TextView) itemView.findViewById(R.id.tv_movieinfo_original_title);
            mCast = (TextView) itemView.findViewById(R.id.tv_movieinfo_cast);
            mCount = (TextView) itemView.findViewById(R.id.tv_movieinfo_count);
            mDirector = (TextView) itemView.findViewById(R.id.tv_movieinfo_director);
            mGenre = (TextView) itemView.findViewById(R.id.tv_movieinfo_genres);
            mRatingBar = (RatingBar) itemView.findViewById(R.id.rb_movieinfo_rating);
            mRatingLayout = (LinearLayout) itemView.findViewById(R.id.ll_movieinfo_rating);
            mBoxContainerLayout = (FrameLayout) itemView.findViewById(R.id.fl_box_container);
            mBoxTv = (TextView) itemView.findViewById(R.id.tv_item_box_rank);
            mBoxIv = (ImageView) itemView.findViewById(R.id.iv_item_box_isNew);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.putExtra("ID", mRankList.get(getLayoutPosition()).getSubject().getId());
            intent.setClass(mContext, SubjectActivity.class);
            mContext.startActivity(intent);
        }
    }
}