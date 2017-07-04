package com.catherine.utils.security.certificate_extensions;

import java.io.IOException;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import org.bouncycastle.asn1.x509.CRLDistPoint;
import org.bouncycastle.asn1.x509.DistributionPoint;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.GeneralName;
import org.bouncycastle.asn1.x509.GeneralNames;
import org.bouncycastle.x509.extension.X509ExtensionUtil;

/**
 * 
 * @author Catherine
 *
 */
public class CRLDistributionPoints {
	private List<String> URINames;
	private X509Certificate caIssuersCert;

	public CRLDistributionPoints(X509Certificate cert) throws CertificateException, IOException {
		URINames = new ArrayList<>();
		byte[] extVal = cert.getExtensionValue(Extension.cRLDistributionPoints.getId());
		CRLDistPoint crlDistPoint = CRLDistPoint.getInstance(X509ExtensionUtil.fromExtensionValue(extVal));
		DistributionPoint[] points = crlDistPoint.getDistributionPoints();
		for (DistributionPoint p : points) {
			GeneralNames tmp = p.getCRLIssuer();
			if (tmp != null) {
				GeneralName[] crlIssers = tmp.getNames();
				for (int i = 0; i < crlIssers.length; i++) {
					if (crlIssers[i].getTagNo() == GeneralName.uniformResourceIdentifier) {
						String issuerUrl = crlIssers[i].toString();
						URINames.add(issuerUrl);
						URL url = new URL(issuerUrl);
						CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
						caIssuersCert = (X509Certificate) certificateFactory.generateCertificate(url.openStream());
					}
				}
			}
		}
	}

	public List<String> getURINames() {
		return URINames;
	}

	public X509Certificate getCaIssuersCert() {
		return caIssuersCert;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("CRLDistributionPoints [\n");
		for (int i = 0; i < URINames.size(); i++) {
			sb.append("URIName:");
			sb.append(URINames.get(i));
			sb.append("\n");
		}
		sb.append("]\n");
		return sb.toString();
	}
}
