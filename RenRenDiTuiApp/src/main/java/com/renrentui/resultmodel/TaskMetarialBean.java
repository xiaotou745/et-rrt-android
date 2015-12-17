package com.renrentui.resultmodel;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by Administrator on 2015/12/2 0002.
 * 任务提交的资料bean
 */
public class TaskMetarialBean implements Serializable {
    public String title ;//页签标题
    public int  count ;//本次条数
    public String nextId ;//下一次获取数据时的开始位置
    public  int waitTotal;//申请中的数量
    public  int passTotal;//通过的数量
    public int refuseTotal;//没有通过的数量
    public ArrayList<TaskMetarialContent> content;// 数据集合 可能为null

    public TaskMetarialBean() {
        super();
    }

    public TaskMetarialBean(String title, int count, String nextId, ArrayList<TaskMetarialContent> content) {
        super();
        this.title = title;
        this.count = count;
        this.nextId = nextId;
        this.content = content;
    }

    @Override
    public String toString() {
        String str_1 = "TaskMetarialBean{" +
                "title='" + title + '\'' +
                ", count=" + count +
                ", nextId='" + nextId + '\'' +
                ", nextID='" + waitTotal + '\'' +
                ", nextID='" + passTotal + '\'' +
                ", nextID='" + refuseTotal + '\'' +
                ", content=" +"[" ;
        StringBuffer sb = new StringBuffer(str_1);
        int isize = content==null?0:content.size();
        for(int i=0;i<isize;i++){
            TaskMetarialContent bean = content.get(i);
            sb.append(bean.toString());
        }
        sb.append("]");
        sb.append("}");

        return sb.toString();
    }
}
