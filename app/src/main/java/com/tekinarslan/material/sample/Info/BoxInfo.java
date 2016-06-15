package com.tekinarslan.material.sample.Info;

import java.util.List;

/**
 * Created by kaishen on 16/6/14.
 */
public class BoxInfo {

    private String date;
    private List<RankInfo> subjects;
    private String title;
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<RankInfo> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<RankInfo> rankInfos) {
        this.subjects = rankInfos;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
