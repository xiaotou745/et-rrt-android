package com.renrentui.requestmodel;

public class RQUserId extends RQBase {
	/** 用户ID */
	public String userId;

	public RQUserId(String userId) {
		super();
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "RQUserId[userId=" + userId + "]";
	}
}
