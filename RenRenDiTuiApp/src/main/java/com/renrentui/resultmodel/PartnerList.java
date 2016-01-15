package com.renrentui.resultmodel;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/1/14 0014.
 * 任务参与人
 */
public class PartnerList implements Serializable {

    private String ctId ;//地推员和任务的绑定关系id
    private String clienterId ;//地推员id
    private String clienterName ;//地推员姓名
    private String phoneNo ;//地推员手机号
    private String headImage ;//地推员头像完全地址

    public PartnerList() {
    }

    public PartnerList(String ctId, String clienterId, String clienterName, String phoneNo, String headImage) {
        this.ctId = ctId;
        this.clienterId = clienterId;
        this.clienterName = clienterName;
        this.phoneNo = phoneNo;
        this.headImage = headImage;
    }

    public void setCtId(String ctId) {
        this.ctId = ctId;
    }

    public void setClienterId(String clienterId) {
        this.clienterId = clienterId;
    }

    public void setClienterName(String clienterName) {
        this.clienterName = clienterName;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public String getCtId() {
        return ctId;
    }

    public String getClienterId() {
        return clienterId;
    }

    public String getClienterName() {
        return clienterName;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public String getHeadImage() {
        return headImage;
    }

    @Override
    public String toString() {
        return "PartnerList{" +
                "ctId='" + ctId + '\'' +
                ", clienterId='" + clienterId + '\'' +
                ", clienterName='" + clienterName + '\'' +
                ", phoneNo='" + phoneNo + '\'' +
                ", headImage='" + headImage + '\'' +
                '}';
    }
}
