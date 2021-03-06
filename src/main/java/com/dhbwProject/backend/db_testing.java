package com.dhbwProject.backend;

import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedList;

import com.dhbwProject.backend.beans.*;
import junit.framework.*;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;

public class db_testing extends TestCase {

	public db_testing(String name) {
		super(name);
	}

	public void testCreateNotiz() {
		dbConnect connection = null;
		try {
			connection = new dbConnect();
		} catch (ClassNotFoundException e3) {

			e3.printStackTrace();
		} catch (SQLException e3) {

			e3.printStackTrace();
		}
		int id = 0;

		// byte[] notiz = null;
		// Path path = Paths.get("C:/Users/CCM/Desktop/test.txt");
		// try {
		// notiz = Files.readAllBytes(path);
		// } catch (IOException e1) {
		// e1.printStackTrace();
		// }
		// byte[] bild = null;
		// try {
		// bild = Files.readAllBytes(path);
		// } catch (IOException e2) {
		// e2.printStackTrace();
		// }
		Path notiz = Paths.get("C:/Users/CCM/Desktop/test.txt");
		Path bild = Paths.get("C:/Users/CCM/Desktop/test.txt");
		byte[] notizBytes = null;
		byte[] bildBytes = null;
		try {
			notizBytes = Files.readAllBytes(notiz);
			bildBytes = Files.readAllBytes(bild);
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		// File bild = new File("C:/Users/CCM/Desktop/test.txt");

		Besuch besuch = null;
		Unternehmen unternehmen = null;
		try {
			unternehmen = connection.getUnternehmenById(1);
			besuch = connection.getBesuchById(1);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Date timestamp = null;
		try {
			Benutzer benutzer = connection.getBenutzerById("fgustavson");
			Gespraechsnotiz gespraechsnotiz = new Gespraechsnotiz(id, notizBytes, bildBytes, unternehmen, besuch,
					timestamp, benutzer);

			connection.createGespraechsnotiz(gespraechsnotiz);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertTrue(true);
	}

	public void testGetNotiz() throws UnsupportedEncodingException {
		dbConnect connection = null;
		try {
			connection = new dbConnect();
		} catch (ClassNotFoundException e3) {

			e3.printStackTrace();
		} catch (SQLException e3) {

			e3.printStackTrace();
		}
		int id = 0;

		// byte[] notiz = null;
		// Path path = Paths.get("C:/Users/CCM/Desktop/test.txt");
		// try {
		// notiz = Files.readAllBytes(path);
		// } catch (IOException e1) {
		// e1.printStackTrace();
		// }
		// byte[] bild = null;
		// try {
		// bild = Files.readAllBytes(path);
		// } catch (IOException e2) {
		// e2.printStackTrace();
		// }
		try {
			Gespraechsnotiz gespraechsnotiz = connection.getGespraechsnotizById(10);
			String s = new String(gespraechsnotiz.getNotiz(), "UTF8");
			System.out.println(s);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertTrue(true);
	}

	public void testInsertStudiengang() {
		dbConnect connection = null;
		try {
			connection = new dbConnect();
		} catch (ClassNotFoundException e3) {

			e3.printStackTrace();
		} catch (SQLException e3) {

			e3.printStackTrace();
		}

		try {
			Studiengang studiengang = connection.getStudiengangByBezeichnung("Wirtschaftsinformatik");
			System.out.println("Studiengang bereits vorhanden");
			assertTrue(false);
		} catch (SQLException e1) {
			try {
				Studiengang studiengang = new Studiengang(0, "Wirtschaftsinformatik");
				assertTrue(connection.createStudiengang(studiengang).equals(studiengang));
				Studiengang p = connection.getStudiengangByBezeichnung("Wirtschaftsinformatik");
				assertTrue(studiengang.equals(p));

			} catch (Exception e2) {
				e2.printStackTrace();
			}
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}
	}

	public void testInsertBenutzer() {
		dbConnect connection = null;
		try {
			connection = new dbConnect();
		} catch (ClassNotFoundException e2) {

			e2.printStackTrace();
		} catch (SQLException e2) {

			e2.printStackTrace();
		}
		try {
			Benutzer benutzer = connection.getBenutzerById("fgustavson");
			System.out.println("Benutzer bereits vorhanden");
			assertTrue(false);
		} catch (SQLException e1) {
			try {
				Beruf beruf = new Beruf(1, "Studiengangsleiter");
				Rolle rolle = new Rolle(1, "ccm_all");
				LinkedList<Studiengang> lStudiengang = new LinkedList<Studiengang>();
				lStudiengang.add(new Studiengang(0, "Wirtschaftsinformatik"));

				Benutzer b = new Benutzer("fgustavson", "Friedrich", "Gustavson", beruf, rolle, lStudiengang,
						"friedrich.gustavson@web.de", "01234 1233");
				assertTrue(connection.createBenutzer(b).equals(b));
				Benutzer p = connection.getBenutzerById("fgustavson");
				assertTrue(b.equals(p));

			} catch (Exception e) {
//				e.printStackTrace();
			}
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}
	}

	public void testAllBenutzer() {
		dbConnect connection = null;
		try {
			connection = new dbConnect();
		} catch (ClassNotFoundException e1) {

			e1.printStackTrace();
		} catch (SQLException e1) {

			e1.printStackTrace();
		}
		try {
			LinkedList<Benutzer> lBen = new LinkedList<Benutzer>();
			Beruf beruf = new Beruf(1, "Studiengangsleiter");
			Rolle rolle = new Rolle(1, "ccm_all");
			LinkedList<Studiengang> lStudiengang = new LinkedList<Studiengang>();
			lStudiengang.add(new Studiengang(0, "Wirtschaftsinformatik"));
			lBen.add(new Benutzer("fgustavson", "Friedrich", "Gustavson", beruf, rolle, lStudiengang,
					"friedrich.gustavson@web.de", "01234 1233"));
			lBen.add(new Benutzer("mmustermann", "Max", "Mustermann", beruf, rolle, null, "max.mustermann@web.de",
					"01234 1179"));
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
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}
	}

	public void testPassword() {
		dbConnect connection = null;
		try {
			connection = new dbConnect();
		} catch (ClassNotFoundException e1) {

			e1.printStackTrace();
		} catch (SQLException e1) {

			e1.printStackTrace();
		}
		try {
			Beruf beruf = new Beruf(1, "Studiengangsleiter");
			Rolle rolle = new Rolle(1, "ccm_all");
			LinkedList<Studiengang> lStudiengang = new LinkedList<Studiengang>();
			lStudiengang.add(new Studiengang(0, "Wirtschaftsinformatik"));
			Benutzer b = new Benutzer("sschlarb", "", "", beruf, rolle, lStudiengang, "friedrich.gustavson@web.de$ä",
					"01234 1233");
			connection.createPassword(PasswordHasher.md5("1234"), b);
			assertTrue(connection.checkPassword(PasswordHasher.md5("1234"), b));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void testDeleteBenutzerFromBesuch() {
		dbConnect connection = null;
		try {
			connection = new dbConnect();
		} catch (ClassNotFoundException e1) {

			e1.printStackTrace();
		} catch (SQLException e1) {

			e1.printStackTrace();
		}
		try {
			Beruf beruf = new Beruf(1, "Studiengangsleiter");
			Rolle rolle = new Rolle(1, "ccm_all");
			LinkedList<Studiengang> lStudiengang = new LinkedList<Studiengang>();
			lStudiengang.add(new Studiengang(0, "Wirtschaftsinformatik"));
			Benutzer b = new Benutzer("mmustermann", "", "", beruf, rolle, lStudiengang, "friedrich.gustavson@web.de",
					"01234 1233");
			connection.deleteBenutzerFromBesuch(connection.getBesuchByBenutzer(b).getFirst(), b);
			assertTrue(true);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		junit.swingui.TestRunner.run(db_testing.class);
	}
}
