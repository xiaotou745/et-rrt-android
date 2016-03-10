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


    public  void setDataCity( CityRegionModel cityBean){
        if(cityBean==null){
            return;
        }
        editor.putString(STR_CITY_NAME_KEY,cityBean.name);
        editor.putString(STR_CITY_CODE_KEY,cityBean.code);
        editor.commit();
    }


    public  void setDataCityName( String cityName){
        if(TextUtils.isEmpty(cityName)){
            return;
        }
        editor.putString(STR_CITY_NAME_KEY,cityName);
        editor.commit();
    }

    /**
     * 设置当前数据城市
     * @param cityCode
     */
    public  void setDataCityCode( String cityCode){
        if(TextUtils.isEmpty(cityCode)){
            return;
        }
        editor.putString(STR_CITY_CODE_KEY,cityCode);
        editor.commit();
    }

//    /**
//     * 获取默认的城市信息
//     * @return
//     */
//    public CityRegionModel getDefaultDataCity(){
//        CityRegionModel bean = new CityRegionModel();
//        bean.name  = "北京市";
//        bean.code = "110100";
//        return bean;
//    }
    /**
     * 获取默认的城市信息
     * @return
     */
    public CityRegionModel getDataCity(){
        CityRegionModel bean = new CityRegionModel();
        bean.name  =sp.getString(STR_CITY_NAME_KEY,"北京市");
        bean.code =sp.getString(STR_CITY_CODE_KEY,"110100");
        return bean;
    }
    /**
     * 获取当前数据城市name
     * @return
     */
    public String getDataCityName(){
       return sp.getString(STR_CITY_NAME_KEY,"");

    }


    /**
     * 获取当前数据城市code
     * @return
     */
    public String getDataCityCode(){
        return sp.getString(STR_CITY_CODE_KEY,"");
    }



}
