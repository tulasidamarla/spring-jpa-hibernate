package com.web.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.web.model.Event;
import com.web.report.model.EventReport;

@Repository("eventRepository")
public class EventRepositoryImpl implements EventRepository {

	@PersistenceContext(type=PersistenceContextType.EXTENDED)
	private EntityManager em;
	
	
	@Override
	public Event save(Event event) {
		em.persist(event);
		em.flush();
		return event;
	}


	@Override
	public List<EventReport> findAll() {
		TypedQuery<EventReport> query= em.createNamedQuery(Event.FIND_EVENT_REPORT, EventReport.class);
		return query.getResultList();
	}

}
