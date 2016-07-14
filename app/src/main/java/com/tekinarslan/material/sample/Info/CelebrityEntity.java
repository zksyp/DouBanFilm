package com.tekinarslan.material.sample.Info;

/**
 * Created by kaishen on 16/6/14.
 */
public class CelebrityEntity {

    private ImagesEntity avatars;
    private String alt;
    private String id;
    private String name;

    public void setAvatars(ImagesEntity avatars) {
        this.avatars = avatars;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ImagesEntity getAvatars() {
        return avatars;
    }

    public String getAlt() {
        return alt;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
