package br.com.dsd.app.client.gui.main.panel;

import static br.com.dsd.app.client.helper.Constants.EMPTY_STRING;
import static br.com.dsd.app.client.helper.Constants.LEFT_ARROWS;
import static br.com.dsd.app.client.helper.Constants.MENU_CONTACTS;
import static br.com.dsd.app.client.helper.Constants.MENU_EXIT;
import static br.com.dsd.app.client.helper.Constants.MENU_GROUPS;
import static br.com.dsd.app.client.helper.Constants.MENU_SETTINGS;
import static br.com.dsd.app.client.helper.Constants.RIGHT_ARROWS;
import static br.com.dsd.app.client.helper.IconConstants.CONTACTS;
import static br.com.dsd.app.client.helper.IconConstants.EXIT;
import static br.com.dsd.app.client.helper.IconConstants.GROUPS;
import static br.com.dsd.app.client.helper.IconConstants.SETTINGS;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import br.com.dsd.app.client.executable.Application;
import br.com.dsd.app.client.gui.component.ColoredMenuBar;
import br.com.dsd.app.client.helper.IconUtil;

public class MenuPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private JButton btnArrow;
	private JPanel pnlItens;
	private boolean open = true;
	private JMenuBar menuBar;
	private JMenu menuContacts;
	private JMenu menuGroups;
	private JMenu menuSettings;
	private JMenu menuExit;

	/**
	 * Create the panel.
	 */
	public MenuPanel() {
		setLayout(new BorderLayout(0, 0));

		JPanel pnlDivisor = new JPanel();
		pnlDivisor.setBackground(Color.DARK_GRAY);
		add(pnlDivisor, BorderLayout.EAST);

		pnlItens = new JPanel();
		pnlItens.setBackground(Color.LIGHT_GRAY);
		add(pnlItens, BorderLayout.CENTER);

		menuBar = new ColoredMenuBar();
		menuBar.setLayout(new GridLayout(0, 1));
		pnlItens.add(menuBar);

		menuContacts = new JMenu(MENU_CONTACTS);
		menuContacts.setIcon(IconUtil.getIcon(CONTACTS));
		menuBar.add(menuContacts);

		menuGroups = new JMenu(MENU_GROUPS);
		menuGroups.setIcon(IconUtil.getIcon(GROUPS));
		menuBar.add(menuGroups);

		menuSettings = new JMenu(MENU_SETTINGS);
		menuSettings.setIcon(IconUtil.getIcon(SETTINGS));
		menuBar.add(menuSettings);

		menuExit = new JMenu(MENU_EXIT);
		menuExit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Application.changeFrameLogin();
			}
		});
		menuExit.setIcon(IconUtil.getIcon(EXIT));
		menuBar.add(menuExit);

		pnlDivisor.setLayout(new GridLayout(0, 1, 0, 0));

		btnArrow = new JButton(LEFT_ARROWS);
		btnArrow.addMouseListener(new ArrowAdapter());
		btnArrow.setBackground(Color.DARK_GRAY);
		btnArrow.setVerticalAlignment(SwingConstants.TOP);
		btnArrow.setHorizontalAlignment(SwingConstants.CENTER);
		btnArrow.setForeground(Color.WHITE);
		btnArrow.setContentAreaFilled(false);
		pnlDivisor.add(btnArrow);

	}

	private class ArrowAdapter extends MouseAdapter {
		@Override
		public void mousePressed(MouseEvent e) {
			if (open) {
				btnArrow.setText(RIGHT_ARROWS);
			} else {
				btnArrow.setText(LEFT_ARROWS);
			}

			menuContacts.setText(open ? EMPTY_STRING : MENU_CONTACTS);
			menuGroups.setText(open ? EMPTY_STRING : MENU_GROUPS);
			menuSettings.setText(open ? EMPTY_STRING : MENU_SETTINGS);
			menuExit.setText(open ? EMPTY_STRING : MENU_EXIT);
			open = !open;
		}
	}

	public JMenu getMenuContacts() {
		return menuContacts;
	}

	public JMenu getMenuGroups() {
		return menuGroups;
	}

}
