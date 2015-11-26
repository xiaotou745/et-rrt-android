package com.renrentui.requestmodel;

import java.io.Serializable;

import android.content.Context;

import com.renrentui.tools.Util;
import com.renrentui.util.ApiConstants;
/**
 * 基本的请求model
 * @author EricHu
 *
 * @param <RQ> 具体的请求数据模型
 * @param <RS> 具体的返回数据模型
 */
public class RQBaseModel<RQ,RS> implements Serializable{
	public Context context;
	public RQ rqModel;// 具体的请求数据模型
	public RS rsModel;// 具体的返回数据模型
	private String ApiUrl = ApiConstants.ApiUrl;// 接口地址
	private String ApiName;// 接口名称
    public int RequestType;//请求方式类型 get 1,post 2
    public RQHandler<RS> rqHandler ;
    /**
     * api访问地址
     * @return
     */
    public String getApiAddress() {
    	return Util.TrimRightStr(ApiUrl,"/")+"/"+Util.TrimLeftRightStr(ApiName, "/");
    }
	public String getApiUrl() {
		return ApiUrl;
	}
	public void setApiUrl(String apiUrl) {
		ApiUrl = apiUrl;
	}
	public String getApiName() {
		return ApiName;
	}
	public void setApiName(String apiName) {
		ApiName = apiName;
	}
	/**
	 * 
	 * @param context
	 * @param rqModel 具体的请求数据模型
	 * @param rsModel 具体的返回数据模型
	 * @param apiUrl 接口地址
	 * @param apiName 接口名称
	 * @param requestType 请求方式类型 get 1,post 2
	 * @param rqHandler 请求回调
	 */
	public RQBaseModel(Context context, RQ rqModel, RS rsModel, 
			String apiName, int requestType, RQHandler<RS> rqHandler) {
		super();
		this.context = context;
		this.rqModel = rqModel;
		this.rsModel = rsModel;
		ApiName = apiName;
		RequestType = requestType;
		this.rqHandler = rqHandler;
	}
	@Override
	public String toString() {
		return "RQModel [context=" + context + ", rqModel=" + rqModel
				+ ", rsModel=" + rsModel + ", ApiUrl=" + ApiUrl + ", ApiName="
				+ ApiName + ", RequestType=" + RequestType + ", rqHandler="
				+ rqHandler + "]";
	}
}
