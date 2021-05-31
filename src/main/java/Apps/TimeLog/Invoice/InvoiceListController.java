package Apps.TimeLog.Invoice;

import java.util.List;

import Apps.TimeLog.Tools.Model;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Setter;

@SuppressWarnings("rawtypes")
public class InvoiceListController implements EventHandler {
	private final InvoiceList invoiceList;
	private @Setter List<Invoice> list;
	private Model model = Model.getModel();
	private Stage stage;

	public InvoiceListController() {
		stage = new Stage();
		this.invoiceList = new InvoiceList(this);
		this.loadData();
		this.fillTable();
		this.stage.setTitle("Invoice list");
		this.stage.setScene(new Scene(invoiceList, 500, 550));
		this.stage.show();
	}

	@Override
	public void handle(Event event) {
		final Object source = event.getSource();
		if (source.equals(this.invoiceList.getNewBtn())) {
			newEntry();
		}
		if (source.equals(this.invoiceList.getEditBtn())) {
			editEntry();
		}
	}

	void newEntry() {
		new InvoiceEntryController(new Invoice());
	}

	void editEntry() {
		Invoice invoice = (Invoice) this.invoiceList.getTableView().getSelectionModel().getSelectedItem();
		if (invoice != null) {
			new InvoiceEntryController(invoice);
		} else {
			model.msgW("No Invoice selected!");
		}
	}

	public void loadData() {
		this.list = this.model.loadInvoiceList();
	}

	public void fillTable() {
		this.invoiceList.fillTableView(list);
	}

}
