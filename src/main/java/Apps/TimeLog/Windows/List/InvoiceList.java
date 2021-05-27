package Apps.TimeLog.Windows.List;

import java.time.LocalDate;

import Apps.TimeLog.Models.Invoice;
import Apps.TimeLog.Tools.PrintInvoice;
import Apps.TimeLog.Windows.Entry.InvoiceEntry;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class InvoiceList extends WindowList {
	private TableColumn<Invoice, String> serNo;
	private TableColumn<Invoice, LocalDate> date;
	private TableColumn<Invoice, String> company;
	private TableColumn<Invoice, String> operation;
	private TableColumn<Invoice, String> period;
	private TableColumn<Invoice, Double> amount;
	private TableView<Invoice> tableView;
	private Button printBtn = new Button("Print");

	public InvoiceList() {
		stage.setTitle("Invoice List");
		printBtn.setOnAction(value -> {
			printInvoice();
		});
		grid.add(printBtn, 2, 0);
		tableView = new TableView<Invoice>();
		addColumns();
		loadData();
		root.getChildren().add(tableView);
		tableView.setId("table-view");
	}

	void printInvoice() {
		Invoice invoice = (Invoice) tableView.getSelectionModel().getSelectedItem();
		if (invoice != null) {
			try {
				new PrintInvoice(invoice);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			model.msgW("No invoice selected!");
		}
	}

	@Override
	void newEntry() {
		new InvoiceEntry();
	}

	@Override
	void editEntry() {
		Invoice invoice = (Invoice) tableView.getSelectionModel().getSelectedItem();
		if (invoice != null) {
			InvoiceEntry companyEntry = new InvoiceEntry();
			companyEntry.setInvoice(invoice);
		} else {
			model.msgW("No invoice selected!");
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	void addColumns() {
		serNo = new TableColumn<>("Ser. no.");
		serNo.setCellValueFactory(new PropertyValueFactory<>("serno"));
		date = new TableColumn<>("Invoice date");
		date.setCellValueFactory(new PropertyValueFactory<>("date"));
		company = new TableColumn<>("Company");
		company.setCellValueFactory(new PropertyValueFactory<>("company"));
		operation = new TableColumn<>("Operation");
		operation.setCellValueFactory(new PropertyValueFactory<>("operation"));
		period = new TableColumn<>("Period");
		period.setCellValueFactory(new PropertyValueFactory<>("period"));
		amount = new TableColumn<>("Amount");
		amount.setCellValueFactory(new PropertyValueFactory<>("amount"));
		tableView.getColumns().addAll(serNo, date, company, operation, period, amount);
		tableView.setPadding(new Insets(5, 5, 5, 5));
	}

	@Override
	void loadData() {
		tableView.getItems().clear();
		for (Invoice temp : model.loadInvoiceList()) {
			tableView.getItems().add(temp);
		}
	}
}
