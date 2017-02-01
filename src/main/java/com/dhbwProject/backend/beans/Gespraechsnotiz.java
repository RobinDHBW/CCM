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
	private Date timestamp;
	private Benutzer autor;
	
	public Gespraechsnotiz(int id, byte[] notiz, byte[] bild, Unternehmen unternehmen, Besuch besuch, Date timestamp, Benutzer autor) {
		super();
		this.id = id;
		this.notiz = notiz;
		this.bild = bild;
		this.unternehmen = unternehmen;
		this.besuch = besuch;
		this.timestamp = timestamp;
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

	public Date getTimestamp() {
		return timestamp;
	}
	public Benutzer getAutor(){
		return autor;
	}

	//folgende Tabellen der DB müssenn noch im Bean implementiert werden:
	//gespraechsnotizen

}
