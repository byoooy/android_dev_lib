package com.android.library.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * FragmentActivity扩展类，统一风格。
 * 
 * @author kycq
 * 
 */
public abstract class BaseFragmentActivity extends FragmentActivity {
	/** Fragment管理实例 */
	protected FragmentManager mFManager;
	/** Fragment堆栈 */
	private HashMap<Integer, ArrayList<String>> mStackFragments;
	/** 当前展示Fragments */
	private HashMap<Integer, String> mCurrentFragments;
	/** Fragment附属资源ID */
	private int mResId = -1;

	@SuppressLint("UseSparseArrays")
	@SuppressWarnings("unchecked")
	@Override
	protected final void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mFManager = getSupportFragmentManager();
		if (savedInstanceState != null) {
			mStackFragments = (HashMap<Integer, ArrayList<String>>) savedInstanceState
					.get("BaseFragmentActivity_mStackFragments");
			mCurrentFragments = (HashMap<Integer, String>) savedInstanceState
					.get("BaseFragmentActivity_mCurrentFragments");
		}
		if (mStackFragments == null) {
			mStackFragments = new HashMap<Integer, ArrayList<String>>();
		}
		if (mCurrentFragments == null) {
			mCurrentFragments = new HashMap<Integer, String>();
		}

		for (Entry<Integer, String> entry : mCurrentFragments.entrySet()) {
			int resId = entry.getKey();
			String tag = entry.getValue();
			changeFragment(resId, findFragmentByTag(resId, tag), tag);
		}

