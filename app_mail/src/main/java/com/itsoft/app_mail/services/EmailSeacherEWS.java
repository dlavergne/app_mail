package com.itsoft.app_mail.services;

import java.util.ArrayList;

import com.itsoft.app_mail.model.Filter;
import com.itsoft.app_mail.model.Mail;

import microsoft.exchange.webservices.data.autodiscover.IAutodiscoverRedirectionUrl;
import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.enumeration.property.WellKnownFolderName;
import microsoft.exchange.webservices.data.core.service.folder.Folder;
import microsoft.exchange.webservices.data.credential.ExchangeCredentials;
import microsoft.exchange.webservices.data.credential.WebCredentials;

/**
 * This Class that allows you to connect to a microsoft account and filter mail
 */
public class EmailSeacherEWS implements EmailSeacher {

	ExchangeService service = new ExchangeService();
	
	static class RedirectionUrlCallback implements IAutodiscoverRedirectionUrl {
		public boolean autodiscoverRedirectionUrlValidationCallback(
				String redirectionUrl) {
			return redirectionUrl.toLowerCase().startsWith("https://");
		}
	}
	
	/* (non-Javadoc)
	 * @see com.itsoft.app_mail.services.EmailSeacher#openConnection(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public void openConnection(String protocol, String host, String username, String password) {
		
	
		ExchangeCredentials credentials = new WebCredentials(username, password);
		service.setCredentials(credentials);
		try {
			service.autodiscoverUrl(username, new RedirectionUrlCallback());

			//Open connection on the main inBox
			Folder emailFolder = Folder.bind(service, WellKnownFolderName.Inbox);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	/* (non-Javadoc)
	 * @see com.itsoft.app_mail.services.EmailSeacher#closeConnection()
	 */
	public void closeConnection() {
		service.close();
		
	}

	/* (non-Javadoc)
	 * @see com.itsoft.app_mail.services.EmailSeacher#searchByFilter(com.itsoft.app_mail.model.Filter)
	 */
	public ArrayList<Mail> searchByFilter(Filter filter) {
		// TODO Auto-generated method stub
		return null;
	}
	
			
}