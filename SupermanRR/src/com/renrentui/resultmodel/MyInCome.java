package com.renrentui.resultmodel;

import java.io.Serializable;
import java.text.DecimalFormat;

public class MyInCome implements Serializable {
	/** 用户手机号 */
	public String phoneNo;
	/** 用户名 */
	public String userName;
	/** 用户头像全地址 */
	public String fullHeadImage;
	/** 余额 */
	private double balance;
	/** 可提现的金额 */
	private double withdraw;
	/** 已提现金额 */
	private double hadWithdraw;
	/** 正在审核中的金额 */
	private double checking;
	/** 正在提现中的金额 */
	private double withdrawing;
	/** 累计财富 */
	private double totalAmount;

	public String getBalance() {
		DecimalFormat df = new DecimalFormat("0.00");
		String db = df.format(balance);
		return db;
	}

	public String getWithdraw() {
		DecimalFormat df = new DecimalFormat("0.00");
		String db = df.format(withdraw);
		return db;
	}

	public String getHadWithdraw() {
		DecimalFormat df = new DecimalFormat("0.00");
		String db = df.format(hadWithdraw);
		return db;
	}

	public String getChecking() {
		DecimalFormat df = new DecimalFormat("0.00");
		String db = df.format(checking);
		return db;
	}

	public String getWithdrawing() {
		DecimalFormat df = new DecimalFormat("0.00");
		String db = df.format(withdrawing);
		return db;
	}

	public String getTotalAmount() {
		DecimalFormat df = new DecimalFormat("0.00");
		String db = df.format(totalAmount);
		return db;
	}

	public MyInCome() {
		super();
	}

	public MyInCome(String phoneNo, String userName, String fullHeadImage,
			double balance, double withdraw, double hadWithdraw,
			double checking, double withdrawing, double totalAmount) {
		super();
		this.phoneNo = phoneNo;
		this.userName = userName;
		this.fullHeadImage = fullHeadImage;
		this.balance = balance;
		this.withdraw = withdraw;
		this.hadWithdraw = hadWithdraw;
		this.checking = checking;
		this.withdrawing = withdrawing;
		this.totalAmount = totalAmount;
	}

	@Override
	public String toString() {
		return "RSMyInCome[phoneNo=" + phoneNo + ",userName=" + userName
				+ ",fullHeadImage=" + fullHeadImage + "balance=" + balance
				+ ",withdraw=" + withdraw + ",checking=" + checking
				+ "，withdrawing=" + withdrawing + ",totalAmount=" + totalAmount
				+ "]";
	}

}
