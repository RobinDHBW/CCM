package com.dhbwProject.unternehmen;

import com.dhbwProject.backend.beans.Unternehmen;
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

public class LookupUnternehmen extends Window{
	private static final long serialVersionUID = 1L;
	private Unternehmen result;
	
	
	private VerticalLayout vlFields;
	private VerticalLayout vlLayout;
	private Button btnPick;
	private Grid grdUnternehmen;
	
	public LookupUnternehmen(Unternehmen uReferenz){
		this.result = uReferenz;
		this.initBtnPick();
		this.initGrdUnternehmen();
		this.initVlFields();
		this.initVlLayout();
		
		this.setCaptionAsHtml(true);
		this.setCaption("<center><h2>Bitte wählen Sie ein Unternehmen</h2></center>");
		this.setSizeFull();
		this.setModal(true);
		this.setDraggable(false);
		this.setClosable(true);
		this.setContent(this.vlLayout);
		this.center();
		
	}
	
	private void initGrdUnternehmen(){
		this.grdUnternehmen = new Grid();
		this.grdUnternehmen.setSelectionMode(SelectionMode.SINGLE);
		this.grdUnternehmen.setHeight("450px");
		this.grdUnternehmen.setWidth("450px");
		
		//Dummywerte
		String[] aEnterprise = {"ebm-papst", "Ziehl-Abegg", "Fujitsu", "Toyota", "Sony", "Toyo", "Phyto", "Mercedes Benz", "Pabronko"};
		this.grdUnternehmen.getContainerDataSource().addContainerProperty("Unternehmen", String.class, null);
		this.grdUnternehmen.getContainerDataSource().addContainerProperty("Standort", String.class, "Location");
		
		for(int i = 0; i<aEnterprise.length; i++){
			Item itm = this.grdUnternehmen.getContainerDataSource().addItem(i);
			itm.getItemProperty("Unternehmen").setValue(aEnterprise[i]);
		}
		this.initGridFilter();
	}
	private void initGridFilter(){
		HeaderRow filterRow = this.grdUnternehmen.appendHeaderRow();
		for (Object propId: this.grdUnternehmen.getContainerDataSource().getContainerPropertyIds()) {
		    HeaderCell cell = filterRow.getCell(propId);
		    TextField tfFilter = new TextField();

		    tfFilter.addTextChangeListener(change -> {
		    	((IndexedContainer) this.grdUnternehmen.getContainerDataSource()).removeContainerFilters(propId);
		        if (! change.getText().isEmpty())
		        	((IndexedContainer) this.grdUnternehmen.getContainerDataSource()).addContainerFilter(
		                new SimpleStringFilter(propId,
		                    change.getText(), true, false));
		    });
		    cell.setComponent(tfFilter);
		}
	}
	
	private void initBtnPick(){
		this.btnPick = new Button("Auswählen");
		this.btnPick.setWidth("300px");
		this.btnPick.setIcon(FontAwesome.INBOX);
		this.btnPick.addClickListener(listener ->{
			//befülle hier das referenzierte Unternehmensobjekt
			this.close();
		});
	}
	
	private void initVlFields(){
		this.vlFields = new VerticalLayout();
		this.vlFields.setSizeUndefined();
		this.vlFields.setSpacing(true);
		this.vlFields.setMargin(new MarginInfo(true, false, true, false));
		this.vlFields.addComponent(this.btnPick);
		this.vlFields.addComponent(this.grdUnternehmen);
	}
	
	private void initVlLayout(){
		this.vlLayout = new VerticalLayout();
		this.vlLayout.setSizeFull();
		this.vlLayout.addComponent(this.vlFields);
		this.vlLayout.setComponentAlignment(this.vlFields, Alignment.TOP_CENTER);
	}
	

}
