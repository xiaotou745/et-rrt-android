package com.renrentui.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.Gson;
import com.renrentui.tools.GsonTools;
import com.renrentui.tools.Util;
import com.renrentui.util.Security;

import android.R.string;
import android.content.Context;

public class HttpRequestDigestImpl implements IHttpRequest {
	private HttpDigestRequest httpDigestRequest;

	public HttpRequestDigestImpl() {
		super();
		this.httpDigestRequest = new HttpDigestRequest();
	}

	@Override
	public void setTimeOut(int connectTimeOut, int soTimeout) {
		// TODO Auto-generated method stub
		httpDigestRequest.setConnectTimeOut(connectTimeOut);
		httpDigestRequest.setSoTimeout(soTimeout);
	}

	@Override
	public String get(Context context, String param, String path,
			int connectTimeOut, int soTimeout) {
		String requestUrl = "";
		if (path == null || path.trim() == "") {
			return "";
		}
		requestUrl = buildRequest(path, param);
		return get(context, requestUrl, connectTimeOut, soTimeout);
	}

	@Override
	public String get(Context context, Map<String, Object> param, String path,
			int connectTimeOut, int soTimeout) {
		String requestUrl = "";
		if (path == null || path.trim() == "") {
			return "";
		}
		Map<String, String> map = getMap(param);
		requestUrl = buildRequest(path, map);
		return get(context, requestUrl, connectTimeOut, soTimeout);
	}

	@Override
	public <T> String get(Context context, T requestModel, String path,
			int connectTimeOut, int soTimeout) {
		// TODO Auto-generated method stub
		String requestUrl = "";
		if (path == null || path.trim() == "") {
			return "";
		}
		requestUrl = buildRequest(path, requestModel);
		return get(context, requestUrl, connectTimeOut, soTimeout);
	}

	@Override
	public String post(Context context, Map<String, Object> param, String path,
			int connectTimeOut, int soTimeout) {
		// TODO Auto-generated method stub
		String paramJson = "";
		if (param != null) {
			paramJson = GsonTools.objectToJson(param);
			System.out.println(paramJson);
		}
		return post(context, paramJson, path, connectTimeOut, soTimeout);
	}

	@Override
	public String post(Context context, String paramJson, String path,
			int connectTimeOut, int soTimeout) {
		// TODO Auto-generated method stub
		if (path == null || path.trim() == "") {
			return "";
		}
		return streamToStr(httpDigestRequest.sendhttpPost(context, path,
				paramJson, connectTimeOut, soTimeout));
	}

	@Override
	public <T> String post(Context context, T requestModel, String path,
			int connectTimeOut, int soTimeout) {
		// TODO Auto-generated method stub
		String paramJson = "";
		if (requestModel != null) {
			paramJson = GsonTools.objectToJson(requestModel);
//			paramJson = "{param:"+Security.aesEncrypt(paramJson)+"}";
			System.out.println(paramJson);
		}
		return post(context, paramJson, path, connectTimeOut, soTimeout);
	}

	@Override
	public String post(Context context, Map<String, Object> param, String path,
			List<String> files, int connectTimeOut, int soTimeout) {
		String paramJson = "";
		if (param != null) {
			paramJson = GsonTools.objectToJson(param);
			System.out.println(paramJson);
		}
		return post(context, paramJson, path, files, connectTimeOut, soTimeout);
	}

	@Override
	public String post(Context context, String paramJson, String path,
			List<String> files, int connectTimeOut, int soTimeout) {
		// TODO Auto-generated method stub
		if (path == null || path.trim() == "") {
			return "";
		}
		return streamToStr(httpDigestRequest.sendhttpPost(context, path,
				paramJson, files, connectTimeOut, soTimeout));
	}

	@Override
	public <T> String post(Context context, T requestModel, String path,
			List<String> files, int connectTimeOut, int soTimeout) {
		String paramJson = "";
		if (requestModel != null) {
			paramJson = GsonTools.objectToJson(requestModel);
			System.out.println(paramJson);
		}
		return post(context, paramJson, path, files, connectTimeOut, soTimeout);
	}

