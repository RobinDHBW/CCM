package com.dhbwProject.besuche;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

import com.dhbwProject.backend.CCM_Constants;
import com.dhbwProject.backend.EMailThread;
import com.dhbwProject.backend.dbConnect;
import com.dhbwProject.backend.beans.Benutzer;
import com.dhbwProject.backend.beans.Besuch;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.Position;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

public class BesuchAnlage extends Window {
	private static final long serialVersionUID = 1L;
	private dbConnect dbConnection;
	private Besuch bAnlage;
	
	private BesuchFelder fields;
	private VerticalLayout vlLayout;
	private Button btnCreate;	
	
	public BesuchAnlage(){
		this.dbConnection = (dbConnect)VaadinSession.getCurrent().getSession().getAttribute(CCM_Constants.SESSION_VALUE_CONNECTION);
		this.setContent(this.initContent());
		
		setCaptionAsHtml(true);
		setCaption("<center><h3>Termin anlegen</h3></center>");
		center();
		setWidth("450px");
		setHeight("600px");
		setDraggable(true);
		setClosable(true);
		setModal(false);
	}
	
	public BesuchAnlage(Date date){
		this();
		this.fields.setDateStart(date);
		this.fields.setDateEnd(date);
	}
	
	private void initFields(){
		this.fields = new BesuchFelder();
		this.fields.addTeilnehmer((Benutzer)VaadinSession.getCurrent().getSession().getAttribute(CCM_Constants.SESSION_VALUE_USER));
		this.btnCreate = new Button("Termin erstellen");
		this.btnCreate.setIcon(FontAwesome.PLUS);
		this.btnCreate.setWidth("300px");
		this.btnCreate.addClickListener(listener ->{
			if(fields.isValid()){
				try {	
					erstelleBesuch();		
					sendMail();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}else{
			Notification meldung = new Notification("Plichtfelder müssen gefüllt werden");
			meldung.setStyleName(ValoTheme.NOTIFICATION_FAILURE);
			meldung.setPosition(Position.TOP_CENTER);
			meldung.show(Page.getCurrent());
			return;
		}
		});
		this.fields.addComponent(this.btnCreate);
	}
	
	private void initVlLayout(){
		this.initFields();
		this.vlLayout = new VerticalLayout(this.fields);
		this.vlLayout.setSizeFull();
		this.vlLayout.setComponentAlignment(this.fields, Alignment.TOP_CENTER);
	}
	
	private Panel initContent(){
		this.initVlLayout();
		Panel p = new Panel();
		p.setContent(vlLayout);
		return p;
	}
	
	protected Besuch getAnlage(){
		return this.bAnlage;
	}
	
	protected void sendMail(){
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy, HH:mm");
		ArrayList<String> eMailList = new ArrayList<String>();
		String titel = "Teilnahme an: "+this.fields.getTitel();
		String inhalt = "<b>Start am: </b><br>"+dateFormat.format(this.fields.getDateStart())+" Uhr <br>"
				+"<b>bis: </b><br>"+dateFormat.format(this.fields.getDateEnd())+" Uhr <br>"
				+"<b>bei: </b><br>"+this.fields.getAdresse().getUnternehmen().getName()+"<br>"
				+this.fields.getAdresse().getStrasse()+" "+this.fields.getAdresse().getHausnummer()+"<br>"
				+this.fields.getAdresse().getPlz()+" "+this.fields.getAdresse().getOrt()+"<br>"
				+"<b>Ansprechpartner: </b><br>"
				+this.fields.getAnsprechpartner().getNachname()+", "+this.fields.getAnsprechpartner().getVorname()+"<br>"
				+"<b>Teilnehmener: </b>"+"<br>";
		
		for(Benutzer b : this.fields.getTeilnehmenr()){
			if(b.getEmail() != null)
				eMailList.add(b.getEmail());
			inhalt = inhalt+b.getNachname()+", "+ b.getVorname() + "<br>";
		}
		if(eMailList.size() >0){
			EMailThread thread = new EMailThread(eMailList, titel, inhalt);
			thread.start();
		}
	}
	
	private void erstelleBesuch() throws IllegalArgumentException, NullPointerException, SQLException{
		Notification message = new Notification("");
		message.setPosition(Position.TOP_CENTER);
		LinkedList<Besuch> lbKollision = checkBesuchKollision(dbConnection.getBesuchByAdresse(fields.getAdresse()), fields.getDateStart());
		if(lbKollision.size() > 0){
			BesuchKollisionsanzeige anzeige = new BesuchKollisionsanzeige(lbKollision);
			anzeige.addCloseListener(close ->{
				if(anzeige.getResult()){
						besuchErstellen();
						message.setStyleName(ValoTheme.NOTIFICATION_SUCCESS);
						message.setCaption(fields.getTitel()+" wurde erfolgreich erstellt");
						message.show(Page.getCurrent());
				}else{
						message.setStyleName(ValoTheme.NOTIFICATION_SUCCESS);
						message.setCaption(fields.getTitel()+" wurde nicht erstellt");
						message.show(Page.getCurrent());
				}
				close();
			});
			getUI().addWindow(anzeige);
		}else{
			besuchErstellen();
			close();
		}
	}
	
	private void besuchErstellen(){
		try {
			this.bAnlage = this.dbConnection.createBesuch(new Besuch(0, fields.getTitel(),
					fields.getDateStart(), fields.getDateEnd(),
					fields.getAdresse(), fields.getStatus(), fields.getAnsprechpartner(),
					fields.getTeilnehmenr(), null, fields.getAutor()));
		} catch (SQLException e) {
			bAnlage = null;
			e.printStackTrace();
		}	
	}
	
	private long differenzTage(Date dAlt, Date dNeu){
		return  Math.abs(((dNeu.getTime() - dAlt.getTime() + CCM_Constants.ONE_HOUR_AS_LONG) / (CCM_Constants.ONE_HOUR_AS_LONG * 24)));
	}

	private LinkedList<Besuch> checkBesuchKollision(LinkedList<Besuch> lBesuch, Date dStartNew){
	LinkedList<Besuch> lbKollision = new LinkedList<Besuch>();
		for(Besuch b : lBesuch)
			if(differenzTage(b.getStartDate(), dStartNew) < CCM_Constants.BESUCH_KOLLISION_WERT)
			lbKollision.add(b);
	return lbKollision;	
	}

}
