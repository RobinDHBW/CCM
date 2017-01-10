package com.dhbwProject.benutzer;

import java.util.LinkedList;
import java.util.Set;

import com.dhbwProject.backend.DummyDataManager;
import com.dhbwProject.backend.beans.Benutzer;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.AbstractSelect.ItemCaptionMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class LookupBenutzer extends Window{
	private static final long serialVersionUID = 1L;
	
	private VerticalLayout layout;
	private VerticalLayout fields;
	private TextField tfFilterNachname;
	private TextField tfFilterVorname;
	private ListSelect select;
	private IndexedContainer container;
	private Button btnOk;
	private LinkedList<Benutzer> lBenutzerSelection;
	private DummyDataManager dummyData;
	private Benutzer bSelection;
	
	public LookupBenutzer(DummyDataManager dummyData){
		this.dummyData = dummyData;
		this.initFields();
		
		this.layout = new VerticalLayout(this.fields);
		this.layout.setSizeFull();
		this.layout.setComponentAlignment(this.fields, Alignment.MIDDLE_CENTER);
		
		this.setContent(this.layout);
		this.center();
		this.setWidth("350px");
		this.setHeight("500px");
	}
	
	public LookupBenutzer(DummyDataManager dummyData, LinkedList<Benutzer> benutzerSelection){
		this(dummyData);
		this.lBenutzerSelection = benutzerSelection;
		this.select.setMultiSelect(true);
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
	    
	    this.btnOk = new Button("Auswählen");
	    this.btnOk.setWidth("300px");
	    this.btnOk.setIcon(FontAwesome.UPLOAD);	    
	    this.btnOk.addClickListener(click ->{
	    	if(select.isMultiSelect()){
	    		Set <Item>values=(Set<Item>) this.select.getValue();
	    		for(Object o : values)
	    			this.lBenutzerSelection.add(this.dummyData.getBenutzer(o));
	    	}else
	    		this.bSelection = this.dummyData.getBenutzer(this.select.getValue());
	    	this.close();	
	    });
	    
		this.fields.addComponent(this.tfFilterNachname);
		this.fields.setComponentAlignment(this.tfFilterNachname, Alignment.TOP_CENTER);
		this.fields.addComponent(this.tfFilterVorname);
		this.fields.setComponentAlignment(this.tfFilterVorname, Alignment.TOP_CENTER);
		this.fields.addComponent(this.select);
		this.fields.setComponentAlignment(this.select, Alignment.TOP_CENTER);
		this.fields.addComponent(this.btnOk);
		this.fields.setComponentAlignment(this.btnOk, Alignment.TOP_CENTER);
		
	}

	private void initContainer(){
		this.container = new IndexedContainer();
		this.container.addContainerProperty("nachname", String.class, null);
		this.container.addContainerProperty("vorname", String.class, null);
		for(Benutzer b : this.dummyData.getlBenutzer()){
			Item itm = container.addItem(b.getId());
			itm.getItemProperty("nachname").setValue(b.getNachname()+",");
			itm.getItemProperty("vorname").setValue(b.getVorname());
		}
	}
	
	public Benutzer getSelection(){
		return this.bSelection;
	}
}
