package br.com.dsd.app.server.dao.factory;

import br.com.dsd.app.server.dao.GroupDAO;
import br.com.dsd.app.server.dao.ServerInfoDAO;
import br.com.dsd.app.server.dao.UserDAO;
import br.com.dsd.app.server.dao.impl.HibernateGroupDAO;
import br.com.dsd.app.server.dao.impl.HibernateServerInfoDAO;
import br.com.dsd.app.server.dao.impl.HibernateUserDAO;

public class HibernateDAOFactory extends DAOFactory {

	public UserDAO getUserDAO() {
		return new HibernateUserDAO();
	}
	
	public GroupDAO getGroupDAO() {
		return new HibernateGroupDAO();
	}
	
	public ServerInfoDAO getServerInfoDAO() {
		return new HibernateServerInfoDAO();
	}

}
