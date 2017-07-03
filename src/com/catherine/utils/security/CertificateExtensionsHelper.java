package com.catherine.utils.security;

import java.io.IOException;
import java.math.BigInteger;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Base64;

import org.bouncycastle.asn1.x509.AccessDescription;
import org.bouncycastle.asn1.x509.AuthorityInformationAccess;
import org.bouncycastle.asn1.x509.AuthorityKeyIdentifier;
import org.bouncycastle.asn1.x509.BasicConstraints;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.GeneralName;
import org.bouncycastle.asn1.x509.X509ObjectIdentifiers;
import org.bouncycastle.x509.extension.X509ExtensionUtil;

/**
 * @author Yi-Jing <br>
 *         yacatherine19@gmail.com <br>
 *         2017年7月3日
 */
public class CertificateExtensionsHelper {
	public static void getAuthorityInformation(X509Certificate cert)
			throws IOException, CertificateException {
//		CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
		// get Authority Information Access extension (will be null if extension
		// is not present)
		byte[] extVal = cert.getExtensionValue(Extension.authorityInfoAccess.getId());
		AuthorityInformationAccess aia = AuthorityInformationAccess.getInstance(X509ExtensionUtil.fromExtensionValue(extVal));

		// check if there is a URL to issuer's certificate
		AccessDescription[] descriptions = aia.getAccessDescriptions();
		System.out.println("AuthorityInfoAccess[");
		for (AccessDescription ad : descriptions) {
			// check if it's a URL to issuer's certificate
			if (ad.getAccessMethod().equals(
					X509ObjectIdentifiers.id_ad_caIssuers)) {
				GeneralName location = ad.getAccessLocation();
				if (location.getTagNo() == GeneralName.uniformResourceIdentifier) {
					String issuerUrl = location.getName().toString();
					// http URL to issuer (test in your browser to see if it's a
					// valid certificate)
					// you can use java.net.URL.openStream() to create a
					// InputStream and create
					// the certificate with your CertificateFactory
					System.out.println("accessMethod:caIssuers" + ", id:"
							+ X509ObjectIdentifiers.id_ad_caIssuers.getId());
					System.out.println("accessLocation: URIName:" + issuerUrl);
					// URL url = new URL(issuerUrl);
					// X509Certificate issuer = (X509Certificate)
					// certificateFactory.generateCertificate(url.openStream());
				}
			}

			else if (ad.getAccessMethod().equals(
					X509ObjectIdentifiers.id_ad_ocsp)) {
				GeneralName location = ad.getAccessLocation();
				if (location.getTagNo() == GeneralName.uniformResourceIdentifier) {
					String issuerUrl = location.getName().toString();
					System.out.println("accessMethod:ocsp" + ", id:"
							+ X509ObjectIdentifiers.id_ad_ocsp.getId());
					System.out.println("accessLocation: URIName:" + issuerUrl);
				}
			}
		}
		System.out.println("]");
	}
	
	public static void getKeyIdentifier(X509Certificate cert) throws IOException{
		System.out.println("KeyIdentifier[");
		byte[] extVal = cert.getExtensionValue(Extension.authorityKeyIdentifier.getId());
		AuthorityKeyIdentifier aki = AuthorityKeyIdentifier.getInstance(X509ExtensionUtil.fromExtensionValue(extVal));
		System.out.println(Base64.getEncoder().encodeToString(aki.getKeyIdentifier()));
		System.out.println("]");
	}
	
	public static void getBasicConstraints(X509Certificate cert) throws IOException{
		System.out.println("BasicConstraints[");
		byte[] extVal = cert.getExtensionValue(Extension.basicConstraints.getId());
		BasicConstraints bc = BasicConstraints.getInstance(X509ExtensionUtil.fromExtensionValue(extVal));
		System.out.println("CA:"+bc.isCA());
		BigInteger pl = bc.getPathLenConstraint();
		if(pl==null)
			System.out.println("PathLen:undefined");
		else		System.out.println("PathLen:"+pl);
		System.out.println("]");
	}
}
