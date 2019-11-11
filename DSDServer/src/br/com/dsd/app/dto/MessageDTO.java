package br.com.dsd.app.dto;

import java.io.Serializable;
import java.util.Date;

public class MessageDTO implements Serializable {

	private static final long serialVersionUID = 3742414013272046137L;

	private String receiver;
	private String sender;
	private Date sendDate;
	private String text;

	public MessageDTO() {
		super();
	}

	public MessageDTO(String receiver, String sender, Date sendDate, String text) {
		super();
		this.receiver = receiver;
		this.sender = sender;
		this.sendDate = sendDate;
		this.text = text;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
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
