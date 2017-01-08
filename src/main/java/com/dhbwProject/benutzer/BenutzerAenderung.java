package com.dhbwProject.benutzer;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.VerticalLayout;

public class BenutzerAenderung extends CustomComponent {
	private static final long serialVersionUID = 1L; // dass der gelb unterstrichene Klassenname nicht nervt
	
	
	private BenutzerFields fields;
	private Button btnAendern;
	
	private VerticalLayout vlLayout;
	
	
	public BenutzerAenderung(){
		this.fields = new BenutzerFields();
		this.initCreateButton();
		this.initLayout();
	}
	
	private void initCreateButton() {
		this.btnAendern = new Button();
		this.btnAendern.setIcon(FontAwesome.PLUS);
		this.btnAendern.setCaption("Ändern");
		this.btnAendern.addClickListener(listener ->{
			//Später wird mehr erfolgen hier
		});
		
		this.fields.addComponent(btnAendern);
	}
	
	private void initLayout(){
		this.vlLayout = new VerticalLayout(this.fields);
		this.vlLayout.setSizeFull();
		this.vlLayout.setComponentAlignment(this.fields, Alignment.TOP_LEFT);
		this.setCompositionRoot(vlLayout);
	}
}

