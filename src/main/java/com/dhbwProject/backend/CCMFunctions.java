package com.dhbwProject.backend;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.LinkedList;

import com.dhbwProject.backend.beans.Benutzer;
import com.dhbwProject.backend.beans.Besuch;

public abstract class CCMFunctions {
	
	public static synchronized long differenzTage(Date dAlt, Date dNeu){
		return  Math.abs(((dNeu.getTime() - dAlt.getTime() + CCM_Constants.ONE_HOUR_AS_LONG) / (CCM_Constants.ONE_HOUR_AS_LONG * 24)));
	}
	
	public static synchronized boolean isBesuchKollision(LinkedList<Besuch> lBesuch, Date dStartNew){
		long differenz = 0;
		for(Besuch b : lBesuch){
			differenz = differenzTage(b.getStartDate(), dStartNew);
			if(differenz < CCM_Constants.BESUCH_KOLLISION_WERT)
				return true;
		}
		return false;
			
	}
	
	public static synchronized String md5(String password) {
		String generatedPassword = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(password.getBytes());
			byte[] bytes = md.digest();
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) 
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			generatedPassword = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return generatedPassword;
	}
	
	public static synchronized boolean isValidPassword(dbConnect connection, String bName, String pw){
		Benutzer bUser;
		if(bName == null || bName.length() <1)
			return false;
		else
			try{
				bUser = connection.getBenutzerById(bName);
			}catch(Exception e){
				return false;
			}
		
		//-Prüfung Passwort
		if (pw == null || pw.length() <1) 
			return false;
		else
			try{
				return connection.checkPassword(PasswordHasher.md5(pw), bUser);
			} catch (Exception e) {
				return false;
			}
	}

}
