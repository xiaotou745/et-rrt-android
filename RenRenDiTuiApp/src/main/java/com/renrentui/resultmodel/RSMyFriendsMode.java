package com.renrentui.resultmodel;

import java.util.List;

/**
 * Created by Administrator on 2016/3/2 0002.
 * 获取我的合伙人结果
 */
public class RSMyFriendsMode extends RSBase {
    private Result data;

    public RSMyFriendsMode() {
        super();
    }

    public RSMyFriendsMode(String Code, String Msg, Result data) {
        super(Code, Msg);
        this.data = data;
    }

    public Result getData() {
        return data;
    }

    public class Result {

        private int count;//本次条数
        private int total;//当前任务参与人总数
        private String nextId;//下一次获取数据时的开始位置
        private List<MyFriendsBean> content;// 数据集合 可能为null

        public int getCount() {
            return count;
        }

        public int getTotal() {
            return total;
        }

        public String getNextId() {
            return nextId;
        }

        public List<MyFriendsBean> getContent() {
            return content;
        }

        public Result(int count, int total, String nextId, List<MyFriendsBean> content) {
            this.count = count;
            this.total = total;
            this.nextId = nextId;
            this.content = content;
        }
    }
}

