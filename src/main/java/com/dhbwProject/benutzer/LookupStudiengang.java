package com.dhbwProject.benutzer;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Set;

import com.dhbwProject.backend.CCM_Constants;
import com.dhbwProject.backend.dbConnect;
import com.dhbwProject.backend.beans.Studiengang;
import com.vaadin.data.Item;
import com.vaadin.data.Property.ReadOnlyException;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.AbstractSelect.ItemCaptionMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class LookupStudiengang extends Window{
private static final long serialVersionUID = 1L;
	
	private VerticalLayout layout;
	private VerticalLayout fields;
	private TextField tfFilterStudiengang;
	private OptionGroup select;
	private IndexedContainer container;
	private Button btnOk;
	private LinkedList<Studiengang> lStudiengangSelection = new LinkedList<Studiengang>();
	private dbConnect dbConnection;
	private Studiengang sSelection;
	
	public LookupStudiengang(){
		this.dbConnection = (dbConnect)VaadinSession.getCurrent().getSession().getAttribute(CCM_Constants.SESSION_VALUE_CONNECTION);
		this.initFields();
		this.select.setMultiSelect(true);
		this.setModal(true);
		
		this.layout = new VerticalLayout(this.fields);
		this.layout.setSizeFull();
		this.layout.setComponentAlignment(this.fields, Alignment.MIDDLE_CENTER);
		
		Panel p = new Panel(this.layout);
		this.setContent(p);
		this.center();
		this.setWidth("370px");
		this.setHeight("500px");
		Responsive.makeResponsive(this);
		Responsive.makeResponsive(layout);
		Responsive.makeResponsive(p);
		
	}

	private void initFields(){
		this.fields = new VerticalLayout();
		this.fields.setSizeUndefined();
		this.fields.setSpacing(true);
		this.fields.setMargin(new MarginInfo(true, true, true, true));
		
		this.select = new OptionGroup();
		this.select.setWidth("300px");
		Responsive.makeResponsive(select);
		this.initContainer();
		this.select.setContainerDataSource(this.container);
		select.setItemCaptionMode(ItemCaptionMode.ITEM);
//		select.select(itemId);
		
		this.tfFilterStudiengang = new TextField();
		this.tfFilterStudiengang.setInputPrompt("Filter Studiengang");
		this.tfFilterStudiengang.setWidth("300px");
		Responsive.makeResponsive(tfFilterStudiengang);
	    this.tfFilterStudiengang.addTextChangeListener(change -> {
	    	container.removeContainerFilters("studiengang");
	        if (! change.getText().isEmpty())
	        	container.addContainerFilter(
	                new SimpleStringFilter("studiengang",
	                    change.getText(), true, false));
	    });
	    
	    this.btnOk = new Button("Auswählen");
	    this.btnOk.setWidth("300px");
	    Responsive.makeResponsive(btnOk);
	    this.btnOk.setIcon(FontAwesome.UPLOAD);	    
	    this.btnOk.addClickListener(click ->{
	    		Set <Item>values = (Set<Item>) this.select.getValue();
	    		for(Object o : values)
	    			this.lStudiengangSelection.add((Studiengang)o);
	    	this.close();	
	    });
	    
		this.fields.addComponent(this.tfFilterStudiengang);
		this.fields.setComponentAlignment(this.tfFilterStudiengang, Alignment.TOP_CENTER);
		this.fields.addComponent(this.select);
		this.fields.setComponentAlignment(this.select, Alignment.TOP_CENTER);
		this.fields.addComponent(this.btnOk);
		this.fields.setComponentAlignment(this.btnOk, Alignment.TOP_CENTER);
		
	}

	private void initContainer(){
		this.container = new IndexedContainer();
		this.container.addContainerProperty("studiengang", String.class, null);
		try {
			for(Studiengang b : this.dbConnection.getAllStudiengang()){
				Item itm = container.addItem(b);
				itm.getItemProperty("studiengang").setValue(b.getBezeichnung());
			}
		} catch (ReadOnlyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public LinkedList<Studiengang> getLSelection(){
		return this.lStudiengangSelection;
	}

}
