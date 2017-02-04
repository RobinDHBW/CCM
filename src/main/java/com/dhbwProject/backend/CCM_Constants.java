package com.dhbwProject.backend;

public interface CCM_Constants {
	
	public static final String VIEW_NAME_START = "Startseite";
	public static final String VIEW_NAME_LOGIN = "Login";
	public static final String VIEW_NAME_BENUTZER = "Benutzerverwaltung";
	public static final String VIEW_NAME_UNTERNEHMEN = "Unternehmen";
	public static final String VIEW_NAME_BESUCH = "Termine";
	
	public static final String COLOR_DHBW_RED = "";
	public static final String COLOR_DHBW_WHITE = "";
	public static final String COLOR_DHBW_BLACK = "";
	
	public static final String SESSION_VALUE_USER = "User";
	public static final String SESSION_VALUE_CONNECTION = "dbConnection";
	
	public static final String E_MAIL_HOST = "smtp.gmail.com";
	public static final int E_MAIL_PORT = 465;
	public static final boolean E_MAIL_SSL = true;
	public static final String E_MAIL_USERNAME = "ccmsys@gmail.com";
	public static final String E_MAIL_PASSWORT = "ccmBenachrichtigung";
	
	public static final long ONE_HOUR_AS_LONG = 60 * 60 * 1000L;
	public static int BESUCH_KOLLISION_WERT = 30;
	

}
