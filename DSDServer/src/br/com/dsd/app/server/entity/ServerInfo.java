package br.com.dsd.app.server.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tb_server_info")
public class ServerInfo {

	@EmbeddedId
	private ServerInfoPK id;

	@Embeddable
	public static class ServerInfoPK implements Serializable {
		private static final long serialVersionUID = 1L;
		
		private String ip;
		private Integer port;

		public ServerInfoPK() {

		}

		public ServerInfoPK(String ip, Integer port) {
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
		
		public boolean equals(Object other) {
			return null != other
					&& other instanceof ServerInfoPK
					&& null != ip
					&& null != ((ServerInfoPK) other).ip
					&& null != port
					&& null != ((ServerInfoPK) other).port
					&& ((ServerInfoPK) other).ip
							.equals(ip)
					&& ((ServerInfoPK) other).port
							.equals(port);
		}

		public int hashCode() {
			long hash = (long) ip.hashCode();
			hash += (long) port;

			return (int) hash;
		}

	}

	public ServerInfo() {

	}

	public ServerInfo(ServerInfoPK id) {
		super();
		this.id = id;
	}

	public ServerInfoPK getId() {
		return id;
	}

	public void setId(ServerInfoPK id) {
		this.id = id;
	}

}
