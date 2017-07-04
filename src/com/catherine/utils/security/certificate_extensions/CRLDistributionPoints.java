package com.catherine.utils.security.certificate_extensions;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;

public interface CRLDistributionPoints {
	public List<String> getURINames();

	public X509Certificate getCaIssuersCert(String URIName) throws CertificateException, IOException;
}
