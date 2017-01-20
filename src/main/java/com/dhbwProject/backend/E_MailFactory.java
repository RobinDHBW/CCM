package com.dhbwProject.backend;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

public abstract class E_MailFactory {
	
	
	//Zum testen---------------------------------------------------------------------
	public static void main(String[] args){
		ArrayList<String> alBenutzerMails = new ArrayList<String>();
		alBenutzerMails.add("thyeria@gmx.de");
		if(sendMail(alBenutzerMails, "TestOutOfCCM", "Here is the Content"))
			System.out.println("E-Mail versandt");
		else
			System.out.println("E-Mail nicht versandt");
	}
	//---------------------------------------------------------------------------------
	
	public static synchronized boolean sendMail(Collection<String> recipients, String eMailTitel, String eMailContent){
		HtmlEmail email = new HtmlEmail();
        email.setHostName(CCM_Constants.E_MAIL_HOST);
        email.setSmtpPort(CCM_Constants.E_MAIL_PORT);
        email.setAuthentication(CCM_Constants.E_MAIL_USERNAME, CCM_Constants.E_MAIL_PASSWORT);
        email.setSSLOnConnect(CCM_Constants.E_MAIL_SSL);
        
        try{
        	email.setFrom(CCM_Constants.E_MAIL_USERNAME);
        	email.addTo(recipients.toArray(new String[]{}));
        	email.setSubject(eMailTitel);
        	email.setHtmlMsg(eMailContent);
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

}
