package com.dhbwProject.benutzer;

import java.sql.SQLException;
import java.util.LinkedList;

import com.dhbwProject.backend.CCM_Constants;
import com.dhbwProject.backend.dbConnect;
import com.dhbwProject.backend.beans.Benutzer;
import com.dhbwProject.backend.beans.Studiengang;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

public class BenutzerAnzeige extends VerticalLayout {
	
	private Table benutzer;
	private dbConnect dbConnect;
	
	
	public BenutzerAnzeige() {
		this.setSpacing(true);
		this.setMargin(new MarginInfo(true, true, true, true));
		this.dbConnect = (dbConnect)VaadinSession.getCurrent().getSession().getAttribute(CCM_Constants.SESSION_VALUE_CONNECTION);
		initFields();
		Responsive.makeResponsive(this);
		
	}
	
	public void initFields() {
		benutzer = new Table();
		benutzer.setSizeFull();
		benutzer.setContainerDataSource(loadTableData());
		addComponent(benutzer);
		Responsive.makeResponsive(benutzer);
		
	}
	public IndexedContainer loadTableData() {
		LinkedList<Benutzer> alleBenutzer = null;
		try {
			alleBenutzer = dbConnect.getAllBenutzer();
		} catch (SQLException e) {
			System.out.println("Fehler bei getAllBenutzer()");
			e.printStackTrace();
		}
		
		IndexedContainer container= new IndexedContainer();
		container.addContainerProperty("Benutzername", String.class, null);
		container.addContainerProperty("Vorname", String.class, null);
		container.addContainerProperty("Nachname", String.class, null);
		container.addContainerProperty("Beruf", String.class, null);
		container.addContainerProperty("Rolle", String.class, null);
		container.addContainerProperty("Studiengang", String.class, null);
		
		for (int i = 0; i<alleBenutzer.size(); i++) {
			Benutzer b = (Benutzer) alleBenutzer.get(i);
			String studiengang = "";
			LinkedList<Studiengang> stg = b.getStudiengang();
			for (int j = 0; j < stg.size(); j++) {
				if (j+2 > stg.size()) {
					studiengang = studiengang.concat(b.getStudiengang().get(j).getBezeichnung());
				}
				else {
					studiengang = studiengang.concat(b.getStudiengang().get(j).getBezeichnung() + ", ");
				}
			}
			Item item = container.addItem(i);
			item.getItemProperty("Benutzername").setValue(b.getId());
			item.getItemProperty("Vorname").setValue(b.getVorname());
			item.getItemProperty("Nachname").setValue(b.getNachname());
			item.getItemProperty("Beruf").setValue(b.getBeruf().getBezeichnung());
			item.getItemProperty("Rolle").setValue(b.getRolle().getBezeichnung());
			item.getItemProperty("Studiengang").setValue(studiengang);
		}
		
		return container;
	}
}
