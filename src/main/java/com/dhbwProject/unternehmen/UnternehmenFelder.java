package com.dhbwProject.unternehmen;

import com.vaadin.ui.AbstractSelect.ItemCaptionMode;
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
	}
	
	private void initFields(){
		this.tfName = new TextField();
		this.tfName.setWidth("300px");
		this.tfName.setCaption("Firma");
		this.addComponent(tfName);
		
		this.cbKennzeichen = new ComboBox();
		this.cbKennzeichen.setWidth("300px");
		this.cbKennzeichen.addContainerProperty("Kennzeichen", String.class, null);
		this.cbKennzeichen.addItem("A").getItemProperty("Kennzeichen").setValue("Premiumpartner");
		this.cbKennzeichen.addItem("B").getItemProperty("Kennzeichen").setValue("Sonstige Partner");
		this.cbKennzeichen.setItemCaptionMode(ItemCaptionMode.ITEM);
		this.addComponent(cbKennzeichen);
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
		return true;
	}
	
}


