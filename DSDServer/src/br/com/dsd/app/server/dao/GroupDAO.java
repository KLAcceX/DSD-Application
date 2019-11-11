package br.com.dsd.app.server.dao;

import br.com.dsd.app.server.entity.Group;

public interface GroupDAO extends GenericDAO<Group, Long> {

	Group getGroupByName(String name);
	
	boolean save(Group group);

}
