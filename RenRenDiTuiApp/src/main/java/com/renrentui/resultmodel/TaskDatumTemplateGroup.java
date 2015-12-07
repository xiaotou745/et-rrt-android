package com.renrentui.resultmodel;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Administrator on 2015/12/4 0004.
 *  任务模板 或任务模板详情 模板组
 *
 */
public class TaskDatumTemplateGroup implements Serializable {
   public int  groupType;// 组类型 1 文字组 2 图片组 3多图组
  public String  title;// 组标题
  public ArrayList<TaskDatumControlBean> controlList;// 控件集合
    public TaskDatumTemplateGroup(){
        super();
    }

    public TaskDatumTemplateGroup(int groupType, String title, ArrayList<TaskDatumControlBean> controlList) {
       super();
        this.groupType = groupType;
        this.title = title;
        this.controlList = controlList;
    }

    @Override
    public String toString() {
        String str_1 = "TaskDatumTemplateGroup{" +
        "groupType=" + groupType +
        ", title='" + title + '\'' +
        ", controlList=[";
        StringBuffer sb = new StringBuffer(str_1);
        int isize = controlList==null?0:controlList.size();
        for (int i=0;i<isize;i++){
            sb.append(controlList.get(i).toString());
        }
        return sb.append("]}").toString();
    }
}
