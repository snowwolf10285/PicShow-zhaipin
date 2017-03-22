package com.magic.picshow.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.magic.picshow.R;
import com.magic.picshow.mvp.ui.fragment.PhotosFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import common.AppComponent;
import common.WEActivity;

public class SameCityActivity extends WEActivity implements View.OnClickListener {

    public final static int CITY_PHOTOS = 1;
    public final static int MINE_PHOTOS = 2;

    private int type;

    @Nullable
    @BindView(R.id.home_title)
    TextView homeTitle;
    private int photosType;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    protected View initView() {
        return LayoutInflater.from(this).inflate(R.layout.activity_same_city, null, false);
    }

    @Override
    protected void initData() {
        photosType = getIntent().getIntExtra("photosType", 1);
        if (photosType == MINE_PHOTOS){
            homeTitle.setText(R.string.mine_photos);
            type = MINE_PHOTOS;
        }else{
            homeTitle.setText(R.string.same_city);
            type = CITY_PHOTOS;
        }

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        PhotosFragment photosFragment = PhotosFragment.newInstance(0,type);
        transaction.add(R.id.same_city_frame, photosFragment, "sameCity").commit();
    }


    public static void show(Context context, int photosType) {
        Intent intent = new Intent(context, SameCityActivity.class);
        intent.putExtra("photosType", photosType);
        context.startActivity(intent);
    }

    @Override
    @OnClick({R.id.back})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }
}
