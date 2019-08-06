package com.revature.launcher;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class Launcher {
	// generation code: alt+shift+s
	// format code: ctrl+shift+f
	// import: ctrl+shift+o
	public static void main(String[] args) {
		SessionFactory sessionFactory = configure();
		System.out.println("Have session factory");
	}
	
	public static SessionFactory configure() {
		// Configuration is one of the primary interfaces of Hibernate
		Configuration configuration = new Configuration()
			.configure(); // Loads the configuration from hibernate.cfg.xml
//			.setProperty("hibernate.connection.username", System.getenv("DB_PASSWORD")); 
			// Used to set property values programmatically
			
			
			
		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
				.applySettings(configuration.getProperties()).build();
		return configuration.buildSessionFactory(serviceRegistry);
	}
}
