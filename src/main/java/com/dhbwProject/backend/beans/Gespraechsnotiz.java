package com.dhbwProject.backend.beans;

import java.util.Date;

public class Gespraechsnotiz {
	
	private int notiz_id;
	private String notiz_datei; // tbd: blob
	private String notiz_bild; // tbd: blob
	private Unternehmen unternehmen;
	private Besuch besuch;
	private Date notiz_timestamp;
	
	public Gespraechsnotiz(int notiz_id, String notiz_datei, String notiz_bild, Unternehmen unternehmen, Besuch besuch,
			Date notiz_timestamp) {
		super();
		this.notiz_id = notiz_id;
		this.notiz_datei = notiz_datei;
		this.notiz_bild = notiz_bild;
		this.unternehmen = unternehmen;
		this.besuch = besuch;
		this.notiz_timestamp = notiz_timestamp;
	}

	public String getNotiz_datei() {
		return notiz_datei;
	}

//	public void setNotiz_datei(String notiz_datei) {
//		this.notiz_datei = notiz_datei;
//	}

	public String getNotiz_bild() {
		return notiz_bild;
	}

//	public void setNotiz_bild(String notiz_bild) {
//		this.notiz_bild = notiz_bild;
//	}

	public Unternehmen getUnternehmen() {
		return unternehmen;
	}

//	public void setUnternehmen(Unternehmen unternehmen) {
//		this.unternehmen = unternehmen;
//	}

	public Besuch getBesuch() {
		return besuch;
	}

//	public void setBesuch(Besuch besuch) {
//		this.besuch = besuch;
//	}

	public int getNotiz_id() {
		return notiz_id;
	}

	public Date getNotiz_timestamp() {
		return notiz_timestamp;
	}
	

	//folgende Tabellen der DB müssenn noch im Bean implementiert werden:
	//gespraechsnotizen -> done ss281216
}
