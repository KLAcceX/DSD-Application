package br.com.dsd.app.client.socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import br.com.dsd.app.client.executable.ClientApplication;
import br.com.dsd.app.client.gui.dialog.Message;
import br.com.dsd.app.client.gui.frame.MainFrame;
import br.com.dsd.app.dto.GroupDTO;
import br.com.dsd.app.dto.InformationDTO;
import br.com.dsd.app.dto.LoginDTO;
import br.com.dsd.app.dto.MessageDTO;
import br.com.dsd.app.dto.RedirectDTO;
import br.com.dsd.app.dto.UserDTO;

public class Client extends Thread {

	private static Client client;

	private static volatile boolean keep = false;
	private static Socket socket;
	private static ObjectOutputStream outs;
	private static ObjectInputStream ins;
	private static RedirectDTO redirect;
	private static LoginDTO login;

	private Client(String ip, String port) {
		try {
			for (int i = 0; i < 3; i++) {
				try {
					socket = new Socket();
					socket.connect(new InetSocketAddress(ip, Integer.parseInt(port)), 10000);
					outs = new ObjectOutputStream(socket.getOutputStream());
					ins = new ObjectInputStream(socket.getInputStream());
					break;
				} catch (IOException e) {
					ins.close();
					outs.close();
					socket.close();
				}
			}
			if(socket == null) {
				Message.createMessage("Host n�o encontrado!");
				return;
			}
			outs.writeObject(login);
			outs.flush();
			Object obj = ins.readObject();
			if (obj instanceof InformationDTO) {
				InformationDTO information = (InformationDTO) obj;
				ClientApplication.changeFrameMain();
				MainFrame mainFrame = MainFrame.getInstance();
				mainFrame.getPnlGroups().addGroups(information.getGroups());
				mainFrame.getPnlContacts().addUsers(information.getUsers());
				mainFrame.setLoggedUser(information.getLoggedUser());
				keep = true;
				redirect = null;
				start();
			} else if (obj instanceof RedirectDTO) {
				RedirectDTO redirect = (RedirectDTO) obj;
				Client.redirect = redirect;
			} else {
				Message.createMessage("Usu�rio ou senha inv�lidos.");
			}
		} catch (NumberFormatException e) {
			Message.createMessage("Houve problema com a numera��o do Port.");
		} catch (UnknownHostException e) {
			Message.createMessage("Host irreconhec�vel.");
		} catch (IOException e) {
			Message.createMessage("Houve problemas de conex�o com o servidor.");
		} catch (ClassNotFoundException e) {
			Message.createMessage("A intera��o se deu com um servidor n�o compat�vel.");
		}
	}

	public static Client getInstance(String ip, String port, LoginDTO login) {
		Client.login = login;
		if (!keep)
			client = null;
		if (client == null)
			client = new Client(ip, port);
		if (redirect != null) {
			client = new Client(redirect.getIp(), redirect.getPort().toString());
		}
		return client;
	}

	public void run() {
		try {
			while (keep) {
				Object obj = ins.readObject();
				if (obj instanceof UserDTO) {
					MainFrame.getInstance().getPnlContacts().addUser((UserDTO) obj);
				} else if (obj instanceof GroupDTO) {
					MainFrame.getInstance().getPnlGroups().addGroup((GroupDTO) obj);
				} else if (obj instanceof MessageDTO) {
					MainFrame.getInstance().getPnlChat().adicionarMensagem((MessageDTO) obj);
				} else if (obj instanceof RedirectDTO) {
					RedirectDTO redirect = (RedirectDTO) obj;
					unkeep();
					Client.redirect = redirect;
				}
			}
		} catch (ClassNotFoundException e) {
			Message.createMessage("Houve problemas com a mensagem recebida pelo servidor.");
		} catch (IOException e) {
			if (keep) {
				Message.createMessage("Houve problemas de conex�o com o servidor.");
			}
		}		
		if(Client.redirect == null) {
			client = null;
			ClientApplication.changeFrameLogin();
		}else {
			client = new Client(redirect.getIp(), redirect.getPort().toString());
		}
	}

	public static boolean sendMessage(MessageDTO message) {
		try {
			outs.writeObject(message);
			outs.flush();
			return true;
		} catch (IOException e) {
			Message.createMessage("N�o foi poss�vel enviar a mensagem.");
		}
		return false;
	}

	public static void unkeep() {
		try {
			keep = false;
			if (socket != null) {
				ins.close();
				outs.close();
				socket.close();
			}
			socket = null;
		} catch (IOException e) {
			Message.createMessage("Tentativa de fechar uma conex�o que n�o est� mais aberta.");
		}
	}

}
