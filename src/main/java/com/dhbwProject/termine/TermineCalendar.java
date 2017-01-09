package com.dhbwProject.termine;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import com.dhbwProject.backend.DummyDataManager;
import com.dhbwProject.backend.beans.Besuch;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Calendar;
import com.vaadin.ui.Window;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.DateClickEvent;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.DateClickHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventClick;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.ForwardEvent;
import com.vaadin.ui.components.calendar.handler.BasicBackwardHandler;
import com.vaadin.ui.components.calendar.handler.BasicForwardHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.BackwardEvent;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventClickHandler;

public class TermineCalendar extends Calendar{
	private static final long serialVersionUID = 1L;
	private LocalDateTime date;
	private DummyDataManager dummyData;
	private BeanItemContainer<TerminEvent> eventContainer;
	
	private GregorianCalendar dateStart;
	private GregorianCalendar dateEnd;
	
	public TermineCalendar(DummyDataManager dummyData){
		super();
//		this.setHeight("800px");
		
		this.date = LocalDateTime.now();
		this.dateStart = new GregorianCalendar(this.date.getYear(), this.date.getMonthValue()-1, 01, 00, 00);
		this.dateEnd = new GregorianCalendar(this.date.getYear(), this.date.getMonthValue()-1, 31, 00, 00);
		
		this.refreshTime();
		this.setLocale(Locale.GERMANY);
		this.setTimeZone(TimeZone.getTimeZone("Europe/Berlin"));
//		super.setSizeFull();
		this.dummyData = dummyData;
		this.eventContainer = new BeanItemContainer<TerminEvent>(TerminEvent.class);
		this.setContainerDataSource(eventContainer);
		
		this.initDateClickHandler();
		this.initEventClickHandler();
		this.initBackwardHandler();
		this.initForwardHandler();
	}
	
	protected void refreshTime(){
		super.setStartDate(dateStart.getTime());
		super.setEndDate(dateEnd.getTime());
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
					refreshCalendarEvents();
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
					refreshCalendarEvents();
				});
				w.setContent(bearbeitung);
				getUI().addWindow(w);			
			}		
		});			
	}
	
	protected void initForwardHandler(){
		this.setHandler(new BasicForwardHandler(){
			private static final long serialVersionUID = 1L;
			protected void setDates(ForwardEvent event, Date start, Date end) {
			 	super.setDates(event, start, end);
			}
		});
	}
	
	protected void initBackwardHandler(){
		this.setHandler(new BasicBackwardHandler(){
			private static final long serialVersionUID = 1L;
			 protected void setDates(BackwardEvent event, Date start, Date end) {
				 super.setDates(event, start, end);
			 }			
		});	
	}
	
	protected void navigateBackward(){
		this.fireNavigationEvent(false);
	}
	
	protected void navigateForward(){
		this.fireNavigationEvent(true);
	}
	
	protected void refreshCalendarEvents(){		
		this.eventContainer.removeAllItems();
		for(Besuch b : this.dummyData.getlTermin())
			this.eventContainer.addBean(new TerminEvent(b));
	}

}
