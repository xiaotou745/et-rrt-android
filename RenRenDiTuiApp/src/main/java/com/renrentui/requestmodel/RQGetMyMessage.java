package com.renrentui.requestmodel;

import android.app.DownloadManager;

/**
 * Created by Administrator on 2016/1/4 0004.
 */
public class RQGetMyMessage extends RQBase {
    private String userId;//用户id

    public RQGetMyMessage() {
    }

    public RQGetMyMessage(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "RQGetMyMessage{" +
                "userId='" + userId + '\'' +
                '}';
    }
}
