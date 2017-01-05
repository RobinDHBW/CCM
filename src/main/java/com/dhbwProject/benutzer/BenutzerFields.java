package com.dhbwProject.benutzer;

import com.dhbwProject.backend.beans.Benutzer;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class BenutzerFields extends VerticalLayout{
	private static final long serialVersionUID = 1L;
	
	private TextField tfVorname;
	private TextField tfNachname;
	private ComboBox cbBeruf;
	
	public BenutzerFields() {
		this.setSizeUndefined();
		this.setSpacing(true);
		this.initVorname();
		this.initNachname();
		this.initCbBerufe();
		
	}
	
	private void initVorname() {
		this.tfVorname = new TextField();
		this.tfVorname.setInputPrompt("Vorname");
		this.setWidth("300px");
		this.addComponent(tfVorname);
		
	}
	
	private void initNachname() {
		this.tfNachname = new TextField();
		this.tfNachname.setInputPrompt("Nachname");
		this.setWidth("300px");
		this.addComponent(tfNachname);
	}
	
	private void initCbBerufe() {
		this.cbBeruf = new ComboBox();
		this.setWidth("300px");
		this.addComponent(cbBeruf);
	}
	
	public void setVorname (Benutzer b){
		this.tfVorname.setValue(b.getVorname());
	}
	
	public String getVorname (){
		return this.tfVorname.getValue();
	}
	
	
	

}
