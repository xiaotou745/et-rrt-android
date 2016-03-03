package com.renrentui.resultmodel;

/**
 * Created by Administrator on 2016/3/2 0002.
 * 我的合伙人信息 bean
 */
public class MyFriendsBean  {
    private String id;
    private String clienterName;
    private String phoneNo;
    private String headImage;
    private String createTime;//注册时间

    public MyFriendsBean() {
    }

    public MyFriendsBean(String id, String clienterName, String phoneNo, String headImage, String createTime) {
        this.id = id;
        this.clienterName = clienterName;
        this.phoneNo = phoneNo;
        this.headImage = headImage;
        this.createTime = createTime;
    }

    public void setId(String id) {
        this.id = id;
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

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getId() {
        return id;
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

    public String getCreateTime() {
        return createTime;
    }

    @Override
    public String toString() {
        return "MyFriendsBean{" +
                "id='" + id + '\'' +
                ", clienterName='" + clienterName + '\'' +
                ", phoneNo='" + phoneNo + '\'' +
                ", headImage='" + headImage + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
    }
}
