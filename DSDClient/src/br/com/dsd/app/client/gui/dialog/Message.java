package br.com.dsd.app.client.gui.dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import br.com.dsd.app.client.gui.frame.MainFrame;
import br.com.dsd.app.client.gui.main.panel.HeadPanel;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.SwingConstants;

public class Message extends JDialog {

	private static final long serialVersionUID = 1L;

	private final JPanel contentPanel = new JPanel();

	private static Message message = null;
	private JLabel lblMessage;

	public static void createMessage(String msg) {
		if (message == null)
			message = new Message(msg);
		message.setVisible(true);
	}

	private Message() {
	}

	private Message(String message) {
		setTitle("DSD-WS-Client");
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			UIManager.put("PopupMenu.border", BorderFactory.createEmptyBorder());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		setUndecorated(true);
		setBounds(100, 100, 400, 280);
		contentPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
		contentPanel.setLayout(new BorderLayout(0, 0));

		JPanel pnlHead = new HeadPanel();
		pnlHead.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
		contentPanel.add(pnlHead, BorderLayout.NORTH);
		{
			JPanel pnlMessage = new JPanel();
			contentPanel.add(pnlMessage, BorderLayout.CENTER);
			pnlMessage.setLayout(new BorderLayout(0, 0));
			{
				lblMessage = new JLabel(message);
				lblMessage.setHorizontalAlignment(SwingConstants.CENTER);
				pnlMessage.add(lblMessage, BorderLayout.CENTER);
			}
		}

		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			JPanel pnlButtons = new JPanel();
			pnlButtons.setBackground(Color.GRAY);
			contentPanel.add(pnlButtons, BorderLayout.SOUTH);
			pnlButtons.setLayout(new FlowLayout(FlowLayout.CENTER));
			{
				JButton okButton = new JButton("OK");
				okButton.setBackground(Color.WHITE);
				okButton.setActionCommand("OK");
				okButton.addMouseListener(new MouseHandler());
				pnlButtons.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}

	private class MouseHandler extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			message.dispose();
		}
	}

}
