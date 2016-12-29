package com.dhbwProject.backend.beans;

import java.util.Date;
import java.util.LinkedList;

public class Besuch {
	
	private int id;
	private String name;
	private Date startDate;
	private Date endDate;
	private Unternehmen unternehmen;
	private Ansprechpartner ansprechpartner;
	private LinkedList<Benutzer> besucher;
	private Status status;
	
	public Besuch(int id, String name, Date startDate, Date endDate, Unternehmen unternehmen, Status status,
			Ansprechpartner ansprechpartner, LinkedList<Benutzer> besucher) {
		super();
		this.id = id;
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
		this.unternehmen = unternehmen;
		this.ansprechpartner = ansprechpartner;
		this.besucher = besucher;
		this.status = status;
	}

	public Besuch() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Date getStartDate() {
		return startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public Unternehmen getUnternehmen() {
		return unternehmen;
	}

	public Ansprechpartner getAnsprechpartner() {
		return ansprechpartner;
	}

	public LinkedList<Benutzer> getBesucher() {
		return besucher;
	}

	public Status getStatus() {
		return status;
	}
	

	//folgende Tabellen der DB müssenn noch im Bean implementiert werden:
	//besuch, status, benutzer_besuch
}
