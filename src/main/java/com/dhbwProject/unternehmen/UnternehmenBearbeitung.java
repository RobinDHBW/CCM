package com.dhbwProject.unternehmen;

import java.sql.SQLException;

import com.dhbwProject.backend.CCM_Constants;
import com.dhbwProject.backend.dbConnect;
import com.dhbwProject.backend.beans.Unternehmen;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class UnternehmenBearbeitung extends Window{
	private static final long serialVersionUID = 1L;
	
	Unternehmen uAlt;
	Unternehmen uNeu;

	private VerticalLayout vlLayout;
	
	private UnternehmenFelder fields;
	private Button btnBearbeiten;
	
	public UnternehmenBearbeitung(Unternehmen u){
		this.center();
		this.setClosable(true);
		this.setModal(false);
		this.setWidth("400px");
		this.setHeight("600px");
		
		this.initFields();
		this.setContent(vlLayout);
		this.uAlt = u;
		this.fields.setName(u.getName());
	}
	
	private void initFields(){
		this.fields = new UnternehmenFelder();
		
		this.btnBearbeiten = new Button();
		this.btnBearbeiten.setCaption("Bearbeiten");
		this.btnBearbeiten.addClickListener(click ->{
			dbConnect connection = (dbConnect)VaadinSession.getCurrent().getSession().getAttribute(CCM_Constants.SESSION_VALUE_CONNECTION);
			this.uNeu = new Unternehmen(0, fields.getName(), uAlt.getlAnsprechpartner(), uAlt.getlAdresse(), fields.getKennzeichen());
//			(int id, String name, LinkedList<Ansprechpartner> lAnsprechpartner, LinkedList<Adresse> lAdresse, String kennzeichen)
			try {
				uNeu = connection.changeUnternehmen(uAlt, uNeu);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.close();
		});
		
		this.fields.addComponent(btnBearbeiten);
		this.vlLayout = new VerticalLayout(fields);
		this.vlLayout.setSizeFull();
		this.vlLayout.setMargin(new MarginInfo(true, true, true, true));
		
	}

}
