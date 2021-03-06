package com.catherine.security;

import java.util.HashMap;
import java.util.Map;
/**
 * 
 * @author Catherine
 *
 */
public class Algorithm {
	public final static String CHARSET = "UTF8";
	/**
	 * 在Android平台的JCE中，非对称Key的常用算法有“RSA”、“DSA”、“Diffie−Hellman”、“Elliptic Curve
	 * (EC)”等。
	 */
	public final static String KEYPAIR_ALGORITHM = "RSA";
	public final static String SINGLE_KEY_ALGORITHM = "DES";

	/**
	 * MD5表示MD的计算方法，RSA表示加密的计算方法。常用的签名算法还有“SHA1withRSA”、“SHA256withRSA”
	 */
	public final static String ALG_MD5_WITH_RSA = "MD5withRSA";
	public final static String ALG_SHA256_WITH_RSA = "SHA256withRSA";
	public final static Map<String, String> rules = new HashMap<>();;
	static {
		rules.put("DES", "DES/CBC/PKCS5Padding");
		rules.put("RSA", "RSA/ECB/PKCS1Padding");
	}
}
