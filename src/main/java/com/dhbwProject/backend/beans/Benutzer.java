package com.dhbwProject.backend.beans;

public class Benutzer {
	
	private String vorname;
	private String nachname;
	private String id;
	private Beruf beruf;
	private Rolle rolle;

	public Benutzer(String id, String vorname, String nachname, Beruf beruf, Rolle rolle) {
		this.id = id;
		this.vorname = vorname;
		this.nachname = nachname;
		this.beruf = beruf;
		this.rolle = rolle;
	}

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

	public String getId() {
		return id;
	}

//	public void setId(String id) {
//		this.id = id;
//	}

	public Beruf getBeruf() {
		return beruf;
	}
//
//	public void setBeruf(String beruf) {
//		this.beruf = beruf;
//	}

	public Rolle getRolle() {
		return rolle;
	}

//	public void setRolle(String rolle) {
//		this.rolle = rolle;
//	}

	@Override
	public String toString() {
		return "Benutzer Vorname=" + vorname + ", Nachname=" + nachname + ", ID=" + id + ", Beruf=" + beruf
				+ ", Rolle=" + rolle;
	}
	
	  @Override
	  public boolean equals(Object o) {
	      if (this == o) return true;
	      if (o == null || getClass() != o.getClass()) return false;
	      Benutzer b = (Benutzer) o;
	      return this.getId().equals(b.getId());
	  }

}
