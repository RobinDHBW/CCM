package com.dhbwProject.benutzer;

import com.vaadin.server.FontAwesome;
import com.vaadin.server.UserError;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.VerticalLayout;

public class BenutzerAnlage extends CustomComponent {

	private BenutzerFields fields;
	private Button btnErstellen;

	private VerticalLayout vlLayout;
	
	public BenutzerAnlage(){
		this.fields = new BenutzerFields();
		this.initCreateButton();
		this.initLayout();
	}
	


	private void initCreateButton() {
		this.btnErstellen = new Button();
		this.btnErstellen.setIcon(FontAwesome.PLUS);
		this.btnErstellen.setCaption("Erstellen");
		this.btnErstellen.addClickListener(listener ->{
			//Erfolgt später
			createBenutzer();
		});
		
		this.fields.addComponent(btnErstellen);
		
	}
	
	private void initLayout(){
		this.vlLayout = new VerticalLayout(this.fields);
		this.vlLayout.setSizeFull();
		this.vlLayout.setSpacing(true);
		this.vlLayout.setMargin(new MarginInfo(true, true, true, true));
		this.vlLayout.setComponentAlignment(this.fields, Alignment.TOP_LEFT);
		this.setCompositionRoot(vlLayout);
		
	}
	
	public void createBenutzer() {
		if (fields.getVorname().equals("")) {
			fields.getTfVorname().setComponentError(new UserError("Vorname eingeben"));
		} else {
			fields.getTfVorname().setComponentError(null);
		}
			
		if (fields.getNachname().equals("")) {
			fields.getTfNachname().setComponentError(new UserError("Nachname eingeben"));
		} else {
			fields.getTfNachname().setComponentError(null);
		}
//		if (beruf.getValue().equals("")) {
//			beruf.setComponentError(new UserError("Beruf eingeben"));
//		} else {
//			beruf.setComponentError(null);
//		}
		if (!fields.getVorname().equals("") && !fields.getNachname().equals("") /*&& !beruf.getValue().equals("")*/) {
			Notification.show("Der Benutzer wurde angelegt",
                    Type.TRAY_NOTIFICATION);
		}
//		else {
//			fields.setComponentError(new UserError("Alle Felder ausfüllen"));
//		}
	}

}
