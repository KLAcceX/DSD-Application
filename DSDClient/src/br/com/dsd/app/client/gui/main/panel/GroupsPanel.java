package br.com.dsd.app.client.gui.main.panel;

import java.awt.GridLayout;

import javax.swing.JPanel;

import br.com.dsd.app.client.entity.dto.GroupDTO;
import br.com.dsd.app.client.gui.component.ContactLabel;
import br.com.dsd.app.client.service.GroupService;

/**
 * Painel para carregar todos os grupos do sistema
 * 
 * @author kl
 *
 */
public class GroupsPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	public GroupsPanel(ChatPanel chat) {
		setLayout(new GridLayout(0, 1));

		//TODO: Definir forma de atualizar painel de Grupo
//		for (GroupDTO group : GroupService.getInstance().getList()) {
//			add(new ContactLabel(group, chat));
//		}
	}
}
