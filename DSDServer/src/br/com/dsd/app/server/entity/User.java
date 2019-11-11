package br.com.dsd.app.server.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * Classe que representa os usuários
 * 
 * @author kl
 */
@Entity
@Table(name = "tb_user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String nickname;
	private String name;
	private String surname;
	private String email;
	private String password;
	private Character status;
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "tb_group_user", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = {
			@JoinColumn(name = "id") })
	private Set<Group> groups = new HashSet<>();

	public User() {

	}

	public User(Integer id, String nickname, String name, String surname, String email, String password,
			Character status) {
		super();
		this.id = id;
		this.nickname = nickname;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.password = password;
		this.status = status;
	}

	public User(String nickname, String name, String surname, String email, String password, Character status) {
		super();
		this.nickname = nickname;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.password = password;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Character getStatus() {
		return status;
	}

	public void setStatus(Character status) {
		this.status = status;
	}

	public String getEntireName() {
		return this.name + " " + this.surname;
	}

	public Set<Group> getGroups() {
		return groups;
	}

	public void setGroups(Set<Group> groups) {
		this.groups = groups;
	}

}
