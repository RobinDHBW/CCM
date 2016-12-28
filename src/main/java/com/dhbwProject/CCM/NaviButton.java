package com.dhbwProject.CCM;

import com.vaadin.navigator.Navigator;
import com.vaadin.ui.Button;
import com.vaadin.ui.themes.ValoTheme;

public class NaviButton extends Button {
	private static final long serialVersionUID = 1L;

	public NaviButton(String viewName, Navigator nav){
		this.setCaptionAsHtml(true);
		//this.setCaption("<font color=#35adcc><left>"+viewName+"</left></font>");
		this.setCaption(viewName);
		this.setWidth("100%");
		//this.setStyleName(ValoTheme.BUTTON_BORDERLESS);
		this.setStyleName("navi");
		this.addClickListener(listener -> {
			nav.navigateTo(viewName);
		});
	}

}
