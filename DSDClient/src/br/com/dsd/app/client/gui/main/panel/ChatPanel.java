package br.com.dsd.app.client.gui.main.panel;

import java.awt.BorderLayout;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import br.com.dsd.app.client.gui.frame.MainFrame;
import br.com.dsd.app.dto.MessageDTO;
import br.com.dsd.app.dto.UserDTO;

/**
 * Painel que comporta as informações do chat
 * 
 * @author kl
 *
 */
public class ChatPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private static JTabbedPane pnlTabs;
	private Map<String, ConversationPanel> tabNames = new HashMap<String, ConversationPanel>();

	public ChatPanel() {
		setLayout(new BorderLayout(0, 0));
		pnlTabs = new JTabbedPane();
		pnlTabs.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		add(pnlTabs, BorderLayout.CENTER);
	}
	
	private void addTab(String name) {
		ConversationPanel conversation = new ConversationPanel(name);
		pnlTabs.add(conversation, name);
		tabNames.put(name, conversation);
	}

	/**
	 * Método que cuida de criar um paine de conversa
	 * 
	 * @param nickname
	 */
	public void adicionarConversa(String nickname) {
		if (pnlTabs.indexOfTab(nickname) == -1) {
			addTab(nickname);
		}
		pnlTabs.setSelectedIndex(pnlTabs.indexOfTab(nickname));
	}

	public void adicionarMensagem(MessageDTO message) {
		UserDTO user = MainFrame.getInstance().getLoggedUser();
		String nickname = (user.getNickname().equals(message.getReceiver())) ? message.getSender() : message.getReceiver();
		if (!tabNames.containsKey(nickname)) {
			addTab(nickname);
		}
		ConversationPanel pnlConversation = tabNames.get(nickname);
		pnlConversation.adicionarMensagem(message);
	}
	
	public void closeTabs() {
		pnlTabs.removeAll();
	}

}
