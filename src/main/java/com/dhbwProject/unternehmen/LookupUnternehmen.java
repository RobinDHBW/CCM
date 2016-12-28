package com.dhbwProject.unternehmen;

import java.util.LinkedList;

import com.dhbwProject.CCM.Lookup;
import com.dhbwProject.backend.beans.Unternehmen;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.Grid.SelectionMode;

/*	Das Lookup soll alle verfügbaren Unternehmen des jeweiligen Benutzers anzeigen
 * 	Der Benutzer soll dann ein Unternehmen aus der Grid auswählen
 * 	Durch die Unternehmensreferenz gelangt das Resultat dann zum Aufrufer
 * */
public class LookupUnternehmen extends Lookup{
	private static final long serialVersionUID = 1L;
	private Unternehmen uReferenz;
	private LinkedList<Unternehmen> lUnternehmen;

	
	public LookupUnternehmen(Unternehmen uReferenz){
		this.uReferenz = uReferenz;
		super.setCaption("<center><h2>Bitte wählen Sie ein Unternehmen</h2></center>");
		super.getOkButton().addClickListener(listener ->{
			//befülle hier das referenzierte Unternehmensobjekt
			this.close();
		});
		super.getGrid().setContainerDataSource(this.loadData());
		super.getGrid().setSelectionMode(SelectionMode.SINGLE);
		super.getGrid().recalculateColumnWidths();
		super.initGridMatchingFilter();
	}
	
	@Override
	protected IndexedContainer loadData(){
		IndexedContainer container = new IndexedContainer();
		container.addContainerProperty("Unternehmen", String.class, null);
		container.addContainerProperty("Standort", String.class, "Location");
		
		//Dummywerte
		String[] aEnterprise = {"ebm-papst", "Ziehl-Abegg", "Fujitsu", "Toyota", "Sony", "Toyo", "Phyto", "Mercedes Benz", "Pabronko"};
		for(int i = 0; i<aEnterprise.length; i++){
			Item itm = container.addItem(i);
			itm.getItemProperty("Unternehmen").setValue(aEnterprise[i]);
		}
		return container;
	}
}
