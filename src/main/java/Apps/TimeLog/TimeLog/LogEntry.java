package Apps.TimeLog.TimeLog;

import java.text.DecimalFormat;
import java.time.LocalDate;

import Apps.TimeLog.Company.CompanyListControler;
import Apps.TimeLog.Contact.ContactListControler;
import Apps.TimeLog.Invoice.InvoiceListControler;
import Apps.TimeLog.Mail.MailListControler;
import Apps.TimeLog.Tools.Model;
import Apps.TimeLog.Windows.Fields.NumberTextField;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.Getter;

public class LogEntry extends VBox {
	private TextField taskName = new TextField("");
	private NumberTextField hours = new NumberTextField();
	private NumberTextField mins = new NumberTextField();
	private TextArea taskBody = new TextArea("");
	private TextArea myComment = new TextArea("");
	private ComboBox<String> contactCBox = new ComboBox<String>();
	private @Getter ComboBox<String> companyCBox = new ComboBox<String>();
	private ComboBox<String> taskTypeCBox = new ComboBox<String>();
	private DatePicker logDate = new DatePicker();
	private DatePicker taskDate = new DatePicker();
	private @Getter Button button = new Button("Save");
	private @Getter MenuItem menuItemQuit = new MenuItem("Quit");
	private @Getter MenuItem menuItemContacts = new MenuItem("Contacts");
	private @Getter MenuItem menuItemCompanies = new MenuItem("Companies");
	private @Getter MenuItem menuItemInvoices = new MenuItem("Invoices");
	private @Getter MenuItem menuItemMails = new MenuItem("Mails");
	private @Getter MenuItem menuItemReport = new MenuItem("Report");
	private GridPane grid = new GridPane();

	@SuppressWarnings("unchecked")
	public LogEntry(LogEntryControler logEntryControler) {
		if (logEntryControler.isMainFlag()) {
			setMenu(logEntryControler);
		}
		companyCBox.setOnAction(logEntryControler);
		taskTypeCBox.getItems().addAll("Email", "Jira", "Skype", "Phone");
		setGrid();
	}

	@SuppressWarnings("unchecked")
	private void setMenu(LogEntryControler logEntryControler) {
		MenuBar menuBar = new MenuBar();
		Menu menuFile = new Menu("File");
		Menu menuRgisters = new Menu("Rgisters");
		Menu menuReport = new Menu("Reports");
		menuFile.getItems().addAll(menuItemQuit);
		menuReport.getItems().addAll(menuItemReport);
		menuRgisters.getItems().addAll(menuItemInvoices, menuItemMails, menuItemContacts, menuItemCompanies);
		menuBar.getMenus().addAll(menuFile, menuRgisters, menuReport);
		menuItemCompanies.setOnAction(logEntryControler);
		menuItemInvoices.setOnAction(logEntryControler);
		menuItemMails.setOnAction(logEntryControler);
		menuItemReport.setOnAction(logEntryControler);
		menuItemQuit.setOnAction(logEntryControler);
		menuItemContacts.setOnAction(logEntryControler);
		this.getChildren().add(menuBar);
	}

	private void setGrid() {
		grid.setVgap(4);
		grid.setHgap(10);
		grid.setPadding(new Insets(10, 10, 10, 10));
		grid.add(new Label("Company: "), 0, 0);
		grid.add(companyCBox, 1, 0);
		grid.add(new Label("Contact: "), 2, 0);
		grid.add(contactCBox, 3, 0);
		grid.add(new Label("Log date: "), 4, 0);
		grid.add(logDate, 5, 0);
		grid.add(new Label("Task type: "), 6, 0);
		grid.add(taskTypeCBox, 7, 0);
		grid.add(new Label("Task code/Subject: "), 0, 1, 1, 10);
		grid.add(taskName, 1, 1, 5, 10);
		grid.add(new Label("Task date: "), 6, 1, 1, 10);
		grid.add(taskDate, 7, 1, 1, 10);
		grid.add(taskBody, 1, 11, 9, 1);
		grid.add(new Separator(Orientation.HORIZONTAL), 0, 12, 9, 1);
		grid.add(myComment, 1, 13, 9, 1);
		grid.add(new Label("Time spent: "), 0, 14);
		grid.add(new Label("Hours : "), 1, 14);
		grid.add(hours, 2, 14);
		grid.add(new Label("Minutes: "), 3, 14);
		grid.add(mins, 4, 14);
		grid.add(button, 5, 14);
		this.getChildren().add(grid);
	}

	public void setDefaults() {
		logDate.setValue(LocalDate.now());
		taskDate.setValue(LocalDate.now());
		taskName.setText(null);
		taskBody.setText(null);
		myComment.setText(null);
		hours.setText(null);
		mins.setText(null);
		taskTypeCBox.getSelectionModel().selectFirst();
	}

	public String getCompany() {
		return this.companyCBox.getValue();
	}

	public void fillCompanies(ObservableList<String> list) {
		companyCBox.getItems().addAll(list);
	}

	public void fillContacts(ObservableList<String> list) {
		contactCBox.getItems().addAll(list);
	}

	public void getTimeLog(TimeLog timeLog) {
		timeLog.setCompany(companyCBox.getValue());
		timeLog.setContactName(contactCBox.getValue());
		timeLog.setTaskType(taskTypeCBox.getValue());
		timeLog.setTaskName(taskName.getText());
		timeLog.setTaskBody(taskBody.getText());
		timeLog.setMyComment(myComment.getText());
		timeLog.setTaskDate(taskDate.getValue());
		timeLog.setLogDate(logDate.getValue());
		timeLog.setHours(hours.getint());
		timeLog.setMins(mins.getint());
		Double time = Double.valueOf(mins.getint()) / 60 + hours.getint();
		DecimalFormat df = new DecimalFormat("#.##");
		time = Double.valueOf(df.format(time));
		timeLog.setTime(time);
	}

	public void setTimeLog(TimeLog timeLog) {
		companyCBox.getSelectionModel().select(timeLog.getCompany());
		contactCBox.getSelectionModel().select(timeLog.getContactName());
		taskTypeCBox.getSelectionModel().select(timeLog.getTaskType());
		taskName.setText(timeLog.getTaskName());
		taskBody.setText(timeLog.getTaskBody());
		myComment.setText(timeLog.getMyComment());
		taskDate.setValue(timeLog.getTaskDate());
		logDate.setValue(timeLog.getLogDate());
		hours.SetInt(timeLog.getHours());
		mins.SetInt(timeLog.getMins());
	}
}
