package com.dhbwProject.unternehmen;

import com.dhbwProject.CCM.Lookup;
import com.dhbwProject.backend.beans.Ansprechpartner;
import com.dhbwProject.backend.beans.Unternehmen;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.Grid.SelectionMode;

/*	Das Lookup soll die verfügbaren Ansprechspartner eines Unternehmens anzeigen
 * 	Der Benutzer soll dann einen Ansprechspartner aus der Grid auswählen
 * 	Durch die Ansprechspartnerreferenz gelangt das Resultat dann zum Aufrufer
 * */
public class LookupAnsprechpartner extends Lookup{
	private static final long serialVersionUID = 1L;
	private Unternehmen uReferenz;
	private Ansprechpartner aReferenz;
	
	public LookupAnsprechpartner(Unternehmen u, Ansprechpartner a){
		this.uReferenz = u;
		super.setCaption("<center><h2>Bitte wählen Sie einen Ansprechspartner</h2></center>");
		super.getOkButton().addClickListener(listener ->{
			//belege den referenzierten Ansprechpartner mit der ausgewählten Position
			this.close();
		});
		super.getGrid().setContainerDataSource(this.loadData());
		super.getGrid().setSelectionMode(SelectionMode.SINGLE);
		super.initGridMatchingFilter();
	}
	
	@Override
	protected IndexedContainer loadData(){
		IndexedContainer container = new IndexedContainer();
		container.addContainerProperty("Vorname", String.class, null);
		container.addContainerProperty("Nachname", String.class, null);
		/*
		 *	Dummywerte
		 */
		String[] aVorname = {"Hans","Peter","Leber","Wurst"};
		String[] aNachname = {"B","Y","A","Z"};
		for(int i = 0; i<aVorname.length;i++){
			Item itm = container.addItem(i);
			itm.getItemProperty("Vorname").setValue(aVorname[i]);
			itm.getItemProperty("Nachname").setValue(aNachname[i]);
		}
		return container;
	}	

}
