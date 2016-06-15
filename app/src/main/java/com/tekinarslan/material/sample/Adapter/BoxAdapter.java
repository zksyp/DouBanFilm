package com.tekinarslan.material.sample.Adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tekinarslan.material.sample.Info.RankInfo;
import com.tekinarslan.material.sample.ListBoxFragment;
import com.tekinarslan.material.sample.R;

import java.util.List;

/**
 * Created by kaishen on 16/6/14.
 */
public class BoxAdapter extends RecyclerView.Adapter<BoxAdapter.MyViewHolder>{

    private List<RankInfo> mRankList;
    private LayoutInflater mInflater;
    private ListBoxFragment mContext;

    public BoxAdapter(ListBoxFragment context, List<RankInfo> rankList) {
        mContext = context;
        mRankList = rankList;
        mInflater = LayoutInflater.from(context.getActivity());
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new MyViewHolder(mInflater.inflate(R.layout.list_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(BoxAdapter.MyViewHolder holder, int position) {
        Glide.with(mContext)
                .load(mRankList.get(position).getSubject().getImages().getMedium())
                .into(holder.mPic);
        holder.mTitle.setText(mRankList.get(position).getSubject().getTitle());
        Log.e("info",""+ mRankList.get(position).getBox());
//        for(int i = 0; i < mRankList.size() ; i++)
//        {
//            Log.e("Url",mRankList.get(i).getSubject().getImages().getMedium());
//            Log.e("Rating",mRankList.get(i).getSubject().getRating().getStars());
//        }
//        int mStar = Integer.parseInt(mRankList.get(position).getSubject().getRating().getStars())/10;
//        holder.mRating.setText(""+mStar+"星");

    }

    @Override
    public int getItemCount() {
        if(mRankList == null)
            return 0;
        else
            return mRankList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView mPic;
        TextView mTitle;
        TextView mRating;

        public MyViewHolder(View itemView) {
            super(itemView);
//            mPic = (ImageView) itemView.findViewById(R.id.iv_movie);
//            mTitle = (TextView) itemView.findViewById(R.id.tv_title);
//            mRating = (TextView) itemView.findViewById(R.id.tv_rating);
        }
    }
}
