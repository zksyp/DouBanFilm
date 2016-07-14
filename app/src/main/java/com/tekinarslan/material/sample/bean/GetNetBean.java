package com.tekinarslan.material.sample.bean;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;
import com.tekinarslan.material.sample.Info.BoxBean;
import com.tekinarslan.material.sample.Info.ListBean;
import com.tekinarslan.material.sample.Info.MovieBean;
import com.tekinarslan.material.sample.util.Constant;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by kaishen on 16/6/15.
 */
public class GetNetBean {

    private final OkHttpClient mClient = new OkHttpClient();
    private static final int IN_THEATERS = 0;
    private static final int COMING_SOON = 1;
    private static final int US_BOX = 2;
    private static final int TOP_250 = 3;

    private String url;
    private Handler mHandler;
    private int infoType;


    public void GetType(int type) {

        this.infoType = type;

        try {
            run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setHandler(Handler mHandler) {
        this.mHandler = mHandler;
    }

    public void run() throws Exception {

        switch (infoType){
            case IN_THEATERS:
                url = Constant.API + Constant.IN_THEATERS;
                break;
            case COMING_SOON:
                url = Constant.API + Constant.COMING;
                break;
            case US_BOX:
                url = Constant.API + Constant.US_BOX;
                break;
            case TOP_250:
                url = Constant.API + Constant.TOP250;
                break;
            default:
                break;
        }

        Request request = new Request.Builder()
                .url(url)
                .build();
        mClient.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                switch (infoType){
                    case IN_THEATERS:
                        Message msg = Message.obtain();
                        msg.obj = response.body().string();
                        msg.what = IN_THEATERS;
                        mHandler.sendMessage(msg);
                        break;
                    case COMING_SOON:
                        msg = Message.obtain();
                        msg.obj = response.body().string();
                        msg.what = COMING_SOON;
                        mHandler.sendMessage(msg);
                        break;
                    case US_BOX:
                        msg = Message.obtain();
                        msg.obj = response.body().string();
                        msg.what = US_BOX;
                        mHandler.sendMessage(msg);
                        break;
                    case TOP_250:
                        msg = Message.obtain();
                        msg.obj = response.body().string();
                        msg.what = TOP_250;
                        mHandler.sendMessage(msg);
                        break;
                    default:
                        break;
                }
            }
        });
    }
}



