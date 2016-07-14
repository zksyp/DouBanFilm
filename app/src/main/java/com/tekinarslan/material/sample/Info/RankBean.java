package com.tekinarslan.material.sample.Info;

import com.google.gson.annotations.SerializedName;

/**
 * Created by kaishen on 16/6/14.
 */
public class RankBean {

    private int box;
    private int rank;
    @SerializedName("new")
    private boolean isNew ;
    private MovieBean subject;

    public int getBox() {
        return box;
    }

    public void setBox(int box) {
        this.box = box;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public boolean getNew() {
        return isNew;
    }

    public void setNew(boolean isNew) {
        this.isNew = isNew;
    }

    public MovieBean getSubject() {
        return subject;
    }

    public void setSubject(MovieBean subject) {
        this.subject = subject;
    }
}
