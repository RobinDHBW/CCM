package com.dhbwProject.termine;


import com.dhbwProject.backend.beans.Besuch;
import com.vaadin.ui.components.calendar.event.BasicEvent;

public class TerminEvent extends BasicEvent {
	private static final long serialVersionUID = 1L;
	private int idTermin;
	
	public TerminEvent(int id){
		super();
		this.idTermin = id;
	}

	public int getIdTermin() {
		return idTermin;
	}

	public void setIdTermin(int idTermin) {
		this.idTermin = idTermin;
	}
}
