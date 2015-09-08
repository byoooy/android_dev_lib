package com.android.library.utils;

import java.security.MessageDigest;

/** MD5加密工具 */
public class MD5EncryptUtil {

	private static MD5EncryptUtil INSTANCE;

	private char[] DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
			'a', 'b', 'c', 'd', 'e', 'f' };

	public static MD5EncryptUtil getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new MD5EncryptUtil();
		}
		return INSTANCE;
	}

	/**
	 * MD5加密
	 * 
	 * @param str
	 *            加密字符串
	 * @return 加密结果
	 */
	public String encode(String str) {
		try {
			byte[] strTemp = str.getBytes();
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char set[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				set[k++] = DIGITS[byte0 >>> 4 & 0xf];
				set[k++] = DIGITS[byte0 & 0xf];
			}
			return new String(set);
		} catch (Exception e) {
			return null;
		}
	}
}
