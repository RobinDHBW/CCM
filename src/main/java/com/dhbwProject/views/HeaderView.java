package com.dhbwProject.views;

import com.vaadin.server.Responsive;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeButton;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class HeaderView extends CustomComponent{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String statustext;
	public HeaderView()	{
	Panel pnHeader = new Panel(); 
	pnHeader.setStyleName(ValoTheme.PANEL_BORDERLESS); //By Robin Bahr 07.01.2017
	//pnHeader.setHeight("40px");
	pnHeader.setWidth("100%");
	HorizontalLayout hL = new HorizontalLayout();
	 hL.setMargin(new MarginInfo(true, true, false, true)); //top right bottom left
	pnHeader.setContent(hL);
	Image dhbwLogo = new Image(" ",
		    new ThemeResource("logodhbw.gif"));
	dhbwLogo.setStyleName("biglogo");
	
	Image smalldhbwLogo = new Image(" ",
		    new ThemeResource("logodhbwsmall.gif"));
	smalldhbwLogo.setStyleName("smalllogo");
	
	
	
	/*Button imageButton = new Button();
	imageButton.setIcon(new ThemeResource("logodhbw.gif"));
	imageButton.setStyleName("btnwhitelogo");
	imageButton.setHeight("-1px");
	imageButton.setWidth("-1px");*/
	
	
	Image ccmLogo= new Image("Costumer Contact Management", new ThemeResource("ccmlogo.png"));
	ccmLogo.setStyleName("ccmbiglogo");
	
	Image ccmLogoSmall = new Image(" ", new ThemeResource("ccmlogosmall.png"));
	ccmLogoSmall.setStyleName("ccmsmalllogo");
		
		hL.addComponent(smalldhbwLogo);
		hL.setComponentAlignment(smalldhbwLogo, Alignment.TOP_LEFT);
		hL.addComponent(dhbwLogo);
		hL.setComponentAlignment(dhbwLogo, Alignment.BOTTOM_LEFT);
		
	
	Label whiteL = new Label(getStatustext());
	/*Button logoutB = new Button();
	logoutB.setCaption("Abmelden");*/
	whiteL.setWidth("100%");
	hL.addComponent(whiteL);
	hL.setComponentAlignment(whiteL, Alignment.BOTTOM_CENTER);
	hL.addComponent(ccmLogo);
	hL.setComponentAlignment(ccmLogo, Alignment.TOP_RIGHT);
	hL.addComponent(ccmLogoSmall);
	hL.setComponentAlignment(ccmLogoSmall, Alignment.TOP_RIGHT);
	/*hL.addComponent(logoutB);
	hL.setComponentAlignment(logoutB, Alignment.TOP_RIGHT);*/
	Responsive.makeResponsive(hL);
	pnHeader.setStyleName("pnheader");
	Responsive.makeResponsive(pnHeader);
	Responsive.makeResponsive(whiteL);
	Responsive.makeResponsive(dhbwLogo);
	Responsive.makeResponsive(ccmLogo);
	
	hL.setSizeFull();
	hL.setHeight("120px");
	pnHeader.setSizeFull();
	pnHeader.setHeight("130px");
	setSizeFull();
	
	setCompositionRoot(pnHeader);
	}
	public String getStatustext() {
		return statustext;
	}
	public void setStatustext(String statustext) {
		this.statustext = statustext;
	}
	
}
