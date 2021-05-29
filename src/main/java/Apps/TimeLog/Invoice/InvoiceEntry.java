package Apps.TimeLog.Invoice;

import java.time.LocalDate;

import Apps.TimeLog.Windows.Fields.DoubleTextField;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.Getter;

public class InvoiceEntry extends VBox {
	private TextField serNo = new TextField("");
	private DatePicker date = new DatePicker();
	private ComboBox<String> company = new ComboBox<String>();
	private TextField operation = new TextField("");
	private TextField period = new TextField("");
	private DoubleTextField amount = new DoubleTextField();
	private CheckBox sent = new CheckBox("Sent");
	private CheckBox printed = new CheckBox("Printed");
	private @Getter Button printBtn = new Button("Print");
	private @Getter Button mailBtn = new Button("Email");
	private @Getter Button saveBtn = new Button("Save");
	private GridPane grid = new GridPane();

	@SuppressWarnings("unchecked")
	public InvoiceEntry(InvoiceEntryControler invoiceEntryControler) {
		saveBtn.setOnAction(invoiceEntryControler);
		printBtn.setOnAction(invoiceEntryControler);
		mailBtn.setOnAction(invoiceEntryControler);
		this.setScene();
	}

	private void setScene() {
		serNo.setPrefWidth(100);
		serNo.setMaxWidth(100);
		HBox hline = new HBox();
		hline.setSpacing(15.0);
		hline.getChildren().addAll(new Label("Ser. no.:"), serNo, printed, sent);
		HBox bline = new HBox();
		bline.setSpacing(20.0);
		bline.getChildren().addAll(saveBtn, printBtn, mailBtn);

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
		grid.setVgap(4);
		grid.setHgap(10);
		grid.setPadding(new Insets(10, 10, 10, 10));
		this.getChildren().add(grid);
	}

	private void setInvoice() {
		company.getSelectionModel().selectFirst();
		date.setValue(LocalDate.now());
	}

	public void setInvoice(Invoice invoice) {
		if (invoice.getCompany() == null) {
			setInvoice();
		} else {
			serNo.setText(invoice.getSerno());
			company.setValue(invoice.getCompany());
			date.setValue(invoice.getDate());
			operation.setText(invoice.getOperation());
			period.setText(invoice.getPeriod());
			amount.SetDouble(invoice.getAmount());
			printed.setSelected(invoice.isPrinted());
			sent.setSelected(invoice.isSent());
		}
		if (invoice.isPrinted()) {
			if (!invoice.isSent()) {
				mailBtn.setDisable(false);
			}
		} else {
			if (invoice.getSerno() != null) {
				printBtn.setDisable(false);
			}
		}
	}

	public void getInvoice(Invoice invoice) {
		invoice.setCompany(company.getValue());
		invoice.setDate(date.getValue());
		invoice.setOperation(operation.getText());
		invoice.setPeriod(period.getText());
		invoice.setAmount(amount.getDouble());
	}

	public void fillCompanies(ObservableList<String> list) {
		company.getItems().addAll(list);
	}
}
