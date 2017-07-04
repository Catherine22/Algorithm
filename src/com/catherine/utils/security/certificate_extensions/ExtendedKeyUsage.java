package com.catherine.utils.security.certificate_extensions;

import java.io.IOException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.KeyPurposeId;
import org.bouncycastle.x509.extension.X509ExtensionUtil;

public class ExtendedKeyUsage {
	private List<String> keyPurposeIds;

	public ExtendedKeyUsage(X509Certificate cert) throws IOException {
		keyPurposeIds = new ArrayList<>();
		byte[] extVal = cert.getExtensionValue(Extension.extendedKeyUsage.getId());
		org.bouncycastle.asn1.x509.ExtendedKeyUsage usage = org.bouncycastle.asn1.x509.ExtendedKeyUsage
				.getInstance(X509ExtensionUtil.fromExtensionValue(extVal));
		KeyPurposeId[] usages = usage.getUsages();
		for (int i = 0; i < usages.length; i++) {
			keyPurposeIds.add(usages[i].getId());
		}
	}

	public List<String> getKeyPurposeIds() {
		return keyPurposeIds;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("ExtendedKeyUsage [\n");
		for (int i = 0; i < keyPurposeIds.size(); i++) {
			sb.append("keyPurposeIds:");
			sb.append(OIDMap.getName(keyPurposeIds.get(i)));
			sb.append("\n");
		}
		sb.append("]\n");
		return sb.toString();
	}
}
