package com.itsoft.app_mail.util;

import java.io.IOException;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.InternetAddress;
import javax.mail.search.AndTerm;
import javax.mail.search.FromTerm;
import javax.mail.search.SearchTerm;
import javax.mail.search.SubjectTerm;

import com.itsoft.app_mail.model.Filter;

public class MailFilter {

	/**
	 * @param filter
	 * @return
	 * @throws Exception
	 */
	public static SearchTerm getSearchCriteria(final Filter filter) throws Exception {
		SearchTerm searchCondition = null;
		FromTerm fromTerm = null;

		if (null != filter && org.apache.commons.lang3.StringUtils.isNotBlank(filter.getWordInSubject())) {
			fromTerm = new FromTerm(new InternetAddress(filter.getSender()));
			searchCondition = new AndTerm(fromTerm, new SubjectTerm(filter.getWordInSubject()));
		} else {
			searchCondition = new FromTerm(new InternetAddress(filter.getSender()));
		}

		return searchCondition;
	}

	/**
	 * @param messages
	 * @param filter
	 * @return
	 * @throws IOException
	 * @throws MessagingException
	 */
	public static boolean hasAttachment(Message [] messages, final Filter filter)
			throws IOException, MessagingException {
		boolean hasAttachment = false;

		for (int i = 0; i < messages.length; i++) {
			Message message = messages[i];
			Multipart multipart = (Multipart) message.getContent();
			for (int k = 0; k < multipart.getCount(); k++) {
				BodyPart bodyPart = multipart.getBodyPart(k);
				if (Part.ATTACHMENT.equalsIgnoreCase(bodyPart.getDisposition())) {
					hasAttachment = true;
				}
			}
		}
		return hasAttachment;
	}
}
