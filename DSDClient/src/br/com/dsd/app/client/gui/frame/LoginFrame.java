package br.com.dsd.app.client.gui.frame;

import static br.com.dsd.app.client.helper.Constants.MAX_PORT;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.security.NoSuchAlgorithmException;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import br.com.dsd.app.client.gui.dialog.Message;
import br.com.dsd.app.client.gui.main.panel.FootPanel;
import br.com.dsd.app.client.gui.main.panel.HeadPanel;
import br.com.dsd.app.client.helper.MD5Util;
import br.com.dsd.app.client.helper.Util;
import br.com.dsd.app.client.socket.Client;
import br.com.dsd.app.dto.LoginDTO;

public class LoginFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private static LoginFrame loginFrame;

	private JPanel contentPane;
	private JTextField txtNickname = new JTextField();
	private JPasswordField txtPassword = new JPasswordField();
	private JTextField txtIP = new JTextField();
	private JTextField txtPort = new JTextField();

	public static LoginFrame getInstance() {
		if (loginFrame == null)
			loginFrame = new LoginFrame();
		return loginFrame;
	}

	/**
	 * Create the frame.
	 */
	public LoginFrame() {
		setTitle("DSD-WS-Client Login");
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			UIManager.put("PopupMeun.border", BorderFactory.createEmptyBorder());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getRootPane().setBorder(BorderFactory.createLineBorder(Color.BLACK));
		setUndecorated(true);
		setBounds(100, 100, 300, 480);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel pnlHead = new HeadPanel();
		pnlHead.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
		contentPane.add(pnlHead, BorderLayout.NORTH);

		JPanel pnlFoot = new FootPanel();
		pnlFoot.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.BLACK));
		contentPane.add(pnlFoot, BorderLayout.SOUTH);

		JPanel pnlContent = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.weightx = 1.;
		gbc.weighty = 200;
		gbc.fill = GridBagConstraints.HORIZONTAL;

		gbc.gridx = 0;
		gbc.gridy = 0;
		pnlContent.add(new JLabel("Nickname"), gbc);

		gbc.gridx = 1;
		txtNickname.setColumns(50);
		pnlContent.add(txtNickname, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		pnlContent.add(new JLabel("Password"), gbc);

		gbc.gridx = 1;
		txtPassword.setColumns(50);
		pnlContent.add(txtPassword, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		pnlContent.add(new JLabel("IP"), gbc);

		gbc.gridx = 1;
		txtIP.setText("192.168.56.1");
		pnlContent.add(txtIP, gbc);

		gbc.gridx = 0;
		gbc.gridy = 3;
		pnlContent.add(new JLabel("Port"), gbc);

		gbc.gridx = 1;
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
		txtPort.setText("8080");
		pnlContent.add(txtPort, gbc);

		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.gridwidth = 2;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.CENTER;
		JButton btnAddUser = new JButton("Login");
		btnAddUser.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				efetuarLogin();
			}
		});
		btnAddUser.setBorder(BorderFactory.createEmptyBorder());
		btnAddUser.setBackground(Color.WHITE);
		pnlContent.add(btnAddUser, gbc);

		InputMap inputUserMap = pnlContent.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
		ActionMap actionUserMap = pnlContent.getActionMap();
		inputUserMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "submitForm");
		actionUserMap.put("submitForm", new AbstractAction("submitForm") {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				efetuarLogin();
			}

		});

		contentPane.add(pnlContent, BorderLayout.CENTER);
	}

	private boolean isValidFields() {
		if (!Util.isNotNullFields(txtNickname, txtPassword, txtIP, txtPort)) {
			Message.createMessage("Favor preencher todos os campos!");
			return false;
		}

		if (!Util.isValidIP(txtIP.getText())) {
			Message.createMessage("Favor inserir um IP válido!");
			Util.clearFields(txtIP);
			return false;
		}

		return true;
	}

	private void efetuarLogin() {
		if (isValidFields()) {
			String password;
			try {
				password = MD5Util.convert(txtPassword.getPassword());
				LoginDTO login = new LoginDTO(txtNickname.getText(), password);
				Client.getInstance(txtIP.getText(), txtPort.getText(), login);
			} catch (NoSuchAlgorithmException e1) {
				Message.createMessage("Senha com caracteres inválidos.");
			}
		}
	}

}
