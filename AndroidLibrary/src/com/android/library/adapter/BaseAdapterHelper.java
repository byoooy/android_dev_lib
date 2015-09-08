package com.android.library.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.util.Linkify;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * Allows an abstraction of the ViewHolder pattern.<br>
 * <br>
 * <p/>
 * <b>Usage</b>
 * <p/>
 * 
 * <pre>
 * return BaseAdapterHelper.get(context, convertView, parent, R.layout.item)
 * 		.setText(R.id.tvName, contact.getName())
 * 		.setText(R.id.tvEmails, contact.getEmails().toString())
 * 		.setText(R.id.tvNumbers, contact.getNumbers().toString()).getView();
 * </pre>
 */
public class BaseAdapterHelper {
	/** Views indexed with their IDs */
	private final SparseArray<View> views;

	private final Context context;

	private int position;

	private View convertView;

	/**
	 * Package private field to retain the associated user object and detect a
	 * change
	 */
	Object associatedObject;

	protected BaseAdapterHelper(Context context, ViewGroup parent,
			int layoutId, int position) {
		this.context = context;
		this.position = position;
		this.views = new SparseArray<View>();
		convertView = LayoutInflater.from(context).inflate(layoutId, parent,
				false);
		convertView.setTag(this);
	}

	/**
	 * This method is the only entry point to get a BaseAdapterHelper.
	 * 
	 * @param context
	 *            The current context.
	 * @param convertView
	 *            The convertView arg passed to the getView() method.
	 * @param parent
	 *            The parent arg passed to the getView() method.
	 * @return A BaseAdapterHelper instance.
	 */
	public static BaseAdapterHelper get(Context context, View convertView,
			ViewGroup parent, int layoutId) {
		return get(context, convertView, parent, layoutId, -1);
	}

	/** This method is package private and should only be used by QuickAdapter. */
	static BaseAdapterHelper get(Context context, View convertView,
			ViewGroup parent, int layoutId, int position) {
		if (convertView == null) {
			return new BaseAdapterHelper(context, parent, layoutId, position);
		}

		// Retrieve the existing helper and update its position
		BaseAdapterHelper existingHelper = (BaseAdapterHelper) convertView
				.getTag();
		existingHelper.position = position;
		return existingHelper;
	}

	/** 通过viewId获取对应的控件对象 */
	public <T extends View> T getView(int viewId) {
		return retrieveView(viewId);
	}

	/** 对id为viewId的TextView设置要显示的文字，文字的内容为value */
	public BaseAdapterHelper setText(int viewId, String value) {
		TextView view = retrieveView(viewId);
		view.setText(value);
		return this;
	}

	/** 对id为viewId的TextView设置要显示的文字，文字对应的资源id为stringResId */
	public BaseAdapterHelper setText(int viewId, int stringResId) {
		TextView view = retrieveView(viewId);
		view.setText(stringResId);
		return this;
	}

	/** 对id为viewId的TextView设置文字颜色，颜色值为textColor */
	public BaseAdapterHelper setTextColor(int viewId, int textColor) {
		TextView view = retrieveView(viewId);
		view.setTextColor(textColor);
		return this;
	}

	/** 对id为viewId的TextView设置文字颜色，颜色资源id为textColorResId */
	public BaseAdapterHelper setTextColorRes(int viewId, int textColorResId) {
		TextView view = retrieveView(viewId);
		view.setTextColor(context.getResources().getColor(textColorResId));
		return this;
	}

	/** 对id为viewId的ImageView设置要显示的图片，图片的资源id为imageResId */
	public BaseAdapterHelper setImageResource(int viewId, int imageResId) {
		ImageView view = retrieveView(viewId);
		view.setImageResource(imageResId);
		return this;
	}
	/** 对id为viewId的ImageView设置要显示的图片，图片为drawable */
	public BaseAdapterHelper setImageDrawable(int viewId, Drawable drawable) {
		ImageView view = retrieveView(viewId);
		view.setImageDrawable(drawable);
		return this;
	}
	/** 对id为viewId的ImageView设置要显示的图片，图片链接为imageUrl<br>
	 * 	在此可以使用第三方图片加载框架，实现要显示图片的加载<br>
	 *  加载的图片包括 http、file、assert、resource等这些图片
	 *  */
	public BaseAdapterHelper setImageUrl(int viewId, String imageUrl) {
		ImageView view = retrieveView(viewId);
		// Picasso.with(context).load(imageUrl).into(view);
		return this;
	}
	/** 对id为viewId的ImageView设置要显示的图片，图片为bitmap */
	public BaseAdapterHelper setImageBitmap(int viewId, Bitmap bitmap) {
		ImageView view = retrieveView(viewId);
		view.setImageBitmap(bitmap);
		return this;
	}
	
	/** 为id为viewId的View设置背景色，背景色的颜色id为color */
	public BaseAdapterHelper setBackgroundColor(int viewId, int color) {
		View view = retrieveView(viewId);
		view.setBackgroundColor(color);
		return this;
	}

	/** 对id为viewId的View设置背景色，背景色的资源id为backgroundResId */
	public BaseAdapterHelper setBackgroundRes(int viewId, int backgroundResId) {
		View view = retrieveView(viewId);
		view.setBackgroundResource(backgroundResId);
		return this;
	}

