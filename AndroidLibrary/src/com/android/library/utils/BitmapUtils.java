package com.android.library.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Base64;
/** 图片处理工具 */
public class BitmapUtils {

	private static BitmapUtils INSTANCE;

	public static BitmapUtils getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new BitmapUtils();
		}
		return INSTANCE;
	}

	/**
	 * 获得圆角图片
	 * 
	 * @author huxj
	 * @param bitmap
	 *            Bitmap资源
	 * @param roundPx
	 *            弧度
	 * @return Bitmap 圆角Bitmap资源
	 */
	public Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx) {
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		return output;
	}

	/**
	 * 保存图片
	 */
	public void saveMyBitmap(Bitmap bmp, String dir, String path) {
		File file = new File(dir);
		if (!file.isDirectory()) {
			file.mkdirs();
		}
		File f = new File(path);
		try {
			f.createNewFile();
			FileOutputStream fOut = null;
			fOut = new FileOutputStream(f);
			bmp.compress(Bitmap.CompressFormat.PNG, 100, fOut);
			fOut.flush();
			fOut.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** 根据路径获取图片 */
	public Bitmap getBitmap(String path, int mScreenWidth, int mScreenHeight) {
		if (path != null) {
			BitmapFactory.Options options = null;
			InputStream is = null;
			try {
				options = new BitmapFactory.Options();
				options.inJustDecodeBounds = true;
				is = new FileInputStream(path);
				BitmapFactory.decodeStream(is, null, options);
				int w = options.outWidth;
				int h = options.outHeight;
				options = new BitmapFactory.Options();
				options.inSampleSize = Math.max((int) (w / mScreenWidth), h
						/ mScreenHeight);
				options.inJustDecodeBounds = false;
				is.close();
				is = new FileInputStream(path);
				return BitmapFactory.decodeStream(is, null, options);
			} catch (Throwable e) {
				e.printStackTrace();
			} finally {
				try {
					if (is != null)
						is.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	/** 根据路径获取图片 */
	public Bitmap getBitmapBySize(String path, int maxWidth, int maxHeight) {
		if (path != null) {
			BitmapFactory.Options options = null;
			InputStream is = null;
			try {
				options = new BitmapFactory.Options();
				options.inJustDecodeBounds = true;
				is = new FileInputStream(path);
				BitmapFactory.decodeStream(is, null, options);
				int w = options.outWidth;
				int h = options.outHeight;
				options = new BitmapFactory.Options();
				options.inSampleSize = Math.max((int) (w / maxWidth), h
						/ maxHeight);
				options.inJustDecodeBounds = false;
				is.close();
				is = new FileInputStream(path);
				return BitmapFactory.decodeStream(is, null, options);
			} catch (Throwable e) {
				e.printStackTrace();
			} finally {
				try {
					if (is != null)
						is.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	public Bitmap getBitmapOrg(String path) {
		if (path != null) {
			InputStream is = null;
			Bitmap bm = null;
			BitmapFactory.Options options = null;
			try {
				options = new BitmapFactory.Options();
				options.inTempStorage = new byte[1024 * 1024 * 8]; // 5MB的临时存储空间
				options.inJustDecodeBounds = false;
				options.inPreferredConfig = Bitmap.Config.ARGB_4444;
				options.inPurgeable = true;
				options.inInputShareable = true;
				is = new FileInputStream(path);
				bm = BitmapFactory.decodeStream(is, null, options);

				return bm;
			} catch (Throwable e) {
				e.printStackTrace();
			} finally {
				try {
					if (is != null)
						is.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	/** 根据路径获取图片，图片大小缩小两倍 */
	public Bitmap getBitmapHalf(String path) {
		if (path != null) {
			try {
				BitmapFactory.Options options = new BitmapFactory.Options();
				InputStream is = new FileInputStream(path);
				options = new BitmapFactory.Options();
				options.inSampleSize = 2;// 缩小2倍
				options.inJustDecodeBounds = false;
				is.close();
				is = new FileInputStream(path);

				return BitmapFactory.decodeStream(is, null, options);
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/** 根据路径获取图片，图片大小缩小4倍 */
	public Bitmap getBitmapFour(String path) {
		if (path != null) {
			try {
				BitmapFactory.Options options = new BitmapFactory.Options();
				InputStream is = new FileInputStream(path);
				options = new BitmapFactory.Options();
				options.inSampleSize = 4;// 缩小4倍
				options.inJustDecodeBounds = false;
				is.close();
				is = new FileInputStream(path);

				return BitmapFactory.decodeStream(is, null, options);
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public Bitmap getFitSizeImg(String path) {
		if (path == null || path.length() < 1)
			return null;
		try {
			File file = new File(path);
			Bitmap resizeBmp = null;
			BitmapFactory.Options opts = new BitmapFactory.Options();
			// 数字越大读出的图片占用的heap越小 不然总是溢出
			if (file.length() < 20480) { // 0-20k
				opts.inSampleSize = 1;
			} else if (file.length() < 51200) { // 20-50k
				opts.inSampleSize = 2;
			} else if (file.length() < 307200) { // 50-300k
				opts.inSampleSize = 4;
			} else if (file.length() < 819200) { // 300-800k
				opts.inSampleSize = 6;
			} else if (file.length() < 1048576) { // 800-1024k
				opts.inSampleSize = 8;
			} else {
				opts.inSampleSize = 10;
			}
			resizeBmp = BitmapFactory.decodeFile(file.getPath(), opts);
			return resizeBmp;
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public Bitmap getBitmapForResource(Context context, int resourceId,
			int mScreenWidth, int mScreenHeight) {
		try {
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeResource(context.getResources(), resourceId,
					options);
			int w = options.outWidth;
			int h = options.outHeight;
			options = new BitmapFactory.Options();
			// options.inSampleSize = 5;
			options.inSampleSize = Math.max((int) (w / mScreenWidth), h
					/ mScreenHeight);
			options.inJustDecodeBounds = false;
			return BitmapFactory.decodeResource(context.getResources(),
					resourceId, options);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}

	public Bitmap stringtoBitmap(String string) {
		// 将字符串转换成Bitmap类型
		Bitmap bitmap = null;
		try {
			byte[] bitmapArray;
			bitmapArray = Base64.decode(string, Base64.DEFAULT);
			bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0,
					bitmapArray.length);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return bitmap;
	}

	/**
	 * 处理图片
	 * 
	 * @param bm
	 *            所要转换的bitmap
	 * @param newWidth新的宽
	 * @param newHeight新的高
	 * @return 指定宽高的bitmap
	 */
	public Bitmap zoomImg(Bitmap bm, int newWidth, int newHeight) {
		// 获得图片的宽高
		int width = bm.getWidth();
		int height = bm.getHeight();
		// 计算缩放比例
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		// 取得想要缩放的matrix参数
		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		// 得到新的图片
		Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix,
				true);
		return newbm;
	}

	/**
	 * 处理图片
	 * 
	 * @param bm
	 *            所要转换的bitmap ，按宽度保持比例
	 * @param newWidth新的宽
	 * @return 指定宽的bitmap
	 */
	public Bitmap zoomImg(Bitmap bm, int newWidth) {
		// 获得图片的宽高
		int width = bm.getWidth();
		int height = bm.getHeight();
		// 计算缩放比例
		float scaleWidth = ((float) newWidth) / width;
		// 取得想要缩放的matrix参数
		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleWidth);
		// 得到新的图片
		Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix,
				true);
		return newbm;
	}
}
