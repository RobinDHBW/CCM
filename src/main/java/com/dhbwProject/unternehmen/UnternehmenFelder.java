package com.dhbwProject.unternehmen;

import com.dhbwProject.backend.beans.Unternehmen;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.Select;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class UnternehmenFelder extends VerticalLayout {
	private static final long serialVersionUID = 1L; // dass net gelb unterstreicht
	
	private TextField tfUnternehmenname;
	//private ComboBox cbKennzeichen;
	private OptionGroup ogKennzeichenauswahl;
	private TextField tfStrasse;
	private TextField tfHausnummer;
	private TextField tfPLZ;
	private TextField tfOrt;
	private TextField tfNameAnsprechpartner;
	private TextField tfVornameAnsprechpartner;
	private TextField tfTelefonnummer;
	private TextField tfEmail;

	
	
	public UnternehmenFelder(){
		this.setSizeUndefined();
		this.setSpacing(true);
		this.initUnternehmenname();
		this.initKennzeichen(); // Combobox, restliche sind Textfelder
		//this.initOptionGroup();
		this.initStrasse();
		this.initHausnummer();
		this.initPLZ();
		this.initOrt();
		this.initVornameAnsprechpartner();
		this.initNameAnsprechpartner();
		this.initTelefonnummer();
		this.initEmail();
		this.setMargin(new MarginInfo(true, true, true, true)); // Abstand der Felder
		
	}
	
	private void initUnternehmenname(){
		this.tfUnternehmenname = new TextField();
		this.tfUnternehmenname.setInputPrompt("Name des Unternehmens");
		this.setWidth("300px");
		this.addComponent(tfUnternehmenname);
	}
	
	private void initKennzeichen(){ // intialisiert als Combobox, da nur A und B Unternehmen gibt
		//this.cbKennzeichen = new ComboBox("Kennzeichen");
		this.setWidth("300px");
		//this.addComponent(cbKennzeichen);
		this.ogKennzeichenauswahl = new OptionGroup("Kennzeichen");
		this.ogKennzeichenauswahl.addItem("Permiumpartner (A)");
		this.ogKennzeichenauswahl.addItem("Sonstiger Partner (B)");
		this.addComponent(ogKennzeichenauswahl);
	}
	
	//private void initOptionGroup(){
		//OptionGroup single = new OptionGroup();
		//single.addItem("Permiumpartner (A)");
		//single.addItem("Sonstiger Partner (B)");
//	}
	
	private void initStrasse(){
		this.tfStrasse = new TextField();
		this.tfStrasse.setInputPrompt("Straße");
		this.setWidth("300px");
		this.addComponent(tfStrasse);
	}
	
	private void initHausnummer(){
		this.tfHausnummer = new TextField();
		this.tfHausnummer.setInputPrompt("Hausnummer");
		this.setWidth("300px");
		this.addComponent(tfHausnummer);
	}
	
	private void initPLZ(){
		this.tfPLZ = new TextField();
		this.tfPLZ.setInputPrompt("Postleitzahl");
		this.setWidth("300px");
		this.addComponent(tfPLZ);
	}
	
	private void initOrt(){
		this.tfOrt = new TextField();
		this.tfOrt.setInputPrompt("Ort");
		this.setWidth("300px");
		this.addComponent(tfOrt);
	}
	
	private void initVornameAnsprechpartner(){
		this.tfVornameAnsprechpartner = new TextField();
		this.tfVornameAnsprechpartner.setInputPrompt("Vorname des Ansprechpartners");
		this.setWidth("300px");
		this.addComponent(tfVornameAnsprechpartner);
	}
	
	private void initNameAnsprechpartner(){
		this.tfNameAnsprechpartner = new TextField();
		this.tfNameAnsprechpartner.setInputPrompt("Nachname des Ansprechpartners");
		this.setWidth("300px");
		this.addComponent(tfNameAnsprechpartner);
	}
	
	private void initTelefonnummer(){
		this.tfTelefonnummer = new TextField();
		this.tfTelefonnummer.setInputPrompt("Telefonnummer");
		this.setWidth("300px");
		this.addComponent(tfTelefonnummer);
	}
	
	private void initEmail(){
		this.tfEmail = new TextField();
		this.tfEmail.setInputPrompt("E-Mail");
		this.setWidth("300px");
		this.addComponent(tfEmail);
		this.tfEmail.setIcon(FontAwesome.AT);
	}
	
	public void setUnternehmen (Unternehmen u){
		this.tfUnternehmenname.setValue(u.getName());
	}
	
	public String getUnternehmen(){
		return this.tfUnternehmenname.getValue();
	}
}
