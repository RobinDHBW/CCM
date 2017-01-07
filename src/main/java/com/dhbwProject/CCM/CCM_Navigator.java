package com.dhbwProject.CCM;

import java.util.LinkedList;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.ui.SingleComponentContainer;
import com.vaadin.ui.UI;

public class CCM_Navigator extends Navigator{
	private static final long serialVersionUID = 1L;
	
	private LinkedList<String> lViews;
	
	public CCM_Navigator(UI ui, SingleComponentContainer container){
		super(ui, container);
		this.lViews = new LinkedList<String>();
	}
	
	@Override
	public void addView(String viewName, Class<? extends View> viewClass){
		super.addView(viewName, viewClass);
		this.lViews.add(viewName);
	}
	
	@Override
	public void addView(String viewName, View view){
		super.addView(viewName, view);
		this.lViews.add(viewName);
	}
	
	public LinkedList<String> getViews(){
		return this.lViews;
	}

}
