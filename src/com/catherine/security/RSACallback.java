package com.catherine.security;

import java.security.PrivateKey;

public interface RSACallback {
	public void onResponse(PrivateKey privateKey);

	public void onResponse(String modulus, String exponent);
}
