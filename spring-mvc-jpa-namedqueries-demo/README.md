Named Queries
-------------
With Named queries we can write JPQL queries in a much cleaner way. Named parameters can be used. Usually Named queries are written on the domain objects, but not required. we can write them in a separate class if needed.

Example

	@Entity
	@Table(name="event",schema="eventtracker")
	@NamedQueries({
		@NamedQuery(name=Event.FIND_EVENT_REPORT,query="select new com.web.report.model.EventReport(e.name, a.name,a.email)"
				+"from Event e, Attendee a where e.id = a.event.id")
	})
	public class Event {
		
		public static final String FIND_EVENT_REPORT="findEventReport";
	
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
		
Using NamedQueries
------------------

	@Override
	public List<EventReport> findAll() {
		TypedQuery<EventReport> query= em.createNamedQuery(Event.FIND_EVENT_REPORT, EventReport.class);
		return query.getResultList();
	}

	