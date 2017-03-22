package com.magic.picshow.mvp.presenter;

import android.app.Application;
import android.util.Log;

import com.jess.arms.base.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxUtils;
import com.jess.arms.utils.ToastUtil;
import com.jess.arms.widget.imageloader.ImageLoader;
import com.magic.picshow.mvp.contract.InitStartContract;
import com.magic.picshow.mvp.model.entity.BaseJson;
import com.magic.picshow.mvp.model.entity.LoginInfo;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

import javax.inject.Inject;


/**
 * 通过Template生成对应页面的MVP和Dagger代码,请注意输入框中输入的名字必须相同
 * 由于每个项目包结构都不一定相同,所以每生成一个文件需要自己导入import包名,可以在设置中设置自动导入包名
 * 请在对应包下按以下顺序生成对应代码,Contract->Model->Presenter->Activity->Module->Component
 * 因为生成Activity时,Module和Component还没生成,但是Activity中有它们的引用,所以会报错,但是不用理会
 * 继续将Module和Component生成完后,编译一下项目再回到Activity,按提示修改一个方法名即可
 * 如果想生成Fragment的相关文件,则将上面构建顺序中的Activity换为Fragment,并将Component中inject方法的参数改为此Fragment
 */


/**
 * Created by snowwolf on 17/3/15.
 */

@ActivityScope
public class InitStartPresenter extends BasePresenter<InitStartContract.Model, InitStartContract.View> {
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private ImageLoader mImageLoader;
    private AppManager mAppManager;
    private LoginInfo mLoginInfo;

    @Inject
    public InitStartPresenter(InitStartContract.Model model, InitStartContract.View rootView
            , RxErrorHandler handler, Application application
            , ImageLoader imageLoader, AppManager appManager) {
        super(model, rootView);
        this.mErrorHandler = handler;
        this.mApplication = application;
        this.mImageLoader = imageLoader;
        this.mAppManager = appManager;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }


    public void requestLogin(String mac) {
        mModel.login(mac).subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        Log.e("Login", "doOnSubscribe");
                    }
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(new Action0() {
                    @Override
                    public void call() {
                        Log.e("Login", "doAfterTerminate");
                    }
                })
                .compose(RxUtils.<BaseJson<LoginInfo>>bindToLifecycle(mRootView))//使用RXlifecycle,使subscription和activity一起销毁
                .subscribe(new ErrorHandleSubscriber<BaseJson<LoginInfo>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseJson<LoginInfo> response) {
                        if (response.isSuccess()) {
                            mLoginInfo = response.getData();
                            mLoginInfo.saveLoginInfo();
                            mRootView.loginSuccess(mLoginInfo);
                        Log.e("Login","onNext = "+mLoginInfo.toString());
                        } else {
                            ToastUtil.showLong("登录失败!");
                            mRootView.showMessage(response.getMsg());
                        }
                    }
                });
    }

}
