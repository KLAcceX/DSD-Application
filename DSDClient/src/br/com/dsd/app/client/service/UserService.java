package br.com.dsd.app.client.service;

import static br.com.dsd.app.client.helper.MessageConstants.USUARIO;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import br.com.dsd.app.client.entity.dto.LoginDTO;
import br.com.dsd.app.client.entity.dto.UserDTO;
import br.com.dsd.app.client.gui.dialog.Message;
import br.com.dsd.app.client.gui.frame.MainFrame;
import br.com.dsd.app.client.helper.MD5Util;

public class UserService {

	private static UserService service = null;

	private UserService() {
	}

	public static UserService getInstance() {
		if (service == null)
			service = new UserService();
		return service;
	}

	public List<UserDTO> getList() {
		List<UserDTO> list = null;
		//TODO: recuperar lista de usuarios do servidor
		return list;
	}
	
	public boolean login(String nickname, String password) {
		try {
			LoginDTO login = new LoginDTO(nickname, MD5Util.convert(password));
			//TODO: enviar login e senha
			if(!login.getUsername().equals("admin")) {
				Message.createMessage(USUARIO);
				return false;
			}
			//TODO: receber UserDTO
			UserDTO user = new UserDTO("nickname", "name", "surname", "email"); 
			MainFrame.getInstance().setLoggedUser(user);
		} catch (NoSuchAlgorithmException e) {
			return false;
		}
		return true;
	}

}
