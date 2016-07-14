package com.tekinarslan.material.sample.Info;

import java.util.List;

/**
 * Created by kaishen on 16/6/14.
 */
public class BoxBean {

    private String date;
    private List<RankBean> subjects;
    private String title;
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<RankBean> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<RankBean> rankBeen) {
        this.subjects = rankBeen;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
