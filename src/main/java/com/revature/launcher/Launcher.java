package com.revature.launcher;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.StringType;

import com.revature.entities.Bear;
import com.revature.entities.Cave;
import com.revature.entities.HoneyJar;
import com.revature.services.CacheService;
import com.revature.services.CaveService;
import com.revature.services.HoneyJarService;

/*
 * About CRUD operations on the Session Object
 * 
 * A. Create Operations
 * 		1. save(entity) - saves entity, returning Serializable id
 * 		2. persist(entity) - JPA method, saves entity, returns void
 * 
 * B. Read Operations
 * 		3. get(entity.class, id) - Returns object with id or null
 * 		4. load(entity.class, id) - Returns potential proxied object, assumes existence
 * 									may cause exceptions if an object does not actually exist
 * 									with the provided identifier. Use only when confident it should
 * 									exist.
 * 
 * C. Update Operations
 * 		5. Update(entity) - Updates a detached instance using the provided transient
 * 							object. Throws an exception if a persistent instance
 * 							exists.
 * 		6. Merge(entity) - Updates or inserts the detached instance and copies state
 * 							of the provided object to any persistent instance that
 * 							exists. JPA method. After merging, use returned persistent
 * 							object and abandon the argument to merge.
 * 
 * D. Delete Operations
 * 		7. Delete(entity or Id) - delete detached/persistent object
 */

public class Launcher {
	// generation code: alt+shift+s
	// format code: ctrl+shift+f
	// import: ctrl+shift+o
	static SessionFactory sessionFactory;
	
	public static void main(String[] args) {
		sessionFactory = configure();
		CaveService caveService = new CaveService(sessionFactory);
		HoneyJarService honeyJarService = new HoneyJarService(sessionFactory);
		CacheService cacheService = new CacheService(sessionFactory);
//		Cave cave = caveService.getCave();
//		System.out.println(cave.getCubicFeetSize());
//		System.out.println(cave.getOccupants());

//		Bear bear = getABear(4);
//		HoneyJar honeyJar = honeyJarService.createHoneyJar();
//		honeyJarService.giveHoneyJar(bear, honeyJar);
////		System.out.println(bear.getCave().getCubicFeetSize());
		
//		createBearFamily();
		
//		createABear("grizzly");
//		createABear("polar");
//		createABear("koala");
//		createABear("panda");
//		createABear("brown");
//		createABear("black");
//		createABear("panda");
//		createABear("koala");
		
		
//		List<Bear> pandas = sampleHQLQuery("grizzly");
//		List<Bear> salmonLovingBears = sampleCriteriaQuery("Salmon");
//		System.out.println(salmonLovingBears);
		
//		namedQuerySample();
//		nativeQuerySample();
		
		cacheService.testingTheL1Cache();
		
	}
	
	
	/**
	 * Native queries allow us to query the database using standard
	 * SQL syntax instead hibernate syntax.  However, this should only be
	 * done when Hibernate does not support the way you want to query.
	 * 
	 * This is because, when you use a native query it will invalidate
	 * your cache.  Hibernate makes a lot of effort to cache objects in
	 * memory to avoid unnecessary queries to the database. 
	 */
	private static void nativeQuerySample() {
		try(Session session = sessionFactory.openSession()) {
			String sql = "SELECT * FROM bears WHERE favorite_food = :favoriteFood";
			List<Bear> bears = session.createNativeQuery(sql)
			.setParameter("favoriteFood", "Salmon")
			.addEntity(Bear.class)
			.getResultList();
			System.out.println(bears);
		}
	}

	private static void namedQuerySample() {
		try(Session session = sessionFactory.openSession()) {
			List<Bear> bears = session.getNamedQuery("favoriteFoodQuery")
				.setParameter("favoriteFood", "trout")
				.list();
			System.out.println(bears);
		}
	}

	/*
	 * HQL is Hibernate Query Language
	 * HQL is a Domain Specific Language (DSL) for Hibernate to query
	 * a database based on the Entity definition rather than Table definition
	 * 
	 * As a result, we reference classes and fields, rather than tables and 
	 * columns.
	 */
	private static List<Bear> sampleHQLQuery(String breed) {
		try(Session session = sessionFactory.openSession()) {
//			String hql = "select b from Bear b WHERE b.breed LIKE :breed";
			String hql = "from Bear b WHERE b.breed like : breed";
			List<Bear> bears = session.createQuery(hql, Bear.class)
					.setParameter("breed", breed, StringType.INSTANCE)
					.list();
			return bears;
		}
	}

