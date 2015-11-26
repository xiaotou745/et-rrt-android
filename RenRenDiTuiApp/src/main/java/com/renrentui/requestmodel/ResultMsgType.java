package com.renrentui.requestmodel;

/**
 * 请求返回消息类型
 * @author EricHu
 *
 */
public final class ResultMsgType {
	/**
	 * 网络无连接
	 */
	public static final int NetworkNotValide = 1;
	/**
	 * 成功请求到结果 success == true
	 */
	public static final int Success = 2;
	/**
	 * 服务器错误 -9999 success ==false
	 */
	public static final int ServiceErr = 3;
	/**
	 * 服务器异常 返回值为"" 
	 */
	public static final int ServiceExp = 4;
}
