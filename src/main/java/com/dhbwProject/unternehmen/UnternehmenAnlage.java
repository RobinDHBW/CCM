package com.dhbwProject.unternehmen;

import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.VerticalLayout;

public class UnternehmenAnlage extends CustomComponent {
	private static final long serialVersionUID = 1L; // dass net gelb unterstreicht
	
	private UnternehmenFelder fields;
	private Button utnErstellen;
	
	private VerticalLayout vlLayout;
	
	public UnternehmenAnlage(){
		this.fields = new UnternehmenFelder();
		this.initCreateButton();
		this.initLayout();
	}
	
	private void initCreateButton(){
		this.utnErstellen = new Button();
		this.utnErstellen.setIcon(FontAwesome.PLUS);
		this.utnErstellen.setCaption("Erstellen");
		this.utnErstellen.addClickListener(listener ->{
			//Erfolgt später noch mehr
		});
		
		this.fields.addComponent(utnErstellen);
	}
	
	private void initLayout(){
		this.vlLayout = new VerticalLayout(this.fields);
		this.vlLayout.setSizeFull();
		this.vlLayout.setSpacing(true);
		this.vlLayout.setMargin(new MarginInfo(true, true, true, true));
		this.vlLayout.setComponentAlignment(this.fields, Alignment.TOP_LEFT);
		this.setCompositionRoot(vlLayout);
	}
	
	//public void createUnternehmen(){
	//	if (fields.getUnternehmen().equals()) {
			
	//	}
	//}
	

}
