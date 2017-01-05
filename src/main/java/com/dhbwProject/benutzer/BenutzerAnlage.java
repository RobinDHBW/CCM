package com.dhbwProject.benutzer;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.VerticalLayout;

public class BenutzerAnlage extends CustomComponent {

	private BenutzerFields fields;
	private Button btnErstellen;

	private VerticalLayout vlLayout;
	
	public BenutzerAnlage(){
		this.fields = new BenutzerFields();
		this.initCreateButton();
		this.initLayout();
	}
	


	private void initCreateButton() {
		this.btnErstellen = new Button();
		this.btnErstellen.setIcon(FontAwesome.PLUS);
		this.btnErstellen.setCaption("Erstellen");
		this.btnErstellen.addClickListener(listener ->{
			//Erfolgt später
		});
		
		this.fields.addComponent(btnErstellen);
		
	}
	
	private void initLayout(){
		this.vlLayout = new VerticalLayout(this.fields);
		this.vlLayout.setSizeFull();
		this.vlLayout.setComponentAlignment(this.fields, Alignment.TOP_LEFT);
		this.setCompositionRoot(vlLayout);
		
	}

}
