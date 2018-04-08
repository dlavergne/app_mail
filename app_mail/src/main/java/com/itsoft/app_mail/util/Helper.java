package com.itsoft.app_mail.util;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.BodyPart;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.MimeBodyPart;

import com.itsoft.app_mail.model.Filter;
import com.itsoft.app_mail.model.Mail;


public class Helper {

	/**
	 * @param filteredMessages
	 * @param path
	 * @throws Exception
	 */
	public static void createDirectoryWithSender(final ArrayList<Mail>  filteredMessages, String path, Filter filter) throws Exception {
		Path monRepertoire = Paths.get(path);
		Path file = Files.createDirectories(monRepertoire);
		
	}
	
	/**
	 * @return File
	 */
	private static File createAndGetDirectory() {
		File file = new File("attachments");
		if (!file.exists()) {
			file.mkdir();
		}
		return file;
	}

	/**
	 * @param multipart
	 * @return
	 * @throws Exception
	 */
	private static Map<String, Object> processMultipart(Multipart multipart) throws Exception {
		Map<String, Object> output = new HashMap<String, Object>();
		output.put("html", "");
		output.put("text", "");
		List<Map<String, Object>> attachments = new ArrayList<Map<String, Object>>();

		for (int i = 0; i < multipart.getCount(); i++) {
			Map<String, Object> result = processBodyPart(multipart.getBodyPart(i));
			if (result != null) {
				if (result.containsKey("type")) {
					if (result.get("type").toString().equalsIgnoreCase("html")) {
						output.put("html", result.get("content").toString());
					} else if (result.get("type").toString().equalsIgnoreCase("text")) {
						output.put("text", result.get("content").toString());
					} else if (result.get("type").toString().equalsIgnoreCase("attachment")) {
						attachments.add(result);
					}
				}
				if (result.containsKey("html")) {
					output.put("html", result.get("html").toString());
				}
				if (result.containsKey("text")) {
					output.put("text", result.get("text").toString());
				}
				if (result.containsKey("attachments")) {
					List<?> thisAttachments = (List<?>) result.get("attachments");
					for (int i2 = 0; i2 < thisAttachments.size(); i2++) {
						attachments.add((Map<String, Object>) thisAttachments.get(i2));
					}
				}
			}
		}
		output.put("attachments", attachments);

		return output;
	}

	/**
	 * @param bodyPart
	 * @return
	 * @throws Exception
	 */
	private static Map<String, Object> processBodyPart(BodyPart bodyPart) throws Exception {
		if (bodyPart.isMimeType("text/html") && bodyPart.getFileName() == null) {
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("type", "html");
			data.put("content", bodyPart.getContent().toString());
			return data;
		} else if (bodyPart.isMimeType("text/plain") && bodyPart.getFileName() == null) {
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("type", "text");
			data.put("content", bodyPart.getContent().toString());
			return data;
		} else if (Part.ATTACHMENT.equalsIgnoreCase(bodyPart.getDisposition()) && bodyPart.getFileName() != null) {
			try {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("type", "attachment");
				map.put("fileName", bodyPart.getFileName());
				String fileType = bodyPart.getContentType();
				map.put("fileType", fileType.contains(":") ? fileType.substring(0, fileType.indexOf(";")) : fileType);
				map.put("mimeBodyPart", bodyPart);
				return map;
			} catch (Exception ex) {
				println("Error_Content=" + bodyPart.getContentType());
				ex.printStackTrace();
			}
		} else if (bodyPart.getContentType().contains("multipart")) {
			Map<String, Object> o = processMultipart((Multipart) bodyPart.getContent());
			return o;
		}
		return null;
	}

	private static void println(Object o) {
		System.out.println(o);
	}
}