		initCreate(savedInstanceState);
	}

	/**
	 * {@linkplain Activity#onCreate(Bundle) <span style="color:#0000FF">
	 * onCreate(Bundle)</span>}扩展
	 * 
	 * @param savedInstanceState
	 *            保存信息
	 */
	protected void initCreate(Bundle savedInstanceState) {
		initConfig(savedInstanceState);
		initViews();
		initData(savedInstanceState);
	}

	/**
	 * 初始化配置方法，在{@linkplain BaseFragmentActivity#initViews() <span
	 * style="color:#0000FF">initViews()</span>}之前调用
	 * 
	 * @param savedInstanceState
	 *            保存信息
	 */
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
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putSerializable("BaseFragmentActivity_mStackFragments",
				mStackFragments);
		outState.putSerializable("BaseFragmentActivity_mCurrentFragments",
				mCurrentFragments);
	}

	@Override
	public void onBackPressed() {
		ArrayList<String> stack = mStackFragments.get(mResId);
		if (stack == null || stack.size() == 0) {
			finish();
		} else {
			String top = stack.get(stack.size() - 1);
			BaseFragment fragment = findFragmentByTag(mResId, top);
			fragment.onBackPressed();
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	/**
	 * 获取默认堆栈数量
	 * 
	 * @return 堆栈数量
	 */
	public int size() {
		return size(mResId);
	}

	/**
	 * 获取指定资源ID堆栈数量
	 * 
	 * @param resId
	 *            资源ID
	 * @return 堆栈数量
	 */
	public int size(int resId) {
		ArrayList<String> stack = mStackFragments.get(resId);
		if (stack == null) {
			return 0;
		}
		return stack.size();
	}

	/**
	 * 设置默认资源ID
	 * 
	 * @param resId
	 *            资源ID
	 */
	public final void setFragmentViewId(int resId) {
		mResId = resId;
	}

	/**
	 * 获取默认资源ID的BaseFragment
	 * 
	 * @return BaseFragment实例
	 */
	public final BaseFragment findFragmentById() {
		return findFragmentById(mResId);
	}

	/**
	 * 获取指定资源ID的BaseFragment
	 * 
	 * @param resId
	 *            资源ID
	 * @return BaseFragment实例
	 */
	public final BaseFragment findFragmentById(int resId) {
		return (BaseFragment) mFManager.findFragmentById(resId);
	}

	/**
	 * 获取默认资源ID的BaseFragment
	 * 
	 * @param clazz
	 *            BaseFragment类型
	 * @param <T>
	 *            BaseFragment类型
	 * @return BaseFragment实例
	 */
	@SuppressWarnings("unchecked")
	public final <T> T findFragmentByClass(Class<T> clazz) {
		return (T) findFragmentByTag(mResId, clazz.getName());
	}

	/**
	 * 获取指定资源ID的BaseFragment
	 * 
	 * @param resId
	 *            资源ID
	 * @param clazz
	 *            BaseFragment类型
	 * @param <T>
	 *            BaseFragment类型
	 * @return BaseFragment实例
	 */
	@SuppressWarnings("unchecked")
	public final <T> T findFragmentByClass(int resId, Class<T> clazz) {
		return (T) findFragmentByTag(resId, clazz.getName());
	}

	/**
	 * 获取默认资源ID的BaseFragment
	 * 
	 * @param tag
	 *            BaseFragment标签
	 * @return BaseFragment实例
	 */
	public final BaseFragment findFragmentByTag(String tag) {
		return findFragmentByTag(mResId, tag);
	}

	/**
	 * 获取指定资源ID的BaseFragment
	 * 
	 * @param resId
	 *            资源ID
	 * @param tag
	 *            BaseFragment标签
	 * @return BaseFragment实例
	 */
	public final BaseFragment findFragmentByTag(int resId, String tag) {
		return (BaseFragment) mFManager.findFragmentByTag(resId + "@" + tag);
	}

	/**
	 * 获取默认资源ID的当前展示BaseFragment
	 * 
	 * @return BaseFragment实例
	 */
	public final BaseFragment getCurrentFragment() {
		return getCurrentFragment(mResId);
	}

	/**
	 * 获取指定资源ID的当前展示BaseFragment
	 * 
	 * @param resId
	 *            资源ID
	 * @return BaseFragment实例
	 */
	public final BaseFragment getCurrentFragment(int resId) {
		return findFragmentByTag(resId, mCurrentFragments.get(resId));
	}

	/**
	 * 配置动画效果
	 * 
	 * @param resId
	 *            资源ID
	 * @param enterFragment
	 *            进入的BaseFragment
	 * @param exitFragment
	 *            退出的BaseFragment
	 * @param transaction
	 *            操作实例
	 * @param remove
	 *            true 退栈操作
	 *            <p>
	 *            false 进栈操作
	 */
	public void onCreateAnimation(int resId, BaseFragment enterFragment,
			BaseFragment exitFragment, FragmentTransaction transaction,
			boolean remove) {
	}

	/**
	 * 替换默认资源ID当前展示BaseFragment
	 * 
	 * @param fragment
	 *            BaseFragment实例
	 * @return BaseFragment实例
	 */
	public final BaseFragment replaceFragment(BaseFragment fragment) {
		return replaceFragment(mResId, fragment, fragment.getClass().getName());
	}

	/**
	 * 替换指定资源ID当前展示BaseFragment
	 * 
	 * @param resId
	 *            资源ID
	 * @param fragment
	 *            BaseFragment实例
	 * @return BaseFragment实例
	 */
	public final BaseFragment replaceFragment(int resId, BaseFragment fragment) {
		return replaceFragment(resId, fragment, fragment.getClass().getName());
	}

	/**
	 * 替换默认资源ID当前展示BaseFragment
	 * 
	 * @param fragment
	 *            BaseFragment实例
	 * @param tag
	 *            BaseFragment标签
	 * @return BaseFragment实例
	 */
	public final BaseFragment replaceFragment(BaseFragment fragment, String tag) {
		return replaceFragment(mResId, fragment, tag);
	}

	/**
	 * 替换指定资源ID当前展示BaseFragment
	 * 
	 * @param resId
	 *            资源ID
	 * @param fragment
	 *            BaseFragment实例
	 * @param tag
	 *            BaseFragment标签
	 * @return BaseFragment实例
	 */
	public final BaseFragment replaceFragment(int resId, BaseFragment fragment,
			String tag) {
		FragmentTransaction ft = mFManager.beginTransaction();

		BaseFragment exitFragment = null;
		String exitTag = mCurrentFragments.get(resId);
		if (exitTag != null) {
			exitFragment = findFragmentByTag(resId, exitTag);
		}
		onCreateAnimation(resId, fragment, exitFragment, ft, false);
		mCurrentFragments.put(resId, tag);

		ft.replace(resId, fragment, resId + "@" + tag);
		ft.commitAllowingStateLoss();

		return fragment;
	}

	/**
	 * 变更默认资源ID当前展示BaseFragment
	 * 
	 * @param fragment
	 *            BaseFragment实例
	 * @return BaseFragment实例
	 */
	public final BaseFragment changeFragment(BaseFragment fragment) {
		return changeFragment(mResId, fragment, fragment.getClass().getName());
	}

	/**
	 * 变更指定资源ID当前展示BaseFragment
	 * 
	 * @param resId
	 *            资源ID
	 * @param fragment
	 *            BaseFragment实例
	 * @return BaseFragment实例
	 */
	public final BaseFragment changeFragment(int resId, BaseFragment fragment) {
		return changeFragment(resId, fragment, fragment.getClass().getName());
	}

	/**
	 * 变更默认资源ID当前展示BaseFragment
	 * 
	 * @param fragment
	 *            BaseFragment实例
	 * @param tag
	 *            BaseFragment标签
	 * @return BaseFragment实例
	 */
	public final BaseFragment changeFragment(BaseFragment fragment, String tag) {
		return changeFragment(mResId, fragment, tag);
	}

	/**
	 * 变更指定资源ID当前展示BaseFragment
	 * 
	 * @param resId
	 *            资源ID
	 * @param fragment
	 *            BaseFragment实例
	 * @param tag
	 *            BaseFragment标签
	 * @return BaseFragment实例
	 */
	public final BaseFragment changeFragment(int resId, BaseFragment fragment,
			String tag) {
		FragmentTransaction ft = mFManager.beginTransaction();
		BaseFragment exitFragment = null;
		String exitTag = mCurrentFragments.get(resId);
		if (exitTag != null) {
			exitFragment = findFragmentByTag(resId, exitTag);
		}
		onCreateAnimation(resId, fragment, exitFragment, ft, false);
		mCurrentFragments.put(resId, tag);

		if (mFManager.getFragments() != null) {
			for (Fragment f : mFManager.getFragments()) {
				if (f != null && f.getId() == resId) {
					ft.hide(f);
				}
			}
		}

		if (findFragmentByTag(resId, tag) == null) {
			ft.add(resId, fragment, resId + "@" + tag);
		} else {
			fragment = findFragmentByTag(resId, tag);
		}
		ft.show(fragment);
		ft.commitAllowingStateLoss();

		return fragment;
	}

	/**
	 * 添加默认资源ID的堆栈的BaseFragment
	 * 
	 * @param fragment
	 *            BaseFragment实例
	 * @return BaseFragment实例
	 */
	public final BaseFragment putFragment(BaseFragment fragment) {
		return putFragment(mResId, fragment, fragment.getClass().getName());
	}

	/**
	 * 添加指定资源ID的堆栈的BaseFragment
	 * 
	 * @param resId
	 *            资源ID
	 * @param fragment
	 *            BaseFragment实例
	 * @return BaseFragment实例
	 */
	public final BaseFragment putFragment(int resId, BaseFragment fragment) {
		return putFragment(resId, fragment, fragment.getClass().getName());
	}

	/**
	 * 添加默认资源ID的堆栈的BaseFragment
	 * 
	 * @param fragment
	 *            BaseFragment实例
	 * @param tag
	 *            BaseFragment标签
	 * @return BaseFragment实例
	 */
	public final BaseFragment putFragment(BaseFragment fragment, String tag) {
		return putFragment(mResId, fragment, tag);
	}

	/**
	 * 添加指定资源ID的堆栈的BaseFragment
	 * 
	 * @param resId
	 *            资源ID
	 * @param fragment
	 *            BaseFragment实例
	 * @param tag
	 *            BaseFragment标签
	 * @return BaseFragment实例
	 */
	public final BaseFragment putFragment(int resId, BaseFragment fragment,
			String tag) {
		ArrayList<String> stack = mStackFragments.get(resId);
		if (stack == null) {
			stack = new ArrayList<String>();
			mStackFragments.put(resId, stack);
		}
		tag = tag + "#" + stack.size();

		FragmentTransaction ft = mFManager.beginTransaction();
		BaseFragment exitFragment = null;
		String exitTag = mCurrentFragments.get(resId);
		if (exitTag != null) {
			exitFragment = findFragmentByTag(resId, exitTag);
		}
		onCreateAnimation(resId, fragment, exitFragment, ft, false);
		mCurrentFragments.put(resId, tag);

		if (mFManager.getFragments() != null) {
			for (Fragment f : mFManager.getFragments()) {
				if (f != null && f.getId() == resId) {
					ft.hide(f);
				}
			}
		}

		ft.add(resId, fragment, resId + "@" + tag);
		ft.commitAllowingStateLoss();

		stack.add(tag);

		return fragment;
	}

	/**
	 * 弹出默认资源ID的堆栈的BaseFragment
	 * 
	 * @return BaseFragment实例
	 */
	public final BaseFragment popFragment() {
		return popFragment(mResId);
	}

	/**
	 * 添加指定资源ID的堆栈的展示BaseFragment
	 * 
	 * @param resId
	 *            资源ID
	 * @return BaseFragment实例
	 */
	public final BaseFragment popFragment(int resId) {
		ArrayList<String> stack = mStackFragments.get(resId);
		if (stack == null || stack.size() <= 1) {
			finish();
			return null;
		} else {
			String exitTag = stack.get(stack.size() - 1);
			String enterTag = stack.get(stack.size() - 2);

			FragmentTransaction ft = mFManager.beginTransaction();
			BaseFragment enterFragment = findFragmentByTag(resId, enterTag);
			BaseFragment exitFragment = findFragmentByTag(resId, exitTag);
			onCreateAnimation(resId, enterFragment, exitFragment, ft, true);

			ft.remove(exitFragment);
			ft.show(enterFragment);
			ft.commitAllowingStateLoss();

			mCurrentFragments.put(resId, enterTag);
			stack.remove(stack.size() - 1);

			return enterFragment;
		}
	}

	/**
	 * 移除默认资源ID的BaseFragment
	 * 
	 * @param fragment
	 *            BaseFragment实例
	 * @return BaseFragment实例
	 */
	public final BaseFragment addFragment(BaseFragment fragment) {
		return addFragment(mResId, fragment, fragment.getClass().getName());
	}

	/**
	 * 添加指定资源ID的BaseFragment
	 * 
	 * @param resId
	 *            资源ID
	 * @param fragment
	 *            BaseFragment实例
	 * @return BaseFragment实例
	 */
	public final BaseFragment addFragment(int resId, BaseFragment fragment) {
		return addFragment(resId, fragment, fragment.getClass().getName());
	}

	/**
	 * 添加默认资源ID的BaseFragment
	 * 
	 * @param fragment
	 *            BaseFragment实例
	 * @param tag
	 *            BaseFragment标签
	 * @return BaseFragment实例
	 */
	public final BaseFragment addFragment(BaseFragment fragment, String tag) {
		return addFragment(mResId, fragment, tag);
	}

	/**
	 * 添加指定资源ID的BaseFragment
	 * 
	 * @param resId
	 *            资源ID
	 * @param fragment
	 *            BaseFragment实例
	 * @param tag
	 *            BaseFragment标签
	 * @return BaseFragment实例
	 */
	public final BaseFragment addFragment(int resId, BaseFragment fragment,
			String tag) {
		FragmentTransaction ft = mFManager.beginTransaction();
		ft.add(resId, fragment, resId + "@" + tag);
		ft.commitAllowingStateLoss();
		return fragment;
	}

	/**
	 * 移除默认资源ID的BaseFragment
	 * 
	 * @param fragment
	 *            BaseFragment实例
	 * @param tag
	 *            BaseFragment标签
	 * @return BaseFragment实例
	 */
	public final BaseFragment removeFragment(BaseFragment fragment) {
		return removeFragment(mResId, fragment);
	}

	/**
	 * 移除指定资源ID的BaseFragment
	 * 
	 * @param resId
	 *            资源ID
	 * @param fragment
	 *            BaseFragment实例
	 * @return BaseFragment实例
	 */
	public final BaseFragment removeFragment(int resId, BaseFragment fragment) {
		FragmentTransaction ft = mFManager.beginTransaction();
		ft.remove(fragment);
		ft.commitAllowingStateLoss();
		return fragment;
	}
}
