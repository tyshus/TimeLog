package Apps.TimeLog.Invoice;

import java.time.LocalDate;

import Apps.TimeLog.Mail.Mail;
import Apps.TimeLog.Mail.MailEntry;
import Apps.TimeLog.Mail.MailEntryControler;
import Apps.TimeLog.Tools.Model;
import Apps.TimeLog.Tools.PrintInvoice;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;

@SuppressWarnings("rawtypes")
public class InvoiceEntryControler implements EventHandler {
	private final InvoiceEntry invoiceEntry;
	private Model model = Model.getModel();
	private Invoice invoice;
	private Stage stage;

	public InvoiceEntryControler(Invoice invoice) {
		this.stage = new Stage();
		this.invoice = invoice;
		this.invoiceEntry = new InvoiceEntry(this);
		this.invoiceEntry.fillCompanies(model.loadCompanies());
		this.invoiceEntry.setInvoice(invoice);
		this.stage.setTitle("Invoice");
		this.stage.setScene(new Scene(invoiceEntry, 450, 250));
		this.stage.show();
	}

	@Override
	public void handle(Event event) {
		final Object source = event.getSource();
		if (source.equals(this.invoiceEntry.getSaveBtn())) {
			save();
		}
		if (source.equals(this.invoiceEntry.getPrintBtn())) {
			print();
		}
		if (source.equals(this.invoiceEntry.getMailBtn())) {
			mail();
		}
	}

	private void save() {
		invoiceEntry.getInvoice(invoice);
		if (invoice.getSerno() == null) {
			model.persist(invoice);
			invoiceEntry.setInvoice(invoice);
		} else {
			model.merge(invoice);
		}
	}

	private void print() {
		try {
			new PrintInvoice(invoice);
			invoiceEntry.setInvoice(invoice);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void mail() {
		StringBuilder mailbody = new StringBuilder();
		mailbody.append(model.prop.getProperty("mail_greatings0"));
		mailbody.append(System.getProperty("line.separator"));
		mailbody.append(System.getProperty("line.separator"));
		mailbody.append(model.prop.getProperty("invoice_mail_body"));
		mailbody.append(invoice.getPeriod());
		mailbody.append(System.getProperty("line.separator"));
		mailbody.append(System.getProperty("line.separator"));
		mailbody.append(model.prop.getProperty("mail_greatings1"));
		mailbody.append(System.getProperty("line.separator"));
		mailbody.append(model.prop.getProperty("mail_greatings2"));

		Mail mail = new Mail();
		mail.setToo(model.getEmails(invoice.getCompany(), "To"));
		mail.setCc(model.getEmails(invoice.getCompany(), "Cc"));
		mail.setBcc(model.getEmails(invoice.getCompany(), "Bcc"));
		mail.setSourceid(invoice.getSerno());
		mail.setSourcetype("inv");
		mail.setSubject("Invoice for period: " + invoice.getPeriod());
		mail.setBody(mailbody.toString());
		mail.setAttachment(invoice.getPrintout());
		mail.setDate(LocalDate.now());

		new MailEntryControler(mail);
		stage.close();
	}

}
