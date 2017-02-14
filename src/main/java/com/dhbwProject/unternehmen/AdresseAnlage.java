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
			
			LinkedList<Adresse> lKollision = checkAdresseKollision();
			if(lKollision.size()>0){
				KollisionAbfrage abfrage = new KollisionAbfrage(lKollision);
				abfrage.addCloseListener(close ->{
					if(abfrage.getResult()){
						createAdresse();
						close();
					}else{
						message.setCaption("Die Adresse wurde nicht erstellt");
						message.setStyleName(ValoTheme.NOTIFICATION_SUCCESS);
						message.show(Page.getCurrent());
					}
						
				});
				getUI().addWindow(abfrage);
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
	
	private LinkedList<Adresse> checkAdresseKollision(){
		LinkedList<Adresse> lKollisionReferenz = new LinkedList<Adresse>();
		try {
			for(Adresse a : this.dbConnection.getAllAdresse())
				if(isKollision(a))
					lKollisionReferenz.add(a);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lKollisionReferenz;
	}
	
	private boolean isKollision(Adresse a){
		if(a.getPlz().equals(this.fields.getPlz())
				&&a.getOrt().equals(this.fields.getOrt())
				&& a.getStrasse().equals(this.fields.getStrasse()))
			return true;
		return false;
			
	}
	
	private void createAdresse(){
		try{
			this.aNeu = new Adresse(0, fields.getPlz(), fields.getOrt(), fields.getStrasse(), fields.getHausnummer(), uReferenz);
			this.aNeu = dbConnection.createAdresse(aNeu);
		}catch(SQLException e){
			aNeu = null;
		}
	}
	
	private class KollisionAbfrage extends Window{
		private boolean bResult = false;
		
		private KollisionAbfrage(LinkedList<Adresse> lAdresse){
			this.center();
			this.setModal(true);
			this.setClosable(false);
			this.setWidth("400px");
			this.setHeight("600px");
			this.setCaptionAsHtml(true);
			this.setCaption("<center><h3>Die Adresse könnte bereits existieren!</h3></center>");
			this.setContent(initContent(lAdresse));
		}
		
		private Panel initContent(LinkedList<Adresse> lAdresse){
			VerticalLayout layout = new VerticalLayout();
			layout.setSizeFull();
			layout.setSpacing(true);
			for(Adresse a : lAdresse){
				Label lblAdresse = new Label(a.getStrasse()+" "+a.getHausnummer()+"<br>"
						+a.getPlz()+"<br>"+a.getOrt(), ContentMode.HTML);
				layout.addComponent(lblAdresse);
				layout.setComponentAlignment(lblAdresse, Alignment.TOP_CENTER);
			}
			
			Button btnOk = new Button();
			btnOk.setCaption("Ja");
			btnOk.setWidth("100px");
			btnOk.setIcon(FontAwesome.CHECK);
			btnOk.addClickListener(click ->{
				this.bResult = true;
				this.close();
			});
			
			Button btnNein = new Button();
			btnNein.setCaption("Nein");
			btnNein.setWidth("100px");
			btnNein.setIcon(FontAwesome.CLOSE);
			btnNein.addClickListener(click -> this.close());
			
			HorizontalLayout hlButtons = new HorizontalLayout(btnOk, btnNein);
			hlButtons.setSpacing(true);
			layout.addComponent(hlButtons);
			layout.setComponentAlignment(hlButtons, Alignment.TOP_CENTER);
			layout.setMargin(true);
			return new Panel(layout);		
		}
		
		private boolean getResult(){
			return bResult;
		}
	}

}
