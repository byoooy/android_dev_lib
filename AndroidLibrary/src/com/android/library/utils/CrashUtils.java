package com.android.library.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Locale;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

/**
 * 错误收集工具，用于收集运行时错误日志。
 * <p>
 * 可配置邮箱地址，远程收集错误日志。
 * 
 * @author kycq
 * 
 */
public class CrashUtils implements UncaughtExceptionHandler {
	private static final String TAG = CrashUtils.class.getSimpleName();

	/** 日志邮件主题 */
	private StringBuilder mEmailSubject = new StringBuilder();
	/** 日志邮件内容 */
	private StringBuilder mEmailContent = new StringBuilder();
	/** 分割线 */
	private static final byte[] Separator = ("\r\n--------------------\r\n")
			.getBytes();
	/** 换行符号 */
	private static final byte[] NEWLINE = ("\r\n").getBytes();
	/** 日志文件保存目录 */
	private static final String LOG_DIR = "LOG/";
	/** 日志文件记录 */
	private static final String LOG_SHARED = "LOG";
	/** 是否发送日志文件 */
	private boolean isSendLog = true;
	/** 发送日志邮件后自动删除 */
	private boolean isAutoDel = false;
	/** 未捕获异常收集处理程序CrashHandler实例 */
	private static CrashUtils INSTANCE;
	/** 上下文环境 */
	private Context mContext;
	/** 系统默认未捕获异常收集处理程序 */
	private UncaughtExceptionHandler mDefaultHandler;
	/** 发送日志邮件邮箱地址 */
	private String mFromEmail;
	/** 发送日志邮件邮箱密码 */
	private String mFromEmailPwd;
	/** 发送邮件服务器 */
	private String mHost;
	/** 发送邮件服务器端口 */
	private String mPort;
	/** 接收日志邮件邮箱地址 */
	private String mToEmail;
	/** 是否拦截显示错误信息 */
	private boolean isShowDebug = false;

	private CrashUtils() {
	}

