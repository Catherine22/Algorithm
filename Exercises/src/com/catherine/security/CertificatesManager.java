package com.catherine.security;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateFactory;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import com.catherine.security.certificate_extensions.CertificateExtensionsHelper;
import com.catherine.security.certificate_extensions.OIDMap;

/**
 * 
 * @author Catherine
 *
 */
public class CertificatesManager {

	/**
	 * X.509是数字证书的规范，X.509只能携带公钥信息。<br>
	 * 另外还有PKCS#7和12包含更多的一些信息。PKCS#12由于可包含私钥信息，而且文件本身还可通过密码保护，所以更适合信息交换。<br>
	 * 常见的证书文件格式：".pem", ".cer", ".crt", ".der", ".p7b", ".p7c", ".p12"<br>
	 * 总结：<br>
	 * 1. 证书包含很多信息，但一般就是各种Key的内容。<br>
	 * 2. 证书由CA签发。为了校验某个证书是否可信，往往需要把一整条证书链都校验一把，直到根证书。<br>
	 * 3. 系统一般会集成很多根证书，这样免得使用者自己去下载根证书了。<br>
	 * 4. 证书自己的格式通用为X.509，但是证书文件的格式却有很多。不同的系统可能支持不同的证书文件。
	 * 
	 * @throws CertificateException
	 * @throws FileNotFoundException
	 */
	public static void printCertificatesInfo(X509Certificate cf)
			throws CertificateException, FileNotFoundException, IOException {

		System.out.println("证书序列号:" + cf.getSerialNumber());
		System.out.println("版本:" + cf.getVersion());
		System.out.println("证书类型:" + cf.getType());
		System.out.println(String.format("有效期限:%s 到 %s", cf.getNotBefore(), cf.getNotAfter()));

		Map<String, String> subjectDN = refactorDN(cf.getSubjectDN().getName());

		StringBuilder su = new StringBuilder();
		boolean[] subjectUniqueID = cf.getSubjectUniqueID();
		if (subjectUniqueID != null) {
			for (boolean b : subjectUniqueID) {
				int myInt = (b) ? 1 : 0;
				su.append(myInt);
			}
		} else
			su.append("null");
		System.out.println(String.format("主体:[唯一标识符:%s, 通用名称:%s, 机构单元名称:%s, 机构名:%s, 地理位置:%s, 州/省名:%s, 国名:%s]", su,
				subjectDN.getOrDefault("CN", ""), subjectDN.getOrDefault("OU", ""), subjectDN.getOrDefault("O", ""),
				subjectDN.getOrDefault("L", ""), subjectDN.getOrDefault("ST", ""), subjectDN.getOrDefault("C", "")));

		Map<String, String> issuerDN = refactorDN(cf.getIssuerDN().getName());

		StringBuilder i = new StringBuilder();
		if (subjectUniqueID != null) {
			boolean[] issuerUniqueID = cf.getIssuerUniqueID();
			for (boolean b : issuerUniqueID) {
				int myInt = (b) ? 1 : 0;
				i.append(myInt);
			}
		} else
			i.append("null");
		System.out.println(String.format("签发者:[唯一标识符:%s, 通用名称:%s, 机构单元名称:%s, 机构名:%s, 地理位置:%s, 州/省名:%s, 国名:%s]", i,
				issuerDN.getOrDefault("CN", ""), issuerDN.getOrDefault("OU", ""), issuerDN.getOrDefault("O", ""),
				issuerDN.getOrDefault("L", ""), issuerDN.getOrDefault("ST", ""), issuerDN.getOrDefault("C", "")));

		System.out.println("签名算法:" + cf.getSigAlgName());
		System.out.println(String.format("签名算法OID:%s (%s)", cf.getSigAlgOID(), OIDMap.getName(cf.getSigAlgOID())));
		String sigAlgParams = (cf.getSigAlgParams() == null) ? "null"
				: Base64.getEncoder().encodeToString(cf.getSigAlgParams());
		System.out.println("签名参数:" + sigAlgParams);
		System.out.println("签名:" + Base64.getEncoder().encodeToString(cf.getSignature()));

		System.out.println("证书的限制路径长度:" + cf.getBasicConstraints());

		Key publicKey = cf.getPublicKey();
		System.out.println("公鑰算法:" + publicKey.getAlgorithm());
		System.out.println("公鑰格式:" + publicKey.getFormat());
		if (publicKey.getAlgorithm().equalsIgnoreCase("RSA")) {
			RSAPublicKey rsaPublicKey = (RSAPublicKey) publicKey;
			System.out.println("Modulus:" + rsaPublicKey.getModulus());
			System.out.println("Exponent:" + rsaPublicKey.getPublicExponent());

		}
		System.out.println("公鑰:" + Base64.getEncoder().encodeToString(publicKey.getEncoded()));

		System.out.println("扩展(Certificate Extensions):[");
		CertificateExtensionsHelper coarseGrainedExtensions = new CertificateExtensionsHelper(cf);
		System.out.println(coarseGrainedExtensions.toString());
		System.out.println("]");
		// System.out.println("==>X509Certificate: " + cf.toString());
	}

