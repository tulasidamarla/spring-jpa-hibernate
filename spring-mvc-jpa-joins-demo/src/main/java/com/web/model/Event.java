package com.web.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="event",schema="eventtracker")
public class Event {

	@Id
	@GeneratedValue
	private Long id;

	@NotNull
	private String name;

	@OneToMany(mappedBy="event", cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	private List<Attendee> attendees = new ArrayList<>();

	public List<Attendee> getAttendees() {
		return attendees;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setAttendees(List<Attendee> attendees) {
		this.attendees = attendees;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

}
