package com.dhbwProject.backend;

import java.time.LocalDateTime;
import java.util.GregorianCalendar;
import java.util.LinkedList;

import com.dhbwProject.backend.beans.Adresse;
import com.dhbwProject.backend.beans.Ansprechpartner;
import com.dhbwProject.backend.beans.Benutzer;
import com.dhbwProject.backend.beans.Besuch;
import com.dhbwProject.backend.beans.Unternehmen;

public class DummyDataManager {
	
	private Benutzer user;
	private LinkedList<Besuch> lTermin= new LinkedList<Besuch>();
	private LinkedList<Unternehmen> lUnternehmen= new LinkedList<Unternehmen>();
	private LinkedList<Benutzer> lBenutzer= new LinkedList<Benutzer>();
	private LinkedList<Ansprechpartner> lAnsprechpartner = new LinkedList<Ansprechpartner>();
	private LinkedList<Adresse> lAdresse = new LinkedList<Adresse>();
	
	public DummyDataManager(){
		this.user = new Benutzer("0", "Alpha", "Engineering", null, null);
		this.initBenutzer();
		this.initUnternehmen();
		this.initAnsprechpartner();
		this.initBesuch();
	}
	
	private void initUnternehmen(){
		this.lUnternehmen.add(new Unternehmen(0, "ebm-papst Mulfingen GmbH & Co. KG", new LinkedList<Ansprechpartner>(), new LinkedList<Adresse>()));
		this.lUnternehmen.add(new Unternehmen(1, "Ziehl-Abegg SE.", new LinkedList<Ansprechpartner>(),new LinkedList<Adresse>()));
		this.lUnternehmen.add(new Unternehmen(2, "DHBW-Mosbach", new LinkedList<Ansprechpartner>(),new LinkedList<Adresse>()));
		this.lUnternehmen.add(new Unternehmen(3, "Audi AG", new LinkedList<Ansprechpartner>(),new LinkedList<Adresse>()));
		this.lUnternehmen.add(new Unternehmen(4, "Ansmann AG", new LinkedList<Ansprechpartner>(),new LinkedList<Adresse>()));
		this.lUnternehmen.add(new Unternehmen(5, "Würth GmbH & Co. KG", new LinkedList<Ansprechpartner>(),new LinkedList<Adresse>()));
		
	}
	
	private void initBenutzer(){
		this.lBenutzer.add(new Benutzer("1", "Robin", "Bahr", null, null));
		this.lBenutzer.add(new Benutzer("2", "Simon", "Schlarb", null, null));
		this.lBenutzer.add(new Benutzer("3", "Jasmin", "Stribik", null, null));
		this.lBenutzer.add(new Benutzer("4", "Bosse", "Bosse", null, null));
		this.lBenutzer.add(new Benutzer("5", "Florian", "Flurer", null, null));
		this.lBenutzer.add(new Benutzer("6", "Christian", "Zaengle", null, null));
		this.lBenutzer.add(new Benutzer("7", "Manuel", "Maier", null, null));
		this.lBenutzer.add(new Benutzer("8", "Klaus-Georg", "Deck", null, null));
		this.lBenutzer.add(new Benutzer("9", "Herbert", "Neuendorf", null, null));
		this.lBenutzer.add(new Benutzer("10", "Dirk", "Palledhuhn", null, null));
	}
	
	private void initAnsprechpartner(){
		//TODO Robin
		this.lAnsprechpartner.add(new Ansprechpartner(0, "Bernd", "Ludwig", this.lAdresse.get(0)));
		this.lAnsprechpartner.add(new Ansprechpartner(1, "Flo", "Flu", this.lAdresse.get(1)));
		this.lAnsprechpartner.add(new Ansprechpartner(2, "Maraike", "Wurst", this.lAdresse.get(2)));
		this.lAnsprechpartner.add(new Ansprechpartner(3, "Yoshi", "Kadowa", this.lAdresse.get(3)));
		this.lAnsprechpartner.add(new Ansprechpartner(4, "Hans", "Brot", this.lAdresse.get(4)));
		this.lAnsprechpartner.add(new Ansprechpartner(5, "Susanno", "Nippon", this.lAdresse.get(5)));
		
//		this.lUnternehmen.get(0).getAnsprechpartner().add(this.lAnsprechpartner.get(0));
//		this.lUnternehmen.get(1).getAnsprechpartner().add(this.lAnsprechpartner.get(1));
//		this.lUnternehmen.get(2).getAnsprechpartner().add(this.lAnsprechpartner.get(2));
//		this.lUnternehmen.get(3).getAnsprechpartner().add(this.lAnsprechpartner.get(3));
//		this.lUnternehmen.get(4).getAnsprechpartner().add(this.lAnsprechpartner.get(4));
//		this.lUnternehmen.get(5).getAnsprechpartner().add(this.lAnsprechpartner.get(5));
	}
	
	private void initBesuch(){
		LocalDateTime date = LocalDateTime.now();
		GregorianCalendar dateStart = new GregorianCalendar(date.getYear(), date.getMonthValue()-1, date.getDayOfMonth(), 13, 30);
		GregorianCalendar dateEnd = new GregorianCalendar(date.getYear(), date.getMonthValue()-1, date.getDayOfMonth(), 18, 00);
		LinkedList<Benutzer> benutzer = new LinkedList<Benutzer>();
		benutzer.add(this.user);
		benutzer.add(this.lBenutzer.get(0));
		benutzer.add(this.lBenutzer.get(1));
		this.lTermin.add(new Besuch(0, "ebm", dateStart.getTime(), dateEnd.getTime(), this.lUnternehmen.get(0), null, this.lAnsprechpartner.get(0), benutzer));
	}
	
	private void initAdresse(){
		this.lAdresse.add(new Adresse(0, "74189", "Weltstadt Wimmental0", lUnternehmen.get(0), "Musterstrasse", "0a"));
		this.lAdresse.add(new Adresse(1, "74189", "Weltstadt Wimmental1", lUnternehmen.get(1), "Musterstrasse", "1b"));
		this.lAdresse.add(new Adresse(2, "74189", "Weltstadt Wimmental2", lUnternehmen.get(2), "Musterstrasse", "2c"));
		this.lAdresse.add(new Adresse(3, "74189", "Weltstadt Wimmental3", lUnternehmen.get(3), "Musterstrasse", "3d"));
		this.lAdresse.add(new Adresse(4, "74189", "Weltstadt Wimmental4", lUnternehmen.get(4), "Musterstrasse", "4e"));
	}

	public Benutzer getUser() {
		return user;
	}

	public void setUser(Benutzer user) {
		this.user = user;
	}

	public LinkedList<Besuch> getlTermin() {
		return lTermin;
	}

	public void setlTermin(LinkedList<Besuch> lTermin) {
		this.lTermin = lTermin;
	}

	public LinkedList<Unternehmen> getlUnternehmen() {
		return lUnternehmen;
	}

	public void setlUnternehmen(LinkedList<Unternehmen> lUnternehmen) {
		this.lUnternehmen = lUnternehmen;
	}

	public LinkedList<Benutzer> getlBenutzer() {
		return lBenutzer;
	}

	public void setlBenutzer(LinkedList<Benutzer> lBenutzer) {
		this.lBenutzer = lBenutzer;
	}

	public LinkedList<Ansprechpartner> getlAnsprechpartner() {
		return lAnsprechpartner;
	}

	public void setlAnsprechpartner(LinkedList<Ansprechpartner> lAnsprechpartner) {
		this.lAnsprechpartner = lAnsprechpartner;
	}
	

}
