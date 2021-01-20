package com.catherine.security;

import java.security.PrivateKey;
import java.security.PublicKey;

public interface RSACallback {
	public void onResponse(PrivateKey privateKey);

	public void onResponse(String modulus, String exponent);

	public void onResponse(PrivateKey privateKey, PublicKey publicKey);
}
