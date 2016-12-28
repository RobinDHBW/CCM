package com.dhbwProject.backend;

import java.util.LinkedList;

import com.dhbwProject.backend.beans.Benutzer;
import junit.framework.*;

public class db_testing extends TestCase {

	public db_testing(String name) {
		super(name);
	}

	public void testInsertBenutzer(){
		dbConnect connection = new dbConnect();
		try {
			Benutzer b = new Benutzer("fgustavson", "Friedrich", "Gustavson", "Studiengangsleiter", "ccm_all");
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
			lBen.add(new Benutzer("fgustavson", "Friedrich", "Gustavson", "Studiengangsleiter", "ccm_all"));
			lBen.add(new Benutzer("mmustermann", "Max", "Mustermann", "Studiengangsleiter", "ccm_all"));
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
