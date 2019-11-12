package br.com.dsd.app.server.dao.impl;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.com.dsd.app.server.dao.ServerInfoDAO;
import br.com.dsd.app.server.entity.ServerInfo;
import br.com.dsd.app.server.helper.HibernateUtil;

public class HibernateServerInfoDAO extends HibernateDAO<ServerInfo, Long> implements ServerInfoDAO {

	public HibernateServerInfoDAO() {
		super(ServerInfo.class);
	}

	@Override
	public boolean hasServerOnline() {
		CriteriaBuilder builder = HibernateUtil.getSession().getCriteriaBuilder();
		CriteriaQuery<ServerInfo> criteria = builder.createQuery(ServerInfo.class);
		Root<ServerInfo> root = criteria.from(ServerInfo.class);
		criteria.select(root);
		criteria.where(builder.equal(root.get("online"), true));
		return HibernateUtil.getSession().createQuery(criteria).getResultList().size() > 0;
	}

}
