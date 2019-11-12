package br.com.dsd.app.dto;

import java.io.Serializable;

/**
 * Classe que representa os usuários
 * 
 * @author kl
 */
public class UserDTO implements Serializable {
	
	private static final long serialVersionUID = 4575402042368392792L;
	
	private Integer id;
	private String nickname;
	private String name;
	private String surname;
	private String email;
	private Character status;

	public UserDTO() {

	}

	public UserDTO(Integer id, String nickname, String name, String surname, String email, Character status) {
		super();
		this.id = id;
		this.nickname = nickname;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.status = status;
	}
	
	public UserDTO(String nickname, String name, String surname, String email, Character status) {
		super();
		this.nickname = nickname;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.status = status;
	}
	
	public UserDTO(String nickname, String name, String surname, String email) {
		super();
		this.nickname = nickname;
		this.name = name;
		this.surname = surname;
		this.email = email;
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

	public String getEntireName() {
		return this.name + " " + this.surname;
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
