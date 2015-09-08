package com.android.library.base;

import com.android.library.utils.LogUtils;

import android.app.Activity;
import android.os.Bundle;

public class BaseActivity extends Activity {
	public static final String TAG = BaseActivity.class.getSimpleName();

	@Override
	protected final void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initCreate(savedInstanceState);
	}

	protected void initCreate(Bundle savedInstanceState) {
		initConfig(savedInstanceState);
		initViews();
		initData(savedInstanceState);
	}

	public void initConfig(Bundle savedInstanceState) {
	}

	/**
	 * 初始化Views方法
	 */
	public void initViews() {
	}

	/**
	 * 初始化数据方法
	 * 
	 * @param savedInstanceState
	 *            保存信息
	 */
	public void initData(Bundle savedInstanceState) {
	}

	@Override
	protected void onResume() {
		super.onResume();
		LogUtils.i(TAG, "onResume");
	}

	@Override
	protected void onPause() {
		super.onPause();
		LogUtils.i(TAG, "onPause");
	}

	@Override
	protected void onStop() {
		super.onStop();
		LogUtils.i(TAG, "onStop");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		LogUtils.i(TAG, "onDestroy");
	}

	@Override
	public void onBackPressed() {
	}

}
