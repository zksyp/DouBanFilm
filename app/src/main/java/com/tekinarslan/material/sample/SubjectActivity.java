package com.tekinarslan.material.sample;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.tekinarslan.material.sample.Info.CelebrityEntity;
import com.tekinarslan.material.sample.Info.MovieBean;
import com.tekinarslan.material.sample.Info.SimpleCardBean;
import com.tekinarslan.material.sample.bean.GetNetBean;
import com.tekinarslan.material.sample.bean.TestBean;
import com.tekinarslan.material.sample.util.Constant;
import com.tekinarslan.material.sample.util.DensityUtil;
import com.tekinarslan.material.sample.util.StringUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by kaishen on 16/7/11.
 */
public class SubjectActivity extends AppCompatActivity
        implements View.OnClickListener, AppBarLayout.OnOffsetChangedListener {

    private int[] cast_id = {R.id.view_cast_layout_1,
            R.id.view_cast_layout_2, R.id.view_cast_layout_3, R.id.view_cast_layout_4,
            R.id.view_cast_layout_5, R.id.view_cast_layout_6};

    private CastViewHolder[] castViewHolders = new CastViewHolder[6];

    private boolean isSummaryShow = false;


    @Bind(R.id.btn_subj_skip)
    FloatingActionButton mBtn;

    @Bind(R.id.header_container_subj)
    AppBarLayout mHeaderContainer;
    @Bind(R.id.toolbar_container_subj)
    CollapsingToolbarLayout mToolBarContainer;
    @Bind(R.id.iv_header_subj)
    ImageView mToolBarImage;
    @Bind(R.id.introduce_container_subj)
    LinearLayout mIntroduceContainer;
    @Bind(R.id.toolbar_subj)
    Toolbar mToolbar;
    @Bind(R.id.iv_subj_images)
    ImageView mImage;
    @Bind(R.id.rb_subj_rating)
    RatingBar mRatingBar;
    @Bind(R.id.tv_subj_rating)
    TextView mRating;
    @Bind(R.id.tv_subj_collect_count)
    TextView mCollect;
    @Bind(R.id.tv_subj_title)
    TextView mTitle;
    @Bind(R.id.tv_subj_original_title)
    TextView mOriginal_title;
    @Bind(R.id.tv_subj_genres)
    TextView mGenres;
    @Bind(R.id.tv_subj_ake)
    TextView mAke;
    @Bind(R.id.tv_subj_countries)
    TextView mCountries;

    @Bind(R.id.film_container_subj)
    LinearLayout mFilmContainer;
    @Bind(R.id.tv_subj_summary)
    TextView mSummaryText;

    @Bind(R.id.tv_subj_recommend_tip)
    TextView mRecommendTip;
    @Bind(R.id.re_subj_recommend)
    RecyclerView mRecommend;

    private String mId;
    private String mContent;
    private MovieBean mMovie = new MovieBean();

    private String mRecommendTags;
    private List<SimpleCardBean> mRecommendData = new ArrayList<>();

    private int mImageWidth;
    private FrameLayout.LayoutParams mIntroduceContainerParams;

    public Handler myHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 1){
                initAfterGetData();
            }

        }
    };

