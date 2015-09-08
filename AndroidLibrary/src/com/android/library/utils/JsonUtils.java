package com.android.library.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;

/** 解析JSON数据工具 */
public class JsonUtils {

	public static boolean isPrintException = true;

	private static JsonUtils instance;

	public static JsonUtils getInstance() {
		if (instance == null) {
			instance = new JsonUtils();
		}
		return instance;
	}

	/**
	 * get Long from jsonObject
	 * 
	 * @param jsonObject
	 * @param key
	 * @param defaultValue
	 */
	public Long getLong(JSONObject jsonObject, String key, Long defaultValue) {
		if (jsonObject == null || TextUtils.isEmpty(key)) {
			return defaultValue;
		}

		try {
			return jsonObject.getLong(key);
		} catch (JSONException e) {
			if (isPrintException) {
				e.printStackTrace();
			}
			return defaultValue;
		}
	}

	/**
	 * get Long from jsonData
	 * 
	 * @param jsonData
	 * @param key
	 * @param defaultValue
	 */
	public Long getLong(String jsonData, String key, Long defaultValue) {
		if (TextUtils.isEmpty(jsonData)) {
			return defaultValue;
		}

		try {
			JSONObject jsonObject = new JSONObject(jsonData);
			return getLong(jsonObject, key, defaultValue);
		} catch (JSONException e) {
			if (isPrintException) {
				e.printStackTrace();
			}
			return defaultValue;
		}
	}

	/**
	 * @param jsonObject
	 * @param key
	 * @param defaultValue
	 * @return
	 * @see JsonUtils#getLong(JSONObject, String, Long)
	 */
	public long getLong(JSONObject jsonObject, String key, long defaultValue) {
		return getLong(jsonObject, key, (Long) defaultValue);
	}

	/**
	 * @param jsonData
	 * @param key
	 * @param defaultValue
	 * @return
	 * @see JsonUtils#getLong(String, String, Long)
	 */
	public long getLong(String jsonData, String key, long defaultValue) {
		return getLong(jsonData, key, (Long) defaultValue);
	}

	/**
	 * get Int from jsonObject
	 * 
	 * @param jsonObject
	 * @param key
	 * @param defaultValue
	 */
	public Integer getInt(JSONObject jsonObject, String key,
			Integer defaultValue) {
		if (jsonObject == null || TextUtils.isEmpty(key)) {
			return defaultValue;
		}

		try {
			return jsonObject.getInt(key);
		} catch (JSONException e) {
			if (isPrintException) {
				e.printStackTrace();
			}
			return defaultValue;
		}
	}

	/**
	 * get Int from jsonData
	 * 
	 * @param jsonData
	 * @param key
	 * @param defaultValue
	 */
	public Integer getInt(String jsonData, String key, Integer defaultValue) {
		if (TextUtils.isEmpty(jsonData)) {
			return defaultValue;
		}

		try {
			JSONObject jsonObject = new JSONObject(jsonData);
			return getInt(jsonObject, key, defaultValue);
		} catch (JSONException e) {
			if (isPrintException) {
				e.printStackTrace();
			}
			return defaultValue;
		}
	}

	/**
	 * @param jsonObject
	 * @param key
	 * @param defaultValue
	 * @return
	 * @see JsonUtils#getInt(JSONObject, String, Integer)
	 */
	public int getInt(JSONObject jsonObject, String key, int defaultValue) {
		return getInt(jsonObject, key, (Integer) defaultValue);
	}

	/**
	 * @param jsonObject
	 * @param key
	 * @param defaultValue
	 * @return
	 * @see JsonUtils#getInt(String, String, Integer)
	 */
	public int getInt(String jsonData, String key, int defaultValue) {
		return getInt(jsonData, key, (Integer) defaultValue);
	}

	/**
	 * get Double from jsonObject
	 * 
	 * @param jsonObject
	 * @param key
	 * @param defaultValue
	 */
	public Double getDouble(JSONObject jsonObject, String key,
			Double defaultValue) {
		if (jsonObject == null || TextUtils.isEmpty(key)) {
			return defaultValue;
		}

		try {
			return jsonObject.getDouble(key);
		} catch (JSONException e) {
			if (isPrintException) {
				e.printStackTrace();
			}
			return defaultValue;
		}
	}

	/**
	 * get Double from jsonData
	 * 
	 * @param jsonData
	 * @param key
	 * @param defaultValue
	 */
	public Double getDouble(String jsonData, String key, Double defaultValue) {
		if (TextUtils.isEmpty(jsonData)) {
			return defaultValue;
		}

		try {
			JSONObject jsonObject = new JSONObject(jsonData);
			return getDouble(jsonObject, key, defaultValue);
		} catch (JSONException e) {
			if (isPrintException) {
				e.printStackTrace();
			}
			return defaultValue;
		}
	}

