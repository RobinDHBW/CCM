package com.dhbwProject.views;

import com.dhbwProject.backend.CCM_Constants;
import com.dhbwProject.backend.beans.Benutzer;
import com.dhbwProject.besuche.BesuchVerwaltung;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Responsive;
import com.vaadin.server.Sizeable;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinSession;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeButton;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class ViewStartseite extends VerticalLayout implements View {
	private static final long serialVersionUID = 1L;
	private Benutzer benutzer = (Benutzer)VaadinSession.getCurrent().getSession().getAttribute(CCM_Constants.SESSION_VALUE_USER);

	public ViewStartseite(){

	}

	@Override
	public void enter(ViewChangeEvent event) {
		 NativeButton welcome = new NativeButton();
		 		 welcome.setEnabled(false);
				 welcome.setCaption("Hallo " + benutzer.getVorname() + " " + benutzer.getNachname() + ",");
				 welcome.setStyleName("btnwhiteeins");
		TextArea ta = new TextArea();
//		ta.setCaption("Hallo " + benutzer.getVorname() + " " + benutzer.getNachname() + ",");
		ta.setValue("Willkommen im CCM System der DHBW");
		ta.setStyleName(ValoTheme.TEXTAREA_BORDERLESS);
		ta.setWidth("400px");
		ta.setWordwrap(true);
		ta.setHeight("150px");
//		Panel p = new Panel();
//		p.setWidth(50, Unit.PERCENTAGE);
//		p.setStyleName(ValoTheme.PANEL_BORDERLESS);
//		
//		
//		 Label deinBesuch = new Label("Willkommen im CCM System, dem Unternehmensmanagement der Dualen Hochschule Baden-Württemberg in Mosbach.");
//		 		 deinBesuch.setEnabled(false);
//		 		 deinBesuch.setCaption("Willkommen im CCM System, dem dualen Partnerunternehmesmanagement der Dualen Hochschule Baden-Württemberg in Mosbach. ");
//		 		 //deinBesuch.setStyleName("btnwhiteeins");

		
		Image ccmLogo= new Image(" ", new ThemeResource("ccmlogo.png"));
		ccmLogo.setHeight("-1px");
		ccmLogo.setWidth("-1px");
				
		
		FooterView fv = new FooterView();
		this.addComponent(welcome);
	this.setComponentAlignment(welcome,Alignment.BOTTOM_CENTER);
		
		this.addComponent(ta);
		this.setComponentAlignment(ta, Alignment.BOTTOM_CENTER);
//		this.addComponent(bv);
		this.addComponent(ccmLogo);
		this.setComponentAlignment(ccmLogo, Alignment.BOTTOM_CENTER);
		this.addComponent(fv);
		Responsive.makeResponsive(welcome);
		Responsive.makeResponsive(ta);
		Responsive.makeResponsive(ccmLogo);
		Responsive.makeResponsive(fv);
		
	
	}

}
