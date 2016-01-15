package com.renrentui.resultmodel;

/**
 * Created by Administrator on 2016/1/14 0014.
 * 任务详情的返回结果  新
 */
public class RSGetTaskDetailInfoNew extends RSBase {
    public TaskDeatailInfoNew data;

    public RSGetTaskDetailInfoNew() {
        super();
    }
    public RSGetTaskDetailInfoNew(String Code, String Msg, TaskDeatailInfoNew data) {
        super(Code, Msg);
        this.data = data;
    }

    @Override
    public String toString() {
        return "RSGetTaskDetailInfoNew{" +
                "data=" + data +
                '}';
    }
}
