package com.dhbwProject.backend.beans;

public class Adresse {

	private int id;
	private String plz;
	private String ort;
	private String strasse;
	private String hausnummer;
	private Unternehmen unternehmen;

	public Adresse(int id, String plz, String ort, String strasse, String hausnummer, Unternehmen unternehmen) {
		super();
		this.id = id;
		this.plz = plz;
		this.ort = ort;
		this.strasse = strasse;
		this.hausnummer = hausnummer;
		this.unternehmen = unternehmen;
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


	public String getStrasse() {
		return strasse;
	}

	public String getHausnummer() {
		return hausnummer;
	}
	
	public Unternehmen getUnternehmen(){
		return unternehmen;
	}

}
