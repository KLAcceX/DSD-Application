package br.com.dsd.app.server.helper;

import java.io.File;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtil {

	private static StandardServiceRegistry registry;
	private static SessionFactory sessionFactory;
	private static Session session;

	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			try {
				File configurationFile = new File("./resources/config/hibernate.cfg.xml");
				registry = new StandardServiceRegistryBuilder().configure(configurationFile).build();
				MetadataSources sources = new MetadataSources(registry);
				Metadata metadata = sources.getMetadataBuilder().build();
				sessionFactory = metadata.getSessionFactoryBuilder().build();
			} catch (Exception e) {
				e.printStackTrace();
				if (registry != null)
					shutdown();
			}
		}
		return sessionFactory;
	}

	public static void shutdown() {
		if (registry != null) {
			StandardServiceRegistryBuilder.destroy(registry);
		}
	}

	public static Session getSession() {
		if (session == null)
			session = getSessionFactory().openSession();
		return session;
	}

	public static void beginTransaction() {
		if (!getSession().getTransaction().isActive())
			getSession().beginTransaction();
	}

	public static void commitTransaction() {
		synchronized(getSession()) {
			getSession().getTransaction().commit();
		}
	}

	public static void rollBackTransaction() {
		getSession().getTransaction().rollback();
	}

	public static void closeSession() {
		getSession().close();
	}
}
