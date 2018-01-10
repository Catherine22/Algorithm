package com.catherine.security.certificate_extensions.itrface;

import java.util.List;

public interface ExtendedKeyUsage {
	public List<String> getKeyPurposeIds();
}
