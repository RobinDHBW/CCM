package com.dhbwProject.backend.beans;

import java.util.LinkedList;

public class Benutzer {
	
	private String vorname;
	private String nachname;
	private String id;
	private Beruf beruf;
	private Rolle rolle;
	private LinkedList<Studiengang> lStudiengang;
	private String email;
	private String telefon;
	private Boolean inaktiv;

	public Benutzer(String id, String vorname, String nachname, Beruf beruf, Rolle rolle, LinkedList<Studiengang> lStudiengang, String email, String telefon) {
		this.id = id;
		this.vorname = vorname;
		this.nachname = nachname;
		this.beruf = beruf;
		this.rolle = rolle;
		this.lStudiengang = lStudiengang;
		this.email = email;
		this.telefon = telefon;
		this.inaktiv = null;
	}
	public Benutzer(String id, String vorname, String nachname, Beruf beruf, Rolle rolle, LinkedList<Studiengang> lStudiengang, String email, String telefon, boolean inaktiv) {
		this.id = id;
		this.vorname = vorname;
		this.nachname = nachname;
		this.beruf = beruf;
		this.rolle = rolle;
		this.lStudiengang = lStudiengang;
		this.email = email;
		this.telefon = telefon;
		this.inaktiv = new Boolean(inaktiv);
	}
	
	public boolean getInaktiv(){
		if(inaktiv == null) return false;
		return inaktiv.booleanValue();
	}
	
	public void setInaktiv(boolean pInaktiv){
		inaktiv = new Boolean(pInaktiv);
	}

	public String getEmail() {
		return email;
	}

	public String getTelefon() {
		return telefon;
	}

	public String getVorname() {
		return vorname;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	public String getNachname() {
		return nachname;
	}

	public void setNachname(String nachname) {
		this.nachname = nachname;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Beruf getBeruf() {
		return beruf;
	}

	public void setBeruf(Beruf beruf) {
		this.beruf = beruf;
	}

	public Rolle getRolle() {
		return rolle;
	}

	public void setRolle(Rolle rolle) {
		this.rolle = rolle;
	}

	@Override
	public String toString() {
		return "Benutzer [vorname=" + vorname + ", nachname=" + nachname + ", id=" + id + ", beruf=" + beruf
				+ ", rolle=" + rolle + ", lStudiengang=" + lStudiengang + "]";
	}
	
	  @Override
	  public boolean equals(Object o) {
	      if (this == o) return true;
	      if (o == null || getClass() != o.getClass()) return false;
	      Benutzer b = (Benutzer) o;
	      return this.getId().equals(b.getId());
	  }

	public void setlStudiengang(LinkedList<Studiengang> lStudiengang) {
		this.lStudiengang = lStudiengang;
	}

	public LinkedList<Studiengang> getStudiengang() {
		return lStudiengang;
	}

}
