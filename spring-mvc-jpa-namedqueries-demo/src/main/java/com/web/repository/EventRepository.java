package com.web.repository;

import java.util.List;

import com.web.model.Event;
import com.web.report.model.EventReport;


public interface EventRepository {
	
	Event save(Event attendee);

	List<EventReport> findAll();

}
