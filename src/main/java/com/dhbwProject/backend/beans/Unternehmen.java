package com.dhbwProject.backend.beans;

import java.util.LinkedList;

public class Unternehmen {
	
	private int id;
	private String name;
	private LinkedList<Ansprechpartner> lAnsprechpartner;
	private LinkedList<Adresse> lAdresse;
	private String kennzeichen;
	
	public Unternehmen(int id, String name, LinkedList<Ansprechpartner> lAnsprechpartner, LinkedList<Adresse> lAdresse, String kennzeichen) {
		super();
		this.id = id;
		this.name = name;
		this.lAnsprechpartner = lAnsprechpartner;
		this.lAdresse = lAdresse;
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
	public LinkedList<Ansprechpartner> getlAnsprechpartner() {
		return lAnsprechpartner;
	}
	public LinkedList<Adresse> getlAdresse() {
		return lAdresse;
	}

}
