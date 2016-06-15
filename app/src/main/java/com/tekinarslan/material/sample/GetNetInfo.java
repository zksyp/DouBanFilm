package com.tekinarslan.material.sample;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.google.gson.Gson;
import com.tekinarslan.material.sample.Info.ListInfo;
import com.tekinarslan.material.sample.Info.MovieInfo;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.List;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.http.Url;

/**
 * Created by kaishen on 16/6/15.
 */
public class GetNetInfo {

    private ListInfo mListInfo = new ListInfo();
    private final OkHttpClient mClient = new OkHttpClient();
    Gson gson = new Gson();
    private Context context;
    private String url;
    private Handler mHandler;
    private boolean isList;
    private MovieInfo mMovieInfo = new MovieInfo();
//    private CallbackHandler mHandler = new CallbackHandler(this);


    public void GetNetInfo( boolean isList) {

        this.isList = isList;

        try {
            run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setmHandler(Handler mHandler) {
        this.mHandler = mHandler;
    }

    public String getUrl() {
        return url;
    }

    public Handler getmHandler() {
        return mHandler;
    }

    public void run() throws Exception {
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            mClient.newCall(request).enqueue(new okhttp3.Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }
                @Override
                public void onResponse(Call call, Response response) throws IOException {

//                String json ="{\"total\":100}";
//                mListInfo = gson.fromJson(json,ListInfo.class);
//                Log.e("aaa",response.body().string().toString());
                    if(isList){
                        mListInfo = gson.fromJson(response.body().string().toString(),ListInfo.class);
                        Message msg = Message.obtain();
                        msg.obj = mListInfo;
                        mHandler.sendMessage(msg);

                    }else {
                        mMovieInfo = gson.fromJson(response.body().string().toString(),MovieInfo.class);
                        Message msg = Message.obtain();
                        msg.obj = mMovieInfo;
                        mHandler.sendMessage(msg);
                    }
//                Log.e("bbb","" + mListInfo.getTotal());
//                for(int i = 1; i < mListInfo.getTotal() ; i++)
//                {
//
//                    Log.e("Url",mListInfo.getSubjects().get(i).getImages().getMedium());
//                    Log.e("Rating",mListInfo.getSubjects().get(i).getRating().getStars());
//
//                }
//                    Message msg = Message.obtain();
//                    msg.what = 1;
//                    mHandler.sendMessage(msg);
                }
            });
    }


//    private class  CallbackHandler extends Handler {
//        private final WeakReference<Context> mContext;
//
//        public CallbackHandler(Context mMcontext){
//            mContext = new WeakReference<>(mMcontext);
//        }
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            Context mmContext = mContext.get();
//            switch (msg.what){
//                case 1:
//
//            }
//        }
//    }
}



