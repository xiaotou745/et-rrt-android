package com.renrentui.resultmodel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/12/10 0010.
 */
public class MyMessageDataBean implements Serializable {
   public String  title ;//页签标题
    public String    count ;//本次条数
    public String    nextID ;//下一次获取数据时的开始位置
    public ArrayList<MyMessageContentBean> content;// 数据集合 可能为null

    public MyMessageDataBean() {
        super();
    }

    public MyMessageDataBean(String title, String count, String nextID, ArrayList<MyMessageContentBean> content) {
        super();
        this.title = title;
        this.count = count;
        this.nextID = nextID;
        this.content = content;
    }

    @Override
    public String toString() {
        String str_1 = "MyMessageDataBean{" +
                "title='" + title + '\'' +
                ", count='" + count + '\'' +
                ", nextID='" + nextID + '\'' +
                ", content=[" ;
        StringBuffer sb = new StringBuffer(str_1);
        int isize = content==null?0:content.size();
        for(int i=0;i<isize;i++){
            MyMessageContentBean bean = content.get(i);
            sb.append(bean.toString());
        }
        sb.append("]");
        sb.append("}");
        return sb.toString();
    }
}
