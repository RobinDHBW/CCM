package com.dhbwProject.unternehmen;

import com.dhbwProject.backend.beans.Unternehmen;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;

public class AdresseBearbeitung extends CustomComponent {
	private static final long serialVersionUID = 1L;
	
	private Table tblAdresse;
	private IndexedContainer container;
	
	private Unternehmen uReferenz;
	
	public AdresseBearbeitung(Unternehmen u){
		this.uReferenz = u;
	}
	
	private void initContainer(){
		this.container = new IndexedContainer();
		this.container.addContainerProperty("Adresse", TextArea.class, null);
		
	}

}
