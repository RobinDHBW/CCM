package com.dhbwProject.besuche;

import java.util.Date;
import java.util.LinkedList;

import com.dhbwProject.backend.CCM_Constants;
import com.dhbwProject.backend.beans.Adresse;
import com.dhbwProject.backend.beans.Ansprechpartner;
import com.dhbwProject.backend.beans.Benutzer;
import com.dhbwProject.backend.beans.Status;
import com.dhbwProject.backend.beans.Unternehmen;
import com.dhbwProject.benutzer.LookupBenutzer;
import com.dhbwProject.unternehmen.LookupAnsprechpartner;
import com.dhbwProject.unternehmen.LookupUnternehmen;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.Button;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class BesuchFelder extends VerticalLayout {
	private static final long serialVersionUID = 1L;
	
	private TextField tfTitel;
	
	private TextField tfStatus;
	private Button btnLookupStatus;
	
	private DateField dfDateStart;
	private DateField dfDateEnd;
	
	private TextArea taAdresse;
	private TextField tfUnternehmen;
	private Button btnLookupAdresse;
	
	private TextField tfAnsprechpartner;
	private Button btnLookupAnsprechpartner;
	
	private TextArea taParticipants;
	private Button btnLookupParticipants;

	private Benutzer autor = (Benutzer)VaadinSession.getCurrent().getSession().getAttribute(CCM_Constants.SESSION_VALUE_USER);
	private Adresse adresse;
	private Unternehmen unternehmen;
	private Ansprechpartner ansprechpartner;
	private Status status;
	private LinkedList<Benutzer> lBenutzer = new LinkedList<Benutzer>();

	public BesuchFelder() {
		this.setSizeUndefined();
		this.setSpacing(true);
		this.setMargin(new MarginInfo(true, true, true, true));
		
		this.initFieldTitel();
		this.initFieldStatus();
		this.initFieldStartDate();
		this.initFieldEndDate();
		this.initFieldAdresse();
		this.initFieldAnsprechpartner();
		this.initFieldParticipants();
	}

	protected void initFieldTitel() {
		this.tfTitel = new TextField();
		this.tfTitel.setCaption("Titel");
		this.tfTitel.setWidth("300px");
		this.addComponent(this.tfTitel);
	}
	
	protected void initFieldStatus(){
		this.tfStatus = new TextField();
		this.tfStatus.setWidth("300px");
		this.tfStatus.setReadOnly(true);
		
		this.btnLookupStatus = new Button();
		this.btnLookupStatus.setIcon(FontAwesome.REPLY);
		this.btnLookupStatus.setWidth("50px");
		this.btnLookupStatus.addClickListener(click ->{
			LookupStatus status = new LookupStatus();
			status.addCloseListener(close ->{
				if(status.getSelection() != null)
					this.setStatus(status.getSelection());
			});
			getUI().addWindow(status);
		});
		HorizontalLayout hlStatus = new HorizontalLayout(this.tfStatus, this.btnLookupStatus);
		hlStatus.setSpacing(true);
		hlStatus.setCaption("Status");
		this.addComponent(hlStatus);
		
	}

	protected void initFieldStartDate() {
		this.dfDateStart = new DateField();
		this.dfDateStart.setCaption("Start:");
		this.dfDateStart.setWidth("300px");
		this.dfDateStart.setResolution(Resolution.MINUTE);
		this.addComponent(this.dfDateStart);
	}
	
	protected void initFieldEndDate(){
		this.dfDateEnd = new DateField();
		this.dfDateEnd.setCaption("Ende:");
		this.dfDateEnd.setWidth("300px");
		this.dfDateEnd.setResolution(Resolution.MINUTE);
		this.addComponent(this.dfDateEnd);
	}

	protected void initFieldAdresse() {
		VerticalLayout vlAdresse = new VerticalLayout();
		HorizontalLayout hlUnternehmen = new HorizontalLayout();
		
		this.tfUnternehmen = new TextField();
		this.tfUnternehmen.setWidth("300px");
		this.tfUnternehmen.setReadOnly(true);
		
		this.taAdresse = new TextArea();
		this.taAdresse.setWidth("300px");
		this.taAdresse.setReadOnly(true);

		this.btnLookupAdresse = new Button();
		this.btnLookupAdresse.setIcon(FontAwesome.REPLY);
		this.btnLookupAdresse.setWidth("50px");
		this.btnLookupAdresse.addClickListener(listener -> {			
			LookupUnternehmen lookup = new LookupUnternehmen();
			lookup.addCloseListener(close ->{
				if(lookup.getSelectionUnternehmen() != null)
					this.setUnternehmen(lookup.getSelectionUnternehmen());
				if(lookup.getSelectionAdresse() != null)
					this.setAdresse(lookup.getSelectionAdresse());
			});
			this.getUI().addWindow(lookup);

		});
		hlUnternehmen.setSizeUndefined();
		hlUnternehmen.setSpacing(true);
		hlUnternehmen.addComponent(this.tfUnternehmen);
		hlUnternehmen.addComponent(this.btnLookupAdresse);
		hlUnternehmen.setCaption("Unternehmen:");
		
		vlAdresse.setSizeUndefined();
		vlAdresse.addComponent(hlUnternehmen);
		vlAdresse.addComponent(this.taAdresse);
		
		this.addComponent(vlAdresse);
	}

	protected void initFieldAnsprechpartner() {
		HorizontalLayout hlAnsprechpartner = new HorizontalLayout();
		this.tfAnsprechpartner = new TextField();
		this.tfAnsprechpartner.setWidth("300px");
		this.tfAnsprechpartner.setReadOnly(true);

		this.btnLookupAnsprechpartner = new Button();
		this.btnLookupAnsprechpartner.setIcon(FontAwesome.REPLY);
		this.btnLookupAnsprechpartner.setWidth("50px");
		this.btnLookupAnsprechpartner.addClickListener(listener -> {
			LookupAnsprechpartner lookup = new LookupAnsprechpartner(this.unternehmen);
			lookup.addCloseListener(CloseListener -> {
				if(lookup.getAnsprechpartner() != null){
					this.setAnsprechpartner(lookup.getAnsprechpartner());
				}
			});
			this.getUI().addWindow(lookup);

		});
		hlAnsprechpartner.setSizeUndefined();
		hlAnsprechpartner.setSpacing(true);
		hlAnsprechpartner.addComponent(this.tfAnsprechpartner);
		hlAnsprechpartner.addComponent(this.btnLookupAnsprechpartner);
		hlAnsprechpartner.setCaption("Ansprechpartner:");
		this.addComponent(hlAnsprechpartner);
	}

	protected void initFieldParticipants() {
		HorizontalLayout hlParticipants = new HorizontalLayout();
		this.taParticipants = new TextArea();
		this.taParticipants.setWidth("300px");
		this.taParticipants.setReadOnly(true);

		this.btnLookupParticipants = new Button();
		this.btnLookupParticipants.setIcon(FontAwesome.REPLY);
		this.btnLookupParticipants.setWidth("50px");
		this.btnLookupParticipants.addClickListener(listener -> {
			this.lBenutzer.clear();
			LookupBenutzer lookup = new LookupBenutzer(true);
			lookup.removeAutorFromList();
			lookup.addCloseListener(CloseListener -> {
				this.setTeilnehmenr(lookup.getLSelection());
			});
			this.getUI().addWindow(lookup);
		});

		hlParticipants.setSizeUndefined();
		hlParticipants.setSpacing(true);
		hlParticipants.addComponent(this.taParticipants);
		hlParticipants.addComponent(this.btnLookupParticipants);
		hlParticipants.setCaption("Teilnehmen:");
		this.addComponent(hlParticipants);
	}
	
	protected Benutzer getAutor(){
		return this.autor;
	}
	
	protected void setAutor(Benutzer b){
		if(b != null){
			this.autor = b;
		}
	}
	
	protected String getTitel(){
		return this.tfTitel.getValue();
	}
	
	protected void setTitel(String s){
		this.tfTitel.setValue(s);
	}
	
	protected Status getStatus(){
		return this.status;
	}
	
	protected void setStatus(Status s){
		this.status = s;
		this.tfStatus.setReadOnly(false);
		this.tfStatus.setValue(s.getBezeichnung());
		this.tfStatus.setReadOnly(true);
	}
	
	protected Date getDateStart() {
		return this.dfDateStart.getValue();
	}

	protected void setDateStart(Date d) {
		this.dfDateStart.setValue(d);
	}
	
	protected Date getDateEnd(){
		return this.dfDateEnd.getValue();
	}
	
	protected void setDateEnd(Date d){
		this.dfDateEnd.setValue(d);
	}

	protected Adresse getAdresse() {
		return this.adresse;
	}

	protected void setAdresse(Adresse a) {
		this.adresse = a;
		this.taAdresse.setReadOnly(false);
		this.taAdresse.setValue(a.getStrasse()+"\n"+a.getPlz()+"\n"+a.getOrt());
		this.taAdresse.setReadOnly(true);
	}
	
	protected Unternehmen getUnternehmen(){
		return this.unternehmen;
	}
	
	protected void setUnternehmen(Unternehmen u){
		this.unternehmen = u;
		this.tfUnternehmen.setReadOnly(false);
		this.tfUnternehmen.setValue(u.getName());
		this.tfUnternehmen.setReadOnly(true);
	}

	protected Ansprechpartner getAnsprechpartner() {
		return this.ansprechpartner;
	}

	protected void setAnsprechpartner(Ansprechpartner a) {
		this.ansprechpartner = a;
		this.tfAnsprechpartner.setReadOnly(false);
		this.tfAnsprechpartner.setValue(a.getNachname()+", "+a.getVorname());
		this.tfAnsprechpartner.setReadOnly(true);
	}

	protected LinkedList<Benutzer> getTeilnehmenr() {
		return this.lBenutzer;
	}

	protected void setTeilnehmenr(LinkedList<Benutzer> lBenutzer) {
		this.lBenutzer = lBenutzer;
		this.addTeilnehmer((Benutzer)VaadinSession.getCurrent().getSession().getAttribute(CCM_Constants.SESSION_VALUE_USER));
		String value = "";
		for (Benutzer b : this.lBenutzer)
			value = value + b.getNachname() + ", " + b.getVorname() + "\n";
		this.taParticipants.setReadOnly(false);
		this.taParticipants.setValue(value);
		this.taParticipants.setReadOnly(true);
	}

	protected boolean addTeilnehmer(Benutzer b) {
		if(!this.lBenutzer.contains(b))
			return this.lBenutzer.add(b);
		return false;
	}

	protected boolean removeTeilnehmer(Benutzer b) {
		return this.lBenutzer.remove(b);
	}

}
