package br.com.dsd.app.server.dao;

import java.util.List;

import br.com.dsd.app.dto.LoginDTO;
import br.com.dsd.app.server.entity.User;

public interface UserDAO extends GenericDAO<User, Long> {
	
	User login(LoginDTO login);

	List<User> getUsersDifferentOfId(Integer id);
	
	User getUserByNickname(String nickname, String email);
	
	boolean save(User user);

}
