package Apps.TimeLog.Mail;

import Apps.TimeLog.Invoice.Invoice;
import Apps.TimeLog.Tools.Model;
import Apps.TimeLog.Tools.SendMail;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;

@SuppressWarnings("rawtypes")
public class MailEntryControler implements EventHandler {
	private final MailEntry mailEntry;
	private Model model = Model.getModel();
	private Mail mail;
	private Stage stage;

	public MailEntryControler(Mail mail) {
		this.stage = new Stage();
		this.mail = mail;
		this.mailEntry = new MailEntry(this);
		this.mailEntry.setMail(mail);
		this.stage.setTitle("Mail");
		this.stage.setScene(new Scene(mailEntry, 450, 250));
		this.stage.show();
		this.stage.setWidth(800);
		this.stage.setHeight(500);
	}

	@Override
	public void handle(Event event) {
		final Object source = event.getSource();
		if (source.equals(this.mailEntry.getSaveBtn())) {
			save();
		}
		if (source.equals(this.mailEntry.getSendBtn())) {
			sendEmail();
		}
	}

	private void save() {
		mailEntry.getMail(mail);
		if (mail.getId() <= 0) {
			model.persist(mail);
			mailEntry.setMail(mail);
		} else {
			model.merge(mail);
		}
	}

	private void sendEmail() {
		if (mail.getId() <= 0) {
			save();
		}
		SendMail sendMail = new SendMail();
		if (sendMail.send(mail)) {
			mail.setSent(true);
			model.merge(mail);
			if (mail.getSourcetype() == "inv") {
				Invoice invoice = model.getInvoice(mail.getSourceid());
				invoice.setSent(true);
				model.merge(invoice);
			}
			mailEntry.setMail(mail);
			model.msg("eMail sent!");
		} else {
			model.msgW("Failed to send email!");
		}
	}

}
