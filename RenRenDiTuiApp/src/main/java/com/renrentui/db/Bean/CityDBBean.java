package com.renrentui.db.Bean;

/**
 * Created by Administrator on 2015/12/11 0011.
 * 城市数据bean
 */
public class CityDBBean {
    public  String CITY_FIRST_LETTER = "";//城市第一字母
    public  String CITY_CODE = "";//城市code
    public  String CITY_NAME = "";//城市名称
    public String  CITY_TYPE= "";//是否热门0:定位城市 1:当前城市 2：最近  3：热门城市  4：城市列表
    public  String VERSION = "";//版本

    public void setCITY_FIRST_LETTER(String CITY_FIRST_LETTER) {
        this.CITY_FIRST_LETTER = CITY_FIRST_LETTER;
    }

    public void setCITY_CODE(String CITY_CODE) {
        this.CITY_CODE = CITY_CODE;
    }

    public void setCITY_NAME(String CITY_NAME) {
        this.CITY_NAME = CITY_NAME;
    }



    public void setVERSION(String VERSION) {
        this.VERSION = VERSION;
    }

    public String getCITY_FIRST_LETTER() {
        return CITY_FIRST_LETTER;
    }

    public String getCITY_CODE() {
        return CITY_CODE;
    }

    public String getCITY_NAME() {
        return CITY_NAME;
    }



    public String getVERSION() {
        return VERSION;
    }

    public String getCITY_TYPE() {
        return CITY_TYPE;
    }

    public void setCITY_TYPE(String CITY_TYPE) {
        this.CITY_TYPE = CITY_TYPE;
    }

    public CityDBBean() {
        super();
    }

    public CityDBBean(String CITY_FIRST_LETTER, String CITY_CODE, String CITY_NAME, String CITY_TYPE, String VERSION) {
        super();
        this.CITY_FIRST_LETTER = CITY_FIRST_LETTER;
        this.CITY_CODE = CITY_CODE;
        this.CITY_NAME = CITY_NAME;
        this.CITY_TYPE = CITY_TYPE;
        this.VERSION = VERSION;
    }
    public CityDBBean( String CITY_NAME,String CITY_FIRST_LETTER) {
        super();
        this.CITY_FIRST_LETTER = CITY_FIRST_LETTER;
        this.CITY_NAME = CITY_NAME;
    }
}
