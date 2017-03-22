package com.magic.picshow.app.utils;

import android.content.Context;
import android.content.SharedPreferences;

import common.WEApplication;


/**
 * xml存储的方法封装类
 * Created by yangfan on 2016/1/4.
 */
public class SharedPreferencesUtils {

    /**
     * 指定xml名称的String存储
     *
     * @param context 存储的上下文
     * @param name xml名称
     * @param key 存储的key
     * @param value 存储的值
     */
    public static void writeXmlString(Context context, String name, String key, String value) {
        SharedPreferences mSettinsSP = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor mSettinsEd = mSettinsSP.edit();
        mSettinsEd.putString(key, value);
        mSettinsEd.commit();
    }

    /**
     *指定xml名称的String读取
     *
     * @param context 存储的上下文
     * @param name xml名称
     * @param key 存储的key
     * @return
     */
    public static String readXmlString(Context context, String name, String key) {
        SharedPreferences mSettinsSP = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor mSettinsEd = mSettinsSP.edit();
        return mSettinsSP.getString(key, "");
    }


    /**
     * 指定xml名称的String存储
     *
     * @param name xml名称
     * @param key 存储的key
     * @param value 存储的值
     */
    public static void writeXmlString(String name, String key, String value) {
        writeXmlString(WEApplication.getContext(),  name,  key,  value);
    }

    /**
     *指定xml名称的String读取
     *
     * @param name xml名称
     * @param key 存储的key
     * @return
     */
    public static String readXmlString( String name, String key) {
        SharedPreferences mSettinsSP =  WEApplication.getContext().getSharedPreferences(name, Context.MODE_PRIVATE);
        return mSettinsSP.getString(key, "");
    }

    /**
     * 不指定xml名称的String存储
     *
     * @param context 存储的上下文
     * @param key 存储的key
     * @param value 存储的值
     */
    public static void writeXmlString(Context context, String key, String value) {
        writeXmlString(context,  context.getPackageName(),  key,  value);
    }

    /**
     *不指定xml名称的String读取
     *
     * @param context 存储的上下文
     * @param key 存储的key
     * @return
     */
    public static String readXmlString(Context context, String key) {
        SharedPreferences mSettinsSP = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        return mSettinsSP.getString(key, "");
    }

