package br.com.dsd.app.client.entity;

import java.io.Serializable;

/**
 * Classe que representa a associação de Usuário com Módulo
 * 
 * @author kl
 *
 */
public class UserModule implements Serializable {

	private static final long serialVersionUID = 4841463101849612094L;

	private User user;
	private Module module;
	private Character status;

	public UserModule(User user, Module module, Character status) {
		super();
		this.user = user;
		this.module = module;
		this.status = status;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

	public Character getStatus() {
		return status;
	}

	public void setStatus(Character status) {
		this.status = status;
	}

}
