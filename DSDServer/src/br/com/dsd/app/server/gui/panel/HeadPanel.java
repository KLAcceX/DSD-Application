package br.com.dsd.app.server.gui.panel;

import static br.com.dsd.app.server.helper.IconConstants.CLOSE_WINDOW;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingUtilities;

import br.com.dsd.app.server.helper.IconUtil;
import br.com.dsd.app.server.socket.Server;

/**
 * Painel do cabeçalho da aplicação
 * 
 * @author kl
 *
 */
public class HeadPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private Point initialClick;
	private JLabel lblClose;

	public HeadPanel() {
		setBackground(Color.GRAY);
		addMouseListener(new MousePressedHandler());
		addMouseMotionListener(new MouseMotionHandler());
		
		lblClose = new JLabel();
		lblClose.setIcon(IconUtil.getIcon(CLOSE_WINDOW));
		lblClose.addMouseListener(new MouseClickHandler());

		JLabel lblApplicationName = new JLabel("DSD-WS-Server-App");
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup().addGap(5)
						.addComponent(lblApplicationName, GroupLayout.PREFERRED_SIZE, 199, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED, 559, Short.MAX_VALUE)
						.addComponent(lblClose, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE).addGap(5)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addGap(5)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblClose, GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
								.addComponent(lblApplicationName, GroupLayout.DEFAULT_SIZE, 15, Short.MAX_VALUE))
						.addGap(5)));
		setLayout(groupLayout);
	}

	/**
	 * Getter do botão de Fechar
	 * 
	 * @return
	 */
	public JLabel getLblClose() {
		return lblClose;
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
				SwingUtilities.getWindowAncestor(e.getComponent().getParent()).setLocation(newPoint);
			}
		}
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
			Server.unkeep();
			System.exit(0);
		}
	}

}
