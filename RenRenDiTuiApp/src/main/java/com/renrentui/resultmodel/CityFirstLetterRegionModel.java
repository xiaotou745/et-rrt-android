package com.renrentui.resultmodel;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Administrator on 2015/12/10 0010.
 * 城市列表
 */
public class CityFirstLetterRegionModel implements Serializable{
    public String firstLetter;
    public ArrayList<CityRegionModel> regionModel;

    public CityFirstLetterRegionModel() {
        super();
    }

    public CityFirstLetterRegionModel(String firstLetter, ArrayList<CityRegionModel> regionModel) {
        super();
        this.firstLetter = firstLetter;
        this.regionModel = regionModel;
    }
}
