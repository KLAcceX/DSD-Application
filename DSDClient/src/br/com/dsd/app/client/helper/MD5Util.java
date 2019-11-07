package br.com.dsd.app.client.helper;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {

	private static final String MD5 = "MD5";

	public static String convert(String str) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance(MD5);
		md.update(str.getBytes(), 0, str.length());
		return new BigInteger(1, md.digest()).toString(16);
	}
}
