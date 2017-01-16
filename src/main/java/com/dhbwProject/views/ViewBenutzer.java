package com.dhbwProject.views;

import com.dhbw.Project.rolle.RolleAnlage;
import com.dhbw.Project.rolle.RolleAnzeige;
import com.dhbwProject.backend.CCM_Constants;
import com.dhbwProject.benutzer.BenutzerAenderung;
import com.dhbwProject.benutzer.BenutzerAnlage;
import com.dhbwProject.benutzer.BenutzerAnzeige;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

public class ViewBenutzer extends VerticalLayout implements View {
	private static final long serialVersionUID = 1L;
	private TabSheet tbContent;
	
	
	
	public ViewBenutzer(){
		this.setSizeFull();
		this.setCaption(CCM_Constants.VIEW_NAME_ROLLE);
		this.initTbContent();
	}

	@Override
	public void enter(ViewChangeEvent event) {
		
		
	}
	
	private void initTbContent(){
		this.tbContent = new TabSheet();
		this.tbContent.addTab(new RolleAnzeige(), "Alle Rollen", FontAwesome.USERS);
		this.tbContent.addTab(new RolleAnlage(), "Rolle anlegen", FontAwesome.USER_PLUS);
		this.tbContent.addTab(new BenutzerAenderung(), "Rolle ändern", FontAwesome.LIST);
		this.addComponent(tbContent);
		
	}
}