	@Override
	public String post(Context context, Map<String, Object> param, String path,
			List<String> files, ProgressListener progressListener,
			int connectTimeOut, int soTimeout) {
		String paramJson = "";
		if (param != null) {
			paramJson = GsonTools.objectToJson(param);
			System.out.println(paramJson);
		}
		return post(context, paramJson, path, files, progressListener,
				connectTimeOut, soTimeout);
	}

	@Override
	public String post(Context context, String paramJson, String path,
			List<String> files, ProgressListener progressListener,
			int connectTimeOut, int soTimeout) {
		// TODO Auto-generated method stub
		if (path == null || path.trim() == "") {
			return "";
		}
		return streamToStr(httpDigestRequest.sendhttpPost(context, path,
				paramJson, files, connectTimeOut, soTimeout, progressListener));
	}

	@Override
	public <T> String post(Context context, T requestModel, String path,
			List<String> files, ProgressListener progressListener,
			int connectTimeOut, int soTimeout) {
		String paramJson = "";
		if (requestModel != null) {
			paramJson = GsonTools.objectToJson(requestModel);
			System.out.println(paramJson);
		}
		return post(context, paramJson, path, files, progressListener,
				connectTimeOut, soTimeout);
	}

	@Override
	public String get(Context context, String requestUrl) {
		// TODO Auto-generated method stub
		if (requestUrl == null || requestUrl.trim() == "") {
			return "";
		}
		return streamToStr(httpDigestRequest.sendhttpGet(context, requestUrl,
				0, 0));
	}

	@Override
	public String get(Context context, String requestUrl, int connectTimeOut,
			int soTimeout) {
		// TODO Auto-generated method stub
		if (requestUrl == null || requestUrl.trim() == "") {
			return "";
		}
		return streamToStr(httpDigestRequest.sendhttpGet(context, requestUrl,
				connectTimeOut, soTimeout));
	}

	@Override
	public String get(Context context, String param, String path) {
		// TODO Auto-generated method stub
		String requestUrl = "";
		if (path == null || path.trim() == "") {
			return "";
		}
		if (param == null || param.trim() == "") {
			param = "";
		}
		requestUrl = buildRequest(path, param);
		return get(context, requestUrl);
	}

	@Override
	public String get(Context context, Map<String, Object> param, String path) {
		// TODO Auto-generated method stub
		String requestUrl = "";
		Map<String, String> map = getMap(param);
		requestUrl = buildRequest(path, map);
		return get(context, requestUrl);
	}

	@Override
	public <T> String get(Context context, T requestModel, String path) {
		// TODO Auto-generated method stub
		String requestUrl = "";
		if (path == null || path.trim() == "") {
			return "";
		}
		requestUrl = buildRequest(path, requestModel);
		return get(context, requestUrl);
	}

	@Override
	public String post(Context context, Map<String, Object> param, String path) {
		// TODO Auto-generated method stub
		String paramJson = "";
		if (param != null) {
			paramJson = GsonTools.objectToJson(param);
			System.out.println(paramJson);
		}
		return post(context, paramJson, path);
	}

	@Override
	public String post(Context context, String paramJson, String path) {
		// TODO Auto-generated method stub
		return streamToStr(httpDigestRequest.sendhttpPost(context, path,
				paramJson, 0, 0));
	}

	@Override
	public <T> String post(Context context, T requestModel, String path) {
		// TODO Auto-generated method stub
		String requestJson = "";
		if (requestModel != null) {
			GsonTools.objectToJson(requestModel);
		}
		return post(context, requestJson, path);
	}

	@Override
	public String post(Context context, Map<String, Object> param, String path,
			List<String> files) {
		// TODO Auto-generated method stub
		String paramJson = "";
		if (param != null) {
			paramJson = GsonTools.objectToJson(param);
			System.out.println(paramJson);
		}
		return post(context, paramJson, path, files);
	}

