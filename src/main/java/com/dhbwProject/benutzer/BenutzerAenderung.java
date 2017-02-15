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
		this.fields.initChLoeschen();
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
		
		
		if (b != null) {
			fields.setID(b);
			fields.setVorname(b);
			fields.setNachname(b);
			fields.setBeruf(b);
			fields.setRolle(b);
			fields.setStudiengang(b);
			fields.setMail(b);
			fields.setTelefonnummer(b);
//			fields.enableFields(true);
//			btnAendern.setEnabled(true);
			}
		
	}
	private void initCreateButton() {
		this.btnAendern = new Button();
		this.btnAendern.setIcon(FontAwesome.PENCIL);
		this.btnAendern.setCaption("Ändern");
		this.btnAendern.setEnabled(false);
		this.btnAendern.addClickListener(listener ->{
			
			if(this.fields.checkFields(fields)) {
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
			Benutzer neu = new Benutzer(fields.getID(), fields.getVorname(), fields.getNachname(), beruf, rolle, stg, fields.getMail(),
					fields.getTelefonnummer());
			try {
				dbConnect.changeBenutzer(b, neu);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Notification.show("Die Benutzerdaten wurden geändert",
	                Type.TRAY_NOTIFICATION);
			
			if (this.fields.getPassword() == true) {
					try {
						dbConnect.changePassword(PasswordHasher.md5("default"), neu);
						Notification.show("Das Passwort wurde auf default zurückgesetzt");
						
						ArrayList<String> mailAdresse = new ArrayList<String>();
						mailAdresse.add(fields.getMail());
						String betreff = "CCM Benutzerkonto";
						String inhalt = "Guten Tag " + fields.getVorname() + " "
								+ fields.getNachname() + ",<br><br> Ihr Passwort im CCM-System wurde auf 'default' zurückgesetzt. <br><br>Benutzername: "
								+ fields.getID() + "<br><br> Bitte ändern Sie das Passwort bei der nächsten Anmeldung.";
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
			
			if (this.fields.getInactive()) {
				try {
					dbConnect.deleteBenutzer(b);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			this.fields.enableFields(false);
			this.close();
		});
		
		this.fields.addComponent(btnAendern);
	}
	
	private void initInactiveButton() {
		Button inactive = new Button("Benutzer wieder aktivieren");
		inactive.setIcon(FontAwesome.CHECK);
		inactive.addClickListener(e -> {
			try {
				dbConnect.activateBenutzer(b);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			this.fields.enableFields(true);
			this.close();
			
		});
		this.fields.addComponent(inactive);
	}
	
	private Panel initLayout(){
		if (!b.getInaktiv()) {
			this.initCreateButton();
			btnAendern.setEnabled(true);
			this.fields.enableFields(true);
		} else {
			this.initInactiveButton();
			this.fields.enableFields(false);			
		}
		this.vlLayout = new VerticalLayout(this.fields);
		this.vlLayout.setSizeFull();
		this.vlLayout.setSpacing(true);
		this.vlLayout.setMargin(new MarginInfo(true, true, true, true));
		this.vlLayout.setComponentAlignment(this.fields, Alignment.TOP_LEFT);
		Panel p = new Panel();
		p.setContent(vlLayout);
		return p;
	}
}

