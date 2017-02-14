package com.dhbwProject.backend.beans;

import java.util.LinkedList;

public class Rolle {
	
//	private LinkedList<Berechtigung> lBerechtigung;
	private String bezeichnung;
	private int id;

//	public Rolle(int id, String bezeichnung, LinkedList<Berechtigung> berechtigung) {
	public Rolle(int id, String bezeichnung){
		super();
//		this.lBerechtigung = berechtigung;
		this.bezeichnung = bezeichnung;
		this.id = id;
	}


//	public LinkedList<Berechtigung> getBerechtigung() {
//		return lBerechtigung;
//	}

	public String getBezeichnung() {
		return bezeichnung;

	}

	public int getId() {
		return id;
	}
}