	/**
	 * @param jsonObject
	 * @param key
	 * @param defaultValue
	 * @return
	 * @see JsonUtils#getDouble(JSONObject, String, Double)
	 */
	public double getDouble(JSONObject jsonObject, String key,
			double defaultValue) {
		return getDouble(jsonObject, key, (Double) defaultValue);
	}

	/**
	 * @param jsonObject
	 * @param key
	 * @param defaultValue
	 * @return
	 * @see JsonUtils#getDouble(String, String, Double)
	 */
	public double getDouble(String jsonData, String key, double defaultValue) {
		return getDouble(jsonData, key, (Double) defaultValue);
	}

	/**
	 * get String from jsonObject
	 * 
	 * @param jsonObject
	 * @param key
	 * @param defaultValue
	 */
	public String getString(JSONObject jsonObject, String key,
			String defaultValue) {
		if (jsonObject == null || TextUtils.isEmpty(key)) {
			return defaultValue;
		}

		try {
			return jsonObject.getString(key);
		} catch (JSONException e) {
			if (isPrintException) {
				e.printStackTrace();
			}
			return defaultValue;
		}
	}

	/**
	 * get String from jsonData
	 * 
	 * @param jsonData
	 * @param key
	 * @param defaultValue
	 */
	public String getString(String jsonData, String key, String defaultValue) {
		if (TextUtils.isEmpty(jsonData)) {
			return defaultValue;
		}

		try {
			JSONObject jsonObject = new JSONObject(jsonData);
			return getString(jsonObject, key, defaultValue);
		} catch (JSONException e) {
			if (isPrintException) {
				e.printStackTrace();
			}
			return defaultValue;
		}
	}

	/**
	 * get String from jsonObject
	 * 
	 * @param jsonObject
	 * @param defaultValue
	 * @param keyArray
	 */
	public String getStringCascade(JSONObject jsonObject, String defaultValue,
			String... keyArray) {
		if (jsonObject == null || isArrayEmpty(keyArray)) {
			return defaultValue;
		}

		String data = jsonObject.toString();
		for (String key : keyArray) {
			data = getString(data, key, defaultValue);
			if (data == null) {
				return defaultValue;
			}
		}
		return data;
	}

	private <V> boolean isArrayEmpty(V[] sourceArray) {
		return (sourceArray == null || sourceArray.length == 0);
	}

	/**
	 * get String from jsonData
	 * 
	 * @param jsonData
	 * @param defaultValue
	 * @param keyArray
	 * @return <ul>
	 *         <li>if jsonData is null, return defaultValue</li>
	 *         <li>if keyArray is null or empty, return defaultValue</li>
	 *         <li>get {@link #getJSONObject(JSONObject, String, JSONObject)} by
	 *         recursion, return it. if anyone is null, return directly</li>
	 *         </ul>
	 */
	public String getStringCascade(String jsonData, String defaultValue,
			String... keyArray) {
		if (TextUtils.isEmpty(jsonData)) {
			return defaultValue;
		}

		String data = jsonData;
		for (String key : keyArray) {
			data = getString(data, key, defaultValue);
			if (data == null) {
				return defaultValue;
			}
		}
		return data;
	}

	/**
	 * get String array from jsonObject
	 * 
	 * @param jsonObject
	 * @param key
	 * @param defaultValue
	 */
	public String[] getStringArray(JSONObject jsonObject, String key,
			String[] defaultValue) {
		if (jsonObject == null || TextUtils.isEmpty(key)) {
			return defaultValue;
		}

		try {
			JSONArray statusArray = jsonObject.getJSONArray(key);
			if (statusArray != null) {
				String[] value = new String[statusArray.length()];
				for (int i = 0; i < statusArray.length(); i++) {
					value[i] = statusArray.getString(i);
				}
				return value;
			}
		} catch (JSONException e) {
			if (isPrintException) {
				e.printStackTrace();
			}
			return defaultValue;
		}
		return defaultValue;
	}

	/**
	 * get String array from jsonData
	 * 
	 * @param jsonData
	 * @param key
	 * @param defaultValue
	 */
	public String[] getStringArray(String jsonData, String key,
			String[] defaultValue) {
		if (TextUtils.isEmpty(jsonData)) {
			return defaultValue;
		}

		try {
			JSONObject jsonObject = new JSONObject(jsonData);
			return getStringArray(jsonObject, key, defaultValue);
		} catch (JSONException e) {
			if (isPrintException) {
				e.printStackTrace();
			}
			return defaultValue;
		}
	}

