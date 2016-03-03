package com.renrentui.requestmodel;

/**
 * Created by Administrator on 2016/3/2 0002.
 * 获取我的合伙人
 */
public class RQMyFriendsMode  extends RQBase {
    private String userId;
    private String nextId;
    private int itemsCount;

    public RQMyFriendsMode() {
        super();
    }

    public RQMyFriendsMode(String userId, String nextId, int itemsCount) {
        super();
        this.userId = userId;
        this.nextId = nextId;
        this.itemsCount = itemsCount;
    }

    public RQMyFriendsMode(String userId, String nextId) {
        super();
        this.userId = userId;
        this.nextId = nextId;
        this.itemsCount = 10;
    }
}