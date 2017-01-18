package com.dhbwProject.benutzer;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Set;

import com.dhbwProject.backend.CCM_Constants;
import com.dhbwProject.backend.dbConnect;
import com.dhbwProject.backend.beans.Benutzer;
import com.dhbwProject.backend.beans.Beruf;
import com.dhbwProject.backend.beans.Rolle;
import com.dhbwProject.backend.beans.Studiengang;
import com.vaadin.data.Item;
import com.vaadin.server.Sizeable;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class BenutzerFields extends VerticalLayout{
	private static final long serialVersionUID = 1L;
	
	private TextField tfID;
	private TextField tfVorname;
	private TextField tfNachname;
	private ComboBox cbBeruf;
	private ComboBox cbRolle;
	private ListSelect lsStudiengang;
	private dbConnect dbConnect;
	private LinkedList<Beruf> alleBerufe;
	private LinkedList<Rolle> alleRollen;
	private LinkedList<Studiengang> alleStudiengaenge;
	
	public BenutzerFields() {
		this.setSizeUndefined();
		this.setSpacing(true);
		this.dbConnect = (dbConnect) VaadinSession.getCurrent().getSession().getAttribute(CCM_Constants.SESSION_VALUE_CONNECTION);
		this.initID();
		this.initVorname();
		this.initNachname();
		this.initCbBerufe();
		this.initCbRolle();
		this.initLsStudiengang();
		
	}
	
	private void initID() {
		this.tfID = new TextField();
		this.tfID.setInputPrompt("ID");
		this.tfID.setWidth("300px");
		this.addComponent(tfID);
	}
	
	private void initVorname() {
		this.tfVorname = new TextField();
		this.tfVorname.setInputPrompt("Vorname");
		this.tfVorname.setWidth("300px");
		this.addComponent(tfVorname);
		
	}
	
	private void initNachname() {
		this.tfNachname = new TextField();
		this.tfNachname.setInputPrompt("Nachname");
		this.tfNachname.setWidth("300px");
		this.addComponent(tfNachname);
	}
	
	private void initCbBerufe() {
		this.cbBeruf = new ComboBox();
		this.cbBeruf.setInputPrompt("Beruf");
		try {
			alleBerufe = dbConnect.getAllBeruf();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (Beruf beruf : alleBerufe) {
			this.cbBeruf.addItem(beruf.getBezeichnung());
		}
		this.cbBeruf.setNullSelectionAllowed(false);
		this.cbBeruf.setTextInputAllowed(false);
		this.cbBeruf.setWidth("300px");
		this.addComponent(cbBeruf);
	}
	
	private void initCbRolle() {
		this.cbRolle = new ComboBox();
		this.cbRolle.setInputPrompt("Rolle");
		try {
			alleRollen = dbConnect.getAllRolle();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(Rolle rolle : alleRollen) {
			this.cbRolle.addItem(rolle.getBezeichnung());
		}
		this.cbRolle.setNullSelectionAllowed(false);
		this.cbRolle.setTextInputAllowed(false);
		this.cbRolle.setWidth("300px");
		this.addComponent(cbRolle);
	}
	
	private void initLsStudiengang() {
		this.lsStudiengang = new ListSelect();
		this.lsStudiengang.setMultiSelect(true);
		this.lsStudiengang.setCaption("Studiengang");
		try {
			alleStudiengaenge = dbConnect.getAllStudiengang();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (Studiengang stg : alleStudiengaenge) {
			lsStudiengang.addItem(stg.getBezeichnung());
		}
		this.lsStudiengang.setRows(lsStudiengang.size());
		this.lsStudiengang.setWidth("300px");
		this.addComponent(lsStudiengang);
	}
	
	public void setID (Benutzer b){
		this.tfID.setValue(b.getId());
	}
	
	public String getID (){
		return this.tfID.getValue();
	}
	
	public void setVorname (Benutzer b){
		this.tfVorname.setValue(b.getVorname());
	}
	
	public String getVorname (){
		return this.tfVorname.getValue();
	}
	
	public void setNachname (Benutzer b){
		this.tfNachname.setValue(b.getNachname());
	}
	
	public String getNachname (){
		return this.tfNachname.getValue();
	}
	
	public void setBeruf (Benutzer b){
		this.cbBeruf.setValue(b.getBeruf().getBezeichnung());
	}
	
	public String getBeruf (){
		return (String) this.cbBeruf.getValue();
	}
	
	public void setRolle (Benutzer b){
		this.cbRolle.setValue(b.getRolle().getBezeichnung());
	}
	
	public String getRolle (){
		return (String) this.cbRolle.getValue();
	}
	
	public void setStudiengang (Benutzer b){
		this.lsStudiengang.setValue(b.getStudiengang().get(0).getBezeichnung());
	}
	
	public LinkedList<String> getStudiengang (){
		Set <Item>values=(Set<Item>) this.lsStudiengang.getValue();
		LinkedList<String> stg = new LinkedList<String>();
		for (Object o : values) {
			stg.add((String) o);
		}
		return stg;
	}
	
	public TextField getTfID() {
		return this.tfID;
	}
	
	public TextField getTfVorname() {
		return this.tfVorname;
	}
	
	public TextField getTfNachname() {
		return this.tfNachname;
	}
	
	public ComboBox getCbBeruf() {
		return this.cbBeruf;
	}
	
	public ComboBox getCbRolle() {
		return this.cbRolle;
	}
	
	public ListSelect getLsStudiengang() {
		return this.lsStudiengang;
	}

	public void enableFields(boolean bool) {
		this.tfID.setEnabled(bool);
		this.tfVorname.setEnabled(bool);
		this.tfNachname.setEnabled(bool);
		this.cbBeruf.setEnabled(bool);
		this.cbRolle.setEnabled(bool);
		this.lsStudiengang.setEnabled(bool);
	}
	
	
	

}
