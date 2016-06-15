package com.tekinarslan.material.sample.Info;

import java.util.List;

/**
 * Created by kaishen on 16/6/14.
 */
public class ListInfo {

    private int total;
    private List<MovieInfo> subjects;

    public int getTotal() {
        return this.total;
    }

    public void setTotal(int mTotal) {
        this.total = mTotal;
    }

    public List<MovieInfo> getSubjects() {
        return this.subjects;
    }

    public void setSubjects(List<MovieInfo> mSubjects) {
        this.subjects = mSubjects;
    }
}
