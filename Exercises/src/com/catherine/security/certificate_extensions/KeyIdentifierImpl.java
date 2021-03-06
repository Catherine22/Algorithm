package com.catherine.security.certificate_extensions;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Base64;

import org.bouncycastle.asn1.x509.AuthorityKeyIdentifier;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.x509.extension.X509ExtensionUtil;

import com.catherine.security.certificate_extensions.itrface.KeyIdentifier;

/**
 * 
 * @author Catherine
 *
 */
public class KeyIdentifierImpl implements KeyIdentifier {
	private byte[] keyIdentifier;
	private boolean lock;

	public KeyIdentifierImpl(X509Certificate cert) throws CertificateException, IOException {
		byte[] extVal = cert.getExtensionValue(Extension.authorityKeyIdentifier.getId());
		if (extVal == null) {
			lock = true;
			return;
		}
		AuthorityKeyIdentifier aki = AuthorityKeyIdentifier.getInstance(X509ExtensionUtil.fromExtensionValue(extVal));
		keyIdentifier = aki.getKeyIdentifier();
	}

	@Override
	public byte[] getKeyIdentifier() {
		return keyIdentifier;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(OIDMap.getName(Extension.authorityKeyIdentifier.getId()));
		sb.append(" [\n");
		if (!lock) {
			sb.append(Base64.getEncoder().encodeToString(keyIdentifier));
			sb.append("\n");
		}
		sb.append("]\n");
		return sb.toString();
	}
}
