package com.dhbwProject.besuche;

import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Window;

public class BesuchEntfernen extends Window {
	private static final long serialVersionUID = 1L;
	private boolean bResult = false;
	
	private Button btnYes;
	private Button btnNo;
	private HorizontalLayout hlLayout;
	
	
	public BesuchEntfernen(String bName){
		this.center();
		this.setHeight("300px");
		this.setWidth("300px");
		this.setCaptionAsHtml(true);
		String titel = "<center><h3>Möchten Sie den Termin: "+"<b>"+bName+"</b>"+" wirklich entfernen?</h3></center>";
		String info = "<center><p>Falls Sie der Autor dieses Termins sind wird der gesamte Termin entfernt</p></center>";
		this.setCaption(titel+"\n"+info);
		this.initFields();
		this.setContent(hlLayout);
		Responsive.makeResponsive(this);
	}
	
	private void initFields(){
		this.btnYes = new Button("Ja");
		this.btnYes.setIcon(FontAwesome.CHECK);
		Responsive.makeResponsive(btnYes);
		this.btnYes.addClickListener(click ->{
			bResult = true;
			close();
		});
		
		this.btnNo = new Button("Nein");
		Responsive.makeResponsive(btnNo);
		this.btnNo.setIcon(FontAwesome.CLOSE);
		this.btnNo.addClickListener(click ->{
			close();
		});
		
		this.hlLayout = new HorizontalLayout(btnNo, btnYes);
		this.hlLayout.setSpacing(true);
		this.hlLayout.setMargin(true);
		Responsive.makeResponsive(hlLayout);
		
	}
	
	public boolean getResult(){
		return bResult;
	}

}
