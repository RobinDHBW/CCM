package com.dhbwProject.unternehmen;

import com.vaadin.ui.AbstractSelect.ItemCaptionMode;
import com.vaadin.data.validator.NullValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.server.Responsive;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class UnternehmenFelder extends VerticalLayout {
	private static final long serialVersionUID = 1L; // dass net gelb unterstreicht
	
	private TextField tfName;
	private ComboBox cbKennzeichen;
	
	public UnternehmenFelder(){
		this.setSpacing(true);
		this.initFields();
		Responsive.makeResponsive(this);
	}
	
	private void initFields(){
		this.tfName = new TextField();
	//	this.tfName.setWidth("300px");
		this.tfName.setStyleName("tffield");
		this.tfName.setCaption("Firma:");
		this.tfName.addValidator(new StringLengthValidator("Tragen Sie die Firma ein", 1, 40, false));
		this.addComponent(tfName);
		Responsive.makeResponsive(tfName);
		
		this.cbKennzeichen = new ComboBox();
	//	this.cbKennzeichen.setWidth("300px");
		this.cbKennzeichen.setCaption("Kennzeichen:");
		this.cbKennzeichen.addContainerProperty("Kennzeichen", String.class, null);
		this.cbKennzeichen.addItem("A").getItemProperty("Kennzeichen").setValue("Premiumpartner");
		this.cbKennzeichen.addItem("B").getItemProperty("Kennzeichen").setValue("Sonstige Partner");
		this.cbKennzeichen.setItemCaptionMode(ItemCaptionMode.ITEM);
		this.cbKennzeichen.addValidator(new NullValidator("Wählen Sie ein Kennzeichen", false));
		this.addComponent(cbKennzeichen);
		Responsive.makeResponsive(cbKennzeichen);
	}

	protected String getName(){
		return this.tfName.getValue();
	}
	
	protected void setName(String s){
		this.tfName.setValue(s);
	}
	
	protected String getKennzeichen(){
		return (String)this.cbKennzeichen.getValue();
	}
	
	protected void setKennzeichen(String s){
		this.cbKennzeichen.setValue(s);
	}
	
	protected boolean areFieldsValid(){
		return (this.tfName.isValid() && this.cbKennzeichen.isValid());
	}
	
}

