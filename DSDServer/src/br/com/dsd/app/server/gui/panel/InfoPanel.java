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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import br.com.dsd.app.server.dao.GenericDAO;
import br.com.dsd.app.server.entity.dao.GroupDAO;
import br.com.dsd.app.server.entity.dao.UserDAO;
import br.com.dsd.app.server.helper.Constants;
import br.com.dsd.app.server.helper.IconUtil;

/**
 * Painel que contêm as informações do servidor (Log e Usuários)
 * 
 * @author kl
 *
 */
public class InfoPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private JTextArea txtLog;
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
	private JTextField txtUserName = new JTextField();
	private JTextField txtUserSurname = new JTextField();
	private JTextField txtUserEmail = new JTextField();
	private JTextField txtGroupName = new JTextField();

	/**
	 * Create the panel.
	 */
	public InfoPanel() {
		setBackground(Color.BLACK);
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBackground(Color.WHITE);
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		add(tabbedPane);

		JPanel pnlLog = new JPanel();
		tabbedPane.addTab(TAB_LOG, null, pnlLog, TOOLTIP_TAB_LOG);
		pnlLog.setLayout(new BoxLayout(pnlLog, BoxLayout.X_AXIS));

		JScrollPane sclLog = new JScrollPane();
		pnlLog.add(sclLog);

		txtLog = new JTextArea();
		sclLog.setViewportView(txtLog);

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
		pnlUserAdd.add(new JLabel("Name"), gbcUser);
		
		gbcUser.gridx = 1;
		txtUserName.setColumns(50);
		pnlUserAdd.add(txtUserName, gbcUser);
		
		gbcUser.gridx = 0;
		gbcUser.gridy = 2;
		pnlUserAdd.add(new JLabel("Surname"), gbcUser);
		
		gbcUser.gridx = 1;
		txtUserSurname.setColumns(50);
		pnlUserAdd.add(txtUserSurname, gbcUser);
		
		gbcUser.gridx = 0;
		gbcUser.gridy = 3;
		pnlUserAdd.add(new JLabel("Email"), gbcUser);
		
		gbcUser.gridx = 1;
		txtUserEmail.setColumns(50);
		pnlUserAdd.add(txtUserEmail, gbcUser);
		
		gbcUser.gridx = 0;
		gbcUser.gridy = 4;
		gbcUser.gridwidth = 2;
		gbcUser.anchor = GridBagConstraints.CENTER;
		JButton btnAddUser = new JButton("Adicionar");
		btnAddUser.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				UserDAO user = new UserDAO(txtUserNickname.getText(), txtUserName.getText(), txtUserSurname.getText(), txtUserEmail.getText());
				GenericDAO.insert(user);
				getTxtLog().append("Adicionando Usuário");
				adicionarUsuarioTabela(user);
				txtUserNickname.setText("");
				txtUserName.setText("");
				txtUserSurname.setText("");
				txtUserEmail.setText("");
				userCardLayout.next(pnlUsersOperation);
			}
		});
		btnAddUser.setBorder(BorderFactory.createEmptyBorder());
		btnAddUser.setBackground(Color.WHITE);
		pnlUserAdd.add(btnAddUser, gbcUser);
		
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

		JPanel pnlGroups = new JPanel();
		tabbedPane.addTab(TAB_GROUP, null, pnlGroups, TOOLTIP_TAB_GROUP);
		pnlGroups.setLayout(new BorderLayout(0, 0));

		pnlGroupsOperation = new JPanel(groupCardLayout);
		pnlGroups.add(pnlGroupsOperation, BorderLayout.CENTER);

		JPanel pnlGroupAction = new JPanel();
		pnlGroupAction.setBackground(Color.WHITE);
		FlowLayout flowLayoutGroup = (FlowLayout) pnlUserAction.getLayout();
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
		gbcGroup.anchor = GridBagConstraints.CENTER;
		JButton btnAddGroup = new JButton("Adicionar");
		btnAddGroup.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				GroupDAO group = new GroupDAO(txtGroupName.getText());
				GenericDAO.insert(group);
				getTxtLog().append("Adicionando Grupo");
				adicionarGrupoTabela(group);
				txtGroupName.setText("");
				groupCardLayout.next(pnlGroupsOperation);
			}
		});
		btnAddGroup.setBorder(BorderFactory.createEmptyBorder());
		btnAddGroup.setBackground(Color.WHITE);
		pnlGroupAdd.add(btnAddGroup, gbcGroup);
		
		JScrollPane sclAddGroup = new JScrollPane(pnlGroupAdd);
		pnlGroupsOperation.add(sclAddGroup, "Add");
		
		JButton btnAdicionarGroup = new JButton();
		// TODO: Método para Adicionar o Grupo
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

		carregarUsuarios();
		carregarGrupos();
	}

	public JTextArea getTxtLog() {
		return txtLog;
	}

	private void carregarUsuarios() {
		List<UserDAO> users = (List<UserDAO>) GenericDAO.list(UserDAO.class);

		for (UserDAO user : users) {
			adicionarUsuarioTabela(user);
		}
	}

	private void adicionarUsuarioTabela(UserDAO user) {
		Object[] row = new Object[Constants.COLUNAS_TABELA_INFO_USUARIOS.length];
		row[0] = user.getNickname();
		row[1] = user.getEntireName();
		row[2] = user.getEmail();

		((DefaultTableModel) tblUsers.getModel()).insertRow(tblUsers.getRowCount(), row);
	}

	private void carregarGrupos() {
		List<GroupDAO> groups = (List<GroupDAO>) GenericDAO.list(GroupDAO.class);

		for (GroupDAO group : groups) {
			adicionarGrupoTabela(group);
		}
	}

	private void adicionarGrupoTabela(GroupDAO group) {
		Object[] row = new Object[Constants.COLUNAS_TABELA_INFO_GRUPOS.length];
		row[0] = group.getName();

		((DefaultTableModel) tblGroups.getModel()).insertRow(tblGroups.getRowCount(), row);
	}

}
