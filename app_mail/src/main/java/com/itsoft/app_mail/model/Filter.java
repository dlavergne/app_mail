package com.itsoft.app_mail.model;

import org.apache.commons.lang3.StringUtils;

/**
 * @author dlave
 *
 */
public class Filter {
	
	private String sender;	
	private Boolean attachments;
	private String WordInSubject;
	
	public Filter(String sender, Boolean attachments, String word) {
		this.sender = sender;
		this.attachments = attachments;
		this.WordInSubject = word;
	}
	
	public Filter(String sender, Boolean attachments) {
		this.sender = sender;
		this.attachments = attachments;
	}
	
	public Filter(String sender, String word) {
		this.sender = sender;
		this.WordInSubject = word;
	}
	
	public Filter(String sender) {
		this.sender = sender;
	}

	public Filter() {
		
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public Boolean getAttachments() {
		return attachments;
	}

	public void setAttachments(Boolean attachments) {
		this.attachments = attachments;
	}
	
	public void setAttachments(String filterOnAttachments) {
		
		if (StringUtils.equalsIgnoreCase(filterOnAttachments, "false"))
			attachments = false;
		else if (StringUtils.equalsIgnoreCase(filterOnAttachments, "true"))
			attachments = true;
		else
			attachments = null;
	}

	public String getWordInSubject() {
		return WordInSubject;
	}

	public void setWordInSubject(String wordInSubject) {
		this.WordInSubject = wordInSubject;
	}
	
	
}