package com.renrentui.resultmodel;

import java.io.Serializable;
import java.text.DecimalFormat;

import org.apache.james.mime4j.field.datetime.DateTime;

/**
 * 已领取任务详情类
 * 
 * @author llp
 */
public class OnGoingTaskInfo implements Serializable {
	/**
	 * 任务ID
	 */
	public String taskId;
	/**
	 * 在已领取的任务表中的唯一标示（不同于任务ID）
	 */
	public String myReceivedTaskId;
	/**
	 * 任务概要信息，显示在任务列表中的
	 */
	public String taskGeneralInfo;
	/**
	 * 发布商名称
	 */
	public String pusher;
	/**
	 * 任务名称
	 */
	public String taskName;
	/**
	 * 任务生命周期（单位：小时）（通过任务生命周期和获取时间，可以计算出任务剩余时间）
	 */
	public float taskCycle;
	/**
	 * 任务单价
	 */
	private double amount;
	/**
	 * 剩余可领取的数量
	 */
	public int availableCount;
	/**
	 * 任务状态 -1未领取 0已领取 1已提交
	 */
	public String status;
	/**
	 * 领取任务的时间
	 */
	public String receivedTime;
	/**
	 * 任务发布时间
	 */
	public String beginTime;
	/**
	 * 任务结束时间
	 */
	public String endTime;
	/**
	 * 任务审核时间
	 */
	public String auditTime;
	/**
	 * 任务发布商logo
	 */
	public String logo;
	/**
	 * 支付方式 1-线上支付 2-线下支付
	 */
	public String paymentMethod;
	/**
	 * 审核状态0待审核，2审核通过，3审核拒绝，默认0待审核
	 */
	public int auditStatus;

	public String getAmount() {
		DecimalFormat df = new DecimalFormat("0.00");
		String db = df.format(amount);
		return db;
	}

	public OnGoingTaskInfo() {
		super();
	}

	public OnGoingTaskInfo(String myReceivedTaskId, String taskId,
			String taskGeneralInfo, String pusher, String taskName,
			double amount, int availableCount, String status, float taskCycle,
			String receivedTime, String beginTime, String endTime,
			String auditTime, String logo, String paymentMethod, int auditStatus) {
		super();
		this.myReceivedTaskId = myReceivedTaskId;
		this.taskId = taskId;
		this.taskGeneralInfo = taskGeneralInfo;
		this.pusher = pusher;
		this.taskName = taskName;
		this.amount = amount;
		this.availableCount = availableCount;
		this.status = status;
		this.taskCycle = taskCycle;
		this.receivedTime = receivedTime;
		this.beginTime = beginTime;
		this.endTime = endTime;
		this.auditTime = auditTime;
		this.logo = logo;
		this.paymentMethod = paymentMethod;
		this.auditStatus = auditStatus;
	}

	@Override
	public String toString() {
		return "OnGoingTaskInfo[taskId=" + taskId + ",myReceivedTaskId="
				+ myReceivedTaskId + ",taskGeneralInfo=" + taskGeneralInfo
				+ ",pusher=" + pusher + ",taskName=" + taskName + ",amount="
				+ amount + ",availableCount=" + availableCount + ",status="
				+ status + ",taskCycle=" + taskCycle + ",receivedTime="
				+ receivedTime + ",beginTime=" + beginTime + ",endTime="
				+ endTime + ",auditTime=" + auditTime + ",logo=" + logo
				+ ",paymentMethod=" + paymentMethod + ",auditStatus="
				+ auditStatus + "]";
	}
}
