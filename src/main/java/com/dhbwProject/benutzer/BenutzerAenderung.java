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
import com.vaadin.server.Responsive;
import com.vaadin.server.UserError;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class BenutzerAenderung extends Window {
	private static final long serialVersionUID = 1L;
	
	
	private BenutzerFields fields;
	private Button btnAendern;
	private Button lookup;
	private dbConnect dbConnect;
	private Benutzer b;
	
	private VerticalLayout vlLayout;
	
	
	public BenutzerAenderung(Benutzer b){
		this.b = b;
		this.fields = new BenutzerFields();
		this.fields.initChPassword();
		this.fields.getTfID().setEnabled(false);
//		this.fields.enableFields(false);
		this.dbConnect = (dbConnect)VaadinSession.getCurrent().getSession().getAttribute(CCM_Constants.SESSION_VALUE_CONNECTION);
		
		this.center();
		this.setWidth("450px");
		this.setHeight("500px");
		this.setModal(true);
		this.setCaptionAsHtml(true);
		this.setCaption("<center><h3>Benutzer ändern</h3></center>");
		this.setContent(initLayout());
		Responsive.makeResponsive(this);
		
		
		if (b != null) {
			fields.setID(b);
			fields.setVorname(b);
			fields.setNachname(b);
			fields.setBeruf(b);
			fields.setRolle(b);
			fields.setStudiengang(b);
			fields.setEmail(b);
			fields.setTelefonnummer(b);
//			fields.enableFields(true);
			btnAendern.setEnabled(true);
			}
		
	}
	private void initCreateButton() {
		this.btnAendern = new Button();
		this.btnAendern.setIcon(FontAwesome.PENCIL);
		this.btnAendern.setCaption("Ändern");
		this.btnAendern.setEnabled(false);
		Responsive.makeResponsive(btnAendern);
		this.btnAendern.addClickListener(listener ->{
			
			
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
				fields.getTaStudiengang().setComponentError(new UserError("Studiengang auswählen"));
			} else {
				fields.getTaStudiengang().setComponentError(null);
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
			Benutzer neu = new Benutzer(fields.getID(), fields.getVorname(), fields.getNachname(), beruf, rolle, stg, fields.getEmail(),
					fields.getTelefonnummer());
			try {
				dbConnect.changeBenutzer(b, neu);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Notification.show("Die Benutzerdaten wurden geändert",
	                Type.TRAY_NOTIFICATION);
			
			if (this.fields.getChPassword().getValue() == true) {
					try {
						dbConnect.changePassword(PasswordHasher.md5("default"), neu);
						Notification.show("Das Passwort wurde auf default zurückgesetzt");
						
						ArrayList<String> mailAdresse = new ArrayList<String>();
						mailAdresse.add(fields.getEmail());
						String betreff = "CCM Benutzerkonto";
						String inhalt = "Guten Tag " + fields.getVorname() + " "
								+ fields.getNachname() + ",<br><br> Ihr Passwort im CRM-System wurde auf 'default' zurückgesetzt. <br><br>Benutzername: "
								+ fields.getID() + "<br><br> Bitte ändern Sie das Passwort bei der nächtsen Anmeldung.";
						try {
						EMailThread mail = new EMailThread(mailAdresse, betreff, inhalt);
						mail.start();
						} catch (Exception e) {
							Notification.show("E-Mail Adresse nicht korrekt", Type.ERROR_MESSAGE);
							e.printStackTrace();
						}
						
					} catch (SQLException e) {
						Notification.show("Das Passwort konnte nicht zurückgesetzt werden");
					}
					
				}
			}
			this.close();
		});
		
		this.fields.addComponent(btnAendern);
	}
	
	private Panel initLayout(){
		this.initCreateButton();
		this.vlLayout = new VerticalLayout(this.fields);
		this.vlLayout.setSizeFull();
		this.vlLayout.setSpacing(true);
		this.vlLayout.setMargin(new MarginInfo(true, true, true, true));
		this.vlLayout.setComponentAlignment(this.fields, Alignment.TOP_LEFT);
		Panel p = new Panel();
		p.setContent(vlLayout);
		Responsive.makeResponsive(vlLayout);
		Responsive.makeResponsive(p);
		return p;
	}
}

