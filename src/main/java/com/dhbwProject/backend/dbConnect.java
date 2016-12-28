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
	
	public ResultSet executeQuery(PreparedStatement ps){
		try {
			ResultSet res = ps.executeQuery();
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
	public ResultSet executeUpdate(PreparedStatement ps){
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
	public ResultSet executeDelete(PreparedStatement ps){
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
	public ResultSet executeInsert(PreparedStatement ps){
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

	public LinkedList<Benutzer> getAllBenutzer() throws SQLException {
		LinkedList<Benutzer> benArr = new LinkedList<Benutzer>();
		PreparedStatement ps = con.prepareStatement("select * from benutzer");
		ResultSet res = executeQuery(ps);
		while (res.next()) {
			String s1 = res.getString(3);
			String s2 = res.getString(1);
			String s3 = res.getString(2);
			int s4 = res.getInt(4);
			int s5 = res.getInt(5);
			Benutzer ben = new Benutzer(s1, s2, s3, getBerufById(s4), getRolleById(s5));
			benArr.add(ben);
		}
		res.close();
		return benArr;
	}

	private Beruf getBerufById(int beruf_id) throws SQLException {
		PreparedStatement ps = con.prepareStatement("select * from beruf where beruf_id = ?");
		ps.setInt(1, beruf_id);
		ResultSet res = executeQuery(ps);
		Beruf beruf = null;
		while (res.next()) {
			beruf = new Beruf(res.getInt(1), res.getString(2));
		}
		res.close();
		stat.close();
		return beruf;
	}

	private Rolle getRolleById(int id) throws SQLException {
		PreparedStatement ps = con.prepareStatement("select * from rolle where rolle_id = ?");
		ps.setInt(1, id);
		Rolle rolle1 = null;
		Rolle rolle2 = null;
		LinkedList<Berechtigung> lBerechtigung = new LinkedList<Berechtigung>();
		while (res.next()) {
			rolle1 = new Rolle(res.getInt(1), res.getString(2), lBerechtigung);
			lBerechtigung = getBerechtigungByRolle(rolle1);
			rolle2 = new Rolle(rolle1.getId(), rolle1.getBezeichnung(), lBerechtigung);
		}
		res.close();
		stat.close();
		return rolle2;
	}

	private LinkedList<Berechtigung> getBerechtigungByRolle(Rolle rolle) {
		try {
			PreparedStatement ps = con.prepareStatement("Select * from berechtigung b, rolle_berechtigung rb where rb.berechtigung_id = b.berechtigung_id and rolle_id = ?");
			ps.setInt(1, rolle.getId());
			executeQuery(ps);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private Beruf getBerufByBezeichnung(String beruf_bezeichnung) throws SQLException {
		PreparedStatement ps = con.prepareStatement("select * from beruf where beruf_bezeichnung = ?");
		ps.setString(1, beruf_bezeichnung);
		ResultSet res = executeQuery(ps);
		Beruf beruf = null;
		while (res.next()) {
			beruf = new Beruf(res.getInt(1), res.getString(2));
		}
		res.close();
		return beruf;
	}

	private Rolle getRolleByBezeichnung(String rolle_Bezeichnung) throws SQLException {
		PreparedStatement ps = con.prepareStatement("select * from `rolle` where rolle_bezeichnung = ?");
		ps.setString(1, rolle_Bezeichnung);
		ResultSet res = executeQuery(ps);
		Rolle rolle1 = null;
		Rolle rolle2 = null;
		LinkedList<Berechtigung> lBerechtigung = new LinkedList<Berechtigung>();
		while (res.next()) {
			rolle1 = new Rolle(res.getInt(1), res.getString(2), lBerechtigung);
			lBerechtigung = getBerechtigungByRolle(rolle1);
			rolle2 = new Rolle(rolle1.getId(), rolle1.getBezeichnung(), lBerechtigung);
		}
		res.close();
		stat.close();
		return rolle2;
	}

	public Benutzer getBenutzerbyId(String pId) throws SQLException {
		Statement stat = con.createStatement();
		ResultSet res = stat.executeQuery("select * from benutzer where benutzer_id = '" + pId + "'");
		res.next();
		String id = res.getString(3);
		String vorname = res.getString(1);
		String nachname = res.getString(2);
		int beruf_id = res.getInt(4);
		int rolle_id = res.getInt(5);

		Benutzer ben = new Benutzer(id, vorname, nachname, getBerufById(beruf_id), getRolleById(rolle_id));
		res.close();
		stat.close();
		return ben;
	}

	public boolean createBenutzer(Benutzer b) {
		try {
			if (getBenutzerbyId(b.getId()).equals(b)) {
				return false;
			}
		} catch (Exception e) {
			try {
				Beruf beruf = getBerufByBezeichnung(b.getBeruf().getBezeichnung());
				Rolle rolle = getRolleByBezeichnung(b.getRolle().getBezeichnung());
				// create our java preparedstatement using a sql update query
				PreparedStatement ps = con.prepareStatement(
						"INSERT INTO `benutzer` (`vorname`, `nachname`, `benutzer_id`, `rolle_id`, `beruf_id`) VALUES (?, ?, ?, ?, ?)");
				// set the preparedstatement parameters
				ps.setString(1, b.getVorname());
				ps.setString(2, b.getNachname());
				ps.setString(3, b.getId());
				ps.setInt(4, beruf.getId());
				ps.setInt(5, rolle.getId());

				// call executeUpdate to execute our sql update statement
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

	// TODO Ansprechpartner
	public Ansprechpartner getAnsprechpartnerByUnternehmen(Unternehmen unternehmen) {
		// TODO ss implement getAnsprechpartnerByUnternehmen
		return null;

	}

	public boolean createAnsprechpartner(Ansprechpartner ansprechpartner) {
		// TODO ss implement createAnsprechpartner
		return false;

	}

	// TODO Besuch
	public LinkedList<Besuch> getBesucheByDate(Date date) {
		// TODO ss implement getBesucheByDate
		return null;

	}

	public boolean createBesuch(Besuch besuch) {
		// TODO ss implement createBesuch
		return false;

	}

	// TODO Gespraechsnotiz
	public Gespraechsnotiz getGespraechsnotizByBesuch(Besuch besuch) {
		// TODO ss implement getGespraechsnotizByBesuch
		return null;

	}

	public boolean createGespraechsnotiz(Gespraechsnotiz gespraechsnotiz) {
		// TODO ss implement createGespreachsnotiz
		return false;

	}

}
