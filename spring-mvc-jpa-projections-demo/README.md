Projections
-----------
Projection is a great way to present objects to UI.

Using Projections we can select the fields that are required and create an object out of the fields.<br> 
Using projections complex joins can be done and the results are used to create projection objects.<br> 
Objects are created using JPQL syntax.<br>
Projection objects are not necessarily JPA entities.<br>
Projection objects need parameterized constructors. 

Using Projection
----------------

	Query query = em.createQuery("select new com.web.report.model.EventReport(e.name, a.name,a.email)"
				+"from Event e, Attendee a where e.id = a.event.id");
				
				
				
				 

  
