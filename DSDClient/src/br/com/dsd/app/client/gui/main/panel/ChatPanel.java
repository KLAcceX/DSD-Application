package br.com.dsd.app.client.gui.main.panel;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;

/**
 * Painel que comporta as informações do chat
 * 
 * @author kl
 *
 */
public class ChatPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	
	private static JTabbedPane pnlTabs;

	public ChatPanel() {
		setLayout(new BorderLayout(0, 0));
		pnlTabs = new JTabbedPane();
		pnlTabs.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		add(pnlTabs, BorderLayout.CENTER);
	}

	/**
	 * Método que cuida de criar um paine de conversa
	 * 
	 * @param nickname
	 */
	public void adicionarConversa(String nickname) {

		if (pnlTabs.indexOfTab(nickname) == -1) {
			JSplitPane pnlConversation = new ConversationPanel();
			pnlTabs.add(pnlConversation, nickname);
		}
		pnlTabs.setSelectedIndex(pnlTabs.indexOfTab(nickname));
	}

}
