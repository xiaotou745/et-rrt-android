package com.renrentui.resultmodel;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.List;

public class TaskDetailInfo implements Serializable {
	/**
	 * 任务ID
	 */
	public String id;
	/**
	 * 任务显示名称
	 */
	public String taskTitle;
	/**
	 * 任务公告
	 */
	public String taskNotice;
	/**
	 * 任务单价
	 */
	private double amount;
	/**
	 * 结束时间
	 */
	public String endTime;
	/**
	 * 任务剩余量
	 */
	public int availableCount;
	/**
	 * 支付方式 1-线上支付 2-线下支付
	 */
	public String paymentMethod;
	/**
	 * 任务描述
	 */
	public String taskGeneralInfo;
	/**
	 * 任务注意事项
	 */
	public String taskNote;
	/**
	 * 商家ID
	 */
	public String businessId;
	/**
	 * 合同模板ID
	 */
	public String templateId;
	/**
	 * 发布人
	 */
	public String pusher;
	/**
	 * 合同模板名称
	 */
	public String templateName;
	/**
	 * 领取状态1 已领取 0未领取
	 */
	public int isHad;
	/**
	 * 如果已领取任务 这是是orderId 否则0
	 */
	public String orderId;
	/**
	 * 发布商家LOGO地址
	 */
	public String logo;
	/**
	 * 公司简介
	 */
	public String companySummary;
	/**
	 * 任务审核周期
	 */
	public String auditCycle;
	/**
	 * 任务完成周期
	 */
	public float taskCycle;
	/** 排队待审核人数 */
	public String waitCount;
	/**
	 * 合同控件信息
	 */
	public List<ControlInfo> controlInfo;
	/** 任务领取时间 */
	public String receivedTime;
	/** 任务提交时间 */
	public String finishTime;
	/** 是否可再次领取任务 */
	public int isAgainPickUp;

	public String getAmount() {
		DecimalFormat df = new DecimalFormat("0.00");
		String db = df.format(amount);
		return db;
	}

	public TaskDetailInfo() {
		super();
	}

	public TaskDetailInfo(String id, String taskNotice, String taskGeneralInfo,
			String taskNote, String businessId, String templateId,
			String pusher, String templateName, int isHad, String orderId,
			String waitCount, String paymentMethod, int availableCount,
			double amount, String endTime, String taskTitle, String logo,
			String companySummary, String auditCycle, float taskCycle,
			List<ControlInfo> controlInfo, String receivedTime,
			String finishTime, int isAgainPickUp) {
		super();
		this.id = id;
		this.taskNotice = taskNotice;
		this.taskGeneralInfo = taskGeneralInfo;
		this.taskNote = taskNote;
		this.businessId = businessId;
		this.templateId = templateId;
		this.pusher = pusher;
		this.templateName = templateName;
		this.isHad = isHad;
		this.orderId = orderId;
		this.waitCount = waitCount;
		this.paymentMethod = paymentMethod;
		this.availableCount = availableCount;
		this.amount = amount;
		this.endTime = endTime;
		this.taskTitle = taskTitle;
		this.logo = logo;
		this.companySummary = companySummary;
		this.auditCycle = auditCycle;
		this.taskCycle = taskCycle;
		this.controlInfo = controlInfo;
		this.receivedTime = receivedTime;
		this.finishTime = finishTime;
		this.isAgainPickUp = isAgainPickUp;
	}

	@Override
	public String toString() {
		return "TaskDetailInfo[id=" + id + ",taskNotice=" + taskNotice
				+ ",taskGeneralInfo=" + taskGeneralInfo + ",taskNote="
				+ taskNote + ",businessId=" + businessId + ",templateId="
				+ templateId + ",pusher=" + pusher + ",templateName="
				+ templateName + ",isHad=" + isHad + ",orderId=" + orderId
				+ ",paymentMethod=" + paymentMethod + ",availableCount="
				+ availableCount + ",amount=" + amount + ",endTime=" + endTime
				+ ",taskTitle=" + taskTitle + ",logo=" + logo
				+ ",companySummary=" + companySummary + ",auditCycle="
				+ auditCycle + ",taskCycle=" + taskCycle + ",controlInfo="
				+ controlInfo + ",waitCount=" + waitCount + ",receivedTime="
				+ receivedTime + ",finishTime=" + finishTime
				+ ",isAgainPickUp=" + isAgainPickUp + "]";
	}
}
