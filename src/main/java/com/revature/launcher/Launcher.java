package com.revature.launcher;


import java.util.ArrayList;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import com.revature.entities.Bear;
import com.revature.entities.Cave;
import com.revature.entities.HoneyJar;
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
//		Cave cave = caveService.getCave();
//		System.out.println(cave.getCubicFeetSize());
//		System.out.println(cave.getOccupants());

//		Bear bear = getABear(4);
//		HoneyJar honeyJar = honeyJarService.createHoneyJar();
//		honeyJarService.giveHoneyJar(bear, honeyJar);
////		System.out.println(bear.getCave().getCubicFeetSize());
		
		createBearFamily();
		
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

	public static void createABear() {
		Bear bear = new Bear();
		bear.setBreed("brown");
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
