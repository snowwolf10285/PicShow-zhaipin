package com.magic.picshow.mvp.presenter;

import android.app.Application;

import com.jess.arms.base.AppManager;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxUtils;
import com.jess.arms.utils.ToastUtil;
import com.magic.picshow.mvp.contract.PhotosContract;
import com.magic.picshow.mvp.model.entity.BaseJson;
import com.magic.picshow.mvp.model.entity.Photos;
import com.magic.picshow.mvp.ui.adapter.PhotosAdapter;

import java.util.ArrayList;
import java.util.List;

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
 * Created by snowwolf on 17/1/29.
 */

@FragmentScope
public class PhotosPresenter extends BasePresenter<PhotosContract.Model, PhotosContract.View> {
    private RxErrorHandler mErrorHandler;
    private AppManager mAppManager;
    private Application mApplication;
    private List<Photos.PhotosItems> mPhotos = new ArrayList<>();
    private DefaultAdapter mAdapter;
    /**
     * 当前页数
     */
    private int page = 1;

    /**
     * 总页数
     */
    public int total = 1;

    /**
     * 总相册数
     */
    private int records = 0;


    @Inject
    public PhotosPresenter(PhotosContract.Model model, PhotosContract.View rootView, RxErrorHandler handler
            , AppManager appManager, Application application) {
        super(model, rootView);
        this.mApplication = application;
        this.mErrorHandler = handler;
        this.mAppManager = appManager;
        mAdapter = new PhotosAdapter(mPhotos);

        mRootView.setAdapter(mAdapter);//设置Adapter
    }

    public void requestPhotos(int classify_id, int type, int user_id, final boolean pullToRefresh) {

        if (pullToRefresh) {
            page = 1;
//            mRootView.isLoadAllItems(false);
        } else {
            if (page >= total) {
//                mRootView.isLoadAllItems(true);
                return;
            }
            page++;
        }

        mModel.getPhotosList(classify_id, type, page, 16, user_id)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        if (pullToRefresh) {
                            mRootView.showLoading();//显示上拉刷新的进度条
                            mRootView.endLoadMore();
                        } else {
                            mRootView.startLoadMore();
                            mRootView.hideLoading();
                        }
                    }
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(new Action0() {
                    @Override
                    public void call() {
                        if (pullToRefresh) {
                            mRootView.hideLoading();//隐藏上拉刷新的进度条
                        } else {
                            mRootView.endLoadMore();
                        }
                        if (page >= total) {
                            mRootView.isLoadAllItems(true);
                        }else{
                            mRootView.isLoadAllItems(false);
                        }
                    }
                })
                .compose(RxUtils.<BaseJson<Photos>>bindToLifecycle(mRootView))//使用RXlifecycle,使subscription和activity一起销毁
                .subscribe(new ErrorHandleSubscriber<BaseJson<Photos>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseJson<Photos> photoses) {
                        if (photoses.isSuccess()) {
                            if (pullToRefresh)
                                mPhotos.clear();//如果是上拉刷新则清空列表
                            mPhotos.addAll(photoses.getData().getRows());
//                            for (Photos photes : photoses.getData()) {
//                                mPhotos.add(photes);
//                            }
                            total = photoses.getData().getTotal();
                            records = photoses.getData().getRecords();

                            mAdapter.notifyDataSetChanged();//通知更新数据
                        } else {
                            ToastUtil.showLong("网络不通畅,请稍后再试!");
                        }

                    }
                });
    }

    public void setOnItemClickListener(DefaultAdapter.OnRecyclerViewItemClickListener listener) {
        mAdapter.setOnItemClickListener(listener);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mAdapter = null;
        this.mPhotos = null;
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mApplication = null;
    }


}
