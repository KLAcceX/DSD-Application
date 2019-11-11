package br.com.dsd.app.server.dao.impl;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.com.dsd.app.server.dao.GroupDAO;
import br.com.dsd.app.server.entity.Group;
import br.com.dsd.app.server.gui.dialog.Message;
import br.com.dsd.app.server.helper.HibernateUtil;

public class HibernateGroupDAO extends HibernateDAO<Group, Long> implements GroupDAO {

	public HibernateGroupDAO() {
		super(Group.class);
	}

	@Override
	public Group getGroupByName(String name) {
		CriteriaBuilder builder = HibernateUtil.getSession().getCriteriaBuilder();
		CriteriaQuery<Group> criteria = builder.createQuery(Group.class);
		Root<Group> root = criteria.from(Group.class);
		criteria.select(root);
		criteria.where(builder.equal(root.get("name"), name));
		return (Group) HibernateUtil.getSession().createQuery(criteria).getResultList().stream().findFirst()
				.orElse(null);
	}

	public boolean save(Group group) {
		if (getGroupByName(group.getName()) == null) {
			HibernateUtil.getSession().saveOrUpdate(group);
			return true;
		}
		return false;
	}

}
