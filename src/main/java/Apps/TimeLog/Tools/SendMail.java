package Apps.TimeLog.Tools;

import java.io.File;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import Apps.TimeLog.Mail.Mail;

public class SendMail {

	static Properties props = new Properties();
	private PropertyLoader prop = PropertyLoader.getInstance();

	public SendMail() {
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
	}

	public boolean send(Mail mail) {
		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(prop.getProperty("gmail_acc"), prop.getProperty("gmail_psw"));
			}
		});

		try {
			MimeMessage message = new MimeMessage(session);
			for (String s : mail.getToo().split(";"))
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(s));

			if (!mail.getCc().isEmpty()) {
				for (String s : mail.getCc().split(";"))
					message.addRecipient(Message.RecipientType.CC, new InternetAddress(s));
			}
			if (!mail.getBcc().isEmpty()) {
				for (String s : mail.getBcc().split(";"))
					message.addRecipient(Message.RecipientType.BCC, new InternetAddress(s));
			}

			Multipart multipart = new MimeMultipart();
			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setText(mail.getBody());
			multipart.addBodyPart(messageBodyPart);

			if (!mail.getAttachment().isEmpty()) {
				if (new File(mail.getAttachment()).exists()) {
					messageBodyPart = new MimeBodyPart();
					String filename = mail.getAttachment();
					DataSource source = new FileDataSource(filename);

					messageBodyPart.setDataHandler(new DataHandler(source));
					messageBodyPart.setFileName(filename);
					multipart.addBodyPart(messageBodyPart);
				}
			}
			message.setContent(multipart);
			message.setSubject(mail.getSubject());
			Transport.send(message);
			return true;
		} catch (MessagingException e) {
			return false;
		}
	}

}
