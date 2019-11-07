package br.com.dsd.app.server.entity.dto;

import java.io.Serializable;
import java.util.Date;

public class MessageDTO implements Serializable {

	private static final long serialVersionUID = 7976707883891601695L;
	
	private UserDTO receiver;
	private UserDTO sender;
	private Date sendDate;
	private String text;

	public MessageDTO(UserDTO receiver, UserDTO sender, Date sendDate, String text) {
		super();
		this.receiver = receiver;
		this.sender = sender;
		this.sendDate = sendDate;
		this.text = text;
	}

	public UserDTO getReceiver() {
		return receiver;
	}

	public void setReceiver(UserDTO receiver) {
		this.receiver = receiver;
	}

	public UserDTO getSender() {
		return sender;
	}

	public void setSender(UserDTO sender) {
		this.sender = sender;
	}

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
