package com.dhbwProject.backend;

import java.util.LinkedList;

import com.dhbwProject.backend.beans.*;
import junit.framework.*;

public class db_testing extends TestCase {

	public db_testing(String name) {
		super(name);
	}

	public void testInsertBenutzer(){
		dbConnect connection = new dbConnect();
		try {
			Beruf beruf = new Beruf(1, "Studiengangsleiter");
			Rolle rolle = new Rolle(1, "ccm_all", null);
			Benutzer b = new Benutzer("fgustavson", "Friedrich", "Gustavson", beruf, rolle);
			assertTrue(connection.createBenutzer(b));
			Benutzer p = connection.getBenutzerbyId("fgustavson");
			assertTrue(b.equals(p));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			connection.close();
		}
	}
	
	public void testAllBenutzer() {
		dbConnect connection = new dbConnect();
		try {
			LinkedList<Benutzer> lBen = new LinkedList<Benutzer>();
			Beruf beruf = new Beruf(1, "Studiengangsleiter");
			Rolle rolle = new Rolle(1, "ccm_all", null);
			lBen.add(new Benutzer("fgustavson", "Friedrich", "Gustavson", beruf, rolle));
			lBen.add(new Benutzer("mmustermann", "Max", "Mustermann", beruf, rolle));
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
