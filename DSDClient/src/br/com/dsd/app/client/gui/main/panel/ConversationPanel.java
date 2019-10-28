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
import javax.swing.JTextPane;
import javax.swing.SwingConstants;

import br.com.dsd.app.client.entity.Message;
import br.com.dsd.app.client.gui.frame.MainFrame;
import br.com.dsd.app.client.helper.DateUtil;

/**
 * Um dos paineis de conversação
 * 
 * @author kl
 *
 */
public class ConversationPanel extends JSplitPane {
	
	private static final long serialVersionUID = 1L;
	
	private JTextPane txtMessage;
	private JTextPane txtChat;

	public ConversationPanel() {
		setOrientation(JSplitPane.VERTICAL_SPLIT);

		JPanel pnlMessage = new JPanel();
		pnlMessage.setLayout(new BorderLayout(0, 0));
		JPanel pnlAction = new JPanel();
		pnlAction.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		pnlMessage.add(pnlAction, BorderLayout.NORTH);
		pnlMessage.setMinimumSize(new Dimension(getWidth(), 100));

		JButton btnEmote = new JButton(ACTION_EMOTE);
		btnEmote.setHorizontalAlignment(SwingConstants.LEFT);
		pnlAction.add(btnEmote);

		JButton btnFile = new JButton(ACTION_FILE);
		btnFile.setHorizontalAlignment(SwingConstants.LEFT);
		pnlAction.add(btnFile);

		txtMessage = new JTextPane();
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
		pnlMessage.add(txtSclMessage);

		JPanel pnlText = new JPanel();
		pnlText.setLayout(new BorderLayout(0, 0));

		txtChat = new JTextPane();
		txtChat.setEditable(false);
		JScrollPane txtSclChat = new JScrollPane(txtChat);
		txtSclChat.setMinimumSize(new Dimension(getWidth(), 200));
		pnlText.add(txtSclChat, BorderLayout.CENTER);

		setLeftComponent(pnlText);
		setRightComponent(pnlMessage);
		setDividerLocation(0.2);
		enviarMensagem("Teste");
	}

	/**
	 * Método que cuida do envio de mensagem
	 * 
	 * @param mensagem
	 */
	public void enviarMensagem(String mensagem) {
		Message message = new Message(MainFrame.getLoggedUser(), new Date(), mensagem);
		// TODO: ENVIAR MENSAGEM PARA DESTINATÁRIO, MÉTODO RETORNANDO BOOLEAN
		// CASO TRUE : MENSAGEM ENVIADA
		// CASO FALSE : MENSAGEM COM ERRO

		if (true) { // colocar aqui o método
			adicionarMensagem(message);
		}
	}

	/**
	 * Método que cuida da apresentação das mensagens
	 * 
	 * @param mensagem
	 */
	public void adicionarMensagem(Message mensagem) {
		txtChat.setText("[" + mensagem.getSender().getNickname() + " " + DateUtil.getChatDate(mensagem.getSendDate())
				+ "] : " + mensagem.getText() + "\n");
	}

}
