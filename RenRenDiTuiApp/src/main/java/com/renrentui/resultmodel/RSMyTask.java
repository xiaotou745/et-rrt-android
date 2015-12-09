package com.renrentui.resultmodel;

import com.task.activity.MyTaskFramentActivity;

/**
 * Created by Administrator on 2015/12/9 0009.
 * 我的任务列表返回接口
 */
public class RSMyTask extends RSBase {
    public MyTaskBean data;

    public RSMyTask() {
        super();
    }

    public RSMyTask(String Code, String Msg, MyTaskBean data) {
        super(Code, Msg);
        this.data = data;
    }

    @Override
    public String toString() {
        return "MyTaskBean[data=" + data + "]";
    }
}
