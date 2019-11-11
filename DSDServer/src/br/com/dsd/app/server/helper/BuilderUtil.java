package br.com.dsd.app.server.helper;

import java.util.ArrayList;
import java.util.List;

import br.com.dsd.app.dto.GroupDTO;
import br.com.dsd.app.dto.UserDTO;
import br.com.dsd.app.server.entity.Group;
import br.com.dsd.app.server.entity.User;

public class BuilderUtil {
	
	public static UserDTO convertToDTO(User user) {
		UserDTO userDTO = new UserDTO();
		userDTO.setNickname(user.getNickname());
		userDTO.setName(user.getName());
		userDTO.setSurname(user.getSurname());
		userDTO.setEmail(user.getEmail());
		userDTO.setStatus(user.getStatus());
		return userDTO;
	}
	
	public static GroupDTO convertToDTO(Group group) {
		GroupDTO groupDTO = new GroupDTO();
		groupDTO.setName(group.getName());
		return groupDTO;
	}
	
	public static List<UserDTO> convertToUserDTOList(List<User> users){
		List<UserDTO> userDTOList = new ArrayList<UserDTO>();
		for(User user : users) 
			userDTOList.add(convertToDTO(user));
		return userDTOList;
	}
	
	public static List<GroupDTO> convertToGroupDTOList(List<Group> groups){
		List<GroupDTO> groupDTOList = new ArrayList<GroupDTO>();
		for(Group group : groups) 
			groupDTOList.add(convertToDTO(group));
		return groupDTOList;
	}

}
