package common;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.danikula.videocache.HttpProxyCacheServer;
import com.jess.arms.base.BaseApplication;
import com.jess.arms.di.module.GlobeConfigModule;
import com.jess.arms.http.GlobeHttpHandler;
import com.jess.arms.utils.UiUtils;
import com.magic.picshow.BuildConfig;
import com.magic.picshow.mvp.model.entity.LoginInfo;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.magic.picshow.di.module.CacheModule;
import com.magic.picshow.di.module.ServiceModule;
import com.magic.picshow.mvp.model.api.Api;
import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

import me.jessyan.rxerrorhandler.handler.listener.ResponseErroListener;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import timber.log.Timber;

/**
 * Created by jess on 8/5/16 11:07
 * contact with jess.yan.effort@gmail.com
 */
public class WEApplication extends BaseApplication {
    private AppComponent mAppComponent;
    private RefWatcher mRefWatcher;//leakCanary观察器
    private LoginInfo mUser;

    /**
     * 视频缓存工具
     */
    private HttpProxyCacheServer proxy;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppComponent = DaggerAppComponent
                .builder()
                .appModule(getAppModule())//baseApplication提供
                .clientModule(getClientModule())//baseApplication提供
                .imageModule(getImageModule())//baseApplication提供
                .globeConfigModule(getGlobeConfigModule())//全局配置
                .serviceModule(new ServiceModule())//需自行创建
                .cacheModule(new CacheModule())//需自行创建
                .build();

        if (BuildConfig.LOG_DEBUG) {//Timber日志打印
            Timber.plant(new Timber.DebugTree());
        }

//        installLeakCanary();//leakCanary内存泄露检查
        initUMPush();

        //bugly
        CrashReport.initCrashReport(getApplicationContext(), "487e5e6b5d", false);
    }

    /**
     * 初始化友盟推送
     */
    public void initUMPush() {
        PushAgent mPushAgent = PushAgent.getInstance(this);
        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {

            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回device token
                Log.e("deviceToken",deviceToken);
            }

            @Override
            public void onFailure(String s, String s1) {

            }
        });

        //调试日志开关
        mPushAgent.setDebugMode(true);
    }


    @Override
    public void onTerminate() {
        super.onTerminate();
        if (mAppComponent != null)
            this.mAppComponent = null;
        if (mRefWatcher != null)
            this.mRefWatcher = null;
    }

    /**
     * 安装leakCanary检测内存泄露
     */
    protected void installLeakCanary() {
        this.mRefWatcher = BuildConfig.USE_CANARY ? LeakCanary.install(this) : RefWatcher.DISABLED;
    }

    /**
     * 获得leakCanary观察器
     *
     * @param context
     * @return
     */
    public static RefWatcher getRefWatcher(Context context) {
        WEApplication application = (WEApplication) context.getApplicationContext();
        return application.mRefWatcher;
    }


    /**
     * 将AppComponent返回出去,供其它地方使用, AppComponent接口中声明的方法返回的实例, 在getAppComponent()拿到对象后都可以直接使用
     *
     * @return
     */
    public AppComponent getAppComponent() {
        return mAppComponent;
    }


    /**
     * app的全局配置信息封装进module(使用Dagger注入到需要配置信息的地方)
     *
     * @return
     */
    @Override
    protected GlobeConfigModule getGlobeConfigModule() {
        return GlobeConfigModule
                .buidler()
                .baseurl(Api.APP_HOST)
                .globeHttpHandler(new GlobeHttpHandler() {// 这里可以提供一个全局处理http响应结果的处理类,
                    // 这里可以比客户端提前一步拿到服务器返回的结果,可以做一些操作,比如token超时,重新获取
                    @Override
                    public Response onHttpResultResponse(String httpResult, Interceptor.Chain chain, Response response) {
                        //这里可以先客户端一步拿到每一次http请求的结果,可以解析成json,做一些操作,如检测到token过期后
                        //重新请求token,并重新执行请求
//                        try {
                        if (!TextUtils.isEmpty(httpResult)) {
                            Timber.tag(TAG).w("result ------>" + httpResult);
//                                JSONArray array = new JSONArray(httpResult);
//                                Log.e(TAG,array.length()+"");
//                                JSONObject object = (JSONObject) array.get(0);
//                                String login = object.getString("login");
//                                String avatar_url = object.getString("avatar_url");
//                                Timber.tag(TAG).w("result ------>" + login + "    ||   avatar_url------>" + avatar_url);
                        }

//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                            return response;
//                        }


                        //这里如果发现token过期,可以先请求最新的token,然后在拿新的token放入request里去重新请求
                        //注意在这个回调之前已经调用过proceed,所以这里必须自己去建立网络请求,如使用okhttp使用新的request去请求
                        // create a new request and modify it accordingly using the new token
//                    Request newRequest = chain.request().newBuilder().header("token", newToken)
//                            .build();

//                    // retry the request
//
//                    response.body().close();
                        //如果使用okhttp将新的请求,请求成功后,将返回的response  return出去即可

                        //如果不需要返回新的结果,则直接把response参数返回出去
                        return response;
                    }

                    // 这里可以在请求服务器之前可以拿到request,做一些操作比如给request统一添加token或者header
                    @Override
                    public Request onHttpRequestBefore(Interceptor.Chain chain, Request request) {
                        //如果需要再请求服务器之前做一些操作,则重新返回一个做过操作的的requeat如增加header,不做操作则返回request

                        //return chain.request().newBuilder().header("token", tokenId)
//                .build();
                        return request;
                    }
                })
                .responseErroListener(new ResponseErroListener() {
                    //     用来提供处理所有错误的监听
                    //     rxjava必要要使用ErrorHandleSubscriber(默认实现Subscriber的onError方法),此监听才生效
                    @Override
                    public void handleResponseError(Context context, Exception e) {
                        Timber.tag(TAG).w("------------>" + e.getMessage());
                        UiUtils.SnackbarText("net error");
                    }
                }).build();
    }


    /**
     * 获取视频缓存 地址
     *
     * @param context
     * @return
     */
    public static HttpProxyCacheServer getProxy(Context context) {
        WEApplication app = (WEApplication) context.getApplicationContext();
        return app.proxy == null ? (app.proxy = app.newProxy()) : app.proxy;
    }

    private HttpProxyCacheServer newProxy() {
        return new HttpProxyCacheServer(this);
    }


    public LoginInfo getmUser() {
        if (mUser == null)
            mUser = new LoginInfo().getUser();
        return mUser;
    }


    public String getMac() {
        String mac = "";
        try {
            Process pp = Runtime.getRuntime().exec("cat /sys/class/net/wlan0/address");
            InputStreamReader ir = new InputStreamReader(pp.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);

            String line;
            while ((line = input.readLine()) != null) {
                mac += line.trim();
            }

            Log.e("--------", mac);

            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return mac;
    }

    public void setmUser(LoginInfo mUser) {
        this.mUser = mUser;
    }
}
