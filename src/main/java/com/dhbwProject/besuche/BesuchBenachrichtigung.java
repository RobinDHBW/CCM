package com.dhbwProject.besuche;

import com.vaadin.server.FontAwesome;
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
	
	public BesuchBenachrichtigung(){
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
		}
		
		private void initContent(){
			this.taNachricht = new TextArea();
			this.taNachricht.setWidth("100%");
			this.taNachricht.setStyleName(ValoTheme.TEXTAREA_BORDERLESS);
			this.taNachricht.setValue("Titel\nZeile\nZeile");
			
			this.vlFields = new VerticalLayout(taNachricht);
			this.vlFields.setMargin(true);
			this.setCompositionRoot(vlFields);
		}
	}


}
