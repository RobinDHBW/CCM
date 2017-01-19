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
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.themes.ValoTheme;

public class AnsprechpartnerBearbeitung extends Window {
	private static final long serialVersionUID = 1L;
	
	private dbConnect dbConnection;
	private Unternehmen unternehmen;
	private Adresse adresse;
	private LinkedList<Ansprechpartner> lAnsprechpartner = new LinkedList<Ansprechpartner>();
	
	private MenuBar mbMenu;
	private AnsprechpartnerFelder fields;
	private Table tblAnsprechpartner;
	private IndexedContainer container;
	
	public AnsprechpartnerBearbeitung(Unternehmen u, Adresse adr){
		this.setCaptionAsHtml(true);
		this.setCaption("<center><h3>Ansprechpartner verwalten</h3></center>");
		this.center();
		this.setWidth("400px");
		this.setHeight("600px");
		
		this.unternehmen = u;
		this.adresse = adr;
		
		
		/*
		 * TEMPORÄR
		 * */
		this.dbConnection = (dbConnect)VaadinSession.getCurrent().getSession().getAttribute(CCM_Constants.SESSION_VALUE_CONNECTION);
		
		try {
			this.lAnsprechpartner.add(this.dbConnection.getAnsprechpartnerById(1));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//--------------------------------
		
		
		
		
//		for(Ansprechpartner a : u.getlAnsprechpartner())
//			if(a != null)
//				if (a.getAdresse() != null){
//					if(a.getAdresse().equals(adr))
//						this.lAnsprechpartner.add(a);
//				}else
//					System.out.println("NULL--------------------------------");
		this.setContent(this.initFields());
	}
	
	private Panel initFields(){
		this.initMenu();
		this.fields = new AnsprechpartnerFelder();
		
		this.tblAnsprechpartner = new Table();
		this.tblAnsprechpartner.setHeight("150px");
		this.initContainer();
		this.tblAnsprechpartner.setContainerDataSource(container);
		this.tblAnsprechpartner.setStyleName(ValoTheme.TABLE_BORDERLESS);
		this.tblAnsprechpartner.addStyleName(ValoTheme.TABLE_NO_STRIPES);
		this.tblAnsprechpartner.addStyleName(ValoTheme.TABLE_COMPACT);
		this.tblAnsprechpartner.addValueChangeListener(valueChange ->{
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
		MenuItem itmBearbeiten = this.mbMenu.addItem("Bearbeiten", FontAwesome.COGS, new MenuBar.Command() {
			private static final long serialVersionUID = 1L;

			@Override
			public void menuSelected(MenuItem selectedItem) {
				Ansprechpartner aAlt = (Ansprechpartner)tblAnsprechpartner.getValue();
				Ansprechpartner aNeu = new Ansprechpartner(0, fields.getVorname(), 
						fields.getNachname(), fields.getAdresse(), 
						null, "", "");
				try {
					dbConnection.changeAnsprechpartner(aAlt, aNeu);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				/*
				 * (int id, String vorname, String nachname, Adresse adresse,
			LinkedList<Studiengang> lStudiengang, String email, String telefonnummer)*/
			}
		});
	}
	
	private void initContainer(){
		this.container = new IndexedContainer();
		this.container.addContainerProperty("Nachname", String.class, null);
		this.container.addContainerProperty("Vorname", String.class, null);
		
		for(Ansprechpartner a : this.lAnsprechpartner){
			Item itm = this.container.addItem(a);
			itm.getItemProperty("Nachname").setValue(a.getNachname());
			itm.getItemProperty("Vorname").setValue(a.getVorname());
		}
	}
	
}
