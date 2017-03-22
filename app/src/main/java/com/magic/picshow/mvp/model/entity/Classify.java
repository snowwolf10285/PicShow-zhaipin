package com.magic.picshow.mvp.model.entity;

/**
 * Created by snowwolf on 17/1/22.
 * 分类
 */

public class Classify {

    private final int classify_id;

    private final String classify_name;

    public Classify(int classify_id,String classify_name){
        this.classify_id = classify_id;
        this.classify_name = classify_name;
    }

    public int getClassify_id() {
        return classify_id;
    }

    public String getClassify_name() {
        return classify_name;
    }

    @Override
    public String toString() {
        return "Classify{" +
                "classify_id=" + classify_id +
                ", classify_name='" + classify_name + '\'' +
                '}';
    }
}
