package com.dhbwProject.backend;

import java.sql.Connection;
import java.sql.DriverManager;
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
	};

	public LinkedList<Benutzer> getAllBenutzer() throws SQLException {
		LinkedList<Benutzer> benArr = new LinkedList<Benutzer>();
		stat = con.createStatement();
		res = stat.executeQuery("select * from benutzer");
		while (res.next()) {
			String s1 = res.getString(3);
			String s2 = res.getString(1);
			String s3 = res.getString(2);
			String s4 = res.getString(4);
			String s5 = res.getString(5);
			Benutzer ben = new Benutzer(s1, s2, s3, getBeruf(s4), getRolle(s5));
			benArr.add(ben);
		}
		return benArr;

	}

	private String getBeruf(String beruf_id) throws SQLException {
		stat = con.createStatement();
		res = stat.executeQuery("select * from beruf where beruf_id = " + beruf_id);
		String beruf = null;
		while (res.next()) {
			beruf = res.getString(2);
		}
		return beruf;
	}

	private String getRolle(String rolle_id) throws SQLException {
		stat = con.createStatement();
		res = stat.executeQuery("select * from rolle where rolle_id = " + rolle_id);
		String rolle = null;
		while (res.next()) {
			rolle = res.getString(2);
		}
		return rolle;
	}
}




