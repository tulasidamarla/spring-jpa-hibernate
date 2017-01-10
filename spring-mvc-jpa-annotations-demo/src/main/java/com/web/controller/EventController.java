package com.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.web.model.Event;

@Controller
@SessionAttributes("event")
public class EventController {
	@RequestMapping(value="/event", method = RequestMethod.GET)
	public String displayEventpage(ModelMap model) {
		Event ev = new Event();
		ev.setName("Java User Group");
		model.addAttribute(ev);
		return "event";
	}
	
	@RequestMapping(value="/", method = RequestMethod.GET)
	public String redirect(ModelMap model) {
		return "redirect:event";
	}
	
	@RequestMapping(value="/event", method = RequestMethod.POST)
	public String saveEvent(@ModelAttribute("event") Event event) {
		System.out.println("event " + event.getName() + " is saved");
		return "redirect:index";
	}
	
	@RequestMapping(value="/index", method = RequestMethod.GET)
	public String displayIndexpage() {
		return "index";
	}
}
