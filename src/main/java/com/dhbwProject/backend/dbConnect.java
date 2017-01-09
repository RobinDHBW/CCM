package com.dhbwProject.backend;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
	private int executeUpdate(String sql, Object... objects)  {
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
			int result = ps.executeUpdate();
			ps.close();
			return result;
		} catch (SQLException e1) {
			e1.printStackTrace();
			return 0;

		}
	}
	private <T> int executeDelete(T obj) {
		try {
		
			if(obj instanceof Adresse){
				 PreparedStatement ps = con.prepareStatement("DELETE FROM `adresse` WHERE `adresse_plz_id` = ? AND `unternehmen_id` = ? AND `adresse_strasse` = ? AND `adresse_hausnummer` = ?)");
				 ps.setString(1, ((Adresse) obj).getPlz());
				 ps.setInt(2, ((Adresse) obj).getUnternehmen().getId());
				 ps.setString(3, ((Adresse) obj).getStrasse());
				 ps.setString(4, ((Adresse) obj).getHausnummer());
				 int result = ps.executeUpdate();ps.close();return result;
			}else
			if(obj instanceof Ansprechpartner){
				 PreparedStatement ps = con.prepareStatement("DELETE FROM `ansprechpartner` WHERE `ansprechpartner_vorname` = ? AND `ansprechpartner_nachname` = ? AND `adressen_id` = ?)");
				 ps.setString(1, ((Ansprechpartner) obj).getVorname());
				 ps.setString(2, ((Ansprechpartner) obj).getNachname());
				 ps.setInt(3, ((Ansprechpartner) obj).getAdresse().getId());
				 int result = ps.executeUpdate();ps.close();return result;
			}else
			if(obj instanceof Benutzer){
				 PreparedStatement ps = con.prepareStatement("DELETE FROM `benutzer` WHERE `vorname`= ? AND `nachname`= ? AND `benutzer_id`= ? AND `rolle_id`= ? AND `beruf_id`= ?)");
				 ps.setString(1, ((Benutzer) obj).getVorname());
				 ps.setString(2, ((Benutzer) obj).getNachname());
				 ps.setString(3, ((Benutzer) obj).getId());
				 ps.setInt(4, ((Benutzer) obj).getRolle().getId());
				 ps.setInt(5, ((Benutzer) obj).getBeruf().getId());
				 int result = ps.executeUpdate();ps.close();return result;
			}else
			if(obj instanceof Berechtigung){
				 PreparedStatement ps = con.prepareStatement("DELETE FROM `berechtigung` WHERE `berechtigung_bezeichnung` = ?)");
				 ps.setString(1, ((Berechtigung) obj).getBezeichnung());
				 int result = ps.executeUpdate();ps.close();return result;
			}else
			if(obj instanceof Beruf){
				 PreparedStatement ps = con.prepareStatement("DELETE FROM `beruf` WHERE `beruf_bezeichnung` = ?)");
				 ps.setString(1, ((Beruf) obj).getBezeichnung());
				 int result = ps.executeUpdate();ps.close();return result;
			}else
			if(obj instanceof Besuch){
				PreparedStatement ps = con.prepareStatement(
						"DELETE FROM `besuch` WHERE `adresse_id` = ? AND `besuch_beginn` = ? AND `besuch_ende` = ? AND `besuch_name` = ? AND `besuch_autor` = ? AND `status_id` = ? AND `ansprechpartner_id` = ?)",
						Statement.RETURN_GENERATED_KEYS);
				ps.setInt(1, ((Besuch) obj).getAdresse().getId());
				 ps.setDate(2, ((Besuch) obj).getStartDate());
				 ps.setDate(3, ((Besuch) obj).getEndDate());
				 ps.setString(4, ((Besuch) obj).getName());
				 ps.setInt(5, ((Besuch) obj).getAutor().getId());
				 ps.setInt(6, ((Besuch) obj).getStatus().getId());
				 ps.setInt(7, ((Besuch) obj).getAnsprechpartner().getId());
				 
				 int p = ps.executeUpdate();
				    ResultSet rs = ps.getGeneratedKeys();
				     rs.next();
				    int auto_id = rs.getInt(1);
				 rs.close();
				 LinkedList<Benutzer> lBenutzer = ((Besuch) obj).getBesucher();
				 for(Benutzer e : lBenutzer){
					 PreparedStatement ps2 = con.prepareStatement("DELETE FROM `benutzer_besuch` WHERE `benutzer_id` = ? AND `besuch_id` = ?)");
					 ps2.setString(1, e.getId());
					 ps2.setInt(2, auto_id);
					 ps2.executeUpdate();
					 ps2.close();
				 	}
				 ps.close();
				 return p;
				 }else
			if(obj instanceof Gespraechsnotiz){
				 PreparedStatement ps = con.prepareStatement("DELETE FROM `gespraechsnotizen` WHERE `gespraechsnotiz_notiz` = ? AND `gespraechsnotiz_bild` = ? AND `unternehmen_id` = ? AND `besuch_id` = ?");
				 FileInputStream inputNotiz = null;
				 FileInputStream inputBild = null;
				try {
					inputNotiz = new FileInputStream(((Gespraechsnotiz) obj).getNotiz().toString());
					ps.setBinaryStream(1, inputNotiz);

					inputBild = new FileInputStream(((Gespraechsnotiz) obj).getBild().toString());
					ps.setBinaryStream(2, inputBild);
				
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				 ps.setInt(3, ((Gespraechsnotiz) obj).getUnternehmen().getId());
				 ps.setInt(4, ((Gespraechsnotiz) obj).getBesuch().getId());
				 int result = ps.executeUpdate();ps.close();return result;
			}else
			if (obj instanceof Rolle) {
				PreparedStatement ps = con.prepareStatement(
						"DELETE FROM `rolle` WHERE `rolle_bezeichnung` = ?)",
						Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, ((Rolle) obj).getBezeichnung());
				int p = ps.executeUpdate();
				ResultSet rs = ps.getGeneratedKeys();
				rs.next();
				int auto_id = rs.getInt(1);
				rs.close();

				LinkedList<Berechtigung> lBerechtigung = ((Rolle) obj).getBerechtigung();
				for (Berechtigung e : lBerechtigung) {
					PreparedStatement ps2 = con.prepareStatement(
							"DELETE FROM `rolle_berechtigung` WHERE `rolle_id` = ? AND `berechtigung_id` = ?)");
					ps2.setInt(1, e.getId());
					ps2.setInt(2, auto_id);
					ps2.executeUpdate();
					ps2.close();
				}
				ps.close();
				return p;
			} else
			if(obj instanceof Status){
				 PreparedStatement ps = con.prepareStatement("DELETE FROM `status` WHERE `status_bezeichnung` = ?)");
				 ps.setString(1, ((Status) obj).getBezeichnung());
				 int result = ps.executeUpdate();
				 ps.close();
				 return result;
			}else
			if(obj instanceof Unternehmen){
				PreparedStatement ps = con.prepareStatement("DELETE FROM `unternehmen` WHERE `unternehmen_name` = ?)");
				 ps.setString(1, ((Unternehmen) obj).getName());
				 int result = ps.executeUpdate();
				 ps.close();
				 return result;
			}
		}
			 catch (SQLException e1) {
			e1.printStackTrace();
		}
		return 0;
	}
	private <T> int executeInsert(T obj) {
		try {
		
			if(obj instanceof Adresse){
				 PreparedStatement ps = con.prepareStatement("INSERT INTO `adresse` (`adresse_plz_id`, `unternehmen_id`, `adresse_strasse`, `adresse_hausnummer`) VALUES (?, ?, ?, ?)");
				 ps.setString(1, ((Adresse) obj).getPlz());
				 ps.setInt(2, ((Adresse) obj).getUnternehmen().getId());
				 ps.setString(3, ((Adresse) obj).getStrasse());
				 ps.setString(4, ((Adresse) obj).getHausnummer());
				 int result = ps.executeUpdate();ps.close();return result;
			}else
			if(obj instanceof Ansprechpartner){
				 PreparedStatement ps = con.prepareStatement("INSERT INTO `ansprechpartner` (`ansprechpartner_vorname`, `ansprechpartner_nachname`, `adressen_id`) VALUES (?, ?, ?)");
				 ps.setString(1, ((Ansprechpartner) obj).getVorname());
				 ps.setString(2, ((Ansprechpartner) obj).getNachname());
				 ps.setInt(3, ((Ansprechpartner) obj).getAdresse().getId());
				 int result = ps.executeUpdate();ps.close();return result;
			}else
			if(obj instanceof Benutzer){
				 PreparedStatement ps = con.prepareStatement("INSERT INTO `benutzer` (`vorname`, `nachname`, `benutzer_id`, `rolle_id`, `beruf_id`) VALUES (?, ?, ?, ?, ?)");
				 ps.setString(1, ((Benutzer) obj).getVorname());
				 ps.setString(2, ((Benutzer) obj).getNachname());
				 ps.setString(3, ((Benutzer) obj).getId());
				 ps.setInt(4, ((Benutzer) obj).getRolle().getId());
				 ps.setInt(5, ((Benutzer) obj).getBeruf().getId());
				 int result = ps.executeUpdate();ps.close();return result;
			}else
			if(obj instanceof Berechtigung){
				 PreparedStatement ps = con.prepareStatement("INSERT INTO `berechtigung` (`berechtigung_id`, `berechtigung_bezeichnung`) VALUES (NULL, ?)");
				 ps.setString(1, ((Berechtigung) obj).getBezeichnung());
				 int result = ps.executeUpdate();ps.close();return result;
			}else
			if(obj instanceof Beruf){
				 PreparedStatement ps = con.prepareStatement("INSERT INTO `beruf` (`beruf_id`, `beruf_bezeichnung`) VALUES (NULL, ?)");
				 ps.setString(1, ((Beruf) obj).getBezeichnung());
				 int result = ps.executeUpdate();ps.close();return result;
			}else
			if(obj instanceof Besuch){
				 PreparedStatement ps = con.prepareStatement("INSERT INTO `besuch` (`besuch_id`, `adresse_id`, `besuch_timestamp`, `besuch_beginn`, `besuch_ende`, `besuch_name`, `besuch_autor`, `status_id`, `ansprechpartner_id`) VALUES (NULL, ?, CURRENT_TIMESTAMP, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
				 ps.setInt(1, ((Besuch) obj).getAdresse().getId());
				 ps.setDate(2, ((Besuch) obj).getStartDate());
				 ps.setDate(3, ((Besuch) obj).getEndDate());
				 ps.setString(4, ((Besuch) obj).getName());
				 ps.setInt(5, ((Besuch) obj).getAutor().getId());
				 ps.setInt(6, ((Besuch) obj).getStatus().getId());
				 ps.setInt(7, ((Besuch) obj).getAnsprechpartner().getId());
				 
				 int p = ps.executeUpdate();
				    ResultSet rs = ps.getGeneratedKeys();
				     rs.next();
				    int auto_id = rs.getInt(1);
				 rs.close();
				 LinkedList<Benutzer> lBenutzer = ((Besuch) obj).getBesucher();
				 for(Benutzer e : lBenutzer){
					 PreparedStatement ps2 = con.prepareStatement("INSERT INTO `benutzer_besuch` (`benutzer_besuch_id`, `benutzer_id`, `besuch_id`) VALUES (NULL, ?, ?)");
					 ps2.setString(1, e.getId());
					 ps2.setInt(2, auto_id);
					 ps2.executeUpdate();
					 ps2.close();
				 	}
				 ps.close();
				 return p;
				 }else
			if(obj instanceof Gespraechsnotiz){
				 PreparedStatement ps = con.prepareStatement("INSERT INTO `gespraechsnotizen` (`gespraechsnotiz_id`, `gespraechsnotiz_notiz`, `gespraechsnotiz_bild`, `unternehmen_id`, `besuch_id`, `gespraechsnotiz_timestamp`) VALUES (NULL, ?, ?, ?, ?, CURRENT_TIMESTAMP)");
				 FileInputStream inputNotiz = null;
				 FileInputStream inputBild = null;
				try {
					inputNotiz = new FileInputStream(((Gespraechsnotiz) obj).getNotiz().toString());
					ps.setBinaryStream(1, inputNotiz);

					inputBild = new FileInputStream(((Gespraechsnotiz) obj).getBild().toString());
					ps.setBinaryStream(2, inputBild);
				
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				 ps.setInt(3, ((Gespraechsnotiz) obj).getUnternehmen().getId());
				 ps.setInt(4, ((Gespraechsnotiz) obj).getBesuch().getId());
				 int result = ps.executeUpdate();ps.close();return result;
			}else
			if (obj instanceof Rolle) {
				PreparedStatement ps = con.prepareStatement(
						"INSERT INTO `rolle` (`rolle_id`, `rolle_bezeichnung`) VALUES (NULL, ?)",
						Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, ((Rolle) obj).getBezeichnung());
				int p = ps.executeUpdate();
				ResultSet rs = ps.getGeneratedKeys();
				rs.next();
				int auto_id = rs.getInt(1);
				rs.close();

				LinkedList<Berechtigung> lBerechtigung = ((Rolle) obj).getBerechtigung();
				for (Berechtigung e : lBerechtigung) {
					PreparedStatement ps2 = con.prepareStatement(
							"INSERT INTO `rolle_berechtigung` (`rolle_berechtigung_id`, `rolle_id`, `berechtigung_id`) VALUES (NULL, ?, ?)");
					ps2.setInt(1, e.getId());
					ps2.setInt(2, auto_id);
					ps2.executeUpdate();
					ps2.close();
				}
				ps.close();
				return p;
			} else
			if(obj instanceof Status){
				 PreparedStatement ps = con.prepareStatement("INSERT INTO `status` (`status_id`, `status_bezeichnung`) VALUES (NULL, ?)");
				 ps.setString(1, ((Status) obj).getBezeichnung());
				 int result = ps.executeUpdate();
				 ps.close();
				 return result;
			}else
			if(obj instanceof Unternehmen){
				PreparedStatement ps = con.prepareStatement("INSERT INTO `unternehmen` (`unternehmen_id`, `unternehmen_name`) VALUES (NULL, ?)");
				 ps.setString(1, ((Unternehmen) obj).getName());
				 int result = ps.executeUpdate();
				 ps.close();
				 return result;
			}
		}
			 catch (SQLException e1) {
			e1.printStackTrace();
		}
		return 0;
	}
	// Adresse
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
		int i = executeInsert(adresse);
		if(i==1)return true;
		return false;
	}
	public boolean changeAdresse(Adresse altAdresse, Adresse neuAdresse) {
		int i = executeUpdate(
				"UPDATE `adresse` SET `adresse_plz_id` = ?, `unternehmen_id` = ?, `adresse_strasse` = ?, `adresse_hausnummer` = ? WHERE `adresse`.`adresse_id` = ? ",
				new Object[] { neuAdresse.getPlz(), neuAdresse.getUnternehmen().getId(), neuAdresse.getStrasse(), neuAdresse.getHausnummer(), altAdresse.getId() });
		if(i!=0){
			return true;
		}
		return false;
	}
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
				Ansprechpartner ansprechpartner = new Ansprechpartner(id, vorname, nachname, adresse, null);
				LinkedList<Studiengang> lStudiengang = getStudiengangByAnsprechpartner(ansprechpartner);
				ansprechpartner =  new Ansprechpartner(id, vorname, nachname, adresse, lStudiengang);
				lAnsprechpartner.add(ansprechpartner);
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
		int i = executeInsert(ansprechpartner);
		if(i==1)return true;
		return false;

	}
	public boolean changeAnsprechpartner(Ansprechpartner altAnsprechpartner, Ansprechpartner neuAnsprechpartner) {
		int i = executeUpdate(
				"UPDATE `ansprechpartner` SET `adresse_id` = ?, `ansprechpartner_vorname` = ?, `ansprechpartner_nachname` = ? WHERE `ansprechpartner`.`ansprechpartner_id` = ? ",
				new Object[] { neuAnsprechpartner.getAdresse().getId(), neuAnsprechpartner.getVorname(), neuAnsprechpartner.getNachname(), altAnsprechpartner.getId() });
		if(i!=0){
			return true;
		}
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
				ansprechpartner = new Ansprechpartner(id, vorname, nachname, adresse, null);
				LinkedList<Studiengang> lStudiengang = getStudiengangByAnsprechpartner(ansprechpartner);
				ansprechpartner =  new Ansprechpartner(id, vorname, nachname, adresse, lStudiengang);
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
		int i = executeDelete(ansprechpartner);
		if(i==1)return true;
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
				Benutzer ben = new Benutzer(id, vorname, nachname, beruf, rolle, null);
				LinkedList<Studiengang> lStudiengang = getStudiengangByBenutzer(ben);
				ben = new Benutzer(id, vorname,nachname,beruf, rolle, lStudiengang);
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
			benutzer = new Benutzer(id, vorname, nachname, beruf, rolle, null);
			LinkedList<Studiengang> lStudiengang = getStudiengangByBenutzer(benutzer);
			benutzer = new Benutzer(id, vorname,nachname,beruf, rolle, lStudiengang);
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
		int i = executeUpdate(
				"UPDATE `benutzer` SET `vorname` = ?, `nachname` = ?, `benutzer_id` = ?, `rolle_id` = ?, `beruf_id` = ? WHERE `benutzer`.`benutzer_id` = ? ",
				new Object[] { neuBenutzer.getVorname(), neuBenutzer.getNachname(), neuBenutzer.getId(), neuBenutzer.getBeruf().getId(), altBenutzer.getId() });
		if(i!=0){
			return true;
		}
		return false;
	}
	public boolean deleteBenutzer(Benutzer benutzer) {
		int i = executeDelete(benutzer);
		if(i==1)return true;
		return false;
	}
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
				Benutzer benutzer = new Benutzer(id, vorname, nachname, beruf, rolle, null);
				LinkedList<Studiengang> lStudiengang = getStudiengangByBenutzer(benutzer);
				benutzer = new Benutzer(id, vorname,nachname,beruf, rolle, lStudiengang);
				lBenutzer.add(benutzer);
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
		int i = executeInsert(berechtigung);
		if(i==1)return true;
		return false;
	}
	public boolean changeBerechtigung(Berechtigung altBerechtigung, Berechtigung neuBerechtigung) {
		int i = executeUpdate(
				"UPDATE `berechtigung` SET `berechtigung_bezeichnung` = ? WHERE `berechtigung`.`berechtigung_id` = ?",
				new Object[] { neuBerechtigung.getBezeichnung(), altBerechtigung.getId()});
		if(i!=0){
			return true;
		}
		return false;
	}
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
	public boolean deleteBerechtigung(Berechtigung berechtigung){
		int i = executeDelete(berechtigung);
		if(i==1)return true;
		return false;
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
	public boolean createBeruf(Beruf beruf) {
		int i = executeInsert(beruf);
		if(i==1)return true;
		return false;
	}
	public boolean deleteBeruf(Beruf beruf){
		int i = executeDelete(beruf);
		if(i==1)return true;
		return false;
	}
	public boolean changeBeruf(Beruf altBeruf, Beruf neuBeruf) {
		int i = executeUpdate(
				"UPDATE `beruf` SET `beruf_bezeichnung` = ? WHERE `beruf`.`beruf_id` = 1 ",
				new Object[] { neuBeruf.getBezeichnung(), altBeruf.getId()});
		if(i!=0){
			return true;
		}
		return false;
	}

	// Besuch
	public LinkedList<Besuch> getBesucheByDate(Date date) {
		LinkedList<Besuch> lBesuch = new LinkedList<Besuch>();
		ResultSet res = executeQuery("select * from besuch where besuch_beginn = ?", new Object[] {(Object) date});
		try {
			while (res.next()) {
				int id = res.getInt("besuch_id");
				Adresse adresse = getAdresseById(res.getInt("adresse_id"));
				java.sql.Date timestamp = res.getDate("besuch_timestamp");
				java.sql.Date startDate = res.getDate("besuch_beginn");
				java.sql.Date endDate = res.getDate("besuch_ende");
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
				java.sql.Date startDate = res.getDate("besuch_beginn");
				java.sql.Date endDate = res.getDate("besuch_ende");
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
		int i = executeInsert(besuch);
		if(i==1)return true;
		return false;
	}
	public boolean deleteBesuch(Besuch besuch){
		int i = executeDelete(besuch);
		if(i==1)return true;
		return false;
	}
	public boolean changeBesuch(Besuch neuBesuch, Besuch altBesuch) {
		int i = executeUpdate(
				"UPDATE `besuch` SET `adresse_id` = ?, `besuch_beginn` = ?, `besuch_ende` = ?, `besuch_name` = ?, `besuch_autor` = ?, `status_id` = ?, `ansprechpartner_id` = ? WHERE `besuch`.`besuch_id` = ? ",
				new Object[] { neuBesuch.getAdresse().getId(), neuBesuch.getStartDate(), neuBesuch.getEndDate(), neuBesuch.getName(), neuBesuch.getAutor().getId(), neuBesuch.getStatus().getId(), neuBesuch.getAnsprechpartner().getId(), altBesuch.getId()});
		try {
			PreparedStatement ps1 = con.prepareStatement("DELETE FROM benutzer_besuch WHERE besuch_id = ?");
			ps1.setInt(1, altBesuch.getId());
			ps1.executeUpdate();
			ps1.close();
			LinkedList<Benutzer> lBenutzer = getBenutzerByBesuchId(altBesuch.getId());
			for(Benutzer e : lBenutzer){
			PreparedStatement ps2 = con.prepareStatement("INSERT INTO `benutzer_besuch` (`benutzer_besuch_id`, `benutzer_id`, `besuch_id`) VALUES (NULL, ?, ?)");
			ps2.setInt(1, altBesuch.getId());
			ps2.setString(2, e.getId());
			ps2.executeUpdate();
			ps2.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (i != 0) {
			return true;
		}
		return false;
	}
	
	// Gespraechsnotiz
	public Gespraechsnotiz getGespraechsnotizByBesuch(Besuch pBesuch) {
		ResultSet res = executeQuery("Select * from gespraechsnotiz where besuch_id = ?",
				new Object[] { new Integer(pBesuch.getId()) });
		Gespraechsnotiz gespraechsnotiz = null;
		try {
			while (res.next()) {
				int id = res.getInt("gespraechsnotiz_id");
				File notizfile = new File("notiz");
				FileOutputStream output1 = new FileOutputStream(notizfile);
				
				InputStream input1 = res.getBinaryStream("gespraechsnotiz_notiz");
				byte[] notiz = new byte[1024];
				while (input1.read(notiz) > 0) {
					output1.write(notiz);
				}
				output1.close();
				
				File file = new File("bild");
				FileOutputStream output2 = new FileOutputStream(file);

				InputStream input2 = res.getBinaryStream("resume");
				byte[] bild = new byte[1024];
				while (input2.read(bild) > 0) {
					output2.write(bild);
				}
				output2.close();
				Unternehmen unternehmen = getUnternehmenById(res.getInt("unternehmen_id"));
				Besuch besuch = getBesuchById(res.getInt("besuch_id"));
				Date timestamp = res.getDate("gespraechsnotiz_timestamp");
				gespraechsnotiz = new Gespraechsnotiz(id, notiz, bild, unternehmen, besuch, timestamp);
			}
		} catch (SQLException | IOException e) {
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
		int i = executeInsert(gespraechsnotiz);
		if(i==1)return true;
		return false;

	}
	public boolean changeGespraechsnotiz(Gespraechsnotiz altGespraechsnotiz, Gespraechsnotiz neuGespraechsnotiz) {
		deleteGespreachsnotiz(altGespraechsnotiz);
		boolean i = createGespraechsnotiz(neuGespraechsnotiz);
		return i;
	}
	public boolean deleteGespreachsnotiz(Gespraechsnotiz gespreachsnotiz){
		int i = executeDelete(gespreachsnotiz);
		if(i==1)return true;
		return false;
	}
	
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
		int i = executeInsert(rolle);
		if(i==1)return true;
		return false;
	}
	public boolean changeRolle(Rolle altRolle, Rolle neuRolle) {
		int i = executeUpdate(
				"UPDATE `rolle` `rolle_bezeichnung` = ? WHERE `rolle`.`rolle_id` = ? ",
				new Object[] { neuRolle.getBezeichnung(), altRolle.getId()});
			try {
				PreparedStatement ps1 = con.prepareStatement("DELETE FROM rolle_berechtigung WHERE rolle_id = ?");
				ps1.setInt(1, altRolle.getId());
				ps1.executeUpdate();
				ps1.close();
				LinkedList<Berechtigung> lBerechtigung = getBerechtigungByRolle(altRolle);
				for(Berechtigung e : lBerechtigung){
				PreparedStatement ps2 = con.prepareStatement("INSERT INTO `rolle_berechtigung` (`rolle_berechtigung_id`, `rolle_id`, `berechtigung_id`) VALUES (NULL, ?, ?)");
				ps2.setInt(1, altRolle.getId());
				ps2.setInt(2, e.getId());
				ps2.executeUpdate();
				ps2.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		if (i != 0) {
			return true;
		}
		return false;
	}
	
	public boolean deleteRolle(Rolle rolle){
		int i = executeDelete(rolle);
		if(i==1)return true;
		return false;
	}
	
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
		int i = executeInsert(status);
		if(i==1)return true;
		return false;

	}
	public boolean changeStatus(Status altStatus, Status neuStatus) {
		int i = executeUpdate(
				"UPDATE `status` SET`status_bezeichnung` = ? WHERE `status`.`status_id` = ? ",
				new Object[] {neuStatus.getBezeichnung(), altStatus.getId()});
		if (i != 0) {
			return true;
		}
		return false;
	}
	public boolean deleteStatus(Status status){
		int i = executeDelete(status);
		if(i==1)return true;
		return false;
	}
	
	// Studiengang
	public Studiengang getStudiengangById(int pId) {
		Studiengang studiengang = null;
		try {
			ResultSet res = executeQuery("SELECT * FROM studiengang WHERE studiengang_id = ?",
					new Object[] { (Object) new Integer(pId) });
			while (res.next()) {
				int id = res.getInt("studiengang_id");
				String name = res.getString("studiengang_bezeichnung");
				studiengang = new Studiengang(id, name);
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
		return studiengang;
	}
	public LinkedList<Studiengang> getStudiengangByBenutzer(Benutzer benutzer){
	LinkedList<Studiengang> lStudiengang = new LinkedList<Studiengang>();
	ResultSet res = executeQuery("SELECT * FROM studiengang_benutzer WHERE benutzer_id = ?", new Object[]{(Object) benutzer.getId()});
	try {
			while(res.next()){
				int id = res.getInt("studiengang_id");
				lStudiengang.add(getStudiengangById(id));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			res.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lStudiengang;
	}
	public LinkedList<Studiengang> getStudiengangByAnsprechpartner(Ansprechpartner ansprechpartner){
		LinkedList<Studiengang> lStudiengang = new LinkedList<Studiengang>();
		ResultSet res = executeQuery("SELECT * FROM studiengang_ansprechpartner WHERE ansprechpartner_id = ?", new Object[]{(Object) ansprechpartner.getId()});
		try {
				while(res.next()){
					int id = res.getInt("studiengang_id");
					lStudiengang.add(getStudiengangById(id));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				res.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return lStudiengang;
		}
	
	
	// Unternhmen 
	public Unternehmen getUnternehmenById(int pId) {
		Unternehmen unternehmen = null;
		try {
			ResultSet res = executeQuery("select * from unternehmen where unternehmen_id = ?", new Object[]{(Object) new Integer(pId)});
			while (res.next()) {
				int id = res.getInt("unternehmen_id");
				String name = res.getString("unternehmen_name");
				String kennzeichen = res.getString("unternehmen_abc_kennzeichen");
				LinkedList<Ansprechpartner> lAnsprechpartner = new LinkedList<Ansprechpartner>();
				LinkedList<Adresse> lAdresse = new LinkedList<Adresse>();
				unternehmen = new Unternehmen(id, name, lAnsprechpartner, lAdresse, kennzeichen);
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
				String kennzeichen = res.getString("unternehmen_abc_kennzeichen");
				LinkedList<Ansprechpartner> lAnsprechpartner = new LinkedList<Ansprechpartner>();
				LinkedList<Adresse> lAdresse = new LinkedList<Adresse>();
				unternehmen = new Unternehmen(id, name, lAnsprechpartner, lAdresse, kennzeichen);
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
		int i = executeInsert(unternehmen);
		if(i==1)return true;
		return false;

	}
	public boolean changeUnternehmen(Unternehmen altUnternehmen, Unternehmen neuUnternehmen) {
		int i = executeUpdate(
				"UPDATE `unternehmen` SET `unternehmen_name` = ? WHERE `unternehmen`.`unternehmen_id` = ? ",
				new Object[] { neuUnternehmen.getName(), altUnternehmen.getId()});
		if (i != 0) {
			return true;
		}
		return false;
	}
	public boolean deleteUnternehmen(Unternehmen unternehmen){
		int i = executeDelete(unternehmen);
		if(i==1)return true;
		return false;
	}
	
	
}
