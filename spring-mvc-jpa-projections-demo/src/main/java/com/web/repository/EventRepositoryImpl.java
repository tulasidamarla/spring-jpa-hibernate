package com.web.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.web.model.Event;

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


	@SuppressWarnings("unchecked")
	@Override
	public List<Event> findAll() {
		Query query = em.createQuery("select new com.web.report.model.EventReport(e.name, a.name,a.email)"
				+"from Event e, Attendee a where e.id = a.event.id");
		return query.getResultList();
	}

}
