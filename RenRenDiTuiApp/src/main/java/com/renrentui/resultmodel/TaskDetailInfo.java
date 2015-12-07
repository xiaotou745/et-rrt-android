package com.renrentui.resultmodel;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 任务详情
 */
public class TaskDetailInfo implements Serializable {
	public TaskBeanInfo task;
	public ArrayList<TaskSpecBeanInfo> taskSetps;

	public TaskDetailInfo(TaskBeanInfo task, ArrayList<TaskSpecBeanInfo> taskSetps) {
		this.task = task;
		this.taskSetps = taskSetps;
	}

	@Override
	public String toString() {
		StringBuffer sb =new StringBuffer();
		sb.append("TaskDetailInfo[").append("task=[").append(task.toString()).append("],");

		int i = taskSetps==null ?0:taskSetps.size();
		sb.append("taskSetps=[");
		for(int j = 0;j<i;j++){
			sb.append(taskSetps.toString());
		}
		sb.append("]");
		return sb.toString();
	}
}
