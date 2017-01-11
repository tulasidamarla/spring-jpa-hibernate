package com.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.web.model.Event;
import com.web.repository.EventRepository;

@Service("eventService")
public class EventServiceImpl implements EventService {
	
	@Autowired
	private EventRepository eventRepository;


	@Transactional
	public Event save(Event event) {
		return eventRepository.save(event);
	}


	@Override
	@Transactional
	public List<Event> findAllEvents() {
		return eventRepository.findAll();
		
	}

}
