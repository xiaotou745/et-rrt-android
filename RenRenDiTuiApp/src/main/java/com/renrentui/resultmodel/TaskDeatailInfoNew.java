package com.renrentui.resultmodel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/1/14 0014.
 *
 * 任务详情 bean -new
 */
public class TaskDeatailInfoNew extends TaskDetailInfo {
    public int partnerTotal;//任务参与人总素
    public List<PartnerList> partnerList;

    public int getPartnerTotal() {
        return partnerTotal;
    }

    public TaskDeatailInfoNew() {
        super();
    }
    public TaskDeatailInfoNew(TaskBeanInfo task, ArrayList<TaskSpecBeanInfo> taskSetps) {
        super(task, taskSetps);
    }

    public TaskDeatailInfoNew(TaskBeanInfo task, ArrayList<TaskSpecBeanInfo> taskSetps, List<PartnerList> partnerList) {
        super(task, taskSetps);
        this.partnerList = partnerList;
    }

    public TaskDeatailInfoNew(int partnerTotal, List<PartnerList> partnerList) {
        this.partnerTotal = partnerTotal;
        this.partnerList = partnerList;
    }

    public TaskDeatailInfoNew(TaskBeanInfo task, ArrayList<TaskSpecBeanInfo> taskSetps, int partnerTotal, List<PartnerList> partnerList) {
        super(task, taskSetps);
        this.partnerTotal = partnerTotal;
        this.partnerList = partnerList;
    }

    @Override
    public String toString() {
        return "TaskDeatailInfoNew{" +
                "partnerList=" + partnerList +
                '}';
    }
}
