package com.dhbwProject.backend.beans;

public class Rolle {
<<<<<<< HEAD

	public Rolle() {
		// TODO folgende Tabellen der DB müssenn noch im Bean implementiert werden:rolle, berechtigung
=======
	
	public Rolle(StringBuffer berechtigung, String bezeichnung, int id) {
		super();
		this.berechtigung = berechtigung;
		this.bezeichnung = bezeichnung;
		this.id = id;
	}
	private StringBuffer berechtigung;
	private String bezeichnung;
	private int id;
	public StringBuffer getBerechtigung() {
		return berechtigung;
	}
	public String getBezeichnung() {
		return bezeichnung;
>>>>>>> refs/remotes/origin/master
	}
	public int getId() {
		return id;
	}



}
