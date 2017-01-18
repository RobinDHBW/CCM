package com.dhbwProject.unternehmen;

import com.dhbwProject.backend.CCM_Constants;
import com.dhbwProject.backend.dbConnect;
import com.dhbwProject.backend.beans.Adresse;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class AdresseFelder extends VerticalLayout{
	private static final long serialVersionUID = 1L;
	private Adresse aCreate;
	
	private TextField tfStrasse;
	private TextField tfHausnummer;
	
	private ComboBox cbOrt;
	
	public AdresseFelder(){
		this.setSpacing(true);
		this.initFields();
		
	}
	
	private void initFields(){		
		this.tfStrasse = new TextField();
		this.tfStrasse.setCaption("Straße:");
		this.tfStrasse.setWidth("300px");
		this.addComponent(this.tfStrasse);
		
		this.tfHausnummer = new TextField();
		this.tfHausnummer.setCaption("Hausnummer:");
		this.tfHausnummer.setWidth("300px");
		this.addComponent(tfHausnummer);
		
		
		dbConnect connection = (dbConnect)VaadinSession.getCurrent().getSession().getAttribute(CCM_Constants.SESSION_VALUE_CONNECTION);
		this.cbOrt = new ComboBox();
		this.cbOrt.setCaption("Ort:");
		this.cbOrt.setWidth("300px");
		this.addComponent(this.cbOrt);
	}
	
//	private String getPlz(){
//		return this.tfPlz.getValue();
//	}
//	
//	private void setPlz(String plz){
//		this.tfPlz.setValue(plz);
//	}
//	
	private String getStrasse(){
		return this.tfStrasse.getValue();
	}
	
	private void setStrasse(String strasse){
		this.tfStrasse.setValue(strasse);
	}
	
//	private String getOrt(){
//		return this.tfOrt.getValue();
//	}
//	
//	private void setOrt(String ort){
//		this.tfOrt.setValue(ort);
//	}

}
