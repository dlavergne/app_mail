package com.itsoft.app_mail.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.SearchTerm;

import com.itsoft.app_mail.model.Filter;
import com.itsoft.app_mail.model.Mail;
import com.itsoft.app_mail.util.MailFilter;

/**
 * Class that allows you to connect to a pop or imap account and filter mail.
 */
public class EmailSeacherPOPIMAP implements EmailSeacher {
	
	private Store store;
	
	private Folder inbox;

	/* (non-Javadoc)
	 * @see com.itsoft.app_mail.services.EmailSeacher#openConnection(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public void openConnection(String PROTOCOL, String HOST, String USER, String PASSWORD) throws Exception {
		Properties props = new Properties();

		if (null != PROTOCOL && PROTOCOL.equalsIgnoreCase("imap")) {
			props = getSettingsIMAP();
		} else {
			props = getSettingsPOP3();
		}

		try {

			Session session = Session.getInstance(props, null);

			Store store;

			if ("pop3".equals(PROTOCOL)) {
				store = session.getStore("pop3s");
			} else {
				store = session.getStore("imap");
			}
			store.connect(HOST, USER, PASSWORD);

		} catch (Exception ex) {
			throw new Exception(ex);
		}
	}

	/* (non-Javadoc)
	 * @see com.itsoft.app_mail.services.EmailSeacher#closeConnection()
	 */
	public void closeConnection() throws MessagingException {
		// Close inbox
		if (inbox != null && inbox.isOpen())
			inbox.close(false);
		// close store
		if (store != null && store.isConnected())
			store.close();
	}

	/* (non-Javadoc)
	 * @see com.itsoft.app_mail.services.EmailSeacher#searchByFilter(com.itsoft.app_mail.model.Filter)
	 */
	public ArrayList<Mail> searchByFilter(final Filter filter) throws Exception {
		
		inbox = store.getFolder("INBOX");
		inbox.open(Folder.READ_ONLY);

		// Find mail by criteria
		SearchTerm searchCondition = MailFilter.getSearchCriteria(filter);
		
		// search by sender and word in subject
		Message[] foundMessages = inbox.search(searchCondition);

		ArrayList<Mail> resEmails = new ArrayList<Mail>();
		// Seach if message has attachement
		if (null != filter.getAttachments() && filter.getAttachments()) {
		
			if(MailFilter.hasAttachment(foundMessages, filter))
		
			for (int i = 0; i < foundMessages.length; i++) {
				Message message = foundMessages[i];
				Mail mail = new Mail();
				mail.setMessage(message);
				
				resEmails.add(mail);
			}	
		}
		return resEmails;
	}

	/** 
	 * @return setting Imap Properties
	 */
	private static Properties getSettingsIMAP() {
		Properties props = new Properties();
		props.setProperty("mail.imap.socketFacory.class", "javax.net.ssl.SSLSocketFactory");
		props.setProperty("mail.imap.socketFactory.fallback", "false");
		props.setProperty("mail.imap.ssl.enable", "true");
		props.setProperty("mail.imap.socketFactory.port", "993");
		props.setProperty("mail.imap.starttls.enable", "true");

		return props;
	}

	/**
	 * @return setting pop3 Properties
	 */
	private static Properties getSettingsPOP3() {
		Properties props = new Properties();
		props.setProperty("mail.pop3.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.setProperty("mail.pop3.socketFactory.fallback", "false");
		props.setProperty("mail.pop3.ssl.enable", "true");
		props.setProperty("mail.pop3.socketFactory.port", "995");
		props.setProperty("mail.pop3.starttls.enable", "true");
		return props;
	}

}
