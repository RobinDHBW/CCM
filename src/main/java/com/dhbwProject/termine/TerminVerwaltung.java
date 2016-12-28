package com.dhbwProject.termine;

import java.time.LocalDateTime;
import java.util.GregorianCalendar;

import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.calendar.event.BasicEvent;
import com.vaadin.ui.themes.Reindeer;
import com.vaadin.ui.themes.ValoTheme;

public class TerminVerwaltung extends CustomComponent {
	private static final long serialVersionUID = 1L;
	
	private VerticalLayout vlFields;
	private VerticalLayout vlLayout;
	
	private TermineCalendar calendar;
	private LocalDateTime date;
	
	private Button btnMonth;
	private Button btnWeek;
	private Button btnDay;
	
	public TerminVerwaltung(){
		this.date = LocalDateTime.now();
		this.initVlFields();
		this.initVlLayout();
		this.initCalendar();
		this.initButtons();
	}
	
	private void initVlFields(){
		this.vlFields = new VerticalLayout();
		this.vlFields.setSizeUndefined();
		this.vlFields.setSpacing(true);
		this.vlFields.setMargin(new MarginInfo(true, false, true, true));
	}
	
	private void initVlLayout(){
		this.vlLayout = new VerticalLayout(this.vlFields);
		this.vlLayout.setSizeFull();
		this.vlLayout.setComponentAlignment(this.vlFields, Alignment.TOP_LEFT);
//		this.vlLayout.setStyleName(Reindeer.LAYOUT_BLUE);
		this.setCompositionRoot(this.vlLayout);
	}
	
	private void initButtons(){
		HorizontalLayout hlButtons = new HorizontalLayout();
		hlButtons.setSizeUndefined();
		hlButtons.setSpacing(true);
		
		this.btnMonth = new Button("Monatansicht");
//		this.btnMonth.setStyleName(ValoTheme.BUTTON_BORDERLESS);
		this.btnMonth.setWidth("200px");
		this.btnMonth.addClickListener(listener ->{
			this.calendar.setViewMonth();
		});
		this.btnWeek = new Button("Wochenansicht");
//		this.btnWeek.setStyleName(ValoTheme.BUTTON_BORDERLESS);
		this.btnWeek.setWidth("200px");
		this.btnWeek.addClickListener(listener ->{
			this.calendar.setViewWeek();
		});
		this.btnDay = new Button("Tagesansicht");
//		this.btnDay.setStyleName(ValoTheme.BUTTON_BORDERLESS);
		this.btnDay.setWidth("200px");
		this.btnDay.addClickListener(listener ->{
			this.calendar.setViewDay();
		});
		hlButtons.addComponent(this.btnMonth);
		hlButtons.addComponent(this.btnWeek);
		hlButtons.addComponent(this.btnDay);
		this.vlFields.addComponent(hlButtons);
	}
	
	private void initCalendar(){
		this.calendar = new TermineCalendar();
		this.vlFields.addComponent(this.calendar);
		
		//Testevent
//		BasicEvent event = new BasicEvent();
		TerminEvent event = new TerminEvent(0);
		event.setCaption("TEST");
		event.setStart(new GregorianCalendar(this.date.getYear(), 
				this.date.getMonthValue()-1, 
				this.date.getDayOfMonth(), 00, 00, 00).getTime());
		event.setEnd(new GregorianCalendar(this.date.getYear(), 
				this.date.getMonthValue()-1, 
				this.date.getDayOfMonth(), 00, 00, 00).getTime());
		event.setAllDay(true);
		this.calendar.addEvent(event);
	}
}
