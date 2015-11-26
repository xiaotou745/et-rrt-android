package com.renrentui.requestmodel;

public class RQWithdraw extends RQBase {
	/** 用户ID */
	public String userId;
	/** 提现金额 */
	public String amount;
	/** 提现账号 */
	public String accountInfo;
	/** 提现账号姓名 */
	public String trueName;

	public RQWithdraw() {
		super();
	}

	public RQWithdraw(String userId, String amount, String accountInfo,
			String trueName) {
		super();
		this.userId = userId;
		this.amount = amount;
		this.accountInfo = accountInfo;
		this.trueName = trueName;
	}

	@Override
	public String toString() {
		return "RQWithdraw[userId=" + userId + ",amount=" + amount
				+ ",accountInfo=" + accountInfo + ",trueName=" + trueName + "]";
	}

}
