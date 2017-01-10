package com.dhbwProject.unternehmen;

import com.dhbwProject.backend.beans.Unternehmen;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class UnternehmenFelder extends VerticalLayout {
	private static final long serialVersionUID = 1L; // dass net gelb unterstreicht
	
	private TextField tfUnternehmenname;
	private ComboBox cbKennzeichen;
	private TextField tfStrasse;
	private TextField tfHausnummer;
	private TextField tfPLZ;
	private TextField tfOrt;
	private TextField tfNameAnsprechpartner;
	private TextField tfVornameAnsprechpartner;

	
	
	public UnternehmenFelder(){
		this.setSizeUndefined();
		this.setSpacing(true);
		this.initUnternehmenname();
		this.initKennzeichen(); // Combobox, restliche sind Textfelder
		this.initStrasse();
		this.initHausnummer();
		this.initPLZ();
		this.initOrt();
		this.initVornameAnsprechpartner();
		this.initNameAnsprechpartner();
		
	}
	
	private void initUnternehmenname(){
		this.tfUnternehmenname = new TextField();
		this.tfUnternehmenname.setInputPrompt("Name des Unternehmens");
		this.setWidth("300px");
		this.addComponent(tfUnternehmenname);
	}
	
	private void initKennzeichen(){ // intialisiert als Combobox, da nur A und B Unternehmen gibt
		this.cbKennzeichen = new ComboBox();
		this.setWidth("300px");
		this.addComponent(cbKennzeichen);
	}
	
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
	
	public void setUnternehmen (Unternehmen u){
		this.tfUnternehmenname.setValue(u.getName());
	}
	
	public String getUnternehmen(){
		return this.tfUnternehmenname.getValue();
	}
}