    /**
     * 不指定xml名称，默认全局存储的String
     *
     * @param key 存储的key
     * @param value 存储的值
     */
    public static void writeXmlString(String key, String value) {
        SharedPreferences mSettinsSP = WEApplication.getContext().getSharedPreferences(WEApplication.getContext().getPackageName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor mSettinsEd = mSettinsSP.edit();
        mSettinsEd.putString(key, value);
        mSettinsEd.commit();
    }

    /**
     *不指定xml名称，默认全局存储的String读取
     *
     * @param key 存储的key
     * @return
     */
    public static String readXmlString(String key) {
        SharedPreferences mSettinsSP = WEApplication.getContext().getSharedPreferences(WEApplication.getContext().getPackageName(), Context.MODE_PRIVATE);

        return mSettinsSP.getString(key, "");
    }

    /**
     *
     * @param context 存储的上下文
     * @param name xml名称
     * @param key 存储的key
     * @param value 存储的值
     */
    public static void writeXmlInt(Context context, String name, String key, int value) {
        SharedPreferences mSettinsSP = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor mSettinsEd = mSettinsSP.edit();
        mSettinsEd.putInt(key, value);
        mSettinsEd.commit();
    }

    /**
     *
     * @param name xml名称
     * @param key 存储的key
     * @param value 存储的值
     */
    public static void writeXmlInt(String name, String key, int value) {
        writeXmlInt(WEApplication.getContext(),name,key,value);
    }

    /**
     *
     * @param context
     * @param name xml名称
     * @param key
     * @return
     */
    public static int readXmlInt(Context context, String name, String key) {
        SharedPreferences mSettinsSP = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        return mSettinsSP.getInt(key, 0);
    }

    /**
     *
     * @param name xml名称
     * @param key
     * @return
     */
    public static int readXmlInt(String name, String key) {
        SharedPreferences mSettinsSP = WEApplication.getContext().getSharedPreferences(name, Context.MODE_PRIVATE);
        return mSettinsSP.getInt(key, 0);
    }

    /**
     *
     * @param context
     * @param key
     * @param value
     */
    public static void writeXmlInt(Context context, String key, int value) {
        SharedPreferences mSettinsSP = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor mSettinsEd = mSettinsSP.edit();
        mSettinsEd.putInt(key, value);
        mSettinsEd.commit();
    }

    /**
     *
     * @param context
     * @param key
     * @return
     */
    public static int readXmlInt(Context context, String key) {
        SharedPreferences mSettinsSP = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        return mSettinsSP.getInt(key, 0);
    }

    /**
     *
     * @param key
     * @param value
     */
    public static void writeXmlInt(String key, int value) {
        SharedPreferences mSettinsSP = WEApplication.getContext().getSharedPreferences(WEApplication.getContext().getPackageName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor mSettinsEd = mSettinsSP.edit();
        mSettinsEd.putInt(key, value);
        mSettinsEd.commit();
    }

    /**
     *
     * @param key
     * @return
     */
    public static int readXmlInt(String key) {
        SharedPreferences mSettinsSP = WEApplication.getContext().getSharedPreferences(WEApplication.getContext().getPackageName(), Context.MODE_PRIVATE);
        return mSettinsSP.getInt(key, 0);
    }

    /**
     *
     * @param context
     * @param name xml名称
     * @param key
     * @param value
     */
    public static void writeXmlBoolean(Context context, String name, String key, Boolean value) {
        SharedPreferences mSettinsSP = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor mSettinsEd = mSettinsSP.edit();
        mSettinsEd.putBoolean(key, value);
        mSettinsEd.commit();
    }

    /**
     *
     * @param context
     * @param name xml名称
     * @param key
     * @return
     */
    public static Boolean readXmlBoolean(Context context, String name, String key) {
        SharedPreferences mSettinsSP = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        return mSettinsSP.getBoolean(key, false);
    }

    /**
     *
     * @param name xml名称
     * @param key
     * @param value
     */
    public static void writeXmlBoolean( String name, String key, Boolean value) {
        SharedPreferences mSettinsSP = WEApplication.getContext().getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor mSettinsEd = mSettinsSP.edit();
        mSettinsEd.putBoolean(key, value);
        mSettinsEd.commit();
    }

    /**
     *
     * @param name xml名称
     * @param key
     * @return
     */
    public static Boolean readXmlBoolean( String name, String key) {
        SharedPreferences mSettinsSP = WEApplication.getContext().getSharedPreferences(name, Context.MODE_PRIVATE);
        return mSettinsSP.getBoolean(key, false);
    }

    /**
     *
     * @param context
     * @param key
     * @param value
     */
    public static void writeXmlBoolean(Context context, String key, Boolean value) {
        SharedPreferences mSettinsSP = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor mSettinsEd = mSettinsSP.edit();
        mSettinsEd.putBoolean(key, value);
        mSettinsEd.commit();
    }

    /**
     *
     * @param context
     * @param key
     * @return
     */
    public static Boolean readXmlBoolean(Context context, String key) {
        SharedPreferences mSettinsSP = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        return mSettinsSP.getBoolean(key, false);
    }

    /**
     *
     * @param key
     * @param value
     */
    public static void writeXmlBoolean(String key, Boolean value) {
        SharedPreferences mSettinsSP = WEApplication.getContext().getSharedPreferences(WEApplication.getContext().getPackageName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor mSettinsEd = mSettinsSP.edit();
        mSettinsEd.putBoolean(key, value);
        mSettinsEd.commit();
    }

    /**
     *
     * @param key
     * @return
     */
    public static Boolean readXmlBoolean(String key) {
        SharedPreferences mSettinsSP = WEApplication.getContext().getSharedPreferences(WEApplication.getContext().getPackageName(), Context.MODE_PRIVATE);
        return mSettinsSP.getBoolean(key, false);
    }

    /**
     * 清除默认 xml 数据
     */
    public static void clearXml(){
        clearXml(WEApplication.getContext().getPackageName());
    }

    /**
     * 清除默认 xml 数据
     */
    public static void clearXml(String name){
        SharedPreferences mSettinsSP = WEApplication.getContext().getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor mSettinsEd = mSettinsSP.edit();
        mSettinsEd.clear();
        mSettinsEd.commit();
    }


}
