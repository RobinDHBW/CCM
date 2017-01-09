package com.dhbwProject.termine;

import com.dhbwProject.backend.DummyDataManager;
import com.dhbwProject.backend.beans.Besuch;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.VerticalLayout;

public class TerminBearbeitung extends CustomComponent {
	private static final long serialVersionUID = 1L;
	
	private DummyDataManager dummyData;
	private Besuch termin;
	
	private VerticalLayout vlLayout;
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
		//Probeweise--------------------------------------------
		if(b.getAutor().equals(dummyData.getUser()))
			this.setCaption("Ihnen");
		else
			this.setCaption(b.getAutor().getNachname()+", "+b.getAutor().getVorname());
		//------------------------------------------------------
		this.fields.setTitel(b.getName());
		this.fields.setAutor(b.getAutor());
		this.fields.setDateStart(b.getStartDate());
		this.fields.setDateEnd(b.getEndDate());
		this.fields.setTeilnehmenr(b.getBesucher());
		this.fields.setAdresse(b.getAdresse());
		this.fields.setAnsprechpartner(b.getAnsprechpartner());
	}
	
	private void initFields(){
		this.fields = new TerminFields(this.dummyData);
		this.vlLayout.addComponent(this.fields);
		this.vlLayout.setComponentAlignment(this.fields, Alignment.TOP_CENTER);
		this.fields.initFieldTitel();
		this.fields.initAnlage(); //Das wird wohl doch vllt. standard
//		this.fields.initFieldAutor();
		this.fields.initDfDateStart();
		this.fields.initDfDateEnd();
		this.fields.initFieldAdresse();
		this.fields.initFieldAnsprechpartner();
		this.fields.initFieldParticipants();
		this.initBtnUpdate();
	}

	private void initVlLayout(){
		this.vlLayout = new VerticalLayout();
		this.vlLayout.setSizeFull();
		this.setCompositionRoot(this.vlLayout);
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
		this.dummyData.updateTermin(this.termin, fields.getTitel(), fields.getDateStart(), fields.getDateEnd(), fields.getAdresse(), fields.getAnsprechpartner(), fields.getTeilnehmenr());
	}
	
	protected void setTermin(Besuch b){
		
	}
	
	protected Button getBtnUpdate(){
		return this.btnUpdate;
	}
	
}
