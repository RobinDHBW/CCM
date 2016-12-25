package com.dhbwProject.termine;

import java.util.ArrayList;

import com.dhbwProject.backend.beans.Benutzer;
import com.dhbwProject.backend.beans.Unternehmen;
import com.dhbwProject.benutzer.LookupBenutzer;
import com.dhbwProject.unternehmen.LookupUnternehmen;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.DateField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.Reindeer;
import com.vaadin.ui.themes.ValoTheme;

public class TerminAnlage extends CustomComponent {
	private static final long serialVersionUID = 1L;
	
	private VerticalLayout vlContent;
	private VerticalLayout vlLayout;
	
	private DateField dfDate;
	private TextField tfUnternehmen;
	private Button btnLookupUnternehmen;
	private TextArea taParticipants;
	private Button btnLookupParticipants;
	private Button btnCreate;
	
	private Unternehmen uAnlage;
	private ArrayList<Benutzer> alBenutzer;
	
	
	public TerminAnlage(){
		this.alBenutzer = new ArrayList<Benutzer>();
		this.initVlContent();
		this.initFields();
		this.initVlLayout();
	}
	
	private void initVlContent(){
		this.vlContent = new VerticalLayout();
		this.vlContent.setSizeUndefined();
		this.vlContent.setSpacing(true);
		this.vlContent.setMargin(new MarginInfo(true, false, true, true));
	}
	
	private void initFields(){
		this.initDfDate();
		this.initFieldUnternehmen();
		this.initFieldParticipants();
		this.initBtnCreate();
	}
	
	private void initDfDate(){
		this.dfDate = new DateField("Wann?");
		this.dfDate.setWidth("300px");
		this.dfDate.setResolution(Resolution.MINUTE);
		this.vlContent.addComponent(this.dfDate);
	}
	
	private void initFieldUnternehmen(){
		HorizontalLayout hlUnternehmen = new HorizontalLayout();
		this.tfUnternehmen = new TextField();
		this.tfUnternehmen.setInputPrompt("Unternehmen");
		this.tfUnternehmen.setWidth("300px");
		
		this.btnLookupUnternehmen = new Button();
		this.btnLookupUnternehmen.setIcon(FontAwesome.REPLY);
		this.btnLookupUnternehmen.setStyleName(ValoTheme.BUTTON_BORDERLESS);
		this.btnLookupUnternehmen.setWidth("50px");
		this.btnLookupUnternehmen.addClickListener(listener ->{
			LookupUnternehmen lookup = new LookupUnternehmen(this.uAnlage);
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
		this.vlContent.addComponent(hlUnternehmen);
	}
	
	private void initFieldParticipants(){
		HorizontalLayout hlParticipants = new HorizontalLayout();
		this.taParticipants = new TextArea();
		this.taParticipants.setInputPrompt("Teilnehmer");
		this.taParticipants.setWidth("300px");
		
		this.btnLookupParticipants = new Button();
		this.btnLookupParticipants.setIcon(FontAwesome.REPLY);
		this.btnLookupParticipants.setStyleName(ValoTheme.BUTTON_BORDERLESS);
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
		this.vlContent.addComponent(hlParticipants);
	}
	
	private void initBtnCreate(){
		this.btnCreate = new Button("Termin erstellen");
		this.btnCreate.setIcon(FontAwesome.PLUS);
		this.btnCreate.setWidth("300px");
		this.btnCreate.addClickListener(listener ->{
			/*	Hier muss eine Abfrage erfolgen ob bei diesem Unternehmen
			 *	vor kurzem oder in naher Zukunft Termine geplant waren
			 *	oder bereits geplant sind*/
			boolean b = false;
			if(b){
				//erstelle termin 
			}else
				this.createWarning();
				
		});
		
		this.vlContent.addComponent(this.btnCreate);
	}
	
	private void initVlLayout(){
		this.vlLayout = new VerticalLayout(this.vlContent);
		this.vlLayout.setSizeFull();
		this.vlLayout.setComponentAlignment(this.vlContent, Alignment.TOP_LEFT);
		this.vlLayout.setStyleName(Reindeer.LAYOUT_BLUE);
		this.setCompositionRoot(this.vlLayout);
	}
	
	private void createWarning(){
		Window warning = new Window();
		VerticalLayout vlFields = new VerticalLayout();
		Button btnYes = new Button("Ja");
		Button btnNo = new Button("Nein");
		
		btnYes.setWidth("200px");
		btnYes.setIcon(FontAwesome.CHECK);
		btnYes.addClickListener(listener ->{
			//erstelle anlage
			Notification.show("Termin wurde erstellt");
			warning.close();
		});
		
		btnNo.setWidth("200px");
		btnNo.setIcon(FontAwesome.CLOSE);
		btnNo.addClickListener(listener ->{
			Notification.show("Termin wurde nicht erstellt");
			warning.close();
		});
		
		vlFields.setWidth("100%");
		vlFields.setMargin(new MarginInfo(true, false, true, false));
		vlFields.addComponent(btnYes);
		vlFields.addComponent(btnNo);
		vlFields.setComponentAlignment(btnYes, Alignment.MIDDLE_CENTER);
		vlFields.setComponentAlignment(btnNo, Alignment.MIDDLE_CENTER);
		
		
		warning.setCaptionAsHtml(true);
		warning.setCaption("<center><h1>Achtung!</h1>"
				+ "<p>Bei diesem Unternehmen sind zeitnahe Termine bereits in planung</p>"
				+ "<p>Möchten Sie diesen Termin dennoch erstellen?</p><center>");
		warning.setSizeFull();
		warning.setModal(true);
		warning.setClosable(false);
		warning.setContent(vlFields);
		
		this.getUI().addWindow(warning);
	}
	
}
