package br.com.dsd.app.dto;

import java.io.Serializable;

public class RedirectDTO implements Serializable {
	
	private static final long serialVersionUID = 6099766137781816720L;
	
	private String ip;
	private Integer port;

	public RedirectDTO() {
		super();
	}

	public RedirectDTO(String ip, Integer port) {
		super();
		this.ip = ip;
		this.port = port;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

}
