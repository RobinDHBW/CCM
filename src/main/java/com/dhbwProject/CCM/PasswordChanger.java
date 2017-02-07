package com.dhbwProject.CCM;

import com.dhbwProject.backend.CCM_Constants;
import com.dhbwProject.backend.PasswordHasher;
import com.dhbwProject.backend.PasswordValidation;
import com.dhbwProject.backend.dbConnect;
import com.dhbwProject.backend.beans.Benutzer;
import com.vaadin.data.validator.NullValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

public class PasswordChanger extends Window { 
		private static final long serialVersionUID = 1L;
		private boolean isLoggedIn;
		private boolean changeResult = false;
		private String validationMessage = "Passwort muss zwischen 5 und 20 Zeichen sein!";
		
		
		private dbConnect dbConnection;
		private TextField tfUser;
		private PasswordField pwFieldOld;
		private PasswordField pwFieldNew1;
		private PasswordField pwFieldNew2;
		private Button btnChange;
		
		private VerticalLayout vlFields;
		private VerticalLayout vlLayout;
		
		public PasswordChanger(boolean isLoggedIn){
			this.center();
//			this.setWidth("350px");
//			this.setHeight("600px");
			this.setClosable(true);
			this.setModal(true);
			this.setResizable(true);
			this.setCaptionAsHtml(true);
			this.setCaption("<center><h3>Passwort ändern</h3></center>");
			this.isLoggedIn = isLoggedIn;
			this.dbConnection = (dbConnect)VaadinSession.getCurrent().getSession().getAttribute(CCM_Constants.SESSION_VALUE_CONNECTION);
			this.initVlLayout();
			Responsive.makeResponsive(this);
		}
		
		private void initFieldUser(){
			this.tfUser = new TextField();
			this.tfUser.setCaption("Benutername");
			this.tfUser.setWidth("300px");
			this.tfUser.setRequired(true);
			Responsive.makeResponsive(tfUser);
		}
		
		private void initpwFieldOld(){
			this.pwFieldOld = new PasswordField("Altes Passwort:");
			this.pwFieldOld.setWidth("300px");
			this.pwFieldOld.setRequired(true);
			this.pwFieldOld.setValue("");
			this.pwFieldOld.setNullRepresentation("");
			Responsive.makeResponsive(pwFieldOld);
		}
		
		private void initpwFieldNew1(){
			this.pwFieldNew1 = new PasswordField("Neues Passwort:");
			this.pwFieldNew1.setWidth("300px");
			this.pwFieldNew1.setRequired(true);
			this.pwFieldNew1.setValue("");
			this.pwFieldNew1.setNullRepresentation("");
//			this.pwFieldNew1.addValidator(new NullValidator(validationMessage, false));
			this.pwFieldNew1.addValidator(new StringLengthValidator(validationMessage, 5, 20, false));
			Responsive.makeResponsive(pwFieldNew1);
		}
		
		private void initpwFieldNew2(){
			this.pwFieldNew2 = new PasswordField("Neues Passwort wiederholen:");
			this.pwFieldNew2.setWidth("300px");
			this.pwFieldNew2.setRequired(true);
			this.pwFieldNew2.setValue("");
			this.pwFieldNew2.setNullRepresentation("");
//			this.pwFieldNew2.addValidator(new NullValidator(validationMessage, false));
			this.pwFieldNew2.addValidator(new StringLengthValidator(validationMessage, 5, 20, false));
			Responsive.makeResponsive(pwFieldNew2);
		}
		
