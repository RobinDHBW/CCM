package com.dhbwProject.unternehmen;
import com.dhbwProject.backend.beans.Adresse;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.server.Responsive;
import com.vaadin.server.UserError;
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
		Responsive.makeResponsive(this);
	}
	
	public AnsprechpartnerFelder(Adresse a){
		this();
		this.setAdresse(a);
	}
	
	private void initFields(){		
		this.tfVornameAnsprechpartner = new TextField("Ansprechpartner:");
		this.tfVornameAnsprechpartner.setCaption("Vorname:");
		this.tfVornameAnsprechpartner.setWidth("300px");
		this.tfVornameAnsprechpartner.addValidator(new StringLengthValidator("Tragen Sie einen Nachnamen ein", 1, 40, false));
		this.addComponent(tfVornameAnsprechpartner);
		
		this.tfNameAnsprechpartner = new TextField();
		this.tfNameAnsprechpartner.setCaption("Nachname:");
		this.tfNameAnsprechpartner.setWidth("300px");
		this.tfNameAnsprechpartner.addValidator(new StringLengthValidator("Tragen Sie einen Vornamen ein", 1, 40, false));
		this.addComponent(tfNameAnsprechpartner);
		
		this.tfTelefonnummer = new TextField();
		this.tfTelefonnummer.setCaption("Telefonnummer:");
		this.tfTelefonnummer.setNullRepresentation("");
		this.tfTelefonnummer.setWidth("300px");
		this.addComponent(tfTelefonnummer);
		
		this.tfEmail = new TextField();
		this.tfEmail.setCaption("E-Mail:");
		this.tfEmail.setNullRepresentation("");
		this.tfEmail.setWidth("300px");
		this.tfEmail.addValidator(new EmailValidator("Tragen Sie eine gültige e-Mail ein"));
		this.addComponent(tfEmail);
		Responsive.makeResponsive(tfVornameAnsprechpartner);
		Responsive.makeResponsive(tfNameAnsprechpartner);
		Responsive.makeResponsive(tfTelefonnummer);
		Responsive.makeResponsive(tfEmail);
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
		try{
			Integer.parseInt(this.tfTelefonnummer.getValue());
		}catch(NumberFormatException e){
			this.tfTelefonnummer.setComponentError(new UserError("Eine Telefonnummer besteht aus Zahlen zwischen 0 und 9"));
			return false;
		}
		return (this.tfNameAnsprechpartner.isValid() && this.tfVornameAnsprechpartner.isValid() && this.tfEmail.isValid() && this.tfTelefonnummer.isValid());
	}

}

