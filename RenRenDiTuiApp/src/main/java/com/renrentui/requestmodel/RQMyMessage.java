package com.renrentui.requestmodel;

/**
 * Created by Administrator on 2015/12/10 0010.
 * 请求我的消息
 */
public class RQMyMessage extends RQBase {
    public String userId ;//用户ID 否
    public String nextId ;//下一次获取数据时的开始位置 否
    public int itemsCount =10;//每次获取数据的条数 否
   public RQMyMessage(){
       super();
   }
        public RQMyMessage(String userId,String nextId,int itemsCount){
        super();
        this.userId = userId;
        this.nextId =nextId;
        this.itemsCount = itemsCount;
    }

    public RQMyMessage(String userId,String nextId){
        super();
        this.userId = userId;
        this.nextId =nextId;
    }

}
