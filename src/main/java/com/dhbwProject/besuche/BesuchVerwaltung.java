package com.dhbwProject.besuche;

import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.NativeButton;
import com.vaadin.ui.VerticalLayout;

public class BesuchVerwaltung extends CustomComponent {
	private static final long serialVersionUID = 1L;

	private HorizontalLayout hlFields;
	private VerticalLayout vlLayout;

	private BesuchKalender calendar;
	private Button btnBackward;
	private Button btnForward;

	public BesuchVerwaltung() {
		this.initFields();
		this.initVlLayout();
		Responsive.makeResponsive(this);
	}

	private void initVlLayout() {
		this.vlLayout = new VerticalLayout(this.hlFields);
		this.vlLayout.setSizeFull();
		this.vlLayout.setComponentAlignment(this.hlFields, Alignment.TOP_LEFT);
		this.setCompositionRoot(this.vlLayout);
		Responsive.makeResponsive(vlLayout);
	}
	
	private void initFields(){
		this.hlFields = new HorizontalLayout();
		this.hlFields.setSizeUndefined();
		this.hlFields.setMargin(new MarginInfo(true, false, true, true)); 
		Responsive.makeResponsive(hlFields);
		
		this.btnBackward = new NativeButton();
		this.btnBackward.setWidth("50px");
		Responsive.makeResponsive(btnBackward);
		this.btnBackward.setStyleName("btnwhite");
		this.btnBackward.setIcon(FontAwesome.BACKWARD);
		this.btnBackward.addClickListener(listener ->{
			this.calendar.navigateBackward();
		});
		hlFields.addComponent(this.btnBackward);
		hlFields.setComponentAlignment(this.btnBackward, Alignment.TOP_LEFT);
		
		this.calendar = new BesuchKalender();
		hlFields.addComponent(this.calendar);
		hlFields.setComponentAlignment(this.calendar, Alignment.TOP_CENTER);
		
		this.btnForward = new NativeButton();
		this.btnForward.setWidth("50px");
		Responsive.makeResponsive(btnForward);
		this.btnForward.setStyleName("btnwhite");
		this.btnForward.setIcon(FontAwesome.FORWARD);
		this.btnForward.addClickListener(listener ->{
			this.calendar.navigateForward();
		});
		hlFields.addComponent(this.btnForward);
		hlFields.setComponentAlignment(this.btnForward, Alignment.TOP_RIGHT);
	}
	
	public void refresh(){
		this.calendar.resetDateRange();
		this.calendar.refreshCalendarEvents();
	}
}
