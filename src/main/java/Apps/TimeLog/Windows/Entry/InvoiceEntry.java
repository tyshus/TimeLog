package Apps.TimeLog.Windows.Entry;

import java.time.LocalDate;

import Apps.TimeLog.Models.Invoice;
import Apps.TimeLog.Models.Mail;
import Apps.TimeLog.Tools.PrintInvoice;
import Apps.TimeLog.Windows.Fields.DoubleTextField;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class InvoiceEntry extends WindowEntry {
	private TextField serNo = new TextField("");
	private DatePicker date = new DatePicker();
	private ComboBox<String> company = new ComboBox<String>();
	private TextField operation = new TextField("");
	private TextField period = new TextField("");
	private DoubleTextField amount = new DoubleTextField();
	private Button printBtn = new Button("Print");
	private Button mailBtn = new Button("Email");
	private CheckBox sent = new CheckBox("Sent");
	private CheckBox printed = new CheckBox("Printed");
	private Invoice invoice = new Invoice();

	public InvoiceEntry() {
		stage.setTitle("Invoice");

		printBtn.setOnAction(value -> {
			try {
				new PrintInvoice(invoice);
				setInvoice(invoice);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		mailBtn.setOnAction(value -> {
			createMail();
		});

		serNo.setPrefWidth(100);
		serNo.setMaxWidth(100);
		HBox hline = new HBox();
		hline.setSpacing(15.0);
		hline.getChildren().addAll(new Label("Ser. no.:"), serNo, printed, sent);
		HBox bline = new HBox();
		bline.setSpacing(20.0);
		bline.getChildren().addAll(saveBtn, printBtn, mailBtn);

		company.getItems().addAll(model.loadCompanies());
		company.getSelectionModel().selectFirst();

		grid.add(hline, 1, 0, 1, 1);
		grid.add(new Label("Date: "), 0, 1);
		grid.add(date, 1, 1);
		grid.add(new Label("Company: "), 0, 2);
		grid.add(company, 1, 2);
		grid.add(new Label("Operation: "), 0, 3);
		grid.add(operation, 1, 3);
		grid.add(new Label("Period: "), 0, 4);
		grid.add(period, 1, 4);
		grid.add(new Label("Amount: "), 0, 5);
		grid.add(amount, 1, 5);
		grid.add(bline, 1, 6);
		serNo.setEditable(false);
		printed.setDisable(true);
		sent.setDisable(true);
		printBtn.setDisable(true);
		mailBtn.setDisable(true);
	}

	void createMail() {
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
		mail.setTo(model.getEmails(invoice.getCompany(), "To"));
		mail.setCc(model.getEmails(invoice.getCompany(), "Cc"));
		mail.setBcc(model.getEmails(invoice.getCompany(), "Bcc"));
		mail.setSourceid(invoice.getSerno());
		mail.setSourcetype("inv");
		mail.setSubject("Invoice for period: " + invoice.getPeriod());
		mail.setBody(mailbody.toString());
		mail.setAttachment(invoice.getPrintout());
		mail.setDate(LocalDate.now());
		model.persist(mail);

		MailEntry mailEntry = new MailEntry();
		mailEntry.setMail(mail);
		stage.close();
	}

	@Override
	void save() {
		invoice.setCompany(company.getValue());
		invoice.setDate(date.getValue());
		invoice.setOperation(operation.getText());
		invoice.setPeriod(period.getText());
		invoice.setAmount(amount.getDouble());
		if (serNo.getText() == null) {
			model.persist(invoice);
		}
		else {
			model.merge(invoice);
		}
		model.refresh();
		setInvoice(invoice);
	}

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
		serNo.setText(invoice.getSerno());
		company.setValue(invoice.getCompany());
		date.setValue(invoice.getDate());
		operation.setText(invoice.getOperation());
		period.setText(invoice.getPeriod());
		amount.SetDouble(invoice.getAmount());
		printed.setSelected(invoice.isPrinted());
		sent.setSelected(invoice.isSent());
		if (invoice.isPrinted()) {
			if (!invoice.isSent()) {
				mailBtn.setDisable(false);
			}
		} else {
			if (!invoice.getSerno().isEmpty()) {
				printBtn.setDisable(false);
			}
		}

	}

}
