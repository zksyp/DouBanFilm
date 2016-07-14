package com.tekinarslan.material.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

/**
 * Created by kaishen on 16/6/15.
 */
public class EditActivity extends AppCompatActivity {
    private TextView mTextView;
    private Toolbar mTbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);
        mTextView = (TextView)findViewById(R.id.tv_option);
        mTbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(mTbar);
        //mToolbar.setNavigationIcon(R.drawable.ic_ab_drawer);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mTbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTextView.setText("这是用户信息修改页面");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            Intent intent = new Intent();
            intent.setClass(this,SampleActivity.class);
            startActivity(intent);
            this.finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
