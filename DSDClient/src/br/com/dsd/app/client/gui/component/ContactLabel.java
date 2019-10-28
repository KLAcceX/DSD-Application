package br.com.dsd.app.client.gui.component;

import static br.com.dsd.app.client.helper.IconConstants.TV_OFF;
import static br.com.dsd.app.client.helper.IconConstants.TV_ON;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import br.com.dsd.app.client.entity.User;
import br.com.dsd.app.client.entity.Group;
import br.com.dsd.app.client.gui.main.panel.ChatPanel;
import br.com.dsd.app.client.helper.IconUtil;

/**
 * Painel que cuida da apresentação de cada contato
 * 
 * @author kl
 *
 */
public class ContactLabel extends JPanel {

	private static final long serialVersionUID = 1L;

	private ContactLabel(String cName, Character status) {
		setLayout(new BorderLayout());
		JLabel name = new JLabel(cName, JLabel.LEFT);
		name.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
		setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
		add(name, BorderLayout.CENTER);

		if (status != null) {
			JLabel icon = new JLabel();
			icon.setIcon(imageIconPorStatus(status));
			add(icon, BorderLayout.EAST);
		}
	}

	/**
	 * Painel para Usuário
	 * 
	 * @param user
	 * @param chat
	 */
	public ContactLabel(User user, ChatPanel chat) {
		this(user.getEntireName(), user.getStatus());
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				chat.adicionarConversa(user.getNickname());
			}
		});
	}

	/**
	 * Painel para Grupo
	 * 
	 * @param group
	 * @param chat
	 */
	public ContactLabel(Group group, ChatPanel chat) {
		this(group.getName(), null);
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				chat.adicionarConversa(group.getName());
			}
		});
	}

	/**
	 * Define um Icone para Status de Usuário
	 * 
	 * @param status
	 * @return
	 */
	private ImageIcon imageIconPorStatus(Character status) {
		String image = "";
		switch (status) {
		case 'A':
			image = TV_ON;
			break;
		default:
			image = TV_OFF;
		}
		return IconUtil.getIcon(image);
	}
}