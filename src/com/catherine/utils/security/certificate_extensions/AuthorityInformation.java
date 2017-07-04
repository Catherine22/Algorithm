package com.catherine.utils.security.certificate_extensions;

import java.io.IOException;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import org.bouncycastle.asn1.x509.AccessDescription;
import org.bouncycastle.asn1.x509.AuthorityInformationAccess;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.GeneralName;
import org.bouncycastle.asn1.x509.X509ObjectIdentifiers;
import org.bouncycastle.x509.extension.X509ExtensionUtil;

/**
 * 
 * @author Catherine
 *
 */
public class AuthorityInformation {
	private List<String> accessIDs;
	private List<String> accessMethods;
	private List<String> accessLocations;
	private X509Certificate caIssuersCert;

	/**
	 * 
	 * @param cert
	 * @throws CertificateException
	 * @throws IOException
	 */
	public AuthorityInformation(X509Certificate cert) throws CertificateException, IOException {
		accessIDs = new ArrayList<>();
		accessMethods = new ArrayList<>();
		accessLocations = new ArrayList<>();

		byte[] extVal = cert.getExtensionValue(Extension.authorityInfoAccess.getId());
		AuthorityInformationAccess aia = AuthorityInformationAccess
				.getInstance(X509ExtensionUtil.fromExtensionValue(extVal));
		// check if there is a URL to issuer's certificate
		AccessDescription[] descriptions = aia.getAccessDescriptions();
		for (AccessDescription ad : descriptions) {
			// check if it's a URL to issuer's certificate
			if (ad.getAccessMethod().equals(X509ObjectIdentifiers.id_ad_caIssuers)) {
				GeneralName location = ad.getAccessLocation();
				if (location.getTagNo() == GeneralName.uniformResourceIdentifier) {
					String issuerUrl = location.getName().toString();
					// http URL to issuer (test in your browser to see if
					// it's a valid certificate)
					// you can use java.net.URL.openStream() to create a
					// InputStream and create the certificate with your
					// CertificateFactory
					accessMethods.add(OIDMap.getName(X509ObjectIdentifiers.id_ad_caIssuers.getId()));
					accessIDs.add(X509ObjectIdentifiers.id_ad_caIssuers.getId());
					accessLocations.add(issuerUrl);

					URL url = new URL(issuerUrl);
					CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
					caIssuersCert = (X509Certificate) certificateFactory.generateCertificate(url.openStream());
				}
			}

			else if (ad.getAccessMethod().equals(X509ObjectIdentifiers.id_ad_ocsp)) {
				GeneralName location = ad.getAccessLocation();
				if (location.getTagNo() == GeneralName.uniformResourceIdentifier) {
					String issuerUrl = location.getName().toString();
					accessMethods.add(OIDMap.getName(X509ObjectIdentifiers.id_ad_ocsp.getId()));
					accessIDs.add(X509ObjectIdentifiers.id_ad_ocsp.getId());
					accessLocations.add(issuerUrl);

				}
			}
		}
	}

	public List<String> getAccessIDs() {
		return accessIDs;
	}

	public List<String> getAccessMethods() {
		return accessMethods;
	}

	public List<String> getAccessLocations() {
		return accessLocations;
	}

	public X509Certificate getCaIssuersCert() {
		return caIssuersCert;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("AuthorityInfoAccess [\n");
		for (int i = 0; i < accessIDs.size(); i++) {
			sb.append("accessID:");
			sb.append(accessIDs.get(i));
			sb.append("\naccessMethod:");
			sb.append(accessMethods.get(i));
			sb.append("\naccessLocation (URIName):");
			sb.append(accessLocations.get(i));
			sb.append("\n");
		}
		sb.append("]\n");
		return sb.toString();
	}

}
