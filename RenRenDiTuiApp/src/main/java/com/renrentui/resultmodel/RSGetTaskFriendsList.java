package com.renrentui.resultmodel;

import java.util.List;

/**
 * Created by Administrator on 2016/1/19 0019.
 * 获取任务参与人信息
 */
public class RSGetTaskFriendsList extends RSBase {
private Result data;
    public RSGetTaskFriendsList() {
        super();
    }
    public RSGetTaskFriendsList(String Code, String Msg, Result data) {
        super(Code, Msg);
        this.data = data;
    }

    public Result getData() {
        return data;
    }

    public class Result {

        private String title ;//页签标题
        private int count ;//本次条数
        private  int total ;//当前任务参与人总数
       private String  nextId ;//下一次获取数据时的开始位置
       private List< PartnerList> content;// 数据集合 可能为null

        public String getTitle() {
            return title;
        }


        public int getCount() {
            return count;
        }

        public int getTotal() {
            return total;
        }

        public String getNextId() {
            return nextId;
        }

        public List<PartnerList> getContent() {
            return content;
        }

        public Result() {
        }

        public Result(List<PartnerList> content, String title, int count, int total, String nextId) {
            this.content = content;
            this.title = title;
            this.count = count;
            this.total = total;
            this.nextId = nextId;
        }
    }


}
