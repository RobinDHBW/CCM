package com.dhbwProject.besuche;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;

import com.dhbwProject.backend.CCM_Constants;
import com.dhbwProject.backend.EMailThread;
import com.dhbwProject.backend.dbConnect;
import com.dhbwProject.backend.beans.Benutzer;
import com.dhbwProject.backend.beans.Besuch;
import com.dhbwProject.backend.beans.Gespraechsnotiz;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.Position;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

public class BesuchKollisionsanzeige extends Window {
	private static final long serialVersionUID = 1L;
	private Boolean bResult = false;
	private LinkedList<Besuch> lBesuch;
	
	private Table tblBesuche;
	private IndexedContainer container;
	private TextArea taNachricht;
	private Button btnYes;
	private Button btnNo;
	
	private BesuchKollisionsanzeige(){
		this.center();
		this.setModal(true);
		this.setClosable(false);
//		this.setHeight("600px");
//		this.setWidth("400px");
		this.setStyleName("pwwindow");
		this.setCaptionAsHtml(true);
		this.setCaption("<center>Termine im Zeitraum von 30 Tagen<br>Diese Operation dennoch ausführen?</center>");
		Responsive.makeResponsive(this);
	}
	
	public BesuchKollisionsanzeige(LinkedList<Besuch> lBesuch){
		this();
		this.lBesuch = lBesuch;
		this.setContent(initContent());
	}
	
	private void initContainer(){
		this.container = new IndexedContainer();
		container.addContainerProperty("Termine", TextArea.class, null);
	}
	
	private void refreshContainer(){
		container.removeAllItems();
		for(Besuch b : this.lBesuch)
			addItem(b);
		
	}
	
	private void addItem(Besuch b){
		Item itm = container.addItem(b);
		TextArea taBesuch = new TextArea();
		taBesuch.setStyleName("taarea");
		taBesuch.setValue(stringPresentation(b));
		taBesuch.setStyleName(ValoTheme.TEXTAREA_BORDERLESS);
		taBesuch.setHeight("100px");
	//	taBesuch.setWidth("250px");
		taBesuch.setReadOnly(true);
		Responsive.makeResponsive(taBesuch);
		itm.getItemProperty("Termine").setValue(taBesuch);
	}
	
	private Panel initContent(){
		this.tblBesuche = new Table();
		this.initContainer();
		this.refreshContainer();
		tblBesuche.setContainerDataSource(container);
		tblBesuche.setHeight("250px");
		tblBesuche.setWidth("100%");
		tblBesuche.setSelectable(true);
		Responsive.makeResponsive(tblBesuche);
		
		this.taNachricht = new TextArea();
		this.taNachricht.setHeight("80px");
		this.taNachricht.setWidth("100%");
		Responsive.makeResponsive(taNachricht);
		this.taNachricht.setInputPrompt("Schreiben Sie eine Nachricht an den Autor eines kollidierenden Termins");
		Button btnNachricht = new Button();
		btnNachricht.setWidth("100%");
		Responsive.makeResponsive(btnNachricht);
		btnNachricht.setIcon(FontAwesome.COMMENT);
		btnNachricht.setCaption("Nachricht senden");
		btnNachricht.addClickListener(click ->{
			if(this.tblBesuche.getValue() == null){
				Notification message = new Notification("Wählen Sie einen kollidierenden Besuch");
				message.setStyleName(ValoTheme.NOTIFICATION_FAILURE);
				message.setPosition(Position.TOP_CENTER);
				message.show(Page.getCurrent());
				return;
			}
			if(taNachricht.getValue() == null || taNachricht.getValue().length() <= 0){
				Notification message = new Notification("Schreiben Sie eine Nachricht");
				message.setStyleName(ValoTheme.NOTIFICATION_FAILURE);
				message.setPosition(Position.TOP_CENTER);
				message.show(Page.getCurrent());
				return;
			}
			try {
				Besuch b = (Besuch)this.tblBesuche.getValue();
				Benutzer bUser = (Benutzer)VaadinSession.getCurrent().getSession().getAttribute(CCM_Constants.SESSION_VALUE_USER);
				dbConnect connection = (dbConnect)VaadinSession.getCurrent().getSession().getAttribute(CCM_Constants.SESSION_VALUE_CONNECTION);
				Gespraechsnotiz gNeu = connection.createGespraechsnotiz(new Gespraechsnotiz(0, taNachricht.getValue().getBytes(),
						null, b.getAdresse().getUnternehmen(), b, null, bUser));

				if(gNeu != null)
					this.sendMailByComment(b, bUser, gNeu);
					
				taNachricht.setValue("");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
		VerticalLayout vlNachricht = new VerticalLayout(taNachricht, btnNachricht);
		vlNachricht.setSpacing(true);
		Responsive.makeResponsive(vlNachricht);
		btnYes = new Button();
		btnYes.setWidth("100%");
		btnYes.setCaption("Ja");
		Responsive.makeResponsive(btnYes);
		btnYes.setIcon(FontAwesome.CHECK);
		btnYes.addClickListener(click ->{
			this.bResult = true;
			close();
		});
		
		btnNo = new Button();
		btnNo.setWidth("100%");
		btnNo.setCaption("Nein");
		Responsive.makeResponsive(btnNo);
		btnNo.setIcon(FontAwesome.CLOSE);
		btnNo.addClickListener(click ->{
			close();
		});
		
		HorizontalLayout hlButtons = new HorizontalLayout(btnYes, btnNo);
		hlButtons.setWidth("100%");
		hlButtons.setSpacing(true);
		Responsive.makeResponsive(hlButtons);
		VerticalLayout hlFields = new VerticalLayout(tblBesuche, vlNachricht, hlButtons);
		hlFields.setSpacing(true);
		hlFields.setComponentAlignment(tblBesuche, Alignment.TOP_CENTER);
		hlFields.setComponentAlignment(vlNachricht, Alignment.TOP_CENTER);
		hlFields.setComponentAlignment(hlButtons, Alignment.TOP_CENTER);
		Responsive.makeResponsive(hlFields);
		hlFields.setSizeFull();
		VerticalLayout layout = new VerticalLayout(hlFields);
		layout.setComponentAlignment(hlFields, Alignment.TOP_CENTER);
		layout.setSizeFull();
		layout.setMargin(true);
		Panel p = new Panel(layout);
		Responsive.makeResponsive(layout);
		Responsive.makeResponsive(p);
		p.setStyleName(ValoTheme.PANEL_BORDERLESS);
		return new Panel(layout);
	}
	
	private String stringPresentation(Besuch b){
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy, HH:mm");
		return b.getName()+"\n"
			+dateFormat.format(b.getStartDate())+" Uhr \n"
			+b.getAutor().getNachname()+", "+b.getAutor().getVorname();
	}
	
	public boolean getResult(){
		return bResult;
	}
	
	private void sendMailByComment(Besuch bReferenz, Benutzer bUser, Gespraechsnotiz gNeu){
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy, HH:mm");
		ArrayList<String> eMailList = new ArrayList<String>();
		String titel = "Neue Nachricht zu: "+bReferenz.getName();
		String inhalt = "<b>"+bUser.getNachname()+", "+bUser.getVorname()+": "+dateFormat.format(gNeu.getTimestamp())+"</b><br>"
				+taNachricht.getValue();
		
		if(bUser.getEmail() != null)
			eMailList.add(bReferenz.getAutor().getEmail());
		EMailThread thread = new EMailThread(eMailList, titel, inhalt);
		thread.start();
	}
	
	
	
}
