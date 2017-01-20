package com.dhbwProject.besuche;

import java.sql.Date;
import java.text.SimpleDateFormat;

import com.dhbwProject.backend.CCM_Constants;
import com.dhbwProject.backend.dbConnect;
import com.dhbwProject.backend.beans.Benutzer;
import com.dhbwProject.backend.beans.Besuch;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.Position;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

public class BesuchUebersicht extends CustomComponent{
	private static final long serialVersionUID = 1L;
	
	private dbConnect dbConnection;
	private Benutzer bUser;
	private MenuBar mbMenu;
	private Table tblBesuche;
	private IndexedContainer container;
	private VerticalLayout vlLayout;
	
	public BesuchUebersicht(){
		this.dbConnection = (dbConnect)VaadinSession.getCurrent().getSession().getAttribute(CCM_Constants.SESSION_VALUE_CONNECTION);
		this.bUser = (Benutzer)VaadinSession.getCurrent().getSession().getAttribute(CCM_Constants.SESSION_VALUE_USER);
		this.initLayout();
	}
	
	private void initLayout(){
		this.initMenu();
		this.initTable();
		this.vlLayout = new VerticalLayout(mbMenu, tblBesuche);
		this.vlLayout.setMargin(true);
		this.refreshContainer();
		this.setCompositionRoot(vlLayout);
	}
	
	private void initTable(){
		this.tblBesuche = new Table();
		this.initContainer();
		this.tblBesuche.setContainerDataSource(this.container);
		this.tblBesuche.setStyleName(ValoTheme.TABLE_BORDERLESS);
		this.tblBesuche.setSelectable(true);
	}
	
	private void initContainer(){
		this.container = new IndexedContainer();
		this.container.addContainerProperty("Titel", String.class, null);
		this.container.addContainerProperty("Start", String.class, null);
		this.container.addContainerProperty("Ende", String.class, null);
		this.container.addContainerProperty("Unternehmen", String.class, null);
		this.container.addContainerProperty("Adresse", TextArea.class, null);
	}
	
	private void refreshContainer(){
		this.container.removeAllItems();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy, HH:mm");
		try{
			for(Besuch b : this.dbConnection.getBesuchByBenutzer(this.bUser)){
				Item itm = this.container.addItem(b);
				itm.getItemProperty("Titel").setValue(b.getName());
				itm.getItemProperty("Start").setValue(dateFormat.format(b.getStartDate()));
				itm.getItemProperty("Ende").setValue(dateFormat.format(b.getEndDate()));
				
				//ITERATIV IST DAS HIER ECHT SCHLECHT-----------------------------------------------
				itm.getItemProperty("Unternehmen").setValue(dbConnection.getUnternehmenByAdresse(b.getAdresse()).getName());
				//----------------------------------------------------------------------------------
				
				TextArea taAdresse = new TextArea();
				taAdresse.setHeight("100px");
				taAdresse.setStyleName(ValoTheme.TEXTAREA_BORDERLESS);
				taAdresse.setValue(b.getAdresse().getPlz()+"\n"+
				b.getAdresse().getStrasse()+
				"\n"+b.getAdresse().getOrt());
				
				itm.getItemProperty("Adresse").setValue(taAdresse);	
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private void initMenu(){
		this.mbMenu = new MenuBar();
		this.mbMenu.setStyleName(ValoTheme.MENUBAR_BORDERLESS);
		
		
		
		MenuItem itmAdd = mbMenu.addItem("Hinzufügen", FontAwesome.PLUS, new MenuBar.Command() {
			private static final long serialVersionUID = 1L;

			@Override
			public void menuSelected(MenuItem selectedItem) {
				BesuchAnlage anlage = new BesuchAnlage();
				getUI().addWindow(anlage);
			}
		});
		
		MenuItem itmChange = mbMenu.addItem("Bearbeiten", FontAwesome.COGS, new MenuBar.Command() {
			
			@Override
			public void menuSelected(MenuItem selectedItem) {
				Notification message = new Notification("");
				message.setPosition(Position.TOP_CENTER);
				if(tblBesuche.getValue() == null){
					message.setStyleName(ValoTheme.NOTIFICATION_FAILURE);
					message.setCaption("Wählen Sie zunächst einen Termin");
					message.show(Page.getCurrent());
					return;
				}
				Besuch b = (Besuch)tblBesuche.getValue();
				BesuchBearbeitung bearbeitung = new BesuchBearbeitung(b);
				getUI().addWindow(bearbeitung);	
			}
		});
		
		MenuItem itmRemove = mbMenu.addItem("Entfernen", FontAwesome.TRASH, new MenuBar.Command(){
			private static final long serialVersionUID = 1L;

			@Override
			public void menuSelected(MenuItem selectedItem) {
				Notification message = new Notification("");
				message.setPosition(Position.TOP_CENTER);
				if(tblBesuche.getValue() == null){
					message.setStyleName(ValoTheme.NOTIFICATION_FAILURE);
					message.setCaption("Wählen Sie zunächst einen Termin");
					message.show(Page.getCurrent());
					return;
				}
				Besuch b = (Besuch)tblBesuche.getValue();
				BesuchEntfernen entfernen = new BesuchEntfernen(b.getName());
				entfernen.addCloseListener(close ->{
					if(entfernen.getResult()){
						//remove ....
						message.setStyleName(ValoTheme.NOTIFICATION_SUCCESS);
						message.setCaption("Der Termin: "+b.getName()+"wurde erfolgreich entfernt");
						message.show(Page.getCurrent());
					}else{
						message.setStyleName(ValoTheme.NOTIFICATION_SUCCESS);
						message.setCaption("Der Termin: "+b.getName()+"wurde nicht entfernt");
						message.show(Page.getCurrent());
					}
				});
				getUI().addWindow(entfernen);
			}
		});
	}
	

}
