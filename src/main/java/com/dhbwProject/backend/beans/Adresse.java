package com.dhbwProject.backend.beans;

public class Adresse {

	private int id;
	private String plz;
	private String ort;
	private Unternehmen unternehmen;
	private String strasse;
	private String hausnummer;

	public Adresse(int id, String plz, String ort, Unternehmen unternehmen, String strasse, String hausnummer) {
		super();
		this.id = id;
		this.plz = plz;
		this.ort = ort;
		this.unternehmen = unternehmen;
		this.strasse = strasse;
		this.hausnummer = hausnummer;
	}

	public int getId() {
		return id;
	}

	public String getPlz() {
		return plz;
	}

	public String getOrt() {
		return ort;
	}

	public Unternehmen getUnternehmen() {
		return unternehmen;
	}

	public String getStrasse() {
		return strasse;
	}

	public String getHausnummer() {
		return hausnummer;
	}

}
