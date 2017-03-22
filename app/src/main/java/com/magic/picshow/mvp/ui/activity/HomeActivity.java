package com.magic.picshow.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jess.arms.utils.NetTypeUtils;
import com.jess.arms.utils.UiUtils;
import com.magic.picshow.R;
import com.magic.picshow.di.component.DaggerHomeComponent;
import com.magic.picshow.di.module.HomeModule;
import com.magic.picshow.mvp.contract.HomeContract;
import com.magic.picshow.mvp.model.entity.Classify;
import com.magic.picshow.mvp.presenter.HomePresenter;
import com.magic.picshow.mvp.ui.adapter.ClassifyAdapter;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.ScrollIndicatorView;
import com.shizhefei.view.indicator.slidebar.ColorBar;
import com.shizhefei.view.indicator.transition.OnTransitionTextListener;

import java.util.ArrayList;
import java.util.List;

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

public class HomeActivity extends WEActivity<HomePresenter> implements HomeContract.View, View.OnClickListener {


    @Nullable
    @BindView(R.id.moretab_indicator)
    ScrollIndicatorView moretabIndicator;

    @Nullable
    @BindView(R.id.moretab_viewPager)
    ViewPager moretabViewPager;

    @BindView(R.id.home_title)
    TextView homeTitle;

    private IndicatorViewPager indicatorViewPager;
    private ClassifyAdapter mClassifyAdapter;


    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerHomeComponent
                .builder()
                .appComponent(appComponent)
                .homeModule(new HomeModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected View initView() {
        return LayoutInflater.from(this).inflate(R.layout.activity_home, null, false);
    }

    @Override
    protected void initData() {
        mPresenter.requestClassifyList();

        //wifi状态下自动更新app
        if (NetTypeUtils.checkNetworkType(this) == NetTypeUtils.TYPE_WIFI){
            mPresenter.requestUpdate();
        }

    }


    private void initIndicator() {
        float unSelectSize = 12;
        float selectSize = unSelectSize * 1.3f;
        moretabIndicator.setOnTransitionListener(new OnTransitionTextListener().setColor(0XFFFFA629, Color.GRAY).setSize(selectSize, unSelectSize));

        moretabIndicator.setScrollBar(new ColorBar(this, 0XFFFFA629, 4));

        moretabViewPager.setOffscreenPageLimit(0);

        indicatorViewPager = new IndicatorViewPager(moretabIndicator, moretabViewPager);

    }

    public static void show(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        context.startActivity(intent);
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    @OnClick({R.id.same_city, R.id.mine})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.same_city:
                SameCityActivity.show(this, SameCityActivity.CITY_PHOTOS);
                break;
            case R.id.mine:
                MineActivity.show(this);
                break;
            default:
                break;
        }
    }

    @Override
    public void setClassifyList(List<Classify> mClassifyList) {
        initIndicator();
        mClassifyAdapter = new ClassifyAdapter(getSupportFragmentManager(), mClassifyList);
        indicatorViewPager.setAdapter(mClassifyAdapter);
    }
}