	/**
	 * 获取异常收集处理程序CrashHandler实例
	 * 
	 * @return 异常收集工具实例
	 */
	public static CrashUtils getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new CrashUtils();
		}
		return INSTANCE;
	}

	/**
	 * 初始化设置
	 * 
	 * @param context
	 *            设备上下文环境
	 */
	public void init(Context context) {
		this.mContext = context;
		mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
		// 设置自定义异常收集处理程序
		Thread.setDefaultUncaughtExceptionHandler(this);
		sendDebugLog();
	}

	/**
	 * 初始化设置
	 * 
	 * @param context
	 *            设备上下文环境
	 * @param isShowDebug
	 *            是否显示抛出错误信息
	 */
	public void init(Context context, boolean isShowDebug) {
		init(context);
		this.isShowDebug = isShowDebug;
	}

	/**
	 * 是否发送日志文件，默认发送
	 * 
	 * @return true 是
	 *         <p>
	 *         false 否
	 */
	public boolean isSendLog() {
		return isSendLog;
	}

	/**
	 * 设置是否发送日志文件，默认发送
	 * 
	 * @param isSendLog
	 *            true 是 false 否
	 */
	public void setSendLog(boolean isSendLog) {
		this.isSendLog = isSendLog;
	}

	/**
	 * 是否自动删除日志文件，默认不删除
	 * 
	 * @return true 是
	 *         <p>
	 *         false 否
	 */
	public boolean isAutoDel() {
		return isAutoDel;
	}

	/**
	 * 设置是否自动删除日志文件，默认不删除
	 * 
	 * @param isAutoDel
	 *            true 是 false 否
	 */
	public void setAutoDel(boolean isAutoDel) {
		this.isAutoDel = isAutoDel;
	}

	/**
	 * 获取发送日志邮件邮箱地址
	 * 
	 * @return 发送邮件邮箱地址
	 */
	public String getFromEmail() {
		return mFromEmail;
	}

	/**
	 * 设置发送日志邮件邮箱地址
	 * 
	 * @param fromEmail
	 *            发送邮件邮箱地址
	 * @param fromEmailPwd
	 *            发送邮件邮箱密码
	 */
	public void setFromEmail(String fromEmail, String fromEmailPwd) {
		mFromEmail = fromEmail;
		mFromEmailPwd = fromEmailPwd;
	}

	/**
	 * 设置邮件服务器和端口号
	 * 
	 * @param host
	 *            服务器地址
	 * @param port
	 *            服务器端口号
	 */
	public void setHost(String host, String port) {
		mHost = host;
		mPort = port;
	}

	/**
	 * 获取接收日志邮件邮箱地址
	 * 
	 * @return 接收邮件邮箱地址
	 */
	public String getToEmail() {
		return mToEmail;
	}

	/**
	 * 设置接收日志邮件邮箱地址
	 * 
	 * @param toEmail
	 *            接收邮件邮箱地址
	 */
	public void setToEmail(String toEmail) {
		mToEmail = toEmail;
	}

	/**
	 * 未捕获异常处理
	 * 
	 * @param thread
	 *            错误信息位置线程
	 * @param ex
	 *            未捕获异常
	 */
	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		if (!handleException(ex) && mDefaultHandler != null) {// 未执行自定义异常处理
			mDefaultHandler.uncaughtException(thread, ex);
		} else {
			if (isShowDebug) {
				mDefaultHandler.uncaughtException(thread, ex);
			}

			new Thread() {
				@Override
				public void run() {
					// Toast 显示需要出现在一个线程的消息队列中
					Looper.prepare();
					Toast.makeText(mContext, "程序发生异常，即将退出", Toast.LENGTH_SHORT)
							.show();
					Looper.loop();
				}
			}.start();

			try {
				Thread.sleep(1000);
			} catch (Exception e) {
			}

			// 关闭应用
			android.os.Process.killProcess(android.os.Process.myPid());
			System.exit(10);
		}
	}

	/**
	 * 自定义异常处理方法
	 * 
	 * @param ex
	 *            未捕获异常
	 * @return true 提供自定义处理
	 */
	private boolean handleException(Throwable ex) {
		if (ex == null) {
			return true;
		}

		try {
			File file = getWriterFile();
			FileOutputStream writer = new FileOutputStream(file, true);

			if (!writeMobileInfo(writer)) {// 写入移动设备相关信息
				return false;
			}

			// 写入错误提示信息
			Writer errorStack = new StringWriter();
			PrintWriter printWriter = new PrintWriter(errorStack);
			ex.printStackTrace(printWriter);
			writer.write(errorStack.toString().getBytes());
			writer.write(Separator);

			// 日志邮件内容
			mEmailContent.append(errorStack.toString());

			if (writeLog(writer, file)) {// 写入日志
				if (isSendLog) {// 如果需要收集未捕获异常，发送异常日志邮件
					PreferenceUtils shared = new PreferenceUtils(mContext,
							LOG_SHARED);
					shared.put("filepath", file.getAbsolutePath());
					shared.put("emailSubject", mEmailSubject.toString());
					shared.put("emailContent", mEmailContent.toString());
				}
				return true;
			}
		} catch (Exception e) {
			Log.e(TAG, "handleException # 读取错误提示信息错误！", e);
		}

		return false;
	}

	/**
	 * 创建日志文件
	 * 
	 * @return 日志文件
	 */
	private File getWriterFile() {
		// 根据时间格式创建日志文件
		java.util.Date now = new java.util.Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss",
				Locale.CHINA);
		String sTime = format.format(now);

		format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
		mEmailSubject.append(format.format(now));

		try {
			// 创建日志目录
			File dir = new File(mContext.getExternalCacheDir(), LOG_DIR);
			dir.mkdirs();
			// 创建日志文件
			File file = new File(dir.getAbsolutePath() + "/" + sTime + ".log");
			if (file.exists()) {
				file.delete();
			}
			file.createNewFile();

			return file;
		} catch (IOException e) {
			Log.e(TAG, "getWriterFile() - 创建日志文件错误！", e);
		}
		return null;
	}

	/**
	 * 写入移动设备相关信息
	 * 
	 * @param writer
	 *            指定文件输出流
	 * @return true 移动设备信息写入成功
	 */
	private boolean writeMobileInfo(FileOutputStream writer) {
		try {
			// APP版本信息
			PackageManager pm = mContext.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(),
					PackageManager.GET_ACTIVITIES);
			if (pi != null) {
				writer.write(("VERSION_NAME:" + (pi.versionName == null ? "NULL"
						: pi.versionName)).getBytes());
				writer.write(NEWLINE);
				writer.write(("VERSION_CODE:" + pi.versionCode).getBytes());
				writer.write(NEWLINE);
			}

			// 使用反射来收集设备信息.在Build类中包含各种设备信息,
			// 例如: 系统版本号,设备生产商 等帮助调试程序的有用信息
			// 返回 Field 对象的一个数组，这些对象反映此 Class 对象所表示的类或接口所声明的所有字段
			Field[] fields = Build.class.getDeclaredFields();
			for (Field field : fields) {
				// setAccessible(boolean flag)
				// 将此对象的 accessible 标志设置为指示的布尔值。
				// 通过设置Accessible属性为true,才能对私有变量进行访问，不然会得到一个IllegalAccessException的异常
				field.setAccessible(true);
				writer.write((field.getName() + ": " + field.get(null))
						.getBytes());
				writer.write(NEWLINE);
			}
			writer.write(Separator);

			// 日志邮件主题
			mEmailSubject.append(" ");
			mEmailSubject.append(pi.applicationInfo.loadLabel(pm).toString());
			mEmailSubject.append("(").append(pi.packageName).append(")");
			mEmailSubject.append(" ");
			mEmailSubject.append(pi.versionName);

			return true;
		} catch (Exception e) {
			Log.e(TAG, "writeMobileInfo() - 读取手机信息错误！", e);
		}
		return false;
	}

	/**
	 * 写入日志
	 * 
	 * @param writer
	 *            指定文件输出流
	 * @param file
	 *            指定文件
	 * @return true 写入日志成功
	 */
	private boolean writeLog(FileOutputStream writer, File file) {
		// 获取APP进程ID号
		String myPid = String.valueOf(android.os.Process.myPid());
		// 日志收集命令
		String command = "logcat -d -v time *:i | grep \"(" + myPid + ")\"";

		try {
			// 运行命令，获取日志
			Process process = Runtime.getRuntime().exec(command);
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					process.getInputStream()), 1024);

			String line = null;
			while ((line = reader.readLine()) != null) {
				if (checkFileMaxSize(file)) {// 控制日志文件大小
					break;
				}

				if (line.length() == 0) {
					continue;
				}

				if (line.contains(myPid)) {// 过滤日志，只保存APP日志
					writer.write(line.getBytes());
					writer.write(NEWLINE);
				}
			}
			writer.flush();

			return true;
		} catch (IOException e) {
			Log.e(TAG, "writeLog # 读取DEBUG日志错误！", e);
		}
		return false;
	}

	/**
	 * 控制日志文件大小
	 * 
	 * @param file
	 *            指定文件
	 * @return true 日志文件已超过指定大小
	 */
	private boolean checkFileMaxSize(File file) {
		if (file.exists()) {
			// 0.5MB
			if (file.length() > 512000) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * 发送DEBUG日志邮件
	 * 
	 * @param filePath
	 *            DEBUG日志文件
	 */
	private void sendDebugLog() {
		// 读取将发送的文件信息
		final PreferenceUtils shared = new PreferenceUtils(mContext, LOG_SHARED);
		String filePath = shared.getString("filepath");
		String subject = shared.getString("emailSubject", "");
		String content = shared.getString("emailContent", "");
		if (filePath == null) {
			return;
		}

		// try {
		// EmailHandler sender = new EmailHandler();
		// sender.setOnProgressListener(new OnProgressListener() {
		//
		// @Override
		// public void onStart() {
		// }
		//
		// @Override
		// public void onSuccess() {
		// shared.clear();
		// }
		//
		// @Override
		// public void onFailure() {
		// Log.e(TAG, "sendDebugLog # 发送日志邮件失败！");
		// }
		//
		// });
		// // 设置服务器地址和端口
		// sender.setProperties(mHost, mPort);
		// // 设置发件人，邮件标题和文本内容
		// sender.setMessage(mFromEmail, subject, content);
		// // 设置收件人
		// sender.setReceivers(mToEmail);
		// // 添加附件
		// sender.addAttachment(filePath);
		// // 发送邮件
		// sender.sendEmail(mHost, mFromEmail, mFromEmailPwd);
		// } catch (Exception e) {
		// Log.e(TAG, "sendDebugLog # 发送日志邮件失败！", e);
		// }
	}
}
