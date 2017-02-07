package com.dhbwProject.views;

import com.dhbwProject.backend.CCM_Constants;
import com.dhbwProject.unternehmen.UnternehmenVerwaltung;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Responsive;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

public class ViewUnternehmen extends VerticalLayout implements View {
	private static final long serialVersionUID = 1L;
	private TabSheet tbContent;
	private UnternehmenVerwaltung verwaltung;
	
	public ViewUnternehmen(){
		this.setSizeFull();
		this.setCaption(CCM_Constants.VIEW_NAME_UNTERNEHMEN);
		this.initTbContent();
		FooterView fv= new FooterView();
		this.addComponent(fv);
		Responsive.makeResponsive(this);
		Responsive.makeResponsive(fv);
		
		
	}
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}
	
	private void initTbContent(){
		this.tbContent = new TabSheet();
		this.verwaltung = new UnternehmenVerwaltung();
		this.tbContent.addTab(this.verwaltung, "Verwaltung");
		this.addComponent(tbContent);
		Responsive.makeResponsive(tbContent);
	}

}
