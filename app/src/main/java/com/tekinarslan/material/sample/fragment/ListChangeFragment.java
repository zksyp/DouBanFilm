package com.tekinarslan.material.sample.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.tekinarslan.material.sample.BoxAdapter;
import com.tekinarslan.material.sample.FloatingActionButton;
import com.tekinarslan.material.sample.Info.BoxBean;
import com.tekinarslan.material.sample.Info.ListBean;
import com.tekinarslan.material.sample.Info.RankBean;
import com.tekinarslan.material.sample.R;
import com.tekinarslan.material.sample.DividerItemDecoration;
import com.tekinarslan.material.sample.bean.GetNetBean;

import java.util.List;

/**
 * Created by kaishen on 16/6/14.
 */
public class ListChangeFragment extends Fragment{

    private static final String ARG_POSITION = "position";
    private static final String LAST_RECORD = "last record";
    private static final String[] TYPE = {"in theaters", "coming", "us box","top 250"};


    private static final int IN_THEATERS = 0;
    private static final int COMING_SOON = 1;
    private static final int US_BOX = 2;
    private static final int TOP_250= 3;

    private int position;

    private boolean isComing = false;

    public static ListChangeFragment newInstance(int position) {

        ListChangeFragment f = new ListChangeFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }

    private RecyclerView mMovieRv;
    private TextView mDateTxt;
    private ImageView mLine;
    private FloatingActionButton mBtn;
    private SwipeRefreshLayout mSwipeLayout;

    private GeneralAdapter mAdapter;
    private BoxAdapter mBoxAdapter;

    private ListBean mListBean = new ListBean();
    private BoxBean mBoxBean = new BoxBean();
    public Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mDataString = (String) msg.obj;
            saveRecord(msg.what);
            if(msg.what == 2){
                mBoxAdapter = new BoxAdapter(getContext(), mBoxBean.getSubjects());
                mMovieRv.setAdapter(mBoxAdapter);
                mDateTxt.setText(mBoxBean.getDate());
            }else
            {
                mAdapter = new GeneralAdapter(getContext(), mListBean.getSubjects(), isComing);
                mMovieRv.setAdapter(mAdapter);
            }
        }
    };
    private RecyclerView.OnScrollListener mScrollListener;
    private GetNetBean mGet = new GetNetBean();

    private SharedPreferences mSharedPreferences;
    private int type;
    private String mDataString = null;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        position = getArguments().getInt(ARG_POSITION);
        View rootView = inflater.inflate(R.layout.list_layout, container, false);
        mMovieRv = (RecyclerView)rootView.findViewById(R.id.rv_movie_list);
        mDateTxt = (TextView)rootView.findViewById(R.id.tv_box_date);
        mLine = (ImageView)rootView.findViewById(R.id.iv_line);
        mSwipeLayout = (SwipeRefreshLayout)rootView.findViewById(R.id.srl_movielist);
        mSharedPreferences = getActivity().getSharedPreferences(LAST_RECORD, Context.MODE_PRIVATE);
        initData();
        initEvent();
        return rootView;
    }

    private void initData() {
        switch (position) {
            case 0:
                type = IN_THEATERS;
                initMovieRecycleView();
                isComing = false;
                break;
            case 1:
                type = COMING_SOON;
                initMovieRecycleView();
                isComing = true;
                break;
            case 2:
                type = US_BOX;
                initBoxRecycleView();
                isComing = false;
                break;
            case 3:
                type = TOP_250;
                initMovieRecycleView();
                isComing = false;
                break;
        }


    }


    private void initBoxRecycleView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mMovieRv.setLayoutManager(linearLayoutManager);
//        mMovieRv.addItemDecoration(new DividerItemDecoration(getContext(),linearLayoutManager.getOrientation()));
//        DividerDecoration itemDecoration = new DividerDecoration(Color.argb(255,0,150,136), Util.dip2px(getActivity(),0.5f),
//                Util.dip2px(getActivity(),72),0);
//        itemDecoration.setDrawLastItem(false);
//        mMovieRv.addItemDecoration(itemDecoration);

        //设置下拉圆圈上的颜色，蓝色、绿色、橙色、红色
        mSwipeLayout.setColorSchemeResources(android.R.color.black, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        mSwipeLayout.setDistanceToTriggerSync(400);// 设置手指在屏幕下拉多少距离会触发下拉刷新
        mSwipeLayout.setProgressBackgroundColor(R.color.BackGreen); // 设定下拉圆圈的背景
        mSwipeLayout.setSize(SwipeRefreshLayout.DEFAULT); // 设置圆圈的大小
        mDateTxt.setVisibility(View.VISIBLE);
        mLine.setVisibility(View.VISIBLE);
        if(getRecord() != null){
            mBoxBean = new Gson().fromJson(getRecord(), BoxBean.class);
            mDateTxt.setText(mBoxBean.getDate());
            mDateTxt.append("榜单");
            mBoxAdapter = new BoxAdapter(getContext(), mBoxBean.getSubjects());
            mMovieRv.setAdapter(mBoxAdapter);
        }
        else
        {
            mGet.setHandler(handler);
            mGet.GetType(type);
        }
    }

    private void initMovieRecycleView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mMovieRv.setLayoutManager(linearLayoutManager);
//        mMovieRv.addItemDecoration(new DividerItemDecoration(getContext(),linearLayoutManager.getOrientation()));
//        DividerDecoration itemDecoration = new DividerDecoration(Color.argb(255,0,150,136), Util.dip2px(getActivity(),0.5f),
//                Util.dip2px(getActivity(),72),0);
//        itemDecoration.setDrawLastItem(false);
//        mMovieRv.addItemDecoration(itemDecoration);

        //设置下拉圆圈上的颜色，蓝色、绿色、橙色、红色
        mSwipeLayout.setColorSchemeResources(android.R.color.black, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        mSwipeLayout.setDistanceToTriggerSync(400);// 设置手指在屏幕下拉多少距离会触发下拉刷新
        mSwipeLayout.setProgressBackgroundColor(R.color.BackGreen); // 设定下拉圆圈的背景
        mSwipeLayout.setSize(SwipeRefreshLayout.DEFAULT); // 设置圆圈的大小
        if(getRecord() != null){
            mListBean = new Gson().fromJson(getRecord(), ListBean.class);
            mAdapter = new GeneralAdapter(getContext(), mListBean.getSubjects(), isComing);
            mMovieRv.setAdapter(mAdapter);
        }
        else
        {
            mGet.setHandler(handler);
            mGet.GetType(type);
        }

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
                }, 3000); // 3秒后发送消息，停止刷新

            }
        });
    }

    private String getRecord(){
        return mSharedPreferences.getString(TYPE[type], null);

    }

    public void saveRecord(int what){
        if(mDataString != null){
            SharedPreferences.Editor editor = mSharedPreferences.edit();
            editor.putString(TYPE[what], mDataString);
            editor.apply();
        }
    }
}

