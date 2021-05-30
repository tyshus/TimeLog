package Apps.TimeLog.TimeLog;

import Apps.TimeLog.Contact.ContactListControler;
import Apps.TimeLog.Invoice.InvoiceListControler;
import Apps.TimeLog.Mail.MailListControler;
import Apps.TimeLog.Tools.Model;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Getter;

@SuppressWarnings("rawtypes")
public class LogEntryControler implements EventHandler {
	private LogEntry logEntry;
	private Model model = Model.getModel();
	private TimeLog timeLog;
	private Stage stage;
	private @Getter boolean mainFlag;

	public LogEntryControler(TimeLog timeLog) {
		this.stage = new Stage();
		this.mainFlag = false;
		this.stage.setTitle("Time Log");
		this.timeLog = timeLog;
		this.setStage();
		this.logEntry.setTimeLog(timeLog);
	}

	public LogEntryControler(Stage mainstage) {
		this.stage = mainstage;
		this.mainFlag = true;
		this.stage.setTitle("Time entry app");
		this.timeLog = new TimeLog();
		this.setStage();
		this.logEntry.setDefaults();
	}

	private void setStage() {
		this.logEntry = new LogEntry(this);
		this.logEntry.fillCompanies(model.loadCompanies());
		this.logEntry.fillContacts(model.loadContacts(logEntry.getCompany()));
		this.stage.setScene(new Scene(logEntry, 1150, 550));
		this.stage.show();
		this.stage.setWidth(1150);
		this.stage.setHeight(550);
	}

	@Override
	public void handle(Event event) {
		final Object source = event.getSource();
		if (source.equals(this.logEntry.getButton())) {
			save();
		}
		if (source.equals(this.logEntry.getCompanyCBox())) {
			this.logEntry.fillContacts(model.loadContacts(logEntry.getCompany()));
		}
		if (source.equals(this.logEntry.getMenuItemContacts())) {
			new ContactListControler();
		}
		if (source.equals(this.logEntry.getMenuItemInvoices())) {
			new InvoiceListControler();
		}
		if (source.equals(this.logEntry.getMenuItemMails())) {
			new MailListControler();
		}
		if (source.equals(this.logEntry.getMenuItemReport())) {
			new ReportControler();
		}
		if (source.equals(this.logEntry.getMenuItemQuit())) {
			Platform.exit();
			System.exit(0);
		}

	}

	private void save() {
		logEntry.getTimeLog(timeLog);
		if (timeLog.getId() <= 0) {
			model.persist(timeLog);
			logEntry.setDefaults();
			model.msg("Time: " + String.valueOf(timeLog.getTime()));
		} else {
			model.merge(timeLog);
			stage.close();
		}
	}

}
