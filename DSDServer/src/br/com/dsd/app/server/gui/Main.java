package br.com.dsd.app.server.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import br.com.dsd.app.server.gui.panel.FootPanel;
import br.com.dsd.app.server.gui.panel.FormPanel;
import br.com.dsd.app.server.gui.panel.HeadPanel;
import br.com.dsd.app.server.gui.panel.InfoPanel;

/**
 * Janela da aplicação
 * 
 * @author kl
 *
 */
public class Main extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private static JPanel pnlForm;
	private static JPanel pnlInfo;

	/**
	 * Inicia a Aplicação
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Cria a janela
	 */
	public Main() {
		setTitle("DSD-WS-Server");
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setUndecorated(true);
		setBounds(100, 100, 800, 480);
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

		JSplitPane splitPane = new JSplitPane();
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		contentPane.add(splitPane, BorderLayout.CENTER);

		pnlForm = new FormPanel();
		splitPane.setLeftComponent(pnlForm);

		pnlInfo = new InfoPanel();
		splitPane.setRightComponent(pnlInfo);
	}

	public static InfoPanel getPnlInfo() {
		return (InfoPanel) pnlInfo;
	}

}
