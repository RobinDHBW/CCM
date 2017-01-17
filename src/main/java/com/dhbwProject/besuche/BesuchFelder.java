package com.dhbwProject.besuche;

import java.util.Date;
import java.util.LinkedList;

import com.dhbwProject.backend.beans.Adresse;
import com.dhbwProject.backend.beans.Ansprechpartner;
import com.dhbwProject.backend.beans.Benutzer;
import com.dhbwProject.backend.beans.Status;
import com.dhbwProject.backend.beans.Unternehmen;
import com.dhbwProject.benutzer.LookupBenutzer;
import com.dhbwProject.unternehmen.LookupAnsprechpartner;
import com.dhbwProject.unternehmen.LookupUnternehmen;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.Button;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class BesuchFelder extends VerticalLayout {
	private static final long serialVersionUID = 1L;
	
	private TextField tfTitel;
	
	private OptionGroup ogStatus;
	
	private DateField dfDateStart;
	private DateField dfDateEnd;
	
	private TextArea taAdresse;
	private TextField tfUnternehmen;
	private Button btnLookupAdresse;
	
	private TextField tfAnsprechpartner;
	private Button btnLookupAnsprechpartner;
	
	private TextArea taParticipants;
	private Button btnLookupParticipants;

	private Benutzer autor;
	private Adresse adresse;
	private Unternehmen unternehmen;
	private Ansprechpartner ansprechpartner;
	private Status staus;
	private LinkedList<Benutzer> lBenutzer = new LinkedList<Benutzer>();

	public BesuchFelder() {
		this.setSizeUndefined();
		this.setSpacing(true);
		this.setMargin(new MarginInfo(true, true, true, true));
		
		this.initFieldTitel();
		this.initFieldStartDate();
		this.initFieldEndDate();
		this.initFieldAdresse();
		this.initFieldAnsprechpartner();
		this.initFieldParticipants();
	}

	protected void initFieldTitel() {
		this.tfTitel = new TextField();
		this.tfTitel.setCaption("Titel");
		this.tfTitel.setInputPrompt("Titel");
		this.tfTitel.setWidth("300px");
		this.addComponent(this.tfTitel);
	}
	
	protected void initOgStatus(){
		/*
		 * Hier bräuchte ich eine getAllStatus-Methode aus dem dbConnect
		 * damit ich die Options befüllen kann
		 * */
//		this.ogStatus = new OptionGroup();
//		this.ogStatus.container
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
		this.tfUnternehmen.setInputPrompt("Unternehmen");
		this.tfUnternehmen.setWidth("300px");
		
		this.taAdresse = new TextArea();
		this.taAdresse.setInputPrompt("Standort");
		this.taAdresse.setWidth("300px");

		this.btnLookupAdresse = new Button();
		this.btnLookupAdresse.setIcon(FontAwesome.REPLY);
		this.btnLookupAdresse.setWidth("50px");
		this.btnLookupAdresse.addClickListener(listener -> {
//			LookupAdresse lookup = new LookupAdresse();
//			lookup.addCloseListener(CloseListener -> {
//				if(lookup.getSelection() != null){
//					this.setAdresse(lookup.getSelection());
//				}
//			});
//			this.getUI().addWindow(lookup);
			
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
		this.tfAnsprechpartner.setInputPrompt("Ansprechpartner");
		this.tfAnsprechpartner.setWidth("300px");

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
		this.taParticipants.setInputPrompt("Teilnehmer");
		this.taParticipants.setWidth("300px");

		this.btnLookupParticipants = new Button();
		this.btnLookupParticipants.setIcon(FontAwesome.REPLY);
		this.btnLookupParticipants.setWidth("50px");
		this.btnLookupParticipants.addClickListener(listener -> {
			this.lBenutzer.clear();
			LookupBenutzer lookup = new LookupBenutzer(this.lBenutzer);
			lookup.addCloseListener(CloseListener -> {
				this.setTeilnehmenr(this.lBenutzer);
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
		this.taAdresse.setValue(a.getStrasse()+"\n"+a.getPlz()+"\n"+a.getOrt());
	}
	
	protected Unternehmen getUnternehmen(){
		return this.unternehmen;
	}
	
	protected void setUnternehmen(Unternehmen u){
		this.unternehmen = u;
		this.tfUnternehmen.setValue(u.getName());
	}

	protected Ansprechpartner getAnsprechpartner() {
		return this.ansprechpartner;
	}

	protected void setAnsprechpartner(Ansprechpartner a) {
		this.ansprechpartner = a;
		this.tfAnsprechpartner.setValue(a.getNachname()+", "+a.getVorname());
	}

	protected LinkedList<Benutzer> getTeilnehmenr() {
		return this.lBenutzer;
	}

	protected void setTeilnehmenr(LinkedList<Benutzer> lBenutzer) {
		this.lBenutzer = lBenutzer;
		String value = "";
		for (Benutzer b : this.lBenutzer)
			value = value + b.getNachname() + ", " + b.getVorname() + "\n";
		this.taParticipants.setValue(value);
	}

	protected boolean addTeilnehmer(Benutzer b) {
		return this.lBenutzer.add(b);
	}

	protected boolean removeTeilnehmer(Benutzer b) {
		return this.lBenutzer.remove(b);
	}

}
