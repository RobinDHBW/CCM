package com.dhbwProject.views;

import com.dhbwProject.backend.CCM_Constants;
import com.dhbwProject.backend.PasswordValidator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

public class ViewLogin extends CustomComponent implements View{
	private static final long serialVersionUID = 1L;
	
	private TextField userField;
	private PasswordField pwField;
	private Button btnLogin;
	
	private VerticalLayout vlFields;
	private VerticalLayout vlLayout;
	
	

	public ViewLogin(){
		this.setSizeFull();
		this.initUserField();
		this.initPwField();
		this.initBtnLogin();
		this.initVlFields();
		this.initVlLayout();
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		this.userField.focus();
		
	}
	
	private void initPwField(){
		this.pwField = new PasswordField("Passwort");
		this.pwField.setWidth("300px");
		this.pwField.setRequired(true);
		this.pwField.addValidator(new PasswordValidator());
		this.pwField.setValue("");
		this.pwField.setNullRepresentation("");
	}
	
	private void initUserField(){
		this.userField = new TextField("Benutzer");
		this.userField.setWidth("300px");
		this.userField.setRequired(true);
		this.userField.setInputPrompt("Ihr Benutzername");	
	}
	
	private void initBtnLogin(){
		this.btnLogin = new Button("Anmelden");
		this.btnLogin.addClickListener(listener ->{
			if (!this.userField.isValid() || !this.pwField.isValid())
	            return;
			/*	An dieser Stelle erfolgt spaeter ein Backendzugriff
			 *	um einen Datenbankabgleich zu tätigen, anstatt einen
			 *	dummy-user abgleich zu taetigen*/
	        boolean isValid = this.userField.getValue().equals("Alpha")
	                && this.pwField.getValue().equals("123");

	        if (isValid) {
	            this.getSession().setAttribute("user", this.userField.getValue());
	            this.getUI().getNavigator().navigateTo(CCM_Constants.VIEW_NAME_START);
	        } else {
	            this.pwField.setValue(null);
	            this.pwField.focus();
	        }	
		});	
	}
	
	private void initVlFields(){
        this.vlFields = new VerticalLayout(this.userField, this.pwField, this.btnLogin);
        this.vlFields.setCaption("Bitte melden Sie sich am System an");
        this.vlFields.setSpacing(true);
        this.vlFields.setMargin(new MarginInfo(true, false, true, false));
        this.vlFields.setSizeUndefined();	
	}
	
	private void initVlLayout(){
        this.vlLayout = new VerticalLayout(this.vlFields);
        this.vlLayout.setSizeFull();
        this.vlLayout.setComponentAlignment(this.vlFields, Alignment.MIDDLE_CENTER);
        this.vlLayout.setStyleName(Reindeer.LAYOUT_BLUE);
        this.setCompositionRoot(this.vlLayout);	
	}

}
