package com.android.library.json;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;

/** json解析的工具类 */
public class JsonHelper {

	private static JsonHelper HELPER;

	private JsonHelper() {
	}

	public static JsonHelper getInstance() {
		if (HELPER == null) {
			HELPER = new JsonHelper();
		}
		return HELPER;
	}

	/** 将javaBean解析为JSON文本 */
	public <T> String parseString(T t) {
		return JSON.toJSONString(t);
	}

	/** 将JSON文本解析为JavaBean对象 */
	public <T> T parseObject(String value, Class<T> clazz) {
		T t = null;
		if (!TextUtils.isEmpty(value)) {
			t = JSON.parseObject(value, clazz);
		}
		return t;
	}

	/** 将JSON对象解析为JavaBean对象 */
	@SuppressWarnings("unchecked")
	public <T> T parseObject(JSON json, Class<Object> clazz) {
		T t = null;
		if (json != null) {
			t = (T) JSON.toJavaObject(json, clazz);
		}
		return t;
	}

}
