package com.dhbwProject.unternehmen;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.VerticalLayout;

public class UnternehmenAenderung extends CustomComponent {
	private static final long serialVersionUID = 1L; // dass net gelb wird
	
	
	private UnternehmenFelder fields;
	private Button utnAendern;
	
	private VerticalLayout vlLayout;
	
	
	public UnternehmenAenderung(){
		this.fields = new UnternehmenFelder();
		this.initCreateButton();
		this.initLayout();
		
		
	}
	
	private void initCreateButton(){
		this.utnAendern = new Button ();
		this.utnAendern.setIcon(FontAwesome.PLUS);
		this.utnAendern.setCaption("Ändern");
		this.utnAendern.addClickListener(listener ->{
			//Später wird mehr erfolgen hier
		});
		
		this.fields.addComponent(utnAendern);
	}
	
	private void initLayout(){
		this.vlLayout = new VerticalLayout(this.fields);
		this.vlLayout.setSizeFull();
		this.vlLayout.setComponentAlignment(this.fields, Alignment.TOP_LEFT);
		this.setCompositionRoot(vlLayout);
	}
}
