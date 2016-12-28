package com.dhbwProject.backend.beans;

import java.util.LinkedList;

public class Rolle {

	public Rolle() {
		// TODO Auto-generated constructor stub
	}

	//folgende Tabellen der DB müssenn noch im Bean implementiert werden:
	//rolle, berechtigung
	
	private LinkedList<Berechtigung> lBerechtigung;
	private String bezeichnung;
	private int id;

	public Rolle(int id, String bezeichnung, LinkedList<Berechtigung> berechtigung) {
		super();
		this.lBerechtigung = berechtigung;
		this.bezeichnung = bezeichnung;
		this.id = id;
	}


	public LinkedList<Berechtigung> getBerechtigung() {
		return lBerechtigung;
	}

	public String getBezeichnung() {
		return bezeichnung;

	}

	public int getId() {
		return id;
	}
}
