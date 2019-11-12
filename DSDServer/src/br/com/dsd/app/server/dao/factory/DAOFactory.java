package br.com.dsd.app.server.dao.factory;

import java.lang.reflect.InvocationTargetException;

import br.com.dsd.app.server.dao.GroupDAO;
import br.com.dsd.app.server.dao.ServerInfoDAO;
import br.com.dsd.app.server.dao.UserDAO;

public abstract class DAOFactory {

	private static final Class<HibernateDAOFactory> FACTORY_CLASS = HibernateDAOFactory.class;

	public static DAOFactory getFactory() {
		DAOFactory daoFactory = null;
		try {
			daoFactory = (DAOFactory) FACTORY_CLASS.getDeclaredConstructor().newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return daoFactory;
	}
	
	public abstract UserDAO getUserDAO();
	
	public abstract GroupDAO getGroupDAO();
	
	public abstract ServerInfoDAO getServerInfoDAO();
}
