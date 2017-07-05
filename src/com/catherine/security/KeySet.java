package com.catherine.security;

public class KeySet {
	// or KeyStore.getDefaultType() for .keystore
	public final static String KEYSTORE_TYPE = "JKS";
	public final static String KEYSTORE_PATH = "assets/productiongg.jks";
	public final static String KEYSTORE_PW = "ggcompany123!";
	public final static String KEYSTORE_ALIAS = "productiongg";
	public final static String KEY_PW = "ggcompany123!";
	public final static String CERTIFICATION_PATH = "assets/productiongg.cer";
	public final static String ROOT_CERTIFICATION_PATH = "assets/GIAG2.crt";
	
	public final static String TRUSTED_CA = "CN=Google Internet Authority G2, O=Google Inc, C=US";
	public final static String GIAG2_URL = "http://pki.google.com/GIAG2.crt";
	public final static String TRUSTED_SSL_HOSTNAME = "attest.android.com";
}