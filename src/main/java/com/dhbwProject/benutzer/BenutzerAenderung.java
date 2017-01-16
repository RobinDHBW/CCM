package com.dhbwProject.benutzer;

import java.util.LinkedList;

import com.dhbwProject.backend.DummyDataManager;
import com.dhbwProject.backend.dbConnect;
import com.dhbwProject.backend.beans.Benutzer;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.VerticalLayout;

public class BenutzerAenderung extends CustomComponent {
	private static final long serialVersionUID = 1L; // dass der gelb unterstrichene Klassenname nicht nervt
	
	
	private BenutzerFields fields;
	private Button btnAendern;
	private Button lookup;
	private DummyDataManager dummyData = new DummyDataManager();
	private dbConnect dbConnect;
	private Benutzer b;
	
	private VerticalLayout vlLayout;
	
	
	public BenutzerAenderung(){
		this.fields = new BenutzerFields();
		this.fields.enableFields(false);
		this.dbConnect = new dbConnect();
		this.initLookup();
		this.initCreateButton();
		this.initLayout();
	}
	
	private void initLookup() {
		lookup = new Button("Benutzer auswählen");
		lookup.setIcon(FontAwesome.ANGLE_DOUBLE_RIGHT);
		lookup.addClickListener(e -> {
			LookupBenutzer bLookup = new LookupBenutzer(dummyData);
			bLookup.addCloseListener(event -> {
				b = bLookup.getSelection();
				fields.setVorname(b);
				fields.setNachname(b);
				fields.enableFields(true);
				btnAendern.setEnabled(true);
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
			//Später wird mehr erfolgen hier
			//Benutzer ("mmayer", "vorname", "nachname", "beruf", "ccmall", studiengänge)
			//id = mmayer
//			Benutzer neu = new Benutzer(null, fields.getVorname(), fields.getNachname(), null, null, null);
//			dbConnect.changeBenutzer(b, neu);
			Notification.show("Die Benutzerdaten wurden geändert",
	                Type.TRAY_NOTIFICATION);
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

