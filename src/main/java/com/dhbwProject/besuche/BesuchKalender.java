package com.dhbwProject.besuche;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.Locale;
import java.util.TimeZone;

import com.dhbwProject.backend.CCM_Constants;
import com.dhbwProject.backend.dbConnect;
import com.dhbwProject.backend.beans.Benutzer;
import com.dhbwProject.backend.beans.Besuch;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Calendar;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.DateClickEvent;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.DateClickHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventMoveHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventClick;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.ForwardEvent;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.MoveEvent;
import com.vaadin.ui.components.calendar.handler.BasicBackwardHandler;
import com.vaadin.ui.components.calendar.handler.BasicForwardHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.BackwardEvent;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventClickHandler;

public class BesuchKalender extends Calendar{
	private static final long serialVersionUID = 1L;
	
	private int year;
	private int month;
	private final int dayStart = 1;
	private int dayEnd;
	
	private int firstDay;
	private int lastDay;
	
	private dbConnect dbConnection;
	private Benutzer bUser;
	private LinkedList<Besuch> lBesuch;
	private BeanItemContainer<BesuchEvent> eventContainer;
	
	private LocalDateTime date;
	private Date dateStart;
	private Date dateEnd;
	
	public BesuchKalender(){
		super();
		this.bUser = (Benutzer)VaadinSession.getCurrent().getSession().getAttribute(CCM_Constants.SESSION_VALUE_USER);
		this.dbConnection = (dbConnect)VaadinSession.getCurrent().getSession().getAttribute(CCM_Constants.SESSION_VALUE_CONNECTION);
		refreshListBesuch();
		resetDateRange();
		refreshDateRange();
		
		this.eventContainer = new BeanItemContainer<BesuchEvent>(BesuchEvent.class);
		this.setContainerDataSource(eventContainer);
		
		this.setLocale(Locale.GERMANY);
		this.setTimeZone(TimeZone.getTimeZone("Europe/Berlin"));
	
		this.initDateClickHandler();
		this.initEventClickHandler();
		this.initBackwardHandler();
		this.initForwardHandler();
		this.initEventMoveHandler();
	}
	
	protected void setTime(){
		super.setStartDate(dateStart);
		super.setEndDate(dateEnd);
	}
	
	protected void initDateClickHandler(){
		this.setHandler(new DateClickHandler(){
			private static final long serialVersionUID = 1L;

			@Override
			public void dateClick(DateClickEvent event) {
				BesuchAnlage anlage = new BesuchAnlage(event.getDate());
				anlage.addCloseListener(close ->{
					if(anlage.getAnlage() == null)
						return;
					refreshListBesuch();
					refreshCalendarEvents();
				});
				getUI().addWindow(anlage);	
			}
		});		
	}
	
	protected void initEventClickHandler(){
		this.setHandler(new EventClickHandler(){
			private static final long serialVersionUID = 1L;

			@Override
			public void eventClick(EventClick event) {
				BesuchEvent e = (BesuchEvent)event.getCalendarEvent();
				BesuchBearbeitung bearbeitung = new BesuchBearbeitung(e.getBesuch());
				bearbeitung.addCloseListener(close ->{
					if(bearbeitung.getBearbeitung() == null)
						return;
					refreshListBesuch();
					refreshCalendarEvents();
				});
				getUI().addWindow(bearbeitung);	
			}		
		});			
	}
	
protected void initEventMoveHandler(){
		this.setHandler(new EventMoveHandler(){
			private static final long serialVersionUID = 1L;

			@Override
			public void eventMove(MoveEvent event) {
				BesuchEvent e = (BesuchEvent)event.getCalendarEvent();
				BesuchBearbeitung bearbeitung = new BesuchBearbeitung(e.getBesuch());
				bearbeitung.setDateStart(e.getStart());
				bearbeitung.setDateEnd(e.getEnd());
				bearbeitung.addCloseListener(close ->{
					if(bearbeitung.getBearbeitung() == null)
						return;
					refreshListBesuch();
					refreshCalendarEvents();
				});
				getUI().addWindow(bearbeitung);	
			}	
		});
	}
	
	protected void initForwardHandler(){
		this.setHandler(new BasicForwardHandler(){
			private static final long serialVersionUID = 1L;
			protected void setDates(ForwardEvent event, Date start, Date end) {
				increaseDateRange();
			 	refreshCalendarEvents();
			}
		});
	}
	
	protected void initBackwardHandler(){
		this.setHandler(new BasicBackwardHandler(){
			private static final long serialVersionUID = 1L;
			 protected void setDates(BackwardEvent event, Date start, Date end) {
				 decreaseDateRange();
				 refreshCalendarEvents();
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
			for(Besuch t  :this.lBesuch)
				if(t.getStartDate().after(dateStart) && t.getEndDate().before(dateEnd))
					this.eventContainer.addBean(new BesuchEvent(t));
	}
	
	protected void increaseDateRange(){
		if(month >=11){
			month = 0;
			year = year +1;
		}else
			this.month = month+1;
		setSpezificDays();
		refreshDateRange();
	}

	protected void decreaseDateRange(){
		if(month <= 0){
			month = 11;
			year = year-1;
		}else
			month = month -1;
		setSpezificDays();
		refreshDateRange();
	}
	
	private void refreshDateRange(){
		this.dateStart = new GregorianCalendar(year, month, dayStart, 01, 01).getTime();
		this.dateEnd = new GregorianCalendar(year, month, dayEnd, 23, 59).getTime();
		this.setTime();
	}
	
	private void setSpezificDays(){
		this.dayEnd = YearMonth.of(year, month+1).lengthOfMonth();
		this.firstDay = YearMonth.of(year, month+1).atDay(dayStart).getDayOfWeek().getValue();
		this.lastDay = YearMonth.of(year, month+1).atDay(dayEnd).getDayOfWeek().getValue();
	}
	
	protected void resetDateRange(){
		this.date = LocalDateTime.now();
		this.year = date.getYear();
		this.month = date.getMonthValue()-1;
		setSpezificDays();
		refreshDateRange();
	}
	
	protected void refreshListBesuch(){
		try {
			this.lBesuch = dbConnection.getBesuchByBenutzer(bUser);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
