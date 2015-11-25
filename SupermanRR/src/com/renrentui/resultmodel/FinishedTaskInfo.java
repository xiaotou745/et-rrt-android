package com.renrentui.resultmodel;

import java.io.Serializable;
import java.text.DecimalFormat;

public class FinishedTaskInfo implements Serializable {
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
	private Double amount;
	/**
	 * 剩余可领取的数量
	 */
	public int availableCount;
	/**
	 * 任务发布时间
	 */
	public String beginTime;
	/**
	 * 任务结束时间
	 */
	public String endTime;
	/**
	 * 领取任务的时间
	 */
	public String receivedTime;
	/**
	 * 任务被审核的时间（为空的时候表示还没有审核，此时State为4）
	 */
	public String auditTime;
	/**
	 * 提交任务的时间
	 */
	public String finishTime;
	/**
	 * 支付方式 1-线上支付 2-线下支付
	 */
	public String paymentMethod;
	/**
	 * 任务发布商logo
	 */
	public String logo;
	/**
	 * 任务状态。1-未领取 2-已领取（未完成） 3-已完成 4-正在审核 5-审核失败
	 */
	public String status;
	/**
	 * 审核状态0待审核，2审核通过，3审核拒绝，默认0待审核
	 */
	public int auditStatus;
	/**
	 * 待审核任务数量
	 */
	public String waitAuditCount;
	/** 是否允许再次接单 */
	public int isAgainPickUp;
	/** 取消时间 */
	public String cancelTime;
	/** 订单结束时间 */
	public String dealLineTime;

	public String getAmount() {
		DecimalFormat df = new DecimalFormat("0.00");
		String db = df.format(amount);
		return db;
	}

	public FinishedTaskInfo() {
		super();
	}

	public FinishedTaskInfo(String myReceivedTaskId, String taskId,
			String taskGeneralInfo, String pusher, String taskName,
			Double amount, int availableCount, String status, float taskCycle,
			String receivedTime, String finishTime, String auditTime,
			String beginTime, String endTime, String paymentMethod,
			String logo, String waitAuditCount, int auditStatus,
			int isAgainPickUp, String cancelTime, String dealLineTime) {
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
		this.finishTime = finishTime;
		this.auditTime = auditTime;
		this.beginTime = beginTime;
		this.endTime = endTime;
		this.paymentMethod = paymentMethod;
		this.logo = logo;
		this.waitAuditCount = waitAuditCount;
		this.auditStatus = auditStatus;
		this.isAgainPickUp = isAgainPickUp;
		this.cancelTime = cancelTime;
		this.dealLineTime = dealLineTime;
	}

	@Override
	public String toString() {
		return "FinishedTaskInfo[myReceivedTaskId=" + myReceivedTaskId
				+ ",taskId=" + taskId + ",taskGeneralInfo=" + taskGeneralInfo
				+ ",pusher=" + pusher + ",taskName=" + taskName + ",amount="
				+ amount + ",availableCount=" + availableCount + ",status="
				+ status + ",taskCycle=" + taskCycle + ",receivedTime="
				+ receivedTime + ",finishTime=" + finishTime + ",auditTime="
				+ auditTime + ",beginTime=" + beginTime + ",endTime=" + endTime
				+ ",paymentMethod=" + paymentMethod + ",logo=" + logo
				+ ",waitAuditCount=" + waitAuditCount + ",auditStatus="
				+ auditStatus + ",isAgainPickUp=" + isAgainPickUp
				+ "cancelTime=" + cancelTime + ",dealLineTime=" + dealLineTime
				+ "]";
	}
}
