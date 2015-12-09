package com.renrentui.resultmodel;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Administrator on 2015/12/9 0009.
 * 我的任务列表bean
 */
public class MyTaskBean implements Serializable {
   public String  title ;//页签标题
    public int  count;// 本次条数
    public String  nextID ;//下一次获取数据时的开始位置
    public String  passTotal ;//进行中的任务的总数
    public String refuseTotal ;//已过期的任务的总数
    public ArrayList<MyTaskContentBean> content ;//数据集合 可能为null

    @Override
    public String toString() {
        String str_1 = "MyTaskBean{" +
                "title='" + title + '\'' +
                ", count='" + count + '\'' +
                ", nextID='" + nextID + '\'' +
                ", passTotal='" + passTotal + '\'' +
                ", refuseTotal='" + refuseTotal + '\'' +
                ", content=["  +
                '}';
        StringBuffer sb = new StringBuffer(str_1);
        int isize = content==null?0:content.size();
        for(int i=0;i<isize;i++){
            MyTaskContentBean bean = content.get(i);
            sb.append(bean.toString());
        }
        sb.append("]").append("}");


        return sb.toString();
    }
}
