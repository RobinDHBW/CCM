package com.dhbwProject.benutzer;

import java.sql.SQLException;
import java.util.LinkedList;

import com.dhbwProject.backend.CCM_Constants;
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

public class BenutzerAenderung extends CustomComponent {
	private static final long serialVersionUID = 1L;
	
	
	private BenutzerFields fields;
	private Button btnAendern;
	private Button lookup;
	private dbConnect dbConnect;
	private Benutzer b;
	
	private VerticalLayout vlLayout;
	
	
	public BenutzerAenderung(){
		this.fields = new BenutzerFields();
		this.fields.enableFields(false);
		this.fields.initChPassword();
		this.dbConnect = (dbConnect)VaadinSession.getCurrent().getSession().getAttribute(CCM_Constants.SESSION_VALUE_CONNECTION);
		this.initLookup();
		this.initCreateButton();
		this.initLayout();
	}
	
	private void initLookup() {
		lookup = new Button("Benutzer auswählen");
		lookup.setIcon(FontAwesome.ANGLE_DOUBLE_RIGHT);
		lookup.addClickListener(e -> {
			LookupBenutzer bLookup = new LookupBenutzer();
			bLookup.addCloseListener(event -> {
				b = bLookup.getSelection();
				if (b != null) {
				fields.setID(b);
				fields.setVorname(b);
				fields.setNachname(b);
				fields.setBeruf(b);
				fields.setRolle(b);
//				fields.setStudiengang(b);
				fields.setEmail(b);
				fields.setTelefonnummer(b);
				fields.enableFields(true);
				btnAendern.setEnabled(true);
				}
			});
			this.getUI().addWindow(bLookup);
		});
		
	}
	private void initCreateButton() {
		this.btnAendern = new Button();
		this.btnAendern.setIcon(FontAwesome.PENCIL);
		this.btnAendern.setCaption("Ändern");
		this.btnAendern.setEnabled(false);
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
					} catch (SQLException e) {
						Notification.show("Das Passwort konnte nicht zurückgesetzt werden");
					}
					
				}
			}
		});
		
		this.fields.addComponent(btnAendern);
	}
	
	private void initLayout(){
		this.vlLayout = new VerticalLayout(lookup, this.fields);
		this.vlLayout.setSizeFull();
		this.vlLayout.setSpacing(true);
		this.vlLayout.setMargin(new MarginInfo(true, true, true, true));
		this.vlLayout.setComponentAlignment(this.fields, Alignment.TOP_LEFT);
		this.setCompositionRoot(vlLayout);
	}
}

