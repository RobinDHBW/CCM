package com.dhbwProject.views;

import java.sql.SQLException;

import com.dhbwProject.backend.CCM_Constants;
import com.dhbwProject.backend.dbConnect;
import com.dhbwProject.backend.beans.Benutzer;
import com.dhbwProject.benutzer.BenutzerAenderung;
import com.dhbwProject.benutzer.BenutzerAnlage;
import com.dhbwProject.benutzer.BenutzerAnzeige;
import com.dhbwProject.besuche.BesuchBearbeitung;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

public class ViewBenutzer extends VerticalLayout implements View {
	private static final long serialVersionUID = 1L;
	private TabSheet tbContent;
	private dbConnect dbConnection = (dbConnect)VaadinSession.getCurrent().getSession().getAttribute(CCM_Constants.SESSION_VALUE_CONNECTION);
	
	
	
	public ViewBenutzer(){
		this.setSizeFull();
		this.setCaption(CCM_Constants.VIEW_NAME_BENUTZER);
		this.initTbContent();
		FooterView fv= new FooterView();
		this.addComponent(fv);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		
		
	}
	
	private void initTbContent(){
		this.tbContent = new TabSheet();
		this.tbContent.addTab(new BenutzerAnzeige(), "Alle Benutzer", FontAwesome.USERS);
		int i = 0;
		try {
			i = dbConnection.checkBerechtigung((Benutzer) VaadinSession.getCurrent().getSession().getAttribute(CCM_Constants.SESSION_VALUE_USER),BenutzerAnlage.BERECHTIGUNG);
		} catch (SQLException e1) {
			System.out.println("Fehler bei der Berechtigungsprüfung!");
		}
		if (i >= 3) {
		this.tbContent.addTab(new BenutzerAnlage(), "Benutzer anlegen", FontAwesome.USER_PLUS);
		}
		this.tbContent.addTab(new BenutzerAenderung(), "Benutzer ändern", FontAwesome.LIST);
		this.addComponent(tbContent);
		
	}
}
