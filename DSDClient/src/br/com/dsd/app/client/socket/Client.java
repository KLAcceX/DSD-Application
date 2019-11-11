package br.com.dsd.app.client.socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import br.com.dsd.app.client.executable.ClientApplication;
import br.com.dsd.app.client.gui.dialog.Message;
import br.com.dsd.app.client.gui.frame.MainFrame;
import br.com.dsd.app.dto.InformationDTO;
import br.com.dsd.app.dto.LoginDTO;
import br.com.dsd.app.dto.MessageDTO;
import br.com.dsd.app.dto.UserDTO;

public class Client extends Thread {

	private static Client client;

	private static volatile boolean keep = false;
	private Socket socket;
	private static ObjectOutputStream outs;
	private ObjectInputStream ins;

	private Client(String ip, String port, LoginDTO login) {
		try {
			socket = new Socket(ip, Integer.parseInt(port));
			outs = new ObjectOutputStream(socket.getOutputStream());
			ins = new ObjectInputStream(socket.getInputStream());
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
				start();
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
		if (!keep)
			client = null;
		if (client == null)
			client = new Client(ip, port, login);
		return client;
	}

	public void run() {
		try {
			while (keep) {
				Object obj = ins.readObject();
				if (obj instanceof UserDTO) {
					MainFrame.getInstance().getPnlContacts().addUser((UserDTO) obj);
				} else if (obj instanceof MessageDTO) {
					MainFrame.getInstance().getPnlChat().adicionarMensagem((MessageDTO) obj);
				}
			}
		} catch (ClassNotFoundException e) {
			Message.createMessage("Houve problemas com a mensagem recebida pelo servidor.");
		} catch (IOException e) {
			Message.createMessage("Houve problemas de conex�o com o servidor.");
		}
		Message.createMessage("Conex�o com o servidor foi encerrada.");
		ClientApplication.changeFrameLogin();
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
		keep = false;
	}

}
