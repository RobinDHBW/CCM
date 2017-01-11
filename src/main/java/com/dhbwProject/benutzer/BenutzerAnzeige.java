package com.dhbwProject.benutzer;

import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

public class BenutzerAnzeige extends VerticalLayout {
	
	Table benutzer;
	
	public BenutzerAnzeige() {
		this.setSpacing(true);
		this.setMargin(new MarginInfo(true, true, true, true));
		initFields();
	}
	
	public void initFields() {
		benutzer = new Table();
		benutzer.setSizeFull();
		benutzer.setContainerDataSource(loadTableData());
		addComponent(benutzer);
		
	}
	public IndexedContainer loadTableData() {
		IndexedContainer container= new IndexedContainer();
		container.addContainerProperty("Vorname", String.class, null);
		container.addContainerProperty("Nachname", String.class, null);
		
		//Dummywerte
		String[] aVorname = {"Albert", "Herbert", "Yoshi", "Sakura", "Robin", "Simon", "Bosse", "Jasmin", "Florian", "Manuel", "Christian"};
		String[] aNachname = {"Terbun", "Remus", "Suzuki", "Shizuki", "Bahr", "Schlarb", "Bosse", "Stribik", "Flurer", "Manu", "Zaengle"};		
		for(int i = 0; i<aVorname.length; i++){
			Item itm = container.addItem(i);
			itm.getItemProperty("Vorname").setValue(aVorname[i]);
			itm.getItemProperty("Nachname").setValue(aNachname[i]);
		}
		return container;
	}
}
