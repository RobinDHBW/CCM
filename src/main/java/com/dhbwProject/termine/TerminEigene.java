package com.dhbwProject.termine;

import java.time.LocalDateTime;
import java.util.GregorianCalendar;

import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Calendar;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;
import com.vaadin.ui.themes.ValoTheme;

public class TerminEigene extends CustomComponent {
	private static final long serialVersionUID = 1L;
	
	private VerticalLayout vlFields;
	private VerticalLayout vlLayout;
	
	private Calendar calendar;
	private LocalDateTime date;
	
	private Button btnMonth;
	private Button btnWeek;
	private Button btnDay;
	
	public TerminEigene(){
		this.date = LocalDateTime.now();
		this.initVlFields();
		this.initVlLayout();
		this.initButtons();
		this.initCalendar();
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
		this.vlLayout.setStyleName(Reindeer.LAYOUT_BLUE);
		this.setCompositionRoot(this.vlLayout);
	}
	
	private void initButtons(){
		HorizontalLayout hlButtons = new HorizontalLayout();
		hlButtons.setSizeUndefined();
		hlButtons.setSpacing(true);
		
		this.btnMonth = new Button("Monatansicht");
		this.btnMonth.setStyleName(ValoTheme.BUTTON_BORDERLESS);
		this.btnMonth.setWidth("200px");
		this.btnMonth.addClickListener(listener ->{
			this.setViewMonth();
		});
		this.btnWeek = new Button("Wochenansicht");
		this.btnWeek.setStyleName(ValoTheme.BUTTON_BORDERLESS);
		this.btnWeek.setWidth("200px");
		this.btnWeek.addClickListener(listener ->{
			this.setViewWeek();
		});
		this.btnDay = new Button("Tagesansicht");
		this.btnDay.setStyleName(ValoTheme.BUTTON_BORDERLESS);
		this.btnDay.setWidth("200px");
		this.btnDay.addClickListener(listener ->{
			this.setViewDay();
		});
		hlButtons.addComponent(this.btnMonth);
		hlButtons.addComponent(this.btnWeek);
		hlButtons.addComponent(this.btnDay);
		this.vlFields.addComponent(hlButtons);
		
		
	}
	
	private void initCalendar(){
		this.calendar = new Calendar();
		this.calendar.setSizeFull();
		this.calendar.setFirstVisibleDayOfWeek(1);
		this.calendar.setLastVisibleDayOfWeek(5);
		this.setViewMonth();
		this.vlFields.addComponent(this.calendar);
	}
	
	private void setViewMonth(){
		this.calendar.setStartDate(new GregorianCalendar(this.date.getYear(), 
				this.date.getMonthValue()-1, 
				this.date.getDayOfMonth(), 00, 00, 00).getTime());
		this.calendar.setEndDate(new GregorianCalendar(this.date.getYear(), 
				this.date.getMonthValue(), 
				this.date.getDayOfMonth(), 00, 00, 00).getTime());
	}
	
	private void setViewWeek(){
		this.calendar.setStartDate(new GregorianCalendar(this.date.getYear(), 
				this.date.getMonthValue(), 
				this.date.getDayOfMonth(), 00, 00, 00).getTime());
		this.calendar.setEndDate(new GregorianCalendar(this.date.getYear(), 
				this.date.getMonthValue(), 
				this.date.getDayOfMonth(), 00, 00, 00).getTime());
	}
	
	private void setViewDay(){
		this.calendar.setStartDate(new GregorianCalendar(this.date.getYear(), 
				this.date.getMonthValue(), 
				this.date.getDayOfMonth(), 00, 00, 00).getTime());
		this.calendar.setEndDate(new GregorianCalendar(this.date.getYear(), 
				this.date.getMonthValue(), 
				this.date.getDayOfMonth(), 00, 00, 00).getTime());
	}

}