	/**
	 * PEM格式字串转X509证书
	 * <p>
	 * -----BEGIN CERTIFICATE----- xxx -----END CERTIFICATE-----
	 *
	 * @param pem
	 * @return
	 * @throws CertificateException
	 * @throws FileNotFoundException
	 */
	public static X509Certificate getX509Certificate(String pem) throws CertificateException, FileNotFoundException {
		CertificateFactory cf = CertificateFactory.getInstance("X.509");
		String newString = pem.replaceAll("-----BEGIN CERTIFICATE-----", "").replaceAll("-----END CERTIFICATE-----",
				"");
		return (X509Certificate) cf
				.generateCertificate(new ByteArrayInputStream(Base64.getDecoder().decode(newString)));
	}

	/**
	 * 读取X509证书文件
	 * 
	 * @return
	 * @throws CertificateException
	 * @throws FileNotFoundException
	 */
	public static X509Certificate loadX509Certificate(String path) throws CertificateException, FileNotFoundException {
		CertificateFactory cf = CertificateFactory.getInstance("X.509");
		FileInputStream fis = new FileInputStream(path); // 证书文件
		return (X509Certificate) cf.generateCertificate(fis);
	}

	/**
	 * 下载X509证书文件
	 * 
	 * @param urlString
	 * @return
	 * @throws CertificateException
	 * @throws IOException
	 */
	public static X509Certificate downloadCaIssuersCert(String urlString) throws CertificateException, IOException {
		URL url = new URL(urlString);
		CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
		return (X509Certificate) certificateFactory.generateCertificate(url.openStream());
	}
	
	/**
	 * 验证证书<br>
	 * 1. 首先检查期限 <br>
	 * 2. 检查发布机构是否可信任<br>
	 * 3. 利用证书发布机构的公钥验证当前证书的签名<br>
	 * 4. 验证证书的SAN的DNSName来自可信任host<br>
	 * <br>
	 * <br>
	 * 验证证书链<br>
	 * 
	 * @param cert
	 * @return
	 */
	public static boolean validate(X509Certificate cert, X509Certificate rootCert) {
		try {
			// 1. Is today's date within validity period?
			cert.checkValidity();// Certificate is active for current date

			// 2. Is the issuing CA a trusted CA?
			Map<String, String> issuerDN = refactorDN(cert.getIssuerDN().getName());
			if (!issuerDN.getOrDefault("CN", "").trim().equals(KeySet.TRUSTED_CA.trim())) {
				System.out.println("This certificate published by a untrusted CA.");
				return false;
			}

			// 3. Does the issuing CA’s public key validate the issuer’s digital
			// signature?
			cert.verify(rootCert.getPublicKey());

			// 4. Does the domain name in the server’s certificate match the
			// domain name of the server itself? (Alternative names)
			CertificateExtensionsHelper coarseGrainedExtensions = new CertificateExtensionsHelper(cert);

			if (!coarseGrainedExtensions.getDNSNames().contains(KeySet.TRUSTED_SSL_HOSTNAME))
				System.out.println("Untrusted domain name:" + coarseGrainedExtensions.getDNSNames());

			return true;
		} catch (CertificateExpiredException e) {
			System.out.println("Certificate is expired");
			e.printStackTrace();
		} catch (CertificateNotYetValidException e) {
			System.out.println("Certificate is not yet valid.");
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			System.out.println("InvalidKeyException on incorrect key.");
			e.printStackTrace();
		} catch (CertificateException e) {
			System.out.println("CertificateException on encoding errors.");
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			System.out.println("NoSuchProviderException if there's no default provider.");
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			System.out.println("NoSuchProviderException if there's no default provider.");
			e.printStackTrace();
		} catch (SignatureException e) {
			System.out.println("SignatureException on signature errors.");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Failed to read certificate");
			e.printStackTrace();
		}
		return false;
	}

	private static Map<String, String> refactorDN(String name) {
		Map<String, String> map = new HashMap<>();
		String[] pairs = name.split(",");
		for (int i = 0; i < pairs.length; i++) {
			String pair = pairs[i];
			String[] keyValue = pair.split("=");
			if (keyValue.length > 1) {
				map.put(keyValue[0], keyValue[1]);
			} else {
				map.put(keyValue[0], "");
			}
		}
		return map;
	}
}
