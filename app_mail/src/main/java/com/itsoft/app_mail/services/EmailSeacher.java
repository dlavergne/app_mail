package com.itsoft.app_mail.services;

import java.util.ArrayList;

import javax.mail.MessagingException;
import com.itsoft.app_mail.model.Filter;
import com.itsoft.app_mail.model.Mail;

public interface EmailSeacher {
	
	/**
	 * Open connection
	 * 
	 * @throws Exception 
	 * @throws MessagingException
	 */
	public  void openConnection(final String protocol, final String host, final String username, final String password) throws Exception;
	
	/**
	 * Close  connection
	 * 
	 * @throws MessagingException
	 */
	public  void closeConnection() throws MessagingException;
	
	/**
	 *  Search Email By Filter.
	 * 
	 * @throws MessagingException
	 * @throws Exception 
	 */
	public  ArrayList<Mail> searchByFilter(final Filter filter) throws MessagingException, Exception;
}
