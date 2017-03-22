package com.magic.picshow.mvp.ui.adapter;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.liji.imagezoom.activity.ImageDetailFragment;
import com.magic.picshow.R;
import com.magic.picshow.app.utils.DisplayUtil;
import com.magic.picshow.mvp.ui.fragment.ImageFragment;
import com.magic.picshow.mvp.ui.fragment.PayFragment;
import com.magic.picshow.mvp.ui.fragment.StoryFragment;
import com.magic.picshow.mvp.ui.fragment.VideoFragment;
import com.shizhefei.view.indicator.IndicatorViewPager;

import java.util.ArrayList;
import java.util.List;

import common.WEFragment;

/**
 * Created by snowwolf on 17/1/30.
 */

public class PhotoDetailAdapter extends IndicatorViewPager.IndicatorFragmentPagerAdapter {

    private List<WEFragment> fragments = new ArrayList<>();

    int selectColor = Color.parseColor("#00FF00");
    int unSelectColor = Color.parseColor("#FF0000");
//    private Map map = new HashMap<String,WEFragment>();

    public PhotoDetailAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    public void addFragment(WEFragment mFragment) {
        if (fragments == null)
            fragments = new ArrayList<>();
        fragments.add(mFragment);
        notifyDataSetChanged();
    }

    public void setFragments(List<WEFragment> mFragments){
        fragments = mFragments;
        notifyDataSetChanged();
    }

    @Override
    public View getViewForTab(int position, View convertView, ViewGroup container) {
        if (convertView == null) {
            convertView = LayoutInflater.from(container.getContext()).inflate(R.layout.tab_top, container, false);
        }
        TextView textView = (TextView) convertView;
        int padding = DisplayUtil.dipToPix(container.getContext(), 15);//tab之间的间隔
        textView.setPadding(padding, 0, padding, 0);
        textView.setText(String.valueOf(position));
        textView.setTextColor(unSelectColor);

        return convertView;
    }

    @Override
    public Fragment getFragmentForPage(int position) {
        return fragments.get(position);
    }

    @Override
    public int getItemPosition(Object object) {
        //这是ViewPager适配器的特点,有两个值 POSITION_NONE，POSITION_UNCHANGED，默认就是POSITION_UNCHANGED,
        // 表示数据没变化不用更新.notifyDataChange的时候重新调用getViewForPage
        return PagerAdapter.POSITION_NONE;
    }

}
