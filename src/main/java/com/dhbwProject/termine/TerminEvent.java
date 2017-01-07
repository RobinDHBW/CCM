package com.dhbwProject.termine;

import com.dhbwProject.backend.beans.Besuch;
import com.vaadin.ui.components.calendar.event.BasicEvent;

public class TerminEvent extends BasicEvent {
	private static final long serialVersionUID = 1L;
	private Besuch termin;
	
	public TerminEvent(Besuch b){
		super();
		this.termin = b;
		super.setCaption(b.getName());	
		super.setStart(b.getStartDate());
		super.setEnd(b.getEndDate());		
	}
	
	public void setBesuch(Besuch b){
		this.termin = b;
	}
	
	public Besuch getBesuch(){
		return this.termin;
	}

}
