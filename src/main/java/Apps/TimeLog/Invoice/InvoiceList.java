package Apps.TimeLog.Invoice;

import java.time.LocalDate;
import java.util.List;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import lombok.Getter;

public class InvoiceList extends VBox {
	private TableColumn<Invoice, String> serNo = new TableColumn<>("Ser. no.");
	private TableColumn<Invoice, LocalDate> date = new TableColumn<>("Invoice date");
	private TableColumn<Invoice, String> company = new TableColumn<>("Company");
	private TableColumn<Invoice, String> operation = new TableColumn<>("Operation");
	private TableColumn<Invoice, String> period = new TableColumn<>("Period");
	private TableColumn<Invoice, Double> amount = new TableColumn<>("Amount");
	private @Getter TableView<Invoice> tableView = new TableView<Invoice>();
	private @Getter Button newBtn = new Button("New");
	private @Getter Button editBtn = new Button("Edit");
	private GridPane grid = new GridPane();

	@SuppressWarnings("unchecked")
	public InvoiceList(InvoiceListControler invoiceListControler) {
		newBtn.setOnAction(invoiceListControler);
		editBtn.setOnAction(invoiceListControler);
		setScene();
	}

	@SuppressWarnings("unchecked")
	void setScene() {
		this.serNo.setCellValueFactory(new PropertyValueFactory<>("serno"));
		this.date.setCellValueFactory(new PropertyValueFactory<>("date"));
		this.company.setCellValueFactory(new PropertyValueFactory<>("company"));
		this.operation.setCellValueFactory(new PropertyValueFactory<>("operation"));
		this.period.setCellValueFactory(new PropertyValueFactory<>("period"));
		this.amount.setCellValueFactory(new PropertyValueFactory<>("amount"));
		this.tableView.getColumns().addAll(serNo, date, company, operation, period, amount);
		this.tableView.setPadding(new Insets(5, 5, 5, 5));
		this.grid.setVgap(10);
		this.grid.setHgap(10);
		this.grid.setPadding(new Insets(15, 10, 10, 10));
		this.grid.add(newBtn, 0, 0);
		this.grid.add(editBtn, 1, 0);
		this.getChildren().add(grid);
		this.getChildren().add(tableView);
		this.getStylesheets().add("windowList.css");
		this.tableView.setId("table-view");
	}

	public void fillTableView(List<Invoice> list) {
		this.tableView.getItems().clear();
		this.tableView.getItems().addAll(list);
	}

}
