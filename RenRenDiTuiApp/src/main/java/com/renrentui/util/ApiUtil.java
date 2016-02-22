package com.renrentui.util;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import com.renrentui.net.HttpRequestDigestImpl;
import com.renrentui.net.IHttpRequest;
import com.renrentui.net.NetworkValidator;
import com.renrentui.requestmodel.RQBaseModel;
import com.renrentui.requestmodel.RequestType;
import com.renrentui.requestmodel.ResultMsgType;
import com.renrentui.resultmodel.RSBase;
import com.renrentui.tools.GsonTools;

public class ApiUtil {
	// 对应某个接口的线程是否已结束 结束或者未开始为true 正在运行为false
	private static Map<String, Boolean> apiThreadState = new HashMap<>();

	public static <RQ, RS> void Request(final RQBaseModel<RQ, RS> rqModel) {
//		System.out.println(rqModel.toString());
		//Log.e("requeset",rqModel.toString());
		try {
			// 控制同一个接口的线程 同一时间只有一个在跑 当当前api对应的线程还没有跑或者已经结束时才能开始run
			if (!apiThreadState.containsKey(rqModel.getApiName()) || apiThreadState.get(rqModel.getApiName())) {
				apiThreadState.put(rqModel.getApiName(), false);
				new Thread(new Runnable() {
					@Override
					public void run() {  
						String resultJson = null;
						IHttpRequest httpRequest = new HttpRequestDigestImpl();
						if (NetworkValidator
								.isNetworkConnected(rqModel.context)) {
							if (rqModel.RequestType == RequestType.GET) {
								resultJson = httpRequest.get(rqModel.context,
										rqModel.rqModel,
										rqModel.getApiAddress(), 0, 0);
							} else if (rqModel.RequestType == RequestType.POST) {
//								System.out.println("rqModel ============ "+rqModel.toString());
					 			resultJson = httpRequest.post(rqModel.context,
										rqModel.rqModel,
										rqModel.getApiAddress(),0, 0);
							}
 							if (resultJson == null || resultJson.trim() == "") {
								rqModel.rqHandler.sendMessage(rqModel.rqHandler
										.obtainMessage(ResultMsgType.ServiceExp));
							} else {
								//System.out.println(resultJson);
								//Log.e("result",resultJson);
								@SuppressWarnings("unchecked")  
								RS rs = (RS) GsonTools.jsonToBean(resultJson,
										rqModel.rsModel.getClass());
								RSBase rsBase = (RSBase) rs;
								if (rs!=null&&rsBase.code.equals("200")) {
									// 成功从服务器获取数据  
									rqModel.rqHandler.sendMessage(rqModel.rqHandler
											.obtainMessage(
													ResultMsgType.Success, rs));
								} else {
									// 服务器错误 获取数据失败
									rqModel.rqHandler.sendMessage(rqModel.rqHandler
											.obtainMessage(
													ResultMsgType.ServiceErr,
													rs));
								}
							}
						} else {
							rqModel.rqHandler.sendMessage(rqModel.rqHandler
									.obtainMessage(ResultMsgType.NetworkNotValide));
						}
						apiThreadState.put(rqModel.getApiName(), true);
					}
				}).start();
			}
		} catch (Exception e) {
		}
	}
}
