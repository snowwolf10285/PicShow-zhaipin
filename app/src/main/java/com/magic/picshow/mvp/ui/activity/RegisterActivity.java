package com.magic.picshow.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jess.arms.utils.ToastUtil;
import com.jess.arms.utils.UiUtils;
import com.magic.picshow.R;
import com.magic.picshow.di.component.DaggerRegisterComponent;
import com.magic.picshow.di.module.RegisterModule;
import com.magic.picshow.mvp.contract.RegisterContract;
import com.magic.picshow.mvp.model.entity.LoginInfo;
import com.magic.picshow.mvp.presenter.RegisterPresenter;
import com.ta.utdid2.android.utils.StringUtils;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import common.AppComponent;
import common.WEActivity;

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

public class RegisterActivity extends WEActivity<RegisterPresenter> implements RegisterContract.View, View.OnClickListener {

    public final static int REGIST = 0;
    public final static int CHANGE_PASSWORD = 1;
    private int registType;
    private int getCodeTime;
    private Timer getCodeTimer;

    @Nullable
    @BindView(R.id.regist)
    Button regist;
    @Nullable
    @BindView(R.id.regist_title)
    TextView registTitle;
    @Nullable
    @BindView(R.id.user_name)
    EditText userNameEt;
    @Nullable
    @BindView(R.id.password)
    EditText passwordEt;
    @Nullable
    @BindView(R.id.password_again)
    EditText passwordAgain;
    @Nullable
    @BindView(R.id.code)
    EditText codeEt;
    @Nullable
    @BindView(R.id.get_code)
    TextView getCode;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerRegisterComponent
                .builder()
                .appComponent(appComponent)
                .registerModule(new RegisterModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected View initView() {
        return LayoutInflater.from(this).inflate(R.layout.activity_register, null, false);
    }

    @Override
    protected void initData() {
        registType = getIntent().getIntExtra("registType", 0);
        if (registType == REGIST) {
            registTitle.setText(R.string.regist);
            regist.setText(R.string.regist);
        } else {
            registTitle.setText(R.string.change_password);
            regist.setText(R.string.change_password);
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
        stopCodeTimer();
        finish();
    }

    private void regist(String account, String password, String code) {
        mPresenter.requestRegist(account, password, code);
    }

    private void updatePassword(String account, String password, String code) {
        mPresenter.requestUpdatePassword(account, password, code);
    }

    private void getCode() {
        if (getCodeTime == 0) {
            startCodeTimer();
        } else {
            ToastUtil.showShort("请在倒计时结束后尝试获取验证码!");
        }
    }

    private void startCodeTimer() {
        stopCodeTimer();

        getCodeTimer = new Timer();
        getCodeTime = 60;
        getCodeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                getCodeTime--;
                RegisterActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (getCodeTime == 0) {
                            stopCodeTimer();
                            getCode.setText(R.string.get_code);
                        } else {
                            getCode.setText(getCodeTime + "s");
                        }
                    }
                });
            }
        }, 0, 1000);
    }

    private void stopCodeTimer() {
        if (getCodeTimer != null) {
            getCodeTimer.cancel();
        }
        getCodeTimer = null;
    }

    public static void show(Context context, int registType) {
        Intent intent = new Intent(context, RegisterActivity.class);
        intent.putExtra("registType", registType);
        context.startActivity(intent);
    }


    @Override
    @OnClick({R.id.back, R.id.regist, R.id.get_code})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                killMyself();
            case R.id.regist:
                String account = userNameEt.getText().toString();
                String password = passwordEt.getText().toString();
                String password2 = passwordAgain.getText().toString();
                String code = codeEt.getText().toString();

                if (StringUtils.isEmpty(account)) {
                    ToastUtil.showLong(R.string.user_name_null);
                    return;
                }
                if (StringUtils.isEmpty(password)) {
                    ToastUtil.showLong(R.string.password_null);
                    return;
                }
                if (!password.equals(password2)){
                    ToastUtil.showLong(R.string.password_err);
                    return;
                }
                if (StringUtils.isEmpty(code)) {
                    ToastUtil.showLong(R.string.code_null);
                    return;
                }

                if (registType == REGIST)
                    regist(account, password, code);
                else
                    updatePassword(account, password, code);

                break;
            case R.id.get_code:
                getCode();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    public void registerSuccess(LoginInfo mLoginInfo) {
        mWeApplication.setmUser(mLoginInfo);
        killMyself();
    }
}