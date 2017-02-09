package com.dhbwProject.backend;

import com.dhbwProject.backend.beans.Benutzer;

public abstract class PasswordValidation {
	
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
		
		//-Prüfung Passwort und ob Benutzer inaktiv
		if (pw == null || pw.length() <1) 
			return false;
		else if(bUser.getInaktiv()){
			return false;
		}else{
			try{
				return connection.checkPassword(PasswordHasher.md5(pw), bUser);
			} catch (Exception e) {
				return false;
			}}
	}

}
