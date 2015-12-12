package com.renrentui.resultmodel;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/12/10 0010.
 * 城市bean
 */
public class CityRegionModel implements Serializable {
    public String code;//城市编码
    public String name;//城市名称

    public CityRegionModel() {
        super();
    }

    public CityRegionModel(String code, String name) {
        super();
        this.code = code;
        this.name = name;
    }
}
