package br.com.dsd.app.client.service;

import java.util.List;

import br.com.dsd.app.client.entity.dto.GroupDTO;

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

	public List<GroupDTO> getList() {
		List<GroupDTO> list = null;
		//TODO: recuperar lista de grupos do servidor
		return list;
	}

}
