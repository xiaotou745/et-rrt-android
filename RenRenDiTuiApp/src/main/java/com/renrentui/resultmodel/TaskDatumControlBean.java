package com.renrentui.resultmodel;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/12/4 0004.
 * 任务模板 或任务模板详情 模板组详情
 */
public class TaskDatumControlBean implements Serializable {

   public String  orderNum ;//排序号
    public String controlKey  ;//提交任务时的key
    public String controlTypeId  ;//控件类型 1是Text 文本框 2是FileUpload 图片上传
    public String controlTitle ;// 控件说明
    public String controlData  ;//控件选项
    public String defaultValue ;// 控件默认值（获取资料模板时有效）
    public String controlValue  ;//控件值(获取资料详情时有效)

    public TaskDatumControlBean(){
        super();
    }


    public TaskDatumControlBean(String controlValue, String orderNum, String controlKey,
                                String controlTypeId, String controlTitle,
                                String controlData, String defaultValue) {
        super();
        this.controlValue = controlValue;
        this.orderNum = orderNum;
        this.controlKey = controlKey;
        this.controlTypeId = controlTypeId;
        this.controlTitle = controlTitle;
        this.controlData = controlData;
        this.defaultValue = defaultValue;
    }

    @Override
    public String toString() {
        return "{" +
                "orderNum='" + orderNum + '\'' +
                ", controlKey='" + controlKey + '\'' +
                ", controlTypeId='" + controlTypeId + '\'' +
                ", controlTitle='" + controlTitle + '\'' +
                ", controlData='" + controlData + '\'' +
                ", defaultValue='" + defaultValue + '\'' +
                ", controlValue='" + controlValue + '\'' +
                '}';
    }
}
