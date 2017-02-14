package com.dhbwProject.views;

import com.dhbwProject.backend.CCM_Constants;
import com.dhbwProject.benutzer.BenutzerVerwaltung;
import com.dhbwProject.rolle.RolleVerwaltung;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

public class ViewBenutzer extends VerticalLayout implements View {
	private static final long serialVersionUID = 1L;
	private TabSheet tbContent;
	
	public ViewBenutzer(){
		this.setSizeFull();
		this.setCaption(CCM_Constants.VIEW_NAME_BENUTZER);
		this.initTbContent();
		FooterView fv= new FooterView();
		this.addComponent(fv);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		
		
	}
	
	private void initTbContent(){
		this.tbContent = new TabSheet();
		this.tbContent.addTab(new BenutzerVerwaltung(), "Benutzer", FontAwesome.USERS);
//		this.tbContent.addTab(new BenutzerAnlage(), "Benutzer anlegen", FontAwesome.USER_PLUS);
//		this.tbContent.addTab(new BenutzerAenderung(), "Benutzer ändern", FontAwesome.LIST);
		this.tbContent.addTab(new RolleVerwaltung(), "Rolle", FontAwesome.LIST);
		Responsive.makeResponsive(tbContent);
		this.addComponent(tbContent);		
	}
}
