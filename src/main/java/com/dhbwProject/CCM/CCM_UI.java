package com.dhbwProject.CCM;

import java.sql.SQLException;

import javax.servlet.annotation.WebServlet;

import com.dhbwProject.backend.CCM_Constants;
import com.dhbwProject.backend.dbConnect;
import com.dhbwProject.views.FooterView;
import com.dhbwProject.views.HeaderView;
import com.dhbwProject.views.ViewBenutzer;
import com.dhbwProject.views.ViewBesuch;
import com.dhbwProject.views.ViewLogin;
import com.dhbwProject.views.ViewStartseite;
import com.dhbwProject.views.ViewUnternehmen;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@Theme("CCM_Theme")
public class CCM_UI extends UI {
	private static final long serialVersionUID = 1L;
	private dbConnect dbConnection;
	
	private Panel pnlContent;
	private VerticalLayout vlFormat;
	private HorizontalLayout hlContent;
	
	private HeaderView header;
	private Navigationsbar naviBar;
	private FooterView footer;
	
//	Direkte Initialisierung wegen dem Navigator
	private Panel pnlViews;	
	
	@Override
    protected void init(VaadinRequest vaadinRequest) {
		try {
			this.dbConnection = new dbConnect();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try{
			VaadinSession.getCurrent().lock();
			VaadinSession.getCurrent().getSession().setAttribute(CCM_Constants.SESSION_VALUE_CONNECTION, this.dbConnection);
		}finally{
			VaadinSession.getCurrent().unlock();
		}
		
		this.addDetachListener(detach ->{
			try {
				this.dbConnection.close();
				System.out.println("Session detached");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
		
		this.setSizeFull();		
		this.initViewNavigator();
		this.initContentLayout();
		this.initFormatLayout();
		
		this.pnlContent = new Panel();
		this.pnlContent.setStyleName(ValoTheme.PANEL_BORDERLESS);
		this.pnlContent.setContent(this.vlFormat);
		this.setContent(this.pnlContent);
		Responsive.makeResponsive(pnlContent);
		int hoch =UI.getCurrent().getPage().getBrowserWindowHeight();
		int breit = UI.getCurrent().getPage().getBrowserWindowWidth();
		System.out.println("Höhe:"+ hoch + ", Breite " + breit);
		this.getNavigator().navigateTo(CCM_Constants.VIEW_NAME_LOGIN);
    }

    @WebServlet(urlPatterns = "/*", name = "CCM_UIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = CCM_UI.class, productionMode = false)
    public static class CCM_UIServlet extends VaadinServlet {
		private static final long serialVersionUID = 1L;
    }
    
    private void initFormatLayout(){
    	this.vlFormat = new VerticalLayout();
    	this.vlFormat.setSizeFull();
    	this.header = new HeaderView();
    	this.header.setSizeFull();
    	this.vlFormat.addComponent(this.header);
    	this.vlFormat.setExpandRatio(this.header, 2);
    	this.vlFormat.addComponent(this.hlContent);
    	this.vlFormat.setExpandRatio(this.hlContent, 8);
    	Responsive.makeResponsive(vlFormat);
    	Responsive.makeResponsive(hlContent);
    	
    }
    
    private void initContentLayout(){
		this.hlContent = new HorizontalLayout();
		this.hlContent.setSizeFull();
		this.initNavigationBar();
		this.initViewPanel();
	}
	
	private void initNavigationBar(){
		this.naviBar = new Navigationsbar((CCM_Navigator)this.getNavigator());
		if(VaadinSession.getCurrent().getSession().getAttribute(CCM_Constants.SESSION_VALUE_USER) == null)	
			this.naviBar.setVisible(false);
		else
			this.naviBar.setVisible(true);
		
		
		this.hlContent.addComponent(this.naviBar);
		this.hlContent.setExpandRatio(this.naviBar, 1);
	}
	
	private void initViewPanel(){	
		this.hlContent.addComponent(this.pnlViews);
		this.hlContent.setExpandRatio(this.pnlViews, 5);
		this.pnlViews.setSizeFull();
		Responsive.makeResponsive(hlContent);
	}
	
	private void initViewNavigator(){
		this.pnlViews = new Panel();
		this.pnlViews.setStyleName("pnviews");
		Responsive.makeResponsive(pnlViews);
		this.pnlViews.addStyleName(ValoTheme.PANEL_BORDERLESS);
		this.setNavigator(new CCM_Navigator(this, this.pnlViews));
		this.getNavigator().addView(CCM_Constants.VIEW_NAME_LOGIN, ViewLogin.class);
		this.getNavigator().addView(CCM_Constants.VIEW_NAME_START, ViewStartseite.class);
		this.getNavigator().addView(CCM_Constants.VIEW_NAME_BENUTZER, ViewBenutzer.class);
		this.getNavigator().addView(CCM_Constants.VIEW_NAME_UNTERNEHMEN, ViewUnternehmen.class);
		this.getNavigator().addView(CCM_Constants.VIEW_NAME_BESUCH, ViewBesuch.class);
//		this.getNavigator().addView(CCM_Constants.VIEW_NAME_TERMIN, new ViewTermin(this.dummyData));
		
		/*	Dieser ViewChangeListener prueft ob sich ein Benutzer an der Session angemeldet hat
		 * 	Ist das nicht der Fall so ist lediglich der Login anzuzeigen*/
		this.getNavigator().addViewChangeListener(new ViewChangeListener() {
			private static final long serialVersionUID = 1L;

			@Override
            public boolean beforeViewChange(ViewChangeEvent event) {
                boolean isLoggedIn = VaadinSession.getCurrent().getSession().getAttribute(CCM_Constants.SESSION_VALUE_USER) != null;
                boolean isLoginView = event.getNewView() instanceof ViewLogin;
                if(isLoggedIn && !naviBar.isVisible()){
                	naviBar.refreshVisibilityByRolle();
                	naviBar.setVisible(true);
                }
                 //	Benutzer user = (Benutzer)VaadinSession.getCurrent().getSession().getAttribute(CCM_Constants.SESSION_VALUE_USER);
                	//header.setStatustext("Herzlich willkommen, "+ "" + user.getVorname() + " " + user.getNachname()  );

                if (!isLoggedIn && !isLoginView) {
                	naviBar.setVisible(false); //das sollte ansich auch wieder rauskönnen
                    getNavigator().navigateTo(CCM_Constants.VIEW_NAME_LOGIN);
                 //   header.setStatustext("Hallo, bitte melden Sie sich am CCM System an.");
                    return false;

                } else if (isLoggedIn && isLoginView)
                	return false;
                return true;
            }

			@Override
			public void afterViewChange(ViewChangeEvent event) {
				boolean isLoggedIn = VaadinSession.getCurrent().getSession().getAttribute(CCM_Constants.SESSION_VALUE_USER) != null;
				if(!isLoggedIn)
					naviBar.setVisible(false);
			}
		});
	}
}
