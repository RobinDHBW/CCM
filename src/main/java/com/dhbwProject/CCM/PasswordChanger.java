package com.dhbwProject.CCM;

import com.dhbwProject.backend.CCM_Constants;
import com.dhbwProject.backend.beans.Benutzer;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

public class PasswordChanger extends Window {  	// Soll nur als Windows erscheinen
		private static final long serialVersionUID = 1L;
		
		//Feld mit PUnkten für PW
		private PasswordField pwFieldOld;
		private PasswordField pwFieldNew1;
		private PasswordField pwFieldNew2;
		
		//Button zum BEstätigen der Änderung
		private Button btnChange;
		
		
		//Layout definieren
		private VerticalLayout vlFields;
		private VerticalLayout vlLayout;
		
		//Constructor
		public PasswordChanger(){
			this.center();  //Fenster zentrieren
			this.setWidth("350px"); 	//Breite festlegen
			this.setHeight("600px"); 	//Höhe festlegen
			this.setClosable(true);
			this.setModal(true);
			this.setResizable(true);
			
			this.initVlLayout();  //LAyout initialisieren
			
		}
		
		
		
		
//		Feld für altes Passwort erzeugen
		private void initpwFieldOld(){
			this.pwFieldOld = new PasswordField("Altes Passwort");
			this.pwFieldOld.setWidth("300px");
			this.pwFieldOld.setRequired(true);
//			this.pwFieldOld.addValidator(new PasswordValidator());
			this.pwFieldOld.setValue("");
			this.pwFieldOld.setNullRepresentation("");
		}
		
//		Feld für erstes neues Passwort erzeugen
		private void initpwFieldNew1(){
			this.pwFieldNew1 = new PasswordField("Altes Passwort");
			this.pwFieldNew1.setWidth("300px");
			this.pwFieldNew1.setRequired(true);
//			this.pwFieldNew1.addValidator(new PasswordValidator());
			this.pwFieldNew1.setValue("");
			this.pwFieldNew1.setNullRepresentation("");
		}
		
//		Feld für zweites neues Passwort erzeugen
		private void initpwFieldNew2(){
			this.pwFieldNew2 = new PasswordField("Altes Passwort");
			this.pwFieldNew2.setWidth("300px");
			this.pwFieldNew2.setRequired(true);
//			this.pwFieldNew2.addValidator(new PasswordValidator());
			this.pwFieldNew2.setValue("");
			this.pwFieldNew2.setNullRepresentation("");
		}
		
//		Button ininitialisieren um die Änderung zu bestätigen
		private void initbtnChange(){
			this.btnChange = new Button("Passwort ändern");
			this.btnChange.setIcon(FontAwesome.CHECK);
			this.btnChange.setStyleName(ValoTheme.BUTTON_ICON_ALIGN_RIGHT);
			this.btnChange.setClickShortcut(KeyCode.ENTER);
			this.btnChange.addClickListener(listener ->{
				this.close();	//Wenn der Button geklickt wurde wieder schließen
			});	
		}
		
//		Das Layout für die Felder festlegen
		private void initVlFields(){
//	        Damit die Initialisierung aufgerufen wird
			this.initpwFieldOld();
	        this.initpwFieldNew1();
	        this.initpwFieldNew2();
	        this.initbtnChange();
			
//	        Layout für die Komponenten festlegen
			this.vlFields = new VerticalLayout(this.pwFieldOld, this.pwFieldNew1, this.pwFieldNew2, this.btnChange);
	        this.vlFields.setCaption("Passwort ändern");
	        this.vlFields.setSpacing(true);
	        this.vlFields.setMargin(new MarginInfo(true, false, true, false));	// Seitliche Abstände
	        this.vlFields.setSizeUndefined();	
		}
		
//		Hier werden die Felder reingepackt
		private void initVlLayout(){
			this.initVlFields();
	        this.vlLayout = new VerticalLayout(this.vlFields);
	        this.vlLayout.setSizeFull();
	        this.vlLayout.setComponentAlignment(this.vlFields, Alignment.MIDDLE_CENTER);
//	        this.vlLayout.setStyleName(Reindeer.LAYOUT_BLUE);
	        this.setContent(this.vlLayout);	
		}
		
		}
	

