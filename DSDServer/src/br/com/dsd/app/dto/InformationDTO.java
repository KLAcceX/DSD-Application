package br.com.dsd.app.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class InformationDTO implements Serializable {

	private static final long serialVersionUID = 3049242521009863981L;
	
	private UserDTO loggedUser;
	private List<UserDTO> users = new ArrayList<UserDTO>();
	private List<GroupDTO> groups = new ArrayList<GroupDTO>();

	public InformationDTO() {}

	public InformationDTO(UserDTO loggedUser, List<UserDTO> users, List<GroupDTO> groups) {
		super();
		this.loggedUser = loggedUser;
		this.users = users;
		this.groups = groups;
	}

	public UserDTO getLoggedUser() {
		return loggedUser;
	}

	public void setLoggedUser(UserDTO loggedUser) {
		this.loggedUser = loggedUser;
	}

	public List<UserDTO> getUsers() {
		return users;
	}

	public void setUsers(List<UserDTO> users) {
		this.users = users;
	}

	public List<GroupDTO> getGroups() {
		return groups;
	}

	public void setGroups(List<GroupDTO> groups) {
		this.groups = groups;
	}

}
