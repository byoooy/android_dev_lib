package com.android.library.animation;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

/** 动画工具类 */
public class AnimationHelper {

	private static AnimationHelper HELPER;

	private AnimationHelper() {

	}

	public static AnimationHelper getInstance() {
		if (HELPER == null) {
			HELPER = new AnimationHelper();
		}
		return HELPER;
	}

	/**
	 * 播放Activity切换时的切换动画效果<br>
	 * 使用场景：紧挨着startActivity()或者finish()函数之后调用
	 * 
	 * */
	public void playActivityChangeAnimation(Activity context, int enterAnim,
			int exitAnim) {
		context.overridePendingTransition(enterAnim, exitAnim);
	}

	/** 检测渐变数值的有效性 */
	private float checkAlphaValue(float value) {
		value = Math.min(value, 1.0f);
		value = Math.max(value, 0.0f);
		return value;
	}

	/**
	 * 创建渐变动画
	 * 
	 * <?xml version="1.0" encoding="utf-8"?> <set
	 * xmlns:android="http://schemas.android.com/apk/res/android" >
	 * 
	 * <alpha android:duration="1000" android:fromAlpha="0.1"
	 * android:toAlpha="1.0" > </alpha>
	 * 
	 * </set>
	 * 
	 * */
	public AlphaAnimation buildAlphaAnimation(float fromAlpha, float toAlpha,
			int duration) {
		fromAlpha = checkAlphaValue(fromAlpha);
		toAlpha = checkAlphaValue(toAlpha);
		AlphaAnimation animation = new AlphaAnimation(fromAlpha, toAlpha);
		animation.setDuration(duration);
		return animation;
	}

	/**
	 * 创建缩放动画
	 * 
	 * <?xml version="1.0" encoding="utf-8"?> <set
	 * xmlns:android="http://schemas.android.com/apk/res/android" >
	 * 
	 * <scale android:duration="2000" android:fillAfter="false"
	 * android:fromXScale="0.0" android:fromYScale="0.0"
	 * android:interpolator="@android:anim/accelerate_decelerate_interpolator"
	 * android:pivotX="50%" android:pivotY="50%" android:toXScale="1.0"
	 * android:toYScale="1.0" />
	 * 
	 * </set>
	 * 
	 * */
	public ScaleAnimation buildScaleAnimation(float fromX, float toX,
			float fromY, float toY, float pivotX, float pivotY, int duration) {
		ScaleAnimation animation = new ScaleAnimation(fromX, toX, fromY, toY,
				pivotX, pivotY);
		animation.setDuration(duration);
		return animation;
	}

	/**
	 * 创建旋转动画
	 * 
	 * <?xml version="1.0" encoding="utf-8"?> <set
	 * xmlns:android="http://schemas.android.com/apk/res/android" >
	 * 
	 * <rotate android:duration="1000" android:fromDegrees="0"
	 * android:interpolator="@android:anim/accelerate_decelerate_interpolator"
	 * android:pivotX="50%" android:pivotY="50%" android:toDegrees="+360" />
	 * 
	 * </set>
	 * 
	 * 
	 * */
	public RotateAnimation buildRotateAnimation(float fromDegrees,
			float toDegrees, int pivotXType, float pivotXValue, int pivotYType,
			float pivotYValue, int duration) {
		RotateAnimation animation = new RotateAnimation(fromDegrees, toDegrees,
				pivotXType, pivotXValue, pivotYType, pivotYValue);
		animation.setDuration(duration);
		animation.setInterpolator(new AccelerateDecelerateInterpolator());
		return animation;
	}

	/**
	 * 创建位移动画
	 * 
	 * <?xml version="1.0" encoding="utf-8"?> <set
	 * xmlns:android="http://schemas.android.com/apk/res/android" >
	 * 
	 * <translate android:duration="1000" android:fromXDelta="10"
	 * android:fromYDelta="10" android:toXDelta="100" android:toYDelta="100" />
	 * 
	 * </set>
	 * */
	public TranslateAnimation buildTraslateAnimatiom(int fromXType,
			float fromXValue, int toXType, float toXValue, int fromYType,
			float fromYValue, int toYType, float toYValue, int duration) {
		TranslateAnimation animation = new TranslateAnimation(fromXType,
				fromXValue, toXType, toXValue, fromYType, fromYValue, toYType,
				toYValue);
		animation.setDuration(duration);
		return animation;
	}

	/** 播放动画组 */
	public void playAnimation(View view, AnimationSet animationSet) {
		if (view == null) {
			return;
		}
		if (animationSet != null) {
			view.startAnimation(animationSet);
		}
	}

	/** 播放动画 */
	public void playAnimation(Context context, View view, int animationResId) {
		if (context == null) {
			return;
		}
		if (view == null) {
			return;
		}
		Animation animation = AnimationUtils.loadAnimation(context,
				animationResId);
		if (animation != null)
			view.startAnimation(animation);
	}

	/** 播放动画 */
	public void playAnimation(View view, Animation animation) {
		if (view == null) {
			return;
		}
		if (animation != null)
			view.startAnimation(animation);
	}

	/**
	 * 播放帧动画
	 * 
	 * <?xml version="1.0" encoding="utf-8"?> <animation-list
	 * xmlns:android="http://schemas.android.com/apk/res/android" >
	 * 
	 * <item android:drawable="@drawable/one" android:duration="500"/> <item
	 * android:drawable="@drawable/two" android:duration="500"/> <item
	 * android:drawable="@drawable/three" android:duration="500"/> <item
	 * android:drawable="@drawable/four" android:duration="500"/> <item
	 * android:drawable="@drawable/five" android:duration="500"/> <item
	 * android:drawable="@drawable/six" android:duration="500"/>
	 * 
	 * </animation-list>
	 * 
	 * */
	public void playFrameAnimation(ImageView view, int resId) {
		if (view == null) {
			return;
		}
		view.setImageResource(resId);
	}

	/** 播放渐变动画 */
	public void playAlphaAnimation(View view, float fromAlpha, float toAlpha,
			int duration) {
		AlphaAnimation animation = buildAlphaAnimation(fromAlpha, toAlpha,
				duration);
		playAnimation(view, animation);
	}

	/** 播放缩放动画 */
	public void playScaleAnimation(View view, float fromX, float toX,
			float fromY, float toY, float pivotX, float pivotY, int duration) {
		ScaleAnimation animation = buildScaleAnimation(fromX, toX, fromY, toY,
				pivotX, pivotY, duration);
		playAnimation(view, animation);
	}

	/** 播放旋转动画 */
	public void playRotateAnimation(View view, float fromDegrees,
			float toDegrees, int pivotXType, float pivotXValue, int pivotYType,
			float pivotYValue, int duration) {
		RotateAnimation animation = this.buildRotateAnimation(fromDegrees,
				toDegrees, pivotXType, pivotXValue, pivotYType, pivotYValue,
				duration);
		playAnimation(view, animation);
	}

	/** 播放位移动画 */
	public void playTraslateAnimation(View view, int fromXType,
			float fromXValue, int toXType, float toXValue, int fromYType,
			float fromYValue, int toYType, float toYValue, int duration) {
		TranslateAnimation animation = this.buildTraslateAnimatiom(fromXType,
				fromXValue, toXType, toXValue, fromYType, fromYValue, toYType,
				toYValue, duration);
		playAnimation(view, animation);
	}

	/**************** 属性动画 *******************/
}
