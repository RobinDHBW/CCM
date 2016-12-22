package com.dhbwProject.backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import com.dhbwProject.backend.beans.Benutzer;

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
	
	public void close(){
		try {
			if (res != null) if(!res.isClosed()) res.close();
			if (stat != null) if (!stat.isClosed())stat.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public LinkedList<Benutzer> getAllBenutzer() throws SQLException {
		LinkedList<Benutzer> benArr = new LinkedList<Benutzer>();
		Statement stat = con.createStatement();
		ResultSet res = stat.executeQuery("select * from benutzer");
		while (res.next()) {
			String s1 = res.getString(3);
			String s2 = res.getString(1);
			String s3 = res.getString(2);
			String s4 = res.getString(4);
			String s5 = res.getString(5);
			Benutzer ben = new Benutzer(s1, s2, s3, getBerufById(s4), getRolleById(s5));
			benArr.add(ben);
		}
		res.close();
		stat.close();
		return benArr;

	}

	private String getBerufById(String beruf_id) throws SQLException {
		Statement stat = con.createStatement();
		ResultSet res = stat.executeQuery("select * from beruf where beruf_id = " + beruf_id);
		String beruf = null;
		while (res.next()) {
			beruf = res.getString(2);
		}
		res.close();
		stat.close();
		return beruf;
	}

	private String getRolleById(String rolle_id) throws SQLException {
		Statement stat = con.createStatement();
		ResultSet res = stat.executeQuery("select * from rolle where rolle_id = " + rolle_id);
		String rolle = null;
		while (res.next()) {
			rolle = res.getString(2);
		}
		res.close();
		stat.close();
		return rolle;
	}

	private int getBerufByBezeichnung(String beruf_bezeichnung) throws SQLException {
		Statement stat = con.createStatement();
		ResultSet res = stat.executeQuery("select * from beruf where beruf_bezeichnung = '" + beruf_bezeichnung+"'");
		int beruf=0;
		while (res.next()) {
			beruf = res.getInt(1);
		}
		res.close();
		stat.close();
		return beruf;
	}

	private int getRolleByBezeichnung(String rolle_Bezeichnung) throws SQLException {
		Statement stat = con.createStatement();
		ResultSet res = stat.executeQuery("select * from `rolle` where rolle_bezeichnung = '" + rolle_Bezeichnung +"'");
		int rolle=0;
		while (res.next()) {
			rolle = res.getInt(1);
		}
		res.close();
		stat.close();
		return rolle;
	}

	public Benutzer getBenutzerbyId(String pId) throws SQLException {
		Statement stat = con.createStatement();
		ResultSet res = stat.executeQuery("select * from benutzer where benutzer_id = '" + pId +"'");
		res.next();
		String id = res.getString(3);
		String vorname = res.getString(1);
		String nachname = res.getString(2);
		String beruf_id = res.getString(4);
		String rolle_id = res.getString(5);

		Benutzer ben = new Benutzer(id, vorname, nachname, getBerufById(beruf_id), getRolleById(rolle_id));
		res.close();
		stat.close();
		return ben;
	}

	public boolean setBenutzer(Benutzer b) {
		try{
		if(getBenutzerbyId(b.getId()).equals(b)){
			return false;
		}
		}catch(Exception e){
		try {
			int beruf = getBerufByBezeichnung(b.getBeruf());
			int rolle = getRolleByBezeichnung(b.getRolle());
			// create our java preparedstatement using a sql update query
		    PreparedStatement ps = con.prepareStatement(
		      "INSERT INTO `benutzer` (`vorname`, `nachname`, `benutzer_id`, `rolle_id`, `beruf_id`) VALUES (?, ?, ?, ?, ?)");
		    // set the preparedstatement parameters
		    ps.setString(1,b.getVorname());
		    ps.setString(2,b.getNachname());
		    ps.setString(3,b.getId());
		    ps.setInt(4, beruf);
		    ps.setInt(5, rolle);

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

	}


