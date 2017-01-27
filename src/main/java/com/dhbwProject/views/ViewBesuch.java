package com.dhbwProject.views;

import com.dhbwProject.backend.CCM_Constants;
import com.dhbwProject.besuche.BesuchUebersicht;
import com.dhbwProject.besuche.BesuchVerwaltung;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.TabSheet;

public class ViewBesuch extends CustomComponent implements View {
	private static final long serialVersionUID = 1L;

	private TabSheet tbContent;	
	private BesuchVerwaltung verwaltung;
	private BesuchUebersicht uebersicht;
	
	public ViewBesuch(){
		this.setSizeFull();
		this.setCaption(CCM_Constants.VIEW_NAME_BESUCH);
		this.initTbContent();
		FooterView fv= new FooterView();
		tbContent.addComponent(fv);
		
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		this.verwaltung.refresh();
		
	}
	
	private void initTbContent(){
		this.tbContent = new TabSheet();
		this.verwaltung = new BesuchVerwaltung();
		this.uebersicht = new BesuchUebersicht();
		this.tbContent.addTab(this.verwaltung, "Meine Termine", FontAwesome.USER);
		this.tbContent.addTab(this.uebersicht, "Gesamtübersicht", FontAwesome.LIST);
		this.setCompositionRoot(this.tbContent);
	}

}
