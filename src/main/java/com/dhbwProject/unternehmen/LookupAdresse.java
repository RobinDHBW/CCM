package com.dhbwProject.unternehmen;


import java.sql.SQLException;

import com.dhbwProject.backend.CCM_Constants;
import com.dhbwProject.backend.DummyDataManager;
import com.dhbwProject.backend.dbConnect;
import com.dhbwProject.backend.beans.Adresse;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

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
//	private DummyDataManager dummyData;
	private dbConnect dbConnection;
	
	/*
	 * Die Adresse kann als Übergabeparameter wohl entfernt werden
	 * - siehe TerminFields für Zugriff
	 * */
	public LookupAdresse(){
//		this.dummyData = dummyData;
		this.dbConnection = (dbConnect)VaadinSession.getCurrent().getSession().getAttribute(CCM_Constants.SESSION_VALUE_CONNECTION);
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
	    	container.removeContainerFilters("Firma");
	        if (! change.getText().isEmpty())
	        	container.addContainerFilter(
	                new SimpleStringFilter("Firma",
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
				try {
					this.aSelect = this.dbConnection.getAdresseById((int)this.tblSelect.getValue());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
//	    		this.aSelect = this.dummyData.getAdresse((int)this.tblSelect.getValue());
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
		container.addContainerProperty("Firma", String.class, null);
		container.addContainerProperty("Standort", TextArea.class, null);
		
		
		/*
		 * NUR TEMPORÄR
		 * */ 
		Adresse a = null;
		try {
			a = this.dbConnection.getAdresseById(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Item itm1 = container.addItem(a.getId());
		itm1.getItemProperty("Firma").setValue(a.getUnternehmen().getName());
		TextArea taStandort = new TextArea();
		taStandort.setValue(a.getStrasse()+"\n"+a.getPlz()+"\n"+a.getOrt());
		taStandort.setStyleName(ValoTheme.TEXTAREA_BORDERLESS);
		taStandort.setHeight("100px");
		itm1.getItemProperty("Standort").setValue(taStandort);
		
		
		
//		for(Adresse a : this.dummyData.getlAdresse()){
//			Item itm = container.addItem(a.getId());	
//			itm.getItemProperty("Firma").setValue(a.getUnternehmen().getName());
//			TextArea taStandort = new TextArea();
//			taStandort.setValue(a.getStrasse()+"\n"+a.getPlz()+"\n"+a.getOrt());
//			taStandort.setStyleName(ValoTheme.TEXTAREA_BORDERLESS);
//			taStandort.setHeight("100px");
//			itm.getItemProperty("Standort").setValue(taStandort);
//		}
	}
	
	public Adresse getSelection(){
		return this.aSelect;
	}
	
	
}
