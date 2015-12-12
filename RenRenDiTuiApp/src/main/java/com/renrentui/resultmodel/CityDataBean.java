package com.renrentui.resultmodel;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Administrator on 2015/12/10 0010.
 * 获取城市列表数据
 */
public class CityDataBean implements Serializable {
    public String version;
    public ArrayList<CityRegionModel> hotRegionModel;
    public ArrayList<CityFirstLetterRegionModel>firstLetterRegionModel;

    public CityDataBean() {
        super();
    }

    public CityDataBean(String version, ArrayList<CityRegionModel> hotRegionModel, ArrayList<CityFirstLetterRegionModel> firstLetterRegionModel) {
        super();
        this.version = version;
        this.hotRegionModel = hotRegionModel;
        this.firstLetterRegionModel = firstLetterRegionModel;
    }
}
