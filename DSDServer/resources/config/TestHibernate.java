package config;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import br.com.dsd.app.server.entity.User;
import br.com.dsd.app.server.helper.HibernateUtil;

public class TestHibernate {

	public static void main(String[] args) {
		List<User> users = new ArrayList<User>();
		for (int i = 0; i < 10; i++) {
			users.add(new User("nickname" + i, "name" + i, "surname" + i, "email" + i, i % 2 == 0 ? 'A' : 'I'));
		}
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			session.save(users.get(0));
			session.save(users.get(1));
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
	}
}
