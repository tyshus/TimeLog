package Apps.TimeLog.TimeLog;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import lombok.Getter;

public class Report extends VBox {
	private TableColumn<TimeLog, Long> id = new TableColumn<>("Id");
	private TableColumn<TimeLog, String> contactName = new TableColumn<>("Contact Name");
	private TableColumn<TimeLog, LocalDate> logDate = new TableColumn<>("Date");
	private TableColumn<TimeLog, LocalDate> taskDate = new TableColumn<>("Task rec. date");
	private TableColumn<TimeLog, String> taskType = new TableColumn<>("Task type");
	private TableColumn<TimeLog, Double> taskName = new TableColumn<>("Task subject");
	private TableColumn<TimeLog, String> taskBody = new TableColumn<>("Task description");
	private TableColumn<TimeLog, Double> myComment = new TableColumn<>("My comments");
	private TableColumn<TimeLog, Double> time = new TableColumn<>("Time spent (hours)");
	private @Getter TableView<TimeLog> tableView = new TableView<TimeLog>();
	private @Getter double totalHours;
	private String printout;
	private Label totalLabel = new Label();
	private ComboBox<String> companyCBox = new ComboBox<String>();
	private DatePicker dateFrom = new DatePicker();
	private DatePicker dateTo = new DatePicker();
	private @Getter Button filterBtn = new Button("Filter");
	private @Getter Button mailBtn = new Button("Email");
	private @Getter Button invoiceBtn = new Button("Invoice");
	private @Getter Button editBtn = new Button("Edit");
	private GridPane grid = new GridPane();

	@SuppressWarnings("unchecked")
	public Report(ReportController reportController) {
		filterBtn.setOnAction(reportController);
		mailBtn.setOnAction(reportController);
		invoiceBtn.setOnAction(reportController);
		editBtn.setOnAction(reportController);
		setScene();
	}

	@SuppressWarnings("unchecked")
	void setScene() {
		grid.setVgap(10);
		grid.setHgap(10);
		grid.setPadding(new Insets(15, 10, 10, 10));
		dateFrom.setValue(LocalDate.now());
		dateTo.setValue(LocalDate.now());
		grid.add(editBtn, 1, 0);
		grid.add(companyCBox, 2, 0);
		grid.add(dateFrom, 3, 0);
		grid.add(dateTo, 4, 0);
		grid.add(filterBtn, 5, 0);
		grid.add(mailBtn, 6, 0);
		grid.add(invoiceBtn, 7, 0);
		grid.add(totalLabel, 8, 0);

		id.setCellValueFactory(new PropertyValueFactory<>("id"));
		contactName.setCellValueFactory(new PropertyValueFactory<>("contactName"));
		logDate.setCellValueFactory(new PropertyValueFactory<>("logDate"));
		taskDate.setCellValueFactory(new PropertyValueFactory<>("taskDate"));
		taskType.setCellValueFactory(new PropertyValueFactory<>("taskType"));
		taskName.setCellValueFactory(new PropertyValueFactory<>("taskName"));
		taskBody.setCellValueFactory(new PropertyValueFactory<>("taskBody"));
		myComment.setCellValueFactory(new PropertyValueFactory<>("myComment"));
		time.setCellValueFactory(new PropertyValueFactory<>("time"));
		tableView.getColumns().addAll(id, logDate, contactName, taskType, taskDate, taskName, time, taskBody,
				myComment);
		tableView.setPadding(new Insets(5, 5, 5, 5));
		taskName.setMaxWidth(400);
		taskName.setMinWidth(400);
		taskBody.setVisible(false);
		myComment.setVisible(false);
		this.getChildren().addAll(grid, tableView);
		tableView.setId("table-view");
	}

	void fillTableView(List<TimeLog> list) {
		tableView.getItems().clear();
		totalHours = 0;
		for (TimeLog temp : list) {
			tableView.getItems().add(temp);
			totalHours += temp.getTime();
		}
		if (totalHours > 0) {
			DecimalFormat df = new DecimalFormat("#.##");
			totalHours = Double.valueOf(df.format(totalHours));
			totalLabel.setText("Total hours: " + String.valueOf(totalHours));
		}
	}

	public void fillCompanies(ObservableList<String> list) {
		companyCBox.getItems().addAll(list);
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
