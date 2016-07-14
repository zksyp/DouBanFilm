package com.tekinarslan.material.sample.Info;

import java.util.List;

/**
 * Created by kaishen on 16/6/14.
 */
public class ListBean {

    private int total;
    private List<MovieBean> subjects;

    public int getTotal() {
        return this.total;
    }

    public void setTotal(int mTotal) {
        this.total = mTotal;
    }

    public List<MovieBean> getSubjects() {
        return this.subjects;
    }

    public void setSubjects(List<MovieBean> mSubjects) {
        this.subjects = mSubjects;
    }
}
