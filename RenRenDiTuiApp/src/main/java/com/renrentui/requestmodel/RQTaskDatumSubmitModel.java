package com.renrentui.requestmodel;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/12/9 0009.
 * 提交任务模板modul
 */
public class RQTaskDatumSubmitModel extends RQBase {
    public String userId;// 用户ID 否
    public String taskId;// 任务ID 否
    public String ctId;//地推人员任务关系id
    public ArrayList<ValueInfo> valueInfo;// 资料ID（获取资料详情时必传） 否

    public RQTaskDatumSubmitModel(){
        super();
    }
    public  RQTaskDatumSubmitModel(String userId,String taskId,String ctId,ArrayList<ValueInfo> data){
        this.userId = userId;
        this.taskId = taskId;
        this.ctId = ctId;
        if(valueInfo!=null){
            valueInfo.clear();
            valueInfo.addAll(data);
        }else{
            valueInfo = new ArrayList<ValueInfo>();
            valueInfo.addAll(data);
        }
    }


    public class ValueInfo {
        public  String controlName ;//控件名称 否
        public String controlValue ;//控件名称 否

        @Override
        public String toString() {
            return "{" +
                    "controlName='" + controlName + '\'' +
                    ", controlValue='" + controlValue + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        String aa = "RQTaskDatumSubmitModel{" +
                "userId='" + userId + '\'' +
                ", taskId='" + taskId + '\'' +
                ", ctId='" + ctId + '\''+"valueInfo[";
        StringBuffer sb  = new StringBuffer(aa);
        int isize = valueInfo==null?0:valueInfo.size();
        for(int i=0;i<isize;i++){
ValueInfo bean = valueInfo.get(i);
            sb.append(bean
            .toString());
        }
        sb.append("]").append("}");
        return sb.toString();

    }
}
