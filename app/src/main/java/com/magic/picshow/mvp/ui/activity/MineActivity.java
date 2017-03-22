package com.magic.picshow.mvp.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jess.arms.utils.ToastUtil;
import com.jess.arms.utils.UiUtils;
import com.jess.arms.widget.imageloader.ImageLoader;
import com.jess.arms.widget.imageloader.glide.GlideImageConfig;
import com.magic.picshow.R;
import com.magic.picshow.app.utils.CacheDataUtil;
import com.magic.picshow.di.component.DaggerMineComponent;
import com.magic.picshow.di.module.MineModule;
import com.magic.picshow.mvp.contract.MineContract;
import com.magic.picshow.mvp.model.entity.LoginInfo;
import com.magic.picshow.mvp.presenter.MinePresenter;
import com.magic.picshow.mvp.ui.widget.CircleTransform;
import com.magic.picshow.mvp.ui.widget.XCRoundImageView;
import com.ta.utdid2.android.utils.StringUtils;
import com.yongchun.library.view.ImageSelectorActivity;

import java.util.ArrayList;

import anet.channel.util.Utils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import common.AppComponent;
import common.WEActivity;
import common.WEApplication;
import timber.log.Timber;

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

public class MineActivity extends WEActivity<MinePresenter> implements MineContract.View, View.OnClickListener {


    @BindView(R.id.user_photo)
    ImageView userPhoto;
    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.goto_login)
    ImageView gotoLogin;
    @BindView(R.id.buy_photos)
    TextView buyPhotos;
    @BindView(R.id.clear_cache)
    TextView clearCache;
    @BindView(R.id.check_version)
    TextView checkVersion;
    @BindView(R.id.about_us)
    TextView aboutUs;
    @BindView(R.id.login_out)
    TextView loginOut;

    private Boolean isLogin = false;
    private ImageLoader mImageLoader;//用于加载图片的管理类,默认使用glide,使用策略模式,可替换框架

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerMineComponent
                .builder()
                .appComponent(appComponent)
                .mineModule(new MineModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected View initView() {

        return LayoutInflater.from(this).inflate(R.layout.activity_mine, null, false);
    }

    @Override
    protected void initData() {
        mImageLoader = mWeApplication.getAppComponent().imageLoader();
        checkVersion.setText(getResources().getString(R.string.check_version).replace("*", Utils.getAppVersion(this)));
        try {
            clearCache.setText(getResources().getString(R.string.clear_cache).replace("*", CacheDataUtil.getTotalCacheSize(this)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 确定当前用户登录状态
     */
    public void initLoginState() {
        if (mWeApplication.getmUser().getId() == 0) {
            //未登陆
            isLogin = false;
            loginOut.setVisibility(View.GONE);
            gotoLogin.setVisibility(View.VISIBLE);
            userName.setVisibility(View.GONE);

            userPhoto.setImageResource(R.mipmap.user_photo);
        } else {
            //登录状态
            isLogin = true;
            loginOut.setVisibility(View.GONE);
            gotoLogin.setVisibility(View.GONE);
            userName.setVisibility(View.VISIBLE);
            if (StringUtils.isEmpty(mWeApplication.getmUser().getName()))
                userName.setText(mWeApplication.getMac().replaceAll(":",""));
            else
                userName.setText(mWeApplication.getmUser().getName());

            mImageLoader.loadImage(mApplication, GlideImageConfig
                    .builder()
                    .transformation(new CircleTransform(this))
                    .errorPic(R.mipmap.user_photo)
                    .url(mWeApplication.getmUser().getImgurl())
                    .imagerView(userPhoto)
                    .build());
        }
    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        UiUtils.SnackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        UiUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }


    public static void show(Context context) {
        Intent intent = new Intent(context, MineActivity.class);
        context.startActivity(intent);
    }

    @Override
    @OnClick({R.id.back, R.id.user_photo, R.id.buy_photos, R.id.clear_cache, R.id.check_version, R.id.about_us, R.id.goto_login, R.id.login_out})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                killMyself();
                break;
            case R.id.user_photo:
                if (isLogin)
                    ImageSelectorActivity.start(MineActivity.this, 0, ImageSelectorActivity.MODE_SINGLE, true, true, true);
                else
                    LoginActivity.show(this);

                break;
            case R.id.buy_photos:
                if (isLogin)
                    SameCityActivity.show(this, SameCityActivity.MINE_PHOTOS);
                else
                    ToastUtil.show("请先登录!", Toast.LENGTH_LONG);
                break;
            case R.id.clear_cache:
                try {
                    CacheDataUtil.clearAllCache(this);
                    clearCache.setText(getResources().getString(R.string.clear_cache).replace("*", CacheDataUtil.getTotalCacheSize(this)));
                    ToastUtil.show("缓存已清理!", Toast.LENGTH_LONG);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            case R.id.check_version:
                mPresenter.requestUpdate();
                break;
            case R.id.about_us:
                // TODO: 17/2/28
                AboutUsActivity.show(this, "");
                break;
            case R.id.goto_login:
                LoginActivity.show(this);
                break;
            case R.id.login_out:
                mWeApplication.getmUser().loginOut();
                initLoginState();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initLoginState();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == ImageSelectorActivity.REQUEST_IMAGE) {
            ArrayList<String> images = (ArrayList<String>) data.getSerializableExtra(ImageSelectorActivity.REQUEST_OUTPUT);
            Log.e(TAG, "resultImg = " + images.get(0));

            mPresenter.updateImg(mWeApplication.getmUser().getId() + "", images.get(0));

        }
    }

    @Override
    public void updateImgSuccess(LoginInfo mLoginInfo) {
        mWeApplication.setmUser(mLoginInfo);
        mImageLoader.loadImage(mApplication, GlideImageConfig
                .builder()
                .transformation(new CircleTransform(this))
                .errorPic(R.mipmap.user_photo)
                .url(mWeApplication.getmUser().getImgurl())
                .imagerView(userPhoto)
                .build());
    }
}