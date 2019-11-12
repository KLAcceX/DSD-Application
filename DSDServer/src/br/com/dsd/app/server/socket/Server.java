package br.com.dsd.app.server.socket;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import br.com.dsd.app.dto.GroupDTO;
import br.com.dsd.app.dto.MessageDTO;
import br.com.dsd.app.dto.UserDTO;
import br.com.dsd.app.server.message.Logger;

public class Server extends Thread {

	private static Server server = null;

	private static List<Connector> connectors;
	private static Map<String, ObjectOutputStream> clients;
	private static ServerSocket serverSocket = null;

	private Server(int port) {
		try {
			serverSocket = new ServerSocket(port);
			connectors = new ArrayList<Connector>();
			clients = new HashMap<String, ObjectOutputStream>();
			Logger.log("Servidor ativo na porta " + port);
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
				Socket connection = serverSocket.accept();
				Logger.log("Nova conexão com o cliente " + connection.getInetAddress().getHostAddress() + ".");
				Connector thread = new Connector(connection);
				if (thread != null) {
					connectors.add(thread);
					clients.put(thread.getUser().getNickname(), thread.getOuts());
				}
			}
		} catch (IOException e) {
			Logger.log("Servidor desligado.");
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
			unkeep();
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
			unkeep();
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
			unkeep();
		}
	}

	public synchronized static void unkeep() {
		try {
			synchronized(connectors) {
				for(Iterator<Connector> iterator = connectors.iterator(); iterator.hasNext(); ) {
					Connector connector = iterator.next();
					connector.unkeep();
				}
			}
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
			for(Iterator<Connector> iterator = connectors.iterator(); iterator.hasNext(); ) {
				Connector connector = iterator.next();
				if(connector.getUser().getNickname().equals(nickname)) {
					iterator.remove();
				}
			}
//			for (Connector connector : connectors) {
//				if (connector.getUser().getNickname().equals(nickname))
//					connectors.remove(connector);
//
//			}
		}
		synchronized (clients) {
			clients.remove(nickname);
		}
	}

}
