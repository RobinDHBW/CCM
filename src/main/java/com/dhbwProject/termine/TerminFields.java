package com.dhbwProject.termine;

import java.util.Date;
import java.util.LinkedList;

import com.dhbwProject.backend.DummyDataManager;
import com.dhbwProject.backend.beans.Adresse;
import com.dhbwProject.backend.beans.Ansprechpartner;
import com.dhbwProject.backend.beans.Benutzer;
import com.dhbwProject.benutzer.LookupBenutzer;
import com.dhbwProject.unternehmen.LookupAdresse;
import com.dhbwProject.unternehmen.LookupAnsprechpartner;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.Button;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class TerminFields extends VerticalLayout {
	private static final long serialVersionUID = 1L;
	private DummyDataManager  dummyData;
	
	private TextField tfTitel;
	private Button btnLookupBesuch;
	
//	private TextField tfAutor;
	
	private DateField dfDateStart;
	private DateField dfDateEnd;
	
	private TextField tfAdresse;
	private Button btnLookupAdresse;
	
	private TextField tfAnsprechpartner;
	private Button btnLookupAnsprechpartner;
	
	private TextArea taParticipants;
	private Button btnLookupParticipants;

	/*
	 * Die Folgenden Entitäten sollen anschließend an die Besuch-Factory im
	 * Backend geschickt werden um die Erzeugung vorzunehmen
	 */
	private Benutzer autor;
	private Adresse adresse;
	private Ansprechpartner ansprechpartner;
	private LinkedList<Benutzer> lBenutzer = new LinkedList<Benutzer>();

	public TerminFields(DummyDataManager dummyData) {
		this.dummyData = dummyData;
		this.setSizeUndefined();
		this.setSpacing(true);
		this.setMargin(new MarginInfo(true, true, true, true));
	}

	protected void initFieldTitel() {
		HorizontalLayout hlBesuch = new HorizontalLayout();
		
		this.tfTitel = new TextField();
		this.tfTitel.setInputPrompt("Titel");
		this.tfTitel.setWidth("300px");
		this.addComponent(this.tfTitel);
		
		this.btnLookupBesuch = new Button();
		this.btnLookupBesuch.setIcon(FontAwesome.REPLY);
		this.btnLookupBesuch.setWidth("50px");
		this.btnLookupBesuch.addClickListener(listener -> {

		});
		hlBesuch.setSizeUndefined();
		hlBesuch.setSpacing(true);
		hlBesuch.addComponent(this.tfTitel);
		hlBesuch.addComponent(this.btnLookupBesuch);
		hlBesuch.setCaption("Titel:");
		this.addComponent(hlBesuch);
	}
	
//	protected void initFieldAutor(){
//		this.tfAutor = new TextField();
//		this.tfAutor.setCaption("Autor:");
//		this.tfAutor.setWidth("300px");
//		this.addComponent(this.tfAutor);
//	}

	protected void initDfDateStart() {
		this.dfDateStart = new DateField();
		this.dfDateStart.setCaption("Start:");
		this.dfDateStart.setWidth("300px");
		this.dfDateStart.setResolution(Resolution.MINUTE);
		this.addComponent(this.dfDateStart);
	}
	
	protected void initDfDateEnd(){
		this.dfDateEnd = new DateField();
		this.dfDateEnd.setCaption("Ende:");
		this.dfDateEnd.setWidth("300px");
		this.dfDateEnd.setResolution(Resolution.MINUTE);
		this.addComponent(this.dfDateEnd);
	}

	protected void initFieldAdresse() {
		HorizontalLayout hlUnternehmen = new HorizontalLayout();
		this.tfAdresse = new TextField();
		this.tfAdresse.setInputPrompt("Unternehmen");
		this.tfAdresse.setWidth("300px");

		this.btnLookupAdresse = new Button();
		this.btnLookupAdresse.setIcon(FontAwesome.REPLY);
		// this.btnLookupUnternehmen.setStyleName(ValoTheme.BUTTON_BORDERLESS);
		this.btnLookupAdresse.setWidth("50px");
		this.btnLookupAdresse.addClickListener(listener -> {
			LookupAdresse lookup = new LookupAdresse(this.dummyData);
			lookup.addCloseListener(CloseListener -> {
				/*
				 * In diesem Wert erfolgt das zurückschreiben zur Anzeige in dem
				 * TextField
				 */
				if(lookup.getSelection() != null){
					this.adresse = lookup.getSelection();
					this.tfAdresse.setValue(this.adresse.getUnternehmen().getName());
				}
				/*
				 * Ansich sollte hier auf die Referenz this.adresse zugegriffen werden
				 * diese scheint aus mir unbekannten gründen nicht befüllt zu sein
				 * obwohl das vorgehen call by reference ist...
				 * naja so gehts natürlich auch
				 * */
			});
			this.getUI().addWindow(lookup);

		});
		hlUnternehmen.setSizeUndefined();
		hlUnternehmen.setSpacing(true);
		hlUnternehmen.addComponent(this.tfAdresse);
		hlUnternehmen.addComponent(this.btnLookupAdresse);
		hlUnternehmen.setCaption("Unternehmen:");
		this.addComponent(hlUnternehmen);
	}

	protected void initFieldAnsprechpartner() {
		HorizontalLayout hlAnsprechpartner = new HorizontalLayout();
		this.tfAnsprechpartner = new TextField();
		this.tfAnsprechpartner.setInputPrompt("Ansprechpartner");
		this.tfAnsprechpartner.setWidth("300px");

		this.btnLookupAnsprechpartner = new Button();
		this.btnLookupAnsprechpartner.setIcon(FontAwesome.REPLY);
		// this.btnLookupAnsprechpartner.setStyleName(ValoTheme.BUTTON_BORDERLESS);
		this.btnLookupAnsprechpartner.setWidth("50px");
		this.btnLookupAnsprechpartner.addClickListener(listener -> {
			LookupAnsprechpartner lookup = new LookupAnsprechpartner(this.adresse, this.dummyData);
			lookup.addCloseListener(CloseListener -> {
				/*
				 * In diesem Wert erfolgt das zurückschreiben zur Anzeige in dem
				 * TextField
				 */
				if(lookup.getAnsprechpartner() != null){
					this.ansprechpartner = lookup.getAnsprechpartner();
					this.tfAnsprechpartner.setValue(this.ansprechpartner.getNachname()+", "+this.ansprechpartner.getVorname());
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
		// this.btnLookupParticipants.setStyleName(ValoTheme.BUTTON_BORDERLESS);
		this.btnLookupParticipants.setWidth("50px");
		this.btnLookupParticipants.addClickListener(listener -> {
			this.lBenutzer.clear();
			LookupBenutzer lookup = new LookupBenutzer(this.lBenutzer, this.dummyData);
			lookup.addCloseListener(CloseListener -> {
				/*
				 * In diesem Wert erfolgt das zurückschreiben zur Anzeige in dem
				 * TextArea Hier erfolgt eine Dummy-Zurückschreibung
				 */
				String value = "";
				for (Benutzer b : this.lBenutzer)
					value = value + b.getNachname() + ", " + b.getVorname() + "\n";
				this.taParticipants.setValue(value);
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
//			this.tfAutor.setValue(b.getNachname()+", "+b.getVorname());
		}
	}
	
	protected void initAnlage(){
		this.btnLookupBesuch.setVisible(false);
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
		this.tfAdresse.setValue(a.getStrasse()+", "+a.getPlz()+" "+a.getOrt());
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
