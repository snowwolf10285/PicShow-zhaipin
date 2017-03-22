package com.magic.picshow.mvp.model.api.service;

import com.magic.picshow.mvp.model.entity.BaseJson;
import com.magic.picshow.mvp.model.entity.Classify;
import com.magic.picshow.mvp.model.entity.OrderAli;
import com.magic.picshow.mvp.model.entity.OrderWX;
import com.magic.picshow.mvp.model.entity.PhotoDetails;
import com.magic.picshow.mvp.model.entity.Photos;
import com.magic.picshow.mvp.model.entity.UpdateInfo;

import java.util.List;
import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * 存放通用的一些API
 * Created by jess on 8/5/16 12:05
 * contact with jess.yan.effort@gmail.com
 */
public interface CommonService {
    @FormUrlEncoded
    @POST("app_resource/get_resource_list.shtml")
    Observable<BaseJson<PhotoDetails>> getPhotoDetailsList(@FieldMap Map<String, String> body);

    @POST("common/get_classify_list.shtml")
    Observable<BaseJson<List<Classify>>> getClassifyList();

    @FormUrlEncoded
    @POST("common/get_apk_info.shtml")
    Observable<BaseJson<UpdateInfo>> checkUpdate(@FieldMap Map<String, String> body);

    @FormUrlEncoded
    @POST("app_resource/get_photo_album_list.shtml")
    Observable<BaseJson<Photos>> getPhotosList(@FieldMap Map<String, String> body);

    @FormUrlEncoded
    @POST("alipay/order.shtml")
    Observable<BaseJson<OrderAli>> creatOrderAli(@FieldMap Map<String, String> body);

    @FormUrlEncoded
    @POST("wxpay/order.shtml")
    Observable<BaseJson<OrderWX>> creatOrderWx(@FieldMap Map<String, String> body);

    @FormUrlEncoded
    @POST("common/pay_notice.shtml")
    Observable<BaseJson> paySuccess(@FieldMap Map<String, String> body);
}
