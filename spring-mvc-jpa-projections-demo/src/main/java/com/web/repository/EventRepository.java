package com.web.repository;

import java.util.List;

import com.web.model.Event;


public interface EventRepository {
	
	Event save(Event attendee);

	List<Event> findAll();

}
