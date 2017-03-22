package com.magic.picshow.mvp.model.api.service;

import com.magic.picshow.mvp.model.entity.BaseJson;
import com.magic.picshow.mvp.model.entity.LoginInfo;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;

/**
 * Created by snowwolf on 17/2/16.
 */

public interface LoginService {

    //http://106.14.65.237:8080/picshow-web/login_app/login_app.shtml
    //account：15062263912 ，passwoed：123456
    @FormUrlEncoded
    @POST("user/login_app.shtml")
    Observable<BaseJson<LoginInfo>> login(@FieldMap Map<String, String> body);

    @FormUrlEncoded
    @POST("user/register.shtml")
    Observable<BaseJson<LoginInfo>> register(@FieldMap Map<String, String> body);

    @FormUrlEncoded
    @POST("/user/update_password.shtml")
    Observable<BaseJson<LoginInfo>> updatePassword(@FieldMap Map<String, String> body);

    //    sex      用户性别：1男，0女
    @FormUrlEncoded
    @POST("user/update_user_info.shtml")
    Observable<BaseJson<LoginInfo>> updateUserInfo(@FieldMap Map<String, String> body);

    /**
     * 图片上传API
     * 上传用户头像
     */
    @Multipart
    @POST("user/update_img.shtml")
    Call<BaseJson<LoginInfo>> updateImg( @Part("id") RequestBody id , @Part MultipartBody.Part file);
}
