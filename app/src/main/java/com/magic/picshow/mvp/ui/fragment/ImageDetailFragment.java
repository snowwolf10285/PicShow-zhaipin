package com.magic.picshow.mvp.ui.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.liji.imagezoom.widget.PhotoViewAttacher;
import com.magic.picshow.R;
import com.magic.picshow.mvp.model.entity.PhotoDetails;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import common.AppComponent;
import common.WEFragment;

/**
 * Created by snowwolf on 17/2/26.
 */

public class ImageDetailFragment extends WEFragment {


    @BindView(R.id.image)
    ImageView mImageView;
    @BindView(R.id.loading)
    ProgressBar progressBar;

    private String mImageUrl;
    private PhotoViewAttacher mAttacher;
    private PhotoDetails.Resource mResource;

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {

    }

    @Override
    protected View initView() {
        return LayoutInflater.from(getActivity()).inflate(R.layout.fragment_image_detail, null, false);
    }

    @Override
    protected void initData() {
        initPhotoViewAttacher();
    }


    public static ImageDetailFragment newInstance(PhotoDetails.Resource resource) {
        final ImageDetailFragment fragment = new ImageDetailFragment();
        final Bundle args = new Bundle();
        args.putSerializable("mResource",resource);
        fragment.setArguments(args);
        return fragment;
    }


    public ImageDetailFragment() {
    }

    public ImageDetailFragment(PhotoDetails.Resource resource) {
        mResource = resource;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mResource = (PhotoDetails.Resource)getArguments().getSerializable("mResource");
        mImageUrl = mResource.getUrl();
    }

    private void initPhotoViewAttacher(){
        mAttacher = new PhotoViewAttacher(mImageView);

        mAttacher.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {

            @Override
            public void onPhotoTap(View arg0, float arg1, float arg2) {
                getActivity().finish();
            }
        });
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        final View v = inflater.inflate(R.layout.fragment_image_detail, container, false);
//        mImageView = (ImageView) v.findViewById(R.id.image);
//
//
//        progressBar = (ProgressBar) v.findViewById(R.id.loading);
//        ButterKnife.bind(this, v);
//        return v;
//    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


//        mImageLoader = ((WEApplication)getContext().getApplicationContext()).getAppComponent().imageLoader();
//        mImageLoader.loadImage(getContext(), GlideImageConfig
//                .builder()
//                .url(mImageUrl)
//                .imagerView(mImageView)
//                .build());

        if (!ImageLoader.getInstance().isInited()) {
            DisplayImageOptions defaultOptions = new DisplayImageOptions
                    .Builder()
                    .showImageForEmptyUri(R.drawable.empty_photo)
                    .showImageOnFail(R.drawable.empty_photo)
                    .cacheInMemory(true)
                    .cacheOnDisc(true)
                    .build();
            ImageLoaderConfiguration config = new ImageLoaderConfiguration
                    .Builder(getActivity().getApplicationContext())
                    .defaultDisplayImageOptions(defaultOptions)
                    .discCacheSize(50 * 1024 * 1024)//
                    .discCacheFileCount(100)//缓存一百张图片
                    .writeDebugLogs()
                    .build();
            ImageLoader.getInstance().init(config);
        }

        ImageLoader.getInstance().displayImage(mImageUrl, mImageView, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                String message = null;
                switch (failReason.getType()) {
                    case IO_ERROR:
                        message = "下载错误";
                        break;
                    case DECODING_ERROR:
                        message = "图片无法显示";
                        break;
                    case NETWORK_DENIED:
                        message = "网络有问题，无法下载";
                        break;
                    case OUT_OF_MEMORY:
                        message = "图片太大无法显示";
                        break;
                    case UNKNOWN:
                        message = "未知的错误";
                        break;
                }
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                progressBar.setVisibility(View.GONE);
                mAttacher.update();
            }
        });

    }
}
