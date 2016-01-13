package com.renrentui.requestmodel;

/**
 * Created by Administrator on 2016/1/11 0011.
 * 我的资金明细
 */
public class RQMyCaptailModel extends RQBase {
    private String userId;
    private String recordType ;//记录类型：1是收入，2是支出 否
    private int nextId =0;//下一页记录开始ID 否
    private int itemsCount=10 ;//每页条数

    public RQMyCaptailModel() {
        super();
    }

    public RQMyCaptailModel(String userId, String recordType, int nextId, int itemsCount) {
        super();
        this.userId = userId;
        this.recordType = recordType;
        this.nextId = nextId;
        this.itemsCount = itemsCount;
    }
    public RQMyCaptailModel(String userId, String recordType) {
        super();
        this.userId = userId;
        this.recordType = recordType;
    }

    @Override
    public String toString() {
        return "RQMyCaptailModel{" +
                "userId='" + userId + '\'' +
                ", recordType='" + recordType + '\'' +
                ", nextId=" + nextId +
                ", itemsCount=" + itemsCount +
                '}';
    }
}
