package com.dhbwProject.unternehmen;

import java.sql.SQLException;
import java.util.LinkedList;

import com.dhbwProject.backend.dbConnect;
import com.dhbwProject.backend.beans.Adresse;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class AdresseKollisionsPruefer extends Window{
	private static final long serialVersionUID = 1L;
	private boolean bResult = false;
	private Adresse aReferenz;
	private LinkedList<Adresse> lKollision;
	
	private dbConnect dbConnection;
	
	public AdresseKollisionsPruefer(Adresse aPruefReferenz, dbConnect connection){
		this.aReferenz = aPruefReferenz;
		this.dbConnection =  connection;
		this.center();
		this.setModal(true);
		this.setClosable(false);
		this.setWidth("400px");
		this.setHeight("600px");
		this.setCaptionAsHtml(true);
		this.setCaption("<center><h3>Die Adresse könnte bereits existieren!</h3></center>");
		this.setContent(initContent(checkAdresseKollision()));
	}
	
	private Panel initContent(LinkedList<Adresse> lAdresse){
		VerticalLayout layout = new VerticalLayout();
		layout.setSizeFull();
		layout.setSpacing(true);
		for(Adresse a : lAdresse){
			Label lblAdresse = new Label(a.getStrasse()+" "+a.getHausnummer()+"<br>"
					+a.getPlz()+"<br>"+a.getOrt(), ContentMode.HTML);
			layout.addComponent(lblAdresse);
			layout.setComponentAlignment(lblAdresse, Alignment.TOP_CENTER);
		}
		
		Button btnOk = new Button();
		btnOk.setCaption("Ja");
		btnOk.setWidth("100px");
		btnOk.setIcon(FontAwesome.CHECK);
		btnOk.addClickListener(click ->{
			this.bResult = true;
			this.close();
		});
		
		Button btnNein = new Button();
		btnNein.setCaption("Nein");
		btnNein.setWidth("100px");
		btnNein.setIcon(FontAwesome.CLOSE);
		btnNein.addClickListener(click -> this.close());
		
		HorizontalLayout hlButtons = new HorizontalLayout(btnOk, btnNein);
		hlButtons.setSpacing(true);
		layout.addComponent(hlButtons);
		layout.setComponentAlignment(hlButtons, Alignment.TOP_CENTER);
		layout.setMargin(true);
		return new Panel(layout);		
	}
	
	protected LinkedList<Adresse> checkAdresseKollision(){
		this.lKollision = new LinkedList<Adresse>();
		try {
			for(Adresse a : this.dbConnection.getAllAdresse())
				if(isKollision(a))
					lKollision.add(a);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lKollision;
	}
	
	private boolean isKollision(Adresse a){
		if(a.getPlz().equals(this.aReferenz.getPlz())
				&&a.getOrt().equals(this.aReferenz.getOrt())
				&& a.getStrasse().equals(this.aReferenz.getStrasse()))
			return true;
		return false;
			
	}
	
	protected boolean getResult(){
		return bResult;
	}
	
	protected int getKollisionSize(){
		return this.lKollision.size();
	}

}
