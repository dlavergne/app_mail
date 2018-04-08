package com.itsoft.app_mail;

import java.util.ArrayList;
import java.util.Properties;

import com.itsoft.app_mail.model.Filter;
import com.itsoft.app_mail.model.Mail;
import com.itsoft.app_mail.services.EmailSeacher;
import com.itsoft.app_mail.services.EmailSeacherEWS;
import com.itsoft.app_mail.services.EmailSeacherPOPIMAP;
import com.itsoft.app_mail.util.Helper;
import com.itsoft.app_mail.util.PropertyLoader;

/**
 * Class Main
 *
 */
public class App {
	public static void main(String[] args) {
		try {
			
			/** 
			 * Step 1 : load properties 
			 * */	
			
			String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
			String appConfigPath = rootPath + "application.properties";
			Properties prop = PropertyLoader.load(appConfigPath);
			final String host = prop.getProperty("connection.servermail.host");
			final String user = prop.getProperty("connection.servermail.user");
			final String password = prop.getProperty("connection.servermail.password");
			final String protocol = prop.getProperty("connection.servermail.protocol");
					
			// Filter settings
			Filter filter = new Filter();
			filter.setSender(prop.getProperty("mail.filter.from"));
			filter.setWordInSubject(prop.getProperty("mail.filter.subject"));
			filter.setAttachments(prop.getProperty("mail.filter.attachment"));
			
			// Directory to save
			final String path= prop.getProperty("mail.storage.directory");
					
			/** 
			 *  Step 2: Open connection
			 *  */	
			
			EmailSeacher emailSeach = null;
			if("ews".equals(protocol)) {
				 emailSeach =(EmailSeacher) new EmailSeacherEWS();
				 ((EmailSeacherEWS) emailSeach).openConnection(protocol, host, user, password);	
			}else {
				emailSeach = (EmailSeacher) new EmailSeacherPOPIMAP();
				((EmailSeacherPOPIMAP) emailSeach).openConnection(protocol, host, user, password);		
			}
				
			
			/**
			 *  Step 3 : Apply filter
			 *  */
			ArrayList<Mail>  filteredMessages = emailSeach.searchByFilter(filter);
				
			/** 
			 * Step 4 create directories and files
			 * */
			Helper.createDirectoryWithSender(filteredMessages, path, filter);
			
			/** 
			 * Step 5 save the mail in various formats 
			 *  */
			
			
			/** Step 6 
			 * Close connection 
			 * */
			emailSeach.closeConnection();
			
			/**
			 * Step 7 Generate json summary 
			 * */
			
			
			System.exit(1);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
}
