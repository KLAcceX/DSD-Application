package br.com.dsd.app.server.dao.impl;

import java.io.Serializable;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.com.dsd.app.server.dao.GenericDAO;
import br.com.dsd.app.server.helper.HibernateUtil;

public class HibernateDAO<T, Type extends Serializable> implements GenericDAO<T, Type> {

	private Class<T> persistentClass;

	public HibernateDAO(Class<T> persistentClass) {
		super();
		this.persistentClass = persistentClass;
	}

	@Override
	public void beginTransaction() {
		HibernateUtil.beginTransaction();
	}

	@Override
	public void commitTransaction() {
		HibernateUtil.commitTransaction();
	}

	@Override
	public boolean save(T entity) {
		try {
			HibernateUtil.getSession().saveOrUpdate(entity);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public void delete(T entity) {
		HibernateUtil.getSession().delete(entity);
	}

	@Override
	public T listById(Integer id) {

		return null;
	}

	@Override
	public List<T> listAll() {
		CriteriaBuilder builder = HibernateUtil.getSession().getCriteriaBuilder();
		CriteriaQuery<T> query = builder.createQuery(persistentClass);
		Root<T> root = query.from(persistentClass);
		query.select(root);
		return HibernateUtil.getSession().createQuery(query).getResultList();
	}

}
