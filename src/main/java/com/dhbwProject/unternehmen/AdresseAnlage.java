package com.dhbwProject.unternehmen;

import java.sql.SQLException;
import java.util.LinkedList;

import com.dhbwProject.backend.CCM_Constants;
import com.dhbwProject.backend.dbConnect;
import com.dhbwProject.backend.beans.Adresse;
import com.dhbwProject.backend.beans.Unternehmen;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

public class AdresseAnlage extends Window {
	private static final long serialVersionUID = 1L;
	private Adresse aNeu;
	private Unternehmen uReferenz;
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
		this.uReferenz = u;
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
			AdresseKollisionsPruefer pruefer = new AdresseKollisionsPruefer(
					new Adresse(0, fields.getPlz(), fields.getOrt(), fields.getStrasse(), fields.getHausnummer(), uReferenz), this.dbConnection);
			if(pruefer.getKollisionSize()>0){
				pruefer.addCloseListener(close ->{
					if(pruefer.getResult()){
						createAdresse();
						close();
					}else{
						message.setCaption("Die Adresse wurde nicht erstellt");
						message.setStyleName(ValoTheme.NOTIFICATION_SUCCESS);
						message.show(Page.getCurrent());
					}
						
				});
				getUI().addWindow(pruefer);
			}else{
				createAdresse();
				close();
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
	
	protected Adresse getAdresseNeu(){
		return aNeu;
	}
	
	private void createAdresse(){
		try{
			this.aNeu = new Adresse(0, fields.getPlz(), fields.getOrt(), fields.getStrasse(), fields.getHausnummer(), uReferenz);
			this.aNeu = dbConnection.createAdresse(aNeu);
		}catch(SQLException e){
			aNeu = null;
		}
	}

}
