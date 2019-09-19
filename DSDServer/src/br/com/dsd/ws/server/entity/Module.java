package br.com.dsd.ws.server.entity;

import java.io.Serializable;

/**
 * Classe que representa os módulos
 * 
 * @author kl
 */
public class Module implements Serializable {

	private static final long serialVersionUID = 4940571873603676852L;
	
	private Integer id;
	private String name;
	private String description;

	public Module(Integer id, String name, String description) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
