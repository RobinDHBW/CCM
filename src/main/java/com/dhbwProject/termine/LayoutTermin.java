package com.dhbwProject.termine;

import com.vaadin.ui.Panel;

public class LayoutTermin extends Panel {
	private static final long serialVersionUID = 1L;
	
	private TerminAnlage anlage;
	
	public LayoutTermin(){
		this.anlage = new TerminAnlage();
		this.setContent(this.anlage);
	}

}
