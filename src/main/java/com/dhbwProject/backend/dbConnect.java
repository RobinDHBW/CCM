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
	private PreparedStatement preparedStatement;

	public dbConnect() throws ClassNotFoundException, SQLException {
		
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ccm_db", "root", "");
	}
	public void close() throws SQLException {
		
//			if (res != null)
//				if (!res.isClosed())
//					res.close();
//			if (stat != null)
//				if (!stat.isClosed())
//					stat.close();
			if (preparedStatement != null)
				if (!preparedStatement.isClosed())
				preparedStatement.close();
			con.close();
		
			
		
	}

	private ResultSet executeQuery(String sql, Object... objects) throws SQLException {
		
			preparedStatement = con.prepareStatement(sql);
			int q = 0;
			for (Object e : objects) {
				q++;
				if (e instanceof Integer) {
					Integer i = (Integer) e;
					preparedStatement.setInt(q, i.intValue());
				} else if(e instanceof Date){
					java.sql.Timestamp d = (java.sql.Timestamp) e;
					preparedStatement.setTimestamp(q,  d);
				} else{
					String s = (String) e;
					preparedStatement.setString(q, s);
				}
			}
			ResultSet res = preparedStatement.executeQuery();
			//ps.close();
			return res;

		
	}
	private int executeUpdate(String sql, Object... objects) throws SQLException  {
		
			PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			int q = 0;
			for (Object e : objects) {
				q++;
				if (e instanceof Integer) {
					Integer i = (Integer) e;
					ps.setInt(q, i.intValue());
				} else if(e instanceof Date){
					java.sql.Timestamp d = new java.sql.Timestamp(((Date) e).getTime());
					ps.setTimestamp(q,  d);
				} else{
					String s = (String) e;
					ps.setString(q, s);
				}
			}
			ps.executeUpdate();
			try{
			ResultSet result = ps.getGeneratedKeys();
			 result.next();
			 int auto_id = result.getInt(1);
			 result.close();
			 ps.close();
			 return auto_id;
			}catch (SQLException e){
				 ps.close();
				 return -1;
			}

		
	}
	private <T> int executeDelete(T obj) throws SQLException {
		
		
			if(obj instanceof Adresse){
				 PreparedStatement ps = con.prepareStatement("DELETE FROM `adresse` WHERE `adresse_plz_id` = ? AND `adresse_strasse` = ? AND `adresse_hausnummer` = ?)");
				 ps.setString(1, ((Adresse) obj).getPlz());
				 ps.setString(2, ((Adresse) obj).getStrasse());
				 ps.setString(3, ((Adresse) obj).getHausnummer());
				 int result = ps.executeUpdate();ps.close();return result;
			}else
			if(obj instanceof Ansprechpartner){
				 PreparedStatement ps = con.prepareStatement("DELETE FROM `ansprechpartner` WHERE `ansprechpartner_vorname` = ? AND `ansprechpartner_nachname` = ? AND `adresse_id` = ?)");
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
				 ps.setTimestamp(2, ((Besuch) obj).getStartDate());
				 ps.setTimestamp(3, ((Besuch) obj).getEndDate());
				 ps.setString(4, ((Besuch) obj).getName());
				 ps.setString(5, ((Besuch) obj).getAutor().getId()); //@Robin Bahr 09.01.2017 20:59 Uhr
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
				try{
					inputNotiz = new FileInputStream(((Gespraechsnotiz) obj).getNotiz().toString());
					ps.setBinaryStream(1, inputNotiz);

					inputBild = new FileInputStream(((Gespraechsnotiz) obj).getBild().toString());
					ps.setBinaryStream(2, inputBild);
				
				} catch (FileNotFoundException e) {
					
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
		
		return 0;
	}
	private <T> int executeInsert(T obj) throws SQLException {
		
		
			if(obj instanceof Adresse){
				 PreparedStatement ps = con.prepareStatement("INSERT INTO `adresse` (`adresse_plz_id`, `adresse_strasse`, `adresse_hausnummer`, `adresse_ort`, `unternehmen_id`) VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
				 ps.setString(1, ((Adresse) obj).getPlz());
				 ps.setString(2, ((Adresse) obj).getStrasse());
				 ps.setString(3, ((Adresse) obj).getHausnummer());
				 ps.setString(4, ((Adresse) obj).getOrt());
				 ps.setInt(5, ((Adresse) obj).getUnternehmen().getId());
				 ps.executeUpdate();
				 ResultSet result = ps.getGeneratedKeys();
				 result.next();
				 int auto_id = result.getInt(1);
				 result.close();
				 ps.close();
				 return auto_id;
			}else
			if(obj instanceof Ansprechpartner){
				PreparedStatement ps = con.prepareStatement("INSERT INTO `ansprechpartner` (`ansprechpartner_id`, `ansprechpartner_vorname`, `ansprechpartner_nachname`, `adresse_id`, `ansprechpartner_emailadresse`, `ansprechpartner_telefonnummer`, `ansprechpartner_unternehmen_id`) VALUES (NULL, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, ((Ansprechpartner) obj).getVorname());
				ps.setString(2, ((Ansprechpartner) obj).getNachname());
				ps.setInt(3, ((Ansprechpartner) obj).getAdresse().getId());
				ps.setString(4, ((Ansprechpartner) obj).getEmailadresse());
				ps.setString(5, ((Ansprechpartner) obj).getTelefonnummer());
//				ps.setInt(4,  getUnternehmenByAdresse(((Ansprechpartner) obj).getAdresse()).getId());
				ps.setInt(6,  ((Ansprechpartner) obj).getAdresse().getUnternehmen().getId()); //By Robin Bahr 22.01.2017 22:30 Uhr
				
				ps.executeUpdate();
				ResultSet result = ps.getGeneratedKeys();
				 result.next();
				 int auto_id = result.getInt(1);
				 result.close();
				 ps.close();
				 return auto_id;

			}else
			if(obj instanceof Benutzer){
				 PreparedStatement ps = con.prepareStatement("INSERT INTO `benutzer` (`vorname`, `nachname`, `benutzer_id`, `rolle_id`, `beruf_id`) VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
				 ps.setString(1, ((Benutzer) obj).getVorname());
				 ps.setString(2, ((Benutzer) obj).getNachname());
				 ps.setString(3, ((Benutzer) obj).getId());
				 ps.setInt(4, ((Benutzer) obj).getRolle().getId());
				 ps.setInt(5, ((Benutzer) obj).getBeruf().getId());
				 ps.executeUpdate();
				 ResultSet result = ps.getGeneratedKeys();
				 result.next();
				 int auto_id = result.getInt(1);
				 result.close();
				 ps.close();
				 return auto_id;
			}else
			if(obj instanceof Berechtigung){
				 PreparedStatement ps = con.prepareStatement("INSERT INTO `berechtigung` (`berechtigung_id`, `berechtigung_bezeichnung`) VALUES (NULL, ?)", Statement.RETURN_GENERATED_KEYS);
				 ps.setString(1, ((Berechtigung) obj).getBezeichnung());
				 ps.executeUpdate();
				 ResultSet result = ps.getGeneratedKeys();
				 result.next();
				 int auto_id = result.getInt(1);
				 result.close();
				 ps.close();
				 return auto_id;
			}else
			if(obj instanceof Beruf){
				 PreparedStatement ps = con.prepareStatement("INSERT INTO `beruf` (`beruf_id`, `beruf_bezeichnung`) VALUES (NULL, ?)");
				 ps.setString(1, ((Beruf) obj).getBezeichnung());
				 ps.executeUpdate();
				 ResultSet result = ps.getGeneratedKeys();
				 result.next();
				 int auto_id = result.getInt(1);
				 result.close();
				 ps.close();
				 return auto_id;
			}else
			if(obj instanceof Besuch){
				 PreparedStatement ps = con.prepareStatement("INSERT INTO `besuch` (`besuch_id`, `adresse_id`, `besuch_timestamp`, `besuch_beginn`, `besuch_ende`, `besuch_name`, `besuch_autor`, `status_id`, `ansprechpartner_id`) VALUES (NULL, ?, CURRENT_TIMESTAMP, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
				 ps.setInt(1, ((Besuch) obj).getAdresse().getId());
				 ps.setTimestamp(2, ((Besuch) obj).getStartDate());
				 ps.setTimestamp(3, ((Besuch) obj).getEndDate());
				 ps.setString(4, ((Besuch) obj).getName());
				 ps.setString(5, ((Besuch) obj).getAutor().getId());//@Robin Bahr 09.01.2017 20:59 Uhr
				 ps.setInt(6, ((Besuch) obj).getStatus().getId());
				 ps.setInt(7, ((Besuch) obj).getAnsprechpartner().getId());
				 
				 int p = ps.executeUpdate();
				    ResultSet rs = ps.getGeneratedKeys();
				     rs.next();
				    int auto_id = rs.getInt(1);
				 rs.close();
				 LinkedList<Benutzer> lBenutzer = ((Besuch) obj).getBesucher();
				 for(Benutzer e : lBenutzer){
					 PreparedStatement ps2 = con.prepareStatement("INSERT INTO `benutzer_besuch` (`benutzer_besuch_id`, `benutzer_id`, `besuch_id`) VALUES (NULL, ?, ?)", Statement.RETURN_GENERATED_KEYS);
					 ps2.setString(1, e.getId());
					 ps2.setInt(2, auto_id);
					 ps2.executeUpdate();
					 ps2.close();
				 	}
				 ps.close();
				 return p;
				 }else
			if(obj instanceof Gespraechsnotiz){
				 PreparedStatement ps = con.prepareStatement("INSERT INTO `gespraechsnotizen` (`gespraechsnotiz_id`, `gespraechsnotiz_notiz`, `gespraechsnotiz_bild`, `unternehmen_id`, `besuch_id`, `gespraechsnotiz_timestamp`) VALUES (NULL, ?, ?, ?, ?, CURRENT_TIMESTAMP)", Statement.RETURN_GENERATED_KEYS);
				 FileInputStream inputNotiz = null;
				 FileInputStream inputBild = null;
				try{
//					inputNotiz = new FileInputStream("C:/Users/CCM/Desktop/test.txt");
					inputNotiz = new FileInputStream(((Gespraechsnotiz) obj).getNotiz());
					ps.setBinaryStream(1, inputNotiz);

//					inputBild = new FileInputStream("C:/Users/CCM/Desktop/test.txt");
					inputBild = new FileInputStream(((Gespraechsnotiz) obj).getBild());
					ps.setBinaryStream(2, inputBild);
				
				} catch (FileNotFoundException e) {
					System.out.println("File not found");
				}
				 ps.setInt(3, ((Gespraechsnotiz) obj).getUnternehmen().getId());
				 ps.setInt(4, ((Gespraechsnotiz) obj).getBesuch().getId());
				 ps.executeUpdate();
				 ResultSet result = ps.getGeneratedKeys();
				 result.next();
				 int auto_id = result.getInt(1);
				 result.close();
				 ps.close();
				 return auto_id;
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
							"INSERT INTO `rolle_berechtigung` (`rolle_berechtigung_id`, `rolle_id`, `berechtigung_id`) VALUES (NULL, ?, ?)", Statement.RETURN_GENERATED_KEYS);
					ps2.setInt(1, e.getId());
					ps2.setInt(2, auto_id);
					ps2.executeUpdate();
					ps2.close();
				}
				ps.close();
				return p;
			} else
			if(obj instanceof Status){
				 PreparedStatement ps = con.prepareStatement("INSERT INTO `status` (`status_id`, `status_bezeichnung`) VALUES (NULL, ?)", Statement.RETURN_GENERATED_KEYS);
				 ps.setString(1, ((Status) obj).getBezeichnung());
				ps.executeUpdate();
				 ResultSet result = ps.getGeneratedKeys();
				 result.next();
				 int auto_id = result.getInt(1);
				 result.close();
				 ps.close();
				 return auto_id;
			}else
				if(obj instanceof Studiengang){
					PreparedStatement ps = con.prepareStatement("INSERT INTO `studiengang` (`studiengang_id`, `studiengang_bezeichnung`) VALUES (NULL, ?)", Statement.RETURN_GENERATED_KEYS);
					 ps.setString(1, ((Studiengang) obj).getBezeichnung());
					 ps.executeUpdate();
					 ResultSet result = ps.getGeneratedKeys();
					 result.next();
					 int auto_id = result.getInt(1);
					 result.close();
					 ps.close();
					 return auto_id;
			}else
			if(obj instanceof Unternehmen){
				PreparedStatement ps = con.prepareStatement("INSERT INTO `unternehmen` (`unternehmen_id`, `unternehmen_name`, `unternehmen_abc_kennzeichen`) VALUES (NULL, ?, ?)", Statement.RETURN_GENERATED_KEYS);
				 ps.setString(1, ((Unternehmen) obj).getName());
				 ps.setString(2, ((Unternehmen) obj).getKennzeichen());
				 ps.executeUpdate();
				 ResultSet result = ps.getGeneratedKeys();
				 result.next();
				 int auto_id = result.getInt(1);
				 result.close();
				 ps.close();
				 return auto_id;
			}
			
		
		return 0;
	}
	// Adresse
	public LinkedList<Adresse> getAllAdresse() throws SQLException{
		LinkedList<Adresse> lAdresse = new LinkedList<Adresse>();
		ResultSet res = executeQuery("select * from adresse", new Object[] {});
		
			while (res.next()) {
				int id = res.getInt("adresse_id");
				String plz = res.getString("adresse_plz_id");
				String ort = res.getString("adresse_ort");
				String strasse = res.getString("adresse_strasse");
				String hausnummer = res.getString("adresse_hausnummer");
				Unternehmen unternehmen = getUnternehmenById(res.getInt("unternehmen_id"));
				Adresse adresse = new Adresse(id, plz, ort, strasse, hausnummer, unternehmen);
				lAdresse.add(adresse);
			}
		
		return lAdresse;
	}
	public LinkedList<Adresse> getAdresseByStudiengang(Studiengang studiengang) throws SQLException{
		LinkedList<Adresse> lAdresse = new LinkedList<Adresse>();
		ResultSet res = executeQuery("SELECT * FROM `adresse`, ansprechpartner,studiengang,studiengang_ansprechpartner WHERE ansprechpartner.adresse_id = adresse.adresse_id AND ansprechpartner.ansprechpartner_id = studiengang_ansprechpartner.ansprechpartner_id AND studiengang.studiengang_id = studiengang_ansprechpartner.studiengang_id AND studiengang.studiengang_id = ?", new Object[] {(Object) new Integer(studiengang.getId())});
		
			while (res.next()) {
				int id = res.getInt("adresse_id");
				String plz = res.getString("adresse_plz_id");
				String ort = res.getString("adresse_ort");
				String strasse = res.getString("adresse_strasse");
				String hausnummer = res.getString("adresse_hausnummer");
				Unternehmen unternehmen = getUnternehmenById(res.getInt("unternehmen_id"));
				Adresse adresse = new Adresse(id, plz, ort, strasse, hausnummer, unternehmen);
				lAdresse.add(adresse);
			}
		
		return lAdresse;
	}
	public Adresse getAdresseById(int pId) throws SQLException {
		Adresse adresse = null;
		
			ResultSet res = executeQuery("select * from adresse where adresse_id = ?", (Object[]) new Integer[] { pId });
			while (res.next()) {
				int id = res.getInt("adresse_id");
				String plz = res.getString("adresse_plz_id");
				String ort = res.getString("adresse_ort");
				String strasse = res.getString("adresse_strasse");
				String hausnummer = res.getString("adresse_hausnummer");
				Unternehmen unternehmen = getUnternehmenById(res.getInt("unternehmen_id"));
				adresse = new Adresse(id, plz, ort, strasse, hausnummer, unternehmen);
			}
			res.close();
			return adresse;
		
	}
	public Adresse createAdresse(Adresse adresse) throws SQLException {
		int i = executeInsert(adresse);
		return getAdresseById(i);
	}
	public Adresse changeAdresse(Adresse altAdresse, Adresse neuAdresse) throws SQLException {
		int i = executeUpdate(
				"UPDATE `adresse` SET `adresse_plz_id` = ?, `adresse_strasse` = ?, `adresse_hausnummer` = ?, adresse_ort = ? WHERE `adresse`.`adresse_id` = ? ",
				new Object[] { neuAdresse.getPlz(), neuAdresse.getStrasse(), neuAdresse.getHausnummer(), neuAdresse.getOrt(), altAdresse.getId() });
		return getAdresseById(altAdresse.getId());
	}
	public LinkedList<Adresse> getAdresseByUnternehmen(Unternehmen pUnternehmen) throws SQLException {
		LinkedList<Adresse> lAdresse = new LinkedList<Adresse>();
		ResultSet res = executeQuery("select * from adresse a, Unternehmen u where u.unternehmen_id = a.unternehmen_id and u.unternehmen_id = ?", new Object[]{(Object) new Integer(pUnternehmen.getId())});
		
			while(res.next()){
				int id = res.getInt("adresse_id");
				String plz = res.getString("adresse_plz_id"); 
				String ort = res.getString("adresse_ort"); 
				String strasse = res.getString("adresse_strasse"); 
				String hausnummer = res.getString("adresse_hausnummer");
				Unternehmen unternehmen = getUnternehmenById(res.getInt("unternehmen_id"));
				lAdresse.add(new Adresse(id, plz, ort, strasse, hausnummer, unternehmen));
			}
		
			res.close();
		return lAdresse;
	}
	
	// Ansprechpartner
	private LinkedList<Ansprechpartner> getAnsprechpartnerByUnternehmen(Unternehmen unternehmen) throws SQLException {
		ResultSet res = executeQuery("Select * from ansprechpartner where ansprechpartner_unternehmen_id = ?", new Object[] {(Object) new Integer(unternehmen.getId())});
		LinkedList<Ansprechpartner> lAnsprechpartner = new LinkedList<Ansprechpartner>();
		
			while(res.next()){
				int id = res.getInt("ansprechpartner_id");
				String vorname = res.getString("ansprechpartner_vorname");
				String nachname = res.getString("ansprechpartner_nachname");
				Adresse adresse = null;
				adresse = getAdresseById(res.getInt("adresse_id"));
				String email = res.getString("ansprechpartner_emailadresse");
				String telefon = res.getString("ansprechpartner_telefonnummer");
				Ansprechpartner ansprechpartner = new Ansprechpartner(id, vorname, nachname, adresse, null, email, telefon);
				LinkedList<Studiengang> lStudiengang = getStudiengangByAnsprechpartner(ansprechpartner);
				ansprechpartner =  new Ansprechpartner(id, vorname, nachname, adresse, lStudiengang, email, telefon);
				lAnsprechpartner.add(ansprechpartner);
			}
			res.close();
		return lAnsprechpartner;
	}
	public LinkedList<Ansprechpartner> getAnsprechpartnerByAdresse(Adresse pAdresse) throws SQLException{
		ResultSet res = executeQuery("Select * from ansprechpartner where adresse_id = ?", new Object[] {(Object) new Integer(pAdresse.getId())});
		LinkedList<Ansprechpartner> lAnsprechpartner = new LinkedList<Ansprechpartner>();
		
			while(res.next()){
				int id = res.getInt("ansprechpartner_id");
				String vorname = res.getString("ansprechpartner_vorname");
				String nachname = res.getString("ansprechpartner_nachname");
				Adresse adresse = null;
				adresse = getAdresseById(res.getInt("adresse_id"));
				String email = res.getString("ansprechpartner_emailadresse");
				String telefon = res.getString("ansprechpartner_telefonnummer");
				Ansprechpartner ansprechpartner = new Ansprechpartner(id, vorname, nachname, adresse, null, email, telefon);
				LinkedList<Studiengang> lStudiengang = getStudiengangByAnsprechpartner(ansprechpartner);
				ansprechpartner =  new Ansprechpartner(id, vorname, nachname, adresse, lStudiengang, email, telefon);
				lAnsprechpartner.add(ansprechpartner);
			}
			res.close();
		return lAnsprechpartner;
	}
	public Ansprechpartner createAnsprechpartner(Ansprechpartner ansprechpartner) throws SQLException {
		int i = executeInsert(ansprechpartner);
		return getAnsprechpartnerById(i);

	}
	public Ansprechpartner changeAnsprechpartner(Ansprechpartner altAnsprechpartner, Ansprechpartner neuAnsprechpartner) throws SQLException {
		int i = executeUpdate(
				"UPDATE `ansprechpartner` SET `adresse_id` = ?, `ansprechpartner_vorname` = ?, `ansprechpartner_nachname` = ?, `adresse_id` = ?, `ansprechpartner_emailadresse` = ?, `ansprechpartner_telefonnummer` = ?, `ansprechpartner_unternehmen_id` = ? WHERE `ansprechpartner`.`ansprechpartner_id` = ? ",
				new Object[] { neuAnsprechpartner.getAdresse().getId(), neuAnsprechpartner.getVorname(), neuAnsprechpartner.getNachname(), neuAnsprechpartner.getAdresse().getId(), neuAnsprechpartner.getEmailadresse(), neuAnsprechpartner.getTelefonnummer(), neuAnsprechpartner.getAdresse().getUnternehmen().getId(), altAnsprechpartner.getId() });
		return getAnsprechpartnerById(altAnsprechpartner.getId());
	
	}
	public Ansprechpartner getAnsprechpartnerById(int pId) throws SQLException{
		ResultSet res = executeQuery("Select * from ansprechpartner where ansprechpartner_id = ?", new Object[] {new Integer(pId)});
		Ansprechpartner ansprechpartner = null;
		
			while(res.next()){
				int id = res.getInt("ansprechpartner_id");
				String vorname = res.getString("ansprechpartner_vorname");
				String nachname = res.getString("ansprechpartner_nachname");
				Adresse adresse = getAdresseById(res.getInt("adresse_id"));
				String email = res.getString("ansprechpartner_emailadresse");
				String telefon = res.getString("ansprechpartner_telefonnummer");
				ansprechpartner = new Ansprechpartner(id, vorname, nachname, adresse, null, email, telefon);
				LinkedList<Studiengang> lStudiengang = getStudiengangByAnsprechpartner(ansprechpartner);
				ansprechpartner =  new Ansprechpartner(id, vorname, nachname, adresse, lStudiengang, email, telefon);
			}
			res.close();
		return ansprechpartner;
	}
	public boolean deleteAnsprechpartner(Ansprechpartner ansprechpartner) throws SQLException{
		int i = executeDelete(ansprechpartner);
		if(i==1)return true;
		return false;
	}
	
	// Benutzer
	public LinkedList<Benutzer> getAllBenutzer() throws SQLException {
		LinkedList<Benutzer> benArr = new LinkedList<Benutzer>();
		ResultSet res = executeQuery("select * from benutzer", new Object[] {});
		
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
		
		return benArr;
	}
	public Benutzer getBenutzerById(String pId) throws SQLException{
		Benutzer benutzer = null;
		ResultSet res = null;
			res = executeQuery("select * from benutzer where benutzer_id = ?", new Object[] {(Object) pId});
			res.next();
			String id = res.getString("benutzer_id");
			String vorname = res.getString("vorname");
			String nachname = res.getString("nachname");
			Beruf beruf = getBerufById(res.getInt("beruf_id"));
			Rolle rolle = getRolleById(res.getInt("rolle_id"));
			benutzer = new Benutzer(id, vorname, nachname, beruf, rolle, null);
			LinkedList<Studiengang> lStudiengang = getStudiengangByBenutzer(benutzer);
			benutzer = new Benutzer(id, vorname,nachname,beruf, rolle, lStudiengang);

			res.close();
		return benutzer;
	}
	public Benutzer createBenutzer(Benutzer b) throws SQLException {
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
		LinkedList<Studiengang> lStudiengang = b.getStudiengang();
		for (Studiengang e : lStudiengang) {
			PreparedStatement ps2 = con.prepareStatement(
					"INSERT INTO `studiengang_benutzer` (`studiengang_benutzer_id`, `studiengang_id`, `benutzer_id`) VALUES (NULL, ?, ?)");
			ps2.setInt(1, getStudiengangByBezeichnung(e.getBezeichnung()).getId());
			ps2.setString(2, b.getId());
			ps2.executeUpdate();
			ps2.close();
		}
		return getBenutzerById(b.getId());
	}
	public Benutzer changeBenutzer(Benutzer altBenutzer, Benutzer neuBenutzer) throws SQLException {
		int i = executeUpdate(
				"UPDATE `benutzer` SET `vorname` = ?, `nachname` = ?, `benutzer_id` = ?, `rolle_id` = ?, `beruf_id` = ? WHERE `benutzer`.`benutzer_id` = ? ",
				new Object[] { neuBenutzer.getVorname(), neuBenutzer.getNachname(), neuBenutzer.getId(), neuBenutzer.getBeruf().getId(),neuBenutzer.getRolle().getId(), altBenutzer.getId() });
		PreparedStatement ps1 = con.prepareStatement("DELETE FROM studiengang_benutzer WHERE benutzer_id = ?");
		ps1.setString(1, altBenutzer.getId());
		ps1.executeUpdate();
		ps1.close();
		for (Studiengang e : neuBenutzer.getStudiengang()) {
			PreparedStatement ps2 = con.prepareStatement(
					"INSERT INTO `studiengang_benutzer` (`studiengang_benutzer_id`, `studiengang_id`, `benutzer_id`) VALUES (NULL, ?, ?)");
			ps2.setInt(1, e.getId());
			ps2.setString(2, altBenutzer.getId());
			ps2.executeUpdate();
			ps2.close();
		}
		return getBenutzerById(neuBenutzer.getId());
	}
	public boolean deleteBenutzer(Benutzer benutzer) throws SQLException {
		int i = executeDelete(benutzer);
		if(i==1)return true;
		return false;
	}
	public LinkedList<Benutzer> getBenutzerByBesuchId(int pId) throws SQLException{
		LinkedList<Benutzer> lBenutzer = new LinkedList<Benutzer>();
		ResultSet res = executeQuery("select * from benutzer b, besuch bs, benutzer_besuch bb where b.benutzer_id=bb.benutzer_id and bs.besuch_id = bb.besuch_id and bs.besuch_id = ?", new Object[]{(Object) new Integer(pId)});
		
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
		

		
			res.close();
		return lBenutzer;
	}
	
	// Berechtigung
	public LinkedList<Berechtigung> getBerechtigungByRolle(Rolle rolle) throws SQLException {
		LinkedList<Berechtigung> lBerechtigung = new LinkedList<Berechtigung>();
		ResultSet res = executeQuery(
				"Select * from berechtigung b, rolle_berechtigung rb where rb.berechtigung_id = b.berechtigung_id and rolle_id = ?",
				new Object[] { new Integer(rolle.getId()) });
		
			while (res.next()) {
				int id = res.getInt("berechtigung_id");
				String bezeichnung = res.getString("berechtigung_bezeichnung");
				lBerechtigung.add(new Berechtigung(id, bezeichnung));
			}
				res.close();
		return lBerechtigung;
	}
	public Berechtigung createBerechtigung(Berechtigung berechtigung) throws SQLException {
		int i = executeInsert(berechtigung);
		return getBerechtigunById(i);
	}
	public Berechtigung changeBerechtigung(Berechtigung altBerechtigung, Berechtigung neuBerechtigung) throws SQLException {
		int i = executeUpdate(
				"UPDATE `berechtigung` SET `berechtigung_bezeichnung` = ? WHERE `berechtigung`.`berechtigung_id` = ?",
				new Object[] { neuBerechtigung.getBezeichnung(), altBerechtigung.getId()});
		return getBerechtigunById(altBerechtigung.getId());
	}
	public Berechtigung getBerechtigunById(int pId) throws SQLException{
		Berechtigung berechtigung = null;
		ResultSet res = executeQuery(
				"select * from berechtigung where berechtigung_id = ?", new Object[] { new Integer(pId) });
		
			while (res.next()) {
				int id = res.getInt("berechtigung_id");
				String bezeichnung = res.getString("berechtigung_bezeichnung");
				berechtigung = new Berechtigung(id, bezeichnung);
			}
		
			
			
				res.close();
		
		return berechtigung;
	}
	public boolean deleteBerechtigung(Berechtigung berechtigung) throws SQLException{
		int i = executeDelete(berechtigung);
		if(i==1)return true;
		return false;
	}
	
	// Beruf
	public LinkedList<Beruf> getAllBeruf() throws SQLException{
		LinkedList<Beruf> lBeruf = new LinkedList<Beruf>();
		ResultSet res = executeQuery("select * from beruf", new Object[]{});
		Beruf beruf = null;
		
			while (res.next()) {
				int id = res.getInt("beruf_id");
				String bezeichnung = res.getString("beruf_bezeichnung");
				beruf = new Beruf(id, bezeichnung);
				lBeruf.add(beruf);
			}
		
			res.close();
		return lBeruf;
	}
	public Beruf getBerufById(int beruf_id) throws SQLException{
		ResultSet res = executeQuery("select * from beruf where beruf_id = ?", new Object[]{(Object) new Integer(beruf_id)});
		Beruf beruf = null;
		
			while (res.next()) {
				int id = res.getInt("beruf_id");
				String bezeichnung = res.getString("beruf_bezeichnung");
				beruf = new Beruf(id, bezeichnung);
			}
		
			res.close();
		return beruf;
	}
	public Beruf getBerufByBezeichnung(String beruf_bezeichnung) throws SQLException {
		ResultSet res1 = executeQuery("select * from beruf where beruf_bezeichnung = ?", new Object[]{(Object) beruf_bezeichnung});
		Beruf beruf = null;
		
			while (res1.next()) {
				int id = res1.getInt("beruf_id");
				String bezeichnung = res1.getString("beruf_bezeichnung");
				beruf = new Beruf(id, bezeichnung);
			}
			res1.close();
		return beruf;
	}
	public Beruf createBeruf(Beruf beruf) throws SQLException {
		int i = executeInsert(beruf);
		return getBerufById(i);
	}
	public boolean deleteBeruf(Beruf beruf) throws SQLException{
		int i = executeDelete(beruf);
		if(i==1)return true;
		return false;
	}
	public Beruf changeBeruf(Beruf altBeruf, Beruf neuBeruf) throws SQLException {
		int i = executeUpdate(
				"UPDATE `beruf` SET `beruf_bezeichnung` = ? WHERE `beruf`.`beruf_id` = 1 ",
				new Object[] { neuBeruf.getBezeichnung(), altBeruf.getId()});
		return getBerufById(altBeruf.getId());
	}

	// Besuch
	public LinkedList<Besuch> getBesucheByDate(Date date) throws SQLException {
		LinkedList<Besuch> lBesuch = new LinkedList<Besuch>();
		ResultSet res = executeQuery("select * from besuch where besuch_beginn = ?", new Object[] {(Object) date});
		
			while (res.next()) {
				int id = res.getInt("besuch_id");
				Adresse adresse = getAdresseById(res.getInt("adresse_id"));
				java.sql.Timestamp timestamp = res.getTimestamp("besuch_timestamp");
				java.sql.Timestamp startDate = res.getTimestamp("besuch_beginn");
				java.sql.Timestamp endDate = res.getTimestamp("besuch_ende");
				String name = res.getString("besuch_name");
				Benutzer autor = getBenutzerById(res.getString("besuch_autor"));
				Status status = getStatusById(res.getInt("status_id"));
				LinkedList<Benutzer> lBenutzer = getBenutzerByBesuchId(id);
				Ansprechpartner ansprechpartner = getAnsprechpartnerById(res.getInt("ansprechpartner_id"));
				lBesuch.add(new Besuch(id, name, startDate, endDate, adresse, status, ansprechpartner, lBenutzer, timestamp, autor));
			}
			res.close();
		return lBesuch;

	}
	public LinkedList<Besuch> getBesuchByBenutzer(Benutzer benutzer) throws SQLException{
		LinkedList<Besuch> lBesuch = new LinkedList<Besuch>();
		ResultSet res = executeQuery("select * from benutzer_besuch where benutzer_id = ?", new Object[] {(Object) benutzer.getId()});
		
			while (res.next()) {
				int besuch_id = res.getInt("besuch_id");
				Besuch besuch = getBesuchById(besuch_id);
				lBesuch.add(besuch);
			}
			res.close();
		return lBesuch;
	}
	public Besuch getBesuchById(int pId) throws SQLException {
		ResultSet res = executeQuery("Select * from besuch where besuch_id = ?", new Object[] {new Integer(pId)});
		Besuch besuch = null;
		
			while (res.next()) {
				int id = res.getInt("besuch_id");
				Adresse adresse = getAdresseById(res.getInt("adresse_id"));
				Date timestamp = res.getDate("besuch_timestamp");
				java.sql.Timestamp startDate = res.getTimestamp("besuch_beginn");
				java.sql.Timestamp endDate = res.getTimestamp("besuch_ende");
				String name = res.getString("besuch_name");
				Benutzer autor = getBenutzerById(res.getString("besuch_autor"));
				Status status = getStatusById(res.getInt("status_id"));
				LinkedList<Benutzer> lBenutzer = getBenutzerByBesuchId(id);
				Ansprechpartner ansprechpartner = getAnsprechpartnerById(res.getInt("ansprechpartner_id"));
				besuch = new Besuch(id, name, startDate, endDate, adresse, status, ansprechpartner, lBenutzer, timestamp, autor);
			}
		
			res.close();
		return besuch;
	}
	public Besuch createBesuch(Besuch besuch) throws SQLException {
		int i = executeInsert(besuch);
		return getBesuchById(i);
	}
	public boolean deleteBesuch(Besuch besuch) throws SQLException{
		int i = executeDelete(besuch);
		if(i==1)return true;
		return false;
	}
	public Besuch changeBesuch(Besuch neuBesuch, Besuch altBesuch) throws SQLException {
		int i = executeUpdate(
				"UPDATE `besuch` SET `adresse_id` = ?, `besuch_beginn` = ?, `besuch_ende` = ?, `besuch_name` = ?, `besuch_autor` = ?, `status_id` = ?, `ansprechpartner_id` = ? WHERE `besuch`.`besuch_id` = ? ",
				new Object[] { neuBesuch.getAdresse().getId(), neuBesuch.getStartDate(), neuBesuch.getEndDate(), neuBesuch.getName(), neuBesuch.getAutor().getId(), neuBesuch.getStatus().getId(), neuBesuch.getAnsprechpartner().getId(), altBesuch.getId()});
		
		
		PreparedStatement ps1 = con.prepareStatement("DELETE FROM benutzer_besuch WHERE besuch_id = ?");
		ps1.setInt(1, altBesuch.getId());
		ps1.executeUpdate();
		ps1.close();
		LinkedList<Benutzer> lBenutzer = neuBesuch.getBesucher();
		for(Benutzer e : lBenutzer){
			PreparedStatement ps2 = con.prepareStatement("INSERT INTO `benutzer_besuch` (`benutzer_besuch_id`, `benutzer_id`, `besuch_id`) VALUES (NULL, ?, ?)");
			ps2.setString(1, e.getId());
			ps2.setInt(2, altBesuch.getId());
			ps2.executeUpdate();
			ps2.close();
		}

		return getBesuchById(altBesuch.getId());
	}
	
	// Gespraechsnotiz
	public Gespraechsnotiz getGespraechsnotizByBesuch(Besuch pBesuch) throws SQLException {
		ResultSet res = executeQuery("Select * from gespraechsnotiz where besuch_id = ?",
				new Object[] { new Integer(pBesuch.getId()) });
		Gespraechsnotiz gespraechsnotiz = null;
		try{
			while (res.next()) {
				int id = res.getInt("gespraechsnotiz_id");
				File notizfile = new File("notiz");
				FileOutputStream output1 = new FileOutputStream(notizfile);
				
				InputStream input1 = res.getBinaryStream("gespraechsnotiz_notiz");
				File notiz = new File("C:/Users/CCM/Desktop/" + pBesuch.getId() + "NOTIZ");
				byte[] nb = new byte[1024];
				while (input1.read(nb) > 0) {
					output1.write(nb);
				}
				output1.close();
				
				File file = new File("bild");
				FileOutputStream output2 = new FileOutputStream(file);

				InputStream input2 = res.getBinaryStream("gespraechsnotiz_bild");
				File bild = new File("C:/Users/CCM/Desktop/" + pBesuch.getId() + "BILD");
				byte[] bb = new byte[1024];
				while (input2.read(bb) > 0) {
					output2.write(bb);
				}
				output2.close();
				Unternehmen unternehmen = getUnternehmenById(res.getInt("unternehmen_id"));
				Besuch besuch = getBesuchById(res.getInt("besuch_id"));
				Date timestamp = res.getDate("gespraechsnotiz_timestamp");
				gespraechsnotiz = new Gespraechsnotiz(id, notiz, bild, unternehmen, besuch, timestamp);
			}
		} catch (IOException e) {
			
		}
		
			res.close();
		
			
		
		return gespraechsnotiz;
	}
	public Gespraechsnotiz createGespraechsnotiz(Gespraechsnotiz gespraechsnotiz) throws SQLException {
		int i = executeInsert(gespraechsnotiz);
		return getGespraechsnotizById(i);

	}
	public Gespraechsnotiz getGespraechsnotizById(int pId) throws SQLException{
		Gespraechsnotiz gespraechsnotiz = null;
		ResultSet res = executeQuery("SELECT * FROM `gespraechsnotizen` WHERE `gespraechsnotiz_id` = ?", new Object[]{(Object) new Integer(pId)});
		try{
			while (res.next()) {
				
				int id = res.getInt("gespraechsnotiz_id");
				
				File notizfile = new File("C:/Users/CCM/Desktop/" + "NOTIZ1" + ".txt");
				notizfile.createNewFile();
				FileOutputStream output1 = new FileOutputStream(notizfile);
				
				InputStream input1 = res.getBinaryStream("gespraechsnotiz_notiz");
				File notiz = new File("C:/Users/CCM/Desktop/" + "NOTIZ2");
				byte[] nb = new byte[1024];
				while (input1.read(nb) > 0) {
					output1.write(nb);
				}
				output1.close();
				
				File bildfile = new File("C:/Users/CCM/Desktop/" + "BILD1" + ".txt");
				bildfile.createNewFile();
				FileOutputStream output2 = new FileOutputStream(bildfile);

				InputStream input2 = res.getBinaryStream("gespraechsnotiz_bild");
				File bild = new File("C:/Users/CCM/Desktop/" + "BILD2");
				byte[] bb = new byte[1024];
				while (input2.read(bb) > 0) {
					output2.write(bb);
				}
				output2.close();
				Unternehmen unternehmen = getUnternehmenById(res.getInt("unternehmen_id"));
				Besuch besuch = getBesuchById(res.getInt("besuch_id"));
				Date timestamp = res.getDate("gespraechsnotiz_timestamp");
				gespraechsnotiz = new Gespraechsnotiz(id, notiz, bild, unternehmen, besuch, timestamp);
			}
		} catch (IOException e) {
			
		}
		
	
		res.close();
	return gespraechsnotiz;
	}
	public Gespraechsnotiz changeGespraechsnotiz(Gespraechsnotiz altGespraechsnotiz, Gespraechsnotiz neuGespraechsnotiz) throws SQLException {
		deleteGespreachsnotiz(altGespraechsnotiz);
		return createGespraechsnotiz(neuGespraechsnotiz);

	}
	public boolean deleteGespreachsnotiz(Gespraechsnotiz gespreachsnotiz) throws SQLException{
		int i = executeDelete(gespreachsnotiz);
		if(i==1)return true;
		return false;
	}
	
	// Rolle
	public LinkedList<Rolle> getAllRolle() throws SQLException{
		Rolle rolle1 = null;
		Rolle rolle2 = null;
		ResultSet res = null;
		LinkedList<Rolle> lRolle = new LinkedList<Rolle>();
		LinkedList<Berechtigung> lBerechtigung = new LinkedList<Berechtigung>();
		
			res = executeQuery("select * from rolle", new Object[]{});
			while (res.next()) {
				rolle1 = new Rolle(res.getInt(1), res.getString(2), lBerechtigung);
				lBerechtigung = getBerechtigungByRolle(rolle1);
				rolle2 = new Rolle(rolle1.getId(), rolle1.getBezeichnung(), lBerechtigung);
				lRolle.add(rolle2);
			}
		
			res.close();
		return lRolle;
	}
	public Rolle getRolleById(int id) throws SQLException{
		Rolle rolle1 = null;
		Rolle rolle2 = null;
		ResultSet res = null;
		LinkedList<Berechtigung> lBerechtigung = new LinkedList<Berechtigung>();
		
			res = executeQuery("select * from rolle where rolle_id = ?", new Object[]{(Object) new Integer(id)});
			while (res.next()) {
				rolle1 = new Rolle(res.getInt(1), res.getString(2), lBerechtigung);
				lBerechtigung = getBerechtigungByRolle(rolle1);
				rolle2 = new Rolle(rolle1.getId(), rolle1.getBezeichnung(), lBerechtigung);
			}
		
			res.close();
		return rolle2;
	}
	public Rolle getRolleByBezeichnung(String rolle_Bezeichnung) throws SQLException{
		ResultSet res = executeQuery("select * from `rolle` where rolle_bezeichnung = ?", new Object[] {(Object) rolle_Bezeichnung});
		Rolle rolle1 = null;
		Rolle rolle2 = null;
		LinkedList<Berechtigung> lBerechtigung = new LinkedList<Berechtigung>();
		
			while (res.next()) {
				rolle1 = new Rolle(res.getInt(1), res.getString(2), lBerechtigung);
				lBerechtigung = getBerechtigungByRolle(rolle1);
				rolle2 = new Rolle(rolle1.getId(), rolle1.getBezeichnung(), lBerechtigung);
			}
			res.close();
		return rolle2;
	}
	public Rolle createRolle(Rolle rolle) throws SQLException {
		int i = executeInsert(rolle);
		return getRolleById(i);
	}
	public Rolle changeRolle(Rolle altRolle, Rolle neuRolle) throws SQLException {
		int i = executeUpdate(
				"UPDATE `rolle` `rolle_bezeichnung` = ? WHERE `rolle`.`rolle_id` = ? ",
				new Object[] { neuRolle.getBezeichnung(), altRolle.getId()});
			
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
		return getRolleById(altRolle.getId());
	}
	
	public boolean deleteRolle(Rolle rolle) throws SQLException{
		int i = executeDelete(rolle);
		if(i==1)return true;
		return false;
	}
	
	// Status
	public LinkedList<Status> getAllStatus() throws SQLException{
		Status status = null;
		LinkedList<Status> lStatus = new LinkedList<Status>();
		ResultSet res = null;
			res = executeQuery("select * from status", new Object[]{});
			while (res.next()) {
				int id = res.getInt("status_id");
				String bezeichnung = res.getString("status_bezeichnung");
				status = new Status(id, bezeichnung);
				lStatus.add(status);
			}
			res.close();
		return lStatus;
	}
	public Status getStatusById(int pId) throws SQLException {
		Status status = null;
		ResultSet res = null;
			res = executeQuery("select * from status where status_id = ?", new Object[]{(Object) new Integer(pId)});
			while (res.next()) {
				int id = res.getInt("status_id");
				String bezeichnung = res.getString("status_bezeichnung");
				status = new Status(id, bezeichnung);
			}
			res.close();
		return status;
	}
	public Status createStatus(Status status) throws SQLException {
		int i = executeInsert(status);
		return getStatusById(i);

	}
	public Status changeStatus(Status altStatus, Status neuStatus) throws SQLException {
		int i = executeUpdate(
				"UPDATE `status` SET`status_bezeichnung` = ? WHERE `status`.`status_id` = ? ",
				new Object[] {neuStatus.getBezeichnung(), altStatus.getId()});
		return getStatusById(altStatus.getId());
	}
	public boolean deleteStatus(Status status) throws SQLException{
		int i = executeDelete(status);
		if(i==1)return true;
		return false;
	}
	
	// Studiengang
	public LinkedList<Studiengang> getAllStudiengang() throws SQLException{
		LinkedList<Studiengang> lStudiengang = new LinkedList<Studiengang>();
		Studiengang studiengang = null;
		ResultSet res = null;
		
			res = executeQuery("SELECT * FROM studiengang",
					new Object[] {  });
			while (res.next()) {
				int id = res.getInt("studiengang_id");
				String name = res.getString("studiengang_bezeichnung");
				studiengang = new Studiengang(id, name);
				lStudiengang.add(studiengang);
			}
			res.close();
		return lStudiengang;
	}
	public Studiengang getStudiengangById(int pId) throws SQLException {
		Studiengang studiengang = null;
		ResultSet res = null;
		
			res = executeQuery("SELECT * FROM studiengang WHERE studiengang_id = ?",
					new Object[] { (Object) new Integer(pId) });
			while (res.next()) {
				int id = res.getInt("studiengang_id");
				String name = res.getString("studiengang_bezeichnung");
				studiengang = new Studiengang(id, name);
			}
			res.close();
		return studiengang;
	}
	public Studiengang getStudiengangByBezeichnung(String pBezeichnung) throws SQLException {
		Studiengang studiengang = null;
		ResultSet res = null;
			res = executeQuery("SELECT * FROM studiengang WHERE studiengang_bezeichnung = ?",
					new Object[] { (Object) pBezeichnung });
			while (res.next()) {
				int id = res.getInt("studiengang_id");
				String name = res.getString("studiengang_bezeichnung");
				studiengang = new Studiengang(id, name);
			}
			res.close();
		return studiengang;
	}
	public LinkedList<Studiengang> getStudiengangByBenutzer(Benutzer benutzer) throws SQLException{
	LinkedList<Studiengang> lStudiengang = new LinkedList<Studiengang>();
	ResultSet res = executeQuery("SELECT * FROM studiengang_benutzer WHERE benutzer_id = ?", new Object[]{(Object) benutzer.getId()});
	
			while(res.next()){
				int id = res.getInt("studiengang_id");
				lStudiengang.add(getStudiengangById(id));
			}
			res.close();
		return lStudiengang;
	}
	public LinkedList<Studiengang> getStudiengangByAnsprechpartner(Ansprechpartner ansprechpartner) throws SQLException{
		LinkedList<Studiengang> lStudiengang = new LinkedList<Studiengang>();
		ResultSet res = executeQuery("SELECT * FROM studiengang_ansprechpartner WHERE ansprechpartner_id = ?", new Object[]{(Object) ansprechpartner.getId()});
		
				while(res.next()){
					int id = res.getInt("studiengang_id");
					lStudiengang.add(getStudiengangById(id));
				}
				res.close();
			return lStudiengang;
		}
	public Studiengang createStudiengang(Studiengang studiengang) throws SQLException{
		int i = executeInsert(studiengang);
		return getStudiengangById(i);

	}
	
	// Unternehmen 
	public LinkedList<Unternehmen> getAllUnternehmen() throws SQLException{
		LinkedList<Unternehmen> lUnternehmen = new LinkedList<Unternehmen>();
		Unternehmen unternehmen = null;
		ResultSet res = null;
		
			res = executeQuery("select * from unternehmen", new Object[]{});
			while (res.next()) {
				int id = res.getInt("unternehmen_id");
				String name = res.getString("unternehmen_name");
				String kennzeichen = res.getString("unternehmen_abc_kennzeichen");
				//LinkedList<Ansprechpartner> lAnsprechpartner = new LinkedList<Ansprechpartner>();
				//LinkedList<Adresse> lAdresse = new LinkedList<Adresse>();
				unternehmen = new Unternehmen(id, name, kennzeichen);
				//lAnsprechpartner = getAnsprechpartnerByUnternehmen(unternehmen); 
				//lAdresse = getAdresseByUnternehmen(unternehmen);
				unternehmen = new Unternehmen(id, name, kennzeichen);
				lUnternehmen.add(unternehmen);
			}
			res.close();
		return lUnternehmen;
	}
	public Unternehmen getUnternehmenById(int pId) throws SQLException {
		Unternehmen unternehmen = null;
		ResultSet res = null;
		
			res = executeQuery("select * from unternehmen where unternehmen_id = ?", new Object[]{(Object) new Integer(pId)});
			while (res.next()) {
				int id = res.getInt("unternehmen_id");
				String name = res.getString("unternehmen_name");
				String kennzeichen = res.getString("unternehmen_abc_kennzeichen");
				//LinkedList<Ansprechpartner> lAnsprechpartner = new LinkedList<Ansprechpartner>();
				//LinkedList<Adresse> lAdresse = new LinkedList<Adresse>();
				unternehmen = new Unternehmen(id, name,  kennzeichen);
				//lAnsprechpartner = getAnsprechpartnerByUnternehmen(unternehmen); 
				//lAdresse = getAdresseByUnternehmen(unternehmen);
				unternehmen = new Unternehmen(id, name,  kennzeichen);
			}
			res.close();
		return unternehmen;
	}
	public Unternehmen getUnternehmenByName(String pName) throws SQLException {
		Unternehmen unternehmen = null;
		ResultSet res = null;
		
			res = executeQuery("select * from unternehmen where unternehmen_name = ?", new Object[]{(Object) pName});
			while (res.next()) {
				int id = res.getInt("unternehmen_id");
				String name = res.getString("unternehmen_name");
				String kennzeichen = res.getString("unternehmen_abc_kennzeichen");
				//LinkedList<Ansprechpartner> lAnsprechpartner = new LinkedList<Ansprechpartner>();
				//LinkedList<Adresse> lAdresse = new LinkedList<Adresse>();
				unternehmen = new Unternehmen(id, name, kennzeichen);
				//lAnsprechpartner = getAnsprechpartnerByUnternehmen(unternehmen);
				//lAdresse = getAdresseByUnternehmen(unternehmen);
			}
			res.close();

		return unternehmen;

	}
	//By Robin Bahr 22.01.2017 22:30 Uhr
//	public Unternehmen getUnternehmenByAdresse(Adresse adresse) throws SQLException{
//		Unternehmen unternehmen = null;
//		ResultSet res = null;
//		res = executeQuery("SELECT * FROM `unternehmen`, `ansprechpartner`, `adresse` WHERE ansprechpartner.ansprechpartner_unternehmen_id = unternehmen.unternehmen_id AND adresse.adresse_id = ansprechpartner.adresse_id AND adresse.adresse_id = ?", new Object[]{(Object) new Integer(adresse.getId())});
//				while (res.next()) {
//					int id = res.getInt("unternehmen_id");
//					String name = res.getString("unternehmen_name");
//					String kennzeichen = res.getString("unternehmen_abc_kennzeichen");
////					LinkedList<Ansprechpartner> lAnsprechpartner = new LinkedList<Ansprechpartner>();
//					//LinkedList<Adresse> lAdresse = new LinkedList<Adresse>();
//					unternehmen = new Unternehmen(id, name, kennzeichen);
////					lAnsprechpartner = getAnsprechpartnerByUnternehmen(unternehmen);
//					//lAdresse = getAdresseByUnternehmen(unternehmen);
//				}
//				res.close();
//
//			return unternehmen;
//	}
	public Unternehmen createUnternehmen(Unternehmen unternehmen) throws SQLException {
		int i = executeInsert(unternehmen);
		return getUnternehmenById(i);

	}
	public Unternehmen changeUnternehmen(Unternehmen altUnternehmen, Unternehmen neuUnternehmen) throws SQLException {
		int i = executeUpdate(
				"UPDATE `unternehmen` SET `unternehmen_name` = ?, `unternehmen_abc_kennzeichen` = ?  WHERE `unternehmen`.`unternehmen_id` = ? ",
				new Object[] { neuUnternehmen.getName(), neuUnternehmen.getKennzeichen(), altUnternehmen.getId()});
		return getUnternehmenById(altUnternehmen.getId());
	}
	public boolean deleteUnternehmen(Unternehmen unternehmen) throws SQLException{
		int i = executeDelete(unternehmen);
		if(i==1)return true;
		return false;
	}
	
	//Password
	public boolean createPassword(String hash, Benutzer benutzer) throws SQLException{
		 PreparedStatement ps = con.prepareStatement("INSERT INTO `password` (`password_hash`, `benutzer_id`, `password_id`) VALUES (?, ?, NULL)");
		 ps.setString(1, hash);
		 ps.setString(2, benutzer.getId());
		 int result = ps.executeUpdate();ps.close();
		 if(result == 1)return true;
		 return false;
	}
	public boolean checkPassword(String hash, Benutzer benutzer) throws SQLException{
		ResultSet res = null;
		Object oldHash = null;
			res = executeQuery("SELECT * FROM password WHERE password.benutzer_id = ?",
					new Object[] { (Object) benutzer.getId() });
			while (res.next()) {
				oldHash = res.getString("password_hash");
			}
			res.close();
		
		return (oldHash.equals(hash));
	}
	public boolean changePassword(String hash, Benutzer benutzer) throws SQLException{
		int i = executeUpdate(
				"UPDATE `password` SET `password_hash` = ? WHERE `password`.`benutzer_id` = ? ",
				new Object[] {(Object) hash, (Object)benutzer.getId()});
		if (i != 0) {
			return true;
		}
		return false;
	
	}
}
