package br.com.dsd.app.client.entity.dto;

import java.io.Serializable;

public class GroupDTO implements Serializable {
	
	private static final long serialVersionUID = 4941453220987652193L;
	
	private Integer id;
	private String name;

	public GroupDTO() {
		super();
	}

	public GroupDTO(Integer id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	public GroupDTO(String name) {
		super();
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
