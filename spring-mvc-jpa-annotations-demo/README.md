Entity Annotations
------------------

@Entity --> declares an object as a database entity.
@Table  --> describes more specific details about an entity like name, schema etc.
@Id --> defines Identifier attribute for a simple primary key type
@GeneratedValue --> Used in conjection with @Id. There are four options to choose for Generated value.
	a)IDENTITY --> used to specify an identity column in the database.
	b)AUTO --> Automatically chooses an implementation based on the underlying database.
	c)SEQUENCE --> works with a database sequence if supports. (see the @SEQUENCEGENERATOR)
	d)TABLE --> Specifies that a database will use an identity table and column to ensure uniqueness.(see @TableGenerator) 
		
	
Note: 
1)using IDENTITY has performance issues, because application cannot pre-allocate id values. Id has to be inserted by database and returned to the application after the transaction is completed.

2)Auto defaults to IDENTITY if available. 