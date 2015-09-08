package com.android.library.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.FrameLayout;

public abstract class BaseFragment extends Fragment {
	/** 调用结果:正常 */
	public static final int RESULT_OK = -1;
	/** 调用结果:取消 */
	public static final int RESULT_CANCELED = 0;

	/** Views实例工具 */
	private LayoutInflater mInflater;
	/** 根View */
	private ViewGroup mRootView;
	/***/
	private ViewGroup mDecorView;
	/** 主View */
	private View mContentView;

	/** 请求编码 */
	private int mRequestCode = RESULT_CANCELED;
	/** 结果编码 */
	private int mResultCode = RESULT_CANCELED;
	/** 结果返回数据 */
	private Intent mResultData;

	/** 子Fragment管理实例 */
	protected FragmentManager mChildFManager;
	/** Fragment堆栈 */
	private HashMap<Integer, ArrayList<String>> mStackFragments;
	/** 当前展示Fragments */
	private HashMap<Integer, String> mCurrentFragments;
	/** Fragment附属资源ID */
	private int mResId = -1;

	@Override
	public final void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle bundle = getArguments();
		if (bundle != null) {
			mRequestCode = bundle.getInt("BaseFragment_requestCode",
					RESULT_CANCELED);
		}
	}

	@Override
	public final View onCreateView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState) {
		mInflater = inflater;
		mRootView = container;
		mDecorView = new FrameLayout(getActivity());
		return mDecorView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@SuppressLint("UseSparseArrays")
	@SuppressWarnings("unchecked")
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		mChildFManager = getChildFragmentManager();
		if (savedInstanceState != null) {
			mStackFragments = (HashMap<Integer, ArrayList<String>>) savedInstanceState
					.get("BaseFragment_mStackFragments");
			mCurrentFragments = (HashMap<Integer, String>) savedInstanceState
					.get("BaseFragment_mCurrentFragments");
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
	 * {@linkplain Fragment#onCreate(Bundle) <span
	 * style="color:#0000FF">onCreate(Bundle)</span>}扩展
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
	 * 初始化配置方法，在{@linkplain BaseFragment#initViews() <span
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
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putSerializable("BaseFragment_mStackFragments",
				mStackFragments);
		outState.putSerializable("BaseFragment_mCurrentFragments",
				mCurrentFragments);
	}

	/**
	 * 获取Fragment放置的根视图
	 * 
	 * @return Fragment根View
	 */
	public ViewGroup getRootView() {
		return mRootView;
	}

	/**
	 * 统一风格，同{@linkplain Activity#setContentView(int) <span
	 * style="color:#0000FF">Activity.setContentView(int)</span>}
	 * 
	 * @param layoutResID
	 *            主View资源ID
	 */
	public void setContentView(int layoutResID) {
		mContentView = mInflater.inflate(layoutResID, mDecorView, false);
		mDecorView.addView(mContentView);
	}

	public void setContentView(View view) {
		mContentView = view;
		mDecorView.addView(mContentView);
	}

	/**
	 * 获取Fragment展示内容
	 * 
	 * @return Fragment展示内容
	 */
	public View getContentView() {
		return mContentView;
	}

	/**
	 * 统一风格，同{@linkplain Activity#findViewById(int) <span
	 * style="color:#0000FF">Activity.findViewById(int)</span>}
	 * 
	 * @param id
	 *            view资源ID
	 * @return view实例
	 */
	public View findViewById(int id) {
		return mContentView.findViewById(id);
	}

	/**
	 * 统一风格，同{@linkplain Activity#startActivity(Intent) <span
	 * style="color:#0000FF">Activity.startActivity(Intent)</span>}
	 * 
	 * @param intent
	 *            传递数据
	 */
	public final void startFragment(Intent intent) {
		try {
			String className = intent.getComponent().getClassName();
			if (getActivity() instanceof BaseFragmentActivity) {
				BaseFragmentActivity activity = (BaseFragmentActivity) getActivity();
				BaseFragment fragment = (BaseFragment) Class.forName(className)
						.newInstance();
				fragment.setArguments(intent.getExtras());
				activity.putFragment(fragment, fragment.getClass().getName());
			} else {
				throw new IllegalArgumentException(
						"Intent ComponentName must be a BaseFragmentActivity Class ");
			}
		} catch (Exception e) {
		}
	}

	/**
	 * 统一风格，同{@linkplain Activity#startActivityForResult(Intent, int) <span
	 * style="color:#0000FF">Activity.startActivityForResult(Intent,
	 * int)</span>}
	 * 
	 * @param intent
	 *            传递数据
	 * @param requestCode
	 *            请求编码
	 */
	public final void startFragmentForResult(Intent intent, int requestCode) {
		String code = intent.getStringExtra("BaseFragment_requestCode");
		if (code != null) {
			throw new IllegalArgumentException(
					"Intent params can't named BaseFragment_requestCode");
		}
		intent.putExtra("BaseFragment_requestCode", requestCode);
		startFragment(intent);
	}

	/**
	 * 统一风格，同<span
	 * style="color:#0000FF">Activity.onActivityResult(int,int,Bundle)</span>
	 * 
	 * @param requestCode
	 *            请求编码
	 * @param resultCode
	 *            结果编码
	 * @param data
	 *            返回数据
	 */
	public void onFragmentResult(int requestCode, int resultCode, Intent data) {
	}

	/**
	 * 统一风格，同{@linkplain Activity#setResult(int) <span
	 * style="color:#0000FF">Activity.setResult(int)</span>}
	 * 
	 * @param resultCode
	 *            结果编码
	 */
	public final void setResult(int resultCode) {
		mResultCode = resultCode;
	}

	/**
	 * 统一风格，同{@linkplain Activity#setResult(int, Intent) <span
	 * style="color:#0000FF">Activity.setResult(int, Intent)</span>}
	 * 
	 * @param resultCode
	 *            结果编码
	 * @param data
	 *            返回数据
	 */
	public final void setResult(int resultCode, Intent data) {
		mResultCode = resultCode;
		mResultData = data;
	}

	/**
	 * 统一风格，同{@linkplain Activity#finish() <span
	 * style="color:#0000FF">Activity.finish()</span>}
	 */
	public final void finish() {
		BaseFragment fragment = null;

		BaseFragment parentFragment = (BaseFragment) getParentFragment();
		if (parentFragment != null) {
			fragment = parentFragment.popFragment();
			if (fragment != null) {
				fragment.onFragmentResult(mRequestCode, mResultCode,
						mResultData);
			}
		} else {
			BaseFragmentActivity activity = (BaseFragmentActivity) getActivity();
			fragment = activity.popFragment();
			if (fragment != null) {
				fragment.onFragmentResult(mRequestCode, mResultCode,
						mResultData);
			}
		}
	}

	/**
	 * 统一风格，同{@linkplain Activity#onBackPressed() <span
	 * style="color:#0000FF">Activity.onBackPressed()</span>}
	 */
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
	public void onDestroy() {
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
		return (BaseFragment) mChildFManager.findFragmentById(resId);
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
		return (BaseFragment) mChildFManager.findFragmentByTag(resId + "@"
				+ tag);
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
	 * 过期的动画配置
	 * 
	 * @param transit
	 *            回转
	 * @param enter
	 *            true 进入
	 * @param nextAnim
	 *            下个动画
	 * @return 动画
	 */
	@Deprecated
	@Override
	public final Animation onCreateAnimation(int transit, boolean enter,
			int nextAnim) {
		return super.onCreateAnimation(transit, enter, nextAnim);
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
		FragmentTransaction ft = mChildFManager.beginTransaction();

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
		FragmentTransaction ft = mChildFManager.beginTransaction();
		BaseFragment exitFragment = null;
		String exitTag = mCurrentFragments.get(resId);
		if (exitTag != null) {
			exitFragment = findFragmentByTag(resId, exitTag);
		}
		onCreateAnimation(resId, fragment, exitFragment, ft, false);
		mCurrentFragments.put(resId, tag);

		if (mChildFManager.getFragments() != null) {
			for (Fragment f : mChildFManager.getFragments()) {
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

		FragmentTransaction ft = mChildFManager.beginTransaction();
		BaseFragment exitFragment = null;
		String exitTag = mCurrentFragments.get(resId);
		if (exitTag != null) {
			exitFragment = findFragmentByTag(resId, exitTag);
		}
		onCreateAnimation(resId, fragment, exitFragment, ft, false);
		mCurrentFragments.put(resId, tag);

		if (mChildFManager.getFragments() != null) {
			for (Fragment f : mChildFManager.getFragments()) {
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

			FragmentTransaction ft = mChildFManager.beginTransaction();
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
	public final BaseFragment removeFragment(BaseFragment fragment) {
		return removeFragment(mResId, fragment, fragment.getClass().getName());
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
		return removeFragment(resId, fragment, fragment.getClass().getName());
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
	public final BaseFragment removeFragment(BaseFragment fragment, String tag) {
		return removeFragment(mResId, fragment, tag);
	}

	/**
	 * 移除指定资源ID的BaseFragment
	 * 
	 * @param resId
	 *            资源ID
	 * @param fragment
	 *            BaseFragment实例
	 * @param tag
	 *            BaseFragment标签
	 * @return BaseFragment实例
	 */
	public final BaseFragment removeFragment(int resId, BaseFragment fragment,
			String tag) {
		FragmentTransaction ft = mChildFManager.beginTransaction();
		ft.remove(fragment);
		ft.commitAllowingStateLoss();
		return fragment;
	}
}
