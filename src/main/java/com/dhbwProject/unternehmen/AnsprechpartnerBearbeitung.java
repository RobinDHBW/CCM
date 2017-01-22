package com.dhbwProject.unternehmen;

import java.sql.SQLException;
import java.util.LinkedList;

import com.dhbwProject.backend.CCM_Constants;
import com.dhbwProject.backend.dbConnect;
import com.dhbwProject.backend.beans.Adresse;
import com.dhbwProject.backend.beans.Ansprechpartner;
import com.dhbwProject.backend.beans.Studiengang;
import com.dhbwProject.backend.beans.Unternehmen;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Notification;
import com.vaadin.ui.themes.ValoTheme;

public class AnsprechpartnerBearbeitung extends Window {
	private static final long serialVersionUID = 1L;
	
	private dbConnect dbConnection;
	private Adresse adresse;
	
	private MenuBar mbMenu;
	private AnsprechpartnerFelder fields;
	private Table tblAnsprechpartner;
	private IndexedContainer container;
	
	public AnsprechpartnerBearbeitung(Adresse adr){
		this.dbConnection = (dbConnect)VaadinSession.getCurrent().getSession().getAttribute(CCM_Constants.SESSION_VALUE_CONNECTION);
		this.setCaptionAsHtml(true);
		this.setCaption("<center><h3>Ansprechpartner verwalten</h3></center>");
		this.center();
		this.setWidth("400px");
		this.setHeight("600px");
		this.adresse = adr;
		this.setContent(this.initFields());
	}
	
	private Panel initFields(){
		this.initMenu();
		this.fields = new AnsprechpartnerFelder();
		
		this.tblAnsprechpartner = new Table();
		this.tblAnsprechpartner.setHeight("150px");
		this.tblAnsprechpartner.setWidth("300px");
		this.initContainer();
		this.tblAnsprechpartner.setContainerDataSource(container);
		this.tblAnsprechpartner.setStyleName(ValoTheme.TABLE_BORDERLESS);
		this.tblAnsprechpartner.addStyleName(ValoTheme.TABLE_NO_STRIPES);
		this.tblAnsprechpartner.addValueChangeListener(valueChange ->{
			if(tblAnsprechpartner.getValue() == null)
				return;
			Ansprechpartner aSelect = (Ansprechpartner)this.tblAnsprechpartner.getValue();
			this.fields.setAdresse(aSelect.getAdresse());
			this.fields.setVorname(aSelect.getVorname());
			this.fields.setNachname(aSelect.getNachname());
			this.fields.setEmail("NVA");
			this.fields.setTelefonnummer("NVA");
		});
		
		VerticalLayout vlLayout = new VerticalLayout(this.mbMenu, tblAnsprechpartner, fields);
		vlLayout.setMargin(new MarginInfo(true, true, true, true));
		vlLayout.setSpacing(true);
		vlLayout.setSizeUndefined();
		
		Panel p = new Panel();
		p.setContent(vlLayout);
		return p;	
	}
	
	private void initMenu(){
		this.mbMenu = new MenuBar();
		Notification message = new Notification("");
		message.setPosition(Position.TOP_CENTER);
		
		MenuItem itmAnlage = this.mbMenu.addItem("Hinzufügen", FontAwesome.PLUS, new MenuBar.Command() {
			
			@Override
			public void menuSelected(MenuItem selectedItem) {
				Window w = new Window();
				w.center();
				w.setCaptionAsHtml(true);
				w.setCaption("<center><h3>Ansprechpartner hinzufügen</h3></center>");
				w.setWidth("400px");
				w.setHeight("500px");
				AnsprechpartnerFelder felder = new AnsprechpartnerFelder(adresse);
				Button btnAnlage = new Button("Hinzufügen");
				btnAnlage.addClickListener(click ->{
					if(!felder.areFieldsValid()){
						message.setCaption("Füllen Sie die fehlenden Felder aus");
						message.setStyleName(ValoTheme.NOTIFICATION_FAILURE);
						message.show(Page.getCurrent());
						return;
					}
						Ansprechpartner aNeu = new Ansprechpartner(0, felder.getVorname(), 
								felder.getNachname(), adresse, null, "", "");
						try{
							addItem(dbConnection.createAnsprechpartner(aNeu));
							message.setCaption(aNeu.getNachname()+", "+aNeu.getVorname()+" erfolgreich angelegt");
							message.setStyleName(ValoTheme.NOTIFICATION_SUCCESS);
							message.show(Page.getCurrent());
							close();
						}catch(SQLException e){
							e.printStackTrace();
						}
					});
				
				felder.addComponent(btnAnlage);
				felder.setComponentAlignment(btnAnlage, Alignment.MIDDLE_CENTER);
				VerticalLayout layout = new VerticalLayout(felder);
				layout.setMargin(true);
				layout.setComponentAlignment(felder, Alignment.TOP_CENTER);
				w.setContent(layout);
				getUI().addWindow(w);
			}
			
		});
		
		
		
		MenuItem itmBearbeiten = this.mbMenu.addItem("Bearbeiten", FontAwesome.COGS, new MenuBar.Command() {
			private static final long serialVersionUID = 1L;

			@Override
			public void menuSelected(MenuItem selectedItem) {
				if(tblAnsprechpartner.getValue() == null){
					message.setStyleName(ValoTheme.NOTIFICATION_FAILURE);
					message.setCaption("Bitte wählen Sie einen Ansprechpartner aus");
					message.show(Page.getCurrent());
					return;
				}
					
				Ansprechpartner aAlt = (Ansprechpartner)tblAnsprechpartner.getValue();
				Ansprechpartner aNeu = new Ansprechpartner(0, fields.getVorname(), 
						fields.getNachname(), fields.getAdresse(), 
						null, "", "");
				try {
					addItem(dbConnection.changeAnsprechpartner(aAlt, aNeu));
					container.removeItem(aAlt);
					message.setStyleName(ValoTheme.NOTIFICATION_SUCCESS);
					message.setCaption(aAlt.getNachname()+", "+aAlt.getVorname()+" wurde verändert");
					message.show(Page.getCurrent());
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private void initContainer(){
		this.container = new IndexedContainer();
		this.container.addContainerProperty("Nachname", String.class, null);
		this.container.addContainerProperty("Vorname", String.class, null);
		refreshContainer();
	}
	private void refreshContainer(){
		this.container.removeAllItems();
		try{
			for(Ansprechpartner a : dbConnection.getAnsprechpartnerByAdresse(adresse))
				addItem(a);
			}catch(SQLException e){
				e.printStackTrace();
			}
	}
	
	private void addItem(Ansprechpartner a){
		Item itm = this.container.addItem(a);
		itm.getItemProperty("Nachname").setValue(a.getNachname());
		itm.getItemProperty("Vorname").setValue(a.getVorname());
	}
	
}
