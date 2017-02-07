package com.dhbwProject.unternehmen;

import java.sql.SQLException;

import com.dhbwProject.backend.CCM_Constants;
import com.dhbwProject.backend.dbConnect;
import com.dhbwProject.backend.beans.Adresse;
import com.dhbwProject.backend.beans.Unternehmen;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Notification;
import com.vaadin.ui.themes.ValoTheme;

public class UnternehmenVerwaltung extends CustomComponent {
	private static final long serialVersionUID = 1L;
	
	private VerticalLayout vlLayout;
	private MenuBar mbMenu;
	private Table tblUnternehmen;
	private IndexedContainer container;
	private dbConnect dbConnection;
	
	public UnternehmenVerwaltung(){
		this.dbConnection = (dbConnect)VaadinSession.getCurrent().getSession().getAttribute(CCM_Constants.SESSION_VALUE_CONNECTION);
		this.initContent();
		this.setSizeFull();
		Responsive.makeResponsive(this);
	}
	
	private void initContent(){
		this.initMenu();
		this.tblUnternehmen = new Table();
		this.tblUnternehmen.setHeight("500px");
		this.tblUnternehmen.setSelectable(true);
		this.tblUnternehmen.setStyleName(ValoTheme.TABLE_BORDERLESS);
		this.tblUnternehmen.addStyleName(ValoTheme.TABLE_NO_STRIPES);
		this.tblUnternehmen.addStyleName(ValoTheme.TABLE_COMPACT);
		Responsive.makeResponsive(tblUnternehmen);
		
		this.initContainer();
		this.tblUnternehmen.setContainerDataSource(this.container);
		this.tblUnternehmen.setColumnHeader("Kennzeichen", "");
		
		this.vlLayout = new VerticalLayout(this.mbMenu, this.tblUnternehmen);
		this.vlLayout.setMargin(true);
		this.vlLayout.setSizeFull();
		Responsive.makeResponsive(vlLayout);
		this.setCompositionRoot(this.vlLayout);	
	}
	
	private void initMenu(){
		this.mbMenu = new MenuBar();
		this.mbMenu.setStyleName(ValoTheme.MENUBAR_BORDERLESS);
		Responsive.makeResponsive(mbMenu);
		Notification message = new Notification("Bitte wählen Sie ein Unternehmen");
		message.setPosition(Position.TOP_CENTER);
		message.setStyleName(ValoTheme.NOTIFICATION_FAILURE);
		
		MenuItem itmUnternehmenAnlage = this.mbMenu.addItem("Unternehmen",
				FontAwesome.PLUS, new MenuBar.Command() {
					private static final long serialVersionUID = 1L;

					@Override
					public void menuSelected(MenuItem selectedItem) {
						UnternehmenAnlage anlage = new UnternehmenAnlage();
						anlage.addCloseListener(close ->{
							if(anlage.getUnternehmenNeu() == null || anlage.getAdresseNeu() == null)
								return;
							else{
								addItem(anlage.getAdresseNeu());
							}
						});
						getUI().addWindow(anlage);
					}
				});
		
		MenuItem itmAdressAnlage = this.mbMenu.addItem("Adresse", FontAwesome.PLUS, new MenuBar.Command() {
			private static final long serialVersionUID = 1L;

			@Override
			public void menuSelected(MenuItem selectedItem) {
				if(tblUnternehmen.getValue() == null){
					message.show(Page.getCurrent());
					return;
				}
				AdresseAnlage anlage = new AdresseAnlage(((Adresse)tblUnternehmen.getValue()).getUnternehmen());
				anlage.addCloseListener(close ->{
					if(anlage.getAdresseNeu() == null)
						return;
					else
						addItem(anlage.getAdresseNeu());
				});
				getUI().addWindow(anlage);
				
			}
		});
		
		MenuItem itmUnternehmenBearbeitung = this.mbMenu.addItem("Bearbeiten",
				FontAwesome.COGS, new MenuBar.Command() {
					private static final long serialVersionUID = 1L;

					@Override
					public void menuSelected(MenuItem selectedItem) {
						if(tblUnternehmen.getValue() == null){
							message.show(Page.getCurrent());
							return;
						}
						
						UnternehmenBearbeitung bearbeitung = new UnternehmenBearbeitung(((Adresse)tblUnternehmen.getValue()).getUnternehmen(), ((Adresse)tblUnternehmen.getValue()));
						bearbeitung.addCloseListener(close ->{
							if(bearbeitung.getAdresseChange() == null || bearbeitung.getUnternehmenChange() == null)
								return;
							refreshContainer();
							
						});
						getUI().addWindow(bearbeitung);
					}
				});
		
		MenuItem itmAnsprechpartner = this.mbMenu.addItem("Ansprechpartner",
				FontAwesome.USER_MD, new MenuBar.Command() {
					private static final long serialVersionUID = 1L;

					@Override
					public void menuSelected(MenuItem selectedItem) {
						if(tblUnternehmen.getValue() == null){
							message.show(Page.getCurrent());
							return;
						}
						
						Unternehmen u = ((Adresse)tblUnternehmen.getValue()).getUnternehmen();
						Adresse a = ((Adresse)tblUnternehmen.getValue()); 
						AnsprechpartnerVerwaltung bearbeitung = new AnsprechpartnerVerwaltung(a);
						getUI().addWindow(bearbeitung);	
					}
				});
	}
	
	private void initContainer(){
		this.container = new IndexedContainer();
		this.container.addContainerProperty("Kennzeichen", Label.class,null);
		this.container.addContainerProperty("Name", String.class, null);
		this.container.addContainerProperty("Adresse", TextArea.class, null);	
		refreshContainer();
	}
	
	private void refreshContainer(){
		this.container.removeAllItems();
		try{
			for(Adresse a : this.dbConnection.getAllAdresse())
				this.addItem(a);
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	private void addItem(Adresse a){
		Item itm = this.container.addItem(a);
		if(a.getUnternehmen().getKennzeichen() != null && a.getUnternehmen().getKennzeichen().equals("A"))
			itm.getItemProperty("Kennzeichen").setValue(new Label(FontAwesome.STAR.getHtml(), ContentMode.HTML));
		itm.getItemProperty("Name").setValue(a.getUnternehmen().getName());
		
		TextArea taAdresse = new TextArea();
		taAdresse.setStyleName(ValoTheme.TEXTAREA_BORDERLESS);
		taAdresse.setHeight("100px");
		taAdresse.setValue(a.getStrasse()+" "+a.getHausnummer()+"\n"+a.getPlz()+"\n"+a.getOrt());
		itm.getItemProperty("Adresse").setValue(taAdresse);
	}	

}
