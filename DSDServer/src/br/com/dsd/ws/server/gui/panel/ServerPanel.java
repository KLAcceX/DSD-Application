package br.com.dsd.ws.server.gui.panel;

import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;

/**
 * JPanel que contem as informações do servidor, assim como possibilita seu
 * início e sua parada.
 * 
 * @author kl
 *
 */
public class ServerPanel extends JPanel {

	private static final long serialVersionUID = 6859063989793420749L;

	private JButton btnStart;
	private JButton btnStop;
	private JTextField txtIp;
	private JTextField txtPort;

	public ServerPanel() {
		generateFields();
		generateButtons();
	}

	/**
	 * Gera os campos do painel de Servidor
	 */
	private void generateFields() {
		setLayout(null);
		generateIpField();
		generatePortField();
	}

	/**
	 * Gera os componentes correspondentes ao campo de IP do painel de Servidor
	 */
	private void generateIpField() {
		JSeparator sptIp = new JSeparator();
		sptIp.setForeground(SystemColor.controlText);
		sptIp.setBackground(SystemColor.controlText);
		sptIp.setBounds(80, 62, 137, 1);
		add(sptIp);

		JLabel lblIp = new JLabel("IP");
		lblIp.setFont(new Font("Cambria", Font.PLAIN, 15));
		lblIp.setBounds(50, 38, 20, 24);
		add(lblIp);

		txtIp = new JFormattedTextField();
		txtIp.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtIp.setBorder(null);
		txtIp.setBackground(SystemColor.menu);
		txtIp.setBounds(80, 38, 137, 24);
		add(txtIp);

		JSeparator sptPort = new JSeparator();
		sptPort.setForeground(SystemColor.controlText);
		sptPort.setBackground(SystemColor.controlText);
		sptPort.setBounds(269, 62, 137, 1);
		add(sptPort);
	}

	/**
	 * Gera os componentes correspondentes ao campo de Port do painel de Servidor
	 */
	private void generatePortField() {
		JSeparator sptPort = new JSeparator();
		sptPort.setForeground(SystemColor.controlText);
		sptPort.setBackground(SystemColor.controlText);
		sptPort.setBounds(269, 62, 137, 1);
		add(sptPort);

		JLabel lblPort = new JLabel("Port");
		lblPort.setFont(new Font("Cambria", Font.PLAIN, 15));
		lblPort.setBounds(225, 38, 34, 24);
		add(lblPort);

		txtPort = new JTextField();
		txtPort.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtPort.setBorder(null);
		txtPort.setBackground(SystemColor.menu);
		txtPort.setBounds(269, 38, 137, 24);
		add(txtPort);
	}

	/**
	 * Gera os componentes correspondentes aos botões do painel de Servidor
	 */
	private void generateButtons() {
		btnStart = new JButton("Start");
		btnStart.setBackground(SystemColor.windowBorder);
		btnStart.setFont(new Font("Cambria", Font.PLAIN, 15));
		btnStart.setBounds(578, 38, 89, 24);
		btnStart.addMouseListener(new StartButtonListener());

		add(btnStart);

		btnStop = new JButton("Stop");
		btnStop.setToolTipText("Stop the ");
		btnStop.setFont(new Font("Cambria", Font.PLAIN, 15));
		btnStop.setBackground(SystemColor.windowBorder);
		btnStop.setBounds(578, 38, 89, 24);
		btnStop.addMouseListener(new StopButtonListener());

		add(btnStop);
	}

	private class StartButtonListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			btnStart.setVisible(false);
			btnStop.setVisible(true);
		}
	}

	private class StopButtonListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			btnStart.setVisible(false);
			btnStop.setVisible(true);
		}
	}

}
