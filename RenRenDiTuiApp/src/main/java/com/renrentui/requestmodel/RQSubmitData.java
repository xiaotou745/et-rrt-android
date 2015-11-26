package com.renrentui.requestmodel;

import java.util.List;

public class RQSubmitData extends RQBase {
	public String userId;
	public String orderId;
	public String templateId;
	public List<ValueInfo> valueInfo;

	public RQSubmitData(String userId, String orderId, String templateId,
			List<ValueInfo> valueInfo) {
		super();
		this.userId = userId;
		this.orderId = orderId;
		this.templateId = templateId;
		this.valueInfo = valueInfo;
	}

	public RQSubmitData() {
		super();
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "RQSubmitData[userId=" + userId + ",orderId=" + orderId
				+ ",valueInfo=" + valueInfo + ",templateId=" + templateId + "]";
	}
}
