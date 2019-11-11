package br.com.dsd.app.server.socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import br.com.dsd.app.dto.InformationDTO;
import br.com.dsd.app.dto.LoginDTO;
import br.com.dsd.app.dto.MessageDTO;
import br.com.dsd.app.dto.UserDTO;
import br.com.dsd.app.server.dao.GroupDAO;
import br.com.dsd.app.server.dao.UserDAO;
import br.com.dsd.app.server.dao.factory.DAOFactory;
import br.com.dsd.app.server.entity.Group;
import br.com.dsd.app.server.entity.User;
import br.com.dsd.app.server.helper.BuilderUtil;
import br.com.dsd.app.server.message.Logger;

public class Connector extends Thread {

	private Socket connection;
	private static ObjectOutputStream outs;
	private ObjectInputStream ins;
	private User user;
	private String ip;
	private UserDAO userDAO;
	private GroupDAO groupDAO;

	public Connector(Socket connection) throws IOException {
		this.connection = connection;
		try {
			DAOFactory daoFactory = DAOFactory.getFactory();
			userDAO = daoFactory.getUserDAO();
			groupDAO = daoFactory.getGroupDAO();
			outs = new ObjectOutputStream(connection.getOutputStream());
			ins = new ObjectInputStream(connection.getInputStream());
			Object obj = ins.readObject();
			if (obj instanceof LoginDTO) {
				LoginDTO login = (LoginDTO) obj;
				ip = connection.getInetAddress().getHostAddress();

				userDAO.beginTransaction();
				user = userDAO.login(login);
				if (user != null) {
					user.setStatus('A');
					userDAO.save(user);
					List<Group> groups = groupDAO.listAll();
					List<User> users = userDAO.getUsersDifferentOfId(user.getId());

					InformationDTO information = new InformationDTO();
					information.setLoggedUser(BuilderUtil.convertToDTO(user));
					information.setGroups(BuilderUtil.convertToGroupDTOList(groups));
					information.setUsers(BuilderUtil.convertToUserDTOList(users));

					outs.writeObject(information);
					sendToAll(information.getLoggedUser());
				} else {
					outs.writeObject(null);
					unkeep();
					return;
				}
				userDAO.commitTransaction();
				start();
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
			while (true) {
				object = ins.readObject();
				if (object instanceof MessageDTO) {
					MessageDTO dto = (MessageDTO) object;
					ObjectOutputStream oos = Server.getServer().getOOSByNickname(dto.getReceiver());
					if (oos != null)
						oos.writeObject(dto);
					else
						sendToAll(dto);
				}
			}
		} catch (IOException | ClassNotFoundException e) {
			Logger.log("A conexão com o usuário (" + ip + ") foi encerrada.");
			unkeep();
		}
	}

	public void unkeep() {
		try {
			ins.close();
			outs.close();
			connection.close();
			synchronized (Server.getAllOOS()) {
				Server.getAllOOS().remove(user.getNickname());
			}
		} catch (IOException e) {
			Logger.err("Tentativa de fechar uma conexão com o usuário que não está mais aberta.");
		}
		user.setStatus('I');
		userDAO.beginTransaction();
		userDAO.save(user);
		userDAO.commitTransaction();
	}

	private void sendToAll(MessageDTO message) throws IOException {
		try {
			synchronized (Server.getAllOOS()) {
				Map<String, ObjectOutputStream> clients = Server.getAllOOS();
				Iterator<Entry<String, ObjectOutputStream>> it = clients.entrySet().iterator();

				while (it.hasNext()) {
					Map.Entry<String, ObjectOutputStream> entry = (Map.Entry<String, ObjectOutputStream>) it.next();
					if (!entry.getKey().equals(user.getNickname())) {
						entry.getValue().writeObject(message);
						entry.getValue().flush();
					}
				}
			}
		} catch (IOException e) {
			unkeep();
		}
	}

	private void sendToAll(UserDTO user) {
		try {
			synchronized (Server.getAllOOS()) {
				Map<String, ObjectOutputStream> clients = Server.getAllOOS();
				Iterator<Entry<String, ObjectOutputStream>> it = clients.entrySet().iterator();

				while (it.hasNext()) {
					Map.Entry<String, ObjectOutputStream> entry = (Map.Entry<String, ObjectOutputStream>) it.next();
					if (!entry.getKey().equals(user.getNickname())) {

						entry.getValue().writeObject(user);
						entry.getValue().flush();
					}
				}
			}
		} catch (IOException e) {
			unkeep();
		}
	}

	public ObjectOutputStream getOuts() {
		return outs;
	}

	public User getUser() {
		return user;
	}

}
