package Recu;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;


public class HibernateUtil {

	private static final SessionFactory sessionFactory = buildSessionfactory();
	
	private static SessionFactory buildSessionfactory() {
		try {
			return new Configuration().configure().buildSessionFactory(
					new StandardServiceRegistryBuilder().configure().build());
		} catch (Throwable e) {
			System.err.println("Fallo al cerrar sesion "+e);
			throw new ExceptionInInitializerError(e);
		}
	}
	
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
}
