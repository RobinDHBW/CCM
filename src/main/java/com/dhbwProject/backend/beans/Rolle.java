package com.dhbwProject.backend.beans;

public class Rolle {
	
	private StringBuffer berechtigung;
	private String bezeichnung;
	private int id;

	public Rolle(StringBuffer berechtigung, String bezeichnung, int id) {
		super();
		this.berechtigung = berechtigung;
		this.bezeichnung = bezeichnung;
		this.id = id;
	}


	public StringBuffer getBerechtigung() {
		return berechtigung;
	}

	public String getBezeichnung() {
		return bezeichnung;

	}

	public int getId() {
		return id;
	}

}
