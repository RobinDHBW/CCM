package com.dhbwProject.unternehmen;

import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

public class UnternehmenAnzeigen extends CustomComponent {
	private static final long serialVersionUID = 1L; // dass es net gelb unterstrichen wird
	
	private UnternehmenFelder fields;
	private Button utnAnzeigen;
	//private Table unternehmen;
	
	private VerticalLayout vlLayout;
	//private HorizontalLayout hzLayout;
	
	
	public UnternehmenAnzeigen(){
		this.fields = new UnternehmenFelder();
		this.initCreateButton();
	//	this.initTable();
		this.initLayout();
		
	}
	
	private void initCreateButton(){
		this.utnAnzeigen = new Button ();
		this.utnAnzeigen.setIcon(FontAwesome.LIST);
		this.utnAnzeigen.setCaption("Anzeigen");
		this.utnAnzeigen.addClickListener(listener ->{
			//Später wird mehr erfolgen hier
		});
		
		this.fields.addComponent(utnAnzeigen);
	}
	
/*	public void initTable(){
		unternehmen = new Table();
		unternehmen.setSizeFull();
		unternehmen.setContainerDataSource(loadTableData());
		//addComponent(unternehmen);
	}
	
	public IndexedContainer loadTableData() {
		IndexedContainer container= new IndexedContainer();
		container.addContainerProperty("Unternehmen", String.class, null);
		container.addContainerProperty("Kennzeichen", String.class, null);
		
		//Dummywerte zum testen
		String[] aUnternehmen = {"Apple", "Amazon", "Boston Consulting", "Porsche SE", "BMW", "ebm-Papst GmbH", "ZIEHL-ABEGG SE", "Alibaba", "Procter & Gamble", "SAP SE", "Nestlé"};
		String[] aKennzeichen = {"A", "A", "B", "A", "B", "B", "B", "A", "A", "A", "B"};		
		for(int i = 0; i<aUnternehmen.length; i++){
			Item itm = container.addItem(i);
			itm.getItemProperty("Unternehmen").setValue(aUnternehmen[i]);
			itm.getItemProperty("Kennzeichen").setValue(aKennzeichen[i]);
		}
		return container;
	*/ // Testversuch einer Table analog zu Manu
	
	 private void initLayout(){
		this.vlLayout = new VerticalLayout(this.fields);
		this.vlLayout.setSizeFull();
		this.vlLayout.setComponentAlignment(this.fields, Alignment.TOP_LEFT);
		this.vlLayout.setSpacing(true);
		this.vlLayout.setMargin(new MarginInfo(true, true, true, true));
		this.setCompositionRoot(vlLayout); // Vertikales Layout

	} 
	
/*	private void initLayout(){
		this.hzLayout = new HorizontalLayout(this.fields);
		this.hzLayout.setSizeUndefined();
		this.hzLayout.setSpacing(true);
		this.hzLayout.setComponentAlignment(this.fields, Alignment.TOP_LEFT);
		this.hzLayout.setMargin(new MarginInfo(true, true, true, true));
		this.setCompositionRoot(hzLayout); //horizontales Layout
	} */
}
