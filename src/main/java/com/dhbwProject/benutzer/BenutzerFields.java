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
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
import com.vaadin.server.Sizeable;
import com.vaadin.server.UserError;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class BenutzerFields extends VerticalLayout{
	private static final long serialVersionUID = 1L;
	
	private TextField tfID;
	private TextField tfVorname;
	private TextField tfNachname;
	private TextField tfTelefonnummer;
	private TextField tfEmail;
	private ComboBox cbBeruf;
	private ComboBox cbRolle;
	private CheckBox chPassword;
	private ListSelect lsStudiengang;
	private dbConnect dbConnect;
	private LinkedList<Beruf> alleBerufe;
	private LinkedList<Rolle> alleRollen;	
	private TextArea taStudiengang;
	private Button btnLookupStudiengang;
	private LinkedList<Studiengang> alleStudiengaenge = new LinkedList<Studiengang>();

	
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
		this.initTelefonnummer();
		this.intitEmail();
		Responsive.makeResponsive(this);
		
		
	}
	
	private void initID() {
		this.tfID = new TextField("Anmeldename");
	//	this.tfID.setWidth("300px");
		this.addComponent(tfID);
		this.tfID.setStyleName("tffield");
		Responsive.makeResponsive(tfID);
	}
	
	private void initVorname() {
		this.tfVorname = new TextField("Vorname");
	//	this.tfVorname.setWidth("300px");
		this.addComponent(tfVorname);
		this.tfVorname.setStyleName("tffield");
		Responsive.makeResponsive(tfVorname);
		
	}
	
	private void initNachname() {
		this.tfNachname = new TextField("Nachname");
	//	this.tfNachname.setWidth("300px");
		this.addComponent(tfNachname);
		this.tfNachname.setStyleName("tffield");
		Responsive.makeResponsive(tfNachname);
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
	//	this.cbBeruf.setWidth("300px");
		Responsive.makeResponsive(cbBeruf);
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
	//	this.cbRolle.setWidth("300px");
		Responsive.makeResponsive(cbRolle);
		this.addComponent(cbRolle);
	}
	
	
