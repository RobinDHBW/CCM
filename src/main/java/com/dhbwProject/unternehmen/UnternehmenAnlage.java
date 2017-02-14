package com.dhbwProject.unternehmen;

import java.sql.SQLException;

import com.dhbwProject.backend.CCM_Constants;
import com.dhbwProject.backend.dbConnect;
import com.dhbwProject.backend.beans.Adresse;
import com.dhbwProject.backend.beans.Unternehmen;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.Position;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

public class UnternehmenAnlage extends Window {
	private static final long serialVersionUID = 1L; // dass net gelb unterstreicht
	private Unternehmen uNeu;
	private Adresse aNeu;
	private dbConnect dbConnection;
	
	private UnternehmenFelder fieldsUnternehmen;
	private AdresseFelder fieldsAdresse;
	private Button btnErstellen;
	
	private VerticalLayout vlLayout;
	
	public UnternehmenAnlage(){
		this.dbConnection = (dbConnect)VaadinSession.getCurrent().getSession().getAttribute(CCM_Constants.SESSION_VALUE_CONNECTION);
		
		this.center();
//		this.setWidth("350px");
//		this.setHeight("500px");
		this.setStyleName("pwwindow");
		this.setCaptionAsHtml(true);
		this.setCaption("<center><h3>Unternehmen hinzufügen</h3></center>");
		this.setContent(this.initContent());
		Responsive.makeResponsive(this);
		
	}
	
	private Panel initContent(){
		this.btnErstellen = new Button("Hinzufügen");
		this.btnErstellen.setIcon(FontAwesome.PLUS);
		Responsive.makeResponsive(btnErstellen);
		this.btnErstellen.addClickListener(click ->{
			Notification message = new Notification("");
			message.setPosition(Position.TOP_CENTER);
			if(!this.fieldsUnternehmen.areFieldsValid() || !this.fieldsAdresse.areFieldsValid()){
				message.setStyleName(ValoTheme.NOTIFICATION_FAILURE);
				message.setCaption("Füllen Sie die fehlenden Felder aus");
				message.show(Page.getCurrent());
				return;
			}
			
			try{
				this.uNeu = new Unternehmen(0, fieldsUnternehmen.getName(), fieldsUnternehmen.getKennzeichen());
				this.uNeu = dbConnection.createUnternehmen(uNeu);
				this.aNeu = new Adresse(0, fieldsAdresse.getPlz(), fieldsAdresse.getOrt(), fieldsAdresse.getStrasse(), fieldsAdresse.getHausnummer(), uNeu);
				this.aNeu = dbConnection.createAdresse(aNeu);
				message.setStyleName(ValoTheme.NOTIFICATION_SUCCESS);
				message.setCaption(uNeu.getName()+" wurde hinzugefügt");
				message.show(Page.getCurrent());
				this.close();
			}catch(SQLException e){
				this.uNeu = null;
				this.aNeu = null;
				e.printStackTrace();
			}
		});
		
		fieldsUnternehmen = new UnternehmenFelder();
		fieldsAdresse = new AdresseFelder();
		fieldsAdresse.addComponent(btnErstellen);
		
		VerticalLayout layout = new VerticalLayout(fieldsUnternehmen, fieldsAdresse);
		layout.setSpacing(true);
		layout.setMargin(true);
		
		Panel p = new Panel();
		p.setContent(layout);
		Responsive.makeResponsive(p);
		Responsive.makeResponsive(layout);
		return p;
		
	}
	
	protected Unternehmen getUnternehmenNeu(){
		return this.uNeu;
	}
	
	protected Adresse getAdresseNeu(){
		return this.aNeu;
	}

}

