package com.renrentui.resultmodel;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by Administrator on 2015/12/1 0001.
 * 任务详情基本信息
 */
public class TaskBeanInfo implements Serializable {

    /**
     * 任务显示名称
     */
    public String taskTitle;
    /**
     * 任务单价
     */
    public double amount;
    /**
     * 结束时间
     */
    public String endTime;
    public String taskGeneralInfo;//任务简述
    public int isHad;//1：已领取 0 未领取
    public String logo;//发布商家LOGO地址
    public String auditCycle;//任务审核周期
    public int taskType ;//任务类型1 签约任务 2 分享任务 3 下载任务
    public String taskTypeName;//任务类型名称;
    public String hotLine;//咨询电话
    public int ctId;//ishad=1时才有 否则为0 地推任务关系ID


    public String getAmount() {

        DecimalFormat df = new DecimalFormat("0.00");
        String db = df.format(amount);
        return db;
    }
public  TaskBeanInfo(){
    super();
}
    public TaskBeanInfo(String taskTitle,double amount,String endTime,String taskGeneralInfo,
                        int isHad,String logo,String auditCycle,int taskType,
                        String taskTypeName,String hotLine ,int ctId) {

        this.taskTitle =taskTitle;
        this.amount =amount;
        this.endTime = endTime;
        this.taskGeneralInfo = taskGeneralInfo;
        this.isHad =isHad;
        this.logo = logo;
        this.auditCycle =auditCycle;
        this.taskType = taskType;
        this.taskTypeName = taskTypeName;
        this.hotLine = hotLine;
        this.ctId = ctId;
    }

    @Override
    public String toString() {
        return "{taskTitle=" + taskTitle + ",amount=" + amount
                + ",endTime=" + endTime + ",taskGeneralInfo="
                + taskGeneralInfo + ",isHad=" + isHad + ",logo="
                + logo + ",auditCycle=" + auditCycle + ",taskType="
                + taskType + ",taskTypeName=" + taskTypeName + ",hotLine=" + hotLine
                + ",ctId=" + ctId +  "}";
    }
}
