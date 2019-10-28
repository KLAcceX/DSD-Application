package br.com.dsd.app.server.gui.panel;

import static br.com.dsd.app.server.helper.Constants.*;

import static br.com.dsd.app.server.helper.IconConstants.ADD_USER;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

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

	private JTable table;
	private JTextArea txtLog;

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
		FlowLayout flowLayout = (FlowLayout) pnlUserAction.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		pnlUsers.add(pnlUserAction);

		JButton btnAdicionar = new JButton();
		//TODO: Método para Adicionar o Usuário
		btnAdicionar.setBorder(BorderFactory.createEmptyBorder());
		btnAdicionar.setBackground(Color.WHITE);
		btnAdicionar.setIcon(IconUtil.getIcon(ADD_USER));

		pnlUserAction.add(btnAdicionar);

		JScrollPane sclUsers = new JScrollPane();
		pnlUsers.add(sclUsers);

		table = new JTable();
		table.setBackground(Color.WHITE);
		sclUsers.setViewportView(table);
		table.setModel(new DefaultTableModel(new Object[][] {}, Constants.COLUNAS_TABELA_INFO_USUARIOS));

	}

	public JTextArea getTxtLog() {
		return txtLog;
	}

}
