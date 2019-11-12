package br.com.dsd.app.server.gui.panel;

import static br.com.dsd.app.server.helper.Constants.BUTTON_START;
import static br.com.dsd.app.server.helper.Constants.BUTTON_STOP;
import static br.com.dsd.app.server.helper.Constants.FIELD_IP;
import static br.com.dsd.app.server.helper.Constants.FIELD_PORT;
import static br.com.dsd.app.server.helper.Constants.MAX_PORT;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import br.com.dsd.app.server.socket.Server;

/**
 * Painel que contem as informações do servidor
 * 
 * @author kl
 *
 */
public class SocketPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private JTextField txtIp;
	private JTextField txtPort;
	private JButton btnServer;

	public SocketPanel() {
		generateLayout();
		generateFields();
		generateButton();
		generateAction();
	}

	private void generateAction() {
		InputMap inputUserMap = getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
		ActionMap actionUserMap = getActionMap();
		inputUserMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "submitForm");
		actionUserMap.put("submitForm", new AbstractAction("submitForm") {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				startServer();
			}

		});
	}

	/**
	 * Define o layout do Painel
	 */
	private void generateLayout() {
		setBorder(null);
		setBackground(Color.LIGHT_GRAY);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, 0.0, 1.0, 0.0, 0.0, 0.0 };
		gridBagLayout.columnWidths = new int[] { 50, 50, 30, 85, 30, 50, 30, 85, 30, 100, 50 };
		gridBagLayout.rowHeights = new int[] { 30, 40, 30, 45, 30 };
		setLayout(gridBagLayout);
	}

	/**
	 * Gera os campos do painel
	 */
	private void generateFields() {
		generateIpField();
		generatePortField();
	}

	/**
	 * Gera o campo de IP do painel
	 */
	private void generateIpField() {
		JLabel lblIp = new JLabel(FIELD_IP);
		GridBagConstraints gbc_lblIp = new GridBagConstraints();
		gbc_lblIp.insets = new Insets(0, 0, 5, 5);
		gbc_lblIp.gridx = 1;
		gbc_lblIp.gridy = 1;
		add(lblIp, gbc_lblIp);

		txtIp = new JTextField();
		txtIp.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtIp.setEditable(false);
		txtIp.setEnabled(false);
		txtIp.setHorizontalAlignment(SwingConstants.RIGHT);
		txtIp.setBackground(Color.LIGHT_GRAY);
		txtIp.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));

		try {
			InetAddress inet = InetAddress.getLocalHost();
			txtIp.setText(inet.getHostAddress());
		} catch (UnknownHostException e) {
			txtIp.setEnabled(true);
		}

		GridBagConstraints gbc_txtIp = new GridBagConstraints();
		gbc_txtIp.anchor = GridBagConstraints.SOUTH;
		gbc_txtIp.insets = new Insets(0, 0, 5, 5);
		gbc_txtIp.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtIp.gridx = 3;
		gbc_txtIp.gridy = 1;
		add(txtIp, gbc_txtIp);
		txtIp.setColumns(10);
	}

	/**
	 * Gera o campo de Porta do painel
	 */
	private void generatePortField() {
		JLabel lblPort = new JLabel(FIELD_PORT);
		GridBagConstraints gbc_lblPort = new GridBagConstraints();
		gbc_lblPort.insets = new Insets(0, 0, 5, 5);
		gbc_lblPort.gridx = 5;
		gbc_lblPort.gridy = 1;
		add(lblPort, gbc_lblPort);

		txtPort = new JTextField();
		txtPort.setDocument(new PlainDocument() {
			private static final long serialVersionUID = 1L;

			public void insertString(int offset, String string, AttributeSet attr) throws BadLocationException {
				string = string.replaceAll("[^0-9]", "");

				if (string == null || string.isEmpty())
					return;

				String value = txtPort.getText() + string;

				if (Integer.parseInt(value) <= MAX_PORT) {
					super.insertString(offset, string, attr);
				}
			}
		});
		txtPort.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtPort.setHorizontalAlignment(SwingConstants.RIGHT);
		txtPort.setBackground(Color.LIGHT_GRAY);
		txtPort.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));
		GridBagConstraints gbc_txtPort = new GridBagConstraints();
		gbc_txtPort.anchor = GridBagConstraints.SOUTH;
		gbc_txtPort.insets = new Insets(0, 0, 5, 5);
		gbc_txtPort.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtPort.gridx = 7;
		gbc_txtPort.gridy = 1;
		add(txtPort, gbc_txtPort);
		txtPort.setColumns(5);
	}

	/**
	 * Gera o botão do painel
	 */
	private void generateButton() {
		btnServer = new JButton(BUTTON_START);
		btnServer.setBackground(Color.WHITE);
		btnServer.setMnemonic(73);
		btnServer.addMouseListener(new ButtonListener());
		GridBagConstraints gbc_btnServer = new GridBagConstraints();
		gbc_btnServer.fill = GridBagConstraints.BOTH;
		gbc_btnServer.insets = new Insets(0, 0, 5, 5);
		gbc_btnServer.gridx = 9;
		gbc_btnServer.gridy = 3;
		add(btnServer, gbc_btnServer);
	}

	/**
	 * Classe que cuida das ações de clique do botão
	 * 
	 * @author kl
	 *
	 */
	private class ButtonListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			startServer();
		}
	}

	private void startServer() {
		switch (btnServer.getMnemonic()) {
		case 73:
			Server.getInstance(Integer.parseInt(txtPort.getText()));
			if (Server.isOn()) {
				txtPort.setEnabled(false);
				btnServer.setMnemonic(80);
				btnServer.setText(BUTTON_STOP);
			}
			break;
		case 80:
			Server.unkeep();
			txtPort.setEnabled(true);
			btnServer.setMnemonic(73);
			btnServer.setText(BUTTON_START);
			break;
		}
	}

}
