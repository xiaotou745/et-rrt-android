package com.renrentui.requestmodel;

/**
 * Created by Administrator on 2016/1/13 0013.
 * 我的资料审核分组
 */
public class RQMyMaterialTaskTeamModel extends RQBase {
    private String userId ;//用户ID 否
    private String nextId ;//下一次获取数据时的开始位置 否
    private String  itemsCount ;// 每次获取数据的条数 否
    private String auditStatus ;//资料的审核状态(待审核(1);审核通过(2);审核不通过(3)) 否

    public RQMyMaterialTaskTeamModel() {
        super();
    }

    public RQMyMaterialTaskTeamModel(String userId, String nextId, String itemsCount, String auditStatus) {
        super();
        this.userId = userId;
        this.nextId = nextId;
        this.itemsCount = itemsCount;
        this.auditStatus = auditStatus;
    }

    @Override
    public String toString() {
        return "RQMyMaterialTaskTeamModel{" +
                "userId='" + userId + '\'' +
                ", nextId='" + nextId + '\'' +
                ", itemsCount='" + itemsCount + '\'' +
                ", auditStatus='" + auditStatus + '\'' +
                '}';
    }
}
