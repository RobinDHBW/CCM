package com.dhbwProject.unternehmen;

import java.sql.SQLException;

import com.dhbwProject.backend.CCM_Constants;
import com.dhbwProject.backend.dbConnect;
import com.dhbwProject.backend.beans.Adresse;
import com.dhbwProject.backend.beans.Unternehmen;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.MenuBar.MenuItem;
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
		this.vlLayout.setSizeFull();
		this.setCompositionRoot(this.vlLayout);	
	}
	
	private void initMenu(){
		this.mbMenu = new MenuBar();
		this.mbMenu.setStyleName(ValoTheme.MENUBAR_BORDERLESS);
		MenuItem itmUnternehmenAnlage = this.mbMenu.addItem("Hinzufügen",
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
		
		MenuItem itmUnternehmenBearbeitung = this.mbMenu.addItem("Bearbeiten",
				FontAwesome.COGS, new MenuBar.Command() {
					private static final long serialVersionUID = 1L;

					@Override
					public void menuSelected(MenuItem selectedItem) {
						UnternehmenBearbeitung bearbeitung = new UnternehmenBearbeitung(((ItemId)tblUnternehmen.getValue()).u);
						getUI().addWindow(bearbeitung);
					}
				});
		
		MenuItem itmAnsprechpartner = this.mbMenu.addItem("Ansprechpartner",
				FontAwesome.USER_MD, new MenuBar.Command() {
					private static final long serialVersionUID = 1L;

					@Override
					public void menuSelected(MenuItem selectedItem) {
						Unternehmen u = ((ItemId)tblUnternehmen.getValue()).u;
						Adresse a = ((ItemId)tblUnternehmen.getValue()).a; 
						AnsprechpartnerBearbeitung bearbeitung = new AnsprechpartnerBearbeitung(u, a);
						getUI().addWindow(bearbeitung);	
					}
				});
	}
	
	private void initContainer(){
		this.container = new IndexedContainer();
		this.container.addContainerProperty("Kennzeichen", FontAwesome.class, null);
		this.container.addContainerProperty("Name", String.class, null);
		this.container.addContainerProperty("Adresse", TextArea.class, null);	
		try{
			for(Unternehmen u : this.dbConnection.getAllUnternehmen()){
				for(Adresse a: u.getlAdresse()){
					Item itm = this.container.addItem(new ItemId(u, a));
					itm.getItemProperty("Kennzeichen").setValue(FontAwesome.CLOSE);
					itm.getItemProperty("Name").setValue(u.getName());
					
					TextArea taAdresse = new TextArea();
					taAdresse.setStyleName(ValoTheme.TEXTAREA_BORDERLESS);
					taAdresse.setHeight("100px");
					taAdresse.setValue(a.getPlz()+"\n"+a.getStrasse()+"\n"+a.getOrt());
					itm.getItemProperty("Adresse").setValue(taAdresse);
				}
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
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
