package com.magic.picshow.mvp.model;

import android.app.Application;
import android.util.Log;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BaseModel;
import com.magic.picshow.mvp.contract.PayContract;
import com.magic.picshow.mvp.model.api.cache.CacheManager;
import com.magic.picshow.mvp.model.api.service.ServiceManager;
import com.magic.picshow.mvp.model.entity.BaseJson;
import com.magic.picshow.mvp.model.entity.OrderAli;
import com.magic.picshow.mvp.model.entity.OrderWX;

import java.util.HashMap;

import javax.inject.Inject;

import rx.Observable;



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
public class PayModel extends BaseModel<ServiceManager, CacheManager> implements PayContract.Model {
    private Gson mGson;
    private Application mApplication;

    @Inject
    public PayModel(ServiceManager serviceManager, CacheManager cacheManager, Gson gson, Application application) {
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
    public Observable<BaseJson<OrderAli>> creatOrderAli(double cashnum, int mercid, int p_id, int user_id) {
        HashMap params = new HashMap();
        params.put("cashnum", cashnum+"");//金额
        params.put("mercid", mercid+"");//产品id
        params.put("p_id", p_id+"");//相册id
        params.put("user_id", user_id+"");//用户id

        Observable<BaseJson<OrderAli>> order = mServiceManager.getCommonService().creatOrderAli(params);
        Log.e("creatOrder", order.toString());
        return order;
    }

    @Override
    public Observable<BaseJson<OrderWX>> creatOrderWX(double cashnum, int mercid, int p_id, int user_id) {
        HashMap params = new HashMap();
        params.put("cashnum", cashnum+"");//金额
        params.put("mercid", mercid+"");//产品id
        params.put("p_id", p_id+"");//相册id
        params.put("user_id", user_id+"");//用户id

        Observable<BaseJson<OrderWX>> order = mServiceManager.getCommonService().creatOrderWx(params);
        Log.e("creatOrder", order.toString());
        return order;
    }

    @Override
    public Observable<BaseJson> paySuccess(int p_id, int pr_id, int user_id,int type) {
        HashMap params = new HashMap();
        params.put("p_id", p_id+"");//相册id
        params.put("pr_id", pr_id+"");//产品id
        params.put("user_id", user_id+"");//用户id
        params.put("type", type+"");//用户id

        Observable<BaseJson> result = mServiceManager.getCommonService().paySuccess(params);
        Log.e("paySuccess", result.toString());
        return result;
    }
}