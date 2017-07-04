package com.catherine.utils.security.certificate_extensions;

import java.io.IOException;
import java.math.BigInteger;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.x509.extension.X509ExtensionUtil;

/**
 * 
 * @author Catherine
 *
 */
public class BasicConstraints {
	/**
	 * 表明该证书可否作为CA证书签发下一级证书
	 */
	private boolean isCA;
	/**
	 * 只有当CA=true时才有效，表明具体可以签发的证书级别
	 */
	private BigInteger pathLen;

	public BasicConstraints(X509Certificate cert) throws CertificateException, IOException {
		byte[] extVal = cert.getExtensionValue(Extension.basicConstraints.getId());
		org.bouncycastle.asn1.x509.BasicConstraints bc = org.bouncycastle.asn1.x509.BasicConstraints
				.getInstance(X509ExtensionUtil.fromExtensionValue(extVal));
		isCA = bc.isCA();
		pathLen = bc.getPathLenConstraint();
	}

	/**
	 * 表明该证书可否作为CA证书签发下一级证书
	 */
	public boolean isCA() {
		return isCA;
	}

	/**
	 * 只有当CA=true时才有效，表明具体可以签发的证书级别
	 */
	public BigInteger getPathLen() {
		return pathLen;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("BasicConstraints [\n");
		sb.append("isCA:");
		sb.append(isCA);
		sb.append("\nPathLen:");
		if (pathLen == null)
			sb.append("undefined");
		else
			sb.append(pathLen);
		sb.append("\n]\n");
		return sb.toString();
	}
}
