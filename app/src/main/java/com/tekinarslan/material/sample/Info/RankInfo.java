package com.tekinarslan.material.sample.Info;

import com.tekinarslan.material.sample.Info.MovieInfo;

/**
 * Created by kaishen on 16/6/14.
 */
public class RankInfo {

    private int box;
    private int rank;
    private boolean isNew ;
    private MovieInfo subject;

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

    public MovieInfo getSubject() {
        return subject;
    }

    public void setSubject(MovieInfo subject) {
        this.subject = subject;
    }
}
