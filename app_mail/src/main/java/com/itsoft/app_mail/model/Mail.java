package com.itsoft.app_mail.model;

/**
 * Objet Mail
 *
 */
public class Mail {

	private Object message;
	
	public Mail() {};

	public Mail(Object emailMessage) {
		message = emailMessage;
	}

	public Object getMessage() {
		return message;
	}

	public void setMessage(Object message) {
		this.message = message;
	}
		
}