package Apps.TimeLog.Windows.List;

import java.io.File;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import Apps.TimeLog.Models.Company;
import Apps.TimeLog.Models.Invoice;
import Apps.TimeLog.Models.Mail;
import Apps.TimeLog.Models.TimeLog;
import Apps.TimeLog.Tools.ReportExcelExport;
import Apps.TimeLog.Windows.Entry.InvoiceEntry;
import Apps.TimeLog.Windows.Entry.LogEntry;
import Apps.TimeLog.Windows.Entry.MailEntry;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class Report extends WindowList{
	private TableColumn<TimeLog, Long> id;
	private TableColumn<TimeLog, String> contactName;
	private TableColumn<TimeLog, LocalDate> logDate;
	private TableColumn<TimeLog, LocalDate> taskDate;
	private TableColumn<TimeLog, String> taskType;
	private TableColumn<TimeLog, Double> taskName;
	private TableColumn<TimeLog, String> taskBody;
	private TableColumn<TimeLog, Double> myComment;
	private TableColumn<TimeLog, Double> time;
	private TableView<TimeLog> tableView;
	
	private Button filterBtn = new Button ("Filter");
	private Button mailBtn = new Button ("Email");
	private Button invoiceBtn = new Button ("Invoice");
	private double totalHours;
	private String printout;
	private Label totalLabel = new Label(); 
	private ComboBox<String> companyCBox = new ComboBox<String>();
    private DatePicker dateFrom = new DatePicker();
    private DatePicker dateTo = new DatePicker();
	@SuppressWarnings("static-access")
	public Report() {
		stage.setTitle("Report"); 
		tableView = new TableView<TimeLog>();
		
		companyCBox.getItems().addAll(model.CompanyList());
		companyCBox.getSelectionModel().selectFirst();
        dateFrom.setValue(LocalDate.now());
        dateTo.setValue(LocalDate.now());
        
        filterBtn.setOnAction(value ->  {LoadData();});
        mailBtn.setOnAction(value -> {SendeMail();});
        invoiceBtn.setOnAction(vaue -> {CreatInvoice();});
		AddColumns();
		LoadData();
		newBtn.setVisible(false);
        grid.add(companyCBox, 2, 0);
        grid.add(dateFrom, 3, 0);
        grid.add(dateTo, 4, 0);
        grid.add(filterBtn, 5, 0);
        grid.add(mailBtn, 6, 0);
        grid.add(invoiceBtn, 7, 0);
        grid.add(totalLabel,8,0);
        root.getChildren().add(tableView);
		tableView.setId("table-view");
		stage.setWidth(900);
		stage.setHeight(550);
		stage.setX(100);
		stage.setY(100);
	}

	@Override
	void newEntry() {
		
	}
	
	void SendeMail() {
		new ReportExcelExport(this);
		if (new File(this.getPrintout()).exists()) {
			String period = this.dateFrom.getValue().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"))
					+"-"+this.dateTo.getValue().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
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
			mail.setTo(model.getEmails(this.companyCBox.getValue(), "To"));
			mail.setCc(model.getEmails(this.companyCBox.getValue(), "Cc"));
			mail.setBcc(model.getEmails(this.companyCBox.getValue(), "Bcc"));
			mail.setSourceid("");
			mail.setSourcetype("report");
			mail.setSubject("Time report for period: "+period);
			mail.setBody(mailbody.toString());
			mail.setAttachment(this.getPrintout());
			mail.setDate(LocalDate.now());
			model.persist(mail);
			
			MailEntry mailEntry = new MailEntry();
			mailEntry.setMail(mail);
		this.setPrintout("");
		}
	}
	
	void CreatInvoice() {
		InvoiceEntry companyEntry = new InvoiceEntry();
		Invoice invoice = new Invoice();
		Company company = model.GetCompany(companyCBox.getValue());
		invoice.setDate(LocalDate.now());
		invoice.setCompany(companyCBox.getValue());
		invoice.setAmount(totalHours*company.getRate());
		invoice.setOperation(model.prop.getProperty("invoice_operation"));
		invoice.setPeriod(dateFrom.getValue().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"))+"-"+
				dateTo.getValue().format(DateTimeFormatter.ofPattern("yyyy.MM.dd")));
		companyEntry.SetInvoice(invoice);
	}

	@Override
	void editEntry() {
		TimeLog timeLog = (TimeLog)tableView.getSelectionModel().getSelectedItem();
		if (timeLog != null) {
			Stage tmp = new Stage();
			tmp.initOwner(stage);
			LogEntry logEntry = new LogEntry(tmp);
			logEntry.SetTimeLog(timeLog);
		}
		else {
			model.msgW("No log selected!");
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	void AddColumns() {
		id= new TableColumn<>("Id");
		id.setCellValueFactory(new PropertyValueFactory<>("id"));
		contactName = new TableColumn<>("Contact Name");
		contactName.setCellValueFactory(new PropertyValueFactory<>("contactName"));
		logDate = new TableColumn<>("Date");
		logDate.setCellValueFactory(new PropertyValueFactory<>("logDate"));
		taskDate = new TableColumn<>("Task rec. date");
		taskDate.setCellValueFactory(new PropertyValueFactory<>("taskDate"));
		taskType = new TableColumn<>("Task type");
		taskType.setCellValueFactory(new PropertyValueFactory<>("taskType"));
		taskName = new TableColumn<>("Task subject");
		taskName.setCellValueFactory(new PropertyValueFactory<>("taskName"));
		taskBody = new TableColumn<>("Task description");
		taskBody.setCellValueFactory(new PropertyValueFactory<>("taskBody"));
		myComment = new TableColumn<>("My comments");
		myComment.setCellValueFactory(new PropertyValueFactory<>("myComment"));
		time = new TableColumn<>("Time spent (hours)");
		time.setCellValueFactory(new PropertyValueFactory<>("time"));
		tableView.getColumns().addAll(id,logDate,contactName,taskType,taskDate,taskName,time,taskBody,myComment);
		tableView.setPadding(new Insets(5, 5, 5, 5));
		taskName.setMaxWidth(400);
		taskName.setMinWidth(400);
		taskBody.setVisible(false);
		myComment.setVisible(false);
	}

	@Override
	void LoadData() {
		tableView.getItems().clear();
		totalHours = 0;
		for (TimeLog temp : model.ReportData(companyCBox.getValue(),dateFrom.getValue(),dateTo.getValue())) {
			tableView.getItems().add(temp);
			totalHours += temp.getTime(); 
		}
		if (totalHours > 0) {
    		DecimalFormat df = new DecimalFormat("#.##");      
			totalHours = Double.valueOf(df.format(totalHours));
			totalLabel .setText("Total hours: "+String.valueOf(totalHours));
		}
	}

	public TableView<TimeLog> getTableView() {
		return tableView;
	}

	public String getCompany() {
		return companyCBox.getValue();
	}


	public LocalDate getDateFrom() {
		return dateFrom.getValue();
	}


	public LocalDate getDateTo() {
		return dateTo.getValue();
	}

	public void setPrintout(String printout) {
		this.printout = printout;
	}
	
	public String getPrintout() {
		return this.printout;
	}
	
}
