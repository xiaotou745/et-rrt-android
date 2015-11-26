package com.renrentui.tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.renderscript.Type;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * json解析工具
 * 
 * @author Eric
 * 
 */
public class GsonTools {
	/*
	 * json解析为单个实体
	 */
	public static <T> T jsonToBean(String jsonString, Class<T> cls) {
		T t = null;
		try {
			Gson gson = new Gson();
			
			t = gson.fromJson(jsonString, cls);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return t;
	}

	/*
	 * json解析为实体列表，实体类型为list<T>类型
	 */
	public static <T> List<T> jsonToList(String jsonString, Class<T> cls) {
		List<T> list = new ArrayList<T>();
		try {
			Gson gson = new Gson();
			list = gson.fromJson(jsonString, new TypeToken<List<T>>() {
			}.getType());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	public static <T> List<T> jsonToList(String jsonString, java.lang.reflect.Type type) {
		List<T> list = new ArrayList<T>();
		try {
			Gson gson = new Gson();
			list = gson.fromJson(jsonString, type);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/*
	 * json解析为实体列表，实体类型为map<object,object>类型
	 */
	public static Map<Object, Object> jsonToMap(String jsonString) {
		Map<Object, Object> map = new HashMap<>();
		try {
			Gson gson = new Gson();
			map = gson.fromJson(jsonString,
					new TypeToken<Map<Object, Object>>() {
					}.getType());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	/*
	 * json解析为实体列表，实体类型为list<map<object,object>>类型
	 */
	public static List<Map<Object, Object>> jsonToMapList(String jsonString) {
		List<Map<Object, Object>> list = new ArrayList<Map<Object, Object>>();
		try {
			Gson gson = new Gson();
			list = gson.fromJson(jsonString,
					new TypeToken<List<Map<Object, Object>>>() {
					}.getType());
		} catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}
	/*
	 * 将object解析为Json  ， object可以是T,list<T>,map<T,T>,list<map<T,T>>
	 */
	public static String objectToJson(Object object) {
		String jsonString = null;
		try {
			Gson gson = new Gson();
			jsonString = gson.toJson(object);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return jsonString;
	}

}
