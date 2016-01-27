package com.renrentui.resultmodel;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by Administrator on 2015/12/2 0002.
 * 任务提交的资料bean内容部分
 */
public class TaskMetarialContent  implements Serializable {
    public String  taskId ;//任务Id
    public String taskName;// 任务名称
    public double amount ;//任务单价
    public int taskType ;//任务类型  id任务类型1 签约任务 2 分享任务 3 下载任务
    public String taskTypeName ;//任务类型名称
    public String taskStatus;// 任务状态描述：1是审核通过，3是过期，4是终止
    public String taskStatusName ;//任务状态描述：1是进行中，3是过期，4是终止
    public String auditCycle;// (审核周期)
    public String taskDatumId;// 资料id
    public String auditStatus;// 审核状态描述：待审核，通过，不通过
    public String createDate;// 资料提交时间
    public String auditTime;// 资料审核过或不过的时间
    public int  groupType;// 模板组的类型id（1是文本组，2是图片组，3是多图组）
    public String ctId;//地推关系id
    public ArrayList<String > titlesList;// 资料的展示信息集合，集合中元素类型是字符串（groupType是文本组时，集合中是资料描述，否则是图片地址）
    public String refuReason;//拒绝原因
    public String getAmount() {

        DecimalFormat df = new DecimalFormat("0.00");
        String db = df.format(amount);
        return db;
    }

    public TaskMetarialContent(){
        super();
    }
    public TaskMetarialContent(String refuReason,String taskId, String taskName, double amount,
                            int taskType, String taskTypeName, String taskStatus,
                            String auditCycle, String taskDatumId, String auditStatus,
                            String createDate, String auditTime, int groupType,
                               ArrayList<String > titlesList,String ciId,String taskStatusName) {
        super();
        this.taskId = taskId;
        this.taskName = taskName;
        this.amount = amount;
        this.taskType = taskType;
        this.taskTypeName = taskTypeName;
        this.taskStatus = taskStatus;
        this.auditCycle = auditCycle;
        this.taskDatumId = taskDatumId;
        this.auditStatus = auditStatus;
        this.createDate = createDate;
        this.auditTime = auditTime;
        this.groupType = groupType;
        this.titlesList = titlesList;
        this.ctId =ciId;
        this.taskStatusName = taskStatusName;
        this.refuReason = refuReason;
    }

    @Override
    public String toString() {
        return "GetTaskMetarialBean{" +
                "taskId='" + taskId + '\'' +
                ", taskName='" + taskName + '\'' +
                ", amount=" + getAmount() +
                ", taskType=" + taskType +
                ", taskTypeName='" + taskTypeName + '\'' +
                ", taskStatus='" + taskStatus + '\'' +
                ", auditCycle='" + auditCycle + '\'' +
                ", taskDatumId='" + taskDatumId + '\'' +
                ", auditStatus='" + auditStatus + '\'' +
                ", createDate='" + createDate + '\'' +
                ", auditTime='" + auditTime + '\'' +
                ", groupType=" + groupType +
                ", titlesList='" + titlesList + '\'' +
                ", taskStatusName='" + taskStatusName + '\'' +
                '}';
    }
}
