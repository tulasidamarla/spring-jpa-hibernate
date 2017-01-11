package com.web.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.web.model.Event;
import com.web.service.EventService;

@Controller
@SessionAttributes("event")
public class EventController {

	@Autowired
	private EventService eventService;

	@RequestMapping(value = "/event", method = RequestMethod.GET)
	public String displayEventHomepage(ModelMap model) {
		Event ev = new Event();
		ev.setName("Java User Group");
		model.addAttribute(ev);
		return "event";
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String redirect(ModelMap model) {
		return "redirect:event";
	}

	@RequestMapping(value = "/event", method = RequestMethod.POST)
	public String saveEvent(@Valid Event event, BindingResult result) {
		System.out.println("event " + event.getName() + " is saved");
		if (result.hasErrors()) {
			return "event";
		} else {
			eventService.save(event);
			return "redirect:index";
		}
	}

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String displayIndexpage() {
		return "index";
	}
	
	@RequestMapping(value = "/getEvents", method = RequestMethod.GET)
	public String displayEvents(ModelMap model) {
		List<Event> events = eventService.findAllEvents();
		model.addAttribute("events",events);
		return "attendeereport";
	}
}
