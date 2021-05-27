package Apps.TimeLog.Windows.Entry;

import java.text.DecimalFormat;
import java.time.LocalDate;

import Apps.TimeLog.Models.Model;
import Apps.TimeLog.Models.TimeLog;
import Apps.TimeLog.Windows.NumberTextField;
import Apps.TimeLog.Windows.List.CompanyList;
import Apps.TimeLog.Windows.List.ContactList;
import Apps.TimeLog.Windows.List.InvoiceList;
import Apps.TimeLog.Windows.List.MailList;
import Apps.TimeLog.Windows.List.Report;
import javafx.application.Platform;
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

public class LogEntry {
	private TextField taskName = new TextField("");
	private NumberTextField hours = new NumberTextField();
	private NumberTextField mins = new NumberTextField();
	private TextArea taskBody = new TextArea ("");
	private TextArea myComment = new TextArea ("");
    private ComboBox<String> contactCBox = new ComboBox<String>();
    private ComboBox<String> companyCBox = new ComboBox<String>();
    private ComboBox<String> taskTypeCBox = new ComboBox<String>();
    private DatePicker logDate = new DatePicker();
    private DatePicker taskDate = new DatePicker();
	private Button button = new Button ("Save");
	private GridPane grid = new GridPane();
	private TimeLog timeLog = new TimeLog();
    private Model model = Model.getModel();
    private Stage stage;
	public LogEntry(Stage mainstage) {
		stage = mainstage;
		//Menu bar items
        Scene scene = new Scene(new VBox(), 1150, 550);
        VBox root = (VBox)scene.getRoot();
        
		if (mainstage.getOwner() == null) {
			MenuBar menuBar = new MenuBar();
			Menu menuFile = new Menu("File");
			Menu menuRgisters = new Menu("Rgisters");
			Menu menuReport = new Menu("Reports");
			MenuItem menuItemQuit = new MenuItem("Quit");
			MenuItem menuItemContacts = new MenuItem("Contacts");
			MenuItem menuItemCompanies = new MenuItem("Companies");
			MenuItem menuItemInvoices = new MenuItem("Invoices");
			MenuItem menuItemMails = new MenuItem("Mails");
			MenuItem menuItemReport = new MenuItem("Report");
			menuFile.getItems().addAll(menuItemQuit);
			menuReport.getItems().addAll(menuItemReport);
			menuRgisters.getItems().addAll(menuItemInvoices,menuItemMails,menuItemContacts,menuItemCompanies);
			menuBar.getMenus().addAll(menuFile,menuRgisters,menuReport);
			menuItemContacts.setOnAction(value -> {new ContactList();});
			menuItemCompanies.setOnAction(value -> {new CompanyList();});   
			menuItemInvoices.setOnAction(value -> {new InvoiceList();});
			menuItemMails.setOnAction(value -> {new MailList();});
			menuItemReport.setOnAction(value ->{new Report();});
			
			menuItemQuit.setOnAction(value ->{
				Platform.exit();
				System.exit(0);
			});
			root.getChildren().add(menuBar);  
		}
        
        //-------- window fields and buttons
        taskTypeCBox.getItems().addAll("Email","Jira","Skype","Phone"); 
        SetDefaults();
        SetGrid();
        button.setOnAction(value ->  {Save();});
        companyCBox.setOnAction(value -> {LoadContacts();});
                
        stage.setTitle("Time entry app");
        root.getChildren().add(grid);       
        stage.setScene(scene);
        stage.show();
	}
	
	private void Save() { 	
		if (timeLog.getId() <= 0) {
			timeLog = new TimeLog();
		}
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
		Double time = Double.valueOf(mins.getint())/60 + hours.getint();
		DecimalFormat df = new DecimalFormat("#.##");      
		time = Double.valueOf(df.format(time));
		timeLog.setTime(time);
		if (timeLog.getId() > 0) {
			model.merge(timeLog);
			model.refresh();
	    	stage.close();
		}else {
			model.persist(timeLog);
    	SetDefaults();
    	model.msg("Time: "+String.valueOf(time));
		}
	}
	
	private void SetGrid() {
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
        grid.add(new Label("Task code/Subject: "), 0, 1,1,10);
        grid.add(taskName, 1, 1, 5, 10);  
        grid.add(new Label("Task date: "), 6, 1,1,10);
        grid.add(taskDate, 7, 1,1,10);
        grid.add(taskBody, 1, 11, 9, 1);
        grid.add(new Separator(Orientation.HORIZONTAL), 0, 12,9,1);
        grid.add(myComment, 1, 13, 9, 1);
        grid.add(new Label("Time spent: "), 0, 14);
        grid.add(new Label("Hours : "), 1, 14);
        grid.add(hours, 2, 14);
        grid.add(new Label("Minutes: "), 3, 14);
        grid.add(mins, 4, 14);
        grid.add(button, 5, 14);
	}
	
	private void SetDefaults() { 
        logDate.setValue(LocalDate.now());
        taskDate.setValue(LocalDate.now());
        taskName.setText(null);
        taskBody.setText(null);
        myComment.setText(null);
        hours.setText(null);
        mins.setText(null);
        taskTypeCBox.getSelectionModel().selectFirst();
        companyCBox.getItems().clear();
        companyCBox.getItems().addAll(model.CompanyList());
        companyCBox.getSelectionModel().selectFirst();
        LoadContacts();
	}
	
	public void LoadContacts() {
		contactCBox.getItems().clear();
		contactCBox.getItems().addAll(model.ContacNametList(companyCBox.getValue()));
		contactCBox.getSelectionModel().selectFirst();
	}
	
	public void SetTimeLog(TimeLog timeLog) {
		this.timeLog = timeLog;
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
