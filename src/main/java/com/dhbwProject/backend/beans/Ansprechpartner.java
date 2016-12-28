package com.dhbwProject.backend.beans;

/*	Ich mach hier lediglich Id, Name, Vorname und Unternehmen rein,
 * 	das reicht für meine Zwecke 
 * */

public class Ansprechpartner {

	private int id;
	private String vorname;
	private String nachname;
	private Unternehmen unternehmen;
	
	public Ansprechpartner(int id) {
		this.id = id;
	}
	
	public Ansprechpartner(int id, String vorname, String nachname, Unternehmen unternehmen){
		this(id);
		this.vorname = vorname;
		this.nachname = nachname;
		this.unternehmen = unternehmen;
	}

	public int getId() {
		return id;
	}

//	public void setId(int id) {
//		this.id = id;
//	}

	public String getVorname() {
		return vorname;
	}

//	public void setVorname(String vorname) {
//		this.vorname = vorname;
//	}

	public String getNachname() {
		return nachname;
	}

//	public void setNachname(String nachname) {
//		this.nachname = nachname;
//	}

	public Unternehmen getUnternehmen() {
		return unternehmen;
	}


//	public void setUnternehmen(Unternehmen unternehmen) {
//		this.unternehmen = unternehmen;
//	}

}
