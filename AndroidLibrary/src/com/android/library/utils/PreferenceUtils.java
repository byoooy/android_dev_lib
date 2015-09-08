package com.android.library.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * SharedPreferences配置保存工具
 */
public class PreferenceUtils {
	private final String CONFIG = "config";

	private SharedPreferences mShared;

	/**
	 * 构建配置工具
	 * 
	 * @param context
	 *            设备上下文环境
	 */
	public PreferenceUtils(Context context) {
		new PreferenceUtils(context, CONFIG);
	}

	/**
	 * 构建配置工具
	 * 
	 * @param context
	 *            设备上下文环境
	 * @param name
	 *            配置文件名
	 */
	public PreferenceUtils(Context context, String name) {
		mShared = context.getSharedPreferences(name, 0);
	}

	/**
	 * 保存boolean值
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 */
	public void put(String key, boolean value) {
		Editor editor = mShared.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}

	/**
	 * 获取boolean值
	 * 
	 * @param key
	 *            键
	 * @return 查找不到值，默认返回false
	 */
	public boolean getBoolean(String key) {
		return getBoolean(key, false);
	}

	/**
	 * 获取boolean值
	 * 
	 * @param key
	 *            键
	 * @param defValue
	 *            默认返回值
	 * @return 值
	 */
	public boolean getBoolean(String key, boolean defValue) {
		return mShared.getBoolean(key, defValue);
	}

	/**
	 * 保存float值
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 */
	public void put(String key, float value) {
		Editor editor = mShared.edit();
		editor.putFloat(key, value);
		editor.commit();
	}

	/**
	 * 获取float值
	 * 
	 * @param key
	 *            键
	 * @return 查找不到值，默认返回0
	 */
	public float getFloat(String key) {
		return getFloat(key, 0);
	}

	/**
	 * 获取float值
	 * 
	 * @param key
	 *            键
	 * @param defValue
	 *            默认返回值
	 * @return 值
	 */
	public float getFloat(String key, float defValue) {
		return mShared.getFloat(key, defValue);
	}

	/**
	 * 保存int值
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 */
	public void put(String key, int value) {
		Editor editor = mShared.edit();
		editor.putInt(key, value);
		editor.commit();
	}

	/**
	 * 获取int值
	 * 
	 * @param key
	 *            键
	 * @return 查找不到值，默认返回0
	 */
	public int getInt(String key) {
		return getInt(key, 0);
	}

	/**
	 * 获取int值
	 * 
	 * @param key
	 *            键
	 * @param defValue
	 *            默认返回值
	 * @return 值
	 */
	public int getInt(String key, int defValue) {
		return mShared.getInt(key, defValue);
	}

	/**
	 * 保存long值
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 */
	public void put(String key, long value) {
		Editor editor = mShared.edit();
		editor.putLong(key, value);
		editor.commit();
	}

	/**
	 * 获取long值
	 * 
	 * @param key
	 *            键
	 * @return 查找不到值，默认返回0
	 */
	public long getLong(String key) {
		return getLong(key, 0);
	}

	/**
	 * 获取long值
	 * 
	 * @param key
	 *            键
	 * @param defValue
	 *            默认返回值
	 * @return 值
	 */
	public long getLong(String key, long defValue) {
		return mShared.getLong(key, defValue);
	}

	/**
	 * 保存String值
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 */
	public void put(String key, String value) {
		Editor editor = mShared.edit();
		editor.putString(key, value);
		editor.commit();
	}

	/**
	 * 获取String值
	 * 
	 * @param key
	 *            键
	 * @return 查找不到值，默认返回null
	 */
	public String getString(String key) {
		return getString(key, null);
	}

	/**
	 * 获取String值
	 * 
	 * @param key
	 *            键
	 * @param defValue
	 *            默认返回值
	 * @return 值
	 */
	public String getString(String key, String defValue) {
		return mShared.getString(key, defValue);
	}

	/**
	 * 保存序列化对象
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 */
	public void put(String key, Serializable value) {
		try {
			ByteArrayOutputStream toByte = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(toByte);
			oos.writeObject(value);
			String str = new String(Base64EncryptUtil.getInstance().encode(
					toByte.toByteArray()));
			Editor editor = mShared.edit();
			editor.putString(key, str);
			editor.commit();
		} catch (IOException e) {
		}
	}

	/**
	 * 获取序列化对象
	 * 
	 * @param key
	 *            键
	 * @return 查找不到值，默认返回null
	 */
	public Object get(String key) {
		return get(key, null);
	}

	/**
	 * 获取序列化对象
	 * 
	 * @param key
	 *            键
	 * @param defValue
	 *            默认返回值
	 * @return 值
	 */
	public Object get(String key, Serializable defValue) {
		String str = mShared.getString(key, null);
		if (str == null) {
			return defValue;
		}
		try {
			byte[] baseBytes = Base64EncryptUtil.getInstance().decode(str)
					.getBytes();
			ByteArrayInputStream fromeByte = new ByteArrayInputStream(baseBytes);
			ObjectInputStream ois = new ObjectInputStream(fromeByte);
			return ois.readObject();
		} catch (Exception e) {
		}
		return defValue;
	}

	/**
	 * 清空保存的数据
	 */
	public void clear() {
		Editor editor = mShared.edit();
		editor.clear();
		editor.commit();
	}
}
