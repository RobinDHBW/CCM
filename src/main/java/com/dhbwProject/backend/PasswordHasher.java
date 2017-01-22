package com.dhbwProject.backend;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public abstract class PasswordHasher {

	/* By Robin Bahr 19.01.2017------------------------------------------------------
	 * 
	public static void main(String[] args) {
		md5(password);
	}

	static String password = "testpw";

	*/
	
//	Methode um aus dem Passwort ein 128bit Hash zu erzeugen
	public static synchronized String md5(String password) {

		String generatedPassword = null;

		try {
			// MessageDigest Instanz für MD5 generieren
			MessageDigest md = MessageDigest.getInstance("MD5");
			// Passwort Bytes zum Digest hinzufügen
			md.update(password.getBytes());
			// Hashbytes holen
			byte[] bytes = md.digest();
			// bytes[] hat bytes im Dezimal format - Konvertieren zu Hex
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			// Hashed Passwort im Hex Format erhalten
			generatedPassword = sb.toString();
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
//		System.out.println(generatedPassword);

		return generatedPassword;

	}

}
