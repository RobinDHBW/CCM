package com.dhbwProject.termine;

import java.sql.SQLException;
import java.util.Date;

import com.dhbwProject.backend.CCM_Constants;
import com.dhbwProject.backend.dbConnect;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class TerminAnlage extends Window {
	private static final long serialVersionUID = 1L;
	private dbConnect dbConnection;
	
	private TerminFields fields;
	private VerticalLayout vlLayout;
	private Button btnCreate;	
	
	public TerminAnlage(){
		this.dbConnection = (dbConnect)VaadinSession.getCurrent().getSession().getAttribute(CCM_Constants.SESSION_VALUE_CONNECTION);
		this.setContent(this.initContent());
		
		setCaptionAsHtml(true);
		setCaption("<center><h3>Termin anlegen</h3></center>");
		center();
		setWidth("450px");
		setHeight("600px");
		setDraggable(true);
		setClosable(true);
		setModal(false);
	}
	
	public TerminAnlage(Date date){
		this();
		this.fields.setDateStart(date);
		this.fields.setDateEnd(date);
	}
	
	private void initFields(){
		this.fields = new TerminFields();
		this.btnCreate = new Button("Termin erstellen");
		this.btnCreate.setIcon(FontAwesome.PLUS);
		this.btnCreate.setWidth("300px");
		this.btnCreate.addClickListener(listener ->{
			try {
				this.dbConnection.createBesuch(null);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.close();
		});
		this.fields.addComponent(this.btnCreate);
	}
	
	private void initVlLayout(){
		this.initFields();
		this.vlLayout = new VerticalLayout(this.fields);
		this.vlLayout.setSizeFull();
		this.vlLayout.setComponentAlignment(this.fields, Alignment.TOP_CENTER);
	}
	
	private Panel initContent(){
		this.initVlLayout();
		Panel p = new Panel();
		p.setContent(vlLayout);
		return p;
	}

}
