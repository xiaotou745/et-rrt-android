package com.renrentui.resultmodel;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/12/1 0001.
 *  任务详情基本数据2
 */
public class TaskSpecBeanInfo implements Serializable {
    private String linkTitle;//连接文本 setpType=3 才有用
    private int sortNo ;//排序
    private int setpType;//类型 1 步骤 2 补充说明 3 细则(URL链接)
    private String content;//具体内容 setpType为 1 或者2 时,是文本内容 为3 时 是链接地址Url

    public TaskSpecBeanInfo(){
        super();
    }
    public TaskSpecBeanInfo(String linkTitle,int sortNo,int setpType,String content){
        super();
        this.linkTitle = linkTitle;
        this.sortNo = sortNo;
        this.setpType =setpType;
        this.content = content;
    }

    @Override
    public String toString() {
        return "TaskSpec{" +
                "linkTitle='" + linkTitle + '\'' +
                ", sortNo=" + sortNo +
                ", setpType=" + setpType +
                ", content='" + content + '\'' +
                '}';
    }

    public String getLinkTitle() {
        return linkTitle;
    }

    public void setLinkTitle(String linkTitle) {
        this.linkTitle = linkTitle;
    }

    public int getSortNo() {
        return sortNo;
    }

    public void setSortNo(int sortNo) {
        this.sortNo = sortNo;
    }

    public int getSetpType() {
        return setpType;
    }

    public void setSetpType(int setpType) {
        this.setpType = setpType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
