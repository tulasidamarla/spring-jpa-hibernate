package com.web.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import com.web.validations.Phone;

@Entity
@Table(name="attendees",schema="eventtracker")
public class Attendee {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Size(min=2, max=30)
	@Column(name="ATTENDEE_NAME",nullable=false)
	private String name;
	@NotEmpty @Email
	private String email;
	
	@Phone
	private String phone;
	
	@ManyToOne
	private Event event;
	

	public String getEmail() {
		return email;
	}

	public Event getEvent() {
		return event;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getPhone() {
		return phone;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
