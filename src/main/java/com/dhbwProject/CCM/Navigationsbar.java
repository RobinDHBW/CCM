package com.dhbwProject.CCM;

import com.dhbwProject.backend.CCM_Constants;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;

public class Navigationsbar extends CssLayout{
	private static final long serialVersionUID = 1L;
	private CCM_Navigator navNavigator;
	
	public Navigationsbar(CCM_Navigator nav){
		this.setSizeFull();
		this.navNavigator = nav;
		this.initNaviButtons();
	}
	
	private void initNaviButtons(){
		for(String s : this.navNavigator.getViews()){
			if(!s.equals(CCM_Constants.VIEW_NAME_LOGIN))
			this.addComponent(new NaviButton(s));
		}
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
		
	}

}
