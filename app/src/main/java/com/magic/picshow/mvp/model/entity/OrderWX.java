package com.magic.picshow.mvp.model.entity;

/**
 * Created by snowwolf on 17/3/19.
 */

public class OrderWX {

//    微信
//    {"date":{"package":"Sign=WXPay","appid":"wx342c95acf8162e11","sign":"DD319F653D8899A76A94E1BE55AB9683",
// "partnerid":"1448692602","prepayid":"wx2017031919285235607bf7660286029986","noncestr":"S6LaFpLLwRxH48RhFrushmIfJhHe0uHK",
// "timestamp":"1489922932"},"code":"0","respMsg":"SUCCESS"}

    private String appid;
    private String sign;
    private String partnerid;
    private String prepayid;
    private String noncestr;
    private String timestamp;
    private String packages = "Sign=WXPay";

    public String getPackages() {
        return packages;
    }

    public void setPackages(String packages) {
        this.packages = packages;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Order{" +
                ", packages='" + packages + '\'' +
                ", appid='" + appid + '\'' +
                ", sign='" + sign + '\'' +
                ", partnerid='" + partnerid + '\'' +
                ", prepayid='" + prepayid + '\'' +
                ", noncestr='" + noncestr + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}
