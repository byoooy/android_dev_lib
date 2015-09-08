package com.android.library.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.WindowManager;

/**
 * 获取系统相关参数
 */
public class SystemUtils {
	
	
	
	/** APP内部版本号 */
	public static int mVersionCode = 1;
	/** APP版本名 */
	public static String mVersionName = "1.0.0";
	/** 屏幕高度 */
	public static int mScreenHeight;
	/** 屏幕宽度 */
	public static int mScreenWidth;
	/** 设备唯一ID */
	public static String IMEI;

	/**
	 * 获取当前版本信息
	 */
	public void setVersionInfo(Context context) {
		try {
			PackageInfo packageInfo = context.getPackageManager()
					.getPackageInfo(context.getPackageName(), 0);
			mVersionCode = packageInfo.versionCode;
			mVersionName = packageInfo.versionName;
		} catch (NameNotFoundException e) {
		}
	}

	/**
	 * 获取屏幕大小
	 */
	public void setScreenSize(Context context) {
		final WindowManager windowManager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		final Display display = windowManager.getDefaultDisplay();
		DisplayMetrics displayMetrics = new DisplayMetrics();
		display.getMetrics(displayMetrics);
		boolean isPortrait = displayMetrics.widthPixels < displayMetrics.heightPixels;
		mScreenWidth = isPortrait ? displayMetrics.widthPixels
				: displayMetrics.heightPixels;
		mScreenHeight = isPortrait ? displayMetrics.heightPixels
				: displayMetrics.widthPixels;
	}

	/**
	 * 获取IMEI
	 */
	public void setIMEI(Context context) {
		TelephonyManager telephonyManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		IMEI = telephonyManager.getDeviceId();
	}

	/**
	 * dip转px
	 * 
	 * @param context
	 *            设备上下文环境
	 * @param dpValue
	 *            dp数值
	 * @return px数值
	 */
	public static float dipToPx(Context context, float dpValue) {
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue,
				context.getResources().getDisplayMetrics());
	}

	/**
	 * sp转px
	 * 
	 * @param context
	 *            设备上下文环境
	 * @param spValue
	 *            sp数值
	 * @return px数值
	 */
	public static float spToPx(Context context, float spValue) {
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue,
				context.getResources().getDisplayMetrics());
	}
	
	/**  
	 * 网络问题
	 * */
	
	
}
