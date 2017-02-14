package com.dhbwProject.unternehmen;

import java.sql.SQLException;

import com.dhbwProject.backend.CCM_Constants;
import com.dhbwProject.backend.dbConnect;
import com.dhbwProject.backend.beans.Adresse;
import com.dhbwProject.backend.beans.Unternehmen;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

public class UnternehmenBearbeitung extends Window{
	private static final long serialVersionUID = 1L;
	
	private Unternehmen uAlt;
	private Unternehmen uNeu;
	
	private Adresse aAlt;
	private Adresse aNeu;

	private VerticalLayout vlLayout;
	
	private dbConnect dbConnection;
	private UnternehmenFelder fieldsUnternehmen;
	private AdresseFelder fieldsAdresse;
	private Button btnBearbeiten;
	
	public UnternehmenBearbeitung(Unternehmen u, Adresse a){
		this.dbConnection = (dbConnect)VaadinSession.getCurrent().getSession().getAttribute(CCM_Constants.SESSION_VALUE_CONNECTION);
		this.center();
		this.setCaptionAsHtml(true);
		this.setCaption("<center><h3>Unternehmen und Adresse bearbeiten</h3></center>");
		this.setClosable(true);
		this.setModal(false);
		this.setWidth("400px");
		this.setHeight("600px");
		
		this.setContent(this.initFields());
		this.uAlt = u;
		this.aAlt = a;
		
		this.fieldsUnternehmen.setName(u.getName());
		this.fieldsUnternehmen.setKennzeichen(u.getKennzeichen());
		
		this.fieldsAdresse.setPlz(a.getPlz());
		this.fieldsAdresse.setStrasse(a.getStrasse());
		this.fieldsAdresse.setHausnummer(a.getHausnummer());
		this.fieldsAdresse.setOrt(a.getOrt());
	}
	
	private Panel initFields(){
		this.fieldsAdresse = new AdresseFelder();
		this.fieldsUnternehmen = new UnternehmenFelder();
		
		
		this.btnBearbeiten = new Button();
		this.btnBearbeiten.setCaption("Bearbeiten");
		this.btnBearbeiten.addClickListener(click ->{
			Notification message = new Notification("");
			message.setPosition(Position.TOP_CENTER);
			if(!this.fieldsUnternehmen.areFieldsValid() && !this.fieldsAdresse.areFieldsValid()){
				message.setCaption("Füllen Sie die fehlenden Felder aus");
				message.setStyleName(ValoTheme.NOTIFICATION_FAILURE);
				message.show(Page.getCurrent());
				return;
			}
			this.uNeu = new Unternehmen(0, fieldsUnternehmen.getName(), fieldsUnternehmen.getKennzeichen());
			this.aNeu = new Adresse(0, fieldsAdresse.getPlz(), fieldsAdresse.getOrt(), fieldsAdresse.getStrasse(), fieldsAdresse.getHausnummer(), uNeu);
			AdresseKollisionsPruefer pruefer = new AdresseKollisionsPruefer(aNeu, this.dbConnection);
			if(pruefer.getKollisionSize()>0){
				pruefer.addCloseListener(close ->{
					if(pruefer.getResult()){
						updateRecord();
						this.close();
					}else{
						message.setCaption("Die Änderungen wurden nicht vorgenommen");
						message.setStyleName(ValoTheme.NOTIFICATION_SUCCESS);
						message.show(Page.getCurrent());
					}
				});
				getUI().addWindow(pruefer);
			}else{
				updateRecord();
				this.close();	
			}
		});
		
		this.fieldsAdresse.addComponent(btnBearbeiten);
		this.vlLayout = new VerticalLayout(fieldsUnternehmen, fieldsAdresse);
		this.vlLayout.setSpacing(true);
		this.vlLayout.setSizeFull();
		this.vlLayout.setMargin(new MarginInfo(true, true, true, true));
		Panel p = new Panel();
		p.setContent(vlLayout);
		return p;
		
	}
	
	public Adresse getAdresseChange(){
		return this.aNeu;
	}
	
	public Unternehmen getUnternehmenChange(){
		return this.uNeu;
	}
	
	private void updateRecord(){
		try {
			uNeu = dbConnection.changeUnternehmen(uAlt, uNeu);
			aNeu = dbConnection.changeAdresse(aAlt, aNeu);
		} catch (SQLException e) {
			uNeu = null;
			aNeu = null;
		}
	}

}
