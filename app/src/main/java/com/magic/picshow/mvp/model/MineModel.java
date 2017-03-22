package com.magic.picshow.mvp.model;

import android.app.Application;
import android.util.Log;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BaseModel;
import com.magic.picshow.mvp.contract.MineContract;
import com.magic.picshow.mvp.model.api.cache.CacheManager;
import com.magic.picshow.mvp.model.api.service.ServiceManager;
import com.magic.picshow.mvp.model.entity.BaseJson;
import com.magic.picshow.mvp.model.entity.LoginInfo;
import com.magic.picshow.mvp.model.entity.UpdateInfo;
import com.ta.utdid2.android.utils.StringUtils;

import java.io.File;
import java.util.HashMap;

import javax.inject.Inject;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * 通过Template生成对应页面的MVP和Dagger代码,请注意输入框中输入的名字必须相同
 * 由于每个项目包结构都不一定相同,所以每生成一个文件需要自己导入import包名,可以在设置中设置自动导入包名
 * 请在对应包下按以下顺序生成对应代码,Contract->Model->Presenter->Activity->Module->Component
 * 因为生成Activity时,Module和Component还没生成,但是Activity中有它们的引用,所以会报错,但是不用理会
 * 继续将Module和Component生成完后,编译一下项目再回到Activity,按提示修改一个方法名即可
 * 如果想生成Fragment的相关文件,则将上面构建顺序中的Activity换为Fragment,并将Component中inject方法的参数改为此Fragment
 */

/**
 * Created by snowwolf on 17/1/29.
 */

@ActivityScope
public class MineModel extends BaseModel<ServiceManager, CacheManager> implements MineContract.Model {
    private Gson mGson;
    private Application mApplication;

    @Inject
    public MineModel(ServiceManager serviceManager, CacheManager cacheManager, Gson gson, Application application) {
        super(serviceManager, cacheManager);
        this.mGson = gson;
        this.mApplication = application;
    }

    @Override
    public void onDestory() {
        super.onDestory();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseJson<LoginInfo>> updateUserInfo(String user_id, int sex, int age, String name, String address) {
        HashMap params = new HashMap();
        if (user_id != null && !StringUtils.isEmpty(user_id))
            params.put("user_id", user_id);
        params.put("sex", sex);
        params.put("age", age);
        if (name != null && !StringUtils.isEmpty(name))
            params.put("name", name);
        if (address != null && !StringUtils.isEmpty(address))
            params.put("address", address);
        Observable<BaseJson<LoginInfo>> loginInfo = mServiceManager.getLoginService().updateUserInfo(params);
        return loginInfo;
    }


    @Override
    public Observable<BaseJson<UpdateInfo>> checkUpdate(String apk_num) {
        HashMap params = new HashMap();
        params.put("apk_num", apk_num);
        Observable<BaseJson<UpdateInfo>> updateInfo = mServiceManager.getCommonService().checkUpdate(params);
        return updateInfo;
    }

    @Override
    public Call<BaseJson<LoginInfo>> updateImg(String id, String imgStr) {
        File file = new File(imgStr);
        // create RequestBody instance from file
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);

        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("img", file.getName(), requestFile);

        // add another part within the multipart request
//        String descriptionString = "hello, this is description speaking";
        RequestBody idBody =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), id);

        // finally, execute the request
        Call<BaseJson<LoginInfo>> call = mServiceManager.getLoginService().updateImg(idBody, body);
        return call;
    }
}