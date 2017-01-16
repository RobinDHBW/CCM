package com.dhbwProject.views;

import com.dhbwProject.backend.CCM_Constants;
import com.dhbwProject.backend.DummyDataManager;
import com.dhbwProject.backend.dbConnect;
import com.dhbwProject.termine.TerminAnlage;
import com.dhbwProject.termine.TerminBearbeitung;
import com.dhbwProject.termine.TerminVerwaltung;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.TabSheet;

public class ViewTermin extends CustomComponent implements View {
	private static final long serialVersionUID = 1L;
	
//	private dbConnect dbConnection;
//	private DummyDataManager dummyData;
	private TabSheet tbContent;
	
	private TerminVerwaltung verwaltung;
//	private TerminAnlage anlage;
//	private TerminBearbeitung bearbeitung;
	
	public ViewTermin(){
		this.setSizeFull();
		this.setCaption(CCM_Constants.VIEW_NAME_TERMIN);
		this.initTbContent();
		
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		this.verwaltung.refresh();
		
	}
	
	private void initTbContent(){
		this.tbContent = new TabSheet();
		this.verwaltung = new TerminVerwaltung();
		this.tbContent.addTab(this.verwaltung, "Meine Termine", FontAwesome.USER);
//		this.tbContent.addTab(new TerminAnlage(this.dummyData), "Termin anlegen", FontAwesome.CALENDAR_PLUS_O);
//		this.tbContent.addTab(new TerminBearbeitung(this.dummyData), "Termin bearbeiten", FontAwesome.LIST);
		this.setCompositionRoot(this.tbContent);
	}

}
