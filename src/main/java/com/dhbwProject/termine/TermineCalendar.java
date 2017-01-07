package com.dhbwProject.termine;

import java.time.LocalDateTime;
import java.util.GregorianCalendar;
import java.util.LinkedList;

import com.dhbwProject.backend.DummyDataManager;
import com.dhbwProject.backend.beans.Besuch;
import com.vaadin.ui.Calendar;
import com.vaadin.ui.Window;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.DateClickEvent;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.DateClickHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventClick;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.ForwardEvent;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.ForwardHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.BackwardEvent;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.BackwardHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventClickHandler;

public class TermineCalendar extends Calendar{
	private static final long serialVersionUID = 1L;
	private LocalDateTime date;
	private DummyDataManager dummyData;
//	private LinkedList<Besuch> lTermine;
	
	//Aktuell eine Idee um forward und backward für den Month-View zu realisieren...
	private GregorianCalendar gcStart;
	private GregorianCalendar gcEnd;
	
	public TermineCalendar(DummyDataManager dummyData){
		super();
		this.date = LocalDateTime.now();
		this.gcStart = new GregorianCalendar(this.date.getYear(), 
				this.date.getMonthValue()-1, 
				this.date.getDayOfMonth(), 00, 00, 00);
		this.gcEnd = new GregorianCalendar(this.date.getYear(), 
				this.date.getMonthValue(), 
				this.date.getDayOfMonth(), 00, 00, 00);
		
		super.setSizeFull();
//		super.setFirstVisibleDayOfWeek(1);
//		super.setLastVisibleDayOfWeek(5);
		this.dummyData = dummyData;
		this.setViewMonth();
		this.initDateClickHandler();
		this.initEventClickHandler();
		this.initCalendarEvents();
//		this.initForwardHandler();
//		this.initBackwardHandler();
		
	}
	
	protected void initDateClickHandler(){
		this.setHandler(new DateClickHandler(){
			private static final long serialVersionUID = 1L;

			@Override
			public void dateClick(DateClickEvent event) {
				Window w = new Window();
				w.setCaptionAsHtml(true);
				w.setCaption("<center><h2>Termin anlegen</h2></center>");
				w.center();
				w.setWidth("400px");
				w.setHeight("600px");
				w.setDraggable(true);
				w.setClosable(true);
				w.setModal(false);
				
				TerminAnlage anlage = new TerminAnlage(dummyData, event.getDate());
				anlage.getBtnCreate().addClickListener(listener ->{
					w.close();
				});
				w.setContent(anlage);
				getUI().addWindow(w);
				
			}
			
		});		
	}
	
	protected void initEventClickHandler(){
		this.setHandler(new EventClickHandler(){
			private static final long serialVersionUID = 1L;

			@Override
			public void eventClick(EventClick event) {
				TerminEvent e = (TerminEvent)event.getCalendarEvent();//So irgendwie
				Window w = new Window();
				w.setCaptionAsHtml(true);
				w.setCaption("<center><h2>Termin bearbeiten</h2></center>");
				w.center();
				w.setWidth("400px");
				w.setHeight("600px");
				w.setDraggable(true);
				w.setClosable(true);
				w.setModal(false);
				
				TerminBearbeitung bearbeitung = new TerminBearbeitung(dummyData, e.getBesuch());
				bearbeitung.getBtnUpdate().addClickListener(listener ->{
					w.close();
				});
				w.setContent(bearbeitung);
				getUI().addWindow(w);
				
			}
			
		});	
				
	}
	
	protected void initForwardHandler(){
		this.setHandler(new ForwardHandler(){
			private static final long serialVersionUID = 1L;

			@Override
			public void forward(ForwardEvent event) {
				gcStart = gcEnd;
				gcEnd = new GregorianCalendar(gcEnd.YEAR, gcEnd.MONTH+1, gcEnd.DAY_OF_MONTH, 00, 00);
				setStartDate(gcStart.getTime());
				setEndDate(gcEnd.getTime());	
			}
		});
	}
	
	protected void initBackwardHandler(){
		this.setHandler(new BackwardHandler(){
			private static final long serialVersionUID = 1L;

			@Override
			public void backward(BackwardEvent event) {
				gcEnd = gcStart;
				gcStart = new GregorianCalendar(gcEnd.YEAR, gcEnd.MONTH-1, gcEnd.DAY_OF_MONTH, 00, 00);
				setStartDate(gcStart.getTime());
				setEndDate(gcEnd.getTime());		
			}
			
		});
		
	}
	
	protected void initCalendarEvents(){		
		for(Besuch b : this.dummyData.getlTermin()){
			TerminEvent event = new TerminEvent(b);
			this.addEvent(event);
		}	
	}
	
	protected void setViewMonth(){
		super.setStartDate(new GregorianCalendar(this.date.getYear(), 
				this.date.getMonthValue()-1, 
				this.date.getDayOfMonth(), 00, 00, 00).getTime());
		super.setEndDate(new GregorianCalendar(this.date.getYear(), 
				this.date.getMonthValue(), 
				this.date.getDayOfMonth(), 00, 00, 00).getTime());
	}
	
	protected void setViewWeek(){
		super.setStartDate(new GregorianCalendar(this.date.getYear(), 
				this.date.getMonthValue(), 
				this.date.getDayOfMonth(), 00, 00, 00).getTime());
		super.setEndDate(new GregorianCalendar(this.date.getYear(), 
				this.date.getMonthValue(), 
				this.date.getDayOfMonth(), 00, 00, 00).getTime());
	}
	
	protected void setViewDay(){
		super.setStartDate(new GregorianCalendar(this.date.getYear(), 
				this.date.getMonthValue(), 
				this.date.getDayOfMonth(), 00, 00, 00).getTime());
		super.setEndDate(new GregorianCalendar(this.date.getYear(), 
				this.date.getMonthValue(), 
				this.date.getDayOfMonth(), 00, 00, 00).getTime());
	}

}
