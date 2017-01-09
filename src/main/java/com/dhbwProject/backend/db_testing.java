package com.dhbwProject.backend;

import java.sql.SQLException;
import java.util.LinkedList;

import com.dhbwProject.backend.beans.*;
import junit.framework.*;

public class db_testing extends TestCase {

	public db_testing(String name) {
		super(name);
	}

	public void testInsertStudiengang(){
		dbConnect connection = new dbConnect();
		
		try{
			Studiengang studiengang = connection.getStudiengangByBezeichnung("Wirtschaftsinformatik");
			System.out.println("Studiengang bereits vorhanden");
			assertTrue(false);
		}catch(SQLException e1){
		try {
			Studiengang studiengang = new Studiengang(0, "Wirtschaftsinformatik");
			assertTrue(connection.createStudiengang(studiengang));
			Studiengang p = connection.getStudiengangByBezeichnung("Wirtschaftsinformatik");
			assertTrue(studiengang.equals(p));
			
		} catch (Exception e2) {
			e2.printStackTrace();
		}}
		finally{
			connection.close();
		}
	}
	
	public void testInsertBenutzer(){
		dbConnect connection = new dbConnect();
		try{
			Benutzer benutzer = connection.getBenutzerById("fgustavson");
			System.out.println("Benutzer bereits vorhanden");
			assertTrue(false);
		}catch(SQLException e1){
		try {
			Beruf beruf = new Beruf(1, "Studiengangsleiter");
			Rolle rolle = new Rolle(1, "ccm_all", null);
			LinkedList<Studiengang> lStudiengang = new LinkedList<Studiengang>();
			lStudiengang.add(new Studiengang(0, "Wirtschaftsinformatik"));
			
			Benutzer b = new Benutzer("fgustavson", "Friedrich", "Gustavson", beruf, rolle, lStudiengang);
			assertTrue(connection.createBenutzer(b));
			Benutzer p = connection.getBenutzerById("fgustavson");
			assertTrue(b.equals(p));
			
		} catch (Exception e) {
			e.printStackTrace();
		}}
		finally{
			connection.close();
		}
	}
	
	public void testAllBenutzer() {
		dbConnect connection = new dbConnect();
		try {
			LinkedList<Benutzer> lBen = new LinkedList<Benutzer>();
			Beruf beruf = new Beruf(1, "Studiengangsleiter");
			Rolle rolle = new Rolle(1, "ccm_all", new LinkedList<Berechtigung>());
			LinkedList<Studiengang> lStudiengang = new LinkedList<Studiengang>();
			lStudiengang.add(new Studiengang(0, "Wirtschaftsinformatik"));
			lBen.add(new Benutzer("fgustavson", "Friedrich", "Gustavson", beruf, rolle, lStudiengang));
			lBen.add(new Benutzer("mmustermann", "Max", "Mustermann", beruf, rolle, null));
			int i = 0;
			LinkedList<Benutzer> dbBen = connection.getAllBenutzer();
			Benutzer p;
			for (Benutzer b : lBen) {
				p = dbBen.get(i);
				boolean bool = b.equals(p);
				assertTrue(bool);
				i++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			connection.close();
		}
	}


	public static void main(String[] args) {
		junit.swingui.TestRunner.run(db_testing.class);
	}
}
