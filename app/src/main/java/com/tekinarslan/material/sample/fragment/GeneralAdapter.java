package com.tekinarslan.material.sample.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.tekinarslan.material.sample.Info.MovieBean;
import com.tekinarslan.material.sample.R;
import com.tekinarslan.material.sample.MovieWebActivity;
import com.tekinarslan.material.sample.SubjectActivity;
import com.tekinarslan.material.sample.bean.GetNetBean;
import com.tekinarslan.material.sample.util.CelebrityUtil;
import com.tekinarslan.material.sample.util.StringUtil;

import java.util.List;

/**
 * Created by kaishen on 16/6/14.
 */
public class GeneralAdapter extends RecyclerView.Adapter<GeneralAdapter.ItemViewHolder>{

    private List<MovieBean> mMovieList;
    private LayoutInflater mInflater;
    private Context mContext;


    /**
     * 判断是否属于“即将上映”
     */
    private boolean mIsComingFilm;
    private int totalDataCount = 0;

    public GeneralAdapter(Context context, List<MovieBean> movieList, boolean isComingFilm) {
        mContext = context;
        mMovieList = movieList;
        mInflater = LayoutInflater.from(context);
        mIsComingFilm = isComingFilm;
    }




    @Override
    public GeneralAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(mInflater.inflate(R.layout.list_item_layout, parent, false));
    }
    @Override
    public void onBindViewHolder(GeneralAdapter.ItemViewHolder holder, int position) {

        holder.update(holder, position);
    }

    @Override
    public int getItemCount() {
        if(mMovieList == null)
            return 0;
        else
            return mMovieList.size();
    }

    public int getTotalDataCount() {
        return totalDataCount;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

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

        public ItemViewHolder(View itemView) {
            super(itemView);
            mPic = (ImageView) itemView.findViewById(R.id.iv_movieinfo_image);
            mTitle = (TextView) itemView.findViewById(R.id.tv_movieinfo_title);
            mRating = (TextView) itemView.findViewById(R.id.tv_movieinfo_rating);
            mOrignalTitle= (TextView) itemView.findViewById(R.id.tv_movieinfo_original_title);
            mCast = (TextView) itemView.findViewById(R.id.tv_movieinfo_cast);
            mCount = (TextView) itemView.findViewById(R.id.tv_movieinfo_count);
            mDirector = (TextView) itemView.findViewById(R.id.tv_movieinfo_director);
            mGenre = (TextView) itemView.findViewById(R.id.tv_movieinfo_genres);
            mRatingBar = (RatingBar) itemView.findViewById(R.id.rb_movieinfo_rating);
            mRatingLayout = (LinearLayout) itemView.findViewById(R.id.ll_movieinfo_rating);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.putExtra("ID", mMovieList.get(getLayoutPosition()).getId());
            intent.setClass(mContext, SubjectActivity.class);
            mContext.startActivity(intent);
        }


        public void update(GeneralAdapter.ItemViewHolder holder, int position) {

            if(!mIsComingFilm){
                mRatingLayout.setVisibility(View.VISIBLE);
                float rate = mMovieList.get(position).getRating().getAverage();
                mRatingBar.setRating(rate / 2);
                mRating.setText(String.format("%s", rate));
                mCount.setText("(");
                mCount.append(String.format("%d", mMovieList.get(position).getCollect_count()));
                mCount.append("人评价)");
            }
            String title = mMovieList.get(position).getTitle();
            String original_title = mMovieList.get(position).getOriginal_title();
            mTitle.setText(title);
            if (original_title.equals(title)) {
                mOrignalTitle.setVisibility(View.GONE);
            } else {
                mOrignalTitle.setText(original_title);
                mOrignalTitle.setVisibility(View.VISIBLE);
            }
            mGenre.setText(StringUtil.getListString(mMovieList.get(position).getGenres(), ','));
            mDirector.setText(StringUtil.getSpannableString("导演:",Color.GRAY));
            mDirector.append(CelebrityUtil.list2String(mMovieList.get(position).getDirectors(), '/'));
            mCast.setText(StringUtil.getSpannableString("主演:", Color.GRAY));
            mCast.append(CelebrityUtil.list2String(mMovieList.get(position).getCasts(), '/'));
            Glide.with(mContext)
                    .load(mMovieList.get(position).getImages().getLarge())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.mPic);
        }
    }

}
