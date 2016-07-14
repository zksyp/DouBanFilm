package com.tekinarslan.material.sample;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


public class SampleActivity extends ActionBarActivity {

    private DrawerLayout mDrawerLayout;//侧滑界面定义
    private ActionBarDrawerToggle drawerToggle;//侧滑界面开关


    private ListView mOptionList;
    private ImageView mUser;
    private TextView mName;
    private TextView mUserInfo;
    private ImageView mEdit;
    ViewPager pager;
    private String[] titles = new String[]{"    正在热映    ", "    即将上映    ", "    北美票房    ", "    TOP250    "};
    private Toolbar toolbar;

    SlidingTabLayout slidingTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        initView();
        initData();
        initEvent();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(Gravity.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    public void initView()
    {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mUser = (ImageView)findViewById(R.id.iv_view_nav_header);
        mName = (TextView)findViewById(R.id.tv_view_nav_name);
        mUserInfo = (TextView)findViewById(R.id.tv_view_nav_intro);
        mEdit = (ImageView)findViewById(R.id.iv_view_nav_edit);
        mOptionList = (ListView) findViewById(R.id.lv_option);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        pager = (ViewPager) findViewById(R.id.viewpager);
        slidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);

    }

    public void initData()
    {
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setNavigationIcon(R.drawable.ic_ab_drawer);
        }

        pager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), titles));
        slidingTabLayout.setViewPager(pager);
        slidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return Color.WHITE;
            }
        });
        drawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.app_name, R.string.app_name);
        mDrawerLayout.setDrawerListener(drawerToggle);
        String[] values = new String[]{
                "首页", "收藏", "系统设置"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);
        mOptionList.setAdapter(adapter);
        mName.setText("KaiShen");
        mUserInfo.setText("用户介绍");
        mEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(SampleActivity.this,EditActivity.class);
                startActivity(intent);
            }
        });

    }

    public void initEvent()
    {
        mOptionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                switch (position) {
                    case 0:
                        mDrawerLayout.closeDrawer(Gravity.START);
                        break;
                    case 1:
                        Intent intent = new Intent();
                        intent.setClass(SampleActivity.this,CollectionActivtiy.class);
                        startActivity(intent);
                        break;
                    case 2:
                        Intent intent1 = new Intent();
                        intent1.setClass(SampleActivity.this,OptionActivity.class);
                        startActivity(intent1);
                        break;
                }

            }
        });

    }



}
