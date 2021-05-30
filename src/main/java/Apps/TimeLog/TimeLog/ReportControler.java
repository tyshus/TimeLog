package Apps.TimeLog.TimeLog;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import Apps.TimeLog.Company.Company;
import Apps.TimeLog.Invoice.Invoice;
import Apps.TimeLog.Invoice.InvoiceEntryControler;
import Apps.TimeLog.Mail.Mail;
import Apps.TimeLog.Mail.MailEntryControler;
import Apps.TimeLog.Tools.Model;
import Apps.TimeLog.Tools.ReportExcelExport;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Setter;

@SuppressWarnings("rawtypes")
public class ReportControler implements EventHandler {
	private final Report report;
	private @Setter List<TimeLog> list;
	private Model model = Model.getModel();
	private Stage stage;

	public ReportControler() {
		stage = new Stage();
		report = new Report(this);
		report.fillCompanies(model.loadCompanies());
		stage.setTitle("Report");
		stage.setScene(new Scene(report, 900, 550));
		stage.show();
		stage.setX(100);
		stage.setY(100);
	}

	@Override
	public void handle(Event event) {
		final Object source = event.getSource();
		if (source.equals(report.getFilterBtn())) {
			loadList();
			fillTable();
		}
		if (source.equals(report.getInvoiceBtn())) {
			creatInvoice();
		}
		if (source.equals(report.getMailBtn())) {
			sendEmail();
		}
		if (source.equals(report.getEditBtn())) {
			editEntry();
		}
	}

	void editEntry() {
		TimeLog timeLog = (TimeLog) report.getTableView().getSelectionModel().getSelectedItem();
		if (timeLog != null) {
			new LogEntryControler(timeLog);
		} else {
			model.msgW("No log selected!");
		}
	}

	private void loadList() {
		list = model.loadReportData(report.getCompany(), report.getDateFrom(), report.getDateTo());
	}

	public void fillTable() {
		this.report.fillTableView(list);
	}

	void sendEmail() {
		new ReportExcelExport(report);
		if (new File(report.getPrintout()).exists()) {
			String period = report.getDateFrom().format(DateTimeFormatter.ofPattern("yyyy.MM.dd")) + "-"
					+ report.getDateTo().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
			StringBuilder mailbody = new StringBuilder();
			mailbody.append(model.prop.getProperty("mail_greatings0"));
			mailbody.append(System.getProperty("line.separator"));
			mailbody.append(System.getProperty("line.separator"));
			mailbody.append(model.prop.getProperty("report_mail_body"));
			mailbody.append(period);
			mailbody.append(System.getProperty("line.separator"));
			mailbody.append(System.getProperty("line.separator"));
			mailbody.append(model.prop.getProperty("mail_greatings1"));
			mailbody.append(System.getProperty("line.separator"));
			mailbody.append(model.prop.getProperty("mail_greatings2"));

			Mail mail = new Mail();
			mail.setToo(model.getEmails(report.getCompany(), "To"));
			mail.setCc(model.getEmails(report.getCompany(), "Cc"));
			mail.setBcc(model.getEmails(report.getCompany(), "Bcc"));
			mail.setSourceid("");
			mail.setSourcetype("report");
			mail.setSubject("Time report for period: " + period);
			mail.setBody(mailbody.toString());
			mail.setAttachment(report.getPrintout());
			mail.setDate(LocalDate.now());

			new MailEntryControler(mail);
			report.setPrintout("");
		}
	}

	void creatInvoice() {
		Invoice invoice = new Invoice();
		Company company = model.getCompany(report.getCompany());
		invoice.setDate(LocalDate.now());
		invoice.setCompany(report.getCompany());
		invoice.setAmount(report.getTotalHours() * company.getRate());
		invoice.setOperation(model.prop.getProperty("invoice_operation"));
		invoice.setPeriod(report.getDateFrom().format(DateTimeFormatter.ofPattern("yyyy.MM.dd")) + "-"
				+ report.getDateTo().format(DateTimeFormatter.ofPattern("yyyy.MM.dd")));
		new InvoiceEntryControler(invoice);
	}
}
