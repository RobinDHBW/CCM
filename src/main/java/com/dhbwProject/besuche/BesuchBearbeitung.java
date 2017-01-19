package com.dhbwProject.besuche;

import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedList;

import com.dhbwProject.backend.CCM_Constants;
import com.dhbwProject.backend.dbConnect;
import com.dhbwProject.backend.beans.Adresse;
import com.dhbwProject.backend.beans.Ansprechpartner;
import com.dhbwProject.backend.beans.Benutzer;
import com.dhbwProject.backend.beans.Besuch;
import com.dhbwProject.backend.beans.Status;
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

public class BesuchBearbeitung extends Window {
	private static final long serialVersionUID = 1L;
	private dbConnect dbConnection;
	private Besuch bAlt;
	private Besuch bNeu;
	
	private VerticalLayout vlLayout;
	private BesuchFelder fields;
	private Button btnUpdate;
	
	public BesuchBearbeitung(){
		this.dbConnection = (dbConnect)VaadinSession.getCurrent().getSession().getAttribute(CCM_Constants.SESSION_VALUE_CONNECTION);
		this.setContent(this.initContent());
		
		setCaptionAsHtml(true);
		center();
		setWidth("450px");
		setHeight("600px");
		setDraggable(true);
		setClosable(true);
		setModal(false);
		
	}
	
	public BesuchBearbeitung(Besuch b){
		this();
		this.bAlt = b;	
		//Probeweise--------------------------------------------
		if(b.getAutor().equals(VaadinSession.getCurrent().getSession().getAttribute(CCM_Constants.SESSION_VALUE_USER)))
			setCaption("<center><b>Sie bearbeiten einen Termin von:</b></center>"+
					"<center><p><b>Ihnen</b></p></center>");
		else
			setCaption("<center><b>Sie bearbeiten einen Termin von:</b></center>"+
					"<center><p><b>"+b.getAutor().getNachname()+", "+b.getAutor().getVorname()+"</b></p></center>");	
		//------------------------------------------------------
		this.fields.setTitel(b.getName());
		this.fields.setStatus(b.getStatus());
		this.fields.setAutor(b.getAutor());
		this.fields.setDateStart(b.getStartDate());
		this.fields.setDateEnd(b.getEndDate());
//		this.fields.setDateStart(new Date(b.getStartDate().getTime()));
//		this.fields.setDateEnd(new Date(b.getEndDate().getTime()));
		this.fields.setTeilnehmenr(b.getBesucher());
		this.fields.setAdresse(b.getAdresse());
//		this.fields.setUnternehmen();
		this.fields.setAnsprechpartner(b.getAnsprechpartner());
	}
	
	private void initFields(){
		this.fields = new BesuchFelder();
		this.btnUpdate = new Button("Bearbeiten");
		this.btnUpdate.setIcon(FontAwesome.CHECK);
		this.btnUpdate.addClickListener(listener ->{
			if(fields.isValid()){
				try {
					this.bNeu = this.dbConnection.changeBesuch(new Besuch(0, fields.getTitel(),
						fields.getDateStart(), fields.getDateEnd(),
						fields.getAdresse(), fields.getStatus(), fields.getAnsprechpartner(),
						fields.getTeilnehmenr(), null, fields.getAutor()), this.bAlt);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				this.close();
			}else{
				Notification meldung = new Notification("Plichtfelder müssen gefüllt werden");
				meldung.setStyleName(ValoTheme.NOTIFICATION_FAILURE);
				meldung.setPosition(Position.TOP_CENTER);
				meldung.show(Page.getCurrent());
				return;
			}
		});
		this.fields.addComponent(this.btnUpdate);
	}

	private void initVlLayout(){
		this.initFields();
		this.vlLayout = new VerticalLayout(this.fields);
		this.vlLayout.setSizeFull();
		this.vlLayout.setComponentAlignment(this.fields, Alignment.MIDDLE_CENTER);
	}
	
	private Panel initContent(){
		this.initVlLayout();
		Panel p = new Panel();
		p.setContent(this.vlLayout);
		return p;
	}
	
	protected Besuch getBearbeitung(){
		return this.bNeu;
	}
	
}
