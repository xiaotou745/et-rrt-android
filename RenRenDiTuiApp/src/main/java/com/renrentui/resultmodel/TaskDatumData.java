package com.renrentui.resultmodel;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Administrator on 2015/12/4 0004.
 * 任务模板 或任务模板详情 data
 */
public class TaskDatumData implements Serializable{
    public TaskBeanInfo task;//任务基本信息
    public ArrayList<TaskDatumTemplateGroup> templateGroup;//模板组

    public TaskDatumData(TaskBeanInfo task, ArrayList<TaskDatumTemplateGroup> templateGroup) {
        this.task = task;
        this.templateGroup = templateGroup;
    }

    @Override
    public String toString() {
        String str_1 = "TaskDatumData{" +
                "task=" + task +
                "TaskDatumData=[";
        StringBuffer sb = new StringBuffer(str_1);
        int isize = templateGroup==null?0:templateGroup.size();
        for (int i=0;i<isize;i++){
            sb.append(templateGroup.get(i).toString());
        }
        return sb.append("]}").toString();
    }
}
