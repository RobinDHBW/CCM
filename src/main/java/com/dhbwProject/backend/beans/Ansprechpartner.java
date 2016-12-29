package com.dhbwProject.backend.beans;

public class Ansprechpartner {
	public Ansprechpartner(int id, String vorname, String nachname, Adresse adresse) {
		super();
		this.id = id;
		this.vorname = vorname;
		this.nachname = nachname;
		this.adresse = adresse;
	}

	private int id;
	private String vorname;
	private String nachname;
	private Adresse adresse;

	public Ansprechpartner() {
		// TODO Auto-generated constructor stub
	}

	public String getNachname() {
		return nachname;
	}

	public Adresse getAdresse() {
		return adresse;
	}

	public int getId() {
		return id;
	}

	public String getVorname() {
		return vorname;
	}

}
