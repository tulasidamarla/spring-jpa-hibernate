JPA is a specification to work with ORM tools. It is used for accessing, persisting and managing data between java objects and relational databases. JPA is not SQL, it uses JPQL instead. It is heavily focused on POJOs.

There are a no of providers which implemented JPA specification. For ex, Hibernate, Top link, ObjectDB, OpenJPA, DataNucleus, Versant etc.

JPA needs persistent.xml file which is used define settings like DataSource, transaction manager ,caching etc.

with spring we can have different configurations per environment, easier to test and dependency injection.

Even with spring if xml configuration is preferred, even then an empty persistent.xml is required in src/main/resources/META-INF directory and can have any custom configuration file.

To develop an application using JPA, the following configuration changes are required for xml.

If web.xml is used, then

	<context-param>
		<param-name>contextConfigLocation</param-name>
		<param-value>classpath:/jpaContext.xml</param-value>
	</context-param>
	<listener>
		<listener-class>org.springframework.web.context.ContextLoaderListener
	</listerner>

Note: ContextLoaderListener class looks for the context param with name "contextConfigLocation" and loads the jpaContext.xml file. with spring jpaContext.xml file used inplace of persitence.xml. Unlike, persistence.xml name does not matter here. This file contains configuration for EntityManagerFactory, Transaction Manager, Annotation Configuration, Datasource configuration etc.

Before start with any configuration settings, jpaContext.xml file should contain the following lines.

	<context:annotation-config/>
	<bean class="org.springframework.orm.jpa.support.PersistenceAnnotationBeanPostProcessor" />

The JPA configuration contains three parts. They are 

	EntityManagerFactory
	TransactionManager
	DataSource

EntityManagerFactory class hooks jpa to the application. Spring has an implementation of EntityManagerFactory named LocalContainerEntityManagerFactoryBean which is located in spring-orm.jar file. This references the persistence unit. Also, defines vendor and vendor specific jpa properties. 

JPA/Hibernate don't provide any transactions on their own. Application needs to provide a JPATransactionManager which takes an EntityManagerFactory as reference. This class JPATransactionManager is present in spring-tx.jarfile.

Datasource configuration be added using spring's org.springframework.jdbc.datasource.DriverManagerDatasource. we can also use third party libraries like apache.   
DriverManagerDatasource class is present in spring-jdbc.jar file.

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
Spring provides @PersistenceContext annotation which injects entity manager into the application.
This is used in the repository class like this.

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

Note: An EntityManager always associated with a persistence context. A persistent context is a set of entities. Within the persistence context, the entity instances and their lifecycle are managed. The EntityManager API is used to create and remove persistence entity instances.

Set of entities that can be managed by a given EntityManager instance are defined by a persistence unit.

Mote: PersistenceContext works in a transactioncal context.

To make the PersistenceContext work in a transactional context, the following configurations are required.<br>
1)Mark the service method with @Transactional<br>
2)Mark the Java configuration class with @EnableTransactionManagement

Note: If it is xml, For step 2, use the following configuration.

	<tx:annotation-driven transaction-manager="transactionManager" />

