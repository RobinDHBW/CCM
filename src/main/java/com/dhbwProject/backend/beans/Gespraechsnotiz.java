package com.dhbwProject.backend.beans;

import java.util.Date;

import com.mysql.jdbc.Blob;

public class Gespraechsnotiz {
	
	private int id;
	private byte[] notiz;
	private byte[] bild;
	private Unternehmen unternehmen;
	private Besuch besuch;
	private Date timestamp;
	
	public Gespraechsnotiz(int id, byte[] notiz, byte[] bild, Unternehmen unternehmen, Besuch besuch, Date timestamp) {
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

	//folgende Tabellen der DB müssenn noch im Bean implementiert werden:
	//gespraechsnotizen

}
