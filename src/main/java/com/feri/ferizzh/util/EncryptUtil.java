package com.feri.ferizzh.util;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
/**
 * Copyright: Copyright (c) 2018
 * 
 * @ClassName: EncryptUtil.java
 * @Description: 该类的功能描述
 * @version: v1.0.0
 * @author: feri
 * @date: 2018年12月18日 下午2:31:05 Modification History: Date Author Version
 *        Description ---------------------------------------------------------*
 *        2018年12月18日 feri v1.0.0 修改原因
 */
public class EncryptUtil {
	public static final String SHA1 = "SHA-1";
	public static final String SHA256 = "SHA-256";
	public static final String SHA512 = "SHA-512";
	public static final String ENCODING = "UTF-8";
	public static final String PUBKEY = "publicKey";
	public static final String PRIKEY = "privateKey";
	// Hash算法
	// MD5 返回值是16进制
	public static String md5Enc(String msg) {
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			digest.update(msg.getBytes(ENCODING));
			return new BigInteger(1, digest.digest()).toString(16);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	// SHA-1 SHA-256 SHA-512 返回值是16进制
	public static String SHAEnc(String type, String msg) {
		try {
			MessageDigest digest = MessageDigest.getInstance(type);
			digest.update(msg.getBytes(ENCODING));
			return new BigInteger(1, digest.digest()).toString(16);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	// AES
	public static byte[] createAESKey() {
		try {
			// 创建秘钥生成器
			KeyGenerator generator = KeyGenerator.getInstance("AES");
			// 完成秘钥初始化 AES的128
			generator.init(128);
			// 生成秘钥
			SecretKey key = generator.generateKey();
			return key.getEncoded();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}



}