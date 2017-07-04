package com.catherine.utils.security.certificate_extensions;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * 
 * @author Catherine
 *
 */
public class CertificateExtensionsHelper {
	public static CoarseGrainedExtensions getExtensions(X509Certificate cert) throws CertificateException, IOException {
		return new CoarseGrainedExtensions(cert);
	}
}
