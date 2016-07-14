package com.tekinarslan.material.sample.util;

import com.tekinarslan.material.sample.Info.CelebrityEntity;

import java.util.List;

/**
 * Created by kaishen on 16/6/14.
 */
public class CelebrityUtil {

    public static String list2String(List<CelebrityEntity> entities, char s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < entities.size(); i++) {
            sb.append(i == 0 ? "" : s).append(entities.get(i).getName());
        }
        return sb.toString();
    }
}
