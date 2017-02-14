package com.dhbwProject.besuche;

import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.LinkedList;

import com.dhbwProject.backend.CCM_Constants;
import com.dhbwProject.backend.EMailThread;
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
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
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
	
	private LinkedList<Besuch> lBesuch;
	private Date currentTime;
	private String titelAnzeige = null;
	private Benutzer bAnzeige;
	private Unternehmen uAnzeige;
	private Date dStart;
	private Date dEnd;
	private boolean alleBenutzer;
	
	public BesuchUebersicht(){
		this.dbConnection = (dbConnect)VaadinSession.getCurrent().getSession().getAttribute(CCM_Constants.SESSION_VALUE_CONNECTION);
		this.bUser = (Benutzer)VaadinSession.getCurrent().getSession().getAttribute(CCM_Constants.SESSION_VALUE_USER);
		this.bAnzeige = bUser;
		LocalDateTime localDate = LocalDateTime.now();
		this.currentTime = new Date(new GregorianCalendar(localDate.getYear(), localDate.getMonthValue()-1, localDate.getDayOfMonth(), 00, 00).getTime().getTime());
		dStart = currentTime;
		this.initLayout();
		Responsive.makeResponsive(this);

	}
	
	private void initLayout(){
		this.initMenu();
		this.initTable();
		this.vlLayout = new VerticalLayout(mbMenu, tblBesuche);
		this.vlLayout.setMargin(true);
		this.refreshContainer(dStart, dEnd);
		Responsive.makeResponsive(vlLayout);
		this.setCompositionRoot(vlLayout);
	}
	
	private void initTable(){
		this.tblBesuche = new Table();
		this.initContainer();
		this.tblBesuche.setContainerDataSource(this.container);
		this.tblBesuche.setStyleName(ValoTheme.TABLE_BORDERLESS);
		this.tblBesuche.setSelectable(true);
		this.tblBesuche.setHeight("500px");
		Responsive.makeResponsive(tblBesuche);
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
	
	private void refreshContainer(Date dStart, Date dEnd){
		this.container.removeAllItems();
		this.container.removeAllContainerFilters();
		refreshBesuchListe();
		try{
			for(Besuch b : lBesuch){
				if(dStart !=null && dEnd != null){
					if(b.getStartDate().after(dStart) && b.getEndDate().before(dEnd))
						addItem(b);
					continue;
				}
				if(dStart != null){
					if(b.getStartDate().after(dStart))
						addItem(b);
					continue;
				}
				if(dEnd != null){
					if(b.getEndDate().before(dEnd))
						addItem(b);
					continue;
				}
				addItem(b);
			}
			if(titelAnzeige != null)
				container.addContainerFilter(new SimpleStringFilter("Titel", titelAnzeige, true, false));
			if(uAnzeige != null)
				container.addContainerFilter(new SimpleStringFilter("Unternehmen", uAnzeige.getName(), true, false));
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private void refreshBesuchListe(){
		try {
			if(!alleBenutzer)
				lBesuch = this.dbConnection.getBesuchByBenutzer(bAnzeige);
			else
				lBesuch = this.dbConnection.getAllBesuche();
		} catch (SQLException e) {
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
		Responsive.makeResponsive(taAdresse);
		taAdresse.setStyleName(ValoTheme.TEXTAREA_BORDERLESS);
		taAdresse.setValue(b.getAdresse().getPlz()+"\n"+
		b.getAdresse().getStrasse()+
		"\n"+b.getAdresse().getOrt());
		
		itm.getItemProperty("Adresse").setValue(taAdresse);	
	}
	
	private void initMenu(){
		this.mbMenu = new MenuBar();
		this.mbMenu.setStyleName(ValoTheme.MENUBAR_BORDERLESS);
		Responsive.makeResponsive(mbMenu);
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
					if(anlage.getAnlage() != null){
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
					if(bearbeitung.getBearbeitung() != null){
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
				Notification message = new Notification("");
				message.setPosition(Position.TOP_CENTER);
				if(tblBesuche.getValue() == null){
					message.setStyleName(ValoTheme.NOTIFICATION_FAILURE);
					message.setCaption("Wählen Sie zunächst einen Termin");
					message.show(Page.getCurrent());
					return;
				}
				Besuch b = (Besuch)tblBesuche.getValue();
				BesuchBenachrichtigung benachrichtigung = new BesuchBenachrichtigung(b);
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
						try {
							container.removeItem(dbConnection.deleteBenutzerFromBesuch(b, bUser));
							sendMailByRemoveParticipant(b, bUser);
							message.setStyleName(ValoTheme.NOTIFICATION_SUCCESS);
							message.setCaption("Der Termin: "+b.getName()+"wurde erfolgreich entfernt");
							message.show(Page.getCurrent());
						} catch (SQLException e) {
							e.printStackTrace();
						}
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
	
	private void sendMailByRemoveParticipant(Besuch besuch, Benutzer benutzer){
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy, HH:mm");
		ArrayList<String> eMailList = new ArrayList<String>();
		String titel = "Terminabmeldung : "+besuch.getName();
		String inhalt = "<b>Teilnehmenr: </b>"
				+benutzer.getNachname()+", "+benutzer.getVorname()+"<br>"
				+"wurde von dem Termin: "+besuch.getName()+" entfernt";
			if(besuch.getAutor().getEmail() != null)
				eMailList.add(besuch.getAutor().getEmail());
		
		EMailThread thread = new EMailThread(eMailList, titel, inhalt);
		thread.start();
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
		CheckBox cbAlleBenutzer;
		
		Button btnOK;
		Button btnReset;
		
		
		private SuchfensterBesuch(){
			this.center();
			this.setCaptionAsHtml(true);
			this.setCaption("<center><h3>Suchfelder</h3></center>");
			this.setClosable(true);
			this.setModal(false);
			this.setContent(this.initContent());
			this.setWidth("400px");
			this.setHeight("600px");
			setTitelAnzeige(titelAnzeige);
			setBenutzer(bAnzeige);
			setUnternehmen(uAnzeige);
			Responsive.makeResponsive(this);
		}
		
		private Panel initContent(){
			tfTitel = new TextField();
			tfTitel.setWidth("300px");
			tfTitel.setCaption("Titel: ");
			Responsive.makeResponsive(tfTitel);
			tfTitel.addValueChangeListener(change ->{
				if(change.getProperty().getValue() == null)
					return;
				setTitelAnzeige(change.getProperty().getValue().toString());
			});
			
			dfStart = new DateField();
			dfStart.setResolution(Resolution.MINUTE);
			dfStart.setWidth("300px");
			Responsive.makeResponsive(dfStart);
			dfStart.setCaption("Beginn: ");
			dfStart.setValue(dStart);
			dfStart.addValueChangeListener(change ->{
				if(change.getProperty().getValue() == null)
						return;
				dStart = new Date(((java.util.Date)change.getProperty().getValue()).getTime());
			});
			
			dfEnd = new DateField();
			dfEnd.setResolution(Resolution.MINUTE);
			dfEnd.setWidth("300px");
			Responsive.makeResponsive(dfEnd);
			dfEnd.setCaption("Ende: ");
			dfEnd.setValue(dEnd);
			dfEnd.addValueChangeListener(change ->{
				if(change.getProperty().getValue() == null)
					return;
				dEnd = new Date(((java.util.Date)change.getProperty().getValue()).getTime());
			});
			
			tfUnternehmen = new TextField();
			tfUnternehmen.setWidth("300px");
			tfUnternehmen.setReadOnly(true);
			Responsive.makeResponsive(tfUnternehmen);
			
			btnUnternehmen = new Button();
			btnUnternehmen.setIcon(FontAwesome.REPLY);
			btnUnternehmen.setWidth("50px");
			Responsive.makeResponsive(btnUnternehmen);
			btnUnternehmen.addClickListener(click ->{
				LookupUnternehmen unternehmen = new LookupUnternehmen();
				unternehmen.addCloseListener(close ->{
					if(unternehmen.getSelectionUnternehmen() == null)
						return;
					setUnternehmen(unternehmen.getSelectionUnternehmen());
				});
				getUI().addWindow(unternehmen);
			});
			

				HorizontalLayout hlUnternehmen = new HorizontalLayout(tfUnternehmen, btnUnternehmen);
				hlUnternehmen.setSpacing(true);
				hlUnternehmen.setCaption("Unternehmen: ");
				Responsive.makeResponsive(hlUnternehmen);
				tfBenutzer = new TextField();
				tfBenutzer.setWidth("300px");
				tfBenutzer.setReadOnly(true);
				Responsive.makeResponsive(tfBenutzer);

				btnBenutzer = new Button();
				btnBenutzer.setIcon(FontAwesome.REPLY);
				btnBenutzer.setWidth("50px");
				Responsive.makeResponsive(btnBenutzer);
				btnBenutzer.addClickListener(click ->{
					LookupBenutzer benutzer = new LookupBenutzer();
					benutzer.addCloseListener(close ->{
						if(benutzer.getSelection() == null)
							return;
						setBenutzer(benutzer.getSelection());
					});
					getUI().addWindow(benutzer);
				});
				HorizontalLayout hlBenutzer = new HorizontalLayout(tfBenutzer, btnBenutzer);
				hlBenutzer.setSpacing(true);
				hlBenutzer.setCaption("Benutzer: ");
				Responsive.makeResponsive(hlBenutzer);
				cbAlleBenutzer = new CheckBox();
				Responsive.makeResponsive(cbAlleBenutzer);
				cbAlleBenutzer.setCaption("Alle Benutzer?");
				cbAlleBenutzer.addValueChangeListener(valueChange ->{
					alleBenutzer = cbAlleBenutzer.getValue();
				});
			
			btnOK = new Button();
			btnOK.setIcon(FontAwesome.SEARCH);
			btnOK.setCaption("Ausführen");
			Responsive.makeResponsive(btnOK);

			btnOK.addClickListener(click -> refreshContainer(dStart, dEnd));
			
			btnReset = new Button();
			btnReset.setIcon(FontAwesome.REPLY_ALL);
			btnReset.setCaption("Zurücksetzen");
			Responsive.makeResponsive(btnReset);
			btnReset.addClickListener(click ->{
				setTitelAnzeige(null);
				setBenutzer(bUser);
				setUnternehmen(null);
				dStart = currentTime;
				dEnd = null;
				dfStart.setValue(dStart);
				dfEnd.setValue(dEnd);
				cbAlleBenutzer.setValue(false);
				refreshContainer(dStart, dEnd);
			});
			HorizontalLayout hlButtons = new HorizontalLayout(btnReset, btnOK);
			hlButtons.setSpacing(true);

			Responsive.makeResponsive(hlButtons);
			VerticalLayout layoutFields;
			if(bUser.getRolle().getId()>1)
				layoutFields = new VerticalLayout(tfTitel, dfStart, dfEnd, hlUnternehmen, hlButtons);
			else
				layoutFields = new VerticalLayout(tfTitel, dfStart, dfEnd, hlUnternehmen,hlBenutzer, cbAlleBenutzer, hlButtons);
					
			VerticalLayout layout = new VerticalLayout(layoutFields);
			layout.setComponentAlignment(layoutFields, Alignment.TOP_CENTER);
			layout.setMargin(true);
			Panel p = new Panel();
			p.setContent(layout);
			Responsive.makeResponsive(layout);
			Responsive.makeResponsive(p);
			return p;
		}
		
		private void setBenutzer(Benutzer b){
			bAnzeige = b;
			tfBenutzer.setReadOnly(false);
			tfBenutzer.setValue(bAnzeige.getNachname()+", "+bAnzeige.getVorname());
			tfBenutzer.setReadOnly(true);
		}
		
		private void setUnternehmen(Unternehmen u){
			uAnzeige = u;
			tfUnternehmen.setReadOnly(false);
			if(uAnzeige != null)
				tfUnternehmen.setValue(u.getName());
			else tfUnternehmen.setValue("");
			tfUnternehmen.setReadOnly(true);
		}
		
		private void setTitelAnzeige(String titel){
			titelAnzeige = titel;
			if(titelAnzeige != null)
				tfTitel.setValue(titel);
			else
				tfTitel.setValue("");
		}
		
	}
		
}
