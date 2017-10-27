package controllers;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import models.Alerts;

public class HibernateUtil {

	private static final SessionFactory sessionFactory;

	static {
		try {
			sessionFactory = new Configuration().configure().buildSessionFactory();
		} catch (Throwable ex) {
			Alerts.showError("Servidor fuera de servicio.");
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
}