package com.dhbwProject.termine;

import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Calendar;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

public class TerminAnlage extends CustomComponent {
	private static final long serialVersionUID = 1L;
	
	private VerticalLayout vlContent;
	private VerticalLayout vlLayout;
	
	private Calendar calendar;
	
	public TerminAnlage(){
		this.initVlContent();
		this.initCalendar();
		this.initVlLayout();
	}
	
	private void initVlContent(){
		this.vlContent = new VerticalLayout();
		this.vlContent.setSizeUndefined();
		this.vlContent.setSpacing(true);
		this.vlContent.setMargin(new MarginInfo(true, false, true, false));
	}
	
	private void initCalendar(){
		this.calendar = new Calendar("Terminkalender");
		this.vlContent.addComponent(this.calendar);
		this.calendar.setWidth("600px");
		this.calendar.setHeight("300px");
	}
	
	private void initVlLayout(){
		this.vlLayout = new VerticalLayout(this.vlContent);
		this.vlLayout.setSizeFull();
		this.vlLayout.setComponentAlignment(this.vlContent, Alignment.MIDDLE_CENTER);
		this.vlLayout.setStyleName(Reindeer.LAYOUT_BLUE);
		this.setCompositionRoot(this.vlLayout);
	}

}
