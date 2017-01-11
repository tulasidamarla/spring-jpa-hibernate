package com.web.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.web.model.Attendee;
import com.web.model.Event;
import com.web.service.AttendeeService;

@Controller
public class AttendeeController {

	@Autowired
	private AttendeeService attendeeService;

	@RequestMapping(value = "/attendee", method = RequestMethod.GET)
	public String displayAttendees(Model model) {
		Attendee attendee = new Attendee();
		attendee.setName("Tulasi");
		attendee.setEmail("ram.damarla@gmail.com");
		model.addAttribute(attendee);
		return "attendee";
	}

	@RequestMapping(value = "/attendee", method = RequestMethod.POST)
	public String saveAttendee(@Valid Attendee attendee, BindingResult result, HttpSession session) {
		if (result.hasErrors()) {
			return "attendee";
		} else {
			Event event = (Event) session.getAttribute("event");
			attendee.setEvent(event);
			attendeeService.save(attendee);
			return "redirect:index";
		}
	}
}
