package com.renrentui.resultmodel;

import android.text.TextUtils;
import android.util.Log;

import java.io.Serializable;
import java.text.DecimalFormat;

/**
 * Created by Administrator on 2015/12/9 0009.
 * 我的任务列表详情内容bean
 */
public class MyTaskContentBean implements Serializable {
  public String   taskId ;//任务Id
    public String   taskGeneralInfo ;//任务描述
    public String   taskName ;//任务名称
    public double   amount ;//任务单价
    public int    taskType ;//任务类型描述（1分享类，2下载类，3签约类）
    public String   taskTypeName ;//任务类型名称
    public String   logo ;//logo图片地址
    public String   auditWaitNum ;//待审核的资料数量(签约类任务才有效)
    public String   auditPassNum ;//审核通过的资料数量(签约类任务才有效)
    public String   auditRefuseNum ;//审核不通过的资料数量(签约类任务才有效)
    public String   complateNum ;//完成数量(分享类和下载类任务才有效)
    public String   ctId;//地推关系

    public String getAmount() {
        DecimalFormat df = new DecimalFormat("0.00");
        String db = df.format(amount);
        return db;
    }
    public String getWaitNum(){
        if(TextUtils.isEmpty(auditWaitNum)){
            return "0";
        }else{
            return auditWaitNum;
        }
    }
    public String getPassNum(){
        if(TextUtils.isEmpty(auditPassNum)){
            return "0";
        }else{
            return auditPassNum;
        }
    }
    public String getRefuseNum(){
        if(TextUtils.isEmpty(auditRefuseNum)){
            return "0";
        }else{
            return auditRefuseNum;
        }
    }
    public String getComplateNum(){
        if(TextUtils.isEmpty(complateNum)){
            return "0";
        }else{
            return complateNum;
        }
    }


    @Override
    public String toString() {
        return "MyTaskContentBean{" +
                "taskId='" + taskId + '\'' +
                ", taskGeneralInfo='" + taskGeneralInfo + '\'' +
                ", taskName='" + taskName + '\'' +
                ", amount='" + getAmount() + '\'' +
                ", taskType='" + taskType + '\'' +
                ", taskTypeName='" + taskTypeName + '\'' +
                ", logo='" + logo + '\'' +
                ", auditWaitNum='" + auditWaitNum + '\'' +
                ", auditPassNum='" + auditPassNum + '\'' +
                ", auditRefuseNum='" + auditRefuseNum + '\'' +
                ", complateNum='" + complateNum + '\'' +
                '}';
    }
}
