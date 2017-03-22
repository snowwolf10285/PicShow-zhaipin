package com.liji.imagezoom.util;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.liji.imagezoom.activity.ImagePagerActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：liji on 2016/7/18 11:03
 * 邮箱：lijiwork@sina.com
 */
public class ImageZoom {

    /**
     * 跳转到图片预览页面
     *
     * @param context
     * @param positon 图片显示的页码
     * @param list    图片URL
     */
    public static void show(Context context, int positon, List<String> list) {
        Intent intent = new Intent(context, ImagePagerActivity.class);
        // 图片url,为了演示这里使用常量，一般从数据库中或网络中获取
        intent.putStringArrayListExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, (ArrayList<String>) list);
        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, positon);
        context.startActivity(intent);
    }

    /**
     * 跳转到图片预览页面
     *
     * @param context
     * @param url     当前图片url
     * @param list    图片URL
     */
    public static void show(Context context, String url, List<String> list) {
        try {
            int positon = list.indexOf(url);
            Intent intent = new Intent(context, ImagePagerActivity.class);
            // 图片url,为了演示这里使用常量，一般从数据库中或网络中获取
            intent.putStringArrayListExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, (ArrayList<String>) list);
            intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, positon);
            context.startActivity(intent);
        } catch (Exception e) {
            Log.e("imagezoom", e.getMessage());
        }
    }

}
