package com.dhbwProject.views;

import com.dhbwProject.backend.CCM_Constants;
import com.dhbwProject.termine.TerminAnlage;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

public class ViewTermin extends VerticalLayout implements View {
	private static final long serialVersionUID = 1L;
	
	private TabSheet tbContent;
	
	public ViewTermin(){
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
		this.tbContent.addTab(new TerminAnlage(), "Termin anlegen", FontAwesome.CALENDAR_PLUS_O);
		this.addComponent(this.tbContent);
	}

}
