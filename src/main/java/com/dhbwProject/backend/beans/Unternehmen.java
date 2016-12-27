package com.dhbwProject.backend.beans;

import java.util.LinkedList;

/*	Ich mach hier lediglich eine Id, einen Namen und eine Ansprechpartnerliste rein, 
 * das reicht für meine Zwecke erstmal
 * */

public class Unternehmen {
	
	private int id;
	private String name;
	private LinkedList<Ansprechpartner> ansprechpartner;

	public Unternehmen(int id) {
		this.id = id;
	}
	
	public Unternehmen(int id, String name, LinkedList<Ansprechpartner> lAnsprechpartner){
		this(id);
		this.name = name;
		this.ansprechpartner = lAnsprechpartner;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LinkedList<Ansprechpartner> getAnsprechpartner() {
		return ansprechpartner;
	}

	public void setAnsprechpartner(LinkedList<Ansprechpartner> ansprechpartner) {
		this.ansprechpartner = ansprechpartner;
	}

	
	
}
