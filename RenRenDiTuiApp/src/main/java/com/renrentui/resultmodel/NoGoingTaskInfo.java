package com.renrentui.resultmodel;

import java.io.Serializable;
import java.text.DecimalFormat;

/**
 * 任务详情类
 * 
 * @author llp
 * 
 */
public class NoGoingTaskInfo implements Serializable{
	/**
	 * 任务ID 唯一标示
	 */
	public String taskId;
	/**
	 * 任务描述
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
	 * 任务单价
	 */
	private double amount;
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
	 * 支付方式 1-线上支付 2-线下支付
	 */
	public String paymentMethod;
	/**
	 * 任务发布商的logo
	 */
	public String logo;
	/**
	 * 任务状态 -1未领取 0已领取 1已提交
	 */
	public String status;
	/**
	 * 审核状态0待审核，2审核通过，3审核拒绝，默认0待审核
	 */
	public String auditStatus;

	public String getAmount() {
		DecimalFormat df = new DecimalFormat("0.00");
		String db = df.format(amount);
		return db;
	}
	public NoGoingTaskInfo() {
	}

	public NoGoingTaskInfo(String taskId, String taskGeneralInfo,
			String pusher, String taskName, double amount, int availableCount,
			String beginTime, String endTime, String paymentMethod, String logo) {
		super();
		this.taskId = taskId;
		this.taskGeneralInfo = taskGeneralInfo;
		this.pusher = pusher;
		this.taskName = taskName;
		this.amount = amount;
		this.availableCount = availableCount;
		this.beginTime = beginTime;
		this.endTime = endTime;
		this.paymentMethod = paymentMethod;
		this.logo = logo;
	}

	@Override
	public String toString() {
		return "NoGoingTaskInfo[taskId=" + taskId + ",taskGeneralInfo="
				+ taskGeneralInfo + ",pusher=" + pusher + ",taskName="
				+ taskName + ",amount=" + amount + ",availableCount="
				+ availableCount + ",beginTime=" + beginTime + ",endTime="
				+ endTime + ",paymentMethod=" + paymentMethod + ",logo=" + logo
				+ "]";
	}
}