//    private ActionBar.DisplayOptions options = MyApplication.getLoaderOptions();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);
        ButterKnife.bind(this);

        mId = getIntent().getStringExtra("ID");
        httpGetMovieInfo();
        initView();
        initEvent();
    }

    private void httpGetMovieInfo() {
        String url = Constant.API + Constant.SUBJECT + mId;
        final Request request = new Request.Builder()
                .url(url)
                .build();
        new OkHttpClient().newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String res = response.body().string();
            //   Log.e("response", response.body().string());
                mMovie = new Gson().fromJson(res,MovieBean.class);
                Log.e("info", mMovie.getTitle());
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        initAfterGetData();
//                    }
//                });
                Message msg = Message.obtain();
                msg.what = 1;
                myHandler.sendMessage(msg);
            }
        });
    }

    private void initAfterGetData() {
        if (mMovie == null) return;
        Glide.with(SubjectActivity.this)
                .load(mMovie.getImages().getLarge())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(mToolBarImage);

        Glide.with(SubjectActivity.this)
                .load(mMovie.getImages().getLarge())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(mImage);
        if (mMovie.getTitle() != null) {
            mToolBarContainer.setTitle(mMovie.getTitle());
        }

        if (mMovie.getRating() != null) {
            float rate = (float) (mMovie.getRating().getAverage() / 2);
            mRatingBar.setRating(rate);
            mRating.setText(String.format("%s", rate * 2));
        }

        mCollect.setText("(");
        mCollect.append(String.format("%s", mMovie.getCollect_count()));
        mCollect.append("人评价");

        mTitle.setText(String.format("%s   ", mMovie.getTitle()));
        mTitle.append(StringUtil.getSpannableString1(
                String.format("  %s  ", mMovie.getYear()),
                new ForegroundColorSpan(Color.WHITE),
                new BackgroundColorSpan(Color.parseColor("#5ea4ff")),
                new RelativeSizeSpan(0.88f)));

        if (mMovie.getOriginal_title() != mMovie.getTitle()) {
            mOriginal_title.setText(mMovie.getOriginal_title());
            mOriginal_title.setVisibility(View.VISIBLE);
        } else {
            mOriginal_title.setVisibility(View.GONE);
        }
        mGenres.setText(StringUtil.getListString(mMovie.getGenres(), ','));
        mAke.setText(StringUtil.getSpannableString("又名:", Color.GRAY));
        mAke.append(StringUtil.getListString(mMovie.getAka(), '/'));
        mCountries.setText(StringUtil.getSpannableString("制片国家或地区", Color.GRAY));
        mCountries.append(StringUtil.getListString(mMovie.getCountries(), '/'));

        mSummaryText.setText(StringUtil.getSpannableString("简介", Color.parseColor("#5ea4ff")));
        mSummaryText.append(mMovie.getSummary());
        mSummaryText.setEllipsize(TextUtils.TruncateAt.END);
        mSummaryText.setOnClickListener(this);


        //获得导演演员数据列表
        getCastData();

        //显示View并配上动画
        mFilmContainer.setAlpha(0f);
        mFilmContainer.setVisibility(View.VISIBLE);
        mFilmContainer.animate().alpha(1f).setDuration(800);
    }

    //获得导演演员数据
    private void getCastData() {
        boolean isDirWithCast = false;
        for (int i = 0; (i < mMovie.getDirectors().size() && i < 2); i++) {
            CelebrityEntity cel = mMovie.getDirectors().get(i);
            //判断导演是不是主演，如果是打上“导演兼主演”标签
            //为满足一些特殊的数据，需要做null判断
            if (i == 0 && mMovie.getCasts().get(0).getId() != null && cel.getId() != null
                    && cel.getId().equals(mMovie.getCasts().get(0).getId())) {
                isDirWithCast = true;
                castViewHolders[i].bindDataForDir(cel, true);
            } else {
                isDirWithCast = false;
                castViewHolders[i].bindDataForDir(cel, false);
            }
        }

        int j = 2;
        for (int i = 0; i < 4; i++) {
            //判断主演是否是导演，如果是就跳过
            if (i == 0 && isDirWithCast) i++;
            if (i == mMovie.getCasts().size()) break;
            CelebrityEntity cel = mMovie.getCasts().get(i);
            castViewHolders[j++].bindData(cel);
        }

    }

    private void initEvent() {
        mBtn.setOnClickListener(this);
        mHeaderContainer.addOnOffsetChangedListener(this);
    }

    private void initView() {
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.setDisplayHomeAsUpEnabled(true);

        //用于collapsingToolbar缩放时content中内容和图片的动作
        mImageWidth = mImage.getLayoutParams().width + DensityUtil.dp2px(getApplication(), 8f);
        mIntroduceContainerParams =
                (FrameLayout.LayoutParams) mIntroduceContainer.getLayoutParams();

        for (int i = 0; i < 6; i++) {
            View view = findViewById(cast_id[i]);
            castViewHolders[i] = new CastViewHolder(view);
        }
    }

    /**
     * AppBarLayout onOffsetChangeListener
     */
    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                //利用AppBarLayout的回调接口启用或者关闭下拉刷新
