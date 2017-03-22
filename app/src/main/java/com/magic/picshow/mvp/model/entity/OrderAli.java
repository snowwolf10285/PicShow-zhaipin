package com.magic.picshow.mvp.model.entity;

/**
 * Created by snowwolf on 17/3/19.
 */

public class OrderAli {
    //    支付宝
//    "data":{
// "orderStr":"
// app_id=2017020905581397&
// biz_content=%7B%22
// out_trade_no%22%3A%22TNO20170312144836%22%2C%22
// total_amount%22%3A%22
// 2.12%22%2C%22
// subject%22%3A%22%E6%B5%8B%E8%AF%95%E6%94%AF%E4%BB%98%22%2C%22
// body%22%3A%22Andy%22%2C%22
// product_code%22%3A%22
// QUICK_MSECURITY_PAY%22%7D&
// charset=UTF-8&
// format=json&
// method=alipay.trade.app.pay&
// notify_url=http%3A%2F%2Fimage.winlang.com%3A8080%2Fpicshow-web%2Falipay%2Fnotify.shtml&
// sign_type=RSA&
// timestamp=2017-03-12%2014%3A48%3A36&
// version=1.0&
// sign=NRGQ%2FP%2But8Xv8XzIzAXyCeiktylK2kkoH3%2F1f9PPiHlqaazrsjfa%2FCPi9ugflRfxzvAVNfks7I9d%2BFJDozInfXePqowGGLI2G4rFEBmFuF1z%2FJAQYbQTrZIguQYPLjj9ozz00bWOE8VZbYt5QMxYjsazaq9I7fdl6tKSkPcOJOWDncRXj44qAGrauIeBGBUXXNKVTKgOQSOyyItnjo%2FXAXAlyQ5OCty4xCPBmmMytsv%2BqxruWNYpG3r5vxtfYrXrWz%2BreH%2FoV6V%2BpYXtcibYmDIlyHXl2PSbe5ugcFU5ETN8f45VgpFP0y9%2Fa6MCesOsidhmM61vh9P0V9Uboycv8w%3D%3D"}

    private String orderStr;

    public String getOrderStr() {
        return orderStr;
    }

    public void setOrderStr(String orderStr) {
        this.orderStr = orderStr;
    }



    @Override
    public String toString() {
        return "Order{" +
                "orderStr='" + orderStr + '\'' +
//                ", packages='" + packages + '\'' +
//                ", appid='" + appid + '\'' +
//                ", sign='" + sign + '\'' +
//                ", partnerid='" + partnerid + '\'' +
//                ", prepayid='" + prepayid + '\'' +
//                ", noncestr='" + noncestr + '\'' +
//                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}
