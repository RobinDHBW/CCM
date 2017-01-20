package com.dhbwProject.besuche;

import java.sql.Date;
import java.text.SimpleDateFormat;

import com.dhbwProject.backend.CCM_Constants;
import com.dhbwProject.backend.dbConnect;
import com.dhbwProject.backend.beans.Benutzer;
import com.dhbwProject.backend.beans.Besuch;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class BesuchUebersicht extends CustomComponent{
	private static final long serialVersionUID = 1L;
	
	private dbConnect dbConnection;
	private Benutzer bUser;
	private Table tblBesuche;
	private IndexedContainer container;
	private VerticalLayout vlLayout;
	
	public BesuchUebersicht(){
		this.dbConnection = (dbConnect)VaadinSession.getCurrent().getSession().getAttribute(CCM_Constants.SESSION_VALUE_CONNECTION);
		this.bUser = (Benutzer)VaadinSession.getCurrent().getSession().getAttribute(CCM_Constants.SESSION_VALUE_USER);
		this.initLayout();
	}
	
	private void initLayout(){
		this.initTable();
		this.vlLayout = new VerticalLayout(tblBesuche);
		this.vlLayout.setMargin(true);
		this.refreshContainer();
		this.setCompositionRoot(vlLayout);
	}
	
	private void initTable(){
		this.tblBesuche = new Table();
		this.initContainer();
		this.tblBesuche.setContainerDataSource(this.container);
		this.tblBesuche.setStyleName(ValoTheme.TABLE_BORDERLESS);
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
	

}
