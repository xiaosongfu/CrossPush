package com.fuxiaosong.wotui.activity.main.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;

import java.util.ArrayList;

/**
 * Created by fuxiaosong on 16/9/2.
 */
public class MainViewpagerAdapter extends PagerAdapter {

    ArrayList<View> viewList = null;

    public MainViewpagerAdapter(ArrayList<View> viewList){
        this.viewList = viewList;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = viewList.get(position);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
        container.removeView(viewList.get(position));
    }

    @Override
    public int getCount() {
        return viewList.size();
    }
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
