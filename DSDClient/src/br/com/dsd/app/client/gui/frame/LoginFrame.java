package br.com.dsd.app.client.gui.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import br.com.dsd.app.client.executable.Application;
import br.com.dsd.app.client.gui.main.panel.FootPanel;
import br.com.dsd.app.client.gui.main.panel.HeadPanel;
import br.com.dsd.app.client.service.UserService;

// TODO: tela de login

public class LoginFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private static LoginFrame loginFrame;
	
	private JPanel contentPane;
	private JTextField txtNickname = new JTextField();
	private JPasswordField txtPassword = new JPasswordField();
	
	public static LoginFrame getInstance() {
		if(loginFrame == null)
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
		setUndecorated(true);
		setBounds(100, 100, 300, 280);
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
		gbc.gridwidth = 2;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.CENTER;
		JButton btnAddUser = new JButton("Login");
		btnAddUser.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (UserService.getInstance().login(txtNickname.getText(), new String(txtPassword.getPassword()))) {
					Application.changeFrameMain();
				}
			}
		});
		btnAddUser.setBorder(BorderFactory.createEmptyBorder());
		btnAddUser.setBackground(Color.WHITE);
		pnlContent.add(btnAddUser, gbc);

		contentPane.add(pnlContent, BorderLayout.CENTER);
	}

}
