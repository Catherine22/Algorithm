package com.catherine.utils.security;

import java.math.BigInteger;
import java.security.PrivateKey;

/**
 * RSA算法可通过模和指数可以还原
 * 
 * @author Catherine
 *
 */
public class RSARule {
	private PrivateKey privateKey;
	private BigInteger modulus;
	private BigInteger exponent;

	public PrivateKey getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(PrivateKey privateKey) {
		this.privateKey = privateKey;
	}

	public BigInteger getModulus() {
		return modulus;
	}

	public void setModulus(BigInteger modulus) {
		this.modulus = modulus;
	}

	public BigInteger getExponent() {
		return exponent;
	}

	public void setExponent(BigInteger exponent) {
		this.exponent = exponent;
	}

}
