package br.com.dsd.app.server.dao.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.com.dsd.app.dto.LoginDTO;
import br.com.dsd.app.server.dao.UserDAO;
import br.com.dsd.app.server.entity.User;
import br.com.dsd.app.server.gui.dialog.Message;
import br.com.dsd.app.server.helper.HibernateUtil;

public class HibernateUserDAO extends HibernateDAO<User, Long> implements UserDAO {

	public HibernateUserDAO() {
		super(User.class);
	}

	@Override
	public User login(LoginDTO login) {
		CriteriaBuilder builder = HibernateUtil.getSession().getCriteriaBuilder();
		CriteriaQuery<User> criteria = builder.createQuery(User.class);
		Root<User> root = criteria.from(User.class);
		criteria.select(root);
		criteria.where(builder.and(builder.equal(root.get("nickname"), login.getUsername()),
				builder.equal(root.get("password"), login.getPassword())));
		return (User) HibernateUtil.getSession().createQuery(criteria).getResultList().stream().findFirst()
				.orElse(null);
	}

	@Override
	public List<User> getUsersDifferentOfId(Integer id) {
		CriteriaBuilder builder = HibernateUtil.getSession().getCriteriaBuilder();
		CriteriaQuery<User> criteria = builder.createQuery(User.class);
		Root<User> root = criteria.from(User.class);
		criteria.select(root);
		criteria.where(builder.notEqual(root.get("id"), id));
		return HibernateUtil.getSession().createQuery(criteria).getResultList();
	}

	@Override
	public User getUserByNickname(String nickname, String email) {
		CriteriaBuilder builder = HibernateUtil.getSession().getCriteriaBuilder();
		CriteriaQuery<User> criteria = builder.createQuery(User.class);
		Root<User> root = criteria.from(User.class);
		criteria.select(root);
		criteria.where(
				builder.or(builder.equal(root.get("nickname"), nickname), builder.equal(root.get("email"), email)));
		return (User) HibernateUtil.getSession().createQuery(criteria).getResultList().stream().findFirst()
				.orElse(null);
	}

	@Override
	public boolean save(User user) {
		if (getUserByNickname(user.getNickname(), user.getEmail()) == null) {
			HibernateUtil.getSession().saveOrUpdate(user);
			return true;
		}
		return false;
	}

}
