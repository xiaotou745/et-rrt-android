package com.renrentui.resultmodel;

/**
 * Created by Administrator on 2015/12/10 0010.
 * 获取城市
 */
public class RSGetCityModel extends RSBase {
    public CityDataBean data;

    public RSGetCityModel(String Code, String Msg, CityDataBean data) {
        super(Code, Msg);
        this.data = data;
    }

    public RSGetCityModel() {
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return "RSCheckVersion[data=" + data + "]";
    }
}
