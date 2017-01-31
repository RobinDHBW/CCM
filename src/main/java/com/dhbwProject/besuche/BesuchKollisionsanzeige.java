package com.dhbwProject.besuche;

import java.text.SimpleDateFormat;
import java.util.LinkedList;

import com.dhbwProject.backend.beans.Besuch;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

public class BesuchKollisionsanzeige extends Window {
	private static final long serialVersionUID = 1L;
	private Boolean bResult = false;
	private LinkedList<Besuch> lBesuch;
	
	private TextArea taBesuche;
	private Button btnYes;
	private Button btnNo;
	
	private BesuchKollisionsanzeige(){
		this.center();
		this.setModal(true);
		this.setClosable(false);
		this.setHeight("600px");
		this.setWidth("400px");
		this.setCaptionAsHtml(true);
		this.setCaption("<center>Es existieren bereits Besuche<br>im Zeitraum von + oder - 30 Tage<br>Diese Operation dennoch ausführen?</center>");
	}
	
	public BesuchKollisionsanzeige(LinkedList<Besuch> lBesuch){
		this();
		this.lBesuch = lBesuch;
		this.setContent(initContent());
	}
	
	private Panel initContent(){
		taBesuche = new TextArea();
		taBesuche.setWidth("350px");
		taBesuche.setHeight("300px");
		taBesuche.setStyleName(ValoTheme.TEXTAREA_BORDERLESS);
		StringBuilder sbValue = new StringBuilder();
		sbValue.append("Zeitnahe Termine: \n\n");
		for(Besuch b : this.lBesuch){
			sbValue.append(stringPresentation(b));
			sbValue.append("\n \n");
		}
		taBesuche.setValue(sbValue.toString());
		taBesuche.setReadOnly(true);
		
		btnYes = new Button();
		btnYes.setWidth("150px");
		btnYes.setCaption("Ja");
		btnYes.setIcon(FontAwesome.CHECK);
		btnYes.addClickListener(click ->{
			this.bResult = true;
			close();
		});
		
		btnNo = new Button();
		btnNo.setWidth("150px");
		btnNo.setCaption("Nein");
		btnNo.setIcon(FontAwesome.CLOSE);
		btnNo.addClickListener(click ->{
			close();
		});
		
		HorizontalLayout hlButtons = new HorizontalLayout(btnYes, btnNo);
		hlButtons.setSpacing(true);
		hlButtons.setSizeUndefined();
		VerticalLayout hlFields = new VerticalLayout(taBesuche, hlButtons);
		hlFields.setSpacing(true);
		hlFields.setSizeUndefined();
		hlFields.setComponentAlignment(taBesuche, Alignment.TOP_CENTER);
		hlFields.setComponentAlignment(hlButtons, Alignment.TOP_CENTER);
		VerticalLayout layout = new VerticalLayout(hlFields);
		layout.setComponentAlignment(hlFields, Alignment.TOP_CENTER);
		layout.setSizeFull();
		layout.setMargin(true);
		Panel p = new Panel(layout);
		p.setStyleName(ValoTheme.PANEL_BORDERLESS);
		return new Panel(layout);
	}
	
	private String stringPresentation(Besuch b){
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		return "Titel: "+b.getName()+"\n"
		+"Zeitraum: "+dateFormat.format(b.getStartDate())+" bis: "+dateFormat.format(b.getEndDate())+"\n"
		+"Autor: "+b.getAutor().getNachname()+", "+b.getAutor().getVorname();
	}
	
	public boolean getResult(){
		return bResult;
	}
	
	
	
}
