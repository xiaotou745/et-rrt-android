package com.renrentui.net;

import java.util.List;
import java.util.Map;

import android.content.Context;

/**
 * 网络访问工具接口
 * 
 * @author EricHu
 * 
 */
public interface IHttpRequest {
	/**
	 * 超时设置
	 * 
	 * @param connectTimeOut
	 * @param soTimeout
	 */
	void setTimeOut(int connectTimeOut, int soTimeout);

	/**
	 * get请求
	 * 
	 * @param context
	 * @param param
	 *            eg: name=1&value=2
	 * @param path
	 *            eg: http://jingyangchun.com/api/
	 * @param connectTimeOut
	 * @param soTimeout
	 * @return
	 */
	String get(Context context, String param, String path,
			   int connectTimeOut, int soTimeout);
	String get(Context context, String param, String path);
	/**
	 * 
	 * @param context
	 * @param requestUrl eg: http://jingyangchun.com/api?name=1&value=1
	 * @return
	 */
	String get(Context context, String requestUrl);
	/**
	 * 
	 * @param context
	 * @param requestUrl eg: http://jingyangchun.com/api?name=1&value=1
	 * @param connectTimeOut
	 * @param soTimeout
	 * @return
	 */
	String get(Context context, String requestUrl, int connectTimeOut, int soTimeout);

	/**
	 * get请求
	 * 
	 * @param context
	 * @param param
	 *            参数的map对象
	 * @param path
	 *            eg: http://jingyangchun.com/api/
	 * @param connectTimeOut
	 * @param soTimeout
	 * @return
	 */
	String get(Context context, Map<String, Object> param, String path,
			   int connectTimeOut, int soTimeout);
	String get(Context context, Map<String, Object> param, String path);

	/**
	 * get请求
	 * 
	 * @param context
	 * @param requestModel
	 *            请求参数model
	 * @param path
	 *            eg: http://jingyangchun.com/api/
	 * @param connectTimeOut
	 * @param soTimeout
	 * @return
	 */
	<T> String get(Context context, T requestModel, String path,
				   int connectTimeOut, int soTimeout);
	<T> String get(Context context, T requestModel, String path);

	/**
	 * post请求 只传基本数据
	 * 
	 * @param context
	 * @param param
	 *            参数的map对象
	 * @param path
	 *            eg: http://jingyangchun.com/api/
	 * @param connectTimeOut
	 * @param soTimeout
	 * @return
	 */
	String post(Context context, Map<String, Object> param, String path,
				int connectTimeOut, int soTimeout);
	String post(Context context, Map<String, Object> param, String path);

	/**
	 * post请求 只传基本数据
	 * 
	 * @param context
	 * @param paramJson
	 *            参数的json序列
	 * @param path
	 *            eg: http://jingyangchun.com/api/
	 * @param connectTimeOut
	 * @param soTimeout
	 * @return
	 */
	String post(Context context, String paramJson, String path,
				int connectTimeOut, int soTimeout);
	String post(Context context, String paramJson, String path);

	/**
	 * post请求 只传基本数据
	 * 
	 * @param context
	 * @param requestModel
	 *            请求参数model
	 * @param path
	 *            eg: http://jingyangchun.com/api/
	 * @param connectTimeOut
	 * @param soTimeout
	 * @return
	 */
	<T> String post(Context context, T requestModel, String path,
					int connectTimeOut, int soTimeout);
	<T> String post(Context context, T requestModel, String path);
	<T> String postSecurity(Context context, T requestModel, String path,
					int connectTimeOut, int soTimeout);

	/**
	 * post请求 传基本数据以及文件（图片，视频，音频）
	 * 
	 * @param context
	 * @param param
	 *            参数的map对象
	 * @param path
	 *            eg: http://jingyangchun.com/api/
	 * @param connectTimeOut
	 * @param soTimeout
	 * @param files
	 *            文件路径
	 * @return
	 */
	String post(Context context, Map<String, Object> param, String path,
				List<String> files, int connectTimeOut, int soTimeout);
	String post(Context context, Map<String, Object> param, String path,
				List<String> files);

	/**
	 * post请求 传基本数据以及文件（图片，视频，音频）
	 * 
	 * @param context
	 * @param paramJson
	 *            参数的json序列
	 * @param path
	 *            eg: http://jingyangchun.com/api/
	 * @param connectTimeOut
	 * @param soTimeout
	 * @param files
	 *            文件路径
	 * @return
	 */
	String post(Context context, String paramJson, String path,
				List<String> files, int connectTimeOut, int soTimeout);
	String post(Context context, String paramJson, String path,
				List<String> files);

	/**
	 * post请求 传基本数据以及文件（图片，视频，音频）
	 * 
	 * @param context
	 * @param requestModel
	 *            请求参数model
	 * @param path
	 *            eg: http://jingyangchun.com/api/
	 * @param connectTimeOut
	 * @param soTimeout
	 * @param files
	 *            文件路径
	 * @return
	 */
	<T> String post(Context context, T requestModel, String path,
					List<String> files, int connectTimeOut, int soTimeout);
	<T> String post(Context context, T requestModel, String path,
					List<String> files);

	/**
	 * post请求 传基本数据以及文件（图片，视频，音频）可监听进度
	 * 
	 * @param context
	 * @param param
	 *            参数的map对象
	 * @param path
	 *            eg: http://jingyangchun.com/api/
	 * @param connectTimeOut
	 * @param soTimeout
	 * @param files
	 *            文件路径
	 * @param progressListener
	 *            进度回调
	 * @return
	 */
	String post(Context context, Map<String, Object> param, String path,
				List<String> files, ProgressListener progressListener,
				int connectTimeOut, int soTimeout);
	String post(Context context, Map<String, Object> param, String path,
				List<String> files, ProgressListener progressListener);

	/**
	 * post请求 传基本数据以及文件（图片，视频，音频）可监听进度
	 * 
	 * @param context
	 * @param paramJson
	 *            参数的json序列
	 * @param path
	 *            eg: http://jingyangchun.com/api/
	 * @param connectTimeOut
	 * @param soTimeout
	 * @param files
	 *            文件路径
	 * @param progressListener
	 *            进度回调
	 * @return
	 */
	String post(Context context, String paramJson, String path,
				List<String> files, ProgressListener progressListener,
				int connectTimeOut, int soTimeout);
	String post(Context context, String paramJson, String path,
				List<String> files, ProgressListener progressListener);

	/**
	 * post请求 传基本数据以及文件（图片，视频，音频）可监听进度
	 * 
	 * @param context
	 * @param requestModel
	 *            请求参数model
	 * @param path
	 *            eg: http://jingyangchun.com/api/
	 * @param connectTimeOut
	 * @param soTimeout
	 * @param files
	 *            文件路径
	 * @param progressListener
	 *            进度回调
	 * @return
	 */
	<T> String post(Context context, T requestModel, String path,
					List<String> files, ProgressListener progressListener,
					int connectTimeOut, int soTimeout);
	<T> String post(Context context, T requestModel, String path,
					List<String> files, ProgressListener progressListener);

}
