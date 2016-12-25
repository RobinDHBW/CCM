package com.dhbwProject.benutzer;

import java.util.ArrayList;

import com.dhbwProject.backend.beans.Benutzer;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Grid.HeaderCell;
import com.vaadin.ui.Grid.HeaderRow;
import com.vaadin.ui.Grid.SelectionMode;

public class LookupBenutzer extends Window {
	private static final long serialVersionUID = 1L;
	private ArrayList<Benutzer> alUser;
	
	private VerticalLayout vlFields;
	private VerticalLayout vlLayout;
	
	private Button btnPick;
	private Grid grdUser;
	
	public LookupBenutzer(ArrayList<Benutzer> alUser){
		this.alUser = alUser;
		this.initBtnPick();
		this.initGrdUser();
		this.initVlFields();
		this.initVlLayout();
		
		this.setCaptionAsHtml(true);
		this.setCaption("<center><h2>Bitte wählen Sie Ihre Teilnemer</h2></center>");
		this.setSizeFull();
		this.setModal(true);
		this.setDraggable(false);
		this.setClosable(true);
		this.setContent(this.vlLayout);
		this.center();
	}
	
	private void initBtnPick(){
		this.btnPick = new Button("Auswählen");
		this.btnPick.setWidth("300px");
		this.btnPick.setIcon(FontAwesome.UPLOAD);
		
		/*	Hier soll abschließend die Benutzrliste befüllt werden
		 * 	Aktuell werden Dummy-Beans erzeugt
		 * */
		this.btnPick.addClickListener(listener ->{
			for(Object o : this.grdUser.getSelectedRows()){
				Benutzer b = new Benutzer(this.grdUser.getContainerDataSource().getItem(o).toString(),
						(String)this.grdUser.getContainerDataSource().getItem(o).getItemProperty("Vorname").getValue(),
						(String)this.grdUser.getContainerDataSource().getItem(o).getItemProperty("Nachname").getValue(),
						null, null);
				this.alUser.add(b);
			}
			this.close();
		});
	}
	private void initGrdUser(){
		this.grdUser = new Grid();
		this.grdUser.setSelectionMode(SelectionMode.MULTI);
		this.grdUser.setHeight("450px");
		this.grdUser.setWidth("450px");
		
		/*	Der Datencontainer soll bei der Initialisierung mit befüllt werden
		 * 	Im Regelfall sollte das Lookup einmalig geöffnet werden und nicht mehrfach
		 *	... Hier eine Dummy-Befüllung
		 * */
		String[] aVorname = {"Albert", "Herbert", "Yoshi", "Sakura", "Robin", "Simon", "Bosse", "Jasmin", "Florian", "Manuel", "Christian"};
		String[] aNachname = {"Terbun", "Remus", "Suzuki", "Shizuki", "Bahr", "Schlarb", "Bosse", "Stribik", "Flurer", "Manu", "Zaengle"};
		
		this.grdUser.getContainerDataSource().addContainerProperty("Vorname", String.class, null);
		this.grdUser.getContainerDataSource().addContainerProperty("Nachname", String.class, null);
		
		for(int i = 0; i<aVorname.length; i++){
			Item itm = this.grdUser.getContainerDataSource().addItem(i);
			itm.getItemProperty("Vorname").setValue(aVorname[i]);
			itm.getItemProperty("Nachname").setValue(aNachname[i]);
		}
		this.initGridFilter();
	}
	
	private void initGridFilter(){
		HeaderRow filterRow = this.grdUser.appendHeaderRow();
		for (Object propId: this.grdUser.getContainerDataSource().getContainerPropertyIds()) {
		    HeaderCell cell = filterRow.getCell(propId);
		    TextField tfFilter = new TextField();

		    tfFilter.addTextChangeListener(change -> {
		    	((IndexedContainer) this.grdUser.getContainerDataSource()).removeContainerFilters(propId);
		        if (! change.getText().isEmpty())
		        	((IndexedContainer) this.grdUser.getContainerDataSource()).addContainerFilter(
		                new SimpleStringFilter(propId,
		                    change.getText(), true, false));
		    });
		    cell.setComponent(tfFilter);
		}
	}
	
	private void initVlFields(){
		this.vlFields = new VerticalLayout();
		this.vlFields.setSizeUndefined();
		this.vlFields.setSpacing(true);
		this.vlFields.setMargin(new MarginInfo(true, false, true, false));
		this.vlFields.addComponent(this.btnPick);
		this.vlFields.addComponent(this.grdUser);
	}
	
	private void initVlLayout(){
		this.vlLayout = new VerticalLayout();
		this.vlLayout.setSizeFull();
		this.vlLayout.addComponent(this.vlFields);
		this.vlLayout.setComponentAlignment(this.vlFields, Alignment.TOP_CENTER);
	}

}
