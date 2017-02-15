package com.dhbwProject.benutzer;

import java.sql.SQLException;
import java.util.LinkedList;

import com.dhbwProject.backend.CCM_Constants;
import com.dhbwProject.backend.dbConnect;
import com.dhbwProject.backend.beans.Adresse;
import com.dhbwProject.backend.beans.Benutzer;
import com.dhbwProject.backend.beans.Studiengang;
import com.dhbwProject.unternehmen.UnternehmenAnlage;
import com.dhbwProject.unternehmen.UnternehmenBearbeitung;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.themes.ValoTheme;

public class BenutzerVerwaltung extends CustomComponent{
	private static final long serialVersionUID = 1L;
	
	private VerticalLayout vl;
	private MenuBar mbMenu;
	private Table tblBenutzer;
	private IndexedContainer container;
	private dbConnect dbConnection;
	
	public BenutzerVerwaltung() {
		this.dbConnection = (dbConnect)VaadinSession.getCurrent().getSession().getAttribute(CCM_Constants.SESSION_VALUE_CONNECTION);
		this.initContent();
		this.setSizeFull();
	}
	
	private void initContent() {
		initMenu();
		this.tblBenutzer = new Table();
		this.tblBenutzer.setHeight("400px");
		this.tblBenutzer.setWidth("100%");
	//	this.tblBenutzer.setSizeFull();
		this.tblBenutzer.setSelectable(true);
	//	this.tblBenutzer.setColumnExpandRatio("Studiengang", 1);
		this.tblBenutzer.setStyleName(ValoTheme.TABLE_BORDERLESS);
		this.tblBenutzer.addStyleName(ValoTheme.TABLE_NO_STRIPES);
		this.tblBenutzer.addStyleName(ValoTheme.TABLE_COMPACT);
		
		this.initContainer();
		this.tblBenutzer.setContainerDataSource(this.container);
		
		this.vl = new VerticalLayout(mbMenu, tblBenutzer);
		this.vl.setMargin(true);
		this.vl.setSizeFull();
		this.setCompositionRoot(this.vl);	
	}
	
	private void initMenu(){
		this.mbMenu = new MenuBar();
		this.mbMenu.setStyleName(ValoTheme.MENUBAR_BORDERLESS);
		Notification message = new Notification("Bitte wählen Sie einen Benutzer");
		message.setPosition(Position.TOP_CENTER);
		message.setStyleName(ValoTheme.NOTIFICATION_FAILURE);
		
		MenuItem itmUnternehmenAnlage = this.mbMenu.addItem("Anlegen",
				FontAwesome.PLUS, new MenuBar.Command() {
					private static final long serialVersionUID = 1L;

					@Override
					public void menuSelected(MenuItem selectedItem) {
						BenutzerAnlage anlage = new BenutzerAnlage();
						anlage.addCloseListener(close ->{
							if(anlage.getBenutzerNeu() == null)
								return;
							else{
								addItem(anlage.getBenutzerNeu());
							}
						});
						getUI().addWindow(anlage);
					}
				});
		
		
		MenuItem itmBenutzerAenderung = this.mbMenu.addItem("Bearbeiten",
				FontAwesome.COGS, new MenuBar.Command() {
					private static final long serialVersionUID = 1L;

					@Override
					public void menuSelected(MenuItem selectedItem) {
						if(tblBenutzer.getValue() == null){
							message.show(Page.getCurrent());
							return;
						}
						
						BenutzerAenderung aenderung = new BenutzerAenderung((Benutzer) tblBenutzer.getValue());
						aenderung.addCloseListener(close ->{
							refreshContainer();							
						});
						getUI().addWindow(aenderung);
					}
				});
	}
	
	private void initContainer() {
		this.container = new IndexedContainer();
		this.container.addContainerProperty("Benutzername", String.class,null);
		this.container.addContainerProperty("Vorname", String.class, null);
		this.container.addContainerProperty("Nachname", String.class, null);
		this.container.addContainerProperty("Beruf", String.class, null);
		this.container.addContainerProperty("Rolle", String.class, null);
		this.container.addContainerProperty("Studiengang", String.class, null);
		this.container.addContainerProperty("E-Mail", String.class, null);
		this.container.addContainerProperty("Telefon", String.class, null);
		refreshContainer();
	}
	
	private void refreshContainer() {
		this.container.removeAllItems();
		this.tblBenutzer.setValue(null);
		try{
			for(Benutzer b : this.dbConnection.getAllBenutzer())
				this.addItem(b);
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	private void addItem(Benutzer b) {
		Item item = this.container.addItem(b);
		item.getItemProperty("Benutzername").setValue(b.getId());
		item.getItemProperty("Vorname").setValue(b.getVorname());
		item.getItemProperty("Nachname").setValue(b.getNachname());
		item.getItemProperty("Beruf").setValue(b.getBeruf().getBezeichnung());
		item.getItemProperty("Rolle").setValue(b.getRolle().getBezeichnung());
		
		String studiengang = "";
		LinkedList<Studiengang> stg = b.getStudiengang();
		for (int j = 0; j < stg.size(); j++) {
			if (j+2 > stg.size()) {
				studiengang = studiengang.concat(b.getStudiengang().get(j).getBezeichnung());
			}
			else {
				studiengang = studiengang.concat(b.getStudiengang().get(j).getBezeichnung() + ", ");
			}
		
		item.getItemProperty("Studiengang").setValue(studiengang);
		item.getItemProperty("E-Mail").setValue(b.getEmail());
		item.getItemProperty("Telefon").setValue(b.getTelefon());
		}
	}

}
