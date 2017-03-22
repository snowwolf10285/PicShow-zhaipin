package com.magic.picshow.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.jess.arms.utils.FileUtils;
import com.magic.picshow.R;
import com.magic.picshow.di.component.DaggerInitStartComponent;
import com.magic.picshow.di.module.InitStartModule;
import com.magic.picshow.mvp.contract.InitStartContract;
import com.magic.picshow.mvp.model.entity.LoginInfo;
import com.magic.picshow.mvp.presenter.InitStartPresenter;
import com.magic.picshow.mvp.ui.fragment.VideoFragment;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import common.AppComponent;
import common.WEActivity;

public class InitStartActivity extends WEActivity<InitStartPresenter> implements InitStartContract.View, View.OnClickListener {

    @BindView(R.id.exit_init)
    TextView exitInit;
    private Timer exitTimer;
    private FragmentTransaction transaction;
    private int videoTime = 16;//视频时长
    private VideoFragment mVideoFragment;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerInitStartComponent
                .builder()
                .appComponent(appComponent)
                .initStartModule(new InitStartModule(this)) //请将LoginModule()第一个首字母改为小写
                .build()
                .inject(this);
    }

    @Override
    protected View initView() {
        return LayoutInflater.from(this).inflate(R.layout.activity_init_start, null, false);
    }

    @Override
    protected void initData() {
        if (mWeApplication.getmUser().getId() == 0)
            mPresenter.requestLogin(mWeApplication.getMac());

        FragmentManager manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        final File f = new File(getFilesDir(), "mp4/222.mp4");
        if (!f.exists()) {
            FileUtils.getInstance(this).copyAssetsToData("mp4", "mp4").setFileOperateCallback(new FileUtils.FileOperateCallback() {
                @Override
                public void onSuccess() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            startMp4(f);
                        }
                    });
                }

                @Override
                public void onFailed(String error) {
                    HomeActivity.show(InitStartActivity.this);
                    finish();
                }
            });
        } else {
            startMp4(f);
        }

    }

    public void startMp4(File f) {
        mVideoFragment = VideoFragment.newInstance(f.getPath(), true);
        transaction.add(R.id.init_frame, mVideoFragment, "initVideo").commit();
        exitTimer = new Timer();
        exitTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                videoTime--;
                InitStartActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (exitInit != null) {
                            exitInit.setText(mWeApplication.getResources().getString(R.string.exit_init) + videoTime);
                            if (videoTime <= 0) {
                                HomeActivity.show(InitStartActivity.this);
                                InitStartActivity.this.finish();
                            }
                        }
                    }
                });

            }
        }, 0, 1 * 1000);
    }

    @Override
    @OnClick({R.id.exit_init})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.exit_init:

                HomeActivity.show(this);
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (exitTimer != null) {
            exitTimer.cancel();
            exitTimer = null;
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void launchActivity(Intent intent) {

    }

    @Override
    public void killMyself() {

    }

    @Override
    public void loginSuccess(LoginInfo mLoginInfo) {
        mWeApplication.setmUser(mLoginInfo);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mVideoFragment.onPause();
    }
}
