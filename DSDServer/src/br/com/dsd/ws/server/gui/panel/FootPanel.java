package br.com.dsd.ws.server.gui.panel;

import java.awt.Color;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * Painel do rodapé da aplicação
 * 
 * @author kl
 *
 */
public class FootPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Cria o painel
	 */
	public FootPanel() {
		setBackground(Color.GRAY);
		JLabel lblInfo = new JLabel("DSD-WS", SwingConstants.RIGHT);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(5)
					.addComponent(lblInfo, GroupLayout.DEFAULT_SIZE, 785, Short.MAX_VALUE)
					.addGap(5))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(5)
					.addComponent(lblInfo, GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE)
					.addGap(5))
		);
		setLayout(groupLayout);
	}

}
