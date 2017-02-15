package com.dhbwProject.backend.beans;

import java.io.File;
import java.util.Date;

import com.mysql.jdbc.Blob;

public class Gespraechsnotiz {
	
	private int id;
	private byte[] notiz;
	private byte[] bild;
	private Unternehmen unternehmen;
	private Besuch besuch;
	private java.sql.Timestamp timestamp;
	private Benutzer autor;
	
	public Gespraechsnotiz(int id, byte[] notiz, byte[] bild, Unternehmen unternehmen, Besuch besuch, Date timestamp, Benutzer autor) {
		super();
		this.id = id;
		this.notiz = notiz;
		this.bild = bild;
		this.unternehmen = unternehmen;
		this.besuch = besuch;
		this.timestamp = new java.sql.Timestamp(timestamp.getTime());
		this. autor = autor;
	}


	public int getId() {
		return id;
	}

	public byte[] getNotiz() {
		return notiz;
	}

	public byte[] getBild() {
		return bild;
	}

	public Unternehmen getUnternehmen() {
		return unternehmen;
	}

	public Besuch getBesuch() {
		return besuch;
	}

	public java.sql.Timestamp getTimestamp() {
		return timestamp;
	}
	public Benutzer getAutor(){
		return autor;
	}

	//folgende Tabellen der DB müssenn noch im Bean implementiert werden:
	//gespraechsnotizen

}
