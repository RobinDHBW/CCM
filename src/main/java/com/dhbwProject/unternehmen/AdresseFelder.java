package com.dhbwProject.unternehmen;

import com.dhbwProject.backend.beans.Adresse;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class AdresseFelder extends VerticalLayout{
	private static final long serialVersionUID = 1L;
	private Adresse aCreate;
	
	private TextField tfStrasse;
	private TextField tfHausnummer;
	private TextField tfPlz;
	private TextField tfOrt;
	
	public AdresseFelder(){
		this.setSpacing(true);
		this.initFields();
		
	}
	
	private void initFields(){		
		this.tfPlz = new TextField();
		this.tfPlz.setCaption("PLZ:");
		this.tfPlz.setWidth("300px");
		this.tfPlz.addValidator(new StringLengthValidator("Eine Postleitzahl ist immer 5 Zeichen lang", 5, 5, false));
		this.addComponent(tfPlz);
		
		this.tfStrasse = new TextField();
		this.tfStrasse.setCaption("Straße:");
		this.tfStrasse.setWidth("300px");
		this.addComponent(this.tfStrasse);
		
		this.tfHausnummer = new TextField();
		this.tfHausnummer.setCaption("Hausnummer:");
		this.tfHausnummer.setWidth("300px");
		this.addComponent(tfHausnummer);
		
		this.tfOrt = new TextField();
		this.tfOrt.setCaption("Ort:");
		this.tfOrt.setWidth("300px");
		this.addComponent(this.tfOrt);
	}
	
	protected String getPlz(){
		return this.tfPlz.getValue();
	}
	
	protected void setPlz(String plz){
		this.tfPlz.setValue(plz);
	}
	
	protected String getStrasse(){
		return this.tfStrasse.getValue();
	}
	
	protected void setStrasse(String strasse){
		this.tfStrasse.setValue(strasse);
	}
	
	protected String getOrt(){
		return this.tfOrt.getValue();
	}
	
	protected void setOrt(String ort){
		this.tfOrt.setValue(ort);
	}
	
	protected String getHausnummer(){
		return this.tfHausnummer.getValue();
	}
	
	protected void setHausnummer(String s){
		this.tfHausnummer.setValue(s);
	}

	protected boolean areFieldsValid(){
		return true;
	}
}
