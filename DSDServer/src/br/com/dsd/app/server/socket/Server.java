package br.com.dsd.app.server.socket;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;

import br.com.dsd.app.server.message.Logger;

public class Server extends Thread {

	private static Server server = null;

	private static Map<String, ObjectOutputStream> clients;
	private static ServerSocket serverSocket = null;

	private Server(int port) {
		try {
			serverSocket = new ServerSocket(port);
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
					clients.put(thread.getUser().getNickname(), thread.getOuts());
				}
			}
		} catch (IOException e) {
			Logger.log("Servidor desligado.");
		}
	}

	public void unkeep() {
		try {
			serverSocket.close();
		} catch (IOException e) {
			Logger.err("Tentativa de parar o servidor desligado.");
		}
	}
	
	public static boolean isOn() {
		return serverSocket != null;
	}

	protected static Server getServer() {
		return server;
	}
	
	protected static ObjectOutputStream getOOSByNickname(String nickname) {
		return clients.get(nickname);
	}
	
	protected static Map<String, ObjectOutputStream> getAllOOS(){
		return clients;
	}

}