	@Override
	public String post(Context context, String paramJson, String path,
			List<String> files) {
		// TODO Auto-generated method stub
		return streamToStr(httpDigestRequest.sendhttpPost(context, path,
				paramJson, files, 0, 0));
	}

	@Override
	public <T> String post(Context context, T requestModel, String path,
			List<String> files) {
		// TODO Auto-generated method stub
		String requestJson = "";
		if (requestModel != null) {
			GsonTools.objectToJson(requestModel);
		}
		return post(context, requestJson, path, files);
	}

	@Override
	public String post(Context context, Map<String, Object> param, String path,
			List<String> files, ProgressListener progressListener) {
		// TODO Auto-generated method stub
		String requestJson = "";
		if (param != null) {
			requestJson = GsonTools.objectToJson(param);
		}
		return post(context, requestJson, path, files, progressListener);
	}

	@Override
	public String post(Context context, String paramJson, String path,
			List<String> files, ProgressListener progressListener) {
		// TODO Auto-generated method stub
		return streamToStr(httpDigestRequest.sendhttpPost(context, path,
				paramJson, files, 0, 0, progressListener));
	}

	@Override
	public <T> String post(Context context, T requestModel, String path,
			List<String> files, ProgressListener progressListener) {
		String requestJson = "";
		if (requestModel != null) {
			GsonTools.objectToJson(requestModel);
		}
		return post(context, requestJson, path, files, progressListener);
	}
	public <T,S> S post(Context context,T requetModel,Class<S> s){
		try {
			return s.newInstance();
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * map<String,Object> 转换 map<String,String>
	 */
	public Map<String, String> getMap(Map<String, Object> params) {
		Map<String, String> map = new HashMap<String, String>();
		if (params != null) {
			for (Entry<String, Object> e : params.entrySet()) {
				String key = e.getKey();
				Object value = e.getValue();
				if (value != null) {
					map.put(key, value.toString());
				} else {
					map.put(key, "");
				}
			}
		}
		return map;
	}

	/**
	 * 流转换成String
	 */
	public String streamToStr(InputStream is) {
		if (is == null) {
			return null;
		}
		String result = null;
		BufferedReader reader = null;
		StringBuilder builder = new StringBuilder();
		reader = new BufferedReader(new InputStreamReader(is));
		try {
			for (String s = reader.readLine(); s != null; s = reader.readLine()) {
				builder.append(s);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		result = builder.toString();
		return result;
	}

	// 构建请求url参数
	public String buildRequest(String path, Map<String, String> params) {
		StringBuffer sb = new StringBuffer(Util.TrimRightStr(path, "/") + "/");
		if (params != null) {
			sb.append("?");
			for (Entry<String, String> e : params.entrySet()) {
				sb.append(e.getKey());
				sb.append("=");
				try {
					sb.append(URLEncoder.encode(e.getValue(), "utf-8"));
				} catch (UnsupportedEncodingException e1) {
					sb.append(e.getValue());
					e1.printStackTrace();
				}
				sb.append("&");
			}
			sb.deleteCharAt(sb.length() - 1);
		}
		return new String(sb.toString());
	}

	public String buildRequest(String path, String params) {
		StringBuffer sb = new StringBuffer(Util.TrimRightStr(path, "/"));
		if (params != null) {
			sb.append("?").append(params);
		}
		return new String(sb.toString());
	}

	public <T> String buildRequest(String path, T t) {
		StringBuffer sb = new StringBuffer(Util.TrimRightStr(path, "/"));
		if (t != null) {
			sb.append("?");
			Field[] fields = t.getClass().getFields();
			for (Field field : fields) {
				sb.append(field.getName()).append("=");
				try {
					sb.append(URLEncoder.encode(field.get(t)+"", "utf-8"));
				} catch (Exception e) {
				}
				sb.append("&");
			}
			sb.deleteCharAt(sb.length() - 1);
		}
		return new String(sb.toString());
	}

}
