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


	//folgende Tabellen der DB müssenn noch im Bean implementiert werden:
	//ansprechpartner, adresse, ort, studiengang_ansprechpartner

//	public void setVorname(String vorname) {
//		this.vorname = vorname;
//	}

	public String getNachname() {
		return nachname;
	}

//	public void setNachname(String nachname) {
//		this.nachname = nachname;
//	}

	public Adresse getAdresse() {
		return adresse;
	}


	public int getId() {
		return id;
	}


	public String getVorname() {
		return vorname;
	}


//	public void setUnternehmen(Unternehmen unternehmen) {
//		this.unternehmen = unternehmen;
//	}


}
