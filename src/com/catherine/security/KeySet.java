package com.catherine.security;

public class KeySet {
	
	public final static String KEYSTORE_TYPE = "JKS";// or KeyStore.getDefaultType() for .keystore
	public final static String KEYSTORE_PATH = "assets/productiongg.jks";
	public final static String KEYSTORE_PW = "ggcompany123!";
	public final static String KEYSTORE_ALIAS = "productiongg";
	public final static String KEY_PW = "ggcompany123!";
	public final static String CERTIFICATION_PATH = "assets/productiongg.cer";
	
	public final static String TRUSTED_CA = "Google Internet Authority G2";
	public final static String GIAG2_URL = "http://pki.google.com/GIAG2.crt";
	public final static String TRUSTED_SSL_HOSTNAME = "attest.android.com";
}