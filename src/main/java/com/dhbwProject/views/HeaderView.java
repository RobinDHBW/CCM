package com.dhbwProject.views;

import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;

public class HeaderView extends CustomComponent{
	public HeaderView()
	{
	Panel pnHeader = new Panel();
	pnHeader.setHeight("40px");
	pnHeader.setWidth("100%");
	HorizontalLayout hL = new HorizontalLayout();
	 hL.setMargin(new MarginInfo(true, true, false, true)); //top right bottom left
	pnHeader.setContent(hL);
	Image dhbwLogo = new Image(" ",
		    new ThemeResource("Logo DHBW_Mosbach.gif"));
	dhbwLogo.setHeight("-1px");
	dhbwLogo.setWidth("-1px");
		hL.addComponent(dhbwLogo);
		hL.setComponentAlignment(dhbwLogo, Alignment.TOP_LEFT);
	
	Label whiteL = new Label(" ");
	Button logoutB = new Button();
	logoutB.setCaption("Abmelden");
	whiteL.setWidth("100%");
	hL.addComponent(whiteL);

	hL.addComponent(logoutB);
	hL.setComponentAlignment(logoutB, Alignment.TOP_RIGHT);
	hL.setSizeFull();
	hL.setHeight("120px");
	pnHeader.setSizeFull();
	pnHeader.setHeight("130px");
	setSizeFull();
	
	setCompositionRoot(pnHeader);
	}
}
