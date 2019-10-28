package br.com.dsd.app.client.entity;

import java.util.Date;

public class Message {

	private User sender;
	private Date sendDate;
	private String text;

	public Message(User sender, Date sendDate, String text) {
		super();
		this.sender = sender;
		this.sendDate = sendDate;
		this.text = text;
	}

	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
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
