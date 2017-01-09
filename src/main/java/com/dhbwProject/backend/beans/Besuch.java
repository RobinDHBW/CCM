package com.dhbwProject.backend.beans;

import java.util.Date;
import java.util.LinkedList;

public class Besuch {
	
	private int id;
	private String name;
	private java.sql.Date startDate;
	private java.sql.Date endDate;
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
		this.startDate = new java.sql.Date(startDate.getTime());
		this.endDate = new java.sql.Date(endDate.getTime());
		this.adresse = adresse;
		this.ansprechpartner = ansprechpartner;
		this.besucher = besucher;
		this.status = status;
		this.autor = autor;
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

	public java.sql.Date getStartDate() {
		return startDate;
	}

	public java.sql.Date getEndDate() {
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

	public Benutzer getAutor() {
		return this.autor;
	}
	

	//folgende Tabellen der DB müssenn noch im Bean implementiert werden:
	//besuch, status, benutzer_besuch
}
