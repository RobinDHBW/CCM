package com.dhbwProject.termine;

import com.vaadin.data.Item;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class LookupUnternehmen extends Window {
	private static final long serialVersionUID = 1L;
	private int idUnternehmen;
	
	
	private VerticalLayout vlFields;
	private VerticalLayout vlLayout;
	private Grid grdUnternehmen;
	
	public LookupUnternehmen(){
		this.initGrdUnternehmen();
		this.initVlFields();
		this.initVlLayout();
		
		this.setCaption("Bitte wählen Sie ein Unternehmen");
		this.setSizeFull();
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
	}
	
	private void initVlFields(){
		this.vlFields = new VerticalLayout();
		this.vlFields.setSizeUndefined();
		this.vlFields.setSpacing(true);
		this.vlFields.setMargin(new MarginInfo(true, false, true, false));
		this.vlFields.addComponent(this.grdUnternehmen);
	}
	
	private void initVlLayout(){
		this.vlLayout = new VerticalLayout();
		this.vlLayout.setSizeFull();
		this.vlLayout.addComponent(this.vlFields);
		this.vlLayout.setComponentAlignment(this.vlFields, Alignment.MIDDLE_CENTER);
	}
	

}
