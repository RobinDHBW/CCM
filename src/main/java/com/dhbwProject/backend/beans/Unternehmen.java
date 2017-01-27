package com.dhbwProject.backend.beans;

import java.util.LinkedList;

public class Unternehmen {
	
	private int id;
	private String name;
	//private LinkedList<Ansprechpartner> lAnsprechpartner;
	private String kennzeichen;
	
	//public Unternehmen(int id, String name, LinkedList<Ansprechpartner> lAnsprechpartner, String kennzeichen) {
	public Unternehmen(int id, String name, String kennzeichen) {
		super();
		this.id = id;
		this.name = name;
		//this.lAnsprechpartner = lAnsprechpartner;
		this.kennzeichen = kennzeichen;
	}
	public Unternehmen() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
//	public LinkedList<Ansprechpartner> getlAnsprechpartner() {
//		return lAnsprechpartner;
//	}
	public String getKennzeichen() {
		return kennzeichen;
	}

}
