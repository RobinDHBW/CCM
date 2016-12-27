package com.dhbwProject.views;

import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;

public class HeaderView {
	public HeaderView()
	{
	HorizontalLayout hL = new HorizontalLayout();
 
	Image dhbwLogo = new Image(null,
		    new ThemeResource("Logo DHBW_Mosbach.gif"));
		hL.addComponent(dhbwLogo);
	
	Label whiteL = new Label(" ");
	Button logoutB = new Button();
	logoutB.setCaption("Abmelden");
 
	hL.addComponent(whiteL);
	hL.addComponent(logoutB);
	}
}
