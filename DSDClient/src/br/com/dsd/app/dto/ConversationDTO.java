package br.com.dsd.app.dto;

import java.io.Serializable;

public class ConversationDTO implements Serializable {

	private static final long serialVersionUID = 2315086759905665990L;
	
	private String nickname;
	private MessageDTO message;

	public ConversationDTO(String nickname, MessageDTO message) {
		super();
		this.nickname = nickname;
		this.message = message;
	}

	public ConversationDTO() {
		super();
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public MessageDTO getMessage() {
		return message;
	}

	public void setMessage(MessageDTO message) {
		this.message = message;
	}

}
