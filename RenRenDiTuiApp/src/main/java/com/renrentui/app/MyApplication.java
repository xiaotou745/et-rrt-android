package com.renrentui.app;

import android.app.Application;
import android.content.Context;

import com.baidu.location.LocationClient;
import com.renrentui.resultmodel.CityRegionModel;

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
    private static CityRegionModel mCurrentLocation =new CityRegionModel();//当前城市信息
    private static CityRegionModel mLocalLocation = new CityRegionModel();//定位城市信息
    public static boolean isMessage=false;//是否有未读信息

    @Override
    public void onCreate() {
        super.onCreate();
        mAppContext = this;
        mLocClient = new LocationClient(getApplicationContext());
        initCurrentLocation();
        initLocation();
    }

    /**
     * 初始化当前位置
     */
    public void initCurrentLocation(){
        mCurrentLocation.code = "110100";
        mCurrentLocation.name = "北京市";

    }
    /**
     * 定位城市
     */
    public void initLocation(){
        mLocalLocation.code = "";
        mLocalLocation.name = "";

    }

    public static void setmCurrentLocation(CityRegionModel currentLocation) {
        mCurrentLocation = currentLocation;
    }

    public static void setmLocalLocation(CityRegionModel localLocation) {
        mLocalLocation = localLocation;
    }

    public LocationClient getmLocClient() {
        return mLocClient;
    }

    public static CityRegionModel getmCurrentLocation() {
        return mCurrentLocation;
    }

    public static  CityRegionModel getmLocalLocation() {
        return mLocalLocation;
    }
}
