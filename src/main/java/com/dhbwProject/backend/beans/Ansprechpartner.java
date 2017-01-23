package com.dhbwProject.backend.beans;

import java.util.LinkedList;

public class Ansprechpartner {
	public Ansprechpartner(int id, String vorname, String nachname, Adresse adresse,
			LinkedList<Studiengang> lStudiengang, String email, String telefonnummer) {
		super();
		this.id = id;
		this.vorname = vorname;
		this.nachname = nachname;
		this.adresse = adresse;
		this.lStudiengang = lStudiengang;
		this.email = email;
		this.telefonnummer = telefonnummer;
	}

	private int id;
	private String vorname;
	private String nachname;
	private Adresse adresse;
	private LinkedList<Studiengang> lStudiengang;
	private String email;
	private String telefonnummer;

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

	public void setId(int id) {
		this.id = id;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	public void setNachname(String nachname) {
		this.nachname = nachname;
	}

	public void setAdresse(Adresse adresse) {
		this.adresse = adresse;
	}

	public void setlStudiengang(LinkedList<Studiengang> lStudiengang) {
		this.lStudiengang = lStudiengang;
	}

	public String getEmailadresse() {
		return email;
	}

	public String getTelefonnummer() {
		return telefonnummer;
	}

}
