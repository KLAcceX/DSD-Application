package br.com.dsd.app.server.socket;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import br.com.dsd.app.dto.GroupDTO;
import br.com.dsd.app.dto.MessageDTO;
import br.com.dsd.app.dto.UserDTO;
import br.com.dsd.app.server.dao.ServerInfoDAO;
import br.com.dsd.app.server.dao.factory.DAOFactory;
import br.com.dsd.app.server.entity.ServerInfo;
import br.com.dsd.app.server.entity.ServerInfo.ServerInfoPK;
import br.com.dsd.app.server.message.Logger;

public class Server extends Thread {

	private static Server server = null;

	private static List<Connector> connectors;
	private static Map<String, ObjectOutputStream> clients;
	private static int port = 0;
	private static ServerSocket serverSocket = null;
	private static ServerInfoDAO serverInfoDAO;
	private static ServerInfo serverInfo;

	private Server(int port) {
		try {
			DAOFactory daoFactory = DAOFactory.getFactory();
			serverInfoDAO = daoFactory.getServerInfoDAO();
			serverInfoDAO.beginTransaction();
			serverInfo = new ServerInfo(new ServerInfoPK(InetAddress.getLocalHost().getHostAddress(), port));
			serverInfoDAO.save(serverInfo);
			serverInfoDAO.commitTransaction();
			serverSocket = new ServerSocket(port);
			connectors = new ArrayList<Connector>();
			clients = new HashMap<String, ObjectOutputStream>();
			Server.port = port;
			Logger.log("Servidor iniciado na porta " + port + ".");
			start();
		} catch (IOException e) {
			Logger.err("Não foi possível se conectar no servidor com a porta " + port + ".");
		}
	}

	public static Server getInstance(int port) {
		if (server == null)
			server = new Server(port);
		return server;
	}

	public void run() {
		try {
			while (true) {
				Logger.log("Aguardando cliente.");
				Socket connection = serverSocket.accept();
				Logger.log("Nova conexão com o cliente " + connection.getInetAddress().getHostAddress() + ".");
				try {
					connection.getOutputStream();
					connection.getInputStream();
					Connector thread = new Connector(connection);
					if (thread != null && thread.getConnection().isConnected() && thread.getUser() != null) {
						connectors.add(thread);
						clients.put(thread.getUser().getNickname(), thread.getOuts());
					}
				}catch(SocketException e) {
					Logger.err("Problema com as streams do cliente " + connection.getInetAddress().getHostAddress() + ".");
				}
			}
		} catch (SocketException e) {
			Logger.log("Servidor será desligado.");
		} catch (IOException e) {
			Logger.log("Servidor será desligado.");
		}
	}

	public static synchronized void sendToAll(UserDTO user, String nickname) {
		try {
			synchronized (clients) {
				Iterator<Entry<String, ObjectOutputStream>> it = clients.entrySet().iterator();

				while (it.hasNext()) {
					Map.Entry<String, ObjectOutputStream> entry = (Map.Entry<String, ObjectOutputStream>) it.next();
					if (!entry.getKey().equals(nickname)) {
						entry.getValue().writeObject(user);
						entry.getValue().flush();
					}
				}
			}
		} catch (IOException e) {
			Logger.err("Problema ao enviar Usuário.");
		}
	}

	public static synchronized void sendToAll(GroupDTO group) {
		try {
			synchronized (clients) {
				Iterator<Entry<String, ObjectOutputStream>> it = clients.entrySet().iterator();

				while (it.hasNext()) {
					Map.Entry<String, ObjectOutputStream> entry = (Map.Entry<String, ObjectOutputStream>) it.next();
					entry.getValue().writeObject(group);
					entry.getValue().flush();
				}
			}
		} catch (IOException e) {
			Logger.err("Problema ao enviar informações de Grupos.");
		}
	}

	public static synchronized void sendToAll(MessageDTO message, String nickname) throws IOException {
		try {
			synchronized (clients) {
				Iterator<Entry<String, ObjectOutputStream>> it = clients.entrySet().iterator();

				while (it.hasNext()) {
					Map.Entry<String, ObjectOutputStream> entry = (Map.Entry<String, ObjectOutputStream>) it.next();
					if (!entry.getKey().equals(nickname)) {
						entry.getValue().writeObject(message);
						entry.getValue().flush();
					}
				}
			}
		} catch (IOException e) {
			Logger.err("Problema ao enviar Mensagem.");
		}
	}

	public synchronized static void unkeep() {
		try {
			serverInfoDAO.beginTransaction();
			serverInfoDAO.delete(serverInfo);
			serverInfoDAO.commitTransaction();
			removeFromConnections(null);
			serverSocket.close();
			server = null;
		} catch (IOException e) {
			Logger.err("Tentativa de parar o servidor desligado.");
		}
	}

	public static boolean isOn() {
		return serverSocket != null;
	}

	public static Server getServer() {
		return server;
	}

	protected static ObjectOutputStream getOOSByNickname(String nickname) {
		return clients.get(nickname);
	}

	protected synchronized static void removeFromConnections(String nickname) {
		synchronized (connectors) {
			for (Iterator<Connector> iterator = connectors.iterator(); iterator.hasNext();) {
				Connector connector = iterator.next();
				if (connector.getUser().getNickname().equals(nickname)) {
					iterator.remove();
				}
				if(nickname == null) connector.closeConnection();
			}
		}
		synchronized (clients) {
			clients.remove(nickname);
		}
	}

	public static int getPort() {
		return port;
	}

}
