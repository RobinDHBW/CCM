package com.dhbwProject.unternehmen;

import java.sql.SQLException;

import com.dhbwProject.backend.CCM_Constants;
import com.dhbwProject.backend.dbConnect;
import com.dhbwProject.backend.beans.Adresse;
import com.dhbwProject.backend.beans.Ansprechpartner;
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

public class AnsprechpartnerAnlage extends Window {
	private static final long serialVersionUID = 1L;
	
	private dbConnect dbConnection;
	private Adresse aReferenz;
	private Ansprechpartner aNeu;
	
	private AnsprechpartnerFelder fields;
	private Button btnAnlage;
	
	public AnsprechpartnerAnlage(Adresse a){
		this.dbConnection = (dbConnect)VaadinSession.getCurrent().getSession().getAttribute(CCM_Constants.SESSION_VALUE_CONNECTION);
		this.aReferenz = a;
		this.center();
		this.setWidth("350px");
		this.setHeight("500px");
		this.setClosable(true);
		this.setModal(false);
		this.setCaptionAsHtml(true);
		this.setCaption("<center><h3>Ansprechpartner anlegen</h3></center>");
		this.setContent(this.initContent());
	}
	
	private Panel initContent(){
		this.fields = new AnsprechpartnerFelder();
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
				aNeu = new Ansprechpartner(0, fields.getVorname(), 
						fields.getNachname(), aReferenz, null, fields.getEmail(), fields.getTelefonnummer());
				aNeu = dbConnection.createAnsprechpartner(aNeu);
				message.setStyleName(ValoTheme.NOTIFICATION_SUCCESS);
				message.setCaption(aNeu.getNachname()+", "+aNeu.getVorname()+" wurde hinzugefügt");
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
		return new Panel(layout);	
	}
	
	protected Ansprechpartner getAnsprechpartnerNeu(){
		return this.aNeu;
	}

}
