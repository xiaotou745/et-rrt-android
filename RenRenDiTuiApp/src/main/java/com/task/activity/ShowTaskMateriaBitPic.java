package com.task.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.TextView;

import com.renrentui.app.R;
import com.task.view.HackyViewPager;

import java.util.ArrayList;

import base.BaseFragmentActivity;

/**
 * Created by Administrator on 2016/1/18 0018.
 *
 * 大图展示
 */
public class ShowTaskMateriaBitPic extends BaseFragmentActivity {
    public static final String STR_STATE_POSIION = "STATE_POSITION";//position
    public static final String STR_STATE_URLS = "STATE_URLS";//url 数据集合

    private HackyViewPager mViewPager;
    private TextView mPageIndicator;
    private int pagerPosition;
    private ArrayList<String> urls = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showtaskmateriabigpic_layout);
        //获取传递的数据
        pagerPosition = this.getIntent().getIntExtra(STR_STATE_POSIION,0);
        urls = this.getIntent().getStringArrayListExtra(STR_STATE_URLS);
        //图片展示
        mViewPager = (HackyViewPager)findViewById(R.id.pic_viewpager);
        mViewPager.setAdapter(new MyPicPagerAdapter(getSupportFragmentManager(), urls));
        mPageIndicator = (TextView)findViewById(R.id.indicator);

        CharSequence text = getString(R.string.viewpager_indicator, 1, mViewPager.getAdapter().getCount());
        mPageIndicator.setText(text);
        //更新下标
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                CharSequence text = getString(R.string.viewpager_indicator, i+1, mViewPager.getAdapter().getCount());
                mPageIndicator.setText(text);
                //更新下标
            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        if(savedInstanceState!=null){
            pagerPosition = savedInstanceState.getInt(STR_STATE_POSIION);
        }
        //设置当前下标
        mViewPager.setCurrentItem(pagerPosition);
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(STR_STATE_POSIION, mViewPager.getCurrentItem());
    }

    /**
     * 图片适配器
     */
    class MyPicPagerAdapter extends FragmentStatePagerAdapter {
        public ArrayList<String> fileList;

        public MyPicPagerAdapter(FragmentManager fm, ArrayList<String> fileList) {
            super(fm);
            this.fileList = fileList;
        }

        @Override
        public int getCount() {
            return fileList == null ? 0 : fileList.size();
        }


        @Override
        public Fragment getItem(int position) {
            String url = fileList.get(position);
            return ImageDetailFragment.newInstance(url);
        }
    }
}
