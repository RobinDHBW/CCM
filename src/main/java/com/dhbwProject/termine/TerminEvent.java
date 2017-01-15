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
		super.setDescription(b.getStartDate().toString()+" bis "+b.getEndDate().toString());
		super.setStart(b.getStartDate());
		super.setEnd(b.getEndDate());
		//Hier wird der Stylename gesetzt damit die Events im Kalender richtig angezeigt werden.
		//super.setStyleName(b.getStatus().getBezeichnung()); 
		super.setStyleName("geplant"); //benötigt für die Anzeige auf der Oberfläche// Jasmin
	}
	
	public void setBesuch(Besuch b){
		this.termin = b;
	}
	
	public Besuch getBesuch(){
		return this.termin;
	}

}
