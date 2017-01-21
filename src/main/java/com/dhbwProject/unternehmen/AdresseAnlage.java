package com.dhbwProject.unternehmen;

import java.sql.SQLException;
import java.util.LinkedList;

import com.dhbwProject.backend.CCM_Constants;
import com.dhbwProject.backend.dbConnect;
import com.dhbwProject.backend.beans.Adresse;
import com.dhbwProject.backend.beans.Ansprechpartner;
import com.dhbwProject.backend.beans.Unternehmen;
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

public class AdresseAnlage extends Window {
	private static final long serialVersionUID = 1L;
	private Adresse aNeu;
	private Unternehmen uAlt;
	private Unternehmen uNeu;
	private dbConnect dbConnection;
	
	private AdresseFelder fields;
	private Button btnAnlage;
	
	public AdresseAnlage(){
		this.dbConnection = (dbConnect)VaadinSession.getCurrent().getSession().getAttribute(CCM_Constants.SESSION_VALUE_CONNECTION);
		this.center();
		this.setCaptionAsHtml(true);
		this.setCaption("<center><h3>Adresse hinzufügen</h3></center>");
		this.setWidth("350px");
		this.setHeight("500px");
		this.setClosable(true);
		this.setModal(false);
		
		this.setContent(this.initContent());
	}
	
	public AdresseAnlage(Unternehmen u){
		this();
		this.uAlt = u;
	}
	
	private Panel initContent(){
		this.fields = new AdresseFelder();
		this.btnAnlage = new Button("Hinzufügen");
		this.btnAnlage.setIcon(FontAwesome.PLUS);
		this.btnAnlage.addClickListener(click ->{
			Notification message = new Notification("");
			message.setPosition(Position.TOP_CENTER);
			
			if(!fields.areFieldsValid()){
				message.setStyleName(ValoTheme.NOTIFICATION_FAILURE);
				message.setCaption("Füllen Sie die fehlenden Felder aus");
				message.show(Page.getCurrent());
				return;
			}
			try{
				this.aNeu = new Adresse(0, fields.getPlz(), fields.getOrt(), fields.getStrasse(), fields.getHausnummer());
				dbConnection.createAdresse(aNeu);
//				uNeu = new Unternehmen(0, uAlt.getName(), uAlt.getlAnsprechpartner(), uAlt.getlAdresse(), "A");
//				uNeu.getlAdresse().add(aNeu);
//				dbConnection.changeUnternehmen(uAlt, uNeu);
				message.setCaption("Adresse wurde angelegt");
				message.setStyleName(ValoTheme.NOTIFICATION_SUCCESS);
				message.show(Page.getCurrent());
				close();
			}catch(SQLException e){
				aNeu = null;
				e.printStackTrace();
			}
		});
		fields.addComponent(btnAnlage);
		VerticalLayout layout = new VerticalLayout(fields);
		layout.setComponentAlignment(fields, Alignment.TOP_CENTER);
		layout.setMargin(true);
		Panel p = new Panel();
		p.setContent(layout);
		return p;
	}

}