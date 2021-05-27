package Apps.TimeLog.Windows.List;

import Apps.TimeLog.Models.Contact;
import Apps.TimeLog.Windows.Entry.ContactEntry;
import javafx.geometry.Insets;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ContactList extends WindowList {

	private TableColumn<Contact, String> id;
	private TableColumn<Contact, String> name;
	private TableColumn<Contact, String> company;
	private TableView<Contact> tableView;

	public ContactList() {
		stage.setTitle("Contact List");
		tableView = new TableView<Contact>();
		AddColumns();
		LoadData();
		root.getChildren().add(tableView);
		tableView.setId("table-view");
	}

	public void LoadData() {
		tableView.getItems().clear();
		for (Contact temp : model.ContactList()) {
			tableView.getItems().add(temp);
		}
	}

	@SuppressWarnings("unchecked")
	void AddColumns() {
		id = new TableColumn<>("ID");
		id.setCellValueFactory(new PropertyValueFactory<>("id"));
		name = new TableColumn<>("Name");
		name.setCellValueFactory(new PropertyValueFactory<>("name"));
		company = new TableColumn<>("Company");
		company.setCellValueFactory(new PropertyValueFactory<>("company"));
		tableView.getColumns().addAll(id, name, company);
		tableView.setPadding(new Insets(5, 5, 5, 5));
	}

	@Override
	void newEntry() {
		new ContactEntry();
	}

	@Override
	void editEntry() {
		Contact contact = (Contact) tableView.getSelectionModel().getSelectedItem();
		if (contact != null) {
			ContactEntry contactEntry = new ContactEntry();
			contactEntry.setCustomer(contact);
		} else {
			model.msgW("No contact selected!");
		}
	}
}
