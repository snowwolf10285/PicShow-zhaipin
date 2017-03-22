package com.magic.picshow.mvp.model.entity;

/**
 * Created by snowwolf on 17/2/22.
 */

public class UpdateInfo {
    private String apk_url;
//    private String versionName;
    private String apk_num;

    private String start_time;
    private String sign;
    private int id;

    public String getApkUrl() {
        return apk_url;
    }

    public void setApkUrl(String apkUrl) {
        this.apk_url = apk_url;
    }

//    public String getVersionName() {
//        return versionName;
//    }
//
//    public void setVersionName(String versionName) {
//        this.versionName = versionName;
//    }

    public String getApk_num() {
        return apk_num;
    }

    public void setApk_num(String apk_num) {
        this.apk_num = apk_num;
    }

    public String getApk_url() {
        return apk_url;
    }

    public void setApk_url(String apk_url) {
        this.apk_url = apk_url;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "UpdateInfo{" +
                "apk_url='" + apk_url + '\'' +
                ", apk_num='" + apk_num + '\'' +
                ", start_time='" + start_time + '\'' +
                ", sign='" + sign + '\'' +
                ", id=" + id +
                '}';
    }
}
