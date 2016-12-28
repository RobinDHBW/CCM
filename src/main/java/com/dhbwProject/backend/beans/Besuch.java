package com.dhbwProject.backend.beans;

import java.util.Date;
import java.util.LinkedList;

public class Besuch {
	
	private int id;
	private String titel;
	private Date dateStart;
	private Date dateEnd;
	
	private Unternehmen unternehmen;
	private Gespraechsnotiz gespraechsnotiz;
	private Ansprechpartner ansprechpartner;
	private LinkedList<Benutzer> benutzer;
	
	public Besuch(int id) {
		this.id = id;
	}
	
	public Besuch(int id, String titel, Date dateStart, Date dateEnd, Unternehmen u, Gespraechsnotiz notiz,
			Ansprechpartner ansprechpartner, LinkedList<Benutzer> benutzer) {
		this(id);
		this.titel = titel;
		this.dateStart = dateStart;
		this.dateEnd = dateEnd;
		this.unternehmen = u;
		this.gespraechsnotiz = notiz;
		this.ansprechpartner = ansprechpartner;
		this.benutzer = benutzer;
	}
	
	
	public int getId() {
		return id;
	}

//	public void setId(int id) {
//		this.id = id;
//	}

	public String getTitel() {
		return titel;
	}

//	public void setTitel(String titel) {
//		this.titel = titel;
//	}

	public Unternehmen getUnternehmen() {
		return unternehmen;
	}

//	public void setUnternehmen(Unternehmen unternehmen) {
//		this.unternehmen = unternehmen;
//	}

	public Gespraechsnotiz getGespraechsnotiz() {
		return gespraechsnotiz;
	}

//	public void setGespraechsnotiz(Gespraechsnotiz gespraechsnotiz) {
//		this.gespraechsnotiz = gespraechsnotiz;
//	}

	public LinkedList<Benutzer> getBenutzer() {
		return benutzer;
	}
//
//	public void setBenutzer(LinkedList<Benutzer> benutzer) {
//		this.benutzer = benutzer;
//	}

	public Ansprechpartner getAnsprechpartner() {
		return ansprechpartner;
	}

//	public void setAnsprechpartner(Ansprechpartner ansprechpartner) {
//		this.ansprechpartner = ansprechpartner;
//	}

	public Date getDateStart() {
		return dateStart;
	}
//
//	public void setDateStart(Date dateStart) {
//		this.dateStart = dateStart;
//	}

	public Date getDateEnd() {
		return dateEnd;
	}

//	public void setDateEnd(Date dateEnd) {
//		this.dateEnd = dateEnd;
//	}


}
