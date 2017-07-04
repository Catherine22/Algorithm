package com.catherine.utils.security.certificate_extensions;

import java.util.List;

public interface ExtendedKeyUsage {
	public List<String> getKeyPurposeIds();
}