//        mRefresh.setEnabled(i == 0);
        //设置AppBarLayout下方内容的滚动效果
        float alpha = Math.abs(verticalOffset) * 1.0f / appBarLayout.getTotalScrollRange();
//        Log.e("alpha",alpha+"");
        changeContentLayout(alpha);

    }

    /**
     * 使content中内容位置和图片透明度随着AppBarLayout的伸缩而改变
     */
    private void changeContentLayout(float alpha) {
        setContentGravity(alpha == 1 ? Gravity.START : Gravity.CENTER_HORIZONTAL);
        mImage.setAlpha(alpha);
        mIntroduceContainerParams.leftMargin = (int) (mImageWidth * alpha);
        mIntroduceContainer.setLayoutParams(mIntroduceContainerParams);
    }

    private void setContentGravity(int gravity) {
        mIntroduceContainer.setGravity(gravity);
        mAke.setGravity(gravity);
        mCountries.setGravity(gravity);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_subj_summary:
                if (isSummaryShow) {
                    isSummaryShow = false;
                    mSummaryText.setEllipsize(TextUtils.TruncateAt.END);
                    mSummaryText.setLines(3);
                } else {
                    isSummaryShow = true;
                    mSummaryText.setEllipsize(null);
                    mSummaryText.setSingleLine(false);
                }
                break;
            case R.id.btn_subj_skip://跳往豆瓣电影的移动版网页
                if (mMovie == null) break;
                Intent intent = new Intent(SubjectActivity.this, MovieWebActivity.class);
                intent.putExtra("URL", mMovie.getMobile_url());
                startActivity(intent);
                break;
            case R.id.tv_subj_recommend_tip:
                break;
        }
    }



    private class CastViewHolder {
        CelebrityEntity mCastData;
        View mCastView;
        ImageView mImage;
        TextView mName;

        CastViewHolder(View castView) {
            mCastView = castView;
            mImage = (ImageView) castView.findViewById(R.id.iv_item_simple_cast_image);
            mName = (TextView) castView.findViewById(R.id.tv_item_simple_cast_text);
            mCastView.setVisibility(View.GONE);
        }

        void setCastData(CelebrityEntity data) {
            mCastData = data;
        }

        void bindData(CelebrityEntity data) {
            setCastData(data);
            mName.setText(data.getName());

            mCastView.setVisibility(View.VISIBLE);
//            mCastView.setOnClickListener(this);

            if (data.getAvatars() == null) return;
            Glide.with(SubjectActivity.this)
                    .load(data.getAvatars().getLarge())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(mImage);
        }

        void bindDataForDir(CelebrityEntity data, boolean isCast) {
            setCastData(data);
            if (isCast) {
                mName.setText(data.getName() + "(导演兼主演)");
            } else {
                mName.setText(data.getName() + "(导演)");
            }

            mCastView.setVisibility(View.VISIBLE);
//            mCastView.setOnClickListener(this);

            if (data.getAvatars() == null) return;

            Glide.with(SubjectActivity.this)
                    .load(data.getAvatars().getLarge())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(mImage);
        }

//        @Override
//        public void onClick(View view) {
//            if (mCastData != null) {
//                if (mCastData.getId() == null) {
//                    Toast.makeText(SubjectActivity.this, "暂无资料", Toast.LENGTH_SHORT).show();
//                } else {
//                    CelebrityActivity.toActivity(
//                            SubjectActivity.this, mCastData.getId());
//                }
//            }
//        }
    }
}
