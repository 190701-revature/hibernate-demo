package com.revature.services;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.revature.entities.Cave;

public class CaveService {
	SessionFactory sessionFactory;

	public CaveService(SessionFactory sessionFactory) {
		super();
		this.sessionFactory = sessionFactory;
	}
	
	public Cave getCave() {
		try(Session session = sessionFactory.openSession()) {
			Cave cave = session.load(Cave.class, 2);
			// We can use this to load in the proxy, but it is not intuitive
			// and we risk someone might remove this line thinking it would
			// be inconsequential
//			cave.getCubicFeetSize();
			
			// Better way to load in a proxy explicitly
			Hibernate.initialize(cave);
			Hibernate.initialize(cave.getOccupants());
			return cave;			
		}
	}
}
