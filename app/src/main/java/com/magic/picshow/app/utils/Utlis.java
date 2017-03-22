package com.magic.picshow.app.utils;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import common.WEApplication;

/**
 * Created by snowwolf on 17/2/28.
 * 通用工具类
 */

public class Utlis {
    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public static String getVersion() {
        try {
            PackageManager manager = WEApplication.getContext().getPackageManager();
            PackageInfo info = manager.getPackageInfo(WEApplication.getContext().getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
