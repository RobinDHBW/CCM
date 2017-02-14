package com.dhbwProject.unternehmen;

import java.sql.SQLException;

import com.dhbwProject.backend.CCM_Constants;
import com.dhbwProject.backend.dbConnect;
import com.dhbwProject.backend.beans.Adresse;
import com.dhbwProject.backend.beans.Ansprechpartner;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.Position;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Notification;
import com.vaadin.ui.themes.ValoTheme;

public class AnsprechpartnerVerwaltung extends Window {
	private static final long serialVersionUID = 1L;
	
	private dbConnect dbConnection;
	private Adresse adresse;
	
	private MenuBar mbMenu;
	private Table tblAnsprechpartner;
	private IndexedContainer container;
	
	public AnsprechpartnerVerwaltung(Adresse adr){
		this.dbConnection = (dbConnect)VaadinSession.getCurrent().getSession().getAttribute(CCM_Constants.SESSION_VALUE_CONNECTION);
		this.setCaptionAsHtml(true);
		this.setCaption("<center><h3>Ansprechpartner verwalten</h3></center>");
		this.center();
//		this.setWidth("600px");
//		this.setHeight("400px");
		this.setStyleName("pwwindow");
		this.adresse = adr;
		this.setContent(this.initFields());
		Responsive.makeResponsive(this);
	}
	
	private Panel initFields(){
		this.initMenu();
		
		this.tblAnsprechpartner = new Table();
		this.tblAnsprechpartner.setHeight("100%");
		this.tblAnsprechpartner.setWidth("100%");
		this.tblAnsprechpartner.setSelectable(true);
		this.initContainer();
		this.tblAnsprechpartner.setContainerDataSource(container);
		this.tblAnsprechpartner.setStyleName(ValoTheme.TABLE_BORDERLESS);
		this.tblAnsprechpartner.addStyleName(ValoTheme.TABLE_NO_STRIPES);
		
		VerticalLayout vlLayout = new VerticalLayout(this.mbMenu, tblAnsprechpartner);
		vlLayout.setMargin(true);
		vlLayout.setSpacing(true);
		Panel p = new Panel();
		p.setContent(vlLayout);
		Responsive.makeResponsive(vlLayout);
		Responsive.makeResponsive(tblAnsprechpartner);
		Responsive.makeResponsive(p);
		return p;
	}
	
	private void initMenu(){
		this.mbMenu = new MenuBar();
		this.mbMenu.setStyleName(ValoTheme.MENUBAR_BORDERLESS);
		Responsive.makeResponsive(mbMenu);
		
		Notification message = new Notification("");
		message.setPosition(Position.TOP_CENTER);
		
		MenuItem itmAnlage = this.mbMenu.addItem("Hinzufügen", FontAwesome.PLUS, new MenuBar.Command() {
			
			@Override
			public void menuSelected(MenuItem selectedItem) {
				AnsprechpartnerAnlage anlage = new AnsprechpartnerAnlage(adresse);
				anlage.addCloseListener(close ->{
					if(anlage.getAnsprechpartnerNeu() == null)
						return;
					else
						addItem(anlage.getAnsprechpartnerNeu());
				});
				getUI().addWindow(anlage);
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
				AnsprechpartnerBearbeitung bearbeitung = new AnsprechpartnerBearbeitung(aAlt, adresse);
				bearbeitung.addCloseListener(close ->{
					if(bearbeitung.getAnsprechpartnerNeu() == null)
						return;
					else{
						container.removeItem(aAlt);
						addItem(bearbeitung.getAnsprechpartnerNeu());
					}
				});
				getUI().addWindow(bearbeitung);
			}
		});
	}
	
	private void initContainer(){
		this.container = new IndexedContainer();
		this.container.addContainerProperty("Nachname", String.class, null);
		this.container.addContainerProperty("Vorname", String.class, null);
		this.container.addContainerProperty("e-Mail", String.class, null);
		this.container.addContainerProperty("Tel.", String.class, null);
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
		itm.getItemProperty("e-Mail").setValue(a.getEmailadresse());
		itm.getItemProperty("Tel.").setValue(a.getTelefonnummer());
	}
	
}

