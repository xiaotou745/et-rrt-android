package com.renrentui.interfaces;

/**
 * RQHandler 对应的接口
 * 
 * @author EricHu
 * 
 */
public interface IRqHandlerMsg<T> {
	void onBefore();

	void onNetworknotvalide();

	void onSuccess(T t);

	void onSericeErr(T t);

	void onSericeExp();
}
