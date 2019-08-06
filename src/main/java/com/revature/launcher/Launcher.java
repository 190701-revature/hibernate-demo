package com.revature.launcher;


import java.io.Serializable;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import com.revature.entities.Bear;

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
		createABear();
		Bear bear = getABear();
		updateABear(bear);
//		deleteABear(bear);
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
	
	public static Bear getABear() {
		try(Session session = sessionFactory.openSession()) {
			return session.get(Bear.class, 3);
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
			.addAnnotatedClass(Bear.class); 
//			.setProperty("hibernate.connection.username", System.getenv("DB_PASSWORD")); 
			// Used to set property values programmatically
			
			
			
		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
				.applySettings(configuration.getProperties()).build();
		return configuration.buildSessionFactory(serviceRegistry);
	}
}
