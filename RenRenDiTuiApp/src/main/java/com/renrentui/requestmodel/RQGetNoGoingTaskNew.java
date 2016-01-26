package com.renrentui.requestmodel;

/**
 * Created by Administrator on 2016/1/18 0018.
 */
public class RQGetNoGoingTaskNew extends RQGetNoGoingTask {
    private int currentPage=0;//页面
    private int pageSize;
    public RQGetNoGoingTaskNew(String userId,int currentPage ,int pageSize,String cityCode,int orderBy){
        super.userId = userId;
        this.currentPage =currentPage;
        this.pageSize = pageSize;
        super.cityCode=cityCode;
        super.orderBy = orderBy;
    }


}
