package com.dhbwProject.views;

import com.dhbwProject.backend.CCM_Constants;
import com.dhbwProject.backend.DummyDataManager;
import com.dhbwProject.backend.dbConnect;
import com.dhbwProject.besuche.BesuchAnlage;
import com.dhbwProject.besuche.BesuchBearbeitung;
import com.dhbwProject.besuche.BesuchVerwaltung;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.TabSheet;

public class ViewBesuch extends CustomComponent implements View {
	private static final long serialVersionUID = 1L;
	
//	private dbConnect dbConnection;
//	private DummyDataManager dummyData;
	private TabSheet tbContent;
	
	private BesuchVerwaltung verwaltung;
//	private TerminAnlage anlage;
//	private TerminBearbeitung bearbeitung;
	
	public ViewBesuch(){
		this.setSizeFull();
		this.setCaption(CCM_Constants.VIEW_NAME_BESUCH);
		this.initTbContent();
		
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		this.verwaltung.refresh();
		
	}
	
	private void initTbContent(){
		this.tbContent = new TabSheet();
		this.verwaltung = new BesuchVerwaltung();
		this.tbContent.addTab(this.verwaltung, "Meine Termine", FontAwesome.USER);
//		this.tbContent.addTab(new TerminAnlage(this.dummyData), "Termin anlegen", FontAwesome.CALENDAR_PLUS_O);
//		this.tbContent.addTab(new TerminBearbeitung(this.dummyData), "Termin bearbeiten", FontAwesome.LIST);
		this.setCompositionRoot(this.tbContent);
	}

}
