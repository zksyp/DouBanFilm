package com.tekinarslan.material.sample.Info;

import java.util.List;

/**
 * Created by kaishen on 16/6/14.
 */
public class MovieInfo {

    private String title;
    private String id;
    private ImageInfo images;
    private RatingInfo rating;
    private List<String> genres;
    private String original_title;
    private List<CelebrityEntity>directors;
    private List<CelebrityEntity>casts;
    private int collect_count;
    private String alt;
    private String mobile_url;

    public String getTitle()
    {
        return this.title ;
    }

    public String getId()
    {
        return this.id;
    }

    public ImageInfo getImages() {
        return images;
    }

    public RatingInfo getRating() {
        return rating;
    }

    public void setTitle(String mTitle)
    {
        this.title = mTitle;
    }

    public void setId(String mId)
    {
        this.id = mId;
    }

    public void setImages(ImageInfo images) {
        this.images = images;
    }

    public void setRating(RatingInfo rating) {
        this.rating = rating;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> geners) {
        this.genres = geners;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public List<CelebrityEntity> getDirectors() {
        return directors;
    }

    public void setDirectors(List<CelebrityEntity> directors) {
        this.directors = directors;
    }

    public List<CelebrityEntity> getCasts() {
        return casts;
    }

    public void setCasts(List<CelebrityEntity> casts) {
        this.casts = casts;
    }

    public int getCount() {
        return collect_count;
    }

    public void setCount(int collect_count) {
        this.collect_count = collect_count;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public void setMobile_url(String mobile_url) {
        this.mobile_url = mobile_url;
    }

    public String getMobile_url() {
        return mobile_url;
    }
}
