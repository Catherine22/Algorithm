package com.catherine.utils.security.certificate_extensions.itrface;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;

public interface AuthorityInformation {
	public List<String> getAccessIDs();

	public List<String> getAccessMethods();

	public List<String> getAccessLocations();

	public X509Certificate getCaIssuersCert(String issuerUrl) throws CertificateException, IOException;

}
