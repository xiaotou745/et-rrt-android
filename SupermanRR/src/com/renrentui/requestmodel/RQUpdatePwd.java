package com.renrentui.requestmodel;

public class RQUpdatePwd extends RQBase {

	public String userId;
	public String oldPwd;
	public String newPwd;

	public RQUpdatePwd() {
		super();
	}

	public RQUpdatePwd(String userId, String oldPwd, String newPwd) {
		this.userId = userId;
		this.oldPwd = oldPwd;
		this.newPwd = newPwd;
	}

	@Override
	public String toString() {
		return "RQUpdatePwd[userId=" + userId + ",oldPwd=" + oldPwd
				+ ",newPwd=" + newPwd + "]";
	}

}
