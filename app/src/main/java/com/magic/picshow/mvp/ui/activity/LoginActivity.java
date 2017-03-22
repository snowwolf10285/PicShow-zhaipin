package com.magic.picshow.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.jess.arms.utils.ToastUtil;
import com.jess.arms.utils.UiUtils;
import com.magic.picshow.R;
import com.magic.picshow.di.component.DaggerLoginComponent;
import com.magic.picshow.di.module.LoginModule;
import com.magic.picshow.mvp.contract.LoginContract;
import com.magic.picshow.mvp.model.entity.LoginInfo;
import com.magic.picshow.mvp.presenter.LoginPresenter;
import com.ta.utdid2.android.utils.StringUtils;

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

public class LoginActivity extends WEActivity<LoginPresenter> implements LoginContract.View, View.OnClickListener {


//    @BindView(R.id.user_name)
//    EditText userNameEt;
//    @BindView(R.id.password)
//    EditText passwordEt;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerLoginComponent
                .builder()
                .appComponent(appComponent)
                .loginModule(new LoginModule(this)) //请将LoginModule()第一个首字母改为小写
                .build()
                .inject(this);
    }

    @Override
    protected View initView() {
        return LayoutInflater.from(this).inflate(R.layout.activity_login, null, false);
    }

    @Override
    protected void initData() {
        login(mWeApplication.getMac());
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
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    public void login(String mac) {
//        ToastUtil.showShort("正在登录");
        showLoading();
//        mPresenter.requestLogin("15062263912", "123456");
        mPresenter.requestLogin(mac);
    }


//    public void login(String account, String password) {
////        ToastUtil.showShort("正在登录");
//        showLoading();
////        mPresenter.requestLogin("15062263912", "123456");
//        mPresenter.requestLogin(account, password);
//    }

    @Override
    @OnClick({R.id.back, R.id.forget_password, R.id.regist, R.id.login})
    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.back:
//                killMyself();
//                break;
//            case R.id.forget_password:
//                RegisterActivity.show(this, RegisterActivity.CHANGE_PASSWORD);
//                break;
//            case R.id.regist:
//                RegisterActivity.show(this, RegisterActivity.REGIST);
//                break;
//            case R.id.login:
//                String account = userNameEt.getText().toString();
//                String password = passwordEt.getText().toString();
//                if (StringUtils.isEmpty(account)) {
//                    ToastUtil.showLong(R.string.user_name_null);
//                    return;
//                }
//                if (StringUtils.isEmpty(password)) {
//                    ToastUtil.showLong(R.string.password_null);
//                    return;
//                }
//
//                login(account, password);
//                break;
//        }
    }

    @Override
    public void loginSuccess(LoginInfo mLoginInfo) {
        mWeApplication.setmUser(mLoginInfo);
        hideLoading();
        killMyself();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //如果检测到已经登陆则退出当前页面
        if (mWeApplication.getmUser().getId() != 0) {
            killMyself();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}