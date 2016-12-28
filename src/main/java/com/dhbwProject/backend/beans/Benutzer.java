package com.dhbwProject.backend.beans;

public class Benutzer {
	
	private String vorname;
	private String nachname;
	private String id;
	private String beruf;
	private String rolle;

	public Benutzer(String string, String string2, String string3, String string4, String string5) {
		id = string;
		vorname = string2;
		nachname = string3;
		beruf = string4;
		rolle = string5;
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

	public String getBeruf() {
		return beruf;
	}
//
//	public void setBeruf(String beruf) {
//		this.beruf = beruf;
//	}

	public String getRolle() {
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
