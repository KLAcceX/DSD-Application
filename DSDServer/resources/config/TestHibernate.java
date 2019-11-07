package config;

import java.util.List;

import br.com.dsd.app.server.dao.GenericDAO;
import br.com.dsd.app.server.entity.dao.UserDAO;

public class TestHibernate {

	public static void main(String[] args) {
		List<UserDAO> list = (List<UserDAO>) GenericDAO.list(UserDAO.class);
		for(UserDAO user : list) {
			System.out.println(user.getEmail());
		}
	}
}