//	Bosse
	protected void initChPassword(){
		this.chPassword = new CheckBox("Passwort zurücksetzen?");
		this.chPassword.setValue(false);
	//	this.chPassword.setWidth("300px");
		this.addComponent(chPassword);
		Responsive.makeResponsive(chPassword);
	}
	
	private void initLsStudiengang() {
		HorizontalLayout hl = new HorizontalLayout();
		this.taStudiengang = new TextArea("Studiengang");
	//	this.taStudiengang.setWidth("300px");
		taStudiengang.setStyleName("tffield");
		this.taStudiengang.setReadOnly(true);
		Responsive.makeResponsive(taStudiengang);

		this.btnLookupStudiengang = new Button();
		this.btnLookupStudiengang.setIcon(FontAwesome.REPLY);
		this.btnLookupStudiengang.setWidth("50px");
		this.btnLookupStudiengang.addClickListener(listener -> {
			this.alleStudiengaenge.clear();
			LookupStudiengang lookup = new LookupStudiengang();
			lookup.addCloseListener(CloseListener -> {
				alleStudiengaenge = lookup.getLSelection();
				this.setStudieng(lookup.getLSelection());
			});
			this.getUI().addWindow(lookup);
		});

		hl.setSizeUndefined();
		hl.setSpacing(true);
		hl.addComponent(this.taStudiengang);
		hl.addComponent(this.btnLookupStudiengang);
		Responsive.makeResponsive(hl);
		this.addComponent(hl);
	}
	
	private void initTelefonnummer() {
		this.tfTelefonnummer = new TextField("Telefonnummer");
	//	this.tfTelefonnummer.setWidth("300px");
		this.addComponent(tfTelefonnummer);
		this.tfTelefonnummer.setStyleName("tffield");
		Responsive.makeResponsive(tfTelefonnummer);
	}
	
	private void intitEmail() {
		this.tfEmail = new TextField("E-Mail");
	//	this.tfEmail.setWidth("300px");
		this.addComponent(tfEmail);
		this.tfEmail.setStyleName("tffield");
		Responsive.makeResponsive(tfEmail);
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
	
//	Bosse
	public void setPassword (boolean b){
		this.chPassword.setValue(b);
	}
	
	public boolean getPassword(){
		return this.chPassword.getValue();
	}
	
	
	public void setStudiengang (Benutzer b){
		this.alleStudiengaenge = b.getStudiengang(); // By Robin 08.02.2017 00:33 Uhr
		this.taStudiengang.clear();
		String value = "";
		for (Studiengang st : b.getStudiengang()) {
			value = value + st.getBezeichnung() + "\n";
			this.taStudiengang.setReadOnly(false);
			this.taStudiengang.setValue(value);
			this.taStudiengang.setReadOnly(true);
		}
	}
	
	public LinkedList<String> getStudiengang (){
		LinkedList<String> stg = new LinkedList<String>();
		for (Studiengang o : alleStudiengaenge) {
			stg.add(o.getBezeichnung());
		}
		return stg;
	}
	
	public void setTelefonnummer (Benutzer b){
		this.tfTelefonnummer.setValue(b.getTelefon());
	}
	
	public String getTelefonnummer (){
		return this.tfTelefonnummer.getValue();
	}
	
	public void setEmail (Benutzer b){
		this.tfEmail.setValue(b.getEmail());
	}
	
	public String getEmail (){
		return this.tfEmail.getValue();
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
	
	public TextArea getTaStudiengang() {
		return this.taStudiengang;
	}
	
	public TextField getTfTelefonnummer() {
		return this.tfTelefonnummer;
	}
	
	public TextField getTfEmail() {
		return this.tfEmail;
	}

//	Bosse
	public CheckBox getChPassword(){
		return this.chPassword;
	}
	
	
//	public void enableFields(boolean bool) {
//		this.tfID.setEnabled(false);
//		this.tfVorname.setEnabled(bool);
//		this.tfNachname.setEnabled(bool);
//		this.cbBeruf.setEnabled(bool);
//		this.cbRolle.setEnabled(bool);
//		this.lsStudiengang.setEnabled(bool);
//		this.tfTelefonnummer.setEnabled(bool);
//		this.tfEmail.setEnabled(bool);
//		this.chPassword.setEnabled(bool);
//	}

	protected void setStudieng(LinkedList<Studiengang> studiengaenge) {
		String value = "";
		for (Studiengang stg : studiengaenge)
			value = value + stg.getBezeichnung() + "\n";
		this.taStudiengang.setReadOnly(false);
		this.taStudiengang.setValue(value);
		this.taStudiengang.setReadOnly(true);
	}
	
	protected boolean checkFields(BenutzerFields fields) {
		
		if (fields.getID().trim().equals("")) {
			tfID.setComponentError(new UserError("ID eingeben"));
		} else {
			tfID.setComponentError(null);
		}
		
		if (fields.getVorname().trim().equals("")) {
			tfVorname.setComponentError(new UserError("Vorname eingeben"));
		} else {
			tfVorname.setComponentError(null);
		}
			
		if (fields.getNachname().trim().equals("")) {
			tfNachname.setComponentError(new UserError("Nachname eingeben"));
		} else {
			tfNachname.setComponentError(null);
		}
		
		if (fields.getBeruf() == null) {
			cbBeruf.setComponentError(new UserError("Beruf auswählen"));
		} else {
			cbBeruf.setComponentError(null);
		}
		
		if (fields.getRolle() == null) {
			cbRolle.setComponentError(new UserError("Rolle auswählen"));
		} else {
			cbRolle.setComponentError(null);
		}
		
		if (fields.getStudiengang().size() < 1) {
			taStudiengang.setComponentError(new UserError("Studiengang auswählen"));
		} else {
			taStudiengang.setComponentError(null);
		}
		
		if (fields.getTelefonnummer().trim().equals("")) {
			tfTelefonnummer.setComponentError(new UserError("Telefonnummer eingeben"));
		} else {
			tfTelefonnummer.setComponentError(null);
		}
		
		if (fields.getEmail().trim().equals("")) {
			tfEmail.setComponentError(new UserError("E-Mail eingeben"));
		} else {
			tfEmail.setComponentError(null);
		}
		
		
		if (!fields.getID().equals("") && !fields.getVorname().equals("") && !fields.getNachname().equals("")
				&& fields.getBeruf() != null && fields.getRolle() != null && fields.getStudiengang().size() > 0
				&& !fields.getTelefonnummer().equals("") && !fields.getEmail().equals("")) {
			return true;
		} else {
			return false;
		}
		
		
		}

}
