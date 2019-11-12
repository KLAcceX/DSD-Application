package br.com.dsd.app.client.gui.main.panel;

import static br.com.dsd.app.client.helper.Constants.ACTION_EMOTE;
import static br.com.dsd.app.client.helper.Constants.ACTION_FILE;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

import br.com.dsd.app.client.gui.frame.MainFrame;
import br.com.dsd.app.client.helper.DateUtil;
import br.com.dsd.app.client.socket.Client;
import br.com.dsd.app.dto.MessageDTO;

/**
 * Um dos paineis de conversação
 * 
 * @author kl
 *
 */
public class ConversationPanel extends JSplitPane {

	private static final long serialVersionUID = 1L;

	private String tabName = "";
	private JTextArea txtMessage;
	private JTextArea txtChat;

	public ConversationPanel(String tabName) {
		setOrientation(JSplitPane.VERTICAL_SPLIT);

		this.tabName = tabName;
		JPanel pnlMessage = new JPanel();
		pnlMessage.setLayout(new BorderLayout(0, 0));
		JPanel pnlAction = new JPanel();
		pnlAction.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		pnlMessage.add(pnlAction, BorderLayout.NORTH);
		pnlMessage.setMinimumSize(new Dimension(getWidth(), 100));

//		JButton btnEmote = new JButton(ACTION_EMOTE);
//		btnEmote.setHorizontalAlignment(SwingConstants.LEFT);
//		pnlAction.add(btnEmote);
//
//		JButton btnFile = new JButton(ACTION_FILE);
//		btnFile.setHorizontalAlignment(SwingConstants.LEFT);
//		pnlAction.add(btnFile);

		txtMessage = new JTextArea();
		txtMessage.setLineWrap(true);
		txtMessage.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (!txtMessage.getText().isEmpty() && e.getKeyCode() == KeyEvent.VK_ENTER) {
					enviarMensagem(txtMessage.getText());
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					txtMessage.setText("");
				}
			}
		});
		JScrollPane txtSclMessage = new JScrollPane(txtMessage);
		txtSclMessage.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		pnlMessage.add(txtSclMessage);

		JPanel pnlText = new JPanel();
		pnlText.setLayout(new BorderLayout(0, 0));

		txtChat = new JTextArea();
		txtChat.setLineWrap(true);
		txtChat.setEditable(false);
		JScrollPane txtSclChat = new JScrollPane(txtChat);
		txtSclChat.setMinimumSize(new Dimension(getWidth(), 200));
		pnlText.add(txtSclChat, BorderLayout.CENTER);

		setLeftComponent(pnlText);
		setRightComponent(pnlMessage);
		setDividerLocation(0.2);
	}

	/**
	 * Método que cuida do envio de mensagem
	 * 
	 * @param mensagem
	 */
	public void enviarMensagem(String mensagem) {
		MessageDTO message = new MessageDTO(tabName, MainFrame.getInstance().getLoggedUser().getNickname(), new Date(),
				mensagem);
		if (Client.sendMessage(message)) {
			adicionarMensagem(message);
		}
	}

	/**
	 * Método que cuida da apresentação das mensagens
	 * 
	 * @param mensagem
	 */
	public void adicionarMensagem(MessageDTO mensagem) {
		txtChat.append("[" + mensagem.getSender() + " " + DateUtil.getChatDate(mensagem.getSendDate()) + "] : "
				+ mensagem.getText() + "\n");
	}

}
