Jpa is all about dealing with binding collection of objects to database. There are four types of annotations to bind objects and collections to one another.
1)@OneToOne<br/>
2)@OneToMany<br/>
3)@ManyToOne<br/>
4)@ManyToMany<br/>

Note: These 4 annotations are used in various configurations like Unidirectional, Bidirectional and Cascade.

@OneToOne is the simplest of all, whereas @ManyToMany is the complex one. Let's see an example of  @OneToMany and @ManyToOne.

All the configuration will be in the Entity objects. For ex, An event has a list of attendees. We can describe this relation using the annotations like this.

Event Entity configuration 

	@OneToMany(mappedBy="event", cascade=CascadeType.ALL)
	private List<Attendee> attendees = new ArrayList<>();

Attendee Entity configuration

		@ManyToOne
		private Event event;	
		
Note: mappedBy field defines a property present in the other entity. i.e. In this example, event should be property name in Attendee Entity.		

When working with associated objects, JPA defines two types of fetching strategies name Eager and Lazy. Eager fetches the associated collection/object data when the original object was created. With Lazy(default) fetch type, the associated object/collection data is fetched from DB, when the property is called. To set these,   

	@OneToMany(mappedBy="event", cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	private List<Attendee> attendees = new ArrayList<>();
	
Note: Fetch type lazy will fail because jpa session is valid with in the transactional context. If application tries to lazily fetch once the transaction is completed we get a hibernate exception(org.hibernate.LazyInitializationException). To fix this, the following configuration is required with PersistenceContext.

	@PersistenceContext(type=PersistenceContextType.EXTENDED)
	private EntityManager em;

		