package com.dhbwProject.backend.beans;

public class Studiengang {
	
	private int id;
	private String bezeichnung;
	
	public Studiengang(int id, String bezeichnung) {
		super();
		this.id = id;
		this.bezeichnung = bezeichnung;
	}
	
	public int getId() {
		return id;
	}
	public String getBezeichnung() {
		return bezeichnung;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Studiengang other = (Studiengang) obj;
		if (bezeichnung == null) {
			if (other.bezeichnung != null)
				return false;
		} else if (!bezeichnung.equals(other.bezeichnung))
			return false;
		return true;
	}

}
