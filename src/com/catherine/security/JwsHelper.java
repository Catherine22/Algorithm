package com.catherine.security;

import java.io.FileNotFoundException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Catherine on 2017/6/30. Soft-World Inc.
 * catherine919@soft-world.com.tw
 */
public class JwsHelper {
	private String[] jws;
	private String decodedHeader;

	public JwsHelper(String jws) {
		this.jws = jws.split("\\.");
		decodedHeader = new String(Base64.getDecoder().decode(this.jws[0]));
	}

	public String getDecodedHeader() {
		return decodedHeader;
	}

	public String getDecodedPayload() {
		return new String(Base64.getDecoder().decode(jws[1]));
	}

	public String getDecodedSignature() {
		return new String(Base64.getDecoder().decode(jws[2]));
	}

	public String getAlg() throws JSONException {
		JSONObject jo = new JSONObject(decodedHeader);
		return jo.optString("alg", "");
	}
	
	public List<X509Certificate> getX5CCertificates() throws JSONException,
			CertificateException, FileNotFoundException {
		JSONObject jo = new JSONObject(decodedHeader);
		JSONArray ja = jo.optJSONArray("x5c");
		if (ja != null) {
			List<X509Certificate> certs = new ArrayList<>();
			for (int i = 0; i < ja.length(); i++) {
				certs.add(CertificatesManager.getX509Certificate(ja
						.getString(i)));
			}
			return certs;
		}
		return null;
	}
}
