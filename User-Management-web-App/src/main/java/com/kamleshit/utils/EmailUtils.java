package com.kamleshit.utils;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailUtils 
{
	@Autowired
	private JavaMailSender mailSender;
	
	public boolean sendAcctRegEmail(String subject,String body, String to)
	{
		boolean isSent = false;
		
		try 
		{
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message);
			helper.setSubject(subject);
			helper.setTo(to);
			
			boolean isHtmlTxt = true;
			helper.setText(body, isHtmlTxt);
			
			mailSender.send(message);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return isSent;
	}
	

}
