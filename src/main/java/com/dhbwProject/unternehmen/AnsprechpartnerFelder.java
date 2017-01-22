package com.dhbwProject.unternehmen;

import com.dhbwProject.backend.beans.Adresse;
import com.vaadin.server.FontAwesome;
//import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class AnsprechpartnerFelder extends VerticalLayout {
	private static final long serialVersionUID = 1L;
	
	private Adresse adresse;
	private TextField tfNameAnsprechpartner;
	private TextField tfVornameAnsprechpartner;
	private TextField tfTelefonnummer;
	private TextField tfEmail;
	
	public AnsprechpartnerFelder(){
		this.setSizeUndefined();
		this.initFields();
	}
	
	public AnsprechpartnerFelder(Adresse a){
		this();
		this.setAdresse(a);
	}
	
	private void initFields(){		
		this.tfVornameAnsprechpartner = new TextField("Ansprechpartner:");
		this.tfVornameAnsprechpartner.setCaption("Vorname:");
		this.setWidth("300px");
		this.addComponent(tfVornameAnsprechpartner);
		
		this.tfNameAnsprechpartner = new TextField();
		this.tfNameAnsprechpartner.setCaption("Nachname:");
		this.setWidth("300px");
		this.addComponent(tfNameAnsprechpartner);
		
		this.tfTelefonnummer = new TextField();
		this.tfTelefonnummer.setCaption("Telefonnummer:");
		this.setWidth("300px");
		this.addComponent(tfTelefonnummer);
		this.tfTelefonnummer.setIcon(FontAwesome.WHATSAPP);
		
		this.tfEmail = new TextField();
		this.tfEmail.setCaption("E-Mail:");
		this.setWidth("300px");
		this.addComponent(tfEmail);
		this.tfEmail.setIcon(FontAwesome.AT);
	}
	
	public Adresse getAdresse(){
		return this.adresse;
	}
	
	public void setAdresse(Adresse a){
		this.adresse = a;
	}
	
	public String getVorname(){
		return this.tfVornameAnsprechpartner.getValue();
	}
	
	public void setVorname(String s){
		this.tfVornameAnsprechpartner.setValue(s);
	}
	
	public String getNachname(){
		return this.tfNameAnsprechpartner.getValue();
	}
	
	public void setNachname(String s){
		this.tfNameAnsprechpartner.setValue(s);
	}
	
	public String getTelefonnummer(){
		return this.tfTelefonnummer.getValue();
	}
	
	public void setTelefonnummer(String s){
		this.tfTelefonnummer.setValue(s);
	}
	
	public String getEmail(){
		return this.tfEmail.getValue();
	}
	
	public void setEmail(String s){
		this.tfEmail.setValue(s);
	}
	
	public boolean areFieldsValid(){
		return true;
	}

}

