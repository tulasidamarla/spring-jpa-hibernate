package com.web.report.model;

public class EventReport {

	private String eventName;
	private String attendeeName;
	private String attendeeEmail;

	public EventReport(String eventName, String attendeeName, String attendeeEmail) {
		this.eventName = eventName;
		this.attendeeName = attendeeName;
		this.attendeeEmail = attendeeEmail;
	}

	public String getAttendeeEmail() {
		return attendeeEmail;
	}

	public String getAttendeeName() {
		return attendeeName;
	}

	public String getEventName() {
		return eventName;
	}

	public void setAttendeeEmail(String attendeeEmail) {
		this.attendeeEmail = attendeeEmail;
	}

	public void setAttendeeName(String attendeeName) {
		this.attendeeName = attendeeName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

}
