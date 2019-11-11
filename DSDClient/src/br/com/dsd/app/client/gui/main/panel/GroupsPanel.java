package br.com.dsd.app.client.gui.main.panel;

import java.awt.GridLayout;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JPanel;

import br.com.dsd.app.client.gui.component.ContactLabel;
import br.com.dsd.app.dto.GroupDTO;

/**
 * Painel para carregar todos os grupos do sistema
 * 
 * @author kl
 *
 */
public class GroupsPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private Map<String, GroupDTO> groups = new HashMap<String, GroupDTO>();
	private ChatPanel chat = null;

	public GroupsPanel(ChatPanel chat) {
		setLayout(new GridLayout(0, 1));
		this.chat = chat;
	}

	public void addGroups(List<GroupDTO> groups) {
		for (GroupDTO group : groups) {
			this.groups.put(group.getName(), group);
		}
		orderGroups();
	}

	public void addGroup(GroupDTO group) {
		this.groups.put(group.getName(), group);
		orderGroups();
	}

	private void orderGroups() {
		removeAll();
		Iterator<Entry<String, GroupDTO>> it = groups.entrySet().iterator();
		while(it.hasNext()) {
			Map.Entry<String, GroupDTO> entry = (Map.Entry<String, GroupDTO>) it.next();
			add(new ContactLabel(entry.getValue(), chat));
		}
		revalidate();
		repaint();
	}

}
