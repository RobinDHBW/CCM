package com.dhbwProject.besuche;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.dhbwProject.backend.beans.Besuch;
import com.vaadin.ui.components.calendar.event.BasicEvent;

public class BesuchEvent extends BasicEvent {
	private static final long serialVersionUID = 1L;
	private Besuch besuch;
	
	public BesuchEvent(Besuch b){
		super();
		this.besuch = b;
		super.setCaption(b.getName());	
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy, HH:mm");
		super.setDescription(dateFormat.format(b.getStartDate())+" bis "+dateFormat.format(b.getEndDate()));
		super.setStart(b.getStartDate());
		super.setEnd(b.getEndDate());
		//Hier wird der Stylename gesetzt damit die Events im Kalender richtig angezeigt werden.
		//super.setStyleName(b.getStatus().getBezeichnung()); 
		if(b.getStartDate().after(new Date()))
			super.setStyleName("geplant");
		else
			super.setStyleName("besucht"); //benötigt für die Anzeige auf der Oberfläche// Jasmin
	}
		
	
	public void setBesuch(Besuch b){
		this.besuch = b;
	}
	
	public Besuch getBesuch(){
		return this.besuch;
	}

}
