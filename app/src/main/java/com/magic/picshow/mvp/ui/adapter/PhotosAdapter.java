package com.magic.picshow.mvp.ui.adapter;

import android.view.View;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;
import com.magic.picshow.R;
import com.magic.picshow.mvp.model.entity.Photos;
import com.magic.picshow.mvp.model.entity.User;
import com.magic.picshow.mvp.ui.holder.PhotosItemHolder;
import com.magic.picshow.mvp.ui.holder.UserItemHolder;

import java.util.List;


/**
 * Created by jess on 9/4/16 12:57
 * Contact with jess.yan.effort@gmail.com
 */
public class PhotosAdapter extends DefaultAdapter<Photos.PhotosItems> {
    public PhotosAdapter(List<Photos.PhotosItems> infos) {
        super(infos);
    }

    @Override
    public BaseHolder<Photos.PhotosItems> getHolder(View v) {
        return new PhotosItemHolder(v);
    }

    @Override
    public int getLayoutId() {
        return R.layout.recycle_list;
    }
}
