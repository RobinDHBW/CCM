package com.dhbwProject.besuche;

import java.io.File;
import java.sql.SQLException;
import java.util.Date;

import com.dhbwProject.backend.CCM_Constants;
import com.dhbwProject.backend.dbConnect;
import com.dhbwProject.backend.beans.Benutzer;
import com.dhbwProject.backend.beans.Besuch;
import com.dhbwProject.backend.beans.Gespraechsnotiz;
import com.dhbwProject.backend.beans.Unternehmen;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
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
	
	private Besuch bReferenz;
	private Benutzer bUser;
	
	public BesuchBenachrichtigung(Besuch b){
		bReferenz = b;
		dbConnection = (dbConnect)VaadinSession.getCurrent().getSession().getAttribute(CCM_Constants.SESSION_VALUE_CONNECTION);
		bUser = (Benutzer)VaadinSession.getCurrent().getSession().getAttribute(CCM_Constants.SESSION_VALUE_USER);
		this.center();
		this.setWidth("400px");
		this.setHeight("500px");
		this.setCaptionAsHtml(true);
		this.setCaption("<center><h3>Benachrichtigungen</h3></center>");
		this.setContent(this.initContent());
	}
	
	private Panel initContent(){
		this.tabSheet = new TabSheet();
		this.tabSheet.addTab(new BenachrichtigungVerlauf(), "Nachrichtsverlauf", FontAwesome.FILE_TEXT);
		this.tabSheet.addTab(new NeueNachricht(), "Neue Nachricht", FontAwesome.PLUS);
		
		VerticalLayout layout = new VerticalLayout(tabSheet);
		layout.setSizeFull();
		layout.setMargin(true);
		
		Panel p = new Panel();
		p.setContent(layout);
		return p;
	}
	
	private class NeueNachricht extends CustomComponent{
		private static final long serialVersionUID = 1L;
		private VerticalLayout vlFields;
		private TextArea taNachricht;
		private Button btnOK;
		
		private NeueNachricht(){
			this.initContent();
		}
		
		private void initContent(){
			this.taNachricht = new TextArea();
			this.taNachricht.setWidth("100%");

			this.btnOK = new Button("Senden");
			this.btnOK.setIcon(FontAwesome.CHECK);
			this.btnOK.addClickListener(click ->{
				//Gespraechsnotiz(int id, File notiz, File bild, Unternehmen unternehmen, Besuch besuch, Date timestamp)
				try {
					dbConnection.createGespraechsnotiz(new Gespraechsnotiz(0, new File(taNachricht.getValue()), null, bReferenz.getAdresse().getUnternehmen(), bReferenz, null));
				} catch (SQLException e) {
					e.printStackTrace();
				}
			});
			this.vlFields = new VerticalLayout(taNachricht, btnOK);
			this.vlFields.setSpacing(true);
			this.vlFields.setMargin(true);
			
			this.setCompositionRoot(vlFields);
		}
		
	}
	
	private class BenachrichtigungVerlauf extends CustomComponent{
		private static final long serialVersionUID = 1L;
		private VerticalLayout vlFields;
		private TextArea taNachricht;
		
		private BenachrichtigungVerlauf(){
			this.initContent();
			try {
				refreshValue();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		private void initContent(){
			this.taNachricht = new TextArea();
			this.taNachricht.setWidth("100%");
			this.taNachricht.setStyleName(ValoTheme.TEXTAREA_BORDERLESS);
			
			this.vlFields = new VerticalLayout(taNachricht);
			this.vlFields.setMargin(true);
			this.setCompositionRoot(vlFields);
		}
		
		private void refreshValue() throws SQLException{
			StringBuilder sbValue = new StringBuilder();
//			for(Gespraechsnotiz g : dbConnection.getGespraechsnotizByBesuch(bReferenz))
//				sbValue.append(g.getNotiz().toString()+"\n");
			Gespraechsnotiz g = dbConnection.getGespraechsnotizByBesuch(bReferenz); 
			
			if(g != null)
				sbValue.append(g.getNotiz().toString()); //TMPORÄR
			taNachricht.setValue(sbValue.toString());
				
		}
	}


}
