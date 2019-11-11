package br.com.dsd.app.client.gui.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import br.com.dsd.app.client.gui.main.panel.ChatPanel;
import br.com.dsd.app.client.gui.main.panel.ContactsPanel;
import br.com.dsd.app.client.gui.main.panel.FootPanel;
import br.com.dsd.app.client.gui.main.panel.GroupsPanel;
import br.com.dsd.app.client.gui.main.panel.HeadPanel;
import br.com.dsd.app.client.gui.main.panel.MenuPanel;
import br.com.dsd.app.dto.UserDTO;

/**
 * Janela da aplicação
 * 
 * @author kl
 *
 */

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private static MainFrame mainFrame;
	private static UserDTO loggedUser;
	private boolean maximized = false;

	private JPanel contentPane;
	private JSplitPane splitPane;
	private JScrollPane pnlSclContacts;
	private JScrollPane pnlSclGroups;
	
	private HeadPanel pnlHead;
	private FootPanel pnlFoot;
	private GroupsPanel pnlGroups;
	private ContactsPanel pnlContacts;
	private ChatPanel pnlChat;

	public static MainFrame getInstance() {
		if (mainFrame == null)
			mainFrame = new MainFrame();
		return mainFrame;
	}

	/**
	 * Cria a janela
	 */
	private MainFrame() {
		setTitle("DSD-WS-Client");
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			UIManager.put("PopupMeun.border", BorderFactory.createEmptyBorder());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		getRootPane().setBorder(BorderFactory.createLineBorder(Color.BLACK));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setUndecorated(true);
		setBounds(100, 100, 800, 480);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		pnlHead = new HeadPanel();
		pnlHead.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
		contentPane.add(pnlHead, BorderLayout.NORTH);

		pnlFoot = new FootPanel();
		pnlFoot.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.BLACK));
		contentPane.add(pnlFoot, BorderLayout.SOUTH);

		JPanel pnlContent = new JPanel();
		contentPane.add(pnlContent, BorderLayout.CENTER);
		pnlContent.setLayout(new BorderLayout(0, 0));

		pnlChat = new ChatPanel();
		pnlChat.setMinimumSize(new Dimension(300, getHeight()));

		JPanel pnlMenu = new MenuPanel();
		((MenuPanel) pnlMenu).getMenuContacts().addMouseListener(new ContactMouseClickHandler());
		((MenuPanel) pnlMenu).getMenuGroups().addMouseListener(new GroupMouseClickHandler());
		pnlContent.add(pnlMenu, BorderLayout.WEST);

		splitPane = new JSplitPane();
		pnlContent.add(splitPane, BorderLayout.CENTER);

		pnlGroups = new GroupsPanel((ChatPanel) pnlChat);
		pnlSclGroups = new JScrollPane(pnlGroups);
		pnlSclGroups.setMinimumSize(new Dimension(300, getHeight()));

		pnlContacts = new ContactsPanel((ChatPanel) pnlChat);
		pnlSclContacts = new JScrollPane(pnlContacts);
		pnlSclContacts.setMinimumSize(new Dimension(300, getHeight()));

		splitPane.setLeftComponent(pnlSclContacts);
		splitPane.setRightComponent(pnlChat);

		splitPane.setDividerLocation(0.6);
	}

	public void alterarPainel(boolean isGroupChange) {
		if (isGroupChange)
			splitPane.setLeftComponent(pnlSclGroups);
		else
			splitPane.setLeftComponent(pnlSclContacts);
		isGroupChange = !isGroupChange;
	}

	private class GroupMouseClickHandler extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			alterarPainel(true);
		}
	}

	private class ContactMouseClickHandler extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			alterarPainel(false);
		}
	}

	public UserDTO getLoggedUser() {
		return loggedUser;
	}

	public void setLoggedUser(UserDTO user) {
		loggedUser = user;
		pnlHead.setText(loggedUser.getNickname());
	}

	public void maximize() {
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		maximized = true;
	}

	public void minimize() {
		this.setExtendedState(JFrame.NORMAL);
		maximized = false;
	}

	public boolean isMaximized() {
		return maximized;
	}

	public GroupsPanel getPnlGroups() {
		return pnlGroups;
	}

	public ContactsPanel getPnlContacts() {
		return pnlContacts;
	}

	public ChatPanel getPnlChat() {
		return pnlChat;
	}

}
