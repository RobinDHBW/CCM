package com.dhbwProject.termine;

import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.AbstractSelect.ItemCaptionMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Grid.HeaderCell;
import com.vaadin.ui.Grid.HeaderRow;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.Reindeer;

public class TerminAnlage extends CustomComponent {
	private static final long serialVersionUID = 1L;
	
	private VerticalLayout vlContent;
	private VerticalLayout vlLayout;
	
	private DateField dfDate;
	private ComboBox cbEnterprise;
	private Grid grdParticipants;
	private IndexedContainer idcParticipants;
	private Button btnCreate;
	
	
	public TerminAnlage(){
		this.initVlContent();
		this.initFields();
		this.initVlLayout();
	}
	
	private void initVlContent(){
		this.vlContent = new VerticalLayout();
		this.vlContent.setSizeUndefined();
		this.vlContent.setSpacing(true);
		this.vlContent.setMargin(new MarginInfo(true, false, true, true));
	}
	
	private void initFields(){
		this.initDfDate();
		this.initCbEnterprise();
		this.initGrdParticipants();
		this.initBtnCreate();
		Button btnTest = new Button("LookupTest");
		btnTest.addClickListener(listener ->{
			LookupUnternehmen lookup = new LookupUnternehmen();
			this.getUI().addWindow(lookup);
		});
		this.vlContent.addComponent(btnTest);

	}
	
	private void initDfDate(){
		this.dfDate = new DateField("Wann?");
		this.dfDate.setWidth("300px");
		this.dfDate.setResolution(Resolution.MINUTE);
		this.vlContent.addComponent(this.dfDate);
	}
	
	private void initCbEnterprise(){
		this.cbEnterprise = new ComboBox("Wo?");
		this.cbEnterprise.setWidth("300px");
		this.cbEnterprise.setItemCaptionMode(ItemCaptionMode.EXPLICIT);
		//Dummywerte
		
		this.cbEnterprise.addContainerProperty("Enterprise", String.class, null);
		String[] aEnterprise = {"ebm-papst", "Ziehl-Abegg", "Fujitsu", "Toyota", "Sony", "Toyo", "Phyto", "Mercedes Benz", "Pabronko"};
		for(int i = 0; i<aEnterprise.length; i++){
			Item itmEnterprise = this.cbEnterprise.addItem(i);
			itmEnterprise.getItemProperty("Enterprise").setValue(aEnterprise[i]);
			this.cbEnterprise.setItemCaption(i, aEnterprise[i]);
		}
		this.vlContent.addComponent(this.cbEnterprise);
	}
	
	private void initGrdParticipants(){
		this.grdParticipants = new Grid("mit Wem?");
		this.grdParticipants.setSelectionMode(SelectionMode.MULTI);
		this.grdParticipants.setWidth("300px");
		this.grdParticipants.setHeight("400px");
		this.refreshParticipants();
		this.vlContent.addComponent(this.grdParticipants);
	}
	
	private void initParticipantsFilter(){
		HeaderRow filterRow = this.grdParticipants.appendHeaderRow();
		for (Object propId: this.idcParticipants.getContainerPropertyIds()) {
		    HeaderCell cell = filterRow.getCell(propId);
		    TextField tfFilter = new TextField();

		    tfFilter.addTextChangeListener(change -> {
		    	this.idcParticipants.removeContainerFilters(propId);
		        if (! change.getText().isEmpty())
		        	this.idcParticipants.addContainerFilter(
		                new SimpleStringFilter(propId,
		                    change.getText(), true, false));
		    });
		    cell.setComponent(tfFilter);
		}
	}
	
	private void initBtnCreate(){
		this.btnCreate = new Button("Termin erstellen");
		this.btnCreate.setIcon(FontAwesome.PLUS);
		this.btnCreate.setWidth("300px");
		this.btnCreate.addClickListener(listener ->{
			/*	Hier muss eine Abfrage erfolgen ob bei diesem Unternehmen
			 *	vor kurzem oder in naher Zukunft Termine geplant waren
			 *	oder bereits geplant sind*/
			boolean b = false;
			if(b){
				//erstelle termin 
			}else
				this.createWarning();
				
		});
		
		this.vlContent.addComponent(this.btnCreate);
	}
	
	private void initVlLayout(){
		this.vlLayout = new VerticalLayout(this.vlContent);
		this.vlLayout.setSizeFull();
		this.vlLayout.setComponentAlignment(this.vlContent, Alignment.TOP_LEFT);
		this.vlLayout.setStyleName(Reindeer.LAYOUT_BLUE);
		this.setCompositionRoot(this.vlLayout);
	}
	
	private void createWarning(){
		Window warning = new Window();
		VerticalLayout vlFields = new VerticalLayout();
		Button btnYes = new Button("Ja");
		Button btnNo = new Button("Nein");
		
		btnYes.setWidth("200px");
		btnYes.setIcon(FontAwesome.CHECK);
		btnYes.addClickListener(listener ->{
			//erstelle anlage
			Notification.show("Termin wurde erstellt");
			warning.close();
		});
		
		btnNo.setWidth("200px");
		btnNo.setIcon(FontAwesome.CLOSE);
		btnNo.addClickListener(listener ->{
			Notification.show("Termin wurde nicht erstellt");
			warning.close();
		});
		
		vlFields.setWidth("100%");
		vlFields.setMargin(new MarginInfo(true, false, true, false));
		vlFields.addComponent(btnYes);
		vlFields.addComponent(btnNo);
		vlFields.setComponentAlignment(btnYes, Alignment.MIDDLE_CENTER);
		vlFields.setComponentAlignment(btnNo, Alignment.MIDDLE_CENTER);
		
		
		warning.setCaptionAsHtml(true);
		warning.setCaption("<center><h1>Achtung!</h1>"
				+ "<p>Bei diesem Unternehmen sind zeitnahe Termine bereits in planung</p>"
				+ "<p>Möchten Sie diesen Termin dennoch erstellen?</p><center>");
		warning.setSizeFull();
		warning.setModal(true);
		warning.setClosable(false);
		warning.setContent(vlFields);
		
		this.getUI().addWindow(warning);
	}
	
	protected void refreshParticipants(){
		//Hier werden wir evtl. einen BeanContainer verwenden
		this.idcParticipants = new IndexedContainer();
		this.idcParticipants.addContainerProperty("Vorname", String.class, null);
		this.idcParticipants.addContainerProperty("Nachname", String.class, null);
		
		//Dummywerte
		String[] aVorname = {"Albert", "Herbert", "Yoshi", "Sakura", "Robin", "Simon", "Bosse", "Jasmin", "Florian", "Manuel", "Christian"};
		String[] aNachname = {"Terbun", "Remus", "Suzuki", "Shizuki", "Bahr", "Schlarb", "Bosse", "Stribik", "Flurer", "Manu", "Zaengle"};
		for(int i = 0; i<=10;i++){
			Item itm = this.idcParticipants.addItem(i);
			itm.getItemProperty("Vorname").setValue(aVorname[i]);
			itm.getItemProperty("Nachname").setValue(aNachname[i]);
		}
		this.grdParticipants.setContainerDataSource(this.idcParticipants);
		this.initParticipantsFilter();
		
	}
	
	private int enterpriseLookUp(){
		Window w = new Window();
		
		return 0;
	}

}