		private void initbtnChange(){
			this.btnChange = new Button("Passwort ändern");
			this.btnChange.setIcon(FontAwesome.CHECK);
			this.btnChange.setStyleName(ValoTheme.BUTTON_ICON_ALIGN_RIGHT);
			this.btnChange.setClickShortcut(KeyCode.ENTER);
			Responsive.makeResponsive(btnChange);
			this.btnChange.addClickListener(listener ->{
				Notification message = new Notification("");
				message.setStyleName(ValoTheme.NOTIFICATION_FAILURE);
				message.setPosition(Position.TOP_CENTER);
				if(!this.pwFieldNew1.isValid() && !this.pwFieldNew2.isValid()){
					message.setCaption(validationMessage);
					message.show(Page.getCurrent());
					return;
				}
				
				if(this.pwFieldNew1.getValue().matches(this.pwFieldNew2.getValue())){
					if(isLoggedIn)
						this.changePasswordLoggedIn();
					else
						this.changePasswordNotLoggedIn();
				}else{
					this.pwFieldNew1.clear();
					this.pwFieldNew2.clear();
					message.setCaption("Ihre neues Passwort stimmt nicht überein!");
					message.show(Page.getCurrent());
				}
			});	
		}
		
		private void initVlFields(){
			this.initFieldUser();
			this.initpwFieldOld();
		    this.initpwFieldNew1();
		    this.initpwFieldNew2();
		    this.initbtnChange();
		    
		    if(isLoggedIn)
		    	this.vlFields = new VerticalLayout(this.pwFieldOld, this.pwFieldNew1, this.pwFieldNew2, this.btnChange);
		    else
		    	this.vlFields = new VerticalLayout(this.tfUser, this.pwFieldOld, this.pwFieldNew1, this.pwFieldNew2, this.btnChange);
		    
		    this.vlFields.setComponentAlignment(this.btnChange, Alignment.MIDDLE_CENTER);
	        this.vlFields.setSpacing(true);
	        this.vlFields.setMargin(new MarginInfo(true, false, true, false));	// Seitliche Abstände
	        this.vlFields.setSizeUndefined();	
		}
		
		private void initVlLayout(){
			this.initVlFields();
	        this.vlLayout = new VerticalLayout(this.vlFields);
	        this.vlLayout.setSizeFull();
	        this.vlLayout.setComponentAlignment(this.vlFields, Alignment.MIDDLE_CENTER);
	        Responsive.makeResponsive(vlLayout);
	        this.setContent(this.vlLayout);	
		}
		
		private void changePasswordLoggedIn(){
			Benutzer bUser = (Benutzer)VaadinSession.getCurrent().getSession().getAttribute(CCM_Constants.SESSION_VALUE_USER);
			if(PasswordValidation.isValidPassword(this.dbConnection, bUser.getId(), this.pwFieldOld.getValue())){
				try{
					changeResult = dbConnection.changePassword(PasswordHasher.md5(this.pwFieldNew1.getValue()), bUser);
					this.close();
				}catch(Exception e){
					changeResult = false;
					e.printStackTrace();
				}
			}else{
				this.pwFieldOld.clear();
				Notification message = new Notification("Passwort oder Benutzername ist falsch!");
				message.setStyleName(ValoTheme.NOTIFICATION_FAILURE);
				message.setPosition(Position.TOP_CENTER);
				message.show(Page.getCurrent());
			}
		}
		
		private void changePasswordNotLoggedIn(){
			Benutzer bUser;
			if(PasswordValidation.isValidPassword(this.dbConnection, this.tfUser.getValue(), this.pwFieldOld.getValue())){
				try{
					bUser = this.dbConnection.getBenutzerById(this.tfUser.getValue());
					changeResult = dbConnection.changePassword(PasswordHasher.md5(this.pwFieldNew1.getValue()), bUser);
					this.close();
				}catch(Exception e){
					changeResult = false;
					e.printStackTrace();
				}
			}else{
				this.pwFieldOld.clear();
				Notification message = new Notification("Passwort oder Benutzername ist falsch!");
				message.setStyleName(ValoTheme.NOTIFICATION_FAILURE);
				message.setPosition(Position.TOP_CENTER);
				message.show(Page.getCurrent());
			}	
		}
		
		public boolean getResult(){
			return this.changeResult;
		}
		
	}
	