	/**
	 * get String list from jsonObject
	 * 
	 * @param jsonObject
	 * @param key
	 * @param defaultValue
	 */
	public List<String> getStringList(JSONObject jsonObject, String key,
			List<String> defaultValue) {
		if (jsonObject == null || TextUtils.isEmpty(key)) {
			return defaultValue;
		}

		try {
			JSONArray statusArray = jsonObject.getJSONArray(key);
			if (statusArray != null) {
				List<String> list = new ArrayList<String>();
				for (int i = 0; i < statusArray.length(); i++) {
					list.add(statusArray.getString(i));
				}
				return list;
			}
		} catch (JSONException e) {
			if (isPrintException) {
				e.printStackTrace();
			}
			return defaultValue;
		}
		return defaultValue;
	}

	/**
	 * get String list from jsonData
	 * 
	 * @param jsonData
	 * @param key
	 * @param defaultValue
	 */
	public List<String> getStringList(String jsonData, String key,
			List<String> defaultValue) {
		if (TextUtils.isEmpty(jsonData)) {
			return defaultValue;
		}

		try {
			JSONObject jsonObject = new JSONObject(jsonData);
			return getStringList(jsonObject, key, defaultValue);
		} catch (JSONException e) {
			if (isPrintException) {
				e.printStackTrace();
			}
			return defaultValue;
		}
	}

	/**
	 * get JSONObject from jsonObject
	 * 
	 * @param jsonObject
	 * @param key
	 * @param defaultValue
	 */
	public JSONObject getJSONObject(JSONObject jsonObject, String key,
			JSONObject defaultValue) {
		if (jsonObject == null || TextUtils.isEmpty(key)) {
			return defaultValue;
		}

		try {
			return jsonObject.getJSONObject(key);
		} catch (JSONException e) {
			if (isPrintException) {
				e.printStackTrace();
			}
			return defaultValue;
		}
	}

	/**
	 * get JSONObject from jsonData
	 * 
	 * @param jsonData
	 * @param key
	 * @param defaultValue
	 */
	public JSONObject getJSONObject(String jsonData, String key,
			JSONObject defaultValue) {
		if (TextUtils.isEmpty(jsonData)) {
			return defaultValue;
		}

		try {
			JSONObject jsonObject = new JSONObject(jsonData);
			return getJSONObject(jsonObject, key, defaultValue);
		} catch (JSONException e) {
			if (isPrintException) {
				e.printStackTrace();
			}
			return defaultValue;
		}
	}

	/**
	 * get JSONObject from jsonObject
	 * 
	 * @param jsonObject
	 * @param defaultValue
	 * @param keyArray
	 */
	public JSONObject getJSONObjectCascade(JSONObject jsonObject,
			JSONObject defaultValue, String... keyArray) {
		if (jsonObject == null || isArrayEmpty(keyArray)) {
			return defaultValue;
		}

		JSONObject js = jsonObject;
		for (String key : keyArray) {
			js = getJSONObject(js, key, defaultValue);
			if (js == null) {
				return defaultValue;
			}
		}
		return js;
	}

	/**
	 * get JSONObject from jsonData
	 * 
	 * @param jsonData
	 * @param defaultValue
	 * @param keyArray
	 */
	public JSONObject getJSONObjectCascade(String jsonData,
			JSONObject defaultValue, String... keyArray) {
		if (TextUtils.isEmpty(jsonData)) {
			return defaultValue;
		}

		try {
			JSONObject jsonObject = new JSONObject(jsonData);
			return getJSONObjectCascade(jsonObject, defaultValue, keyArray);
		} catch (JSONException e) {
			if (isPrintException) {
				e.printStackTrace();
			}
			return defaultValue;
		}
	}

	/**
	 * get JSONArray from jsonObject
	 * 
	 * @param jsonObject
	 * @param key
	 * @param defaultValue
	 */
	public JSONArray getJSONArray(JSONObject jsonObject, String key,
			JSONArray defaultValue) {
		if (jsonObject == null || TextUtils.isEmpty(key)) {
			return defaultValue;
		}

		try {
			return jsonObject.getJSONArray(key);
		} catch (JSONException e) {
			if (isPrintException) {
				e.printStackTrace();
			}
			return defaultValue;
		}
	}

