package com.revature.services;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.revature.entities.Bear;

public class CacheService {
	SessionFactory sessionFactory;

	public CacheService(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	
	/**
	 * L1 Cache - (Level 1 Cache)
	 * The basic cache built into Hibernate.  The L1 cache is associated
	 * with the Session object and caches data within the scope of a Session.
	 * This data is cached for the lifecycle of the Session object for which
	 * it originated.
	 * 
	 * This is essential hibernate behavior. It cannot be disabled, and there
	 * is no way to use Hibernate without it.
	 */
	public void testingTheL1Cache() {
		Bear beara = null;
		
		try(Session session = sessionFactory.openSession()) {
			beara = session.get(Bear.class, 5);
			Bear bearb = session.get(Bear.class, 5);
			Bear bearc = session.load(Bear.class, 5);
			Bear beard = session.get(Bear.class, 5);
			
			System.out.println(beara != null);
			System.out.println(bearb != null);
			System.out.println(bearc != null);
			System.out.println(beard != null);
			
			System.out.println(beara.equals(bearb));
			System.out.println(beara == bearc);
		}
		
		try(Session session = sessionFactory.openSession()) {
			Bear newBear = session.get(Bear.class, 5);
			System.out.println(beara == newBear);
		}
	}
}
