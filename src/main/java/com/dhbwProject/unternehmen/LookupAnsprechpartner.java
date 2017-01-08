package com.dhbwProject.unternehmen;

import com.dhbwProject.backend.DummyDataManager;
import com.dhbwProject.backend.beans.Adresse;
import com.dhbwProject.backend.beans.Ansprechpartner;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.AbstractSelect.ItemCaptionMode;

public class LookupAnsprechpartner extends Window{
	private static final long serialVersionUID = 1L;

	private VerticalLayout layout;
	private VerticalLayout fields;
	private TextField tfFilterVorname;
	private TextField tfFilterNachname;
	private ListSelect select;
	private Button btnOK;
	private IndexedContainer container;
	private DummyDataManager dummyData;
	private Adresse aReferenz;
	private Ansprechpartner aPSelect;
	
	public LookupAnsprechpartner(Adresse a, DummyDataManager dummyData){
		this.dummyData = dummyData;
		this.aReferenz = a;
		this.initFields();
		
		this.layout = new VerticalLayout(this.fields);
		this.layout.setSizeFull();
		this.layout.setComponentAlignment(this.fields, Alignment.TOP_CENTER);
		
		this.setContent(this.layout);
		this.center();
		this.setWidth("350px");
		this.setHeight("500px");
	}
	
	private void initFields(){
		this.fields = new VerticalLayout();
		this.fields.setSizeUndefined();
		this.fields.setSpacing(true);
		this.fields.setMargin(new MarginInfo(true, true, true, true));
		
		this.select = new ListSelect();
		this.select.setWidth("300px");
		this.initContainer();
		this.select.setContainerDataSource(this.container);
		select.setItemCaptionMode(ItemCaptionMode.ITEM);
		
		this.tfFilterNachname = new TextField();
		this.tfFilterNachname.setInputPrompt("Filter Nachname");
		this.tfFilterNachname.setWidth("300px");
	    this.tfFilterNachname.addTextChangeListener(change -> {
	    	container.removeContainerFilters("nachname");
	        if (! change.getText().isEmpty())
	        	container.addContainerFilter(
	                new SimpleStringFilter("nachname",
	                    change.getText(), true, false));
	    });
	    
	    this.tfFilterVorname = new TextField();
	    this.tfFilterVorname.setInputPrompt("Filter Vorname");
	    this.tfFilterVorname.setWidth("300px");
	    this.tfFilterVorname.addTextChangeListener(change ->{
	    	container.removeContainerFilters("vorname");
	        if (! change.getText().isEmpty())
	        	container.addContainerFilter(
	                new SimpleStringFilter("vorname",
	                    change.getText(), true, false));
	    });
	    
	    this.btnOK = new Button("Auswählen");
	    this.btnOK.setWidth("300px");
	    this.btnOK.setIcon(FontAwesome.UPLOAD);
	    this.btnOK.addClickListener(listener ->{
	    	if(this.select.getValue() != null)
	    		this.aPSelect = this.dummyData.getAnsprechpartner((int)this.select.getValue());
	    	this.close();
	    });
	    
		this.fields.addComponent(this.tfFilterNachname);
		this.fields.setComponentAlignment(this.tfFilterNachname, Alignment.TOP_CENTER);
		this.fields.addComponent(this.tfFilterVorname);
		this.fields.setComponentAlignment(this.tfFilterVorname, Alignment.TOP_CENTER);
		this.fields.addComponent(this.select);
		this.fields.setComponentAlignment(this.select, Alignment.TOP_CENTER);
		this.fields.addComponent(this.btnOK);
		this.fields.setComponentAlignment(this.btnOK, Alignment.TOP_CENTER);
	}
	
	private void initContainer(){
		this.container = new IndexedContainer();
		this.container.addContainerProperty("nachname", String.class, null);
		this.container.addContainerProperty("vorname", String.class, null);
		
		for(Ansprechpartner a : this.dummyData.getAnsprechpartnerList(this.aReferenz)){
			Item itm = this.container.addItem(a.getId());
			itm.getItemProperty("nachname").setValue(a.getNachname());
			itm.getItemProperty("vorname").setValue(a.getVorname());
		}
	}
	
	public Ansprechpartner getAnsprechpartner(){
		return this.aPSelect;
	}

}
