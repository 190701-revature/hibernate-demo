package com.revature.services;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.revature.entities.Bear;
import com.revature.entities.HoneyJar;

public class HoneyJarService {
	SessionFactory sessionFactory;

	public HoneyJarService(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	
	public HoneyJar getHoneyJar(int id) {
		return null;
	}
	
	public HoneyJar createHoneyJar() {
		HoneyJar honeyJar = new HoneyJar();
		honeyJar.setVolumeLiters(2);
		honeyJar.setHoneyLiters(1);
		try(Session session = sessionFactory.openSession()) {
			Transaction tx = session.beginTransaction();
			session.save(honeyJar);
			tx.commit();
			return honeyJar;
		}
	}
	
	public void giveHoneyJar(Bear bear, HoneyJar honeyJar) {
		try(Session session = sessionFactory.openSession()) {
			Transaction tx = session.beginTransaction();
			bear.setHoneyjar(honeyJar);
			session.merge(bear);
			tx.commit();
		}
	
	}
	
}
