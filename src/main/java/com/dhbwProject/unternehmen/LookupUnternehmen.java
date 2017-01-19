package com.dhbwProject.unternehmen;

import java.sql.SQLException;

import com.dhbwProject.backend.CCM_Constants;
import com.dhbwProject.backend.dbConnect;
import com.dhbwProject.backend.beans.Adresse;
import com.dhbwProject.backend.beans.Unternehmen;
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

public class LookupUnternehmen extends Window{
	private static final long serialVersionUID = 1L;
	
	private VerticalLayout layout;
	private VerticalLayout fields;
	private TextField tfFirma;
	private Table tblSelect;
	private IndexedContainer container;
	private Button btnOk;
	private Unternehmen uSelect;
	private Adresse aSelect;
	private dbConnect dbConnection;
	
	public LookupUnternehmen(){
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
	    this.btnOk.addClickListener(listener ->{
	    	if(this.tblSelect.getValue() != null){
				ItemId id = (ItemId)this.tblSelect.getValue();
				this.uSelect = id.uItem;
				this.aSelect = id.aItem;
	    	}
	    	this.close();
	    });
	    
	    this.fields.addComponent(this.tfFirma);
	    this.fields.setComponentAlignment(this.tfFirma, Alignment.TOP_CENTER);
	    this.fields.addComponent(this.tblSelect);
	    this.fields.setComponentAlignment(this.tblSelect, Alignment.TOP_CENTER);
	    this.fields.addComponent(this.btnOk);
	    this.fields.setComponentAlignment(this.btnOk, Alignment.TOP_CENTER);
	}
	
	private void initContainer(){
		this.container = new IndexedContainer();
		container.addContainerProperty("Firma", String.class, null);
		container.addContainerProperty("Standort", TextArea.class, null);

		try{
			for(Unternehmen u : this.dbConnection.getAllUnternehmen()){
				for(Adresse a  : u.getlAdresse()){
					Item itm = container.addItem(new ItemId(u, a));
					itm.getItemProperty("Firma").setValue(u.getName());
					TextArea taStandort = new TextArea();
					taStandort.setValue(a.getStrasse()+"\n"+a.getPlz()+"\n"+a.getOrt());
					taStandort.setStyleName(ValoTheme.TEXTAREA_BORDERLESS);
					taStandort.setHeight("100px");
					itm.getItemProperty("Standort").setValue(taStandort);
				}
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public Unternehmen getSelectionUnternehmen(){
		return this.uSelect;
	}
	
	public Adresse getSelectionAdresse(){
		return this.aSelect;
	}
	
	private class ItemId{
		private Unternehmen uItem;
		private Adresse aItem;
		
		private ItemId(Unternehmen u, Adresse a){
			this.uItem = u;
			this.aItem = a;
		}
	}
	
	
}
