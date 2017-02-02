package com.dhbwProject.besuche;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import com.dhbwProject.backend.CCM_Constants;
import com.dhbwProject.backend.dbConnect;
import com.dhbwProject.backend.beans.Benutzer;
import com.dhbwProject.backend.beans.Besuch;
import com.dhbwProject.backend.beans.Gespraechsnotiz;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

public class BesuchBenachrichtigung extends Window {
	private static final long serialVersionUID = 1L;
	
	private TabSheet tabSheet;
	private dbConnect dbConnection;
	
	private BenachrichtigungVerlauf verlauf;
	private NeueNachricht nachricht;
	
	private Besuch bReferenz;
	private Benutzer bUser;
	
	public BesuchBenachrichtigung(Besuch b){
		bReferenz = b;
		dbConnection = (dbConnect)VaadinSession.getCurrent().getSession().getAttribute(CCM_Constants.SESSION_VALUE_CONNECTION);
		bUser = (Benutzer)VaadinSession.getCurrent().getSession().getAttribute(CCM_Constants.SESSION_VALUE_USER);
		this.center();
		this.setWidth("400px");
		this.setHeight("600px");
		this.setCaptionAsHtml(true);
		this.setCaption("<center><h3>Benachrichtigungen</h3></center>");
		this.setContent(this.initContent());
	}
	
	private Panel initContent(){
		this.verlauf = new BenachrichtigungVerlauf();
		this.nachricht = new NeueNachricht();
		this.tabSheet = new TabSheet();
		this.tabSheet.addTab(verlauf, "Nachrichtsverlauf", FontAwesome.FILE_TEXT);
		this.tabSheet.addTab(nachricht, "Neue Nachricht", FontAwesome.PLUS);
		this.tabSheet.setSizeFull();
		
		VerticalLayout layout = new VerticalLayout(tabSheet);
		layout.setSizeFull();
		layout.setMargin(true);
		
		Panel p = new Panel();
		p.setContent(layout);
		return p;
	}
	
	protected void createNachricht(){
		try {
			dbConnection.createGespraechsnotiz(new Gespraechsnotiz(0, nachricht.taNachricht.getValue().getBytes(),
					null, bReferenz.getAdresse().getUnternehmen(), bReferenz, null, bUser));

			nachricht.taNachricht.setValue("");
			verlauf.refreshValue();
			tabSheet.setSelectedTab(0);
		} catch (SQLException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}	
	
	private class NeueNachricht extends CustomComponent{
		private static final long serialVersionUID = 1L;
		private VerticalLayout vlFields;
		private TextArea taNachricht;
		private CheckBox cbEmail;
		private Button btnOK;
		
		private NeueNachricht(){
			this.initContent();
		}
		
		private void initContent(){
			this.taNachricht = new TextArea();
			this.taNachricht.setWidth("100%");
			
			this.cbEmail = new CheckBox();
			cbEmail.setCaption("e-Mail Benachrichtigung?");

			this.btnOK = new Button("Senden");
			this.btnOK.setIcon(FontAwesome.CHECK);
			this.btnOK.addClickListener(click -> createNachricht());
			this.vlFields = new VerticalLayout(taNachricht, cbEmail, btnOK);
			this.vlFields.setSpacing(true);
			this.vlFields.setMargin(true);
			
			this.setCompositionRoot(vlFields);
		}
		
	}
	
	private class BenachrichtigungVerlauf extends CustomComponent{
		private static final long serialVersionUID = 1L;
		private VerticalLayout vlFields;
		
		private BenachrichtigungVerlauf(){
			this.setSizeFull();
			this.initContent();
			try {
				refreshValue();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch(UnsupportedEncodingException e){
				e.printStackTrace();
			}
		}
		
		private void initContent(){
			vlFields = new VerticalLayout();
			vlFields.setSizeFull();
			vlFields.setSpacing(true);
			
			Panel p = new Panel(vlFields);
			p.setSizeFull();
			p.setStyleName(ValoTheme.PANEL_BORDERLESS);
			this.setCompositionRoot(p);
		}
		
		private void refreshValue() throws SQLException, UnsupportedEncodingException{
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy, HH:mm");
			for(Gespraechsnotiz g : dbConnection.getGespraechsnotizByBesuch(bReferenz))
				if(g != null){
					Label lbl = new Label(
							"<b>"+g.getAutor().getNachname()+", "+g.getAutor().getVorname()+": "+dateFormat.format(g.getTimestamp())+"</b><br>"
									+new String(g.getNotiz(), "UTF-8"), ContentMode.HTML);
					vlFields.addComponent(lbl);
					vlFields.setComponentAlignment(lbl, Alignment.TOP_LEFT);
				}
		}
	}


}
