package com.renrentui.resultmodel;

/**
 * Created by Administrator on 2015/12/4 0004.
 * 任务资料模板参数对象
 */
public class TaskDatumTempletParamsBean {
    private String tag;//标签  texts  images multiple_images
    private int team;//组
    private int position;//位置
    private String orderNum;//排序号
    private String controlKey;//提交任务时的key
    private String controlValue;//任务模板输入的内容

    public TaskDatumTempletParamsBean() {
        super();
    }

    public TaskDatumTempletParamsBean(String tag, int team, int position, String orderNum,
                                      String controlKey, String controlValue) {
        super();
        this.tag = tag;
        this.team = team;
        this.position = position;
        this.orderNum = orderNum;
        this.controlKey = controlKey;
        this.controlValue = controlValue;
    }

    public String getTag() {
        return tag;
    }

    public int getTeam() {
        return team;
    }

    public int getPosition() {
        return position;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public String getControlKey() {
        return controlKey;
    }

    public String getControlValue() {
        return controlValue;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setTeam(int team) {
        this.team = team;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public void setControlKey(String controlKey) {
        this.controlKey = controlKey;
    }

    public void setControlValue(String controlValue) {
        this.controlValue = controlValue;
    }
}
