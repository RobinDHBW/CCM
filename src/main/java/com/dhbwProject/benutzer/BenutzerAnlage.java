package com.dhbwProject.benutzer;

import java.sql.SQLException;
import java.util.LinkedList;

import com.dhbwProject.backend.CCM_Constants;
import com.dhbwProject.backend.dbConnect;
import com.dhbwProject.backend.beans.Benutzer;
import com.dhbwProject.backend.beans.Beruf;
import com.dhbwProject.backend.beans.Rolle;
import com.dhbwProject.backend.beans.Studiengang;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.UserError;
import com.vaadin.server.VaadinSession;
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
	private dbConnect dbConnect;

	private VerticalLayout vlLayout;
	
	public BenutzerAnlage(){
		this.fields = new BenutzerFields();
		this.dbConnect = (dbConnect)VaadinSession.getCurrent().getSession().getAttribute(CCM_Constants.SESSION_VALUE_CONNECTION);
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
			String id = fields.getVorname().substring(0, 1) + fields.getNachname();
			
			Rolle rolle = null;
			try {
				rolle = dbConnect.getRolleByBezeichnung("ccm_all");
			} catch (SQLException e1) {
				System.out.println("Fehler bei get RolleByBezeichnung");
				e1.printStackTrace();
			}
			Beruf beruf = null;
			try {
				beruf = dbConnect.getBerufByBezeichnung("Studiengangsleiter");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			LinkedList<Studiengang> stg = new LinkedList<Studiengang>();
			stg.add(new Studiengang(0, "Wirtschaftsinformatik"));
			Benutzer benutzer = new Benutzer(id, fields.getVorname(), fields.getNachname(), beruf, rolle, stg);
			try {
				this.dbConnect.createBenutzer(benutzer);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			Notification.show("Der Benutzer wurde angelegt",
                    Type.TRAY_NOTIFICATION);
		}
	}

}
