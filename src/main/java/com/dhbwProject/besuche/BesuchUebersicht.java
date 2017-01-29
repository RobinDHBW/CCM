package com.dhbwProject.besuche;

import java.sql.Date;
import java.text.SimpleDateFormat;

import com.dhbwProject.backend.CCM_Constants;
import com.dhbwProject.backend.dbConnect;
import com.dhbwProject.backend.beans.Benutzer;
import com.dhbwProject.backend.beans.Besuch;
import com.dhbwProject.backend.beans.Unternehmen;
import com.dhbwProject.benutzer.LookupBenutzer;
import com.dhbwProject.unternehmen.LookupUnternehmen;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

public class BesuchUebersicht extends CustomComponent{
	private static final long serialVersionUID = 1L;
	
	private dbConnect dbConnection;
	private Benutzer bUser;
	private MenuBar mbMenu;
	private Table tblBesuche;
	private IndexedContainer container;
	private VerticalLayout vlLayout;
	
	public BesuchUebersicht(){
		this.dbConnection = (dbConnect)VaadinSession.getCurrent().getSession().getAttribute(CCM_Constants.SESSION_VALUE_CONNECTION);
		this.bUser = (Benutzer)VaadinSession.getCurrent().getSession().getAttribute(CCM_Constants.SESSION_VALUE_USER);
		this.initLayout();
	}
	
	private void initLayout(){
		this.initMenu();
		this.initTable();
		this.vlLayout = new VerticalLayout(mbMenu, tblBesuche);
		this.vlLayout.setMargin(true);
		this.refreshContainer(this.bUser);
		this.setCompositionRoot(vlLayout);
	}
	
	private void initTable(){
		this.tblBesuche = new Table();
		this.initContainer();
		this.tblBesuche.setContainerDataSource(this.container);
		this.tblBesuche.setStyleName(ValoTheme.TABLE_BORDERLESS);
		this.tblBesuche.setSelectable(true);
	}
	
	private void initContainer(){
		this.container = new IndexedContainer();
		this.container.addContainerProperty("Status", Label.class, null);
		this.container.addContainerProperty("Titel", String.class, null);
		this.container.addContainerProperty("Start", String.class, null);
		this.container.addContainerProperty("Ende", String.class, null);
		this.container.addContainerProperty("Unternehmen", String.class, null);
		this.container.addContainerProperty("Adresse", TextArea.class, null);
	}
	
