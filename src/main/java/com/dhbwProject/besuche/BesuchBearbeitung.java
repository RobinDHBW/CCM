package com.dhbwProject.besuche;

import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedList;

import com.dhbwProject.backend.CCM_Constants;
import com.dhbwProject.backend.dbConnect;
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
		this.fields.setTeilnehmenr(b.getBesucher());
		this.fields.setUnternehmen(b.getAdresse().getUnternehmen());
		this.fields.setAdresse(b.getAdresse());
		this.fields.setAnsprechpartner(b.getAnsprechpartner());
	}
	
	private void initFields(){
		this.fields = new BesuchFelder();
		this.btnUpdate = new Button("Bearbeiten");
		this.btnUpdate.setIcon(FontAwesome.CHECK);
		this.btnUpdate.addClickListener(listener ->{
			if(fields.isValid()){
				try {
					bearbeiteBesuch();
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
	
	private void bearbeiteBesuch() throws IllegalArgumentException, NullPointerException, SQLException{
		Notification message = new Notification("");
		message.setPosition(Position.TOP_CENTER);
		LinkedList<Besuch> lbKollision = checkBesuchKollision(dbConnection.getBesuchByAdresse(fields.getAdresse()),bAlt, fields.getDateStart());
		if(lbKollision.size() >0){
			BesuchKollisionsanzeige anzeige = new BesuchKollisionsanzeige(lbKollision);
			anzeige.addCloseListener(close ->{
				if(anzeige.getResult()){
						besuchBearbeitung();
						message.setStyleName(ValoTheme.NOTIFICATION_SUCCESS);
						message.setCaption(fields.getTitel()+" wurde erfolgreich bearbeitet");
						message.show(Page.getCurrent());
				}else{
						message.setStyleName(ValoTheme.NOTIFICATION_SUCCESS);
						message.setCaption(fields.getTitel()+" wurde nicht bearbeitet");
						message.show(Page.getCurrent());
				}
				close();
			});
			getUI().addWindow(anzeige);
		}else{
			besuchBearbeitung();
			close();
		}
	}
	
	private void besuchBearbeitung(){
		try {
			this.bNeu = this.dbConnection.changeBesuch(new Besuch(0, fields.getTitel(),
					fields.getDateStart(), fields.getDateEnd(),
					fields.getAdresse(), fields.getStatus(), fields.getAnsprechpartner(),
					fields.getTeilnehmenr(), null, fields.getAutor()), this.bAlt);
		} catch (SQLException e) {
			bNeu = null;
			e.printStackTrace();
		}
	}
	
	private LinkedList<Besuch> checkBesuchKollision(LinkedList<Besuch> lBesuch, Besuch bBasis, Date dStartNew){
		LinkedList<Besuch> lbResult = new LinkedList<Besuch>();
		for(Besuch b : lBesuch){
			if(b.getId() == bBasis.getId())
				continue;
			if(differenzTage(b.getStartDate(), dStartNew) < CCM_Constants.BESUCH_KOLLISION_WERT)
				lbResult.add(b);
		}
		return lbResult;	
	}
	
	private long differenzTage(Date dAlt, Date dNeu){
		return  Math.abs(((dNeu.getTime() - dAlt.getTime() + CCM_Constants.ONE_HOUR_AS_LONG) / (CCM_Constants.ONE_HOUR_AS_LONG * 24)));
	}
	
	protected void setDateStart(Date dStart){
		this.fields.setDateStart(dStart);
	}
	
	protected void setDateEnd(Date dEnd){
		this.fields.setDateEnd(dEnd);
	}
	
}
