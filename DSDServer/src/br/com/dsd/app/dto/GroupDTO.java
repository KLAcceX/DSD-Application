package br.com.dsd.app.dto;

import java.io.Serializable;

public class GroupDTO implements Serializable {

	private static final long serialVersionUID = 4941453220987652193L;

	private String name;

	public GroupDTO() {
		super();
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
