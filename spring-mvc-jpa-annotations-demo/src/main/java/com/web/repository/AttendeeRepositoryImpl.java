package com.web.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.web.model.Attendee;

@Repository("attendeeRepository")
public class AttendeeRepositoryImpl implements AttendeeRepository {

	@PersistenceContext
	private EntityManager em;
	
	
	@Override
	public Attendee save(Attendee attendee) {
		em.persist(attendee);
		em.flush();
		return attendee;
	}

}
