package com.web.service;

import java.util.List;

import com.web.model.Event;
import com.web.report.model.EventReport;

public interface EventService {
	
	Event save(Event event);

	List<EventReport> findAllEvents();

}
