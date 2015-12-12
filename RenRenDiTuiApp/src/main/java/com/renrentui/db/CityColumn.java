package com.renrentui.db;

import android.provider.BaseColumns;

/**
 * Created by Administrator on 2015/12/10 0010.
 * 数据库中城市信息
 */
public class CityColumn implements BaseColumns {
    public static final String CITY_FIRST_LETTER = "CITY_FIRST_LETTER";//城市第一字母
    public static final String CITY_CODE = "CITY_CODE";//城市code
    public static final String CITY_NAME = "CITY_NAME";//城市名称
    public static final String CITY_TYPE = "CITY_TYPE";//是否热门(0:定位城市 1:当前城市  2：热门城市  3：城市列表  4:历史信息)
    public static final String VERSION = "VERSION";//版本

    public static final String[] CITY_PROJECTION = {_ID,CITY_FIRST_LETTER,CITY_CODE,CITY_NAME,CITY_TYPE,VERSION};
}
