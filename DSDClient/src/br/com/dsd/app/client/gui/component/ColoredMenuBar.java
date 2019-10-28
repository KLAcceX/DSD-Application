package br.com.dsd.app.client.gui.component;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.BorderFactory;
import javax.swing.JMenuBar;

/**
 * Barra de Menu com cor definida
 * 
 * @author kl
 *
 */
public class ColoredMenuBar extends JMenuBar {
	private static final long serialVersionUID = 1L;

	public ColoredMenuBar() {
		super();
		setBorder(BorderFactory.createEmptyBorder());
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.LIGHT_GRAY);
		g2d.fillRect(0, 0, getWidth(), getHeight());
	}
}