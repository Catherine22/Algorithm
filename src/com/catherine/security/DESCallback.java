package com.catherine.security;

public interface DESCallback {
	public void onResponse(byte[] iv, byte[] message);
}
