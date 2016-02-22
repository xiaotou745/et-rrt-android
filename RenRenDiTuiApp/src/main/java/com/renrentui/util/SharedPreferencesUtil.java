package com.renrentui.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.renrentui.resultmodel.CityRegionModel;


/**
 * Created by Administrator on 2016/2/22 0022.
 *
 * 文件数据存储操作
 */
public class SharedPreferencesUtil {
    public static final String STR_SHAREPREFERENCE_FILE_NAME = "RENRENTUI_SHAREPREFERENC_COMMONS";
    public static final String STR_CITY_NAME_KEY = "CITY_NAME_KEY";//城市名称
    public static final String STR_CITY_CODE_KEY = "CITY_CODE_KEY";//城市编码
    private SharedPreferences sp;
    private  SharedPreferences.Editor editor;

    public SharedPreferencesUtil(Context context ,String fileName){
        if(TextUtils.isEmpty(fileName)){
            fileName = STR_SHAREPREFERENCE_FILE_NAME;
        }
        sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        editor = sp.edit();
    }

    /**
     * 保存单签定位城市信息
     * @param cityBean
     */
    public  void setCurrentLocadCity( CityRegionModel cityBean){
        if(cityBean==null){
            return;
        }
        editor.putString(STR_CITY_NAME_KEY,cityBean.name);
        editor.putString(STR_CITY_CODE_KEY,cityBean.code);
        editor.commit();
    }

    /**
     * 设置前保存的定位城市信息
     * @param cityName
     */
    public  void setCurrentLocadCityName( String cityName){
        if(TextUtils.isEmpty(cityName)){
            return;
        }
        editor.putString(STR_CITY_NAME_KEY,cityName);
        editor.commit();
    }

    /**
     * 前保存的定位城市信息
     * @param cityCode
     */
    public  void setCurrentLocadCityCode( String cityCode){
        if(TextUtils.isEmpty(cityCode)){
            return;
        }
        editor.putString(STR_CITY_CODE_KEY,cityCode);
        editor.commit();
    }
    /**
     * 获取当前保存的定位城市信息
     * @return
     */
    public CityRegionModel getCurrentLocadCity(){
        CityRegionModel bean = new CityRegionModel();
        bean.name = sp.getString(STR_CITY_NAME_KEY,"北京市");
        bean.code = sp.getString(STR_CITY_CODE_KEY, "110100");
        return bean;
    }

    /**
     * 获取当前保存的定位城市信息
     * @return
     */
    public String getCurrentLocadCityName(){
       return sp.getString(STR_CITY_NAME_KEY,"");

    }

    /**
     * S获取当前保存的定位城市信息
     * @return
     */
    public String getCurrentLocadCityCode(){
        return sp.getString(STR_CITY_CODE_KEY,"");
    }



}
