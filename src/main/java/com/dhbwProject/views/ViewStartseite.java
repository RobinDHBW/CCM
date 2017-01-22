package com.dhbwProject.views;

import com.dhbwProject.backend.CCM_Constants;
import com.dhbwProject.backend.beans.Benutzer;
import com.dhbwProject.besuche.BesuchVerwaltung;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeButton;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class ViewStartseite extends VerticalLayout implements View {
	private static final long serialVersionUID = 1L;
	private Benutzer benutzer = (Benutzer)VaadinSession.getCurrent().getSession().getAttribute(CCM_Constants.SESSION_VALUE_USER);

	public ViewStartseite(){

	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		 NativeButton welcome = new NativeButton();
		 		 welcome.setEnabled(false);
				 welcome.setCaption("Hallo " + benutzer.getVorname() + " " + benutzer.getNachname());
				 welcome.setStyleName("btnwhiteeins");
		 NativeButton deinBesuch = new NativeButton();
		 		 deinBesuch.setEnabled(false);
		 		 deinBesuch.setCaption("Ihre nächsten Besuche sind bei folgenden Unternehmen:");
		 		 deinBesuch.setStyleName("btnwhiteeins");
		BesuchVerwaltung bv = new BesuchVerwaltung();
		 
		FooterView fv = new FooterView();
		this.addComponent(welcome);
		this.setComponentAlignment(welcome,Alignment.BOTTOM_CENTER);
		
		this.addComponent(deinBesuch);
		this.setComponentAlignment(deinBesuch, Alignment.BOTTOM_CENTER);
		this.addComponent(bv);
		this.addComponent(fv);
		
	
	}

}