	/** 对id为viewId的View设置透明度,透明度的值0-1 */
	@SuppressLint("NewApi")
	public BaseAdapterHelper setAlpha(int viewId, float value) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			retrieveView(viewId).setAlpha(value);
		} else {
			// Pre-honeycomb hack to set Alpha value
			AlphaAnimation alpha = new AlphaAnimation(value, value);
			alpha.setDuration(0);
			alpha.setFillAfter(true);
			retrieveView(viewId).startAnimation(alpha);
		}
		return this;
	}

	/** 对id为viewId的View设置是否可见*/
	public BaseAdapterHelper setVisible(int viewId, boolean visible) {
		View view = retrieveView(viewId);
		view.setVisibility(visible ? View.VISIBLE : View.GONE);
		return this;
	}

	/** 对id为viewId的TextView设置添加超链接 */
	public BaseAdapterHelper linkify(int viewId) {
		TextView view = retrieveView(viewId);
		Linkify.addLinks(view, Linkify.ALL);
		return this;
	}

	/** 对id为viewId的TextView设置字体样式 */
	public BaseAdapterHelper setTypeface(int viewId, Typeface typeface) {
		TextView view = retrieveView(viewId);
		view.setTypeface(typeface);
		view.setPaintFlags(view.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
		return this;
	}

	/** 对id为viewIds的TextView组设置字体样式 */
	public BaseAdapterHelper setTypeface(Typeface typeface, int... viewIds) {
		for (int viewId : viewIds) {
			setTypeface(viewId, typeface);
		}
		return this;
	}

	/** 为id为viewId的ProgressBar，设置当前进度 */
	public BaseAdapterHelper setProgress(int viewId, int progress) {
		ProgressBar view = retrieveView(viewId);
		view.setProgress(progress);
		return this;
	}

	/** 为id为viewId的ProgressBar，设置进度范围和当前进度 */
	public BaseAdapterHelper setProgress(int viewId, int progress, int max) {
		ProgressBar view = retrieveView(viewId);
		view.setMax(max);
		view.setProgress(progress);
		return this;
	}

	/** 为id为viewId的ProgressBar，设置进度范围 */
	public BaseAdapterHelper setMax(int viewId, int max) {
		ProgressBar view = retrieveView(viewId);
		view.setMax(max);
		return this;
	}

	/** 为id为viewId的RatingBar，设置当前评分 */
	public BaseAdapterHelper setRating(int viewId, float rating) {
		RatingBar view = retrieveView(viewId);
		view.setRating(rating);
		return this;
	}

	/** 为id为viewId的RatingBar，设置当前评分和范围*/
	public BaseAdapterHelper setRating(int viewId, float rating, int max) {
		RatingBar view = retrieveView(viewId);
		view.setMax(max);
		view.setRating(rating);
		return this;
	}

	/** 为id为viewId的View,添加点击监听事件*/
	public BaseAdapterHelper setOnClickListener(int viewId,
			View.OnClickListener listener) {
		View view = retrieveView(viewId);
		view.setOnClickListener(listener);
		return this;
	}

	/** 为id为viewId的View,添加触摸监听事件*/
	public BaseAdapterHelper setOnTouchListener(int viewId,
			View.OnTouchListener listener) {
		View view = retrieveView(viewId);
		view.setOnTouchListener(listener);
		return this;
	}

	/** 为id为viewId的View,添加长按监听事件*/
	public BaseAdapterHelper setOnLongClickListener(int viewId,
			View.OnLongClickListener listener) {
		View view = retrieveView(viewId);
		view.setOnLongClickListener(listener);
		return this;
	}

	/** 对listview or gridview的项添加点击事件 */
	public BaseAdapterHelper setOnItemClickListener(int viewId,
			AdapterView.OnItemClickListener listener) {
		AdapterView view = retrieveView(viewId);
		view.setOnItemClickListener(listener);
		return this;
	}

	/** 对listview or gridview的项添加长按事件 */
	public BaseAdapterHelper setOnItemLongClickListener(int viewId,
			AdapterView.OnItemLongClickListener listener) {
		AdapterView view = retrieveView(viewId);
		view.setOnItemLongClickListener(listener);
		return this;
	}

	/** 对listview or gridview的子项添加选中事件 */
	public BaseAdapterHelper setOnItemSelectedClickListener(int viewId,
			AdapterView.OnItemSelectedListener listener) {
		AdapterView view = retrieveView(viewId);
		view.setOnItemSelectedListener(listener);
		return this;
	}

	/** 为id为viewId的View，设置tag */
	public BaseAdapterHelper setTag(int viewId, Object tag) {
		View view = retrieveView(viewId);
		view.setTag(tag);
		return this;
	}

	/** 为id为viewId的View，设置tag */
	public BaseAdapterHelper setTag(int viewId, int key, Object tag) {
		View view = retrieveView(viewId);
		view.setTag(key, tag);
		return this;
	}

	/** 为id为viewId的Checkable，设置是否选中 */
	public BaseAdapterHelper setChecked(int viewId, boolean checked) {
		Checkable view = (Checkable) retrieveView(viewId);
		view.setChecked(checked);
		return this;
	}

	/** 为id为viewId的AdapterView设置Adapter */
	public BaseAdapterHelper setAdapter(int viewId, Adapter adapter) {
		AdapterView view = retrieveView(viewId);
		view.setAdapter(adapter);
		return this;
	}

	/** 获取当前显示的convertView */
	public View getView() {
		return convertView;
	}

	/** 获取当前显示的convertView的Position */
	public int getPosition() {
		if (position == -1)
			throw new IllegalStateException(
					"Use BaseAdapterHelper constructor "
							+ "with position if you need to retrieve the position.");
		return position;
	}
	/** 通过viewId获取View */
	@SuppressWarnings("unchecked")
	protected <T extends View> T retrieveView(int viewId) {
		View view = views.get(viewId);
		if (view == null) {
			view = convertView.findViewById(viewId);
			views.put(viewId, view);
		}
		return (T) view;
	}

	/** Retrieves the last converted object on this view. */
	public Object getAssociatedObject() {
		return associatedObject;
	}

	/** Should be called during convert */
	public void setAssociatedObject(Object associatedObject) {
		this.associatedObject = associatedObject;
	}
}
