package com.tekinarslan.material.sample;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Movie;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
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
import com.google.gson.Gson;
import com.tekinarslan.material.sample.Adapter.BaseAdapter;
import com.tekinarslan.material.sample.Info.MovieInfo;

import java.util.List;

/**
 * Created by kaishen on 16/6/14.
 */
class GeneralAdapter extends RecyclerView.Adapter<GeneralAdapter.ItemViewHolder>{

    private List<MovieInfo> mMovieList;
    private LayoutInflater mInflater;
    private Context mContext;


    /**
     * 判断是否属于“即将上映”
     */
    private boolean mIsComingFilm;
    private int totalDataCount = 0;
    private GetNetInfo mGet = new GetNetInfo();
    private MovieInfo mMovieInfo;
    private String mLoadUrl;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mMovieInfo = (MovieInfo) msg.obj;
            mLoadUrl = mMovieInfo.getMobile_url();
            Intent intent = new Intent();
            intent.putExtra("LoadUrl",mLoadUrl);
            Log.e("set",mLoadUrl);
//            intent.putExtra("LoadUrl","https://movie.douban.com/subject/1764796/mobile");
            intent.setClass(mContext, MovieWebActivity.class);
            mContext.startActivity(intent);
        }
    };


    public GeneralAdapter(Context context, List<MovieInfo> movieList, boolean isComingFilm) {
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

//        holder.mTitle.setText(mMovieList.get(position).getTitle());
//        int mStar = Integer.parseInt(mMovieList.get(position).getRating().getStars())/10;
//        holder.mRating.setText(""+mStar+"星");
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
            mGet.setmHandler(mHandler);
            mGet.setUrl("https://api.douban.com/v2/movie/subject/" + mMovieList.get(getLayoutPosition()).getId());
            mGet.GetNetInfo(false);
        }


        public void update(GeneralAdapter.ItemViewHolder holder, int position) {

            if(!mIsComingFilm){
                mRatingLayout.setVisibility(View.VISIBLE);
                float rate = (float) mMovieList.get(position).getRating().getAverage();
                mRatingBar.setRating(rate / 2);
                mRating.setText(String.format("%s", rate));
                mCount.setText("(");
                mCount.append(String.format("%d", mMovieList.get(position).getCount()));
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
