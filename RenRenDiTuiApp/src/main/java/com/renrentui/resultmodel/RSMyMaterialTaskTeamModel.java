package com.renrentui.resultmodel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/1/13 0013.
 * 我的资料任务分组
 */
public class RSMyMaterialTaskTeamModel extends RSBase {
    private Result data;
    public RSMyMaterialTaskTeamModel() {
       super();
    }
    public RSMyMaterialTaskTeamModel(String Code, String Msg, Result data) {
        super(Code, Msg);
        this.data = data;
    }

    public Result getData() {
        return data;
    }

    public class Result implements Serializable{
       private String  title ;//页签标题
        private int count ;//本次条数
        private String  nextId ;//下一次获取数据时的开始位置
        private int   waitTotal ;//待审核的资料的总数
        private int  passTotal ;//审核通过的资料的总数
        private int  refuseTotal ;//审核不通过的资料的总数
        private List<Content> content ;//数据集合 可能为null

        public Result() {
        }

        public Result(String title, int count, String nextId, int waitTotal, int passTotal, int refuseTotal, List<Content> content) {
            this.title = title;
            this.count = count;
            this.nextId = nextId;
            this.waitTotal = waitTotal;
            this.passTotal = passTotal;
            this.refuseTotal = refuseTotal;
            this.content = content;
        }

        public String getTitle() {
            return title;
        }

        public int getCount() {
            return count;
        }

        public String getNextId() {
            return nextId;
        }

        public int getWaitTotal() {
            return waitTotal;
        }

        public int getPassTotal() {
            return passTotal;
        }

        public int getRefuseTotal() {
            return refuseTotal;
        }

        public List<Content> getContent() {
            return content;
        }
    }

    public class Content implements Serializable{
        private String  taskId ;//任务Id
        private String taskName ;//任务名称
        private String amount ;//任务单价
        private int  taskType ;//任务类型1 签约任务 2 分享任务 3 下载任务
        private String taskTypeName ;//任务类型名称
        private String taskStatus ;//任务状态码：1是进行中，3是过期，4是终止
        private String taskStatusName ;//任务状态描述：1是进行中，3是过期，4是终止
        private String logo ;//商家logo图片完整地址
        private String  taskDatumCount ;//当前状态下资料的总数
        private String ctId;//地推关系id
        public String tagName;//标签名称
        public String tagColorCode;//标签颜色值

        public Content() {
            super();
        }

        public Content(String taskId, String taskName, String amount, int taskType, String taskTypeName, String taskStatus, String taskStatusName, String logo, String taskDatumCount, String ctId,String tagName,String tagColorCode) {
            this.taskId = taskId;
            this.taskName = taskName;
            this.amount = amount;
            this.taskType = taskType;
            this.taskTypeName = taskTypeName;
            this.taskStatus = taskStatus;
            this.taskStatusName = taskStatusName;
            this.logo = logo;
            this.taskDatumCount = taskDatumCount;
            this.ctId = ctId;
            this.tagName = tagName;
            this.tagColorCode = tagColorCode;
        }

        public String getCtId() {
            return ctId;
        }

        public String getTaskId() {
            return taskId;
        }

        public String getTaskName() {
            return taskName;
        }

        public String getAmount() {
            return amount;
        }

        public int getTaskType() {
            return taskType;
        }

        public String getTaskTypeName() {
            return taskTypeName;
        }

        public String getTaskStatus() {
            return taskStatus;
        }

        public String getTaskStatusName() {
            return taskStatusName;
        }

        public String getLogo() {
            return logo;
        }

        public String getTaskDatumCount() {
            return taskDatumCount;
        }
    }
}
