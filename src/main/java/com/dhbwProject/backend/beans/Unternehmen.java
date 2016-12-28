package com.dhbwProject.backend.beans;

import java.util.LinkedList;

public class Unternehmen {
	
	public Unternehmen(int id, String name, LinkedList<Ansprechpartner> lAnsprechpartner) {
		super();
		this.id = id;
		this.name = name;
		this.lAnsprechpartner = lAnsprechpartner;
	}
	public Unternehmen() {
		// TODO Auto-generated constructor stub
	}
	private int id;
	private String name;
	private LinkedList<Ansprechpartner> lAnsprechpartner;


	
	

	//folgende Tabellen der DB müssenn noch im Bean implementiert werden:
	//unternehmen, ort
}
