package com.dhbwProject.unternehmen;


import com.dhbwProject.backend.DummyDataManager;
import com.dhbwProject.backend.beans.Adresse;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PopupView;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.ui.AbstractSelect.ItemCaptionMode;

public class LookupAdresse extends Window{
	private static final long serialVersionUID = 1L;
	
	private VerticalLayout layout;
	private VerticalLayout fields;
	private TextField tfFirma;
	private Table tblSelect;
//	private ListSelect select;
	private IndexedContainer container;
	private Button btnOk;
	private Adresse aSelect;
	private DummyDataManager dummyData;
	
	/*
	 * Die Adresse kann als Übergabeparameter wohl entfernt werden
	 * - siehe TerminFields für Zugriff
	 * */
	public LookupAdresse(DummyDataManager dummyData){
		this.dummyData = dummyData;
		this.initFields();
		
		this.layout = new VerticalLayout(this.fields);
		this.layout.setSizeFull();
		this.layout.setComponentAlignment(this.fields, Alignment.TOP_CENTER);
		
		Panel p = new Panel();
		p.setContent(this.layout);
		this.setContent(p);
		this.center();
		this.setWidth("650px");
		this.setHeight("500px");
	}
	
	private void initFields(){
		this.fields = new VerticalLayout();
		this.fields.setSizeUndefined();
		this.fields.setSpacing(true);
		this.fields.setMargin(new MarginInfo(true, true, true, true));
		
//		this.select = new ListSelect();
//		this.select.setWidth("600px");
//		this.initContainer();
//		this.select.setContainerDataSource(this.container);
//		select.setItemCaptionMode(ItemCaptionMode.ITEM);
		
		this.tblSelect = new Table();
		this.tblSelect.setWidth("600px");
		this.tblSelect.setHeight("300px");
		this.initContainer();
		this.tblSelect.setContainerDataSource(container);
		this.tblSelect.setSelectable(true);
		this.tblSelect.setStyleName(ValoTheme.TABLE_BORDERLESS);
		this.tblSelect.addStyleName(ValoTheme.TABLE_NO_STRIPES);
		this.tblSelect.addStyleName(ValoTheme.TABLE_COMPACT);
		
		this.tfFirma = new TextField();
		this.tfFirma.setInputPrompt("Filter Firma");
		this.tfFirma.setWidth("600px");
	    this.tfFirma.addTextChangeListener(change -> {
	    	container.removeContainerFilters("uName");
	        if (! change.getText().isEmpty())
	        	container.addContainerFilter(
	                new SimpleStringFilter("uName",
	                    change.getText(), true, false));
	    });
	    
	    this.btnOk = new Button("Auswählen");
	    this.btnOk.setWidth("300px");
	    this.btnOk.setIcon(FontAwesome.UPLOAD);
//	    this.btnOk.addClickListener(listener ->{
//	    	if(this.select.getValue() != null)
//	    		this.aSelect = this.dummyData.getAdresse((int)this.select.getValue());
//	    	this.close();
//	    });
	    this.btnOk.addClickListener(listener ->{
	    	if(this.tblSelect.getValue() != null)
	    		this.aSelect = this.dummyData.getAdresse((int)this.tblSelect.getValue());
	    	this.close();
	    });
	    
	    this.fields.addComponent(this.tfFirma);
	    this.fields.setComponentAlignment(this.tfFirma, Alignment.TOP_CENTER);
//	    this.fields.addComponent(this.select);
//	    this.fields.setComponentAlignment(this.select, Alignment.TOP_CENTER);
	    this.fields.addComponent(this.tblSelect);
	    this.fields.setComponentAlignment(this.tblSelect, Alignment.TOP_CENTER);
	    this.fields.addComponent(this.btnOk);
	    this.fields.setComponentAlignment(this.btnOk, Alignment.TOP_CENTER);
	}
	
	private void initContainer(){
		this.container = new IndexedContainer();
		container.addContainerProperty("uName", String.class, null);
		container.addContainerProperty("Standort", String.class, null);
		
		for(Adresse a : this.dummyData.getlAdresse()){
			Item itm = container.addItem(a.getId());	
			itm.getItemProperty("uName").setValue(a.getUnternehmen().getName());
			itm.getItemProperty("Standort").setValue(a.getStrasse()+" - "+a.getPlz()+" "+a.getOrt());
		}
	}
	
	public Adresse getSelection(){
		return this.aSelect;
	}
	
	
}
