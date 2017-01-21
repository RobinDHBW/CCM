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
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.Position;
import com.vaadin.ui.CustomComponent;
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
	}
	
	private void initContent(){
		this.initMenu();
		this.tblUnternehmen = new Table();
		this.tblUnternehmen.setSelectable(true);
		this.tblUnternehmen.setStyleName(ValoTheme.TABLE_BORDERLESS);
		this.tblUnternehmen.addStyleName(ValoTheme.TABLE_NO_STRIPES);
		this.tblUnternehmen.addStyleName(ValoTheme.TABLE_COMPACT);
		
		this.initContainer();
		this.tblUnternehmen.setContainerDataSource(this.container);
		
		this.vlLayout = new VerticalLayout(this.mbMenu, this.tblUnternehmen);
		this.vlLayout.setMargin(true);
		this.vlLayout.setSizeFull();
		this.setCompositionRoot(this.vlLayout);	
	}
	
	private void initMenu(){
		this.mbMenu = new MenuBar();
		this.mbMenu.setStyleName(ValoTheme.MENUBAR_BORDERLESS);
		Notification message = new Notification("Bitte wählen Sie ein Unternehmen");
		message.setPosition(Position.TOP_CENTER);
		message.setStyleName(ValoTheme.NOTIFICATION_FAILURE);
		MenuItem itmUnternehmenAnlage = this.mbMenu.addItem("Unternehmen",
				FontAwesome.PLUS, new MenuBar.Command() {
					private static final long serialVersionUID = 1L;

					@Override
					public void menuSelected(MenuItem selectedItem) {
						Window w = new Window();
						w.setContent(new UnternehmenAnlage());
						w.center();
						w.setWidth("400px");
						w.setHeight("600px");
						w.setClosable(true);
						w.setModal(false);
						getUI().addWindow(w);
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
				AdresseAnlage anlage = new AdresseAnlage(((ItemId)tblUnternehmen.getValue()).u);
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
						
						UnternehmenBearbeitung bearbeitung = new UnternehmenBearbeitung(((ItemId)tblUnternehmen.getValue()).u, ((ItemId)tblUnternehmen.getValue()).a);
						bearbeitung.addCloseListener(close ->{
							if(bearbeitung.getAdresseChange() == null || bearbeitung.getUnternehmenChange() == null)
								return;
//							container.removeItem((ItemId)tblUnternehmen.getValue());
//							addItem(bearbeitung.getUnternehmenChange(), bearbeitung.getAdresseChange());
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
						
						Unternehmen u = ((ItemId)tblUnternehmen.getValue()).u;
						Adresse a = ((ItemId)tblUnternehmen.getValue()).a; 
						AnsprechpartnerBearbeitung bearbeitung = new AnsprechpartnerBearbeitung(a);
						getUI().addWindow(bearbeitung);	
					}
				});
	}
	
	private void initContainer(){
		this.container = new IndexedContainer();
		this.container.addContainerProperty("Kennzeichen", TextField.class, null);
		this.container.addContainerProperty("Name", String.class, null);
		this.container.addContainerProperty("Adresse", TextArea.class, null);	
		refreshContainer();
	}
	
	private void refreshContainer(){
		this.container.removeAllItems();
		try{
			for(Unternehmen u : this.dbConnection.getAllUnternehmen()){
				for(Adresse a: u.getlAdresse()){
					this.addItem(u, a);
				}
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	private void addItem(Unternehmen u, Adresse a){
		Item itm = this.container.addItem(new ItemId(u, a));
		TextField tfKennzeichen = new TextField();
		tfKennzeichen.setIcon(FontAwesome.CLOSE);
		tfKennzeichen.setValue(null);
		tfKennzeichen.setNullRepresentation("");
		tfKennzeichen.setWidth("40px");
		itm.getItemProperty("Kennzeichen").setValue(tfKennzeichen);
		itm.getItemProperty("Name").setValue(u.getName());
		
		TextArea taAdresse = new TextArea();
		taAdresse.setStyleName(ValoTheme.TEXTAREA_BORDERLESS);
		taAdresse.setHeight("100px");
		taAdresse.setValue(a.getPlz()+"\n"+a.getStrasse()+"\n"+a.getOrt());
		itm.getItemProperty("Adresse").setValue(taAdresse);
	}
	
	private class ItemId{
		private Unternehmen u;
		private Adresse a;
		
		private ItemId(Unternehmen u, Adresse a){
			this.u = u;
			this.a = a;
		}
	}
	
	

}
