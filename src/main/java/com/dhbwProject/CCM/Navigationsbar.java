package com.dhbwProject.CCM;

import com.dhbwProject.backend.CCM_Constants;
import com.dhbwProject.backend.beans.Benutzer;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

public class Navigationsbar extends CssLayout{
	private static final long serialVersionUID = 1L;
	private CCM_Navigator navNavigator;
	
	NaviButton btnStartseite;
	NaviButton btnBenutzer;
	NaviButton btnUnternehmen;
	NaviButton btnBesuch;
	
	public Navigationsbar(CCM_Navigator nav){
		this.setSizeFull();
		this.navNavigator = nav;
		
		/*TEMPORÄR
		 * */
		Button btnChangePW = new Button("PW ändern");
//		btnChangePW.setWidth("90%");
		btnChangePW.setStyleName("naviother");
		btnChangePW.addClickListener(click ->{
			PasswordChanger changer = new PasswordChanger(true);
			getUI().addWindow(changer);
		});
		//this.addComponent(btnChangePW);
		/*TEMPORÄR ENDE
		 * */
		
		
		
		this.initNaviButtons();
		//Temporär soll der PW Ändern Button unter die üblichen Navigationsbuttons und über den Logout Button
		this.addComponent(btnChangePW);
		this.initLogoutButton();
		
		if(VaadinSession.getCurrent().getSession().getAttribute(CCM_Constants.SESSION_VALUE_USER) != null)
			refreshVisibilityByRolle();
	}
	
	private void initLogoutButton(){
		Button btnLogout = new Button();
	//    btnLogout.setWidth("90%");
		btnLogout.setCaption("Abmelden");
		btnLogout.setIcon(FontAwesome.SIGN_OUT);
		btnLogout.setStyleName("naviother");
	//	btnLogout.setStyleName(ValoTheme.BUTTON_ICON_ALIGN_RIGHT);
		btnLogout.addClickListener(click ->{
			/*
			 * Hier sollte statt der Schleife eher ein gesamter Resett der UI hin
			 * sonst sind wir in "gewisser" Art statefull
			 * */
			for(Window w : this.getUI().getWindows())
				w.close();
			try{
				VaadinSession.getCurrent().lock();
				VaadinSession.getCurrent().getSession().setAttribute(CCM_Constants.SESSION_VALUE_USER, null);
			}finally{
				VaadinSession.getCurrent().unlock();
			}
			this.navNavigator.navigateTo(CCM_Constants.VIEW_NAME_LOGIN);
		
		});
		this.addComponent(btnLogout);
		Responsive.makeResponsive(btnLogout);
	}

	private void initNaviButtons(){
		btnStartseite = new NaviButton(CCM_Constants.VIEW_NAME_START, FontAwesome.HOME);
		this.addComponent(btnStartseite);
		
		btnBenutzer = new NaviButton(CCM_Constants.VIEW_NAME_BENUTZER, FontAwesome.GROUP);
		this.addComponent(btnBenutzer);
		
		btnUnternehmen = new NaviButton(CCM_Constants.VIEW_NAME_UNTERNEHMEN, FontAwesome.BUILDING);
		this.addComponent(btnUnternehmen);
		
		btnBesuch = new NaviButton(CCM_Constants.VIEW_NAME_BESUCH, FontAwesome.CALENDAR);
		this.addComponent(btnBesuch);
		Responsive.makeResponsive(btnStartseite);
		Responsive.makeResponsive(btnBenutzer);
		Responsive.makeResponsive(btnUnternehmen);
		Responsive.makeResponsive(btnBesuch);
	}
	
	protected void refreshVisibilityByRolle(){
		Benutzer bUser = (Benutzer)VaadinSession.getCurrent().getSession().getAttribute(CCM_Constants.SESSION_VALUE_USER);
		if(bUser.getRolle().getId() > 1)
			btnBenutzer.setVisible(false);
		else if(!btnBenutzer.isVisible())
			btnBenutzer.setVisible(true);
	}
	
	
	private class NaviButton extends Button{
		private static final long serialVersionUID = 1L;

		private NaviButton(String viewName){
			//this.setWidth("100%");
			this.setCaption(viewName);
			this.addClickListener(listener ->{
				navNavigator.navigateTo(viewName);
			});
		}
		
		private NaviButton(String viewName, FontAwesome icon){
			this(viewName);
			this.setIcon(icon);
			//this.setStyleName(ValoTheme.BUTTON_ICON_ALIGN_RIGHT);
			this.addStyleName("navi");
		}
		
	}

}
