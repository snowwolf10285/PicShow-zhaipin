package com.magic.picshow.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.jess.arms.utils.UiUtils;
import com.magic.picshow.R;
import com.magic.picshow.di.component.DaggerPhotoDetailComponent;
import com.magic.picshow.di.module.PhotoDetailModule;
import com.magic.picshow.mvp.contract.PhotoDetailContract;
import com.magic.picshow.mvp.model.entity.PhotoDetails;
import com.magic.picshow.mvp.presenter.PhotoDetailPresenter;
import com.magic.picshow.mvp.ui.adapter.PhotoDetailAdapter;
import com.magic.picshow.mvp.ui.fragment.ImageDetailFragment;
import com.magic.picshow.mvp.ui.fragment.PayFragment;
import com.magic.picshow.mvp.ui.fragment.StoryFragment;
import com.magic.picshow.mvp.ui.fragment.VideoFragment;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.ScrollIndicatorView;
import com.shizhefei.view.indicator.slidebar.SpringBar;
import com.shizhefei.view.indicator.transition.OnTransitionTextListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import common.AppComponent;
import common.WEActivity;
import common.WEFragment;
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

public class PhotoDetailActivity extends WEActivity<PhotoDetailPresenter> implements PhotoDetailContract.View, View.OnClickListener, IndicatorViewPager.OnIndicatorPageChangeListener {

    private final int RES_IMG_TYPE = 1;
    private final int RES_VIDEO_TYPE = 2;
    private final int RES_STORY_TYPE = 3;

    @Nullable
    @BindView(R.id.home_title)
    TextView homeTitle;

    @Nullable
    @BindView(R.id.detail_indicator)
    ScrollIndicatorView detailIndicator;

    @Nullable
    @BindView(R.id.detail_viewPager)
    ViewPager detailViewPager;

    int selectColor = Color.parseColor("#FFFFFF");//选中时数字的颜色
    int unSelectColor = Color.parseColor("#999999");//未选中时数字的颜色
    int backgroundColor = Color.parseColor("#FFA629");//背景色

    private PhotoDetailAdapter adapter;
    private IndicatorViewPager indicatorViewPager;

    private int photo_id;
    private PhotoDetails mPhotoDetails;
    private ArrayList<WEFragment> fragments = new ArrayList();
    ;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerPhotoDetailComponent
                .builder()
                .appComponent(appComponent)
                .photoDetailModule(new PhotoDetailModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected View initView() {
        return LayoutInflater.from(this).inflate(R.layout.activity_photo_detail, null, false);
    }

    @Override
    protected void initData() {
        initIndicatorPager();
        photo_id = getIntent().getIntExtra("photo_id",0);
        mPresenter.requestPhotoDetailRes(mWeApplication.getmUser().getId(), photo_id);
    }

    private void initIndicatorPager() {
        adapter = new PhotoDetailAdapter(this.getSupportFragmentManager());
        adapter.setFragments(fragments);
        detailIndicator.setOnTransitionListener(new OnTransitionTextListener().setColor(selectColor, unSelectColor));
        detailIndicator.setScrollBar(new SpringBar(getApplicationContext(), backgroundColor));//tab圆点的颜色
        detailViewPager.setOffscreenPageLimit(4);
        indicatorViewPager = new IndicatorViewPager(detailIndicator, detailViewPager);
        indicatorViewPager.setAdapter(adapter);
        indicatorViewPager.setCurrentItem(0, false);
        indicatorViewPager.setOnIndicatorPageChangeListener(this);
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

    public static void show(Context context,int photo_id) {
        Intent intent = new Intent(context, PhotoDetailActivity.class);
        intent.putExtra("photo_id",photo_id);
        context.startActivity(intent);
    }

    @Override
    @OnClick({R.id.back})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                killMyself();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    /**
     * 判断用户是痘已经缴费
     *
     * @param mPhotoDetails
     */
    @Override
    public void checkRes(PhotoDetails mPhotoDetails) {
        this.mPhotoDetails = mPhotoDetails;
        ArrayList<PhotoDetails.Product> buyProduct = (ArrayList<PhotoDetails.Product>) mPhotoDetails.getBuyproduct();
        ArrayList<PhotoDetails.Product> products = (ArrayList<PhotoDetails.Product>) mPhotoDetails.getProduct();
        ArrayList<PhotoDetails.Resource> resources = (ArrayList<PhotoDetails.Resource>) mPhotoDetails.getResource();

        for (PhotoDetails.Resource resource : resources) {
            int tempPro_id = resource.getPro_id();
            PhotoDetails.Product thisProduct = null;
            for (PhotoDetails.Product product : products) {
                if (product.getId() == tempPro_id) {
                    thisProduct = product;
                    break;
                }
            }
            if (thisProduct == null)
                continue;
            if (thisProduct.getPrice() == 0.00 || buyProduct.contains(thisProduct)) {
                switch (resource.getType()) {
                    case RES_IMG_TYPE:
                        fragments.add(ImageDetailFragment.newInstance(resource));
                        break;
                    case RES_VIDEO_TYPE:
                        fragments.add(VideoFragment.newInstance(resource.getUrl(),false));
                        break;
                    case RES_STORY_TYPE:
                        fragments.add(StoryFragment.newInstance(resource));
                        break;
                    default:
                        fragments.add(PayFragment.newInstance(thisProduct,photo_id));
                        break;
                }
            } else {
                fragments.add(PayFragment.newInstance(thisProduct,photo_id));
            }
        }

        adapter.notifyDataSetChanged();
    }


    @Override
    public void onIndicatorPageChange(int preItem, int currentItem) {
        String preName = fragments.get(preItem).getClass().getSimpleName();
        String currentName = fragments.get(currentItem).getClass().getSimpleName();
        Timber.tag(TAG).e("preName : " + preName + " ,currentName = " + currentName);
        if (preName.equals("VideoFragment")) {
            ((VideoFragment) fragments.get(preItem)).pauseLoad();
        }

        if (currentName.equals("VideoFragment")) {
            ((VideoFragment) fragments.get(currentItem)).startLoad();
        }
    }

    public void payProduct(){
        fragments.clear();
        mPresenter.requestPhotoDetailRes(mWeApplication.getmUser().getId(), photo_id);

    }
}