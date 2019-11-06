package br.com.dsd.app.client.service;

import java.util.ArrayList;
import java.util.List;

import br.com.dsd.app.client.dao.GenericDAO;
import br.com.dsd.app.client.entity.Group;
import br.com.dsd.app.client.entity.User;

public class GroupService {

	private static GroupService service = null;

	private GroupService() {
		//TODO: SE CONECTAR COM O BANCO, INFORMAÇÕES DO GRUPO. CRIAR CAMADA DAO.
	}

	public static GroupService getInstance() {
		if (service == null)
			service = new GroupService();
		return service;
	}

	public List<Group> getList() {
		List<Group> list = (List<Group>) GenericDAO.list(Group.class);
		return list;
		
	}

}
