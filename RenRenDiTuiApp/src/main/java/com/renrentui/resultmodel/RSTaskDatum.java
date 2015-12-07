package com.renrentui.resultmodel;

/**
 * Created by Administrator on 2015/12/4 0004.
 * 任务模板 或任务模板详情
 */
public class RSTaskDatum extends RSBase{
    public TaskDatumData data;

    public RSTaskDatum() {
        super();
    }

    public RSTaskDatum(String Code, String Msg, TaskDatumData data) {
        super(Code, Msg);
        this.data = data;
    }

    @Override
    public String toString() {
        return "RSTaskDatum{" +
                "data=" + data +
                '}';
    }
}
