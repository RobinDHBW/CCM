package com.dhbwProject.backend.beans;


// Simon: Wird nicht mehr verwendet
public class Berechtigung {
	
	private int id;
	private String bezeichnung;
	
	public Berechtigung(int id, String bezeichnung) {
		super();
		this.id = id;
		this.bezeichnung = bezeichnung;
	}

	public int getId() {
		return id;
	}
	public String getBezeichnung() {
		return bezeichnung;
	}
	

}
