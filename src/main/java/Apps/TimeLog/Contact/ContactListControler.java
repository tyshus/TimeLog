package Apps.TimeLog.Contact;

import java.util.List;

import Apps.TimeLog.Models.Model;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Setter;

@SuppressWarnings("rawtypes")
public class ContactListControler implements EventHandler {
	private final ContactList contactList;
	private @Setter List<Contact> list;
	private Model model = Model.getModel();
	private Stage stage;

	public ContactListControler() {
		stage = new Stage();
		this.contactList = new ContactList(this);
		this.loadData();
		this.fillTable();
		this.stage.setTitle("Contact list");
		this.stage.setScene(new Scene(contactList, 500, 550));
		this.stage.show();
	}

	@Override
	public void handle(Event event) {
		final Object source = event.getSource();
		if (source.equals(this.contactList.getNewBtn())) {
			newEntry();
		}
		if (source.equals(this.contactList.getEditBtn())) {
			editEntry();
		}
	}

	void newEntry() {
		new ContactEntryControler(new Contact());
	}

	void editEntry() {
		Contact contact = (Contact) this.contactList.getTableView().getSelectionModel().getSelectedItem();
		if (contact != null) {
			new ContactEntryControler(contact);
		} else {
			model.msgW("No contact selected!");
		}
	}

	public void loadData() {
		this.list = this.model.loadContactList();
	}

	public void fillTable() {
		this.contactList.filltableView(list);
	}

}