	private void refreshContainer(Benutzer benutzer){
		this.container.removeAllItems();
		try{
			for(Besuch b : this.dbConnection.getBesuchByBenutzer(benutzer))
				this.addItem(b);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private void addItem(Besuch b) throws Exception{
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy, HH:mm");
		Item itm = this.container.addItem(b);
		if(b.getStatus() != null && b.getStatus().getId() == 1)
			itm.getItemProperty("Status").setValue(new Label(FontAwesome.CHECK.getHtml(), ContentMode.HTML));
		itm.getItemProperty("Titel").setValue(b.getName());
		itm.getItemProperty("Start").setValue(dateFormat.format(b.getStartDate()));
		itm.getItemProperty("Ende").setValue(dateFormat.format(b.getEndDate()));
		itm.getItemProperty("Unternehmen").setValue(b.getAdresse().getUnternehmen().getName());
		
		TextArea taAdresse = new TextArea();
		taAdresse.setHeight("100px");
		taAdresse.setStyleName(ValoTheme.TEXTAREA_BORDERLESS);
		taAdresse.setValue(b.getAdresse().getPlz()+"\n"+
		b.getAdresse().getStrasse()+
		"\n"+b.getAdresse().getOrt());
		
		itm.getItemProperty("Adresse").setValue(taAdresse);	
	}
	
	private void initMenu(){
		this.mbMenu = new MenuBar();
		this.mbMenu.setStyleName(ValoTheme.MENUBAR_BORDERLESS);
		
		MenuItem itmSearch = mbMenu.addItem("Suchen", FontAwesome.SEARCH, new MenuBar.Command() {
			
			@Override
			public void menuSelected(MenuItem selectedItem) {
				SuchfensterBesuch suche = new SuchfensterBesuch();
				getUI().addWindow(suche);
				
			}
		});
		
		
		
		MenuItem itmAdd = mbMenu.addItem("Hinzufügen", FontAwesome.PLUS, new MenuBar.Command() {
			private static final long serialVersionUID = 1L;

			@Override
			public void menuSelected(MenuItem selectedItem) {
				BesuchAnlage anlage = new BesuchAnlage();
				anlage.addCloseListener(close ->{
					if(anlage.getAnlage() == null)
						return;
					else{
						try {
							addItem(anlage.getAnlage());
							
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
				getUI().addWindow(anlage);
			}
		});
		
		MenuItem itmChange = mbMenu.addItem("Bearbeiten", FontAwesome.COGS, new MenuBar.Command() {
			
			@Override
			public void menuSelected(MenuItem selectedItem) {
				Notification message = new Notification("");
				message.setPosition(Position.TOP_CENTER);
				if(tblBesuche.getValue() == null){
					message.setStyleName(ValoTheme.NOTIFICATION_FAILURE);
					message.setCaption("Wählen Sie zunächst einen Termin");
					message.show(Page.getCurrent());
					return;
				}
				Besuch b = (Besuch)tblBesuche.getValue();
				BesuchBearbeitung bearbeitung = new BesuchBearbeitung(b);
				bearbeitung.addCloseListener(close ->{
					if(bearbeitung.getBearbeitung() == null)
						return;
					else{
						container.removeItem(b);
						try {
							addItem(bearbeitung.getBearbeitung());
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
				getUI().addWindow(bearbeitung);	
			}
		});
		
		MenuItem itmBenachrichtigung = mbMenu.addItem("Benachrichtigung", FontAwesome.COMMENTS, new MenuBar.Command() {
			
			@Override
			public void menuSelected(MenuItem selectedItem) {
				BesuchBenachrichtigung benachrichtigung = new BesuchBenachrichtigung();
				getUI().addWindow(benachrichtigung);
				
			}
		});
		
		MenuItem itmRemove = mbMenu.addItem("Entfernen", FontAwesome.TRASH, new MenuBar.Command(){
			private static final long serialVersionUID = 1L;

			@Override
			public void menuSelected(MenuItem selectedItem) {
				Notification message = new Notification("");
				message.setPosition(Position.TOP_CENTER);
				if(tblBesuche.getValue() == null){
					message.setStyleName(ValoTheme.NOTIFICATION_FAILURE);
					message.setCaption("Wählen Sie zunächst einen Termin");
					message.show(Page.getCurrent());
					return;
				}
				Besuch b = (Besuch)tblBesuche.getValue();
				BesuchEntfernen entfernen = new BesuchEntfernen(b.getName());
				entfernen.addCloseListener(close ->{
					if(entfernen.getResult()){
						//remove ....
						message.setStyleName(ValoTheme.NOTIFICATION_SUCCESS);
						message.setCaption("Der Termin: "+b.getName()+"wurde erfolgreich entfernt");
						message.show(Page.getCurrent());
					}else{
						message.setStyleName(ValoTheme.NOTIFICATION_SUCCESS);
						message.setCaption("Der Termin: "+b.getName()+"wurde nicht entfernt");
						message.show(Page.getCurrent());
					}
				});
				getUI().addWindow(entfernen);
			}
		});
	}
	
	private class SuchfensterBesuch extends Window{
		private static final long serialVersionUID = 1L;
		TextField tfTitel;
		DateField dfStart;
		DateField dfEnd;
		TextField tfUnternehmen;
		Button btnUnternehmen;
		TextField tfBenutzer;
		Button btnBenutzer;
		
		Unternehmen uFilter;
		Benutzer bFilter;
		Button btnOK;
		
		
		private SuchfensterBesuch(){
			this.center();
			this.setCaptionAsHtml(true);
			this.setCaption("<center><h3>Suchfelder</h3></center>");
			this.setClosable(true);
			this.setModal(false);
			this.setContent(this.initContent());
			this.setWidth("400px");
			this.setHeight("600px");
		}
		
		private Panel initContent(){
			tfTitel = new TextField();
			tfTitel.setWidth("300px");
			tfTitel.setCaption("Titel: ");
			
			dfStart = new DateField();
			dfStart.setResolution(Resolution.MINUTE);
			dfStart.setWidth("300px");
			dfStart.setCaption("Beginn: ");
			
			dfEnd = new DateField();
			dfEnd.setResolution(Resolution.MINUTE);
			dfEnd.setWidth("300px");
			dfEnd.setCaption("Ende: ");
			
			tfUnternehmen = new TextField();
			tfUnternehmen.setWidth("300px");
			
			btnUnternehmen = new Button();
			btnUnternehmen.setIcon(FontAwesome.REPLY);
			btnUnternehmen.setWidth("50px");
			btnUnternehmen.addClickListener(click ->{
				LookupUnternehmen unternehmen = new LookupUnternehmen();
				unternehmen.addCloseListener(close ->{
					if(unternehmen.getSelectionUnternehmen() == null)
						return;
					uFilter = unternehmen.getSelectionUnternehmen();
					tfUnternehmen.setValue(unternehmen.getSelectionUnternehmen().getName());
				});
				getUI().addWindow(unternehmen);
			});
			
			HorizontalLayout hlUnternehmen = new HorizontalLayout(tfUnternehmen, btnUnternehmen);
			hlUnternehmen.setSpacing(true);
			hlUnternehmen.setCaption("Unternehmen: ");
			
			tfBenutzer = new TextField();
			tfBenutzer.setWidth("300px");
			
			btnBenutzer = new Button();
			btnBenutzer.setIcon(FontAwesome.REPLY);
			btnBenutzer.setWidth("50px");
			btnBenutzer.addClickListener(click ->{
				LookupBenutzer benutzer = new LookupBenutzer();
				benutzer.addCloseListener(close ->{
					if(benutzer.getSelection() == null)
						return;
					bFilter = benutzer.getSelection();
					tfBenutzer.setValue(benutzer.getSelection().getNachname()+", "+benutzer.getSelection().getVorname());
				});
				getUI().addWindow(benutzer);
			});
			HorizontalLayout hlBenutzer = new HorizontalLayout(tfBenutzer, btnBenutzer);
			hlBenutzer.setSpacing(true);
			hlBenutzer.setCaption("Benutzer: ");
			
			btnOK = new Button();
			btnOK.setIcon(FontAwesome.SEARCH);
			btnOK.setCaption("Ausführen");
			btnOK.addClickListener(click ->{
				container.removeAllContainerFilters();
				if(bFilter != null){
					refreshContainer(bFilter);
					bFilter = null;
				}
				for(Object pid : container.getContainerPropertyIds()){
					switch(pid.toString()){
					case "Titel":{
						container.addContainerFilter(new SimpleStringFilter(pid, tfTitel.getValue(), true, false));
						break;
					}
					case "Start":{
						break;
					}
					case "Ende":{
						break;
					}
					case "Unternehmen":{
						if(uFilter != null)
							container.addContainerFilter(new SimpleStringFilter(pid, uFilter.getName(), true, false));
						break;
					}
					default :
					
					}
				}
			});
			
			VerticalLayout layoutFields = new VerticalLayout(tfTitel, dfStart, dfEnd, hlUnternehmen, hlBenutzer, btnOK);
			VerticalLayout layout = new VerticalLayout(layoutFields);
			layout.setComponentAlignment(layoutFields, Alignment.TOP_CENTER);
			layout.setMargin(true);
			return new Panel(layout);
		}
	}
	
	
	
	
}
