package com.task.activity;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.renrentui.app.R;
import com.renrentui.util.ImageLoadManager;

import base.BaseActivity;

/**
 * Created by Administrator on 2016/1/18 0018.
 *
 * 大图展示
 */
public class ShowTaskMateriaBitPic extends BaseActivity{
    private ViewPager mViewPager;
    String[] urls = new String[6];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showtaskmateriabigpic_layout);
        urls[0] = "http://image.baidu.com/search/detail?ct=503316480&z=0&ipn=d&word=%E5%9B%BE%E7%89%87&step_word=&pn=0&spn=0&di=153605233540&pi=&rn=1&tn=baiduimagedetail&is=&istype=0&ie=utf-8&oe=utf-8&in=&cl=2&lm=-1&st=undefined&cs=1070902365%2C2619384777&os=1476752734%2C2742259091&simid=3473458608%2C462633313&adpicid=0&ln=1000&fr=&fmq=1453100248525_R&ic=undefined&s=undefined&se=&sme=&tab=0&width=&height=&face=undefined&ist=&jit=&cg=&bdtype=10&objurl=http%3A%2F%2Fpic.nipic.com%2F2007-11-09%2F2007119122519868_2.jpg&fromurl=ippr_z2C%24qAzdH3FAzdH3Fooo_z%26e3Bgtrtv_z%26e3Bv54AzdH3Ffi5oAzdH3F808lld_z%26e3Bip4s&gsm=0";
        urls[1] = "http://image.baidu.com/search/detail?ct=503316480&z=0&ipn=d&word=%E5%9B%BE%E7%89%87&step_word=&pn=1&spn=0&di=112989922650&pi=&rn=1&tn=baiduimagedetail&is=&istype=0&ie=utf-8&oe=utf-8&in=&cl=2&lm=-1&st=undefined&cs=1183223528%2C3058066243&os=123530706%2C110939732&simid=4263705247%2C758731806&adpicid=0&ln=1000&fr=&fmq=1453100248525_R&ic=undefined&s=undefined&se=&sme=&tab=0&width=&height=&face=undefined&ist=&jit=&cg=&bdtype=10&objurl=http%3A%2F%2Fpic14.nipic.com%2F20110522%2F7411759_164157418126_2.jpg&fromurl=ippr_z2C%24qAzdH3FAzdH3Fooo_z%26e3Bgtrtv_z%26e3Bv54AzdH3Ffi5oAzdH3FnAzdH3F8cAzdH3F9m8c9bahjj01b8dv_z%26e3Bip4s&gsm=0";
        urls[2] = "http://image.baidu.com/search/detail?ct=503316480&z=0&ipn=d&word=%E5%9B%BE%E7%89%87&step_word=&pn=2&spn=0&di=26190239680&pi=&rn=1&tn=baiduimagedetail&is=&istype=0&ie=utf-8&oe=utf-8&in=&cl=2&lm=-1&st=undefined&cs=1825165654%2C1935296637&os=1693314692%2C3224960954&simid=4137073490%2C592617987&adpicid=0&ln=1000&fr=&fmq=1453100248525_R&ic=undefined&s=undefined&se=&sme=&tab=0&width=&height=&face=undefined&ist=&jit=&cg=&bdtype=10&objurl=http%3A%2F%2Fimg2.3lian.com%2Fimg2007%2F19%2F33%2F005.jpg&fromurl=ippr_z2C%24qAzdH3FAzdH3Fooo_z%26e3Bnstwg_z%26e3Bv54AzdH3F2tuAzdH3FdaabAzdH3Fm-dbAzdH3F8ac0anl9md9_z%26e3Bip4s&gsm=0";
        urls[3] = "http://image.baidu.com/search/detail?ct=503316480&z=0&ipn=d&word=%E5%9B%BE%E7%89%87&step_word=&pn=3&spn=0&di=149282221330&pi=&rn=1&tn=baiduimagedetail&is=&istype=0&ie=utf-8&oe=utf-8&in=&cl=2&lm=-1&st=undefined&cs=128811874%2C840272376&os=922079446%2C3220597865&simid=3438637943%2C188811404&adpicid=0&ln=1000&fr=&fmq=1453100248525_R&ic=undefined&s=undefined&se=&sme=&tab=0&width=&height=&face=undefined&ist=&jit=&cg=&bdtype=10&objurl=http%3A%2F%2Fpic2.ooopic.com%2F01%2F03%2F51%2F25b1OOOPIC19.jpg&fromurl=ippr_z2C%24qAzdH3FAzdH3Fojtst_z%26e3B555rtv_z%26e3Bv54AzdH3Fojtst_8anc8dc_z%26e3Bip4s&gsm=0";
        urls[4] = "http://image.baidu.com/search/detail?ct=503316480&z=0&ipwordn=d&=%E5%9B%BE%E7%89%87&step_word=&pn=4&spn=0&di=10061234040&pi=&rn=1&tn=baiduimagedetail&is=&istype=0&ie=utf-8&oe=utf-8&in=&cl=2&lm=-1&st=undefined&cs=2651666712%2C3423349209&os=86336067%2C208014894&simid=4062530830%2C421456190&adpicid=0&ln=1000&fr=&fmq=1453100248525_R&ic=undefined&s=undefined&se=&sme=&tab=0&width=&height=&face=undefined&ist=&jit=&cg=&bdtype=10&objurl=http%3A%2F%2Fpic25.nipic.com%2F20121209%2F9252150_194258033000_2.jpg&fromurl=ippr_z2C%24qAzdH3FAzdH3Fooo_z%26e3Bgtrtv_z%26e3Bv54AzdH3Ffi5oAzdH3F9AzdH3F0lAzdH3F0dnbcndhmcnmd18d_z%26e3Bip4s&gsm=0";
        mViewPager = (ViewPager)findViewById(R.id.vp_pic);
        mViewPager.setAdapter(new MyPicPagerAdapter());
    }
    private void initView(){
        mViewPager = (ViewPager)findViewById(R.id.vp_pic);
    }

    class MyPicPagerAdapter extends PagerAdapter{
        @Override
        public int getCount() {
            return urls.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return false;
        }

        public MyPicPagerAdapter() {
            super();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View mView = LayoutInflater.from(context).inflate(R.layout.item_pagerview_layout, null);
            ImageView mIv=(ImageView)mView.findViewById(R.id.iv_1);
            ImageLoadManager.getLoaderInstace().disPlayNormalImg(urls[position], mIv,
                    R.drawable.icon_pic_default);
            return super.instantiateItem(container, position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            try {
                container.removeViewAt(position);
            }catch (Exception e){

            }
        }
    }
}
