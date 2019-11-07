package br.com.dsd.app.client.gui.main.panel;

import static br.com.dsd.app.client.helper.IconConstants.CLOSE_WINDOW;

import java.awt.Color;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingUtilities;

import br.com.dsd.app.client.gui.frame.MainFrame;
import br.com.dsd.app.client.helper.IconUtil;

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
		addMouseListener(new MousePanelHandler());
		addMouseMotionListener(new MouseMotionHandler());

		lblClose = new JLabel();
		lblClose.setIcon(IconUtil.getIcon(CLOSE_WINDOW));
		lblClose.addMouseListener(new MouseCloseClickHandler());

		JLabel lblApplicationName = new JLabel("DSD-WS-Client-App");
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
	private class MousePanelHandler extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
				MainFrame mf = MainFrame.getInstance();
				if (mf.isMaximized())
					mf.minimize();
				else
					mf.maximize();
			}
		}

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
	private class MouseCloseClickHandler extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			System.exit(0);
		}
	}

}
