package com.dhbwProject.backend.beans;

import java.util.Date;
import java.util.LinkedList;

public class Besuch {
	
	private int id;
	private String name;
	private Date startDate;
	private Date endDate;
	private Adresse adresse;
	private Ansprechpartner ansprechpartner;
	private LinkedList<Benutzer> besucher;
	private Status status;
	private Date timestamp;
	private Benutzer autor;
	
	public Besuch(int id, String name, Date startDate, Date endDate, Adresse adresse, Status status,
			Ansprechpartner ansprechpartner, LinkedList<Benutzer> besucher, Date timestamp, Benutzer autor) {
		super();
		this.id = id;
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
		this.adresse = adresse;
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

	public Adresse getAdresse() {
		return adresse;
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
