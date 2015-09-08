package com.android.library.utils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import android.text.TextUtils;

/**
 * 序列化保存工具
 */
public class SerializeUtils {

	private static SerializeUtils INSTANCE;

	public static SerializeUtils getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new SerializeUtils();
		}
		return INSTANCE;
	}

	/**
	 * 保存序列化对象
	 * 
	 * @param filePath
	 *            保存路径
	 * @param obj
	 *            序列化对象
	 */
	public void saveObject(String filePath, Serializable obj) {
		if (obj == null) {
			return;
		}
		File file = new File(filePath);
		if (file.exists()) {
			file.delete();
		}
		DataOutputStream dos = null;
		ObjectOutputStream oos = null;
		try {
			dos = new DataOutputStream(new FileOutputStream(file));
			oos = new ObjectOutputStream(dos);
			oos.writeObject(obj);
		} catch (Exception e) {
			file.delete();
		} finally {
			if (dos != null) {
				try {
					dos.close();
				} catch (Exception e) {
				}
			}
			if (oos != null) {
				try {
					oos.close();
				} catch (Exception e) {
				}
			}
		}
	}

	/**
	 * 读取序列化对象
	 * 
	 * @param filePath
	 *            保存路径
	 * @return 序列化对象
	 */
	public Object readObject(String filePath) {
		if (TextUtils.isEmpty(filePath)) {
			return null;
		}
		File file = new File(filePath);
		DataInputStream dis = null;
		ObjectInputStream ois = null;
		Object obj = null;
		try {
			dis = new DataInputStream(new FileInputStream(file));
			ois = new ObjectInputStream(dis);
			obj = ois.readObject();
		} catch (Exception e) {
		} finally {
			if (dis != null) {
				try {
					dis.close();
				} catch (IOException e) {
				}
			}
			if (ois != null) {
				try {
					ois.close();
				} catch (IOException e) {
				}
			}
		}
		return obj;
	}

	/**
	 * 删除保存文件
	 * 
	 * @param filePath
	 *            保存路径
	 */
	public void removeObject(String filePath) {
		File file = new File(filePath);
		file.delete();
	}
}
