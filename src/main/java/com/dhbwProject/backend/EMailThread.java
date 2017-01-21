package com.dhbwProject.backend;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

public class EMailThread extends Thread {
	
	private Collection<String> empfaenger;
	private String eMailTitel;
	private String eMailInhalt;
	private boolean bResult = false;
	
	public EMailThread(Collection<String> empfaenger, String eMailTitel, String eMailInhalt){
		this.empfaenger = empfaenger;
		this.eMailTitel = eMailTitel;
		this.eMailInhalt = eMailInhalt;
	}
	
	@Override
	public void run(){
		this.bResult = this.sendMail();
	}
	
	
	//Zum testen---------------------------------------------------------------------
	public static void main(String[] args){
		ArrayList<String> alBenutzerMails = new ArrayList<String>();
		alBenutzerMails.add("thyeria@gmx.de");
		EMailThread eMailThread = new EMailThread(alBenutzerMails, "TestOutOfThread", "Here is the Content");
		eMailThread.start();
	}
	//---------------------------------------------------------------------------------
	
	private boolean sendMail(){
		HtmlEmail email = new HtmlEmail();
        email.setHostName(CCM_Constants.E_MAIL_HOST);
        email.setSmtpPort(CCM_Constants.E_MAIL_PORT);
        email.setAuthentication(CCM_Constants.E_MAIL_USERNAME, CCM_Constants.E_MAIL_PASSWORT);
        email.setSSLOnConnect(CCM_Constants.E_MAIL_SSL);
        
        try{
        	email.setFrom(CCM_Constants.E_MAIL_USERNAME);
        	email.addTo(this.empfaenger.toArray(new String[]{}));
        	email.setSubject(this.eMailTitel);
        	email.setHtmlMsg(this.eMailInhalt);
        	email.send();
        	return true;
        }catch(NullPointerException e){
        	e.printStackTrace();
        	return false;
        }
        catch(EmailException e){
        	e.printStackTrace();
        	return false;
        }
	}
	
	public boolean getResult(){
		return this.bResult;
	}

}
