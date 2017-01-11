package com.dhbwProject.unternehmen;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.VerticalLayout;

public class UnternehmenAnzeigen extends CustomComponent {
	private static final long serialVersionUID = 1L; // dass es net gelb unterstrichen wird
	
	private UnternehmenFelder fields;
	private Button utnAnzeigen;
	
	private VerticalLayout vlLayout;
	
	
	public UnternehmenAnzeigen(){
		this.fields = new UnternehmenFelder();
		this.initCreateButton();
		this.initLayout();
		
	}
	
	private void initCreateButton(){
		this.utnAnzeigen = new Button ();
		this.utnAnzeigen.setIcon(FontAwesome.PLUS);
		this.utnAnzeigen.setCaption("Anzeigen");
		this.utnAnzeigen.addClickListener(listener ->{
			//Später wird mehr erfolgen hier
		});
		
		this.fields.addComponent(utnAnzeigen);
	}
	
	private void initLayout(){
		this.vlLayout = new VerticalLayout(this.fields);
		this.vlLayout.setSizeFull();
		this.vlLayout.setComponentAlignment(this.fields, Alignment.TOP_LEFT);
		this.setCompositionRoot(vlLayout);
	}

}
