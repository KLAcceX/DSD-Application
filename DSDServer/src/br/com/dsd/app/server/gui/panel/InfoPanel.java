package br.com.dsd.app.server.gui.panel;

import static br.com.dsd.app.server.helper.Constants.TAB_GROUP;
import static br.com.dsd.app.server.helper.Constants.TAB_LOG;
import static br.com.dsd.app.server.helper.Constants.TAB_USER;
import static br.com.dsd.app.server.helper.Constants.TOOLTIP_TAB_GROUP;
import static br.com.dsd.app.server.helper.Constants.TOOLTIP_TAB_LOG;
import static br.com.dsd.app.server.helper.Constants.TOOLTIP_TAB_USER;
import static br.com.dsd.app.server.helper.IconConstants.ADD_USER;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;

import br.com.dsd.app.server.dao.GroupDAO;
import br.com.dsd.app.server.dao.UserDAO;
import br.com.dsd.app.server.dao.factory.DAOFactory;
import br.com.dsd.app.server.entity.Group;
import br.com.dsd.app.server.entity.User;
import br.com.dsd.app.server.gui.dialog.Message;
import br.com.dsd.app.server.helper.BuilderUtil;
import br.com.dsd.app.server.helper.Constants;
import br.com.dsd.app.server.helper.IconUtil;
import br.com.dsd.app.server.helper.MD5Util;
import br.com.dsd.app.server.helper.Util;
import br.com.dsd.app.server.message.Logger;
import br.com.dsd.app.server.socket.Server;

/**
 * Painel que contêm as informações do servidor (Log e Usuários)
 * 
 * @author kl
 *
 */
