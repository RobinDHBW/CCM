package com.dhbwProject.views;

import com.dhbwProject.backend.CCM_Constants;
// import com.dhbwProject.unternehmen.LayoutUnternehmen;
import com.dhbwProject.unternehmen.UnternehmenAenderung;
import com.dhbwProject.unternehmen.UnternehmenAnlage;
import com.dhbwProject.unternehmen.UnternehmenAnzeigen;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

public class ViewUnternehmen extends VerticalLayout implements View {
	private static final long serialVersionUID = 1L;
	private TabSheet tbContent;
	
	public ViewUnternehmen(){
		this.setSizeFull();
		this.setCaption(CCM_Constants.VIEW_NAME_UNTERNEHMEN);
		this.initTbContent();
		
		
	}
	/* 
	 * 
	 * private LayoutUnternehmen content;
	
	public ViewUnternehmen(){
	this.setSizeFull();
	this.setCaption(CCM_Constants.VIEW_NAME_UNTERNEHMEN);
	this.content = new LayoutUnternehmen();
	//Hier wird zunächst der Header eingefügt.
//	this.addComponent(new HeaderView());
	this.addComponent(this.content);
	}
	
	Habe ich (Flo) mal auskommentiert, verstehe den Sinn/Nutzen nicht
*/ 
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}
	
	private void initTbContent(){
		this.tbContent = new TabSheet();
		this.tbContent.addTab(new UnternehmenAnlage(), "Unternehmen anlegen", FontAwesome.PLUS);
		this.tbContent.addTab(new UnternehmenAenderung(), "Unternehmen bearbeiten", FontAwesome.PLUS);
		this.tbContent.addTab(new UnternehmenAnzeigen(), "Unternehmen anzeigen", FontAwesome.PLUS);
		this.addComponent(tbContent);
	}

}
