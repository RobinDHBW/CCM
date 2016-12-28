package com.dhbwProject.benutzer;

import java.util.ArrayList;

import com.dhbwProject.CCM.Lookup;
import com.dhbwProject.backend.beans.Benutzer;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.Grid.SelectionMode;

/*	Das Lookup soll die verfügbaren Benutzer anzeigen
 * 	Der Benutzer soll dann einen oder mehrere Benutzer(Teilnehmer) aus der Grid auswählen
 * 	Durch die Benutzerlistenreferenz gelangt das Resultat dann zum Aufrufer
 * */
public class LookupBenutzer extends Lookup {
	private static final long serialVersionUID = 1L;
	private ArrayList<Benutzer> lUser;
	
	public LookupBenutzer(ArrayList<Benutzer> lUser){
		this.lUser = lUser;
		super.setCaption("<center><h2>Bitte wählen Sie Ihre Teilnemer</h2></center>");
		super.getOkButton().addClickListener(listener ->{
			/*	Hier soll abschließend die Benutzrliste befüllt werden
			 * 	Aktuell werden Dummy-Beans erzeugt
			 * */
			for(Object o : super.getGrid().getSelectedRows()){
				
				
				
				Benutzer b = new Benutzer(super.getGrid().getContainerDataSource().getItem(o).toString(),
						(String)super.getGrid().getContainerDataSource().getItem(o).getItemProperty("Vorname").getValue(),
						(String)super.getGrid().getContainerDataSource().getItem(o).getItemProperty("Nachname").getValue(),
						null, null);
				this.lUser.add(b);
			}
			this.close();
		});
		super.getGrid().setContainerDataSource(this.loadData());
		super.getGrid().setSelectionMode(SelectionMode.MULTI);
		super.getGrid().recalculateColumnWidths();
		super.initGridMatchingFilter();
	}

	@Override
	protected IndexedContainer loadData() {
		IndexedContainer container= new IndexedContainer();
		container.addContainerProperty("Vorname", String.class, null);
		container.addContainerProperty("Nachname", String.class, null);
		
//		for(Benutzer b : this.allUsers){
//			Item itm = container.addItem(b.getId());
//			itm.getItemProperty("Vorname").setValue(b.getVorname());
//			itm.getItemProperty("Nachname").setValue(b.getNachname());
//		}
		
		//Dummywerte
		String[] aVorname = {"Albert", "Herbert", "Yoshi", "Sakura", "Robin", "Simon", "Bosse", "Jasmin", "Florian", "Manuel", "Christian"};
		String[] aNachname = {"Terbun", "Remus", "Suzuki", "Shizuki", "Bahr", "Schlarb", "Bosse", "Stribik", "Flurer", "Manu", "Zaengle"};		
		for(int i = 0; i<aVorname.length; i++){
			Item itm = container.addItem(i);
			itm.getItemProperty("Vorname").setValue(aVorname[i]);
			itm.getItemProperty("Nachname").setValue(aNachname[i]);
		}
		
		return container;
	}

}
