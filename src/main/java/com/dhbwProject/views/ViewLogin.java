package com.dhbwProject.views;

import java.sql.SQLException;

import com.dhbwProject.CCM.PasswordChanger;
import com.dhbwProject.backend.CCM_Constants;
import com.dhbwProject.backend.PasswordHasher;
import com.dhbwProject.backend.PasswordValidation;
import com.dhbwProject.backend.dbConnect;
import com.dhbwProject.backend.beans.Benutzer;
import com.vaadin.data.validator.AbstractValidator;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class ViewLogin extends CustomComponent implements View{
	private static final long serialVersionUID = 1L;
	private dbConnect dbConnection;
	
	private TextField userField;
	private PasswordField pwField;
	private Button btnLogin;
	private Button btnChangePw;
	
	private VerticalLayout vlFields;
	private VerticalLayout vlLayout;
	
	public ViewLogin(){
		this.dbConnection = (dbConnect)VaadinSession.getCurrent().getSession().getAttribute(CCM_Constants.SESSION_VALUE_CONNECTION);
		this.setSizeFull();
		this.initUserField();
		this.initPwField();
		this.initBtnLogin();
		this.initBtnChangePw();
		this.initVlFields();
		this.initVlLayout();
		this.setStyleName("cclayout");
		Responsive.makeResponsive(this);
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		this.userField.focus();
	}
	
	private void initPwField(){
		this.pwField = new PasswordField("Passwort");
		this.pwField.setWidth("-1px");
		this.pwField.setRequired(true);
//		this.pwField.addValidator(new PasswordValidator());
		this.pwField.setValue("");
		this.pwField.setNullRepresentation("");
		this.pwField.setStyleName("tffield");
		Responsive.makeResponsive(pwField);
	}
	
	private void initUserField(){
		this.userField = new TextField("Benutzer");
		this.userField.setWidth("-1px");
		this.userField.setRequired(true);
		this.userField.setInputPrompt("Ihr Benutzername");	
		this.userField.setStyleName("tffield");
		Responsive.makeResponsive(userField);
	}
	
	private void initBtnLogin(){
		this.btnLogin = new Button();
		this.btnLogin.setIcon(FontAwesome.SIGN_IN);
	//	this.btnLogin.setWidth("100%");
		this.btnLogin.setCaption("Anmelden"); //Beschriftung des Buttons
//		this.btnLogin.setStyleName(ValoTheme.BUTTON_ICON_ALIGN_RIGHT);
		this.btnLogin.setClickShortcut(KeyCode.ENTER);
		this.btnLogin.setStyleName("login");
		Responsive.makeResponsive(btnLogin);
		this.btnLogin.addClickListener(listener ->{
			if (!this.userField.isValid())
	            return;
//			else if(this.pwField.isValid()){
			else if(PasswordValidation.isValidPassword(this.dbConnection, userField.getValue(), pwField.getValue())){
	            try {
	            	VaadinSession.getCurrent().lock();
	            	VaadinSession.getCurrent().getSession().setAttribute(CCM_Constants.SESSION_VALUE_USER, this.dbConnection.getBenutzerById(this.userField.getValue()));
	            } catch (SQLException e) {
					e.printStackTrace();
				}finally{
					VaadinSession.getCurrent().unlock();
				}
	        	this.getUI().getNavigator().navigateTo(CCM_Constants.VIEW_NAME_START);
	        } else {
	            this.pwField.setValue(null);
	            this.pwField.focus();
	            Notification message = new Notification("Benutzername oder Passwort ist falsch");
	            message.setStyleName(ValoTheme.NOTIFICATION_FAILURE);
	            message.setPosition(Position.TOP_CENTER);
	            message.show(Page.getCurrent());
	        }	
		});	
	}
	
	private void initBtnChangePw(){
		this.btnChangePw = new Button();
//		this.btnChangePw.setWidth("100%");
//		this.btnChangePw.setCaption("Passwort ändern");
		this.btnChangePw.setStyleName("login");
		this.btnChangePw.setIcon(FontAwesome.COGS);
		Responsive.makeResponsive(btnChangePw);
		this.btnChangePw.addClickListener(click ->{
			PasswordChanger changer = new PasswordChanger(false);
			getUI().addWindow(changer);
		});
	}
	
	private void initVlFields(){
		HorizontalLayout hlButtons = new HorizontalLayout(this.btnLogin, this.btnChangePw);
		hlButtons.setSpacing(true);
		hlButtons.setStyleName("hllogin");
	//	hlButtons.setWidth("300px");
        this.vlFields = new VerticalLayout(this.userField, this.pwField, hlButtons);
        this.vlFields.setCaption("Bitte melden Sie sich am System an");
        this.vlFields.setSpacing(true);
        this.vlFields.setStyleName("vlfelder");
        this.vlFields.setMargin(new MarginInfo(true, false, true, false));
        this.vlFields.setSizeUndefined();
        Responsive.makeResponsive(hlButtons);
	}
	
	private void initVlLayout(){
        this.vlLayout = new VerticalLayout(this.vlFields);
        this.vlLayout.setSizeFull();
        this.vlLayout.setComponentAlignment(this.vlFields, Alignment.MIDDLE_CENTER);
        this.setCompositionRoot(this.vlLayout);	
        Responsive.makeResponsive(vlLayout);
	}
	
//	private class PasswordValidator extends AbstractValidator<String>{
//		private static final long serialVersionUID = 1L;
//
//		public PasswordValidator() {
//			super("Passwort oder Benutzername ist fehlerhaft!");
//		}
//
//		@Override
//		protected boolean isValidValue(String value) {
//			//-Prüfung Benutzer
//			Benutzer bUser;
//			if(userField.getValue() == null || userField.getValue().length() <1)
//				return false;
//			else
//				try{
//					bUser = dbConnection.getBenutzerById(userField.getValue());
//				}catch(Exception e){
//					return false;
//				}
//			
//			//-Prüfung Passwort
//			if (value == null || value.length() <1) 
//				return false;
//			else
//				try{
//					return dbConnection.checkPassword(PasswordHasher.md5(value), bUser);
//				} catch (Exception e) {
//					return false;
//				}
//		}
//
//		@Override
//		public Class<String> getType() {
//			return String.class;
//		}
//	}

}
