package com.dhbwProject.benutzer;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Set;

import com.dhbwProject.backend.CCM_Constants;
import com.dhbwProject.backend.dbConnect;
import com.dhbwProject.backend.beans.Benutzer;
import com.dhbwProject.backend.beans.Studiengang;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.AbstractSelect.ItemCaptionMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class LookupBenutzer extends Window{
	private static final long serialVersionUID = 1L;
	
	private VerticalLayout fields;
	private TextField tfFilterNachname;
	private TextField tfFilterVorname;
	private TextArea taFilterStudiengang;
	private Button btnLookupStudiengang;
	private ListSelect select;
	private IndexedContainer container;
	private LinkedList<Benutzer> lBenutzerAuswahl;
	private Button btnOk;
	private LinkedList<Benutzer> lBenutzerSelection;
	private dbConnect dbConnection;
	private Benutzer bSelection;
	
	public LookupBenutzer(){
		this.dbConnection = (dbConnect)VaadinSession.getCurrent().getSession().getAttribute(CCM_Constants.SESSION_VALUE_CONNECTION);
		try {
			this.lBenutzerAuswahl = this.dbConnection.getAllBenutzer();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		this.setContent(this.initFields());
		this.center();
		this.setWidth("400");
		this.setHeight("600px");
		this.setCaptionAsHtml(true);
		this.setCaption("<center><h3>Wählen Sie Ihre Teilnehmer:</h3></center>");
	}
	
	public LookupBenutzer(boolean multiSelect){
		this();
		this.select.setMultiSelect(multiSelect);
	}

	private Panel initFields(){		
		this.select = new ListSelect();
		this.select.setWidth("300px");
		this.initContainer();
		this.select.setContainerDataSource(this.container);
		select.setItemCaptionMode(ItemCaptionMode.ITEM);
		
		this.tfFilterNachname = new TextField();
		this.tfFilterNachname.setInputPrompt("Filter Nachname");
		this.tfFilterNachname.setWidth("300px");
	    this.tfFilterNachname.addTextChangeListener(change -> {
	    	container.removeContainerFilters("nachname");
	        if (! change.getText().isEmpty())
	        	container.addContainerFilter(
	                new SimpleStringFilter("nachname",
	                    change.getText(), true, false));
	    });
	    
	    this.tfFilterVorname = new TextField();
	    this.tfFilterVorname.setInputPrompt("Filter Vorname");
	    this.tfFilterVorname.setWidth("300px");
	    this.tfFilterVorname.addTextChangeListener(change ->{
	    	container.removeContainerFilters("vorname");
	        if (! change.getText().isEmpty())
	        	container.addContainerFilter(
	                new SimpleStringFilter("vorname",
	                    change.getText(), true, false));
	    });
	    
	    this.taFilterStudiengang = new TextArea();
	    this.taFilterStudiengang.setWidth("300px");
	    this.taFilterStudiengang.setHeight("100px");
	    this.taFilterStudiengang.setReadOnly(true);
	    
	    this.btnLookupStudiengang = new Button();
	    this.btnLookupStudiengang.setWidth("50px");
	    this.btnLookupStudiengang.setIcon(FontAwesome.REPLY);
	    this.btnLookupStudiengang.addClickListener(click ->{
	    	LookupStudiengang lookup = new LookupStudiengang();
	    	lookup.addCloseListener(close ->{
	    		if(lookup.getLSelection() == null)
	    			return;
	    		else if(lookup.getLSelection().size() <=0){
	    			refreshContainer();
	    			setStudiengang(lookup.getLSelection());
	    		}else{
		    		setStudiengang(lookup.getLSelection());
		    		refreshContainer(lookup.getLSelection());
	    		}
	    	});
	    	getUI().addWindow(lookup);
	    });
	    
	    HorizontalLayout hlStudiengang = new HorizontalLayout(taFilterStudiengang, btnLookupStudiengang);
	    hlStudiengang.setCaption("Studiengänge:");
	    hlStudiengang.setSpacing(true);
	    
	    this.btnOk = new Button("Auswählen");
	    this.btnOk.setWidth("300px");
	    this.btnOk.setIcon(FontAwesome.UPLOAD);	    
	    this.btnOk.addClickListener(click ->{
	    	if(select.isMultiSelect()){
	    		lBenutzerSelection  = new LinkedList<Benutzer>();
	    		Set <Item>values = (Set<Item>) this.select.getValue();
	    		for(Object o : values)
	    			this.lBenutzerSelection.add((Benutzer)o);
	    	}else
	    		this.bSelection = (Benutzer)this.select.getValue();
	    	this.close();	
	    });
	    
		this.fields = new VerticalLayout(tfFilterNachname, tfFilterVorname, hlStudiengang, select, btnOk);
		this.fields.setSizeUndefined();
		this.fields.setSpacing(true);

		VerticalLayout layout = new VerticalLayout(fields);
		layout.setMargin(true);
		layout.setComponentAlignment(fields, Alignment.TOP_CENTER);
		return new Panel(layout);
	    
	}

	private void initContainer(){
		this.container = new IndexedContainer();
		this.container.addContainerProperty("nachname", String.class, null);
		this.container.addContainerProperty("vorname", String.class, null);
		refreshContainer();
	}
	
	private void refreshContainer(){
		this.container.removeAllItems();
		for(Benutzer b : this.lBenutzerAuswahl)
			addItem(b);
	}
	
	private void refreshContainer(LinkedList<Studiengang> lStudiengang){
		this.container.removeAllItems();
		for(Benutzer b : this.lBenutzerAuswahl)
			for(Studiengang s : b.getStudiengang())
				if(lStudiengang.contains(s)){
					addItem(b);
					break;
				}
	}
	
	private void addItem(Benutzer b){
		Item itm = container.addItem(b);
		itm.getItemProperty("nachname").setValue(b.getNachname()+", ");
		itm.getItemProperty("vorname").setValue(b.getVorname());
	}
	
	public boolean removeAutorFromList(){
		Benutzer autor = (Benutzer)VaadinSession.getCurrent().getSession().getAttribute(CCM_Constants.SESSION_VALUE_USER);
		for(Object o : container.getItemIds())
			if(o.equals(autor))
				return this.container.removeItem(o);
		return false;
	}
	
	public Benutzer getSelection(){
		return this.bSelection;
	}
	
	public LinkedList<Benutzer> getLSelection(){
		return this.lBenutzerSelection;
	}
	
	private void setStudiengang(LinkedList<Studiengang> lStudiengang){
		StringBuilder sbValue = new StringBuilder();
		for(Studiengang s : lStudiengang)
			sbValue.append(s.getBezeichnung()+"\n");
		this.taFilterStudiengang.setReadOnly(false);
		this.taFilterStudiengang.setValue(sbValue.toString());
		this.taFilterStudiengang.setReadOnly(true);
	}
}
