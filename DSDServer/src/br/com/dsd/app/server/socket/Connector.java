package br.com.dsd.app.server.socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;

import br.com.dsd.app.dto.InformationDTO;
import br.com.dsd.app.dto.LoginDTO;
import br.com.dsd.app.dto.MessageDTO;
import br.com.dsd.app.dto.RedirectDTO;
import br.com.dsd.app.server.dao.GroupDAO;
import br.com.dsd.app.server.dao.ServerInfoDAO;
import br.com.dsd.app.server.dao.UserDAO;
import br.com.dsd.app.server.dao.factory.DAOFactory;
import br.com.dsd.app.server.entity.Group;
import br.com.dsd.app.server.entity.ServerInfo;
import br.com.dsd.app.server.entity.User;
import br.com.dsd.app.server.helper.BuilderUtil;
import br.com.dsd.app.server.message.Logger;

public class Connector extends Thread {

	private Socket connection;
	private boolean keep = false;
	private static ObjectOutputStream outs;
	private ObjectInputStream ins;
	private User user;
	private String ip;
	private UserDAO userDAO;
	private GroupDAO groupDAO;
	private ServerInfoDAO serverInfoDAO;

	public Connector(Socket connection) throws IOException {
		this.connection = connection;
		try {
			DAOFactory daoFactory = DAOFactory.getFactory();
			userDAO = daoFactory.getUserDAO();
			groupDAO = daoFactory.getGroupDAO();
			serverInfoDAO = daoFactory.getServerInfoDAO();

			outs = new ObjectOutputStream(connection.getOutputStream());
			ins = new ObjectInputStream(connection.getInputStream());

			Object obj = ins.readObject();
			if (obj instanceof LoginDTO) {
				ServerInfo serverInfo = checarPortAtivo();
				if (serverInfo == null) {
					LoginDTO login = (LoginDTO) obj;
					ip = connection.getInetAddress().getHostAddress();
					userDAO.beginTransaction();
					user = userDAO.login(login);
					if (user != null) {
						user.setStatus('A');
						userDAO.save(user);
						userDAO.commitTransaction();

						List<Group> groups = groupDAO.listAll();
						List<User> users = userDAO.getUsersDifferentOfId(user.getId());

						InformationDTO information = new InformationDTO();
						information.setLoggedUser(BuilderUtil.convertToDTO(user));
						information.setGroups(BuilderUtil.convertToGroupDTOList(groups));
						information.setUsers(BuilderUtil.convertToUserDTOList(users));

						outs.writeObject(information);
						Server.sendToAll(information.getLoggedUser(), user.getNickname());
						keep = true;
						start();
					} else {
						userDAO.commitTransaction();
						outs.writeObject(null);
						unkeep();
					}
				} else {
					RedirectDTO redirect = new RedirectDTO(serverInfo.getId().getIp(), serverInfo.getId().getPort());
					outs.writeObject(redirect);
					unkeep();
				}
			} else {
				unkeep();
			}
		} catch (ClassNotFoundException e) {
			Logger.err("O usuário (" + ip + ") é incompatível com o sistema.");
		}
	}

	public void run() {
		try {
			Object object = null;
			while (keep) {
				object = ins.readObject();
				ServerInfo serverInfo = checarPortAtivo();
				if (serverInfo == null) {
					if (object instanceof MessageDTO) {
						MessageDTO dto = (MessageDTO) object;
						ObjectOutputStream oos = Server.getOOSByNickname(dto.getReceiver());
						if (oos != null)
							oos.writeObject(dto);
						else
							Server.sendToAll(dto, user.getNickname());
					}
				} else {
					RedirectDTO redirect = new RedirectDTO(serverInfo.getId().getIp(), serverInfo.getId().getPort());
					outs.writeObject(redirect);
					unkeep();
				}
			}
		} catch (IOException | ClassNotFoundException e) {
			Logger.log("A conexão com o usuário (" + ip + ") foi encerrada.");
			unkeep();
		}
	}

	public void unkeep() {
		closeStream();
		if (user != null) {
			Server.getServer().removeFromConnections(user.getNickname());
			user.setStatus('I');
			userDAO.beginTransaction();
			userDAO.save(user);
			userDAO.commitTransaction();
		}
	}

	private void closeStream() {
		try {
			ins.close();
			outs.close();
			connection.close();
			keep = false;
		} catch (IOException e) {
			Logger.err("Tentativa de fechar uma conexão com o usuário que não está mais aberta.");
		}
	}

	public void closeConnection() {
		ServerInfo serverInfo = checarPortAtivo();
		if (serverInfo != null) {
			try {
				RedirectDTO redirect = new RedirectDTO(serverInfo.getId().getIp(), serverInfo.getId().getPort());
				outs.writeObject(redirect);
			} catch (IOException e) {
				Logger.err("Problema ao enviar mensagem de redirecionamento para o usuário.");
			}
		}
		closeStream();
	}

	public ObjectOutputStream getOuts() {
		return outs;
	}

	public User getUser() {
		return user;
	}

	public Socket getConnection() {
		return connection;
	}

	public ServerInfo checarPortAtivo() {
		serverInfoDAO.beginTransaction();
		List<ServerInfo> servers = serverInfoDAO.listAll();
		serverInfoDAO.commitTransaction();

		for (ServerInfo server : servers) {
			if (server.getId().getPort().intValue() != Server.getPort()) {
				Logger.log("Validando servidor de port " + server.getId().getPort() + ".");
				try {
					Socket socket = new Socket();
					socket.connect(new InetSocketAddress(server.getId().getPort()));
					socket.close();
					Logger.log("Enviando cliente para o servidor de port " + server.getId().getPort());
					return server;
				} catch (IOException e) {
					Logger.log("Servidor de port " + server.getId().getPort() + " offline.");
				}
			} else {
				return null;
			}

		}

		return null;
	}

}
