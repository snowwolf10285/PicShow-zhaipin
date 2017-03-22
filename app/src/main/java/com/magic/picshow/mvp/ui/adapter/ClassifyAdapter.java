package com.magic.picshow.mvp.ui.adapter;

import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.magic.picshow.R;
import com.magic.picshow.app.utils.DisplayUtil;
import com.magic.picshow.mvp.model.entity.Classify;
import com.magic.picshow.mvp.ui.fragment.PhotosFragment;
import com.shizhefei.view.indicator.IndicatorViewPager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by snowwolf on 17/1/22.
 */

public class ClassifyAdapter extends IndicatorViewPager.IndicatorFragmentPagerAdapter {
//    private String[] versions = {"Cupcake", "Donut", "Éclair", "Froyo", "Gingerbread", "Honeycomb", "Ice Cream Sandwich", "Jelly Bean", "KitKat", "Lolipop", "Marshmallow"};
//    private String[] names = {"纸杯蛋糕", "甜甜圈", "闪电泡芙", "冻酸奶", "姜饼", "蜂巢", "冰激凌三明治", "果冻豆", "奇巧巧克力棒", "棒棒糖", "棉花糖"};
    private Map map = new HashMap<Integer,PhotosFragment>();
    private List<Classify> mClassifyList;

    public ClassifyAdapter(FragmentManager fragmentManager,List<Classify> mClassifyList) {
        super(fragmentManager);
        this.mClassifyList = mClassifyList;
    }

    @Override
    public int getCount() {
        return mClassifyList.size();
    }

    public void setClassifyList(List<Classify> mClassifyList){
        this.mClassifyList = mClassifyList;
        notifyDataSetChanged();
    }

    @Override
    public View getViewForTab(int position, View convertView, ViewGroup container) {
        if (convertView == null) {
            convertView =  LayoutInflater.from(container.getContext()).inflate(R.layout.tab_top, container, false);
        }
        TextView textView = (TextView) convertView;
        textView.setText(mClassifyList.get(position).getClassify_name());

        int witdh = getTextWidth(textView);
        int padding = DisplayUtil.dipToPix(container.getContext(), 8);
        //因为wrap的布局 字体大小变化会导致textView大小变化产生抖动，这里通过设置textView宽度就避免抖动现象
        //1.3f是根据上面字体大小变化的倍数1.3f设置
        textView.setWidth((int) (witdh * 1.3f) + padding);

        return convertView;
    }

    @Override
    public Fragment getFragmentForPage(int position) {
        PhotosFragment fragment = (PhotosFragment) map.get(mClassifyList.get(position).getClassify_id());
        if (fragment == null){
            fragment = PhotosFragment.newInstance(mClassifyList.get(position).getClassify_id(),0);
            map.put(mClassifyList.get(position).getClassify_id(),fragment);
        }
        return fragment;
    }

    @Override
    public int getItemPosition(Object object) {
        //这是ViewPager适配器的特点,有两个值 POSITION_NONE，POSITION_UNCHANGED，默认就是POSITION_UNCHANGED,
        // 表示数据没变化不用更新.notifyDataChange的时候重新调用getViewForPage
        return PagerAdapter.POSITION_NONE;
    }

    private int getTextWidth(TextView textView) {
        if (textView == null) {
            return 0;
        }
        Rect bounds = new Rect();
        String text = textView.getText().toString();
        Paint paint = textView.getPaint();
        paint.getTextBounds(text, 0, text.length(), bounds);
        int width = bounds.left + bounds.width();
        return width;
    }
}
