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
		Query query = em.createQuery("select event from Event event");
		return query.getResultList();
	}

}
