package com.dhbwProject.termine;

import java.util.ArrayList;
import java.util.Date;

import com.dhbwProject.backend.beans.Ansprechpartner;
import com.dhbwProject.backend.beans.Benutzer;
import com.dhbwProject.backend.beans.Besuch;
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
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class TerminFields extends VerticalLayout {
	private static final long serialVersionUID = 1L;
	
	private Besuch termin;
	
	private TextField tfTitel;
	private DateField dfDate;
	private TextField tfUnternehmen;
	private Button btnLookupUnternehmen;
	private TextField tfAnsprechpartner;
	private Button btnLookupAnsprechpartner;
	private TextArea taParticipants;
	private Button btnLookupParticipants;
	
	//Diese Objekte können später wegfallen, sofern die Termin-Beanklasse implementiert ist
	private Unternehmen unternehmen = new Unternehmen();
	private Ansprechpartner ansprechpartner = new Ansprechpartner();
	private ArrayList<Benutzer> alBenutzer = new ArrayList<Benutzer>();
	
	public TerminFields(){
		this.setSizeUndefined();
		this.setSpacing(true);
		this.setMargin(new MarginInfo(true, true, true, true));
	}
	
	protected void initFieldTitel(){
		this.tfTitel = new TextField();
		this.tfTitel.setInputPrompt("Titel");
		this.tfTitel.setWidth("300px");
		this.addComponent(this.tfTitel);
	}
	
	protected void initDfDate(){
		this.dfDate = new DateField();
		this.dfDate.setWidth("300px");
		this.dfDate.setResolution(Resolution.MINUTE);
		this.addComponent(this.dfDate);
	}
	
	protected void initFieldUnternehmen(){
		HorizontalLayout hlUnternehmen = new HorizontalLayout();
		this.tfUnternehmen = new TextField();
		this.tfUnternehmen.setInputPrompt("Unternehmen");
		this.tfUnternehmen.setWidth("300px");
		
		this.btnLookupUnternehmen = new Button();
		this.btnLookupUnternehmen.setIcon(FontAwesome.REPLY);
//		this.btnLookupUnternehmen.setStyleName(ValoTheme.BUTTON_BORDERLESS);
		this.btnLookupUnternehmen.setWidth("50px");
		this.btnLookupUnternehmen.addClickListener(listener ->{
			LookupUnternehmen lookup = new LookupUnternehmen(this.unternehmen);
			lookup.addCloseListener(CloseListener ->{
				/*	In diesem Wert erfolgt das zurückschreiben
				 *	zur Anzeige in dem TextField
				 * */
			});
			this.getUI().addWindow(lookup);

		});
		hlUnternehmen.setSizeUndefined();
		hlUnternehmen.addComponent(this.tfUnternehmen);
		hlUnternehmen.addComponent(this.btnLookupUnternehmen);
		this.addComponent(hlUnternehmen);
	}
	
	protected void initFieldAnsprechpartner(){
		HorizontalLayout hlAnsprechpartner = new HorizontalLayout();
		this.tfAnsprechpartner = new TextField();
		this.tfAnsprechpartner.setInputPrompt("Ansprechpartner");
		this.tfAnsprechpartner.setWidth("300px");
		
		this.btnLookupAnsprechpartner = new Button();
		this.btnLookupAnsprechpartner.setIcon(FontAwesome.REPLY);
//		this.btnLookupAnsprechpartner.setStyleName(ValoTheme.BUTTON_BORDERLESS);
		this.btnLookupAnsprechpartner.setWidth("50px");
		this.btnLookupAnsprechpartner.addClickListener(listener ->{
			LookupAnsprechpartner lookup = new LookupAnsprechpartner(this.unternehmen, this.ansprechpartner);
			lookup.addCloseListener(CloseListener ->{
				/*	In diesem Wert erfolgt das zurückschreiben
				 *	zur Anzeige in dem TextField
				 * */
			});
			this.getUI().addWindow(lookup);

		});
		hlAnsprechpartner.setSizeUndefined();
		hlAnsprechpartner.addComponent(this.tfAnsprechpartner);
		hlAnsprechpartner.addComponent(this.btnLookupAnsprechpartner);
		this.addComponent(hlAnsprechpartner);
	}
	
	protected void initFieldParticipants(){
		HorizontalLayout hlParticipants = new HorizontalLayout();
		this.taParticipants = new TextArea();
		this.taParticipants.setInputPrompt("Teilnehmer");
		this.taParticipants.setWidth("300px");
		
		this.btnLookupParticipants = new Button();
		this.btnLookupParticipants.setIcon(FontAwesome.REPLY);
//		this.btnLookupParticipants.setStyleName(ValoTheme.BUTTON_BORDERLESS);
		this.btnLookupParticipants.setWidth("50px");
		this.btnLookupParticipants.addClickListener(listener ->{
			LookupBenutzer lookup = new LookupBenutzer(this.alBenutzer);
			lookup.addCloseListener(CloseListener ->{
				/*	In diesem Wert erfolgt das zurückschreiben
				 *	zur Anzeige in dem TextArea
				 *	Hier erfolgt eine Dummy-Zurückschreibung
				 * */	
				String value = "";
				for(Benutzer b : this.alBenutzer)
					value = value +b.getNachname()+", "+b.getVorname()+"\n"; 
				this.taParticipants.setValue(value);
			});
			this.getUI().addWindow(lookup);
		});
		
		hlParticipants.setSizeUndefined();
		hlParticipants.addComponent(this.taParticipants);
		hlParticipants.addComponent(this.btnLookupParticipants);
		this.addComponent(hlParticipants);
	}
	
	protected Besuch getTermin(){
		return this.termin;
	}
	
	protected void setTermin(Besuch b){
		this.termin = b;
	}

	//Die Folgenden Methoden können durch das Termin-Objekt abgelöst werden
	protected Date getDate(){
		return this.dfDate.getValue();
	}
	
	protected void setDate(Date d){
		this.dfDate.setValue(d);
	}
	
	protected Unternehmen getUnternehmen(){
		return this.unternehmen;
	}
	
	protected void setUnternehmen(Unternehmen u){
		this.unternehmen = u;
	}
	
	protected Ansprechpartner getAnsprechpartner(){
		return this.ansprechpartner;
	}
	
	protected void setAnsprechpartner(Ansprechpartner a){
		this.ansprechpartner = a;
	}
	
	protected ArrayList<Benutzer> getTeilnehmenr(){
		return this.alBenutzer;
	}
	
	protected void setTeilnehmenr(ArrayList<Benutzer> alBenutzer){
		this.alBenutzer = alBenutzer;
	}
	
	protected boolean addTeilnehmer(Benutzer b){
		return this.alBenutzer.add(b);
	}
	
	protected boolean removeTeilnehmer(Benutzer b){
		return this.alBenutzer.remove(b);
	}
	
	protected void clear(){
		this.termin = null;
		this.unternehmen = null;
		this.ansprechpartner = null;
		this.alBenutzer.clear();
	}
}
