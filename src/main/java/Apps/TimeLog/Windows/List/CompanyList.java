package Apps.TimeLog.Windows.List;

import Apps.TimeLog.Models.Company;
import Apps.TimeLog.Windows.Entry.CompanyEntry;
import javafx.geometry.Insets;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class CompanyList extends WindowList {
	private TableColumn<Company, String> id;
	private TableColumn<Company, String> name;
	private TableColumn<Company, String> address;
	private TableColumn<Company, String> code;
	private TableColumn<Company, String> vatRegNo;
	private TableView<Company> tableView;

	public CompanyList() {
		stage.setTitle("Company List");
		tableView = new TableView<Company>();
		addColumns();
		loadData();
		root.getChildren().add(tableView);
		tableView.setId("table-view");
	}

	@Override
	void newEntry() {
		new CompanyEntry();
	}

	@Override
	void editEntry() {
		Company company = (Company) tableView.getSelectionModel().getSelectedItem();
		if (company != null) {
			CompanyEntry companyEntry = new CompanyEntry();
			companyEntry.setCompany(company);
		} else {
			model.msgW("No company selected!");
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	void addColumns() {
		id = new TableColumn<>("Code");
		id.setCellValueFactory(new PropertyValueFactory<>("id"));
		name = new TableColumn<>("Name");
		name.setCellValueFactory(new PropertyValueFactory<>("name"));
		address = new TableColumn<>("Address");
		address.setCellValueFactory(new PropertyValueFactory<>("address"));
		code = new TableColumn<>("Company code");
		code.setCellValueFactory(new PropertyValueFactory<>("code"));
		vatRegNo = new TableColumn<>("VAT reg. no.");
		vatRegNo.setCellValueFactory(new PropertyValueFactory<>("vatRegNo"));
		tableView.getColumns().addAll(id, name, address, code, vatRegNo);
		tableView.setPadding(new Insets(5, 5, 5, 5));
	}

	@Override
	void loadData() {
		tableView.getItems().clear();
		for (Company temp : model.loadCompanyList()) {
			tableView.getItems().add(temp);
		}
	}
}
