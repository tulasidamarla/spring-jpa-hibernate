package com.web.service;

import java.util.List;

import com.web.model.Event;

public interface EventService {
	
	Event save(Event event);

	List<Event> findAllEvents();

}
