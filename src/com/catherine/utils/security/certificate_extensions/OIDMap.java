package com.catherine.utils.security.certificate_extensions;

import java.util.HashMap;
import java.util.Map;

/**
 * 密钥用法，这边只列举几个
 * 
 * @author Catherine
 *
 */
public class OIDMap {
	private final static Map<String, String> names = new HashMap<>();
	private final static Map<String, String> descriptions = new HashMap<>();
	static {
		names.put("1.3.6.1.5.5.7.3.1", "serverAuth");
		names.put("1.3.6.1.5.5.7.3.2", "clientAuth");
		names.put("1.3.6.1.5.5.7.3.3", "codeSigning");
		names.put("1.3.6.1.5.5.7.3.4", "emailProtection");
		names.put("1.3.6.1.5.5.7.3.5", "ipsecEndSystem");
		names.put("1.3.6.1.5.5.7.3.6", "ipsecTunnel");
		names.put("2.23.140.1.2.2", "extended-validation");
		names.put("1.3.6.1.4.1.11129.2.5.1", "Google Internet Authority G2");
		names.put("1.3.6.1.5.5.7.48.2", "caIssuers");
		names.put("1.3.6.1.5.5.7.48.1", "OCSP");

		descriptions.put("1.3.6.1.5.5.7.3.1", "Indicates that a certificate can be used as an SSL server certificate.");
		descriptions.put("1.3.6.1.5.5.7.3.2", "Indicates that a certificate can be used as an SSL client certificate.");
		descriptions.put("1.3.6.1.5.5.7.3.3", "Indicates that a certificate can be used for code signing.");
		descriptions.put("1.3.6.1.5.5.7.3.4",
				"Indicates that a certificate can be used for protecting email (signing, encryption, key agreement).");
		descriptions.put("1.3.6.1.5.5.7.3.5", "URL for further info: http://www.ietf.org/rfc/rfc2459.txt");
		descriptions.put("1.3.6.1.5.5.7.3.6", "URL for further info: http://www.ietf.org/rfc/rfc2459.txt");
		descriptions.put("2.23.140.1.2.2",
				"CA-Browser Forum, Certificate Policy, Extended Validation Baseline Requirements, Organization Validated");
		descriptions.put("1.3.6.1.4.1.11129.2.5.1",
				"https://static.googleusercontent.com/media/pki.google.com/en//GIAG2-CPS-1.0.pdf");
		descriptions.put("1.3.6.1.5.5.7.48.2", "URL for further info: http://www.ietf.org/rfc/rfc2459.txt");
		descriptions.put("1.3.6.1.5.5.7.48.1",
				"Online Certificate Status Protokoll\nSee also the OID Repository website reference for 1.3.6.1.5.5.7.48.1");
	}

	public static String getName(String OID) {
		return names.get(OID);
	}

	public static String getDescription(String OID) {
		return descriptions.get(OID);
	}
}
