# JPA

- JPA is a specification to work with ORM tools.
- It is used for accessing, persisting and managing data between java objects and relational databases.
- JPA is not SQL, it uses JPQL instead. It is heavily focused on POJOs.
- Many providers like Hibernate, Top link, ObjectDB, OpenJPA, DataNucleus, Versant etc have implemented JPA specification.
- Older configurations need persistent.xml file which has settings like DataSource, transaction manager ,caching etc.
- With spring-JPA different configurations per environment can be achieved, and easier to test.
- Spring doesn't need xml configuration.
  - If xml configuration is preferred, then an empty persistent.xml should be provided at the location `src/main/resources/META-INF`.
  - To develop an application using JPA, the following configuration changes are required for in web.xml.
```
	<context-param>
		<param-name>contextConfigLocation</param-name>
		<param-value>classpath:/jpaContext.xml</param-value>
	</context-param>
	<listener>
		<listener-class>org.springframework.web.context.ContextLoaderListener
	</listerner>
```
- ContextLoaderListener class looks for the context param with name "contextConfigLocation" and loads the jpaContext.xml file.
- With spring jpaContext.xml file is used inplace of persitence.xml.
- Unlike, non-spring applications persistence.xml name does not matter here.
- This file contains configuration for EntityManagerFactory, Transaction Manager, Annotation Configuration, Datasource configuration etc.
- The jpaContext.xml file should contain the following lines at the start.
```
	<context:annotation-config/>
	<bean class="org.springframework.orm.jpa.support.PersistenceAnnotationBeanPostProcessor" />
```
- The JPA configuration should contain the following bean definitions.
```
	EntityManagerFactory
	TransactionManager
	DataSource
```
- EntityManagerFactory class hooks jpa to the application.
  - Spring has an implementation of EntityManagerFactory named LocalContainerEntityManagerFactoryBean which is located in spring-orm.jar file.
  - This references the persistence unit. 
  - It also defines vendor and vendor specific jpa properties. 

- JPA/Hibernate doesn't provide transaction support.
  - Application needs to provide a JPATransactionManager which takes an EntityManagerFactory as reference.
  - This class JPATransactionManager is present in spring-tx.jarfile.

- Datasource configuration is added using spring's org.springframework.jdbc.datasource.DriverManagerDatasource.
  - Third party libraries like apache also provide support for datasource configuration.   
  - DriverManagerDatasource class is present in spring-jdbc.jar file.

Sample XML configuration(jpaContext.xml)
----------------------------------------

	<?xml version="1.0" encoding="UTF-8"?>
	<beans xmlns="http://www.springframework.org/schema/beans"
		xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
		xmlns:context="http://www.springframework.org/schema/context"
		xmlns:tx="http://www.springframework.org/schema/tx"
		xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
			http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-4.0.xsd
			http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx-4.0.xsd">
		<context:annotation-config/>
		<bean class="org.springframework.orm.jpa.support.PersistenceAnnotationBeanPostProcessor"/>
		
		<bean id="entityManagerFactory" class="org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean">
			<property name="persistenceUnitName" value="punit"/>
			<property name="dataSource" ref="dataSource"></property>
			<property name="jpaVendorAdapter">
				<bean class="org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter">
					<property name="showSql" value="true"/>
				</bean>
			</property>
			<property name="jpaPropertyMap">
				<map>
					<entry key="hibernate.dialect" value="org.hibernate.dialect.MySQLInnoDBDialect"/>
					<entry key="hibernate.hbm2ddl.auto" value="update"/>
					<entry key="hibernate.format_sql" value="true"/>
				</map>
			</property>
			
		</bean>
		
		<bean id="transactionManager" class="org.springframework.orm.jpa.JpaTransactionManager">
			<property name="entityManagerFactory" ref="entityManagerFactory"/>
		</bean>
		
		<bean id="dataSource" class="org.springframework.jdbc.datasource.DriverManagerDataSource">
			<property name="driverClassName" value="com.mysql.jdbc.Driver"/>
			<property name="url" value="jdbc:mysql://localhost:3306/eventtracker?autoReconnect=true"/>
			<property name="username" value="root"/>
			<property name="password" value="password"/>
		</bean>
	
	</beans>

