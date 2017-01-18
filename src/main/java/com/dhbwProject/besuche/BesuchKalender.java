package com.dhbwProject.besuche;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.Locale;
import java.util.TimeZone;

import com.dhbwProject.backend.CCM_Constants;
import com.dhbwProject.backend.dbConnect;
import com.dhbwProject.backend.beans.Adresse;
import com.dhbwProject.backend.beans.Ansprechpartner;
import com.dhbwProject.backend.beans.Benutzer;
import com.dhbwProject.backend.beans.Besuch;
import com.dhbwProject.backend.beans.Status;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Calendar;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Window;
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
	private LocalDateTime date;
	private dbConnect dbConnection;
	private BeanItemContainer<BesuchEvent> eventContainer;
	
	private GregorianCalendar dateStart;
	private GregorianCalendar dateEnd;
	
	public BesuchKalender(){
		super();
		this.dbConnection = (dbConnect)VaadinSession.getCurrent().getSession().getAttribute(CCM_Constants.SESSION_VALUE_CONNECTION);
		
		this.date = LocalDateTime.now();
		this.dateStart = new GregorianCalendar(this.date.getYear(), this.date.getMonthValue()-1, 01, 00, 00);
		this.dateEnd = new GregorianCalendar(this.date.getYear(), this.date.getMonthValue()-1, 31, 00, 00);
		
		this.refreshTime();
		this.setLocale(Locale.GERMANY);
		this.setTimeZone(TimeZone.getTimeZone("Europe/Berlin"));
		this.eventContainer = new BeanItemContainer<BesuchEvent>(BesuchEvent.class);
		this.setContainerDataSource(eventContainer);
		
		this.initDateClickHandler();
		this.initEventClickHandler();
		this.initBackwardHandler();
		this.initForwardHandler();
		this.initEventMoveHandler();
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
				BesuchAnlage anlage = new BesuchAnlage(event.getDate());
				anlage.addCloseListener(close ->{
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
				Besuch bAlt = e.getBesuch();
				Besuch bNeu = new Besuch(bAlt.getId(), bAlt.getName(),
						e.getStart(), e.getEnd(), bAlt.getAdresse(), bAlt.getStatus(),
						bAlt.getAnsprechpartner(), bAlt.getBesucher(),null , bAlt.getAutor());
				
				
//				try {
//					if(dbConnection.changeBesuch(bNeu, bAlt)){
//						eventContainer.removeItem(e);
//						eventContainer.addBean(new BesuchEvent(dbConnection.getBesuchById(bAlt.getId())));	
//					}
//				} catch (SQLException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
				
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
		Benutzer b = (Benutzer)VaadinSession.getCurrent().getSession().getAttribute(CCM_Constants.SESSION_VALUE_USER);
		try{
			for(Besuch t  :this.dbConnection.getBesuchByBenutzer(b))
				this.eventContainer.addBean(new BesuchEvent(t));
		}catch(SQLException e){
			e.printStackTrace();
		}
	}

}
