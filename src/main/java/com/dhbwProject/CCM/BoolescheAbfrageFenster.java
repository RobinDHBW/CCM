package com.dhbwProject.CCM;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class BoolescheAbfrageFenster extends Window {
	private static final long serialVersionUID = 1L;
	private  boolean bResult = false;
	
	private Button btnYes;
	private Button btnNo;
	
	
	private BoolescheAbfrageFenster(){
		this.center();
		this.setModal(true);
		this.setWidth("400px");
		this.setHeight("400px");
		this.setClosable(false);
	}
	
	public BoolescheAbfrageFenster(String question){
		this();
		this.setCaptionAsHtml(true);
		this.setCaption(question);
		this.setContent(initContent());
	}
	
	private HorizontalLayout initContent(){
		btnYes = new Button("Ja");
		btnYes.setWidth("150px");
		btnYes.setIcon(FontAwesome.CHECK);
		btnYes.addClickListener(click ->{
			bResult = true;
			this.close();
		});
		
		btnNo = new Button("Nein");
		btnNo.setWidth("150px");
		btnNo.setIcon(FontAwesome.CLOSE);
		btnNo.addClickListener(click ->{
			this.close();
		});
		
		HorizontalLayout layout = new HorizontalLayout(btnYes, btnNo);
		layout.setSizeFull();
		layout.setSpacing(true);
//		layout.setComponentAlignment(btnYes, Alignment.TOP_CENTER);
//		layout.setComponentAlignment(btnNo, Alignment.TOP_CENTER);
		layout.setMargin(true);
		return layout;
	}
	
	public boolean getResult(){
		return bResult;
	}

}
