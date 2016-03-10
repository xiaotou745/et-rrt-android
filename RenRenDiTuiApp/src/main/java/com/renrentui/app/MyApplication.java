package com.renrentui.app;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.baidu.location.LocationClient;
import com.renrentui.resultmodel.CityRegionModel;
import com.renrentui.util.SharedPreferencesUtil;
import com.share.ShareUtils;

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
    //private static CityRegionModel mLocationCity = new CityRegionModel();//定位城市信息
    public static CityRegionModel mDataCity = new CityRegionModel ();//数据城市信息（）
    public static boolean isMessage=false;//是否有未读信息
    public static boolean isChangeCity= true;//是否提示切换城市
    public static boolean isLocationCity = false;//是否需要定位城市

    @Override
    public void onCreate() {
        super.onCreate();
        mAppContext = this;
        mLocClient = new LocationClient(getApplicationContext());
        isLocationCity = true;
        //initCurrentLocation();
      //  initCurrentCity();
       // initLocationCity();
        initDataCity();
        //分享信息初始化
        ShareUtils.initUMShareControl();
    }

//    /**
//     * 初始化当前位置
//     */
//    public void initCurrentLocation(){
//        mCurrentLocation.code = "110100";
//        mCurrentLocation.name = "北京市";
//
//    }
//    public void initLocationCity(){
//        SharedPreferencesUtil mSharedPreferencesUtils = new SharedPreferencesUtil(mAppContext,SharedPreferencesUtil.STR_SHAREPREFERENCE_FILE_NAME);
//        mLocationCity =  mSharedPreferencesUtils.getCurrentLocadCity();
//    }

    /**(
     * 初始化数据城市
     */
    private void initDataCity(){
        SharedPreferencesUtil mSharedPreferencesUtils = new SharedPreferencesUtil(mAppContext,SharedPreferencesUtil.STR_SHAREPREFERENCE_FILE_NAME);
        mDataCity = mSharedPreferencesUtils.getDataCity();
    }
 /**
  * 设置数据城市
     */
    public static void setDataCity(CityRegionModel city) {
        SharedPreferencesUtil mSharedPreferencesUtils = new SharedPreferencesUtil(mAppContext,SharedPreferencesUtil.STR_SHAREPREFERENCE_FILE_NAME);
        mSharedPreferencesUtils.setDataCity(city);
        mDataCity = city;
    }

//    /**
//     * 获取当前城市
//     * @return
//     */
//    public static CityRegionModel getDataCity() {
//        if(mDataCity==null || TextUtils.isEmpty(mDataCity.name)){
//            SharedPreferencesUtil mSharedPreferencesUtils = new SharedPreferencesUtil(mAppContext,SharedPreferencesUtil.STR_SHAREPREFERENCE_FILE_NAME);
//            mDataCity = mSharedPreferencesUtils.getDataCity();
//        }
//        return mDataCity;
//    }

    /**
     * 设置默认的数据城市信息
     */
    public static void setDefaultDataCity() {
            SharedPreferencesUtil mSharedPreferencesUtils = new SharedPreferencesUtil(mAppContext,SharedPreferencesUtil.STR_SHAREPREFERENCE_FILE_NAME);
        mSharedPreferencesUtils.setDataCity(mDataCity);

    }
//    /**
//     * 定位城市
//     */
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
