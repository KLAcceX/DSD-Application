package br.com.dsd.app.client.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;

import br.com.dsd.app.client.helper.HibernateUtil;

public class GenericDAO {

	private Session session = null;
	private Transaction transaction = null;

	private void startTransaction() {
		if (!session.isOpen())
			session = HibernateUtil.getSessionFactory().openSession();
		transaction = session.beginTransaction();
	}

	public void inserir(Object object) {
		try {
			startTransaction();
			session.save(object);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
	}

	public void update(Object object) {
		try {
			startTransaction();
			session.merge(object);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
	}

	public void delete(Object object) {
		try {
			startTransaction();
			session.delete(object);
			session.flush();
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
	}

	public List list(Class clazz) {
		List list = null;
		try {
			startTransaction();
			Criteria criteria = session.createCriteria(clazz);
			transaction.commit();
			list = criteria.list();
			session.flush();
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}

		return list;
	}

	public Object listById(Class clazz, Integer pk) {
		Object object = null;
		try {
			startTransaction();
			object = session.load(clazz, pk);
			transaction.commit();
			session.flush();
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}

		return object;
	}

}
