package com.catherine.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;

public class KeystoreManager {
	public void generateKeyPair() {
		try {
			KeyStore keystore = KeyStore.getInstance("JKS");// or
															// KeyStore.getDefaultType()
															// for .keystore
			File kf = new File(KeySet.KEYSTORE_PATH);
			// System.out.println(kf.getAbsolutePath());
			keystore.load(new FileInputStream(kf), KeySet.KEYSTORE_PW.toCharArray());
			Key key = keystore.getKey(KeySet.KEYSTORE_ALIAS, KeySet.KEY_PW.toCharArray());
			if (key instanceof PrivateKey) {
				
				// Get certificate of public key
				Certificate cert = keystore.getCertificate(KeySet.KEYSTORE_ALIAS);

				// Get public key
				PublicKey publicKey = cert.getPublicKey();

				// Return a key pair
				KeyPair kp = new KeyPair(publicKey, (PrivateKey) key);

				System.out.println("private key:" + kp.getPrivate());
				System.out.println("public key:" + kp.getPublic());
			}
		} catch (FileNotFoundException fe) {
			fe.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnrecoverableKeyException e) {
			e.printStackTrace();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		}
	}
}
