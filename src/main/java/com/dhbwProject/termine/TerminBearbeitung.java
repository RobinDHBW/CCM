package com.dhbwProject.termine;

import com.dhbwProject.backend.DummyDataManager;
import com.dhbwProject.backend.beans.Besuch;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class TerminBearbeitung extends CustomComponent {
	private static final long serialVersionUID = 1L;
	
	private DummyDataManager dummyData;
	private Besuch termin;
	
	private VerticalLayout vlLayout;
	private TextField tfTermin;
	private Button btnLookupTermin;
	private TerminFields fields;
	private Button btnUpdate;
	
	public TerminBearbeitung(DummyDataManager dummyData){
		this.dummyData = dummyData;
		this.initVlLayout();
		this.initFields();
	}
	
	public TerminBearbeitung(DummyDataManager dummyData, Besuch b){
		this(dummyData);
		this.termin = b;
		this.tfTermin.setValue(b.getName());
		this.fields.setDate(b.getStartDate());
		this.fields.setTeilnehmenr(b.getBesucher());
		this.fields.setUnternehmen(b.getAdresse().getUnternehmen());
		this.fields.setAnsprechpartner(b.getAnsprechpartner());
	}
	
	private void initFields(){
		this.fields = new TerminFields(this.dummyData);
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
//		this.btnLookupTermin.setStyleName(ValoTheme.BUTTON_BORDERLESS);
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
//		this.btnUpdate.setStyleName(ValoTheme.BUTTON_ICON_ALIGN_RIGHT);
		this.btnUpdate.addClickListener(listener ->{
			this.updateTermin();
		});
		this.fields.addComponent(this.btnUpdate);
		
		
		
	}
	
	private void updateTermin(){
		/*
		 * Wie wollen wir updates vornehmen?
		 * */
	}
	
	protected void setTermin(Besuch b){
		
	}
	
	protected Button getBtnUpdate(){
		return this.btnUpdate;
	}
	
	
}
