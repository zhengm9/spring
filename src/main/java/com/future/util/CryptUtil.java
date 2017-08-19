package com.future.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.sun.crypto.provider.SunJCE;


public class CryptUtil {
	private final static String desAlgorithm = "DESede/CBC/PKCS5Padding";

	private final static String desKeyAlgorithm = "DESede";

	private static final char[] DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c',
			'd', 'e', 'f' };
	private final static byte defaultIV[] = new byte[8];


	static {
		try {
			java.security.Security.addProvider(new SunJCE());
		} catch (Exception e) {
			throw new RuntimeException("SunJCE error");
		}
	}


	public static String md5Hex(String content) throws Exception {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		}
		try {
			md5.update(content.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			throw new Exception("error", e);
		}

		return new String(encodeHex(md5.digest()));

	}

	/**
	 * @param data
	 * @return
	 */
	public static char[] encodeHex(byte[] data) {

		int l = data.length;

		char[] out = new char[l << 1];

		// two characters form the hex value.
		for (int i = 0, j = 0; i < l; i++) {
			out[j++] = DIGITS[(0xF0 & data[i]) >>> 4];
			out[j++] = DIGITS[0x0F & data[i]];
		}

		return out;
	}


	// private static
	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		String secretStr=md5Hex("891230:o3gx");
		System.out.println(secretStr);
	}

}
