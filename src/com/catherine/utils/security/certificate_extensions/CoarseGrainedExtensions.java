package com.catherine.utils.security.certificate_extensions;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * 
 * @author Catherine
 *
 */
public class CoarseGrainedExtensions {
	private AuthorityInformation authorityInformation;
	private KeyIdentifier keyIdentifier;
	private BasicConstraints basicConstraints;
	private CRLDistributionPoints crlDistributionPoints;
	private CertificatePolicies certificatePolicies;
	private ExtendedKeyUsage extendedKeyUsage;

	public CoarseGrainedExtensions(X509Certificate cert) throws CertificateException, IOException {
		authorityInformation = new AuthorityInformation(cert);
		keyIdentifier = new KeyIdentifier(cert);
		basicConstraints = new BasicConstraints(cert);
		crlDistributionPoints = new CRLDistributionPoints(cert);
		certificatePolicies = new CertificatePolicies(cert);
		extendedKeyUsage = new ExtendedKeyUsage(cert);

	}

	@Override
	public String toString() {
		return String.format("%s%s%s%s%s%s", 
				authorityInformation.toString(), 
				keyIdentifier.toString(),
				basicConstraints.toString(), 
				crlDistributionPoints.toString(), 
				certificatePolicies.toString(),
				extendedKeyUsage.toString());
	}

}
