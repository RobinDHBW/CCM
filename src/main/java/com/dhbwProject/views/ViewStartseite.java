package com.dhbwProject.views;

import com.dhbwProject.backend.CCM_Constants;
import com.dhbwProject.backend.DummyDataManager;
import com.dhbwProject.besuche.BesuchVerwaltung;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class ViewStartseite extends VerticalLayout implements View {
	private static final long serialVersionUID = 1L;
	//DummyDataManager dummyData;
	public ViewStartseite(){
	/* String username = this.getSession().getAttribute(CCM_Constants.SESSION_VALUE_USER).toString();
	 Label welcome = new Label("Hallo" + "Alpha" );
	 Label deinBesuch = new Label("Ihre nächsten Besuche sind bei folgenden Unternehmen:");
	 //TerminVerwaltung tv = new TerminVerwaltung(this.dummyData);
	 
	 this.addComponent(welcome);
	 this.addComponent(deinBesuch);
	// vL.addComponent(tv);
*/		
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}

}
