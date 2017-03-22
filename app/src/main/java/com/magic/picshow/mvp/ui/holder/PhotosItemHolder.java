package com.magic.picshow.mvp.ui.holder;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.widget.imageloader.ImageLoader;
import com.jess.arms.widget.imageloader.glide.GlideImageConfig;
import com.magic.picshow.R;
import com.magic.picshow.mvp.model.entity.Photos;

import butterknife.BindView;
import common.WEApplication;

/**
 * Created by yangfan
 */
public class PhotosItemHolder extends BaseHolder<Photos.PhotosItems>{

    @Nullable
    @BindView(R.id.iv_avatar)
    ImageView mAvater;
    @Nullable
    @BindView(R.id.tv_name)
    TextView mName;
    private ImageLoader mImageLoader;//用于加载图片的管理类,默认使用glide,使用策略模式,可替换框架
    private final WEApplication mApplication;

    public PhotosItemHolder(View itemView) {
        super(itemView);
        //可以在任何可以拿到Application的地方,拿到AppComponent,从而得到用Dagger管理的单例对象
        mApplication = (WEApplication) itemView.getContext().getApplicationContext();
        mImageLoader = mApplication.getAppComponent().imageLoader();
    }

    @Override
    public void setData(Photos.PhotosItems data) {
        mName.setText(data.getName());

        mImageLoader.loadImage(mApplication, GlideImageConfig
                .builder()
                .url(data.getCover_url())
                .imagerView(mAvater)
                .build());
    }

}
