package br.com.dsd.app.client.gui.main.panel;

import java.awt.GridLayout;

import javax.swing.JPanel;

import br.com.dsd.app.client.entity.dto.UserDTO;
import br.com.dsd.app.client.gui.component.ContactLabel;
import br.com.dsd.app.client.service.UserService;

/**
 * Painel para carregar todos os usuários do sistema
 * 
 * @author kl
 *
 */
public class ContactsPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	public ContactsPanel(ChatPanel chat) {
		setLayout(new GridLayout(0, 1));
		
		//TODO: Definir forma de atualizar painel de Usuário
//		for (UserDTO user : UserService.getInstance().getList()) {
//			add(new ContactLabel(user, chat));
//		}
	}
}