public class InfoPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private UserDAO userDAO;
	private GroupDAO groupDAO;

	private JTabbedPane tabbedPane;

	private JTextPane txtLog;
	private JTable tblUsers;
	private JTable tblGroups;

	private CardLayout userCardLayout = new CardLayout();
	private CardLayout groupCardLayout = new CardLayout();
	private JPanel pnlUsersOperation;
	private JPanel pnlUserTable;
	private JPanel pnlUserAdd;
	private JPanel pnlGroupsOperation;
	private JPanel pnlGroupTable;
	private JPanel pnlGroupAdd;
	private JTextField txtUserNickname = new JTextField();
	private JPasswordField txtUserPassword = new JPasswordField();
	private JTextField txtUserName = new JTextField();
	private JTextField txtUserSurname = new JTextField();
	private JTextField txtUserEmail = new JTextField();
	private JTextField txtGroupName = new JTextField();

	public InfoPanel() {
		DAOFactory daoFactory = DAOFactory.getFactory();
		userDAO = daoFactory.getUserDAO();
		groupDAO = daoFactory.getGroupDAO();

		setBackground(Color.BLACK);
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBackground(Color.WHITE);
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		add(tabbedPane);

		criarPaineis();
		carregarUsuarios();
		carregarGrupos();
	}

	private void criarPaineis() {
		criarPainelLog();
		criarPainelUsuario();
		criarPainelGrupo();
	}

	private void criarPainelLog() {
		JPanel pnlLog = new JPanel();
		tabbedPane.addTab(TAB_LOG, null, pnlLog, TOOLTIP_TAB_LOG);
		pnlLog.setLayout(new BoxLayout(pnlLog, BoxLayout.X_AXIS));

		JScrollPane sclLog = new JScrollPane();
		pnlLog.add(sclLog);

		txtLog = new JTextPane();
		txtLog.setEditable(false);
		Style logStyle = txtLog.addStyle("logStyle", null);
		StyleConstants.setForeground(logStyle, Color.BLACK);

		Style errStyle = txtLog.addStyle("errStyle", null);
		StyleConstants.setForeground(errStyle, Color.RED);

		sclLog.setViewportView(txtLog);
	}

	private void criarPainelUsuario() {
		JPanel pnlUsers = new JPanel();
		tabbedPane.addTab(TAB_USER, null, pnlUsers, TOOLTIP_TAB_USER);
		pnlUsers.setLayout(new BorderLayout(0, 0));

		pnlUsersOperation = new JPanel(userCardLayout);
		pnlUsers.add(pnlUsersOperation);

		JPanel pnlUserAction = new JPanel();
		pnlUserAction.setBackground(Color.WHITE);
		FlowLayout flowLayoutUser = (FlowLayout) pnlUserAction.getLayout();
		flowLayoutUser.setAlignment(FlowLayout.RIGHT);
		pnlUsers.add(pnlUserAction, BorderLayout.NORTH);

		DefaultTableModel userTableModel = new DefaultTableModel(new Object[][] {},
				Constants.COLUNAS_TABELA_INFO_USUARIOS);

		pnlUserTable = new JPanel();
		pnlUserTable.setLayout(new BorderLayout(0, 0));
		JScrollPane sclUsers = new JScrollPane();
		tblUsers = new JTable();
		tblUsers.setBackground(Color.WHITE);
		sclUsers.setViewportView(tblUsers);
		tblUsers.setModel(userTableModel);
		pnlUserTable.add(sclUsers);

		pnlUsersOperation.add(pnlUserTable, "Table");

		pnlUserAdd = new JPanel(new GridBagLayout());
		pnlUserAdd.setFocusable(true);
		pnlUserAdd.requestFocusInWindow();
		GridBagConstraints gbcUser = new GridBagConstraints();
		gbcUser.anchor = GridBagConstraints.EAST;
		gbcUser.insets = new Insets(10, 10, 10, 10);
		gbcUser.weightx = 1.;
		gbcUser.fill = GridBagConstraints.HORIZONTAL;

		gbcUser.gridx = 0;
		gbcUser.gridy = 0;
		pnlUserAdd.add(new JLabel("Nickname"), gbcUser);

		gbcUser.gridx = 1;
		txtUserNickname.setColumns(50);
		pnlUserAdd.add(txtUserNickname, gbcUser);

		gbcUser.gridx = 0;
		gbcUser.gridy = 1;
		pnlUserAdd.add(new JLabel("Password"), gbcUser);

		gbcUser.gridx = 1;
		pnlUserAdd.add(txtUserPassword, gbcUser);

		gbcUser.gridx = 0;
		gbcUser.gridy = 2;
		pnlUserAdd.add(new JLabel("Name"), gbcUser);

		gbcUser.gridx = 1;
		txtUserName.setColumns(50);
		pnlUserAdd.add(txtUserName, gbcUser);

		gbcUser.gridx = 0;
		gbcUser.gridy = 3;
		pnlUserAdd.add(new JLabel("Surname"), gbcUser);

		gbcUser.gridx = 1;
		txtUserSurname.setColumns(50);
		pnlUserAdd.add(txtUserSurname, gbcUser);

		gbcUser.gridx = 0;
		gbcUser.gridy = 4;
		pnlUserAdd.add(new JLabel("Email"), gbcUser);

		gbcUser.gridx = 1;
		txtUserEmail.setColumns(50);
		pnlUserAdd.add(txtUserEmail, gbcUser);

		gbcUser.gridx = 0;
		gbcUser.gridy = 5;
		gbcUser.gridwidth = 2;
		gbcUser.fill = GridBagConstraints.BOTH;
		gbcUser.anchor = GridBagConstraints.CENTER;
		JButton btnAddUser = new JButton("Adicionar");
		btnAddUser.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				efetuarOperacoesAdicionarUsuario();
			}
		});
		btnAddUser.setBorder(BorderFactory.createEmptyBorder());
		btnAddUser.setBackground(Color.WHITE);
		pnlUserAdd.add(btnAddUser, gbcUser);

		InputMap inputUserMap = pnlUserAdd.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
		ActionMap actionUserMap = pnlUserAdd.getActionMap();
		inputUserMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "submitUserForm");
		actionUserMap.put("submitUserForm", new AbstractAction("submitUserForm") {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				efetuarOperacoesAdicionarUsuario();
			}

		});

		JScrollPane sclAddUser = new JScrollPane(pnlUserAdd);
		pnlUsersOperation.add(sclAddUser, "Add");

		JButton btnAdicionarUser = new JButton();
		btnAdicionarUser.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				userCardLayout.next(pnlUsersOperation);
			}
		});
		btnAdicionarUser.setBorder(BorderFactory.createEmptyBorder());
		btnAdicionarUser.setBackground(Color.WHITE);
		btnAdicionarUser.setIcon(IconUtil.getIcon(ADD_USER));

		pnlUserAction.add(btnAdicionarUser);
	}

	private void criarPainelGrupo() {
		JPanel pnlGroups = new JPanel();
		tabbedPane.addTab(TAB_GROUP, null, pnlGroups, TOOLTIP_TAB_GROUP);
		pnlGroups.setLayout(new BorderLayout(0, 0));

		pnlGroupsOperation = new JPanel(groupCardLayout);
		pnlGroups.add(pnlGroupsOperation, BorderLayout.CENTER);

		JPanel pnlGroupAction = new JPanel();
		pnlGroupAction.setBackground(Color.WHITE);
		FlowLayout flowLayoutGroup = (FlowLayout) pnlGroupAction.getLayout();
		flowLayoutGroup.setAlignment(FlowLayout.RIGHT);
		pnlGroups.add(pnlGroupAction, BorderLayout.NORTH);

		JScrollPane sclGroups = new JScrollPane();

		pnlGroupTable = new JPanel();
		tblGroups = new JTable();
		tblGroups.setBackground(Color.WHITE);
		sclGroups.setViewportView(tblGroups);
		DefaultTableModel groupTableModel = new DefaultTableModel(new Object[][] {},
				Constants.COLUNAS_TABELA_INFO_GRUPOS);
		pnlGroupTable.setLayout(new BorderLayout(0, 0));
		tblGroups.setModel(groupTableModel);
		pnlGroupTable.add(sclGroups);

		pnlGroupsOperation.add(pnlGroupTable, "Table");

		pnlGroupAdd = new JPanel(new GridBagLayout());
		GridBagConstraints gbcGroup = new GridBagConstraints();
		gbcGroup.anchor = GridBagConstraints.EAST;
		gbcGroup.insets = new Insets(10, 10, 10, 10);
		gbcGroup.weightx = 1.;
		gbcGroup.fill = GridBagConstraints.HORIZONTAL;

		gbcGroup.gridx = 0;
		gbcGroup.gridy = 0;
		pnlGroupAdd.add(new JLabel("Nickname"), gbcGroup);

		gbcGroup.gridx = 1;
		txtGroupName.setColumns(50);
		pnlGroupAdd.add(txtGroupName, gbcGroup);

		gbcGroup.gridx = 0;
		gbcGroup.gridy = 1;
		gbcGroup.gridwidth = 2;
		gbcGroup.fill = GridBagConstraints.BOTH;
		gbcGroup.anchor = GridBagConstraints.CENTER;
		JButton btnAddGroup = new JButton("Adicionar");
		btnAddGroup.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				efetuarOperacoesAdicionarGrupo();
			}
		});
		btnAddGroup.setBorder(BorderFactory.createEmptyBorder());
		btnAddGroup.setBackground(Color.WHITE);
		pnlGroupAdd.add(btnAddGroup, gbcGroup);

		InputMap inputGroupMap = pnlGroupAdd.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
		ActionMap actionGroupMap = pnlGroupAdd.getActionMap();
		inputGroupMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "submitGroupForm");
		actionGroupMap.put("submitGroupForm", new AbstractAction("submitGroupForm") {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				efetuarOperacoesAdicionarGrupo();
			}

		});

		JScrollPane sclAddGroup = new JScrollPane(pnlGroupAdd);
		pnlGroupsOperation.add(sclAddGroup, "Add");

		JButton btnAdicionarGroup = new JButton();
		btnAdicionarGroup.setBorder(BorderFactory.createEmptyBorder());
		btnAdicionarGroup.setBackground(Color.WHITE);
		btnAdicionarGroup.setIcon(IconUtil.getIcon(ADD_USER));
		btnAdicionarGroup.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				groupCardLayout.next(pnlGroupsOperation);
			}
		});

		pnlGroupAction.add(btnAdicionarGroup);
	}

	public JTextPane getTxtLog() {
		return txtLog;
	}

	private void carregarUsuarios() {
		userDAO.beginTransaction();
		List<User> users = (List<User>) userDAO.listAll();
		userDAO.commitTransaction();

		for (User user : users) {
			adicionarUsuarioTabela(user);
		}
	}

	private void adicionarUsuarioTabela(User user) {
		Object[] row = new Object[Constants.COLUNAS_TABELA_INFO_USUARIOS.length];
		row[0] = user.getNickname();
		row[1] = user.getEntireName();
		row[2] = user.getEmail();

		((DefaultTableModel) tblUsers.getModel()).insertRow(tblUsers.getRowCount(), row);
	}

	private void carregarGrupos() {
		groupDAO.beginTransaction();
		List<Group> groups = (List<Group>) groupDAO.listAll();
		groupDAO.commitTransaction();

		for (Group group : groups) {
			adicionarGrupoTabela(group);
		}
	}

	private void adicionarGrupoTabela(Group group) {
		Object[] row = new Object[Constants.COLUNAS_TABELA_INFO_GRUPOS.length];
		row[0] = group.getName();

		((DefaultTableModel) tblGroups.getModel()).insertRow(tblGroups.getRowCount(), row);
	}

	private void efetuarOperacoesAdicionarUsuario() {
		if (Util.isNotNullFields(txtUserNickname, txtUserName, txtUserSurname, txtUserEmail)) {

			try {
				User user = new User(txtUserNickname.getText(), txtUserName.getText(), txtUserSurname.getText(),
						txtUserEmail.getText(), MD5Util.convert(new String(txtUserPassword.getPassword())), 'I');
				adicionarUsuario(user);
				Util.clearFields(txtUserNickname, txtUserPassword, txtUserName, txtUserSurname, txtUserEmail);
			} catch (NoSuchAlgorithmException e) {
				Message.createMessage("Problemas ao processar as informações do usuário.");
			}
		}
	}

	private void adicionarUsuario(User user) {
		userDAO.beginTransaction();
		boolean adicionado = userDAO.save(user);
		userDAO.commitTransaction();
		if (adicionado) {
			Logger.log("Usuário " + user.getNickname() + " adicionado.");
			adicionarUsuarioTabela(user);
			userCardLayout.next(pnlUsersOperation);
			Server.sendToAll(BuilderUtil.convertToDTO(user), null);
		} else {
			Message.createMessage("Usuário com apelido ou e-mail já existente.");
		}
	}

	private void efetuarOperacoesAdicionarGrupo() {
		if (Util.isNotNullFields(txtGroupName)) {
			Group group = new Group(txtGroupName.getText());
			adicionarGrupo(group);
			Util.clearFields(txtGroupName);
		}
	}

	private void adicionarGrupo(Group group) {
		groupDAO.beginTransaction();
		boolean adicionado = groupDAO.save(group);
		groupDAO.commitTransaction();
		if (adicionado) {
			Logger.log("Grupo " + group.getName() + " adicionado.");
			adicionarGrupoTabela(group);
			groupCardLayout.next(pnlGroupsOperation);
			Server.sendToAll(BuilderUtil.convertToDTO(group));
		} else {
			Message.createMessage("Grupo com este nome já existe.");
		}
	}

}
