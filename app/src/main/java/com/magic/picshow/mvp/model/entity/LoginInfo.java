package com.magic.picshow.mvp.model.entity;

import com.magic.picshow.app.utils.SharedPreferencesUtils;

/**
 * Created by snowwolf on 17/2/8.
 */

public class LoginInfo {
    private final String TAG = this.getClass().getSimpleName();

//{"code":"0","data":{"imgurl":"http://image.winlang.com:8080/picshow-web/resources/upload/member/1/20170310223611373.JPEG",
//        "password":"123456","address":"南京雨花台区","phone":"15062263912","sex":1,"name":"小明","sign":1,"id":1,"age":36},"respMsg":"SUCCESS"}


    private int id;
    private int sex;
    private int age;
    private int sign;
    private String name;
    private String password;
    private String phone;
    private String imgurl;
    private String address;
    private String mac;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImgurl() {
        if (imgurl == null)
            imgurl = "";
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getSign() {
        return sign;
    }

    public void setSign(int sign) {
        this.sign = sign;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public void saveLoginInfo() {
        SharedPreferencesUtils.writeXmlInt(TAG, "USER_ID", id);
        SharedPreferencesUtils.writeXmlInt(TAG, "USER_SEX", sex);
        SharedPreferencesUtils.writeXmlInt(TAG, "USER_AGE", age);
        SharedPreferencesUtils.writeXmlInt(TAG, "USER_SIGN", sign);
        SharedPreferencesUtils.writeXmlString(TAG, "USER_NAME", name);
        SharedPreferencesUtils.writeXmlString(TAG, "USER_PASSWORD", password);
        SharedPreferencesUtils.writeXmlString(TAG, "USER_PHONE", phone);
        SharedPreferencesUtils.writeXmlString(TAG, "USER_IMGURL", imgurl);
        SharedPreferencesUtils.writeXmlString(TAG, "USER_ADDRESS", address);
        SharedPreferencesUtils.writeXmlString(TAG, "USER_MAC", mac);
    }

    public LoginInfo getUser() {
        id = SharedPreferencesUtils.readXmlInt(TAG, "USER_ID");
        sex = SharedPreferencesUtils.readXmlInt(TAG, "USER_SEX");
        age = SharedPreferencesUtils.readXmlInt(TAG, "USER_AGE");
        sign = SharedPreferencesUtils.readXmlInt(TAG, "USER_SIGN");
        name = SharedPreferencesUtils.readXmlString(TAG, "USER_NAME");
        password = SharedPreferencesUtils.readXmlString(TAG, "USER_PASSWORD");
        phone = SharedPreferencesUtils.readXmlString(TAG, "USER_PHONE");
        imgurl = SharedPreferencesUtils.readXmlString(TAG, "USER_IMGURL");
        address = SharedPreferencesUtils.readXmlString(TAG, "USER_ADDRESS");
        mac = SharedPreferencesUtils.readXmlString(TAG, "USER_MAC");
        return this;
    }

    public void loginOut() {
        SharedPreferencesUtils.clearXml(TAG);
        clearLoginInfo();
    }

    private void clearLoginInfo() {
        id = 0;
        sex = 0;
        age = 0;
        sign = 0;
        name = "";
        password = "";
        phone = "";
        imgurl = "";
        address = "";
    }

    @Override
    public String toString() {
        return "LoginInfo{" +
                "TAG='" + TAG + '\'' +
                ", id=" + id +
                ", sex=" + sex +
                ", age=" + age +
                ", sign=" + sign +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", imgurl='" + imgurl + '\'' +
                ", address='" + address + '\'' +
                ", mac='" + mac + '\'' +
                '}';
    }
}
