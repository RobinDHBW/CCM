package com.dhbwProject.views;

import com.dhbwProject.backend.CCM_Constants;
import com.dhbwProject.unternehmen.LayoutUnternehmen;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.VerticalLayout;

public class ViewUnternehmen extends VerticalLayout implements View {
	private static final long serialVersionUID = 1L;
	private LayoutUnternehmen content;
	
	public ViewUnternehmen(){
	this.setSizeFull();
	this.setCaption(CCM_Constants.VIEW_NAME_UNTERNEHMEN);
	this.content = new LayoutUnternehmen();
	//Hier wird zunächst der Header eingefügt.
	this.addComponent(new HeaderView());
	this.addComponent(this.content);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}

}
