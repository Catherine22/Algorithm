package com.catherine.utils.security;

public class DESRule {
	/**
	 * 初始向量，呼叫Cipher的加密后会产生。
	 */
	private byte[] iv;
	private byte[] message;

	public byte[] getIv() {
		return iv;
	}

	public void setIv(byte[] iv) {
		this.iv = iv;
	}

	public byte[] getMessage() {
		return message;
	}

	public void setMessage(byte[] message) {
		this.message = message;
	}

}
