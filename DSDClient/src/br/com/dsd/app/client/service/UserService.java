package br.com.dsd.app.client.service;

import java.util.ArrayList;
import java.util.List;

import br.com.dsd.app.client.dao.GenericDAO;
import br.com.dsd.app.client.entity.User;

public class UserService {

	private static UserService service = null;

	private UserService() {
		//TODO: SE CONECTAR COM O BANCO, INFORMAÇÕES DOS USUÁRIOS. CRIAR CAMADA DAO.
	}

	public static UserService getInstance() {
		if (service == null)
			service = new UserService();
		return service;
	}

	public List<User> getList() {
		List<User> list = (List<User>) GenericDAO.list(User.class);
		return list;
	}

}
