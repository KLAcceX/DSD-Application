package br.com.dsd.app.server.gui.panel;

import static br.com.dsd.app.server.helper.Constants.TAB_GROUP;
import static br.com.dsd.app.server.helper.Constants.TAB_LOG;
import static br.com.dsd.app.server.helper.Constants.TAB_USER;
import static br.com.dsd.app.server.helper.Constants.TOOLTIP_TAB_GROUP;
import static br.com.dsd.app.server.helper.Constants.TOOLTIP_TAB_LOG;
import static br.com.dsd.app.server.helper.Constants.TOOLTIP_TAB_USER;
import static br.com.dsd.app.server.helper.IconConstants.ADD_USER;

import java.awt.Color;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

import br.com.dsd.app.server.dao.GenericDAO;
import br.com.dsd.app.server.entity.Group;
import br.com.dsd.app.server.entity.User;
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
		pnlUsers.setLayout(new BoxLayout(pnlUsers, BoxLayout.Y_AXIS));

		JPanel pnlUserAction = new JPanel();
		pnlUserAction.setBackground(Color.WHITE);
		FlowLayout flowLayoutUser = (FlowLayout) pnlUserAction.getLayout();
		flowLayoutUser.setAlignment(FlowLayout.RIGHT);
		pnlUsers.add(pnlUserAction);

		JButton btnAdicionarUser = new JButton();
		//TODO: Método para Adicionar o Usuário
		btnAdicionarUser.setBorder(BorderFactory.createEmptyBorder());
		btnAdicionarUser.setBackground(Color.WHITE);
		btnAdicionarUser.setIcon(IconUtil.getIcon(ADD_USER));

		pnlUserAction.add(btnAdicionarUser);

		JScrollPane sclUsers = new JScrollPane();
		pnlUsers.add(sclUsers);

		tblUsers = new JTable();
		tblUsers.setBackground(Color.WHITE);
		sclUsers.setViewportView(tblUsers);
		DefaultTableModel userTableModel = new DefaultTableModel(new Object[][] { }, Constants.COLUNAS_TABELA_INFO_USUARIOS);
		tblUsers.setModel(userTableModel);
		
		JPanel pnlGroups = new JPanel();
		tabbedPane.addTab(TAB_GROUP, null, pnlGroups, TOOLTIP_TAB_GROUP);
		pnlGroups.setLayout(new BoxLayout(pnlGroups, BoxLayout.Y_AXIS));

		JPanel pnlGroupAction = new JPanel();
		pnlGroupAction.setBackground(Color.WHITE);
		FlowLayout flowLayoutGroup = (FlowLayout) pnlUserAction.getLayout();
		flowLayoutGroup.setAlignment(FlowLayout.RIGHT);
		pnlGroups.add(pnlGroupAction);

		JButton btnAdicionarGroup = new JButton();
		//TODO: Método para Adicionar o Usuário
		btnAdicionarGroup.setBorder(BorderFactory.createEmptyBorder());
		btnAdicionarGroup.setBackground(Color.WHITE);
		btnAdicionarGroup.setIcon(IconUtil.getIcon(ADD_USER));

		pnlGroupAction.add(btnAdicionarGroup);

		JScrollPane sclGroups = new JScrollPane();
		pnlGroups.add(sclGroups);

		tblGroups = new JTable();
		tblGroups.setBackground(Color.WHITE);
		sclGroups.setViewportView(tblGroups);
		DefaultTableModel groupTableModel = new DefaultTableModel(new Object[][] { }, Constants.COLUNAS_TABELA_INFO_USUARIOS);
		tblGroups.setModel(groupTableModel);
		
		carregarUsuarios();
		carregarGrupos();
	}

	public JTextArea getTxtLog() {
		return txtLog;
	}
	
	private void carregarUsuarios() {
		List<User> users = (List<User>) GenericDAO.list(User.class);
		
		for(User user : users) {
			adicionarUsuarioTabela(user);
		}
	}
	
	private void adicionarUsuarioTabela(User user) {
		Object[] row = new Object[Constants.COLUNAS_TABELA_INFO_USUARIOS.length];
		row[0] = user.getId();
		row[1] = user.getNickname();
		row[2] = user.getEntireName();
		row[3] = user.getEmail();
		
		((DefaultTableModel) tblUsers.getModel()).insertRow(tblUsers.getRowCount(), row);
	}
	
	private void carregarGrupos() {
		List<Group> groups = (List<Group>) GenericDAO.list(Group.class);
		
		for(Group group : groups) {
			adicionarGrupoTabela(group);
		}
	}
	
	private void adicionarGrupoTabela(Group group) {
		Object[] row = new Object[Constants.COLUNAS_TABELA_INFO_GRUPOS.length];
		row[0] = group.getId();
		row[1] = group.getName();
		
		((DefaultTableModel) tblGroups.getModel()).insertRow(tblGroups.getRowCount(), row);
	}

}
