package com.android.library.utils;

import java.io.File;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Environment;

/** 设置存储路径 */
public class StorageUtils {
	private static StorageUtils INSTANCE;

	private String CACHE_NAME;

	public static StorageUtils getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new StorageUtils();
		}
		return INSTANCE;
	}

	/**
	 * 获取可以使用的缓存目录
	 * 
	 * @param context
	 *            设备上下文环境
	 * @param uniqueName
	 *            目录名称
	 * @return 缓存目录
	 */
	public File getCacheDir(Context context, String uniqueName) {
		final String cachePath = Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState()) ? getExternalCacheDir(context)
				.getPath() : context.getCacheDir().getPath();
		CACHE_NAME = cachePath + File.separator + uniqueName + File.separator;
		return new File(CACHE_NAME);
	}

	/**
	 * 获取扩展卡的缓存目录
	 * 
	 * @param context
	 *            设备上下文环境
	 * @return 扩展卡缓存路径
	 */
	@SuppressLint("NewApi")
	public File getExternalCacheDir(Context context) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
			return context.getExternalCacheDir();
		}
		// 在2.2之前，我们需要自己构建外部高速缓存目录
		final String cacheDir = "/Android/data/" + context.getPackageName()
				+ "/cache/";
		return new File(Environment.getExternalStorageDirectory().getPath()
				+ cacheDir);
	}

	/** 创建自定义缓存目录 */
	public File getExternalCacheDir(Context context, String cacheDirName) {
		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {
			String path = CACHE_NAME + cacheDirName;
			File file = new File(path);
			if (file.exists() && file.isFile()) {
				file.delete();
			}
			file.mkdir();
			CACHE_NAME = file.getPath() + cacheDirName + File.separator;
			return file;
		} else {
			return null;
		}
	}

	/** 创建自定义缓存子目录 */
	public File getExternalChildCacheDir(Context context, String cacheDirName) {
		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {
			String path = CACHE_NAME + cacheDirName;
			File file = new File(path);
			if (file.exists() && file.isFile()) {
				file.delete();
			}
			file.mkdir();
			return file;
		} else {
			return null;
		}
	}

}
