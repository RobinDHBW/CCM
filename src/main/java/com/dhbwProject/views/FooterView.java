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
import com.vaadin.ui.themes.ValoTheme;

public class FooterView extends CustomComponent{
	FooterView(){
		HorizontalLayout hL = new HorizontalLayout();
		Panel pnFooter = new Panel(); 
		pnFooter.setStyleName(ValoTheme.PANEL_BORDERLESS);
		pnFooter.setHeight("40px");
		pnFooter.setWidth("100%");
		 hL.setMargin(new MarginInfo(true, true, false, true)); //top right bottom left
		pnFooter.setContent(hL);
		Label impressum = new Label("Impressum");
		Label whiteL = new Label(" ");
		Label version = new Label("Version" + "1.0");
		hL.addComponent(impressum);
		hL.setComponentAlignment(impressum, Alignment.TOP_LEFT);
		hL.addComponent(whiteL);
		hL.addComponent(version);
		hL.setComponentAlignment(version,Alignment.TOP_RIGHT);


		hL.setSizeFull();
		hL.setHeight("50px");
		pnFooter.setSizeFull();
		pnFooter.setHeight("50px");
		setSizeFull();
		
		setCompositionRoot(pnFooter);
	}
}
