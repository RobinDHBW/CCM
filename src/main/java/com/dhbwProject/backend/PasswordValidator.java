package com.dhbwProject.backend;

import com.vaadin.data.validator.AbstractValidator;

public class PasswordValidator extends AbstractValidator<String>{
	private static final long serialVersionUID = 1L;

	public PasswordValidator() {
		super("Passwort oder Benutzername ist fehlerhaft!");
	}

	@Override
	protected boolean isValidValue(String value) {
        if (value != null&& (value.length() <3)) 
            return false;
        return true;
	}

	@Override
	public Class<String> getType() {
		return String.class;
	}

}
