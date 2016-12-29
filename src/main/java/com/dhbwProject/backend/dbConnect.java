package com.dhbwProject.backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.LinkedList;

import com.dhbwProject.backend.beans.*;

public class dbConnect {

	private Connection con;
	private Statement stat;
	private ResultSet res;

	public dbConnect() {
		try {

			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ccm_db", "root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void close() {
		try {
			if (res != null)
				if (!res.isClosed())
					res.close();
			if (stat != null)
				if (!stat.isClosed())
					stat.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private ResultSet executeQuery(String sql, Object... objects) {
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			int q = 0;
			for (Object e : objects) {
				q++;
				if (e instanceof Integer) {
					Integer i = (Integer) e;
					ps.setInt(q, i.intValue());
				} else if(e instanceof Date){
					java.sql.Date d = (java.sql.Date) e;
					ps.setDate(q,  d);
				} else{
					String s = (String) e;
					ps.setString(q, s);
				}
			}
			ResultSet res = ps.executeQuery();
			ps.close();
			return res;
		} catch (SQLException e1) {
			e1.printStackTrace();
			return null;

		}
	}
	private ResultSet executeUpdate(PreparedStatement ps) {
		try {
			res = ps.executeQuery();
			ps.close();
			return res;
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				ps.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return null;
		}
	}
	private ResultSet executeDelete(PreparedStatement ps) {
		try {
			res = ps.executeQuery();
			ps.close();
			return res;
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				ps.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return null;
		}
	}
	private ResultSet executeInsert(PreparedStatement ps) {
		try {
			res = ps.executeQuery();
			ps.close();
			return res;
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				ps.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return null;
		}
	}

	// Adresse

	// TODO Adresse
	public Adresse getAdresseById(int pId) {
		Adresse adresse = null;
		try {
			ResultSet res = executeQuery("select * from Adress where id = ?", (Object[]) new Integer[] { pId });
			while (res.next()) {
				int id = res.getInt("adressen_id");
				String plz = res.getString("adresse_plz_id");
				String ort = getOrtByPlz(plz);
				Unternehmen unternehmen = getUnternehmenById(res.getInt("unternehmen_id"));
				String strasse = res.getString("adresse_strasse");
				String hausnummer = res.getString("adresse_hausnummer");
				adresse = new Adresse(id, plz, ort, unternehmen, strasse, hausnummer);
			}
			res.close();
			return adresse;
		} catch (SQLException e) {
			e.printStackTrace();
			return adresse;
		}
	}
	private String getOrtByPlz(String plz) {
		String ort = null;
		try {
			ResultSet res = executeQuery("select * from ort where ort_plz = ?", (Object[]) new String[] { plz });
			while (res.next()) {
				ort = res.getString("ort_name");;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			res.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ort;
	}
	public boolean createAdresse(Adresse adresse) {
		// TODO ss implement
		return false;
	}
	public boolean changeAdresse(Adresse altAdresse, Adresse neuAdresse) {
		// TODO ss implement
		return false;
	}

	// TODO Ansprechpartner
	private LinkedList<Adresse> getAdresseByUnternehmen(Unternehmen pUnternehmen) {
		LinkedList<Adresse> lAdresse = new LinkedList<Adresse>();
		ResultSet res = executeQuery("select * from adresse a, Unternehmen u where u.unternehmen_id = a.unternehmen_id and unternehmen_id = ?", new Object[]{(Object) new Integer(pUnternehmen.getId())});
		try{
			while(res.next()){
				int id = res.getInt("adresse_id");
				String plz = res.getString("adresse_plz_id"); 
				String ort = getOrtByPlz(plz); 
				Unternehmen unternehmen = getUnternehmenById(id);
				String strasse = res.getString("adresse_strasse"); 
				String hausnummer = res.getString("adresse_hausnummer");
				lAdresse.add(new Adresse(id, plz, ort, unternehmen, strasse, hausnummer));
			}
		}catch(SQLException e){
			e.printStackTrace();
			
		}
		try {
			res.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lAdresse;
	}
	
	// Ansprechpartner
	public LinkedList<Ansprechpartner> getAnsprechpartnerByUnternehmen(Unternehmen unternehmen) {
		ResultSet res = executeQuery("Select * from Ansprechpartner where Unternehmen_id = ?", new Object[] {(Object) unternehmen.getName()});
		LinkedList<Ansprechpartner> lAnsprechpartner = new LinkedList<Ansprechpartner>();
		try {
			while(res.next()){
				int id = res.getInt("ansprechpartner_id");
				String vorname = res.getString("ansprechpartner_vorname");
				String nachname = res.getString("ansprechpartner_nachname");
				Adresse adresse = getAdresseById(res.getInt("adressen_id"));
				lAnsprechpartner.add(new Ansprechpartner(id, vorname, nachname, adresse));
			}
			res.close();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				res.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		return lAnsprechpartner;
	}
	public boolean createAnsprechpartner(Ansprechpartner ansprechpartner) {
		// TODO ss implement createAnsprechpartner
		return false;

	}
	public boolean changeAnsprechpartner(Ansprechpartner altAnsprechpartner, Ansprechpartner neuAnsprechpartner) {
		// TODO ss implement
		return false;
	}
	public Ansprechpartner getAnsprechpartnerById(int pId){
		ResultSet res = executeQuery("Select * from Ansprechpartner where Unternehmen_id = ?", new Object[] {new Integer(pId)});
		Ansprechpartner ansprechpartner = null;
		try {
			while(res.next()){
				int id = res.getInt("ansprechpartner_id");
				String vorname = res.getString("ansprechpartner_vorname");
				String nachname = res.getString("ansprechpartner_nachname");
				Adresse adresse = getAdresseById(res.getInt("adressen_id"));
				ansprechpartner = new Ansprechpartner(id, vorname, nachname, adresse);
			}
			res.close();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				res.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		return ansprechpartner;
	}
	public boolean deleteAnsprechpartner(Ansprechpartner ansprechpartner){
		//TODO ss implement
		return false;
	}
	
	// Benutzer
	public LinkedList<Benutzer> getAllBenutzer() {
		LinkedList<Benutzer> benArr = new LinkedList<Benutzer>();
		ResultSet res = executeQuery("select * from benutzer", new Object[] {});
		try {
			while (res.next()) {
				String id = res.getString("benutzer_id");
				String vorname;
				vorname = res.getString("vorname");
				String nachname = res.getString("nachname");
				Beruf beruf = getBerufById(res.getInt("beruf_id"));
				Rolle rolle = getRolleById(res.getInt("rolle_id"));
				Benutzer ben = new Benutzer(id, vorname, nachname, beruf, rolle);
				benArr.add(ben);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			res.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return benArr;
	}
	public Benutzer getBenutzerById(String pId){
		Benutzer benutzer = null;
		try {
			ResultSet res = stat.executeQuery("select * from benutzer where benutzer_id = '" + pId + "'");
			res.next();
			String id = res.getString("benutzer_id");
			String vorname = res.getString("vorname");
			String nachname = res.getString("nachname");
			Beruf beruf = getBerufById(res.getInt("beruf_id"));
			Rolle rolle = getRolleById(res.getInt("rolle_id"));
			benutzer = new Benutzer(id, vorname, nachname, beruf, rolle);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			res.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return benutzer;
	}
	public boolean createBenutzer(Benutzer b) {
		try {
			if (getBenutzerById(b.getId()).equals(b)) {
				return false;
			}
		} catch (Exception e) {
			try {
				Beruf beruf = getBerufByBezeichnung(b.getBeruf().getBezeichnung());
				Rolle rolle = getRolleByBezeichnung(b.getRolle().getBezeichnung());
				PreparedStatement ps = con.prepareStatement(
						"INSERT INTO `benutzer` (`vorname`, `nachname`, `benutzer_id`, `rolle_id`, `beruf_id`) VALUES (?, ?, ?, ?, ?)");
				ps.setString(1, b.getVorname());
				ps.setString(2, b.getNachname());
				ps.setString(3, b.getId());
				ps.setInt(4, beruf.getId());
				ps.setInt(5, rolle.getId());
				ps.executeUpdate();
				ps.close();

				return true;
			} catch (Exception ex) {
				ex.printStackTrace();
				return false;
			}

		}
		return false;

	}
	public boolean changeBenutzer(Benutzer altBenutzer, Benutzer neuBenutzer) {
		// TODO ss implement
		return false;
	}
	public boolean deleteBenutzer(Benutzer benutzer) {
		// TODO ss implement
		return false;
	}
	// TODO Berechtigung
	public LinkedList<Benutzer> getBenutzerByBesuchId(int pId){
		LinkedList<Benutzer> lBenutzer = new LinkedList<Benutzer>();
		ResultSet res = executeQuery("select * from benutzer b, besuch bs, benutzer_besuch bb where b.benutzer_id=bb.benutzer_id and bs.besuch_id = bb.besuch_id and bs.besuch_id = ?", new Object[]{(Object) new Integer(pId)});
		try {
			while(res.next()){
				String id = res.getString("benutzer_id");
				String vorname = res.getString("vorname");
				String nachname = res.getString("nachname");
				Beruf beruf = getBerufById(res.getInt("beruf_id")); 
				Rolle rolle = getRolleById(res.getInt("rolle_id"));
				lBenutzer.add(new Benutzer(id, vorname, nachname, beruf, rolle));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			res.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lBenutzer;
	}
	
	// Berechtigung

	// Berechtigung
	public LinkedList<Berechtigung> getBerechtigungByRolle(Rolle rolle) {
		LinkedList<Berechtigung> lBerechtigung = new LinkedList<Berechtigung>();
		ResultSet res = executeQuery(
				"Select * from berechtigung b, rolle_berechtigung rb where rb.berechtigung_id = b.berechtigung_id and rolle_id = ?",
				new Object[] { new Integer(rolle.getId()) });
		try {
			while (res.next()) {
				int id = res.getInt("berechtigung_id");
				String bezeichnung = res.getString("berechtigung_bezeichnung");
				lBerechtigung.add(new Berechtigung(id, bezeichnung));
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				res.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		return lBerechtigung;
	}
	public boolean createBerechtigung(Berechtigung berechtigung) {
		// TODO ss implement
		return false;
	}
	public boolean changeBerechtigung(Berechtigung altBerechtigung, Berechtigung neuBerechtigung) {
		// TODO ss implement
		return false;
	}

	// TODO Beruf
	
	//Beruf
	public Berechtigung getBerechtigunById(int pId){
		Berechtigung berechtigung = null;
		ResultSet res = executeQuery(
				"select * from berechtigung where berechtigung_id = ?", new Object[] { new Integer(pId) });
		try {
			while (res.next()) {
				int id = res.getInt("berechtigung_id");
				String bezeichnung = res.getString("berechtigung_bezeichnung");
				berechtigung = new Berechtigung(id, bezeichnung);
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				res.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		return berechtigung;
	}
	
	// Beruf
	public Beruf getBerufById(int beruf_id){
		ResultSet res = executeQuery("select * from beruf where beruf_id = ?", new Object[]{(Object) new Integer(beruf_id)});
		Beruf beruf = null;
		try {
			while (res.next()) {
				int id = res.getInt("beruf_id");
				String bezeichnung = res.getString("beruf_bezeichnung");
				beruf = new Beruf(id, bezeichnung);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			res.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return beruf;
	}
	public Beruf getBerufByBezeichnung(String beruf_bezeichnung) throws SQLException {
		ResultSet res = executeQuery("select * from beruf where beruf_bezeichnung = ?", new Object[]{(Object) beruf_bezeichnung});
		Beruf beruf = null;
		try {
			while (res.next()) {
				int id = res.getInt("beruf_id");
				String bezeichnung = res.getString("beruf_bezeichnung");
				beruf = new Beruf(id, bezeichnung);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			res.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return beruf;
	}

	// TODO Besuch
	
	//Besuch
	
	// Besuch
	public LinkedList<Besuch> getBesucheByDate(Date date) {
		LinkedList<Besuch> lBesuch = new LinkedList<Besuch>();
		ResultSet res = executeQuery("select * from besuch where besuch_beginn = ?", new Object[] {(Object) date});
		try {
			while (res.next()) {
				int id = res.getInt("besuch_id");
				Adresse adresse = getAdresseById(res.getInt("adresse_id"));
				Date timestamp = res.getDate("besuch_timestamp");
				Date startDate = res.getDate("besuch_beginn");
				Date endDate = res.getDate("besuch_ende");
				String name = res.getString("besuch_name");
				Benutzer autor = getBenutzerById(res.getString("besuch_autor"));
				Status status = getStatusById(res.getInt("status_id"));
				LinkedList<Benutzer> lBenutzer = getBenutzerByBesuchId(id);
				Ansprechpartner ansprechpartner = getAnsprechpartnerById(res.getInt("ansprechpartner_id"));
				lBesuch.add(new Besuch(id, name, startDate, endDate, adresse, status, ansprechpartner, lBenutzer, timestamp, autor));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			res.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lBesuch;

	}
	public Besuch getBesuchById(int pId) {
		ResultSet res = executeQuery("Select * from besuch where besuch_id = ?", new Object[] {new Integer(pId)});
		Besuch besuch = null;
		try {
			while (res.next()) {
				int id = res.getInt("besuch_id");
				Adresse adresse = getAdresseById(res.getInt("adresse_id"));
				Date timestamp = res.getDate("besuch_timestamp");
				Date startDate = res.getDate("besuch_beginn");
				Date endDate = res.getDate("besuch_ende");
				String name = res.getString("besuch_name");
				Benutzer autor = getBenutzerById(res.getString("besuch_autor"));
				Status status = getStatusById(res.getInt("status_id"));
				LinkedList<Benutzer> lBenutzer = getBenutzerByBesuchId(id);
				Ansprechpartner ansprechpartner = getAnsprechpartnerById(res.getInt("ansprechpartner_id"));
				besuch = new Besuch(id, name, startDate, endDate, adresse, status, ansprechpartner, lBenutzer, timestamp, autor);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			res.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return besuch;
	}
	public boolean createBesuch(Besuch besuch) {
		// TODO ss implement
		return false;
	}

	// TODO Gespraechsnotiz
	
	//Gespreachsnotiz
	public boolean deleteBesuch(Besuch besuch){
		//TODO ss implement
		return false;
	}
	
	// Gespraechsnotiz
	public Gespraechsnotiz getGespraechsnotizByBesuch(Besuch pBesuch) {
		ResultSet res = executeQuery("Select * from gespraechsnotiz where besuch_id = ?", new Object[] {new Integer(pBesuch.getId())});
		Gespraechsnotiz gespraechsnotiz = null;
		try {
			while (res.next()) {
				int id = res.getInt("gespraechsnotiz_id");
				java.sql.Blob notiz = res.getBlob("gespraechsnotiz_notiz");
				java.sql.Blob bild = res.getBlob("gespraechsnotiz_bild");
				Unternehmen unternehmen = getUnternehmenById(res.getInt("unternehmen_id")); 
				Besuch besuch = getBesuchById(res.getInt("besuch_id"));
				Date timestamp = res.getDate("gespraechsnotiz_timestamp");
				gespraechsnotiz = new Gespraechsnotiz(id, notiz, bild, unternehmen, besuch, timestamp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			res.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return gespraechsnotiz;
	}
	public boolean createGespraechsnotiz(Gespraechsnotiz gespraechsnotiz) {
		// TODO ss implement createGespreachsnotiz
		return false;

	}
	public boolean changeGespraechsnotiz(Gespraechsnotiz altGespraechsnotiz, Gespraechsnotiz neuGespraechsnotiz) {
		// TODO ss implement
		return false;
	}

	// TODO Rolle
	
	//Rolle
	
	// Rolle
	public Rolle getRolleById(int id){
		Rolle rolle1 = null;
		Rolle rolle2 = null;
		LinkedList<Berechtigung> lBerechtigung = new LinkedList<Berechtigung>();
		try {
			ResultSet res = executeQuery("select * from rolle where rolle_id = ?", new Object[]{(Object) new Integer(id)});
			while (res.next()) {
				rolle1 = new Rolle(res.getInt(1), res.getString(2), lBerechtigung);
				lBerechtigung = getBerechtigungByRolle(rolle1);
				rolle2 = new Rolle(rolle1.getId(), rolle1.getBezeichnung(), lBerechtigung);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			res.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rolle2;
	}
	public Rolle getRolleByBezeichnung(String rolle_Bezeichnung){
		ResultSet res = executeQuery("select * from `rolle` where rolle_bezeichnung = ?", new Object[] {(Object) rolle_Bezeichnung});
		Rolle rolle1 = null;
		Rolle rolle2 = null;
		LinkedList<Berechtigung> lBerechtigung = new LinkedList<Berechtigung>();
		try {
			while (res.next()) {
				rolle1 = new Rolle(res.getInt(1), res.getString(2), lBerechtigung);
				lBerechtigung = getBerechtigungByRolle(rolle1);
				rolle2 = new Rolle(rolle1.getId(), rolle1.getBezeichnung(), lBerechtigung);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			res.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rolle2;
	}
	public boolean createRolle(Rolle rolle) {
		// TODO ss implement
		return false;
	}
	public boolean changeRolle(Rolle altRolle, Rolle neuRolle) {
		// TODO ss implement
		return false;
	}

	//Status
	
	// Status
	public Status getStatusById(int pId) {
		Status status = null;
		try {
			ResultSet res = executeQuery("select * from status where status_id = ?", new Object[]{(Object) new Integer(pId)});
			while (res.next()) {
				int id = res.getInt("status_id");
				String bezeichnung = res.getString("status_bezeichnung");
				status = new Status(id, bezeichnung);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			res.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return status;
	}
	public boolean createStatus(Status status) {
		// TODO ss implement createStatus
		return false;

	}
	public boolean changeStatus(Status altStatus, Status neuStatus) {
		// TODO ss implement
		return false;
	}

	//Unternehmen
	public boolean deleteStatus(Status status){
		//TODO ss implement
		return false;
	}
	
	// Unternhmen 
	public Unternehmen getUnternehmenById(int pId) {
		Unternehmen unternehmen = null;
		try {
			ResultSet res = executeQuery("select * from unternehmen where unternehmen_id = ?", new Object[]{(Object) new Integer(pId)});
			while (res.next()) {
				int id = res.getInt("unternehmen_id");
				String name = res.getString("unternehmen_name");
				LinkedList<Ansprechpartner> lAnsprechpartner = new LinkedList<Ansprechpartner>();
				LinkedList<Adresse> lAdresse = new LinkedList<Adresse>();
				unternehmen = new Unternehmen(id, name, lAnsprechpartner, lAdresse);
				lAnsprechpartner = getAnsprechpartnerByUnternehmen(unternehmen);
				lAdresse = getAdresseByUnternehmen(unternehmen);
			}
			res.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			res.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return unternehmen;
	}
	public Unternehmen getUnternehmenByName(String pName) {
		Unternehmen unternehmen = null;
		try {
			ResultSet res = executeQuery("select * from unternehmen where unternehmen_name = ?", new Object[]{(Object) pName});
			while (res.next()) {
				int id = res.getInt("unternehmen_id");
				String name = res.getString("unternehmen_name");
				LinkedList<Ansprechpartner> lAnsprechpartner = new LinkedList<Ansprechpartner>();
				LinkedList<Adresse> lAdresse = new LinkedList<Adresse>();
				unternehmen = new Unternehmen(id, name, lAnsprechpartner, lAdresse);
				lAnsprechpartner = getAnsprechpartnerByUnternehmen(unternehmen);
				lAdresse = getAdresseByUnternehmen(unternehmen);
			}
			res.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			res.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return unternehmen;

	}
	public boolean createUnternehmen(Unternehmen unternehmen) {
		// TODO ss implement
		return false;

	}
	public boolean changeUnternehmen(Unternehmen altUnternehmen, Unternehmen neuUnternehmen) {
		// TODO ss implement
		return false;
	}

}
