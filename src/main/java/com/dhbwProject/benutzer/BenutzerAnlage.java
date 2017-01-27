package com.dhbwProject.benutzer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;

import com.dhbwProject.backend.CCM_Constants;
import com.dhbwProject.backend.EMailThread;
import com.dhbwProject.backend.PasswordHasher;
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
		this.btnErstellen.setCaption("Anlegen");
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
		if (fields.getID().equals("")) {
			fields.getTfID().setComponentError(new UserError("ID eingeben"));
		} else {
			fields.getTfID().setComponentError(null);
		}
		
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
		
		if (fields.getBeruf() == null) {
			fields.getCbBeruf().setComponentError(new UserError("Beruf auswählen"));
		} else {
			fields.getCbBeruf().setComponentError(null);
		}
		
		if (fields.getRolle() == null) {
			fields.getCbRolle().setComponentError(new UserError("Rolle auswählen"));
		} else {
			fields.getCbRolle().setComponentError(null);
		}
		
		if (fields.getStudiengang().size() < 1) {
			fields.getLsStudiengang().setComponentError(new UserError("Studiengang auswählen"));
		} else {
			fields.getLsStudiengang().setComponentError(null);
		}
		
		if (fields.getTelefonnummer().equals("")) {
			fields.getTfTelefonnummer().setComponentError(new UserError("Telefonnummer eingeben"));
		} else {
			fields.getTfTelefonnummer().setComponentError(null);
		}
		
		if (fields.getEmail().equals("")) {
			fields.getTfEmail().setComponentError(new UserError("E-Mail eingeben"));
		} else {
			fields.getTfEmail().setComponentError(null);
		}
		
		if (!fields.getID().equals("") && !fields.getVorname().equals("") && !fields.getNachname().equals("")
				&& fields.getBeruf() != null && fields.getRolle() != null && fields.getStudiengang().size() > 0
				&& !fields.getTelefonnummer().equals("") && !fields.getEmail().equals("")) {
			String id = fields.getID();
			Rolle rolle = null;
			try {
				rolle = dbConnect.getRolleByBezeichnung(fields.getRolle());
			} catch (SQLException e1) {
				System.out.println("Fehler bei get RolleByBezeichnung");
				e1.printStackTrace();
			}
			Beruf beruf = null;
			try {
				beruf = dbConnect.getBerufByBezeichnung(fields.getBeruf());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			LinkedList<Studiengang> stg = new LinkedList<Studiengang>();
			for (String stud : fields.getStudiengang()) {
				try {
					stg.add(dbConnect.getStudiengangByBezeichnung(stud));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			Benutzer benutzer = new Benutzer(id, fields.getVorname(), fields.getNachname(), beruf, rolle, stg, fields.getEmail(),
					fields.getTelefonnummer());
			try {
				this.dbConnect.createBenutzer(benutzer);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				dbConnect.createPassword(PasswordHasher.md5("default"), benutzer);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			ArrayList<String> mailAdresse = new ArrayList<String>();
			mailAdresse.add(fields.getEmail());
			String betreff = "CCM Benutzerkonto";
			String inhalt = "Guten Tag " + fields.getVorname() + " "
					+ fields.getNachname() + ",<br><br> Im CRM-System ein Benutzerkonto für Sie angelegt. <br><br>Benutzername: "
					+ fields.getId() + " <br>Passwort: default <br><br> bitte ändern Sie dieses bei der ersten Anmeldung.";
			EMailThread mail = new EMailThread(mailAdresse, betreff, inhalt);
			mail.start();
			
			Notification.show("Der Benutzer wurde angelegt",
                    Type.TRAY_NOTIFICATION);
		}
	}

}
