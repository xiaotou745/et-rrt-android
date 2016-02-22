package com.renrentui.app;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.baidu.location.LocationClient;
import com.renrentui.resultmodel.CityRegionModel;
import com.renrentui.util.SharedPreferencesUtil;

/**
 * Created by Administrator on 2015/12/11 0011.
 * 人人地推
 */
public class MyApplication extends Application{
    private static Context mAppContext;
    private final String TAG = MyApplication.class.getSimpleName();
    /**
     * 定位相关
     */
    public LocationClient mLocClient;
    private static CityRegionModel mCurrentCity =new CityRegionModel();//当前城市信息
    //private static CityRegionModel mLocationCity = new CityRegionModel();//定位城市信息
    public static CityRegionModel mDefaultCity = new CityRegionModel ("110100","北京市");//默认城市信息
    public static boolean isMessage=false;//是否有未读信息

    @Override
    public void onCreate() {
        super.onCreate();
        mAppContext = this;
        mLocClient = new LocationClient(getApplicationContext());
        //initCurrentLocation();
        initCurrentCity();
       // initLocationCity();
    }

//    /**
//     * 初始化当前位置
//     */
//    public void initCurrentLocation(){
//        mCurrentLocation.code = "110100";
//        mCurrentLocation.name = "北京市";
//
//    }
    public void initCurrentCity(){
        SharedPreferencesUtil mSharedPreferencesUtils = new SharedPreferencesUtil(mAppContext,SharedPreferencesUtil.STR_SHAREPREFERENCE_FILE_NAME);
        mCurrentCity =  mSharedPreferencesUtils.getCurrentLocadCity();
    }

    /**
     * 设置当前城市
     * @param city
     */
    public static void setCurrentCity(CityRegionModel city) {
        SharedPreferencesUtil mSharedPreferencesUtils = new SharedPreferencesUtil(mAppContext,SharedPreferencesUtil.STR_SHAREPREFERENCE_FILE_NAME);
        mSharedPreferencesUtils.setCurrentLocadCity(city);
        mCurrentCity = city;
    }

    /**
     * 获取当前城市
     * @return
     */
    public static CityRegionModel getCurrentCity() {
        if(mCurrentCity==null || TextUtils.isEmpty(mCurrentCity.name)){
            SharedPreferencesUtil mSharedPreferencesUtils = new SharedPreferencesUtil(mAppContext,SharedPreferencesUtil.STR_SHAREPREFERENCE_FILE_NAME);
            mCurrentCity = mSharedPreferencesUtils.getCurrentLocadCity();
        }
        return mCurrentCity;
    }
    /**
     * 定位城市
     */
//    public void initLocationCity(){
//        mLocationCity.code = "";
//        mLocationCity.name = "";
//    }
//    public static CityRegionModel getLocationCity() {
//        return  mLocationCity;
//    }
//    public static void setLocationCity(CityRegionModel city){
//        mLocationCity = city;
//    }

    public LocationClient getmLocClient() {
        return mLocClient;
    }


}
