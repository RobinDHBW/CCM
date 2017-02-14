package com.dhbwProject.besuche;

import java.sql.SQLException;

import com.dhbwProject.backend.CCM_Constants;
import com.dhbwProject.backend.dbConnect;
import com.dhbwProject.backend.beans.Status;
import com.vaadin.data.Item;
import com.vaadin.data.Property.ReadOnlyException;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.AbstractSelect.ItemCaptionMode;

public class LookupStatus extends Window {
	private static final long serialVersionUID = 1L;
	
	private VerticalLayout layout;
	private VerticalLayout fields;
	private IndexedContainer container;
	private ListSelect select;
	private Button btnOK;
	
	private dbConnect dbConnection;
	private Status sSelect;
	
	public LookupStatus(){
		this.dbConnection = (dbConnect)VaadinSession.getCurrent().getSession().getAttribute(CCM_Constants.SESSION_VALUE_CONNECTION);
		this.initFields();
		
		this.layout = new VerticalLayout(this.fields);
		this.layout.setSizeFull();
		this.layout.setComponentAlignment(this.fields, Alignment.TOP_CENTER);
		
		this.setContent(this.layout);
		this.center();
//		this.setWidth("350px");
//		this.setHeight("500px");
		this.setStyleName("pwwindow");
		Responsive.makeResponsive(layout);
		Responsive.makeResponsive(this);
	}
	
	private void initFields(){
		this.fields = new VerticalLayout();
		this.fields.setSizeUndefined();
		this.fields.setSpacing(true);
		this.fields.setMargin(new MarginInfo(true, true, true, true));
		
		this.select = new ListSelect();
	//	this.select.setWidth("300px");
		Responsive.makeResponsive(select);
		try {
			this.initContainer();
		} catch (ReadOnlyException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		this.select.setContainerDataSource(this.container);
		select.setItemCaptionMode(ItemCaptionMode.ITEM);
		
	    
	    this.btnOK = new Button("Auswählen");
	//    this.btnOK.setWidth("300px");
	    Responsive.makeResponsive(btnOK);
	    this.btnOK.setIcon(FontAwesome.UPLOAD);
	    this.btnOK.addClickListener(listener ->{
	    	if(this.select.getValue() != null)
				try {
					this.sSelect = dbConnection.getStatusById((int)this.select.getValue());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    	this.close();
	    });
	    
		this.fields.addComponent(this.select);
		this.fields.setComponentAlignment(this.select, Alignment.TOP_CENTER);
		this.fields.addComponent(this.btnOK);
		this.fields.setComponentAlignment(this.btnOK, Alignment.TOP_CENTER);
	}
	
	private void initContainer() throws ReadOnlyException, SQLException{
		this.container = new IndexedContainer();
		this.container.addContainerProperty("bezeichning", String.class, null);
		
		for(Status s : dbConnection.getAllStatus()){
			Item itm = this.container.addItem(s.getId());
			itm.getItemProperty("bezeichning").setValue(s.getBezeichnung());
		}
	}
	
	public Status getSelection(){
		return this.sSelect;
	}

}
