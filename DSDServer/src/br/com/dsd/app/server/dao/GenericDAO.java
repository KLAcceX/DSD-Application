package br.com.dsd.app.server.dao;

import java.io.Serializable;
import java.util.List;

public interface GenericDAO<T, Type extends Serializable> {
	
	void beginTransaction();
	
	void commitTransaction();
	
	boolean save(T entity);
	
	void delete(T entity);
	
	T listById(Integer id);
	
	List<T> listAll();

}
