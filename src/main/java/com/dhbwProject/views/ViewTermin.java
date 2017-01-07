package com.dhbwProject.views;

import com.dhbwProject.backend.CCM_Constants;
import com.dhbwProject.backend.DummyDataManager;
import com.dhbwProject.termine.TerminAnlage;
import com.dhbwProject.termine.TerminBearbeitung;
import com.dhbwProject.termine.TerminVerwaltung;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

public class ViewTermin extends VerticalLayout implements View {
	private static final long serialVersionUID = 1L;
	private DummyDataManager dummyData;
	
	private TabSheet tbContent;
	
	public ViewTermin(DummyDataManager dummyData){
		this.dummyData = dummyData;
		this.setSizeFull();
		this.setCaption(CCM_Constants.VIEW_NAME_TERMIN);
		this.initTbContent();
		
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}
	
	private void initTbContent(){
		this.tbContent = new TabSheet();
		this.tbContent.addTab(new TerminVerwaltung(this.dummyData), "Meine Termine", FontAwesome.USER);
		this.tbContent.addTab(new TerminAnlage(this.dummyData), "Termin anlegen", FontAwesome.CALENDAR_PLUS_O);
		this.tbContent.addTab(new TerminBearbeitung(this.dummyData), "Termin bearbeiten", FontAwesome.LIST);
		this.addComponent(this.tbContent);
	}

}
