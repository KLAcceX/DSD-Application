package br.com.dsd.app.dto;

import java.io.Serializable;

public class GroupDTO implements Serializable {

	private static final long serialVersionUID = -3638239882085822724L;
	
	private String name;

	public GroupDTO() {

	}

	public GroupDTO(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
