package com.tekinarslan.material.sample.util;

import com.google.gson.reflect.TypeToken;
import com.tekinarslan.material.sample.Info.CelebrityBean;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by kaishen on 16/7/12.
 */
/**
 * 记录项目常量
 */
public class Constant {

    public static final String API = "http://api.douban.com";
    public static final String IN_THEATERS = "/v2/movie/in_theaters";
    public static final String US_BOX = "/v2/movie/us_box";
    public static final String COMING = "/v2/movie/coming_soon";
    public static final String TOP250 = "/v2/movie/top250";
    public static final String SUBJECT = "/v2/movie/subject/";
    public static final String CELEBRITY = "/v2/movie/celebrity/";
    public static final String SEARCH_Q = "/v2/movie/search?q=";
    public static final String SEARCH_TAG = "/v2/movie/search?tag=";

//    public static final Type subType = new TypeToken<SubjectBean>() {
//    }.getType();
//    public static final Type cleType = new TypeToken<CelebrityBean>() {
//    }.getType();
//    public static final Type simpleSubTypeList = new TypeToken<List<SimpleSubjectBean>>() {
//    }.getType();
//    public static final Type simpleBoxTypeList = new TypeToken<List<BoxSubjectBean>>() {
//    }.getType();
}
