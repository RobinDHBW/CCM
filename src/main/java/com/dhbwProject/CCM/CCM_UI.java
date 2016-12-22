package com.dhbwProject.CCM;

import javax.servlet.annotation.WebServlet;

import com.dhbwProject.backend.CCM_Constants;
import com.dhbwProject.views.ViewBenutzer;
import com.dhbwProject.views.ViewStartseite;
import com.dhbwProject.views.ViewTermin;
import com.dhbwProject.views.ViewUnternehmen;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;

@Theme("CCM_Theme")
public class CCM_UI extends UI {
	private static final long serialVersionUID = 1L;
	private HorizontalLayout hlContent;
	private CssLayout clNavigation;
	private final Panel pnlViews = new Panel();
	private Navigator navigator;
	
	
	@Override
    protected void init(VaadinRequest vaadinRequest) {
		this.setSizeFull();
		this.initViewNavigator();
		this.initContentLayout();
		this.initNavigationLayout();
		this.initViewPanel();
		this.setContent(this.hlContent);
    }

    @WebServlet(urlPatterns = "/*", name = "CCM_UIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = CCM_UI.class, productionMode = false)
    public static class CCM_UIServlet extends VaadinServlet {
		private static final long serialVersionUID = 1L;
    }
    
    private void initContentLayout(){
		this.hlContent = new HorizontalLayout();
		this.hlContent.setSizeFull();
	}
	
	private void initNavigationLayout(){
		this.clNavigation = new CssLayout();
		this.clNavigation.setSizeFull();
		this.clNavigation.addComponent(new NaviButton(CCM_Constants.VIEW_NAME_START, this.navigator));
		this.clNavigation.addComponent(new NaviButton(CCM_Constants.VIEW_NAME_BENUTZER, this.navigator));
		this.clNavigation.addComponent(new NaviButton(CCM_Constants.VIEW_NAME_UNTERNEHMEN, this.navigator));
		this.clNavigation.addComponent(new NaviButton(CCM_Constants.VIEW_NAME_TERMIN, this.navigator));	
		this.hlContent.addComponent(this.clNavigation);
		this.hlContent.setExpandRatio(this.clNavigation, 1);
	}
	
	private void initViewPanel(){	
		this.hlContent.addComponent(this.pnlViews);
		this.hlContent.setExpandRatio(this.pnlViews, 7);
		this.pnlViews.setSizeFull();	
	}
	
	private void initViewNavigator(){
		this.navigator = new Navigator(this, this.pnlViews);
		this.navigator.addView(CCM_Constants.VIEW_NAME_START, new ViewStartseite());
		this.navigator.addView(CCM_Constants.VIEW_NAME_BENUTZER, new ViewBenutzer());
		this.navigator.addView(CCM_Constants.VIEW_NAME_UNTERNEHMEN, new ViewUnternehmen());
		this.navigator.addView(CCM_Constants.VIEW_NAME_TERMIN, new ViewTermin());
		this.navigator.navigateTo(CCM_Constants.VIEW_NAME_START);
	}
}
