package com.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.web.model.Attendee;
import com.web.repository.AttendeeRepository;

@Service("attendeeService")
public class AttendeeServiceImpl implements AttendeeService {
	
	@Autowired
	private AttendeeRepository attendeeRepository;


	@Transactional
	public Attendee save(Attendee attendee) {
		return attendeeRepository.save(attendee);
	}

}
