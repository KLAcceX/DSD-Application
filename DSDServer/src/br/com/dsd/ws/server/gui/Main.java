package br.com.dsd.ws.server.gui;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import br.com.dsd.ws.server.gui.panel.ConsolePanel;
import br.com.dsd.ws.server.gui.panel.ServerPanel;

public class Main extends JFrame {

	private static final long serialVersionUID = 6194063708029266837L;

	private Point initialClick;
	private JPanel contentPane;
	private static ServerPanel pnlServer;
	private static ConsolePanel pnlConsole;

	/**
	 * Inicia a aplicação
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
	 * Cria o Frame.
	 */
	public Main() {
		doFrameOperations();
	}

	/**
	 * Realiza as operações inicias do JFrame e do JPanel principal.
	 */
	private void doFrameOperations() {
		setContentPaneOptions();
		setFrameOptions();

		JLabel lblClose = generateCloseLabel();
		contentPane.add(lblClose);

		pnlServer = new ServerPanel();
		pnlServer.setBounds(0, 0, 800, 100);
		contentPane.add(pnlServer);

		pnlConsole = new ConsolePanel();
		pnlConsole.setBounds(0, 100, 800, 400);
		contentPane.add(pnlConsole);
	}

	/**
	 * Define as opções do JFrame
	 */
	private void setFrameOptions() {
		setTitle("DSD-WS-Server");
		addMouseListener(new MousePressedHandler());
		addMouseMotionListener(new MouseMotionHandler());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setUndecorated(true);
		setBounds(100, 100, 800, 500);
		setContentPane(contentPane);
	}

	/**
	 * Define as opções do JPanel principal
	 */
	private void setContentPaneOptions() {
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
	}

	/**
	 * Gera a JLabel que representa o botão de fechar do JFrame
	 * 
	 * @return JLabel com X
	 */
	private JLabel generateCloseLabel() {
		JLabel jLabel = new JLabel("X");
		jLabel.addMouseListener(new MouseClickHandler());
		jLabel.setFont(new Font("Cambria", Font.PLAIN, 15));
		jLabel.setBounds(780, 5, 10, 24);
		return jLabel;
	}

	/**
	 * MouseAdapter para fechar a aplicação
	 * 
	 * @author kl
	 *
	 */
	private class MouseClickHandler extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			System.exit(0);
		}
	}

	/**
	 * MouseAdapter para gravar o Ponto em que o mouse está pressionando
	 * 
	 * @author kl
	 *
	 */
	private class MousePressedHandler extends MouseAdapter {
		@Override
		public void mousePressed(MouseEvent e) {
			initialClick = e.getPoint();
		}
	}

	/**
	 * MouseMotionAdapter para transladar em relação a movimentação do mouse
	 * 
	 * @author kl
	 *
	 */
	private class MouseMotionHandler extends MouseMotionAdapter {
		@Override
		public void mouseDragged(MouseEvent e) {
			if (initialClick != null) {
				Point newPoint = e.getLocationOnScreen();
				newPoint.translate(-initialClick.x, -initialClick.y);
				setLocation(newPoint);
			}
		}
	}

	/**
	 * Getter do Painel com as informações do Servidor
	 * @return Painel do Servidor
	 */
	public static ServerPanel getPnlServer() {
		return pnlServer;
	}

	/**
	 * Getter do Painel com o Console
	 * @return Painel do Console
	 */
	public static ConsolePanel getPnlConsole() {
		return pnlConsole;
	}
	
}
