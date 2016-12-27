package com.dhbwProject.termine;

import com.dhbwProject.backend.beans.Besuch;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;
import com.vaadin.ui.themes.ValoTheme;

public class TerminBearbeitung extends CustomComponent {
	private static final long serialVersionUID = 1L;
	
	private Besuch termin;
	
	private VerticalLayout vlLayout;
	private TextField tfTermin;
	private Button btnLookupTermin;
	private TerminFields fields;
	private Button btnUpdate;
	
	public TerminBearbeitung(){
		this.initVlLayout();
		this.initFields();
	}
	
	public TerminBearbeitung(Besuch b){
		this();
		this.setTermin(b);
	}
	
	private void initFields(){
		this.fields = new TerminFields();
		this.vlLayout.addComponent(this.fields);
		this.initLookupTermin();
		this.fields.initFieldTitel();
		this.fields.initDfDate();
		this.fields.initFieldUnternehmen();
		this.fields.initFieldAnsprechpartner();
		this.fields.initFieldParticipants();
		this.initBtnUpdate();
	}

	private void initVlLayout(){
		this.vlLayout = new VerticalLayout();
		this.vlLayout.setSizeFull();
		this.setCompositionRoot(this.vlLayout);
	}
	
	private void initLookupTermin(){
		HorizontalLayout hlLayout = new HorizontalLayout();
		this.tfTermin = new TextField();
		this.tfTermin.setInputPrompt("Termin");
		this.tfTermin.setWidth("300px");
		
		this.btnLookupTermin = new Button();
		this.btnLookupTermin.setIcon(FontAwesome.REPLY);
		this.btnLookupTermin.setStyleName(ValoTheme.BUTTON_BORDERLESS);
		this.btnLookupTermin.setWidth("50px");
		this.btnLookupTermin.addClickListener(listener ->{
			
		});
		hlLayout.setSizeUndefined();
		hlLayout.addComponent(this.tfTermin);
		hlLayout.addComponent(this.btnLookupTermin);
		this.fields.addComponent(hlLayout);	
	}
	
	private void initBtnUpdate(){
		this.btnUpdate = new Button("Bearbeiten");
		this.btnUpdate.setIcon(FontAwesome.CHECK);
		this.btnUpdate.setStyleName(ValoTheme.BUTTON_ICON_ALIGN_RIGHT);
		this.btnUpdate.addClickListener(listener ->{
			this.updateTermin();
		});
		this.fields.addComponent(this.btnUpdate);
		
		
		
	}
	
	private void updateTermin(){
		
	}
	
	protected void setTermin(Besuch b){
		
	}
	
	protected Button getBtnUpdate(){
		return this.btnUpdate;
	}
	
	
}
