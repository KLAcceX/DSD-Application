package br.com.dsd.ws.server.entity;

import java.io.Serializable;

/**
 * Classe que representa os usuários
 * 
 * @author kl
 */
public class User implements Serializable {

	private static final long serialVersionUID = -3769038376481481901L;

	private Integer id;
	private String nickname;
	private String name;
	private String surname;
	private String email;
	private Character status;

	public User(Integer id, String nickname, String name, String surname, String email, Character status) {
		super();
		this.id = id;
		this.nickname = nickname;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.status = status;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Character getStatus() {
		return status;
	}

	public void setStatus(Character status) {
		this.status = status;
	}

}
