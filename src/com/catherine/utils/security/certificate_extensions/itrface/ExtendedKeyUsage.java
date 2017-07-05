package com.catherine.utils.security.certificate_extensions.itrface;

import java.util.List;

public interface ExtendedKeyUsage {
	public List<String> getKeyPurposeIds();
}
