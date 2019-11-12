package br.com.dsd.app.server.dao;

import br.com.dsd.app.server.entity.ServerInfo;

public interface ServerInfoDAO extends GenericDAO<ServerInfo, Long> {
	
	boolean hasServerOnline();

}
