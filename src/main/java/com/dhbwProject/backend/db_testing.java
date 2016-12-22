package com.dhbwProject.backend;

import java.util.LinkedList;

import com.dhbwProject.backend.beans.Benutzer;
import junit.framework.*;

public class db_testing extends TestCase {

	public db_testing(String name) {
		super(name);
	}

	public void testAllBenutzer() {
		dbConnect connection = new dbConnect();
		try {
			LinkedList<Benutzer> lBen = new LinkedList<Benutzer>();
			lBen.add(new Benutzer("mmustermann", "Max", "Mustermann", "Studiengangsleiter", "ccm_all"));
			int i = 0;
			Benutzer p;
			for (Benutzer b : lBen) {
				p = connection.getAllBenutzer().get(i);
				assertTrue(b.equals(p));
				i++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		junit.swingui.TestRunner.run(db_testing.class);
	}
}
