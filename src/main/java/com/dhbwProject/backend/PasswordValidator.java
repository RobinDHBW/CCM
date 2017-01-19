package com.dhbwProject.backend;

import java.sql.SQLException;

import com.vaadin.data.validator.AbstractValidator;
import com.vaadin.server.VaadinSession;

/*	Der PasswordValidator prueft das Passwort auf feste Richtlinien,
 * 	diese muessen evtl. noch ausdiskutiert werden*/
public class PasswordValidator extends AbstractValidator<String>{
	private static final long serialVersionUID = 1L;
	String benutzer;

	public PasswordValidator(String bName) {
		super("Passwort oder Benutzername ist fehlerhaft!");
		this.benutzer = bName;
	}

	@Override
	protected boolean isValidValue(String value) {
//        dbConnect connection = (dbConnect)VaadinSession.getCurrent().getSession().getAttribute(CCM_Constants.SESSION_VALUE_CONNECTION);
//		try {
//			if (value != null&& (value.length() <3)) 
//				return false;
//			else if(connection.checkPassword(PasswordHasher.md5(value), connection.getBenutzerById(benutzer))) //Hier kommt der Hasher zum Einsatz
//				return true;
//		} catch (SQLException e) {
//				e.printStackTrace();
//		}
//		return false;
		
		
		return true;
	}

	@Override
	public Class<String> getType() {
		return String.class;
	}

}
