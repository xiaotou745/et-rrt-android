package com.renrentui.requestmodel;

/**
 * Created by Administrator on 2016/1/8 0008.
 *
 * 消息读取 或 删除
 */
public class RQReadMyMessage extends RQBase {
   public String  userId ;//用户ID 否
    public String msgId ;//消息id 否
    public String opType ;//操作类型（1是删除消息，2是将消息置为已读） 否
    public RQReadMyMessage(){

    }

    public RQReadMyMessage(String userId, String msgId, String opType) {
        this.userId = userId;
        this.msgId = msgId;
        this.opType = opType;
    }

    @Override
    public String toString() {
        return "RQReadMyMessage{" +
                "userId='" + userId + '\'' +
                ", msgId='" + msgId + '\'' +
                ", opType='" + opType + '\'' +
                '}';
    }
}
