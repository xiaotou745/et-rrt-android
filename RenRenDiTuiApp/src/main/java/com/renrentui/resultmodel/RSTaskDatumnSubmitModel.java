package com.renrentui.resultmodel;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by Administrator on 2015/12/9 0009.
 * 任务模板信息提交
 */
public class RSTaskDatumnSubmitModel extends RSBase {
    //public ArrayList<Objects> data;
    public  RSTaskDatumnSubmitModel(){
        super();
    }
    public  RSTaskDatumnSubmitModel(String Code, String Message, ArrayList<Objects> data){
        super(Code,Message);
//        if(this.data!=null){
//            this.data.clear();
//            this.data.addAll(data);
//        }else{
//            this.data = new ArrayList<Objects>();
//        }
    }

    @Override
    public String toString() {
        return "RSTaskDatumnSubmitModel{" +
                "code=" + code + ",msg=" + msg+
                '}';
    }
}
