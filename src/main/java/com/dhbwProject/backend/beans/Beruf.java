package com.dhbwProject.backend.beans;

public class Beruf {
	public Beruf(int id, String bezeichnung) {
		super();
		this.id = id;
		this.bezeichnung = bezeichnung;
	}
	private int id;
	private String bezeichnung;
	
	public int getId() {
		return id;
	}
	public String getBezeichnung() {
		return bezeichnung;
	}

}
