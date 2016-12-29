package com.dhbwProject.backend.beans;

import java.util.Date;

import com.mysql.jdbc.Blob;

public class Gespraechsnotiz {
	
	private int id;
	private java.sql.Blob notiz;
	private java.sql.Blob bild;
	private Unternehmen unternehmen;
	private Besuch besuch;
	private Date timestamp;
	
	public Gespraechsnotiz(int id, java.sql.Blob notiz, java.sql.Blob bild, Unternehmen unternehmen, Besuch besuch, Date timestamp) {
		super();
		this.id = id;
		this.notiz = notiz;
		this.bild = bild;
		this.unternehmen = unternehmen;
		this.besuch = besuch;
		this.timestamp = timestamp;
	}


	public int getId() {
		return id;
	}

	public java.sql.Blob getNotiz() {
		return notiz;
	}

	public java.sql.Blob getBild() {
		return bild;
	}

	public Unternehmen getUnternehmen() {
		return unternehmen;
	}

	public Besuch getBesuch() {
		return besuch;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	//folgende Tabellen der DB müssenn noch im Bean implementiert werden:
	//gespraechsnotizen

}
