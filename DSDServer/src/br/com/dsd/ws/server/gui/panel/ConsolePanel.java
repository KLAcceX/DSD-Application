package br.com.dsd.ws.server.gui.panel;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * JPanel que contem as informações do console
 * 
 * @author kl
 *
 */
public class ConsolePanel extends JPanel {

	private static final long serialVersionUID = -1000316165694809546L;

	private JTextArea txtaConsole;

	public ConsolePanel() {
		setLayout(null);
		generateConsoleTextArea();
	}

	/**
	 * Gera os componentes correspondentes a area de texto para os logs
	 */
	private void generateConsoleTextArea() {
		JScrollPane spnConsole = new JScrollPane();

		txtaConsole = new JTextArea();
		txtaConsole.setColumns(98);
		txtaConsole.setRows(21);
		txtaConsole.setEditable(false);
		spnConsole.setViewportView(txtaConsole);

		add(spnConsole);
	}

	/**
	 * Getter da Área de Texto do Console
	 * @return Área de Texto do console
	 */
	public JTextArea getTxtaConsole() {
		return txtaConsole;
	}

}
