package com.renrentui.resultmodel;

import java.io.Serializable;
import java.text.DecimalFormat;

public class MyInCome implements Serializable {
	public String id;//用户id
	public String clienterName;//用户姓名
	/** 用户手机号 */
	public String phoneNo;
	/** 用户名 */
	//public String userName;
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

	private String accountNo;//提现账号（新增）
	private String trueName;//账号实名（新增）
	private String accountType;//账号类型(-1没绑定 1网银 2支付宝 3微信 4财付通 5百度钱包)（新增，本版只支持支付宝）

	public String getAccountNo() {
		return accountNo;
	}

	public String getTrueName() {
		return trueName;
	}

	public String getAccountType() {
		return accountType;
	}

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

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public MyInCome() {
		super();
	}

	public MyInCome(String id,String clienterName , String phoneNo, String userName, String fullHeadImage,
			double balance, double withdraw, double hadWithdraw,
			double checking, double withdrawing, double totalAmount,String accountNo,String trueName,String accountType) {
		super();
		this.id =id;
		this.clienterName = clienterName;
		this.phoneNo = phoneNo;
		//this.userName = userName;
		this.fullHeadImage = fullHeadImage;
		this.balance = balance;
		this.withdraw = withdraw;
		this.hadWithdraw = hadWithdraw;
		this.checking = checking;
		this.withdrawing = withdrawing;
		this.totalAmount = totalAmount;
		this.accountNo = accountNo;
		this.trueName = trueName;
		this.accountType = accountType;
	}

	@Override
	public String toString() {
		return "MyInCome{" +
				"id='" + id + '\'' +
				", clienterName='" + clienterName + '\'' +
				", phoneNo='" + phoneNo + '\'' +
				", fullHeadImage='" + fullHeadImage + '\'' +
				", balance=" + balance +
				", withdraw=" + withdraw +
				", hadWithdraw=" + hadWithdraw +
				", checking=" + checking +
				", withdrawing=" + withdrawing +
				", totalAmount=" + totalAmount +
				", accountNo='" + accountNo + '\'' +
				", trueName='" + trueName + '\'' +
				", accountType='" + accountType + '\'' +
				'}';
	}
}