	/**
	 * get JSONArray from jsonData
	 * 
	 * @param jsonData
	 * @param key
	 * @param defaultValue
	 */
	public JSONArray getJSONArray(String jsonData, String key,
			JSONArray defaultValue) {
		if (TextUtils.isEmpty(jsonData)) {
			return defaultValue;
		}

		try {
			JSONObject jsonObject = new JSONObject(jsonData);
			return getJSONArray(jsonObject, key, defaultValue);
		} catch (JSONException e) {
			if (isPrintException) {
				e.printStackTrace();
			}
			return defaultValue;
		}
	}

	/**
	 * get Boolean from jsonObject
	 * 
	 * @param jsonObject
	 * @param key
	 * @param defaultValue
	 */
	public boolean getBoolean(JSONObject jsonObject, String key,
			Boolean defaultValue) {
		if (jsonObject == null || TextUtils.isEmpty(key)) {
			return defaultValue;
		}

		try {
			return jsonObject.getBoolean(key);
		} catch (JSONException e) {
			if (isPrintException) {
				e.printStackTrace();
			}
			return defaultValue;
		}
	}

	/**
	 * get Boolean from jsonData
	 * 
	 * @param jsonData
	 * @param key
	 * @param defaultValue
	 */
	public boolean getBoolean(String jsonData, String key, Boolean defaultValue) {
		if (TextUtils.isEmpty(jsonData)) {
			return defaultValue;
		}

		try {
			JSONObject jsonObject = new JSONObject(jsonData);
			return getBoolean(jsonObject, key, defaultValue);
		} catch (JSONException e) {
			if (isPrintException) {
				e.printStackTrace();
			}
			return defaultValue;
		}
	}

	/**
	 * get map from jsonObject.
	 * 
	 * @param jsonObject
	 *            key-value pairs json
	 * @param key
	 */
	public Map<String, String> getMap(JSONObject jsonObject, String key) {
		return parseKeyAndValueToMap(getString(jsonObject, key, null));
	}

	/**
	 * get map from jsonData.
	 * 
	 * @param jsonData
	 *            key-value pairs string
	 * @param key
	 */
	public Map<String, String> getMap(String jsonData, String key) {

		if (jsonData == null) {
			return null;
		}
		if (jsonData.length() == 0) {
			return new HashMap<String, String>();
		}

		try {
			JSONObject jsonObject = new JSONObject(jsonData);
			return getMap(jsonObject, key);
		} catch (JSONException e) {
			if (isPrintException) {
				e.printStackTrace();
			}
			return null;
		}
	}

	/**
	 * parse key-value pairs to map. ignore empty key, if getValue exception,
	 * put empty value
	 * 
	 * @param sourceObj
	 *            key-value pairs json
	 */
	@SuppressWarnings("rawtypes")
	public Map<String, String> parseKeyAndValueToMap(JSONObject sourceObj) {
		if (sourceObj == null) {
			return null;
		}

		Map<String, String> keyAndValueMap = new HashMap<String, String>();
		for (Iterator iter = sourceObj.keys(); iter.hasNext();) {
			String key = (String) iter.next();
			putMapNotEmptyKey(keyAndValueMap, key,
					getString(sourceObj, key, ""));

		}
		return keyAndValueMap;
	}

	/**
	 * add key-value pair to map, and key need not null or empty
	 * 
	 * @param map
	 * @param key
	 * @param value
	 * @return <ul>
	 *         <li>if map is null, return false</li>
	 *         <li>if key is null or empty, return false</li>
	 *         <li>return {@link Map#put(Object, Object)}</li>
	 *         </ul>
	 */
	private boolean putMapNotEmptyKey(Map<String, String> map, String key,
			String value) {
		if (map == null || TextUtils.isEmpty(key)) {
			return false;
		}

		map.put(key, value);
		return true;
	}

	/**
	 * parse key-value pairs to map. ignore empty key, if getValue exception,
	 * put empty value
	 * 
	 * @param source
	 *            key-value pairs json
	 * @return <ul>
	 *         <li>if source is null or source's length is 0, return empty map</li>
	 *         <li>if source {@link JSONObject#JSONObject(String)} exception,
	 *         return null</li>
	 *         <li>return {@link JsonUtils#parseKeyAndValueToMap(JSONObject)}</li>
	 *         </ul>
	 */
	public Map<String, String> parseKeyAndValueToMap(String source) {
		if (TextUtils.isEmpty(source)) {
			return null;
		}

		try {
			JSONObject jsonObject = new JSONObject(source);
			return parseKeyAndValueToMap(jsonObject);
		} catch (JSONException e) {
			if (isPrintException) {
				e.printStackTrace();
			}
			return null;
		}
	}
}
