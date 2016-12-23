package com.dhbwProject.CCM;

import javax.servlet.annotation.WebServlet;

import com.dhbwProject.backend.CCM_Constants;
import com.dhbwProject.views.ViewBenutzer;
import com.dhbwProject.views.ViewLogin;
import com.dhbwProject.views.ViewStartseite;
import com.dhbwProject.views.ViewTermin;
import com.dhbwProject.views.ViewUnternehmen;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
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
	
	@Override
    protected void init(VaadinRequest vaadinRequest) {
		this.setSizeFull();		
		this.initViewNavigator();
		this.initContentLayout();
		this.initNavigationLayout();
		this.initViewPanel();
		this.setContent(this.hlContent);
		this.getNavigator().navigateTo(CCM_Constants.VIEW_NAME_LOGIN);
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
		this.clNavigation.addComponent(new NaviButton(CCM_Constants.VIEW_NAME_START, this.getNavigator()));
		this.clNavigation.addComponent(new NaviButton(CCM_Constants.VIEW_NAME_BENUTZER, this.getNavigator()));
		this.clNavigation.addComponent(new NaviButton(CCM_Constants.VIEW_NAME_UNTERNEHMEN, this.getNavigator()));
		this.clNavigation.addComponent(new NaviButton(CCM_Constants.VIEW_NAME_TERMIN, this.getNavigator()));	
		if(this.getSession().getAttribute("user") == null){
			this.clNavigation.setVisible(false);
		}else
			this.clNavigation.setVisible(true);
		this.hlContent.addComponent(this.clNavigation);
		this.hlContent.setExpandRatio(this.clNavigation, 1);
	}
	
	private void initViewPanel(){	
		this.hlContent.addComponent(this.pnlViews);
		this.hlContent.setExpandRatio(this.pnlViews, 7);
		this.pnlViews.setSizeFull();	
	}
	
	private void initViewNavigator(){
		
		this.setNavigator(new Navigator(this, this.pnlViews));
		this.getNavigator().addView(CCM_Constants.VIEW_NAME_LOGIN, ViewLogin.class);
		this.getNavigator().addView(CCM_Constants.VIEW_NAME_START, ViewStartseite.class);
		this.getNavigator().addView(CCM_Constants.VIEW_NAME_BENUTZER, ViewBenutzer.class);
		this.getNavigator().addView(CCM_Constants.VIEW_NAME_UNTERNEHMEN, ViewUnternehmen.class);
		this.getNavigator().addView(CCM_Constants.VIEW_NAME_TERMIN, ViewTermin.class);
		
		this.getNavigator().addViewChangeListener(new ViewChangeListener() {
			private static final long serialVersionUID = 1L;

			@Override
            public boolean beforeViewChange(ViewChangeEvent event) {
                boolean isLoggedIn = getSession().getAttribute("user") != null;
                boolean isLoginView = event.getNewView() instanceof ViewLogin;
                if(isLoggedIn && !clNavigation.isVisible())
                	clNavigation.setVisible(true);

                if (!isLoggedIn && !isLoginView) {
                    getNavigator().navigateTo(CCM_Constants.VIEW_NAME_LOGIN);
                    return false;

                } else if (isLoggedIn && isLoginView)
                	return false;
                return true;
            }

			@Override
			public void afterViewChange(ViewChangeEvent event) {
				
			}
		});
	}
}
