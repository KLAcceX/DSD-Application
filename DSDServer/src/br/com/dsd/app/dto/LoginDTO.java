package br.com.dsd.app.dto;

import java.io.Serializable;

public class LoginDTO implements Serializable {
	
	private static final long serialVersionUID = -4522810588593876598L;
	
	private String username;
	private String password;
	
	public LoginDTO(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
