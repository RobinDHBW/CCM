package com.dhbwProject.CCM;

import com.dhbwProject.backend.CCM_Constants;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

public class Navigationsbar extends CssLayout{
	private static final long serialVersionUID = 1L;
	private CCM_Navigator navNavigator;
	
	public Navigationsbar(CCM_Navigator nav){
		this.setSizeFull();
		this.navNavigator = nav;
		this.initLogoutButton();
		this.initNaviButtons();
		
	}
	
	private void initLogoutButton(){
		Button btnLogout = new Button();
		btnLogout.setWidth("100%");
		btnLogout.setCaption("Abmelden");
		btnLogout.setIcon(FontAwesome.SIGN_OUT);
		btnLogout.setStyleName(ValoTheme.BUTTON_ICON_ALIGN_RIGHT);
		btnLogout.addClickListener(click ->{
			/*
			 * Hier sollte statt der Schleife eher ein gesamter Resett der UI hin
			 * sonst sind wir in "gewisser" Art statefull
			 * */
			for(Window w : this.getUI().getWindows())
				w.close();
			this.getSession().setAttribute(CCM_Constants.SESSION_VALUE_USER, null);
			this.navNavigator.navigateTo(CCM_Constants.VIEW_NAME_LOGIN);
		
		});
		this.addComponent(btnLogout);
	}
	
	private void initNaviButtons(){
		NaviButton btnStartseite = new NaviButton(CCM_Constants.VIEW_NAME_START, FontAwesome.HOME);
		this.addComponent(btnStartseite);
		
		NaviButton btnBenutzer = new NaviButton(CCM_Constants.VIEW_NAME_BENUTZER, FontAwesome.GROUP);
		this.addComponent(btnBenutzer);
		
		NaviButton btnUnternehmen = new NaviButton(CCM_Constants.VIEW_NAME_UNTERNEHMEN, FontAwesome.BUILDING);
		this.addComponent(btnUnternehmen);
		
		NaviButton btnRolle = new NaviButton(CCM_Constants.VIEW_NAME_ROLLE, FontAwesome.USER);
		this.addComponent(btnRolle);
		
		NaviButton btnBesuch = new NaviButton(CCM_Constants.VIEW_NAME_TERMIN, FontAwesome.CALENDAR);
		this.addComponent(btnBesuch);
	}
	
	
	private class NaviButton extends Button{
		private static final long serialVersionUID = 1L;

		private NaviButton(String viewName){
			this.setWidth("100%");
			this.setCaption(viewName);
			this.addClickListener(listener ->{
				navNavigator.navigateTo(viewName);
			});
		}
		
		private NaviButton(String viewName, FontAwesome icon){
			this(viewName);
			this.setIcon(icon);
			this.setStyleName(ValoTheme.BUTTON_ICON_ALIGN_RIGHT);
			this.addStyleName("navi");
		}
		
	}

}