	/*
	 * Criteria - It's Gross
	 *
	 * A purely object oriented way to query your database. The lack of a
	 * string being used to define the query means that your development tools
	 * can give you feedback when the syntax is incorrect.
	 * 
	 * Because Criteria uses builders, we can pass the builder around to make
	 * queries in a more piecemeal fashion.
	 * 
	 * Session.getCriteriaBuilder() to start a criteria query
	 * CriteriaBuilder & CriteriaQuery & Query interfaces
	 * 
	 * 
	 */
	private static List<Bear> sampleCriteriaQuery(String favoriteFood) {
		try(Session session = sessionFactory.openSession()) {
			CriteriaBuilder cb = session.getCriteriaBuilder();
			CriteriaQuery<Bear> bearQuery = cb.createQuery(Bear.class);
			Root<Bear> root = bearQuery.from(Bear.class);
			
			bearQuery.select(root)
				.where(cb.equal(root.get("favoriteFood"), favoriteFood));

			Query query = session.createQuery(bearQuery);
			List<Bear> results = (List<Bear>) query.getResultList();
			return results;
			
		}
	}
	
	private static void createBearFamily() {
		try(Session session = sessionFactory.openSession()) {
			Transaction tx = session.beginTransaction();
			
			Bear bearA = session.get(Bear.class, 4);
			Bear bearB = session.get(Bear.class, 5);
			Bear bearC = session.get(Bear.class, 6);
			Bear bearD = session.get(Bear.class, 7);
			
			bearA.getCubs().add(bearC);
			bearA.getCubs().add(bearD);
			bearB.getCubs().add(bearC);
			bearB.getCubs().add(bearD);
			
			tx.commit();
		}
	}
	
	private static void putBearsInCave() {
		try(Session session = sessionFactory.openSession()) {
			Transaction tx = session.beginTransaction();
			Cave cave  = new Cave();
			cave.setCubicFeetSize(100);
			cave.setHasWater(false);
			cave.setOccupants(new ArrayList());
			session.save(cave);
			
			Bear bearA = session.get(Bear.class, 4);
			cave.getOccupants().add(bearA);
			
			Bear bearB = session.get(Bear.class, 5);
			cave.getOccupants().add(bearB);
			
			/**
			 * Automatic dirty checking
			 * Hibernate feature that allows it to automatically persist
			 * changes to persistent context objects when a transaction/session
			 * ends. This is possible because objects in the persistent context
			 * are being actively tracked by Hibernate.
			 */
			
			tx.commit();
		}
	}

	public static void deleteABear(Bear bear) {
		try(Session session = sessionFactory.openSession()) {
			Transaction tx = session.beginTransaction();
			session.delete(bear);
			tx.commit();
		}
	}

	public static void createABear(String bearColor) {
		Bear bear = new Bear();
		bear.setBreed(bearColor);
		bear.setFavoriteFood("trout");
		bear.setKilograms(100);
		
		// Using a try block to autoclose the session object
		try(Session session = sessionFactory.openSession()) {
			Transaction tx = session.beginTransaction();
			session.save(bear);
			tx.commit();
		}
		System.out.println(bear);
	}
	
	public static Bear getABear(int id) {
		try(Session session = sessionFactory.openSession()) {
			return session.get(Bear.class, id);
		}
	}
	
	public static void updateABear(Bear bear) {
		bear.setFavoriteFood("Salmon");
		
		// Try-With-Resources - Autocloses the Autocloseable variable declared
		// within the try () parenthesis
		try(Session session = sessionFactory.openSession()) {
			Transaction tx = session.beginTransaction();
			Bear bearCopy = session.get(Bear.class, bear.getId());
			System.out.println(bearCopy);
			Bear bearC = (Bear) session.merge(bear);
			System.out.println(bearCopy);
			tx.commit();
			System.out.println(bearC == bearCopy);
		}
	}
	
	public static SessionFactory configure() {
		// Configuration is one of the primary interfaces of Hibernate
		
		// Builder pattern
		Configuration configuration = new Configuration()
			.configure() // Loads the configuration from hibernate.cfg.xml
			.addAnnotatedClass(Bear.class)
			.addAnnotatedClass(Cave.class)
			.addAnnotatedClass(HoneyJar.class); 
//			.setProperty("hibernate.connection.username", System.getenv("DB_PASSWORD")); 
			// Used to set property values programmatically
			
			
			
		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
				.applySettings(configuration.getProperties()).build();
		return configuration.buildSessionFactory(serviceRegistry);
	}
}
