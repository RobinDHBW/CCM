package com.dhbwProject.unternehmen;

import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class UnternehmenFelder extends VerticalLayout {
	private static final long serialVersionUID = 1L; // dass net gelb unterstreicht
	
	private TextField tfName;
	private OptionGroup ogKennzeichen;
	
	public UnternehmenFelder(){
		this.setSpacing(true);
		this.initFields();
	}
	
	private void initFields(){
		this.tfName = new TextField();
		this.tfName.setWidth("300px");
		this.tfName.setCaption("Firma");
		this.addComponent(tfName);
		
		this.ogKennzeichen = new OptionGroup();
		this.ogKennzeichen.setCaption("Kennzeichen");
		this.ogKennzeichen.addItem("Premiumpartner");
		this.ogKennzeichen.addItem("Sonstiger Partner");
		this.addComponent(ogKennzeichen);
	}

	protected String getName(){
		return this.tfName.getValue();
	}
	
	protected void setName(String s){
		this.tfName.setValue(s);
	}
	
	protected String getKennzeichen(){
		return "A";
	}
	
	protected void setKennzeichen(String s){
		
	}
	
}
