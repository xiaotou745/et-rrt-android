package com.renrentui.resultmodel;

/**
 * Created by Administrator on 2015/12/2 0002.
 * 获取资料数据集
 */
public class RSTaskMaterial  extends RSBase {
    public TaskMetarialBean data;

    public RSTaskMaterial() {
        super();
    }

    public RSTaskMaterial(String Code, String Msg, TaskMetarialBean data) {
        super(Code, Msg);
        this.data = data;
    }
}
