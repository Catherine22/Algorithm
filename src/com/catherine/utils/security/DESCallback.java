package com.catherine.utils.security;

public interface DESCallback {
	public void onResponse(byte[] iv, byte[] message);
}