Java Configuration
------------------

	@Bean
	  public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
	    dataSource.setDriverClassName("com.mysql.jdbc.Driver");
	    dataSource.setUrl("jdbc:mysql://localhost:3306/eventtracker?autoReconnect=true");
	    dataSource.setUsername("root");
	    dataSource.setPassword("password");
	    return dataSource;
	  }
	 
	  @Bean
	  public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
	    LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
	    entityManagerFactory.setPersistenceUnitName("punit");
	    entityManagerFactory.setDataSource(dataSource);
	    entityManagerFactory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
	    entityManagerFactory.setJpaDialect(new HibernateJpaDialect());
	    entityManagerFactory.setPackagesToScan("com.web.model"); 
	    entityManagerFactory.setJpaPropertyMap(hibernateJpaProperties());
	    return entityManagerFactory;
	  }
	 
	  private Map<String, ?> hibernateJpaProperties() {
	    HashMap<String, String> properties = new HashMap<>();
	    properties.put("hibernate.hbm2ddl.auto", "create");
	    properties.put("hibernate.show_sql", "true");
	    properties.put("hibernate.format_sql", "true");
	    properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5InnoDBDialect");
	    return properties;
	  }
	 
	  @Bean
	  public JpaTransactionManager transactionManager(EntityManagerFactory emf) {
	    JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
	    jpaTransactionManager.setEntityManagerFactory(emf);
	    return jpaTransactionManager;
	  }

PersistenceContext
------------------
- Spring provides @PersistenceContext annotation which injects entity manager into the application.
```
	@Repository("attendeeRepository")
	public class AttendeeRepositoryImpl implements AttendeeService {

		@PersistenceContext
		private EntityManager em;
		
		@Override
		public Attendee save(Attendee attendee) {
			em.persist(attendee);
			em.flush();
			return attendee;
		}
	}
```
- An EntityManager always associated with a persistence context.
- A persistent context is a set of entities.
  - Within the persistence context, the entity instances and their lifecycle are managed.
- The EntityManager API is used to create and remove persistence entity instances.
- Set of entities that can be managed by a given EntityManager instance are defined by a persistence unit.
- PersistenceContext works in a transactioncal context.
- To make the PersistenceContext work in a transactional context, the following configurations are required.
  - Mark the service method with @Transactional
  - Mark the Java configuration class with @EnableTransactionManagement
  - For xml, use the following configuration to enable transaction management.
```
 <tx:annotation-driven transaction-manager="transactionManager" />
```

Entity Annotations
------------------
- @Entity --> declares an object as a database entity.<br>
- @Table  --> describes more specific details about an entity like name, schema etc. For ex,<br>
- @Id --> defines Identifier attribute for a simple primary key type<br>
- @GeneratedValue --> Used in conjection with @Id. There are four options to choose for Generated value.<br>
  - AUTO --> Automatically chooses an implementation based on the underlying database.<br>
  - IDENTITY --> used to specify an identity column in the database.<br>
  - SEQUENCE --> works with a database sequence if supports. (see the @SEQUENCEGENERATOR)<br>
  - TABLE --> Specifies that a database will use an identity table and column to ensure uniqueness.(see @TableGenerator) <br>
		
@Table
------
- @Table describes more specific details of a database table. For ex,
```
	@Entity
	@Table(name="attendees",schema="eventtracker")
	public class Attendee {}
```	
@Column
-------	
- @Column will allow to override default column names. It is used to configure various properties of a db column like
```
	columnDefinition
	insertable
	length
	name
	nullable
	precision
	scale
	table
	unique
	adaptable
```
- For ex,
```	
	@Entity
	@Table(name="attendees",schema="eventtracker")
	public class Attendee {
		
		@Column(name="ATTENDEE_NAME",nullable=false)
		private String name;
		
	}
```
Joins
-----
- Jpa is all about dealing with binding collection of objects to database.
- There are four types of annotations to bind objects and collections to one another.
  - @OneToOne
  - @OneToMany
  - @ManyToOne
  - @ManyToMany

- These 4 annotations are used with various configurations like Unidirectional, Bidirectional and Cascade.
- @OneToOne is the simplest of all, whereas @ManyToMany is the complex one. Let's see an example of @OneToMany and @ManyToOne.
- All the configuration is in the Entity objects.
  - For ex, An event has a list of attendees.  
```
	@OneToMany(mappedBy="event", cascade=CascadeType.ALL)
	private List<Attendee> attendees = new ArrayList<>();
```
  - Attendee Entity configuration
```
		@ManyToOne
		private Event event;	
```		
- mappedBy field defines a property present in the other entity. i.e. In this example, event should be property name in Attendee Entity.		
- JPA defines two types of fetching strategies name Eager and Lazy.
  - Eager fetches the associated collection/object data when the original object was created.
  - Lazy(default) fetches the associated object/collection data from DB, when the property is called.    
```
	@OneToMany(mappedBy="event", cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	private List<Attendee> attendees = new ArrayList<>();
```	
- By default fetch type lazy will fail because jpa session is valid with in the transactional context.
  - If application tries to lazily fetch once the transaction is completed an hibernate exception(org.hibernate.LazyInitializationException) is thrown.
  - To fix this, the following configuration is required with PersistenceContext.
```
	@PersistenceContext(type=PersistenceContextType.EXTENDED)
	private EntityManager em;
```
JPQL
----
JPQL is Java persistence Query Language. JPQL deals with objects. Here is the sample query.

	Query query = em.createQuery("select event from Event event");
	
