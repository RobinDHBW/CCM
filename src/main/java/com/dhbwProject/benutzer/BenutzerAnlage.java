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
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class BenutzerAnlage extends Window {

	private BenutzerFields fields;
	private Button btnErstellen;
	private dbConnect dbConnect;
	private Benutzer neu;

	private VerticalLayout vlLayout;
	
	public BenutzerAnlage(){
		this.fields = new BenutzerFields();
		this.dbConnect = (dbConnect)VaadinSession.getCurrent().getSession().getAttribute(CCM_Constants.SESSION_VALUE_CONNECTION);
		
		this.center();
		this.setWidth("410px");
		this.setHeight("500px");
		this.setModal(true);
		this.setCaptionAsHtml(true);
		this.setCaption("<center><h3>Benutzer hinzufügen</h3></center>");
		this.setContent(initLayout());
	}
	


	private void initCreateButton() {
		this.btnErstellen = new Button();
		this.btnErstellen.setIcon(FontAwesome.PLUS);
		this.btnErstellen.setCaption("Anlegen");
		this.btnErstellen.addClickListener(listener ->{
			createBenutzer();
		});
		
		this.fields.addComponent(btnErstellen);
		
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
		return p;
		
	}
	
	public void createBenutzer() {
		if (this.fields.checkFields(fields)) {
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
			Benutzer benutzer = new Benutzer(id, fields.getVorname(), fields.getNachname(), beruf, rolle, stg, fields.getMail(),
					fields.getTelefonnummer());
			try {
				this.neu = this.dbConnect.createBenutzer(benutzer);
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
			mailAdresse.add(fields.getMail());
			String betreff = "CCM Benutzerkonto";
			String inhalt = "Guten Tag " + fields.getVorname() + " "
					+ fields.getNachname() + ",<br><br> Im CCM-System wurde ein Benutzerkonto für Sie angelegt. <br><br>Benutzername: "
					+ fields.getID() + " <br>Passwort: default <br><br> Bitte ändern Sie das Passwort bei der ersten Anmeldung.";
			try {
			EMailThread mail = new EMailThread(mailAdresse, betreff, inhalt);
			mail.start();
			} catch (Exception e) {
				Notification.show("E-Mail Adresse nicht korrekt", Type.ERROR_MESSAGE);
				e.printStackTrace();
			}
			
			this.close();
			
			Notification.show("Der Benutzer wurde angelegt",
                    Type.TRAY_NOTIFICATION);
		}
	}
	
	protected Benutzer getBenutzerNeu() {
		return this.neu;
	}

}
