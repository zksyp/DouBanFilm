package com.tekinarslan.material.sample;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.internal.bind.util.ISO8601Utils;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import com.jude.rollviewpager.Util;
import com.tekinarslan.material.sample.Info.ListInfo;
import com.tekinarslan.material.sample.Info.MovieInfo;

import java.io.IOException;
import java.lang.ref.WeakReference;

import javax.security.auth.callback.CallbackHandler;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by kaishen on 16/6/14.
 */
public class ListChangeFragment extends Fragment{

    private static final String ARG_POSITION = "position";

    private int position;

    private  boolean isComingFilm;

    public static ListChangeFragment newInstance(int position) {

        ListChangeFragment f = new ListChangeFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }

    private RecyclerView mMovieRv;
    private GeneralAdapter mAdapter;
    private FloatingActionButton mBtn;
    private SwipeRefreshLayout mSwipeLayout;
    private String mUrl;
    private ListInfo mListInfo = new ListInfo();
    Gson gson = new Gson();
    public Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mListInfo = (ListInfo) msg.obj;
            mAdapter = new GeneralAdapter(getActivity(), mListInfo.getSubjects(), isComingFilm);
            mMovieRv.setAdapter(mAdapter);
        }
    };
    private RecyclerView.OnScrollListener mScrollListener;
    private GetNetInfo mGet = new GetNetInfo();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        position = getArguments().getInt(ARG_POSITION);
        View rootView = inflater.inflate(R.layout.list_layout, container, false);
        mMovieRv = (RecyclerView)rootView.findViewById(R.id.rv_movielist);
        mSwipeLayout = (SwipeRefreshLayout)rootView.findViewById(R.id.srl_movielist);
        switch (position) {
            case 0:
                mUrl = "https://api.douban.com/v2/movie/in_theaters";
                isComingFilm = false;
                break;
            case 1:
                mUrl = "https://api.douban.com/v2/movie/coming_soon";
                isComingFilm = true;
                break;
            case 3:
                mUrl = "https://api.douban.com/v2/movie/top250";
                isComingFilm = false;
                break;
        }
        initData();
        initEvent();
//        Log.e("URL",mUrl);
//        Handler mHandler = new Handler(){
//            @Override
//            public void handleMessage(Message msg) {
//                super.handleMessage(msg);
//            }
//        };
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mMovieRv.setLayoutManager(linearLayoutManager);
        mMovieRv.addItemDecoration(new DividerItemDecoration(getActivity(),linearLayoutManager.getOrientation()));
//        DividerDecoration itemDecoration = new DividerDecoration(Color.argb(255,0,150,136), Util.dip2px(getActivity(),0.5f),
//                Util.dip2px(getActivity(),72),0);
//        itemDecoration.setDrawLastItem(false);
//        mMovieRv.addItemDecoration(itemDecoration);

        // 设置下拉圆圈上的颜色，蓝色、绿色、橙色、红色
        mSwipeLayout.setColorSchemeResources(android.R.color.black, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        mSwipeLayout.setDistanceToTriggerSync(400);// 设置手指在屏幕下拉多少距离会触发下拉刷新
        mSwipeLayout.setProgressBackgroundColor(R.color.BackGreen); // 设定下拉圆圈的背景
        mSwipeLayout.setSize(SwipeRefreshLayout.DEFAULT); // 设置圆圈的大小
        return rootView;
    }

    private void initData() {

        mGet.setUrl(mUrl);
        mGet.setmHandler(handler);
        mGet.GetNetInfo(true);
    }

    /*
     * 监听器SwipeRefreshLayout.OnRefreshListener中的方法，当下拉刷新后触发
     */

    private void initEvent() {
        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        // 停止刷新
                        mSwipeLayout.setRefreshing(false);
                    }
                }, 5000); // 5秒后发送消息，停止刷新

            }
        });
    }
}

