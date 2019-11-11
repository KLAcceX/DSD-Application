package br.com.dsd.app.client.gui.main.panel;

import java.awt.GridLayout;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JPanel;

import br.com.dsd.app.client.gui.component.ContactLabel;
import br.com.dsd.app.dto.UserDTO;

/**
 * Painel para carregar todos os usuários do sistema
 * 
 * @author kl
 *
 */
public class ContactsPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private Map<String, UserDTO> users = new HashMap<String, UserDTO>();
	private ChatPanel chat = null;

	public ContactsPanel(ChatPanel chat) {
		setLayout(new GridLayout(0, 1));
		this.chat = chat;
	}

	public void addUsers(List<UserDTO> users) {
		for (UserDTO user : users) {
			this.users.put(user.getNickname(), user);
		}
		orderUsers();
	}

	public void addUser(UserDTO user) {
		this.users.put(user.getNickname(), user);
		orderUsers();
	}

	private void orderUsers() {
		removeAll();
		Iterator<Entry<String, UserDTO>> it = users.entrySet().iterator();
		while(it.hasNext()) {
			Map.Entry<String, UserDTO> entry = (Map.Entry<String, UserDTO>) it.next();
			add(new ContactLabel(entry.getValue(), chat));
		}
		revalidate();
		repaint();
	}
}
