package com.tekinarslan.material.sample;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.tekinarslan.material.sample.Adapter.BoxAdapter;
import com.tekinarslan.material.sample.Info.BoxInfo;

import java.io.IOException;
import java.lang.ref.WeakReference;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by kaishen on 16/6/14.
 */
public class ListBoxFragment extends Fragment {

    private static final String ARG_POSITION = "position";
    //ItemView的类型，FootView应用于加载更多
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOT = 1;

    //FootView的显示类型
    public static final int FOOT_LOADING = 0;
    public static final int FOOT_COMPLETED = 1;
    public static final int FOOT_FAIL = 2;
//    private FootViewHolder mFootView;
    //用于判断是否是加载失败时点击的FootView
    public static final String FOOT_VIEW_ID = "-1";

    private Context mContext;

    public static ListChangeFragment newInstance(int position) {
        ListChangeFragment f = new ListChangeFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }

    private BoxAdapter mAdapter;
    private String mUrl = "https://api.douban.com/v2/movie/us_box";
    private BoxInfo mBoxInfo;
    private final OkHttpClient mClient = new OkHttpClient();
    Gson gson = new Gson();
    private CallbackHandler mHandler = new CallbackHandler(this);
    private RecyclerView mMovieRv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.list_layout, container, false);
        mMovieRv = (RecyclerView)rootView.findViewById(R.id.rv_movielist);
        initData();
//        Handler mHandler = new Handler(){
//            @Override
//            public void handleMessage(Message msg) {
//                super.handleMessage(msg);
//            }
//        };
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mMovieRv.setLayoutManager(linearLayoutManager);
        return rootView;
    }

    private void initData() {
        try {
            run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() throws Exception {
        Request request = new Request.Builder()
                .url(mUrl)
                .build();
        final Response mResponse = null;

        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {

                Log.e("aaa",response.body().string().toString());
                String test = "{\"date\":\"6yue1ri\",\"title\":001}";
//                BoxInfo mTest = gson.fromJson(test,BoxInfo.class);
                mBoxInfo = gson.fromJson(test,BoxInfo.class);
                //mBoxInfo = gson.fromJson(response.body().string().toString(),BoxInfo.class);
                Log.e("BOXbbb",mBoxInfo.getTitle());
//                for(int i = 1; i < mListInfo.getTotal() ; i++)
//                {
//
//                    Log.e("Url",mListInfo.getSubjects().get(i).getImages().getMedium());
//                    Log.e("Rating",mListInfo.getSubjects().get(i).getRating().getStars());
//
//                }
                Message msg = Message.obtain();
                msg.what = 1;
                mHandler.sendMessage(msg);
            }
        });
    }

    private static class  CallbackHandler extends Handler{
        private final WeakReference<ListBoxFragment> mFragment;

        public CallbackHandler(ListBoxFragment fragment){
            mFragment = new WeakReference<>(fragment);
        }
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ListBoxFragment mmFragment = mFragment.get();
            switch (msg.what){
                case 1:
                    mmFragment.mAdapter = new BoxAdapter(mmFragment, mmFragment.mBoxInfo.getSubjects());
                    mmFragment.mMovieRv.setAdapter(mmFragment.mAdapter);
            }
        }
    }

//    /**
//     * recyclerView上拉加载更多的footViewHolder
//     */
//    class FootViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//
//        private ProgressBar progress_bar;
//        private TextView text_load_tip;
//
//
//        public FootViewHolder(final View itemView) {
//            super(itemView);
//            progress_bar = (ProgressBar) itemView.findViewById(R.id.pb_view_load_tip);
//            text_load_tip = (TextView) itemView.findViewById(R.id.tv_view_load_tip);
//            itemView.setOnClickListener(this);
//        }
//
//        public void update() {
//            if (isLoadCompleted()) setFootView(FOOT_COMPLETED);
//            else setFootView(FOOT_LOADING);
//        }
//
//        public void setFootView(int event) {
//            ViewGroup.LayoutParams params = itemView.getLayoutParams();
//            switch (event) {
//                case FOOT_LOADING:
//                    params.height = DensityUtil.dp2px(mContext, 40f);
//                    itemView.setLayoutParams(params);
//                    progress_bar.setVisibility(View.VISIBLE);
//                    text_load_tip.setText(mContext.getString(R.string.foot_loading));
//                    itemView.setClickable(false);
//                    break;
//                case FOOT_COMPLETED:
//                    params.height = 0;
//                    itemView.setLayoutParams(params);
//                    itemView.setClickable(false);
//                    break;
//                case FOOT_FAIL:
//                    params.height = DensityUtil.dp2px(mContext, 40f);
//                    itemView.setLayoutParams(params);
//                    progress_bar.setVisibility(View.GONE);
//                    text_load_tip.setText(mContext.getString(R.string.foot_fail));
//                    itemView.setClickable(true);
//            }
//        }
//
//        @Override
//        public void onClick(View view) {
//            if (mCallback != null) {
//                setFootView(FOOT_LOADING);
//                mCallback.onItemClick(FOOT_VIEW_ID, null);
//            }
//        }
//    }
}
