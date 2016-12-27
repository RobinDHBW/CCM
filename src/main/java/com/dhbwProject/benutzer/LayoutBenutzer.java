package com.dhbwProject.benutzer;

import com.dhbwProject.views.HeaderView;
import com.vaadin.ui.VerticalLayout;

public class LayoutBenutzer extends VerticalLayout {
	private static final long serialVersionUID = 1L;
	public LayoutBenutzer(){
		//Hier wird zunächst der Header eingefügt.
		this.addComponent(new HeaderView());
	}

}
